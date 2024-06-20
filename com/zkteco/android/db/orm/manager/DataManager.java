package com.zkteco.android.db.orm.manager;

import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import com.zktechnology.android.aop.DataManagerAspect;
import com.zkteco.android.core.interfaces.DatabaseInterface;
import com.zkteco.android.core.sdk.AbstractDataManager;
import com.zkteco.android.db.orm.AbstractORM;
import com.zkteco.util.YAMLHelper;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.AroundClosure;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;

public class DataManager extends AbstractDataManager {
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ JoinPoint.StaticPart ajc$tjp_3 = null;

    public class AjcClosure1 extends AroundClosure {
        public AjcClosure1(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.intObject(((DataManager) objArr2[0]).provider.getIntOption((String) objArr2[1], Conversions.intValue(objArr2[2])));
        }
    }

    public class AjcClosure3 extends AroundClosure {
        public AjcClosure3(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return DataManager.getStrOption_aroundBody2((DataManager) objArr2[0], (String) objArr2[1], (String) objArr2[2], (JoinPoint) objArr2[3]);
        }
    }

    public class AjcClosure5 extends AroundClosure {
        public AjcClosure5(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.intObject(((DataManager) objArr2[0]).provider.setIntOption((String) objArr2[1], Conversions.intValue(objArr2[2])));
        }
    }

    public class AjcClosure7 extends AroundClosure {
        public AjcClosure7(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.intObject(((DataManager) objArr2[0]).provider.setStrOption((String) objArr2[1], (String) objArr2[2]));
        }
    }

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("DataManager.java", DataManager.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "getIntOption", "com.zkteco.android.db.orm.manager.DataManager", "java.lang.String:int", "optionName:defaultValue", "", "int"), 95);
        ajc$tjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "getStrOption", "com.zkteco.android.db.orm.manager.DataManager", "java.lang.String:java.lang.String", "optionName:defaultValue", "", "java.lang.String"), 100);
        ajc$tjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "setIntOption", "com.zkteco.android.db.orm.manager.DataManager", "java.lang.String:int", "optionName:value", "", "int"), 110);
        ajc$tjp_3 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig("1", "setStrOption", "com.zkteco.android.db.orm.manager.DataManager", "java.lang.String:java.lang.String", "optionName:value", "", "int"), 115);
    }

    public String getDatabaseId() {
        return "ZKDB.db";
    }

    /* access modifiers changed from: protected */
    public void onTableModified(String str) {
    }

    public int getDatabaseVersion() {
        return getDBVersion();
    }

    public List<AbstractORM> getTables() {
        try {
            return (List) YAMLHelper.getInstanceFromFile(Environment.getExternalStorageDirectory().getPath() + "/config/orm/zkdb_createtable.yml");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void open(String str, Integer num) {
        this.provider.open(str, num);
    }

    public Cursor executeQuery(String str, DatabaseInterface.SQLCommand sQLCommand) {
        return this.provider.executeQuery(str, sQLCommand);
    }

    public void executeUpdate(String str, DatabaseInterface.SQLCommand sQLCommand) {
        this.provider.executeUpdate(str, sQLCommand);
    }

    public Number executeInsert(String str, DatabaseInterface.SQLCommand sQLCommand) {
        return this.provider.executeInsert(str, sQLCommand);
    }

    public boolean executeTransaction(String str, List<DatabaseInterface.SQLCommand> list) {
        return this.provider.executeTransaction(str, list);
    }

    public void executeSql(String str, String str2, Object[] objArr) {
        this.provider.executeSql(str, str2, objArr);
    }

    public void systemInitOptionTable() {
        this.provider.systemInitOptionTable();
    }

    public void systemInit() {
        this.provider.systemInit();
    }

    public int systemFree() {
        return this.provider.systemFree();
    }

    public int getIntOption(String str, int i) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, this, this, str, Conversions.intObject(i));
        try {
            DataManagerAspect.aspectOf().beforeGetIntOption(makeJP);
            return Conversions.intValue(DataManagerAspect.aspectOf().getIntOption(new AjcClosure1(new Object[]{this, str, Conversions.intObject(i), makeJP}).linkClosureAndJoinPoint(69648)));
        } finally {
            DataManagerAspect.aspectOf().afterGetIntOption(makeJP);
        }
    }

    static final /* synthetic */ String getStrOption_aroundBody2(DataManager dataManager, String str, String str2, JoinPoint joinPoint) {
        String strOption = dataManager.provider.getStrOption(str, str2);
        return TextUtils.isEmpty(strOption) ? str2 : strOption;
    }

    public String getStrOption(String str, String str2) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_1, this, this, str, str2);
        try {
            DataManagerAspect.aspectOf().beforeGetStrOption(makeJP);
            return (String) DataManagerAspect.aspectOf().getStrOption(new AjcClosure3(new Object[]{this, str, str2, makeJP}).linkClosureAndJoinPoint(69648));
        } finally {
            DataManagerAspect.aspectOf().afterGetStrOption(makeJP);
        }
    }

    public int setIntOption(String str, int i) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_2, this, this, str, Conversions.intObject(i));
        try {
            DataManagerAspect.aspectOf().beforeSetIntOption(makeJP);
            return Conversions.intValue(DataManagerAspect.aspectOf().setIntOption(new AjcClosure5(new Object[]{this, str, Conversions.intObject(i), makeJP}).linkClosureAndJoinPoint(69648)));
        } finally {
            DataManagerAspect.aspectOf().afterSetIntOption(makeJP);
        }
    }

    public int setStrOption(String str, String str2) {
        JoinPoint makeJP = Factory.makeJP(ajc$tjp_3, this, this, str, str2);
        try {
            DataManagerAspect.aspectOf().beforeSetStrOption(makeJP);
            return Conversions.intValue(DataManagerAspect.aspectOf().setStrOption(new AjcClosure7(new Object[]{this, str, str2, makeJP}).linkClosureAndJoinPoint(69648)));
        } finally {
            DataManagerAspect.aspectOf().afterSetStrOption(makeJP);
        }
    }

    public int getDBVersion() {
        return this.provider.getDBVersion();
    }

    public Cursor queryBySql(String str) {
        if (str == null) {
            return null;
        }
        DatabaseInterface.SQLCommand sQLCommand = new DatabaseInterface.SQLCommand();
        sQLCommand.setSql(str);
        return this.provider.executeQuery(getDatabaseId(), sQLCommand);
    }
}
