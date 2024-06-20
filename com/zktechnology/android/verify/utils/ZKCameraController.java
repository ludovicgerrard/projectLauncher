package com.zktechnology.android.verify.utils;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import com.zktechnology.android.device.camera.CameraPictureInfo;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zktechnology.android.launcher2.ZkFaceLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zkteco.android.core.sdk.HubProtocolManager;
import com.zkteco.android.db.orm.tna.PhotoIndex;
import com.zkteco.android.zkcore.utils.ZKFilePath;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ZKCameraController {
    public static final String TAG = "ZKCameraController";
    private static volatile ZKCameraController instance;
    private MediaPlayer mediaPlayer;

    public static ZKCameraController getInstance() {
        if (instance == null) {
            synchronized (ZKCameraController.class) {
                if (instance == null) {
                    instance = new ZKCameraController();
                }
            }
        }
        return instance;
    }

    private ZKCameraController() {
    }

    public void takePicture(PhotoIndex photoIndex, String str, String str2) {
        LauncherApplication.getLauncherApplicationContext().sendBroadcast(new Intent(ZkFaceLauncher.ACTION_TAKE_PICTURE).putExtra(ZkFaceLauncher.TAKE_PICTURE_INFO, new CameraPictureInfo(str + str2 + ZKFilePath.SUFFIX_IMAGE, photoIndex)));
    }

    public void shootSound() {
        try {
            AssetFileDescriptor openFd = LauncherApplication.getLauncherApplicationContext().getAssets().openFd("sounds/capture.wav");
            MediaPlayer mediaPlayer2 = this.mediaPlayer;
            if (mediaPlayer2 == null) {
                this.mediaPlayer = new MediaPlayer();
            } else {
                mediaPlayer2.stop();
                this.mediaPlayer.reset();
            }
            this.mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSavePictureFinish(PhotoIndex photoIndex, String str) {
        if (photoIndex != null) {
            HubProtocolManager hubProtocolManager = new HubProtocolManager(LauncherApplication.getLauncherApplicationContext());
            File file = new File(str);
            if (file.exists() && file.isFile() && file.length() > 0) {
                try {
                    photoIndex.create();
                    sendHub(hubProtocolManager, photoIndex);
                } catch (SQLException e) {
                    e.printStackTrace();
                    FileLogUtils.writeVerifyExceptionLog("save photoIndex exception: " + e.toString());
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendHub(com.zkteco.android.core.sdk.HubProtocolManager r7, com.zkteco.android.db.orm.tna.PhotoIndex r8) {
        /*
            r6 = this;
            r0 = 0
            long r2 = r7.convertPushInit()     // Catch:{ Exception -> 0x0041, all -> 0x003e }
            java.lang.String r4 = "PHOTO_INDEX"
            r7.setPushTableName(r2, r4)     // Catch:{ Exception -> 0x003c }
            java.lang.String r4 = r8.getUser_PIN()     // Catch:{ Exception -> 0x003c }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x003c }
            if (r4 != 0) goto L_0x001e
            java.lang.String r4 = "User_PIN"
            java.lang.String r5 = r8.getUser_PIN()     // Catch:{ Exception -> 0x003c }
            r7.setPushStrField(r2, r4, r5)     // Catch:{ Exception -> 0x003c }
        L_0x001e:
            java.lang.String r4 = "Photo_Time"
            java.lang.String r5 = r8.getPhoto_Time()     // Catch:{ Exception -> 0x003c }
            r7.setPushStrField(r2, r4, r5)     // Catch:{ Exception -> 0x003c }
            java.lang.String r4 = "Photo_Type"
            int r8 = r8.getPhoto_Type()     // Catch:{ Exception -> 0x003c }
            r7.setPushIntField(r2, r4, r8)     // Catch:{ Exception -> 0x003c }
            r8 = 0
            r4 = 0
            r7.sendHubAction(r8, r2, r4)     // Catch:{ Exception -> 0x003c }
            int r8 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x004d
            goto L_0x004a
        L_0x003a:
            r8 = move-exception
            goto L_0x004e
        L_0x003c:
            r8 = move-exception
            goto L_0x0043
        L_0x003e:
            r8 = move-exception
            r2 = r0
            goto L_0x004e
        L_0x0041:
            r8 = move-exception
            r2 = r0
        L_0x0043:
            r8.printStackTrace()     // Catch:{ all -> 0x003a }
            int r8 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x004d
        L_0x004a:
            r7.convertPushFree(r2)
        L_0x004d:
            return
        L_0x004e:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0055
            r7.convertPushFree(r2)
        L_0x0055:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.verify.utils.ZKCameraController.sendHub(com.zkteco.android.core.sdk.HubProtocolManager, com.zkteco.android.db.orm.tna.PhotoIndex):void");
    }
}
