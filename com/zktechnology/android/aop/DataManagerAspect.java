package com.zktechnology.android.aop;

import com.zktechnology.android.aidl.ZkAidlDataManager;
import com.zktechnology.android.launcher2.LauncherApplication;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DataManagerAspect {
    private static /* synthetic */ Throwable ajc$initFailureCause;
    public static final /* synthetic */ DataManagerAspect ajc$perSingletonInstance = null;
    private long mHookMethodStartTime;
    private long mHookMethodStartTime1;
    private long mHookMethodStartTime2;
    private long mHookMethodStartTime3;
    private long mHookMethodStartTime4;

    static {
        try {
            ajc$perSingletonInstance = new DataManagerAspect();
        } catch (Throwable th) {
            ajc$initFailureCause = th;
        }
    }

    public static DataManagerAspect aspectOf() {
        DataManagerAspect dataManagerAspect = ajc$perSingletonInstance;
        if (dataManagerAspect != null) {
            return dataManagerAspect;
        }
        throw new NoAspectBoundException("com.zktechnology.android.aop.DataManagerAspect", ajc$initFailureCause);
    }

    public static boolean hasAspect() {
        return ajc$perSingletonInstance != null;
    }

    @After("executeGetIntOption()")
    public void afterGetIntOption(JoinPoint joinPoint) {
    }

    @After("executeGetStrOption()")
    public void afterGetStrOption(JoinPoint joinPoint) {
    }

    @After("callOpen()")
    public void afterOpen(JoinPoint joinPoint) {
    }

    @After("executeSetIntOption()")
    public void afterSetIntOption(JoinPoint joinPoint) {
    }

    @After("executeSetStrOption()")
    public void afterSetStrOption(JoinPoint joinPoint) {
    }

    @Before("executeGetIntOption()")
    public void beforeGetIntOption(JoinPoint joinPoint) {
    }

    @Before("executeGetStrOption()")
    public void beforeGetStrOption(JoinPoint joinPoint) {
    }

    @Before("executeSetIntOption()")
    public void beforeSetIntOption(JoinPoint joinPoint) {
    }

    @Before("executeSetStrOption()")
    public void beforeSetStrOption(JoinPoint joinPoint) {
    }

    @Pointcut("call(* com.zkteco.android.db.orm.manager.DataManager.open(..))")
    public void callOpen() {
    }

    @Pointcut("execution(* com.zkteco.android.db.orm.manager.DataManager.getIntOption(..))")
    public void executeGetIntOption() {
    }

    @Pointcut("execution(* com.zkteco.android.db.orm.manager.DataManager.getStrOption(..))")
    public void executeGetStrOption() {
    }

    @Pointcut("execution(* com.zkteco.android.db.orm.manager.DataManager.setIntOption(..))")
    public void executeSetIntOption() {
    }

    @Pointcut("execution(* com.zkteco.android.db.orm.manager.DataManager.setStrOption(..))")
    public void executeSetStrOption() {
    }

    @Before("callOpen()")
    public void beforeOpen(JoinPoint joinPoint) {
        ZkAidlDataManager.getInstance().bindService(LauncherApplication.getLauncherApplicationContext());
    }

    @Around("executeGetIntOption()")
    public Object getIntOption(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            if (args == null || args.length != 2 || !(args[0] instanceof String)) {
                return proceedingJoinPoint.proceed();
            }
            int intValue = ((Integer) args[1]).intValue();
            return Integer.valueOf(ZkAidlDataManager.getInstance().getIntOption((String) args[0], intValue));
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }

    @Around("executeGetStrOption()")
    public Object getStrOption(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            if (args == null || args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)) {
                return proceedingJoinPoint.proceed();
            }
            return ZkAidlDataManager.getInstance().getStrOption((String) args[0], (String) args[1]);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    @Around("executeSetIntOption()")
    public Object setIntOption(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof Integer)) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable th) {
                th.printStackTrace();
                return 0;
            }
        } else {
            int intValue = ((Integer) args[1]).intValue();
            return Integer.valueOf(ZkAidlDataManager.getInstance().setIntOption((String) args[0], intValue));
        }
    }

    @Around("executeSetStrOption()")
    public Object setStrOption(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        if (args == null || args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)) {
            try {
                return proceedingJoinPoint.proceed();
            } catch (Throwable th) {
                th.printStackTrace();
                return 0;
            }
        } else {
            return Integer.valueOf(ZkAidlDataManager.getInstance().setStrOption((String) args[0], (String) args[1]));
        }
    }
}
