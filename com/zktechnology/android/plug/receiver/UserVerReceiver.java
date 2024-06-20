package com.zktechnology.android.plug.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.DateTimeUtils;
import com.zkteco.android.db.orm.manager.template.TemplateManager;
import com.zkteco.android.db.orm.tna.AttLog;
import com.zkteco.android.db.orm.tna.UserInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserVerReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        action.hashCode();
        if (action.equals("android.intent.action.DATE_CHANGED") && userValidateDate(context) && validTimeFun(context)) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ArrayList arrayList = new ArrayList();
                    List<UserInfo> access$000 = UserVerReceiver.this.getUserData();
                    int intOption = DBManager.getInstance().getIntOption("DateFmtFunOn", 0);
                    for (UserInfo userInfo : access$000) {
                        if (!UserVerReceiver.this.expires(userInfo, intOption)) {
                            arrayList.add(userInfo);
                        }
                    }
                    UserVerReceiver.this.handleUserInfo(UserVerReceiver.this.overValidTime(context), arrayList, context);
                }
            });
            thread.setName("user_ver_receiver");
            thread.run();
        }
    }

    /* access modifiers changed from: private */
    public void handleUserInfo(int i, List<UserInfo> list, Context context) {
        for (UserInfo next : list) {
            if (i == 0) {
                AttLog attLog = new AttLog();
                try {
                    new ArrayList();
                    List<AttLog> query = attLog.getQueryBuilder().where().eq("User_PIN", next.getUser_PIN()).query();
                    if (query != null && query.size() > 0) {
                        for (AttLog delete : query) {
                            delete.delete();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (i == 2) {
                try {
                    TemplateManager templateManager = new TemplateManager(context);
                    next.delete();
                    templateManager.deleteFingerTemplateAndBiokey10ForUserPin(next.getUser_PIN());
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean userValidateDate(Context context) {
        String strOption = DBManager.getInstance().getStrOption("SupportUserValidateDate", "0");
        if (strOption == null || strOption.equals("0") || !strOption.equals("1")) {
            return false;
        }
        return true;
    }

    private boolean validTimeFun(Context context) {
        String strOption = DBManager.getInstance().getStrOption("UserValidTimeFun", "0");
        if (!strOption.equals("0") && strOption.equals("1")) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public int overValidTime(Context context) {
        return DBManager.getInstance().getIntOption("OverValidTime", 0);
    }

    /* access modifiers changed from: private */
    public List<UserInfo> getUserData() {
        ArrayList arrayList = new ArrayList();
        try {
            return new UserInfo().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    /* access modifiers changed from: private */
    public boolean expires(UserInfo userInfo, int i) {
        int expires = userInfo.getExpires();
        boolean z = true;
        if (expires == 0) {
            return true;
        }
        if (expires == 1) {
            return DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), i);
        }
        if (expires != 2) {
            if (expires != 3) {
                return false;
            }
            boolean isInExpires = DateTimeUtils.isInExpires(userInfo.getStartDatetime(), userInfo.getEndDatetime(), i);
            if (!isInExpires) {
                return isInExpires;
            }
            if (userInfo.getVaildCount() <= 0) {
                z = false;
            }
            return z;
        } else if (userInfo.getVaildCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
