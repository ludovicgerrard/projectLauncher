package com.zktechnology.android.utils;

import android.content.Context;
import com.zktechnology.android.aop.DataManagerAspect;
import com.zktechnology.android.launcher2.LauncherApplication;
import com.zkteco.android.db.orm.manager.DataManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.reflect.Factory;

public class DBManager {
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_0 = null;
    private static volatile DataManager dataManager;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("DBManager.java", DBManager.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig("1", "open", "com.zkteco.android.db.orm.manager.DataManager", "android.content.Context", "context", "", "void"), 18);
    }

    /* JADX INFO: finally extract failed */
    public static DataManager getInstance() {
        if (dataManager == null) {
            synchronized (DBManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager();
                    DataManager dataManager2 = dataManager;
                    Context launcherApplicationContext = LauncherApplication.getLauncherApplicationContext();
                    JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, (Object) null, (Object) dataManager2, (Object) launcherApplicationContext);
                    try {
                        DataManagerAspect.aspectOf().beforeOpen(makeJP);
                        dataManager2.open(launcherApplicationContext);
                        DataManagerAspect.aspectOf().afterOpen(makeJP);
                    } catch (Throwable th) {
                        DataManagerAspect.aspectOf().afterOpen(makeJP);
                        throw th;
                    }
                }
            }
        }
        return dataManager;
    }
}
