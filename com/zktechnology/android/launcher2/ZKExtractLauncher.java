package com.zktechnology.android.launcher2;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.bean.ExtUserBean;
import com.zktechnology.android.face.FaceDBUtils;
import com.zktechnology.android.push.err.ErrorPush;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.service.IPCCallBack;
import com.zktechnology.android.service.IPCServerService;
import com.zktechnology.android.utils.AES256Util;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.utils.ZKRunnable;
import com.zktechnology.android.utils.ZKThreadPool;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.ErrorLog;
import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.db.orm.util.Base64Encoder;
import com.zkteco.android.logfile.LogManager;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import com.zkteco.liveface562.ZkFaceManager;
import com.zkteco.liveface562.bean.ZkExtractResult;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ZKExtractLauncher extends ZKCommMethodLauncher implements IPCCallBack {
    /* access modifiers changed from: private */
    public static final ZKFileObserver.OnFileChangeListener BIO_FACE_LISTENER = $$Lambda$ZKExtractLauncher$Cn2CBLPQ0nyXatxkp4IqsC18Aew.INSTANCE;
    /* access modifiers changed from: private */
    public static final ReentrantLock LOCK = new ReentrantLock();
    private static final int MAX_COUNT = 10000;
    /* access modifiers changed from: private */
    public static final String SD_CARD_PATH;
    private static final String TAG = "ReceivePush";
    public static final int TEMPLATE_TYPE_FACE_VL = 9;
    private static final ZKFileObserver.OnFileChangeListener USER_PHOTO_LISTENER = $$Lambda$ZKExtractLauncher$hW8q4R04YYvCtmctugG_wTmrl_8.INSTANCE;
    private static final String VALUE_TYPE = "update";
    /* access modifiers changed from: private */
    public static final ZKFileObserver ZK_BIO_PHOTO_FILE_OBSERVER;
    private static final ZKFileObserver ZK_PHOTO_FILE_OBSERVER;
    private static Subject<String> delayExtPaths = PublishSubject.create();
    private static final List<ExtUserBean> extCache = Collections.synchronizedList(new ArrayList(100));
    /* access modifiers changed from: private */
    public static ExtTempTask mExtTempTask;
    private DataManager dataManager;
    /* access modifiers changed from: private */
    public Disposable delayExtDisposable;
    /* access modifiers changed from: private */
    public volatile boolean isStartTask;
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<String> mDeleteCacheQueue = new LinkedBlockingQueue<>(MAX_COUNT);
    private DeleteTask mDeleteTask;
    private Thread mDeleteThread = null;
    private Thread mExtractThread = null;
    private Future<?> mFuture;
    private final LinkedTransferQueue<String> mUpdateTemplateQueue = new LinkedTransferQueue<>();
    private FileObserver zkBiophotoDirObserver;

    static {
        String path = Environment.getExternalStorageDirectory().getPath();
        SD_CARD_PATH = path;
        ZK_BIO_PHOTO_FILE_OBSERVER = new ZKFileObserver(path + "/ZKTeco/data/biophoto/face");
        ZK_PHOTO_FILE_OBSERVER = new ZKFileObserver(path + "/ZKTeco/data/photo");
    }

    public static void pauseExtract() {
        Log.d(TAG, "pauseExtract");
        try {
            LOCK.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resumeExtract() {
        Log.d(TAG, "resumeExtract");
        try {
            LOCK.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static /* synthetic */ void lambda$static$0(int i, String str) {
        if (i == 8) {
            Log.d(TAG, "onFileChanged：" + str);
            delayExtPaths.onNext(str);
        }
    }

    static /* synthetic */ void lambda$static$1(int i, String str) {
        if (i == 8) {
            String str2 = SD_CARD_PATH + "/ZKTeco/data/photo/" + str;
            String pinFromPath = getPinFromPath(str2);
            if (!isPinInUserInfo(pinFromPath)) {
                File file = new File(str2);
                if (file.exists()) {
                    file.delete();
                }
                FileLogUtils.writeExtractLog("Error: Pin is not exist: userPhotoListener delete this photo, fileName： " + pinFromPath);
            }
        }
    }

    private static class ZKDirObserver extends FileObserver {
        private WeakReference<ZKExtractLauncher> obj;

        private ZKDirObserver(String str, ZKExtractLauncher zKExtractLauncher) {
            super(str);
            this.obj = new WeakReference<>(zKExtractLauncher);
        }

        public void onEvent(int i, String str) {
            int i2 = i & 4095;
            if (i2 != 256) {
                if (i2 == 512) {
                    ZKExtractLauncher.ZK_BIO_PHOTO_FILE_OBSERVER.stopWatching();
                }
            } else if (this.obj.get() != null) {
                ZKExtractLauncher.ZK_BIO_PHOTO_FILE_OBSERVER.start(ZKExtractLauncher.BIO_FACE_LISTENER);
            }
        }
    }

    /* access modifiers changed from: private */
    public static ExtUserBean getTheCacheInExtCache(String str) {
        ExtUserBean extUserBean;
        List<ExtUserBean> list = extCache;
        synchronized (list) {
            extUserBean = null;
            for (ExtUserBean next : list) {
                if (next.getUserPin().equals(str) && SystemClock.elapsedRealtime() - next.getTimeStamp() <= 5000) {
                    extUserBean = next;
                }
            }
        }
        return extUserBean;
    }

    /* access modifiers changed from: private */
    public static void updateResultExtCache(String str, int i) {
        List<ExtUserBean> list = extCache;
        synchronized (list) {
            for (ExtUserBean next : list) {
                if (next.getUserPin().equals(str)) {
                    next.setResult(i);
                }
            }
        }
    }

    private static void updateCmdIdExtCache(String str, String str2) {
        List<ExtUserBean> list = extCache;
        synchronized (list) {
            for (ExtUserBean next : list) {
                if (next.getUserPin().equals(str)) {
                    next.setCmdId(str2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void insertToExtCache(ExtUserBean extUserBean) {
        List<ExtUserBean> list = extCache;
        synchronized (list) {
            if (list.size() >= 100) {
                list.remove(0);
            }
            list.add(extUserBean);
        }
    }

    /* access modifiers changed from: private */
    public static void pushErrorLog(ExtUserBean extUserBean) {
        String str;
        String cmdId = extUserBean.getCmdId();
        if (TextUtils.isEmpty(cmdId)) {
            ExtUserBean theCacheInExtCache = getTheCacheInExtCache(extUserBean.getUserPin());
            if (theCacheInExtCache != null && !TextUtils.isEmpty(theCacheInExtCache.getCmdId())) {
                cmdId = theCacheInExtCache.getCmdId();
            } else {
                return;
            }
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userPin", extUserBean.getUserPin());
            str = jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str = "";
        }
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorCode("D01E0006");
        errorLog.setDataorigin("dev");
        errorLog.setCmdID(cmdId);
        errorLog.setAdditional(Base64Encoder.encode(str.getBytes()));
        ErrorPush.getInstance().addErrorLog(errorLog);
    }

    private static boolean isPinAlreadyExist(String str) {
        boolean z = false;
        Cursor cursor = null;
        try {
            Cursor queryBySql = DBManager.getInstance().queryBySql("select count(*) from Pers_BioTemplate WHERE bio_type = 9 And USER_PIN = '" + str + "'");
            if (queryBySql != null) {
                queryBySql.moveToFirst();
                if (queryBySql.getLong(0) > 0) {
                    z = true;
                }
            } else {
                LogManager.getInstance().OutputLog("ZK_L_IS_EMPTY", "cursor == null");
            }
            if (queryBySql != null) {
                queryBySql.close();
            }
            return z;
        } catch (Exception e) {
            LogManager.getInstance().OutputLog("ZK_L_IS_EMPTY", "Exc:" + e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable unused) {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isPinInUserInfo(String str) {
        boolean z = false;
        Cursor cursor = null;
        try {
            Cursor queryBySql = DBManager.getInstance().queryBySql("select count(*) from USER_INFO WHERE USER_PIN = '" + str + "'");
            if (queryBySql != null) {
                queryBySql.moveToFirst();
                if (queryBySql.getLong(0) > 0) {
                    z = true;
                }
            } else {
                LogManager.getInstance().OutputLog("ZK_L_IS_EMPTY", "isPinInUserInfo: cursor == null");
            }
            if (queryBySql != null) {
                queryBySql.close();
            }
            return z;
        } catch (Exception e) {
            LogManager.getInstance().OutputLog("ZK_L_IS_EMPTY", "isPinInUserInfo: Exc:" + e.getMessage());
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable unused) {
            if (cursor != null) {
                cursor.close();
            }
            return false;
        }
    }

    public void onUserUpdate(String str, String str2, String str3, String str4) {
        Thread thread = this.mExtractThread;
        if (thread == null || !thread.isAlive()) {
            if (this.mExtractThread != null) {
                this.mExtractThread = null;
            }
            startExtractThread();
        }
        if (TextUtils.isEmpty(str) || !VALUE_TYPE.equals(str) || TextUtils.isEmpty(str3)) {
            FileLogUtils.writeExtractLog("Error: onUserUpdate type = " + str + "    picName = " + str3);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("userPin is null!");
            LogManager.getInstance().OutputLog("ZK_L_CALLBACK", stringBuffer.toString());
            return;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("picName:");
        stringBuffer2.append(str3);
        LogManager.getInstance().OutputLog("ZK_L_CALLBACK", stringBuffer2.toString());
        FileLogUtils.writeExtractLog("onUserUpdate picname = " + str3 + "cmdId =" + str2);
        ExtUserBean theCacheInExtCache = getTheCacheInExtCache(str3);
        if (theCacheInExtCache == null) {
            ExtUserBean extUserBean = new ExtUserBean();
            extUserBean.setUserPin(str3);
            extUserBean.setCmdId(str2);
            insertToExtCache(extUserBean);
            mExtTempTask.putExtTask(extUserBean);
            return;
        }
        int result = theCacheInExtCache.getResult();
        if (result == -1) {
            updateCmdIdExtCache(str3, str2);
        } else if (result == 0) {
            theCacheInExtCache.setCmdId(str2);
            pushErrorLog(theCacheInExtCache);
            updateCmdIdExtCache(str3, str2);
        }
    }

    public void deleteSingleUser(String str) {
        Thread thread = this.mDeleteThread;
        if (thread == null || !thread.isAlive()) {
            if (this.mDeleteThread != null) {
                this.mDeleteThread = null;
            }
            startDeleteThread();
        }
        if (!TextUtils.isEmpty(str)) {
            this.mDeleteCacheQueue.offer(str);
        }
    }

    public void deleteAll() {
        Log.i("IPC", "updateUserPic: delete all");
        ZkFaceManager.getInstance().dbClear();
    }

    public void onTemplateUpdate(String str, int i) {
        if (i == 9) {
            Future<?> future = this.mFuture;
            if (future == null || future.isDone() || this.mFuture.isCancelled()) {
                this.mFuture = ZKThreadPool.getInstance().commitTask(new Runnable() {
                    public final void run() {
                        ZKExtractLauncher.this.lambda$onTemplateUpdate$2$ZKExtractLauncher();
                    }
                });
            }
            this.mUpdateTemplateQueue.offer(str);
        }
    }

    public /* synthetic */ void lambda$onTemplateUpdate$2$ZKExtractLauncher() {
        Thread.currentThread().setName("thread-template-update");
        Log.e(TAG, "onTemplateUpdate: start thread---" + Thread.currentThread().getName());
        while (this.isStartTask) {
            String str = null;
            try {
                str = this.mUpdateTemplateQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (str != null) {
                try {
                    List<PersBiotemplate> query = new PersBiotemplate().getQueryBuilder().where().eq("user_pin", str).and().eq("bio_type", 9).query();
                    if (query != null && !query.isEmpty()) {
                        for (PersBiotemplate persBiotemplate : query) {
                            PersBiotemplatedata persBiotemplatedata = (PersBiotemplatedata) new PersBiotemplatedata().queryForId(Long.valueOf(persBiotemplate.getID()));
                            if (persBiotemplatedata != null) {
                                ZkFaceManager.getInstance().dbAdd(persBiotemplate.getUser_pin(), persBiotemplatedata.getTemplate_data());
                            }
                        }
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private static class DeleteTask extends ZKRunnable<ZKExtractLauncher> {
        public DeleteTask(ZKExtractLauncher zKExtractLauncher) {
            super(zKExtractLauncher);
        }

        public void run(ZKExtractLauncher zKExtractLauncher) {
            String str;
            while (zKExtractLauncher.isStartTask && (str = (String) zKExtractLauncher.mDeleteCacheQueue.take()) != null) {
                try {
                    zKExtractLauncher.startDeleteFaceTemplateByPin(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StringBuilder sb = new StringBuilder();
        String str = SD_CARD_PATH;
        File file = new File(sb.append(str).append("/ZKTeco/data/biophoto/face").toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        this.isStartTask = true;
        this.zkBiophotoDirObserver = new ZKDirObserver(str + "/ZKTeco/data/biophoto/", this);
        connectService();
        delayExtPaths.observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).delay(3, TimeUnit.SECONDS).subscribe(new Observer<String>() {
            public void onComplete() {
            }

            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = ZKExtractLauncher.this.delayExtDisposable = disposable;
            }

            public void onNext(String str) {
                Log.d(ZKExtractLauncher.TAG, "delay" + str);
                if (ZKExtractLauncher.getTheCacheInExtCache(ZKExtractLauncher.getPinFromPath(str)) == null) {
                    ExtUserBean extUserBean = new ExtUserBean();
                    extUserBean.setUserPin(ZKExtractLauncher.getPinFromPath(str));
                    ZKExtractLauncher.insertToExtCache(extUserBean);
                    ZKExtractLauncher.mExtTempTask.putExtTask(extUserBean);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initDB() {
        this.dataManager = DBManager.getInstance();
    }

    public void onDestroy() {
        super.onDestroy();
        this.isStartTask = false;
        Future<?> future = this.mFuture;
        if (future != null && !future.isDone() && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
        Disposable disposable = this.delayExtDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.delayExtDisposable.dispose();
        }
        try {
            this.mDeleteThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ZKFileObserver zKFileObserver = ZK_PHOTO_FILE_OBSERVER;
        if (zKFileObserver != null) {
            zKFileObserver.stopWatching();
        }
        ZKFileObserver zKFileObserver2 = ZK_BIO_PHOTO_FILE_OBSERVER;
        if (zKFileObserver2 != null) {
            zKFileObserver2.stopWatching();
        }
        FileObserver fileObserver = this.zkBiophotoDirObserver;
        if (fileObserver != null) {
            fileObserver.stopWatching();
            this.zkBiophotoDirObserver = null;
        }
        mExtTempTask.stopExtTempTask();
        disconnectService();
    }

    /* access modifiers changed from: protected */
    public void startCheckPushBioPhotoThread() {
        startExtractThread();
        startDeleteThread();
        startFileCheckObservers();
    }

    private void startFileCheckObservers() {
        this.zkBiophotoDirObserver.startWatching();
        ZK_BIO_PHOTO_FILE_OBSERVER.start(BIO_FACE_LISTENER);
        ZK_PHOTO_FILE_OBSERVER.start(USER_PHOTO_LISTENER);
    }

    /* access modifiers changed from: private */
    public void checkAllBioPhoto() {
        String[] list = new File(SD_CARD_PATH + "/ZKTeco/data/biophoto/face").list();
        if (list == null) {
            list = new String[0];
        }
        for (String str : list) {
            if (!isPinAlreadyExist(getPinFromPath(str))) {
                ExtUserBean extUserBean = new ExtUserBean();
                extUserBean.setUserPin(getPinFromPath(str));
                mExtTempTask.putExtTask(extUserBean);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void checkPhoto() {
        String str = SD_CARD_PATH + "/ZKTeco/data/photo";
        String[] list = new File(str).list();
        if (list != null) {
            for (String pinFromPath : list) {
                String pinFromPath2 = getPinFromPath(pinFromPath);
                if (!isPinInUserInfo(pinFromPath2)) {
                    File file = new File(str + "/" + pinFromPath2 + ZKFilePath.SUFFIX_IMAGE);
                    if (file.exists()) {
                        file.delete();
                    }
                    FileLogUtils.writeExtractLog("Error: Pin is not exist: checkPhoto delete this photo from photo dir    fileName： " + pinFromPath2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static String getPinFromPath(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 == -1) {
            return "";
        }
        return str.substring(lastIndexOf + 1, lastIndexOf2);
    }

    private static class ExtractTaskListener implements ExtTempTask.ExtractTaskInterface {
        private ExtractTaskListener() {
        }

        public boolean startTask(String str, ZKExtractLauncher zKExtractLauncher) {
            return zKExtractLauncher.startExtTask(str);
        }

        public boolean isNeedExtract(String str, ZKExtractLauncher zKExtractLauncher) {
            if (ZKExtractLauncher.isPinInUserInfo(str)) {
                ExtUserBean access$800 = ZKExtractLauncher.getTheCacheInExtCache(str);
                if (access$800 == null) {
                    return true;
                }
                if (access$800.getResult() == -1) {
                    return true;
                }
                return false;
            }
            File file = new File(ZKExtractLauncher.SD_CARD_PATH + "/ZKTeco/data/biophoto/face/" + str + ZKFilePath.SUFFIX_IMAGE);
            File file2 = new File(ZKExtractLauncher.SD_CARD_PATH + "/ZKTeco/data/temp/" + str + "-temp" + ZKFilePath.SUFFIX_IMAGE);
            if (file.exists()) {
                file.delete();
            }
            if (file2.exists()) {
                file2.delete();
            }
            FileLogUtils.writeExtractLog("Error: isNeedExtract(), Pin is not exist: delete this photo    fileName： " + str);
            return false;
        }

        public void extSuccess(ExtUserBean extUserBean, ZKExtractLauncher zKExtractLauncher) {
            ZKExtractLauncher.updateResultExtCache(extUserBean.getUserPin(), extUserBean.getResult());
            File file = new File(ZKExtractLauncher.SD_CARD_PATH + "/ZKTeco/data/biophoto/face/" + extUserBean.getUserPin() + ZKFilePath.SUFFIX_IMAGE);
            File file2 = new File(ZKExtractLauncher.SD_CARD_PATH + "/ZKTeco/data/temp/" + extUserBean.getUserPin() + "-temp" + ZKFilePath.SUFFIX_IMAGE);
            if (file2.exists()) {
                file2.delete();
            }
            if ("0".equals(ZKLauncher.sSaveBioPhotoFunOn) && file.exists()) {
                file.delete();
            }
        }

        public void extFailed(ExtUserBean extUserBean, ZKExtractLauncher zKExtractLauncher) {
            ZKExtractLauncher.updateResultExtCache(extUserBean.getUserPin(), extUserBean.getResult());
            File file = new File(ZKExtractLauncher.SD_CARD_PATH + "/ZKTeco/data/temp/" + extUserBean.getUserPin() + "-temp" + ZKFilePath.SUFFIX_IMAGE);
            if (file.exists()) {
                file.delete();
            }
            ZKExtractLauncher.pushErrorLog(extUserBean);
        }

        public void onInit(ZKExtractLauncher zKExtractLauncher) {
            zKExtractLauncher.checkAllBioPhoto();
            ZKExtractLauncher.checkPhoto();
        }
    }

    private void startExtractThread() {
        Thread thread = this.mExtractThread;
        if (thread == null || !thread.isAlive()) {
            mExtTempTask = new ExtTempTask(new ExtractTaskListener(), this);
            Thread thread2 = new Thread(mExtTempTask);
            this.mExtractThread = thread2;
            thread2.setName("checkPushBioPhotoThread");
            this.mExtractThread.start();
        }
    }

    private void startDeleteThread() {
        this.mDeleteTask = new DeleteTask(this);
        Thread thread = new Thread(this.mDeleteTask, "deleteFaceTemplateThread");
        this.mDeleteThread = thread;
        thread.start();
    }

    /* access modifiers changed from: private */
    public void startDeleteFaceTemplateByPin(String str) {
        LogUtils.d("ReceivePush-delete", "pin = " + str);
        if (!TextUtils.isEmpty(str)) {
            FaceDBUtils.deleteUserTemplateInFaceAnalyzer(str);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        com.zkteco.android.logfile.LogManager.getInstance().OutputLog("ZK_L_TASK", "Exc: query PersBiotemplatedata");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0277, code lost:
        if (r11 != null) goto L_0x0279;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0279, code lost:
        r11.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x027c, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x027d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x026e */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x015a  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01b4  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01d9  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0280  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean updateDBTemplateData(java.lang.String r18, byte[] r19) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            java.lang.String r4 = "ZK_L_TASK"
            long r5 = android.os.SystemClock.elapsedRealtime()
            com.zkteco.liveface562.ZkFaceManager r0 = com.zkteco.liveface562.ZkFaceManager.getInstance()
            int r0 = r0.dbDel(r2)
            r7 = 0
            java.lang.Integer r8 = java.lang.Integer.valueOf(r7)
            if (r0 == 0) goto L_0x001c
            return r7
        L_0x001c:
            com.zkteco.liveface562.ZkFaceManager r0 = com.zkteco.liveface562.ZkFaceManager.getInstance()
            int r0 = r0.dbAdd(r2, r3)
            if (r0 == 0) goto L_0x0027
            return r7
        L_0x0027:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "更新face service: 耗时: "
            java.lang.StringBuilder r0 = r0.append(r9)
            long r9 = android.os.SystemClock.elapsedRealtime()
            long r9 = r9 - r5
            java.lang.StringBuilder r0 = r0.append(r9)
            java.lang.String r0 = r0.toString()
            java.lang.String r5 = "ReceivePush"
            android.util.Log.d(r5, r0)
            long r9 = android.os.SystemClock.elapsedRealtime()
            r6 = 9
            com.zkteco.android.db.orm.tna.PersBiotemplate r0 = new com.zkteco.android.db.orm.tna.PersBiotemplate     // Catch:{ SQLException -> 0x008d }
            r0.<init>()     // Catch:{ SQLException -> 0x008d }
            com.j256.ormlite.stmt.QueryBuilder r0 = r0.getQueryBuilder()     // Catch:{ SQLException -> 0x008d }
            com.j256.ormlite.stmt.Where r0 = r0.where()     // Catch:{ SQLException -> 0x008d }
            java.lang.String r12 = "user_pin"
            com.j256.ormlite.stmt.Where r0 = r0.eq(r12, r2)     // Catch:{ SQLException -> 0x008d }
            com.j256.ormlite.stmt.Where r0 = r0.and()     // Catch:{ SQLException -> 0x008d }
            java.lang.String r12 = "bio_type"
            java.lang.Integer r13 = java.lang.Integer.valueOf(r6)     // Catch:{ SQLException -> 0x008d }
            com.j256.ormlite.stmt.Where r0 = r0.eq(r12, r13)     // Catch:{ SQLException -> 0x008d }
            java.util.List r12 = r0.query()     // Catch:{ SQLException -> 0x008d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x008b }
            r0.<init>()     // Catch:{ SQLException -> 0x008b }
            java.lang.String r13 = "查询PersBiotemplate: 耗时: "
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ SQLException -> 0x008b }
            long r13 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SQLException -> 0x008b }
            long r13 = r13 - r9
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ SQLException -> 0x008b }
            java.lang.String r0 = r0.toString()     // Catch:{ SQLException -> 0x008b }
            android.util.Log.d(r5, r0)     // Catch:{ SQLException -> 0x008b }
            goto L_0x00c7
        L_0x008b:
            r0 = move-exception
            goto L_0x008f
        L_0x008d:
            r0 = move-exception
            r12 = 0
        L_0x008f:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Error: updateDBTemplateData query PersBiotemplate Error："
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = r0.getMessage()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeExtractLog(r9)
            com.zkteco.android.logfile.LogManager r9 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r13 = "Exc: query PersBiotemplate Error, "
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r10.append(r0)
            java.lang.String r0 = r0.toString()
            r9.OutputLog(r4, r0)
        L_0x00c7:
            if (r12 == 0) goto L_0x00d6
            boolean r0 = r12.isEmpty()
            if (r0 != 0) goto L_0x00d6
            java.lang.Object r0 = r12.get(r7)
            com.zkteco.android.db.orm.tna.PersBiotemplate r0 = (com.zkteco.android.db.orm.tna.PersBiotemplate) r0
            goto L_0x00d7
        L_0x00d6:
            r0 = 0
        L_0x00d7:
            com.zkteco.android.logfile.LogManager r9 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r12 = "oldPersBiotemplate = null is "
            java.lang.StringBuilder r10 = r10.append(r12)
            if (r0 != 0) goto L_0x00ea
            r13 = 1
            goto L_0x00eb
        L_0x00ea:
            r13 = r7
        L_0x00eb:
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r10 = r10.toString()
            r9.OutputLog(r4, r10)
            long r9 = android.os.SystemClock.elapsedRealtime()
            r13 = 5
            java.lang.String r14 = "ZKDB.db"
            r15 = 3
            r16 = 2
            if (r0 == 0) goto L_0x015a
            int r0 = r0.getTemplate_id()
            long r11 = (long) r0
            java.lang.Object[] r0 = new java.lang.Object[r13]
            r0[r7] = r3
            r2 = 1
            r0[r2] = r8
            r0[r16] = r8
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            r0[r15] = r3
            java.lang.Long r2 = java.lang.Long.valueOf(r11)
            r3 = 4
            r0[r3] = r2
            com.zkteco.android.db.orm.manager.DataManager r2 = r1.dataManager
            java.lang.String r3 = "update Pers_BioTemplateData set template_data=?, CREATE_ID=?, MODIFY_TIME=?, SEND_FLAG=? WHERE ID=?"
            r2.executeSql(r14, r3, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "updateDBTemplateData: 更新PersBiotemplateData耗时: "
            java.lang.StringBuilder r0 = r0.append(r2)
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r2 = r2 - r9
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r5, r0)
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r2 = "updateBioTemplateData-->ID:"
            r0.append(r2)
            r0.append(r11)
            com.zkteco.android.logfile.LogManager r2 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.String r0 = r0.toString()
            r2.OutputLog(r4, r0)
        L_0x0157:
            r0 = 1
            goto L_0x0241
        L_0x015a:
            r0 = 4
            java.lang.Object[] r11 = new java.lang.Object[r0]
            r11[r7] = r3
            r0 = 1
            r11[r0] = r8
            r11[r16] = r8
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            r11[r15] = r3
            com.zkteco.android.db.orm.manager.DataManager r0 = r1.dataManager
            java.lang.String r3 = "insert into Pers_BioTemplateData(template_data,CREATE_ID, MODIFY_TIME, SEND_FLAG) values(?,?,?,?)"
            r0.executeSql(r14, r3, r11)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "插入PersBiotemplateData耗时: "
            java.lang.StringBuilder r0 = r0.append(r3)
            long r11 = android.os.SystemClock.elapsedRealtime()
            long r11 = r11 - r9
            java.lang.StringBuilder r0 = r0.append(r11)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r5, r0)
            com.zkteco.android.logfile.LogManager r0 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.String r3 = "insert BioTemplateData"
            r0.OutputLog(r4, r3)
            java.lang.String r0 = " SELECT   * FROM   Pers_BioTemplateData ORDER BY   ID  DESC   LIMIT 1"
            com.zkteco.android.db.orm.manager.DataManager r3 = r1.dataManager     // Catch:{ Exception -> 0x026d, all -> 0x026a }
            android.database.Cursor r11 = r3.queryBySql(r0)     // Catch:{ Exception -> 0x026d, all -> 0x026a }
            r0 = -1
            if (r11 == 0) goto L_0x01b1
            boolean r3 = r11.moveToFirst()     // Catch:{ Exception -> 0x026e }
            if (r3 == 0) goto L_0x01b1
            java.lang.String r3 = "ID"
            int r3 = r11.getColumnIndex(r3)     // Catch:{ Exception -> 0x026e }
            int r3 = r11.getInt(r3)     // Catch:{ Exception -> 0x026e }
            goto L_0x01b2
        L_0x01b1:
            r3 = r0
        L_0x01b2:
            if (r11 == 0) goto L_0x01b7
            r11.close()
        L_0x01b7:
            if (r3 != r0) goto L_0x01d9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Error: updateDBTemplateData tempId exception："
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeExtractLog(r0)
            com.zkteco.android.logfile.LogManager r0 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.String r2 = "Exc: query Pers_BioTemplateData,templateId == -1"
            r0.OutputLog(r4, r2)
            return r7
        L_0x01d9:
            long r9 = android.os.SystemClock.elapsedRealtime()
            r0 = 7
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0242 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x0242 }
            r0[r7] = r11     // Catch:{ Exception -> 0x0242 }
            r11 = 1
            r0[r11] = r2     // Catch:{ Exception -> 0x0242 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x0242 }
            r0[r16] = r2     // Catch:{ Exception -> 0x0242 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)     // Catch:{ Exception -> 0x0242 }
            r0[r15] = r2     // Catch:{ Exception -> 0x0242 }
            r2 = 622(0x26e, float:8.72E-43)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x0242 }
            r6 = 4
            r0[r6] = r2     // Catch:{ Exception -> 0x0242 }
            r0[r13] = r8     // Catch:{ Exception -> 0x0242 }
            r2 = 6
            r0[r2] = r8     // Catch:{ Exception -> 0x0242 }
            com.zkteco.android.db.orm.manager.DataManager r2 = r1.dataManager     // Catch:{ Exception -> 0x0242 }
            java.lang.String r6 = "insert into Pers_BioTemplate(template_id, user_pin, bio_type, major_ver, minor_ver, template_no, SEND_FLAG) values(?,?,?,?,?,?,?)"
            r2.executeSql(r14, r6, r0)     // Catch:{ Exception -> 0x0242 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0242 }
            r0.<init>()     // Catch:{ Exception -> 0x0242 }
            java.lang.String r2 = "插入PersBiotemplate耗时: "
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0242 }
            long r11 = android.os.SystemClock.elapsedRealtime()     // Catch:{ Exception -> 0x0242 }
            long r11 = r11 - r9
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Exception -> 0x0242 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0242 }
            android.util.Log.d(r5, r0)     // Catch:{ Exception -> 0x0242 }
            com.zkteco.android.logfile.LogManager r0 = com.zkteco.android.logfile.LogManager.getInstance()     // Catch:{ Exception -> 0x0242 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0242 }
            r2.<init>()     // Catch:{ Exception -> 0x0242 }
            java.lang.String r5 = "create Pers_BioTemplate-->TemplateID:"
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x0242 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0242 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0242 }
            r0.OutputLog(r4, r2)     // Catch:{ Exception -> 0x0242 }
            goto L_0x0157
        L_0x0241:
            return r0
        L_0x0242:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error: updateDBTemplateData insert temp exception："
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            com.zktechnology.android.push.util.FileLogUtils.writeExtractLog(r0)
            com.zkteco.android.logfile.LogManager r0 = com.zkteco.android.logfile.LogManager.getInstance()
            java.lang.String r2 = "create Pers_BioTemplate-->Exc"
            r0.OutputLog(r4, r2)
            return r7
        L_0x026a:
            r0 = move-exception
            r11 = 0
            goto L_0x027e
        L_0x026d:
            r11 = 0
        L_0x026e:
            com.zkteco.android.logfile.LogManager r0 = com.zkteco.android.logfile.LogManager.getInstance()     // Catch:{ all -> 0x027d }
            java.lang.String r2 = "Exc: query PersBiotemplatedata"
            r0.OutputLog(r4, r2)     // Catch:{ all -> 0x027d }
            if (r11 == 0) goto L_0x027c
            r11.close()
        L_0x027c:
            return r7
        L_0x027d:
            r0 = move-exception
        L_0x027e:
            if (r11 == 0) goto L_0x0283
            r11.close()
        L_0x0283:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.launcher2.ZKExtractLauncher.updateDBTemplateData(java.lang.String, byte[]):boolean");
    }

    private byte[] extractFaceTemplate(String str) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(str));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bitmap = null;
        }
        if (bitmap == null) {
            return null;
        }
        ZkExtractResult[] zkExtractResultArr = new ZkExtractResult[1];
        if (ZkFaceManager.getInstance().extractFeature(bitmap, false, zkExtractResultArr) != 0) {
            return null;
        }
        return zkExtractResultArr[0].feature;
    }

    private boolean hasUserInfo(String str) {
        try {
            List query = new UserInfo().getQueryBuilder().where().eq("User_PIN", str).query();
            if (query == null || query.isEmpty()) {
                return false;
            }
            return true;
        } catch (SQLException unused) {
            return false;
        }
    }

    private String getPath(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = SD_CARD_PATH;
        File file = new File(sb.append(str2).append("/ZKTeco/data/temp/").toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        if (ZKLauncher.zkDataEncdec != 1) {
            return str2 + "/ZKTeco/data/biophoto/face/" + str + ZKFilePath.SUFFIX_IMAGE;
        }
        if (!new File(str2 + "/ZKTeco/data/biophoto/face/" + str + "-temp" + ZKFilePath.SUFFIX_IMAGE).exists()) {
            AES256Util.decryptFile(str2 + "/ZKTeco/data/biophoto/face/" + str + ZKFilePath.SUFFIX_IMAGE, str2 + "/ZKTeco/data/temp/" + str + "-temp" + ZKFilePath.SUFFIX_IMAGE, ZKLauncher.PUBLIC_KEY, ZKLauncher.iv);
        }
        return str2 + "/ZKTeco/data/temp/" + str + "-temp" + ZKFilePath.SUFFIX_IMAGE;
    }

    /* access modifiers changed from: private */
    public boolean startExtTask(String str) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean hasUserInfo = hasUserInfo(str);
        Log.d(TAG, "startExtTask: 查找用户是否存在耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        if (!hasUserInfo) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("USER_INFO is not exist,pin = ");
            stringBuffer.append(str);
            LogManager.getInstance().OutputLog("ZK_L_TASK", stringBuffer.toString());
            FileLogUtils.writeExtractLog("startExtTask user is not exist ： " + str);
            return false;
        }
        LogManager.getInstance().OutputLog("ZK_L_TASK", "pin:" + str);
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        String path = getPath(str);
        Log.d(TAG, "startExtTask: 获取提取路径耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime2) + ", 总耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        if (!new File(path).exists()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("bioPhoto is not exist,path: ");
            stringBuffer2.append(path);
            LogManager.getInstance().OutputLog("ZK_L_TASK", stringBuffer2.toString());
            FileLogUtils.writeExtractLog("startExtTask path not exist ： " + path);
            return false;
        }
        LogManager.getInstance().OutputLog("ZK_L_TASK", "path:" + path);
        long elapsedRealtime3 = SystemClock.elapsedRealtime();
        byte[] extractFaceTemplate = extractFaceTemplate(path);
        Log.d(TAG, "startExtTask: 提取模板耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime3) + ", 总耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        if (extractFaceTemplate == null || extractFaceTemplate.length <= 0) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("extract template failed! pin=");
            stringBuffer3.append(str);
            stringBuffer3.append(", path:");
            stringBuffer3.append(path);
            LogManager.getInstance().OutputLog("ZK_L_TASK", stringBuffer3.toString());
            FileLogUtils.writeExtractLog(stringBuffer3.toString());
            return false;
        }
        LogManager.getInstance().OutputLog("ZK_L_TASK", "template");
        long elapsedRealtime4 = SystemClock.elapsedRealtime();
        boolean updateDBTemplateData = updateDBTemplateData(str, extractFaceTemplate);
        Log.d(TAG, "startExtTask: 更新数据库模板及数据耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime4) + ", 总耗时: " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        LogManager.getInstance().OutputLog("ZK_L_TASK", "updateDB:" + updateDBTemplateData);
        if (updateDBTemplateData) {
            return true;
        }
        LogManager.getInstance().OutputLog("ZK_L_TASK", "update or insert template failed,pin = " + str);
        return false;
    }

    private void updateMem(String str) {
        FaceDBUtils.updateTemplateByUserPin(str);
    }

    private void connectService() {
        IPCServerService.setIPCCallBack(this);
    }

    private void disconnectService() {
        IPCServerService.setIPCCallBack((IPCCallBack) null);
    }

    private static class ExtTempTask implements Runnable {
        private boolean isStartExtTask = true;
        private final LinkedTransferQueue<ExtUserBean> mExtractCacheQueue = new LinkedTransferQueue<>();
        private final WeakReference<ExtractTaskInterface> mExtractTaskInterface;
        private final WeakReference<ZKExtractLauncher> obj;

        public interface ExtractTaskInterface {
            void extFailed(ExtUserBean extUserBean, ZKExtractLauncher zKExtractLauncher);

            void extSuccess(ExtUserBean extUserBean, ZKExtractLauncher zKExtractLauncher);

            boolean isNeedExtract(String str, ZKExtractLauncher zKExtractLauncher);

            void onInit(ZKExtractLauncher zKExtractLauncher);

            boolean startTask(String str, ZKExtractLauncher zKExtractLauncher);
        }

        ExtTempTask(ExtractTaskInterface extractTaskInterface, ZKExtractLauncher zKExtractLauncher) {
            this.mExtractTaskInterface = new WeakReference<>(extractTaskInterface);
            this.obj = new WeakReference<>(zKExtractLauncher);
        }

        public void run() {
            ExtractTaskInterface extractTaskInterface = (ExtractTaskInterface) this.mExtractTaskInterface.get();
            if (extractTaskInterface != null) {
                Process.setThreadPriority(-1);
                extractTaskInterface.onInit((ZKExtractLauncher) this.obj.get());
                while (this.isStartExtTask) {
                    try {
                        if (ZKExtractLauncher.LOCK.isLocked()) {
                            Log.d(ZKExtractLauncher.TAG, "isLocked");
                            Thread.sleep(200);
                        } else {
                            ExtUserBean take = this.mExtractCacheQueue.take();
                            if (this.mExtractTaskInterface != null) {
                                if (!TextUtils.isEmpty(take.getUserPin())) {
                                    if (extractTaskInterface.isNeedExtract(take.getUserPin(), (ZKExtractLauncher) this.obj.get())) {
                                        int i = 0;
                                        while (true) {
                                            if (i >= 3) {
                                                break;
                                            } else if (extractTaskInterface.startTask(take.getUserPin(), (ZKExtractLauncher) this.obj.get())) {
                                                take.setResult(1);
                                                extractTaskInterface.extSuccess(take, (ZKExtractLauncher) this.obj.get());
                                                FileLogUtils.writeExtractLog("ExtTempTask Success " + take.getUserPin());
                                                break;
                                            } else {
                                                int i2 = i + 1;
                                                FileLogUtils.writeExtractLog("Error: ExtTempTask count" + i2 + "    fileName： " + take.getUserPin());
                                                if (i == 2) {
                                                    take.setResult(0);
                                                    extractTaskInterface.extFailed(take, (ZKExtractLauncher) this.obj.get());
                                                } else {
                                                    Thread.sleep(300);
                                                }
                                                i = i2;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FileLogUtils.writeExtractLog("Error: ExtTempTask exception  " + e.getMessage());
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void putExtTask(ExtUserBean extUserBean) {
            this.mExtractCacheQueue.offer(extUserBean);
        }

        /* access modifiers changed from: package-private */
        public void stopExtTempTask() {
            this.isStartExtTask = false;
            this.mExtractTaskInterface.clear();
            putExtTask(new ExtUserBean());
        }
    }

    private static class ZKFileObserver extends FileObserver {
        private WeakReference<OnFileChangeListener> onFileChangeListener;

        public interface OnFileChangeListener {
            void onEvent(int i, String str);
        }

        ZKFileObserver(String str) {
            super(str, 8);
        }

        public void onEvent(int i, String str) {
            int i2 = i & 4095;
            WeakReference<OnFileChangeListener> weakReference = this.onFileChangeListener;
            if (weakReference != null && weakReference.get() != null) {
                ((OnFileChangeListener) this.onFileChangeListener.get()).onEvent(i2, str);
            }
        }

        public void stopWatching() {
            super.stopWatching();
            this.onFileChangeListener = null;
        }

        public void start(OnFileChangeListener onFileChangeListener2) {
            this.onFileChangeListener = new WeakReference<>(onFileChangeListener2);
            startWatching();
        }
    }
}
