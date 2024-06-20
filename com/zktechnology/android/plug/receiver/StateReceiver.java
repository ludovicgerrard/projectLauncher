package com.zktechnology.android.plug.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.zktechnology.android.utils.AttStateListener;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.tna.ShortState;
import com.zkteco.android.db.orm.tna.StateTimeZone;
import com.zkteco.android.db.orm.tna.TimeZone;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class StateReceiver extends BroadcastReceiver {
    private static final String ACTION_FEX_CHANGED = "com.zktechnology.android.state.fix.changed";
    private static final String ACTION_STATE_CHANGED = "com.zktechnology.android.state.changed";
    public AttStateListener attStateListener;
    private String state;
    private int timeX;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(final android.content.Context r7, android.content.Intent r8) {
        /*
            r6 = this;
            java.lang.String r0 = r8.getAction()     // Catch:{ Exception -> 0x0084 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0084 }
            if (r1 == 0) goto L_0x000b
            return
        L_0x000b:
            int r1 = r0.hashCode()     // Catch:{ Exception -> 0x0084 }
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = -1
            switch(r1) {
                case -1513032534: goto L_0x0035;
                case 505380757: goto L_0x002b;
                case 1351889819: goto L_0x0021;
                case 1516133492: goto L_0x0017;
                default: goto L_0x0016;
            }     // Catch:{ Exception -> 0x0084 }
        L_0x0016:
            goto L_0x003f
        L_0x0017:
            java.lang.String r1 = "com.zktechnology.android.state.changed"
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x0084 }
            if (r0 == 0) goto L_0x003f
            r0 = 0
            goto L_0x0040
        L_0x0021:
            java.lang.String r1 = "com.zktechnology.android.state.fix.changed"
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x0084 }
            if (r0 == 0) goto L_0x003f
            r0 = r4
            goto L_0x0040
        L_0x002b:
            java.lang.String r1 = "android.intent.action.TIME_SET"
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x0084 }
            if (r0 == 0) goto L_0x003f
            r0 = r2
            goto L_0x0040
        L_0x0035:
            java.lang.String r1 = "android.intent.action.TIME_TICK"
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x0084 }
            if (r0 == 0) goto L_0x003f
            r0 = r3
            goto L_0x0040
        L_0x003f:
            r0 = r5
        L_0x0040:
            java.lang.String r1 = "stateMode"
            if (r0 == 0) goto L_0x0073
            if (r0 == r4) goto L_0x0058
            if (r0 == r3) goto L_0x004b
            if (r0 == r2) goto L_0x004b
            goto L_0x0088
        L_0x004b:
            com.zktechnology.android.utils.ZKThreadPool r8 = com.zktechnology.android.utils.ZKThreadPool.getInstance()     // Catch:{ Exception -> 0x0084 }
            com.zktechnology.android.plug.receiver.StateReceiver$3 r0 = new com.zktechnology.android.plug.receiver.StateReceiver$3     // Catch:{ Exception -> 0x0084 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x0084 }
            r8.executeTask(r0)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0088
        L_0x0058:
            java.lang.String r0 = r8.getStringExtra(r1)     // Catch:{ Exception -> 0x0084 }
            java.lang.String r1 = "stateNo"
            int r8 = r8.getIntExtra(r1, r5)     // Catch:{ Exception -> 0x0084 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x0084 }
            com.zktechnology.android.utils.ZKThreadPool r1 = com.zktechnology.android.utils.ZKThreadPool.getInstance()     // Catch:{ Exception -> 0x0084 }
            com.zktechnology.android.plug.receiver.StateReceiver$2 r2 = new com.zktechnology.android.plug.receiver.StateReceiver$2     // Catch:{ Exception -> 0x0084 }
            r2.<init>(r7, r0, r8)     // Catch:{ Exception -> 0x0084 }
            r1.executeTask(r2)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0088
        L_0x0073:
            java.lang.String r8 = r8.getStringExtra(r1)     // Catch:{ Exception -> 0x0084 }
            com.zktechnology.android.utils.ZKThreadPool r0 = com.zktechnology.android.utils.ZKThreadPool.getInstance()     // Catch:{ Exception -> 0x0084 }
            com.zktechnology.android.plug.receiver.StateReceiver$1 r1 = new com.zktechnology.android.plug.receiver.StateReceiver$1     // Catch:{ Exception -> 0x0084 }
            r1.<init>(r7, r8)     // Catch:{ Exception -> 0x0084 }
            r0.executeTask(r1)     // Catch:{ Exception -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r7 = move-exception
            r7.printStackTrace()
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.plug.receiver.StateReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public void setAttStateListener(AttStateListener attStateListener2) {
        this.attStateListener = attStateListener2;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003c, code lost:
        if (r3.equals("0") == false) goto L_0x001e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stateChangeTick(android.content.Context r7) {
        /*
            r6 = this;
            java.lang.String r0 = "STATE_MODEL_SP"
            r1 = 0
            android.content.SharedPreferences r0 = r7.getSharedPreferences(r0, r1)
            android.content.SharedPreferences$Editor r2 = r0.edit()
            java.lang.String r3 = "STATE_MODEL_TAG"
            java.lang.String r4 = ""
            java.lang.String r3 = r0.getString(r3, r4)
            r3.hashCode()
            int r4 = r3.hashCode()
            r5 = -1
            switch(r4) {
                case 48: goto L_0x0036;
                case 50: goto L_0x002b;
                case 53: goto L_0x0020;
                default: goto L_0x001e;
            }
        L_0x001e:
            r1 = r5
            goto L_0x003f
        L_0x0020:
            java.lang.String r1 = "5"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L_0x0029
            goto L_0x001e
        L_0x0029:
            r1 = 2
            goto L_0x003f
        L_0x002b:
            java.lang.String r1 = "2"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L_0x0034
            goto L_0x001e
        L_0x0034:
            r1 = 1
            goto L_0x003f
        L_0x0036:
            java.lang.String r4 = "0"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L_0x003f
            goto L_0x001e
        L_0x003f:
            java.lang.String r4 = "ACTION_PARAM"
            switch(r1) {
                case 0: goto L_0x0077;
                case 1: goto L_0x005f;
                case 2: goto L_0x0045;
                default: goto L_0x0044;
            }
        L_0x0044:
            goto L_0x0083
        L_0x0045:
            java.lang.String r7 = "STATE_NO"
            int r7 = r0.getInt(r7, r5)
            java.lang.String r0 = java.lang.String.valueOf(r7)
            r2.putString(r4, r0)
            r2.apply()
            com.zktechnology.android.utils.AttStateListener r0 = r6.attStateListener
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r0.attStateChange(r3, r7)
            goto L_0x0083
        L_0x005f:
            int r7 = r6.transition(r7)
            java.lang.String r0 = java.lang.String.valueOf(r7)
            r2.putString(r4, r0)
            r2.apply()
            com.zktechnology.android.utils.AttStateListener r0 = r6.attStateListener
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r0.attStateChange(r3, r7)
            goto L_0x0083
        L_0x0077:
            r2.clear()
            r2.apply()
            com.zktechnology.android.utils.AttStateListener r7 = r6.attStateListener
            r0 = 0
            r7.attStateChange(r3, r0)
        L_0x0083:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.plug.receiver.StateReceiver.stateChangeTick(android.content.Context):void");
    }

    private int transition(Context context) {
        String str = "state0";
        DBManager.getInstance();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        StateTimeZone stateTimeZone = new StateTimeZone();
        ShortState shortState = new ShortState();
        TimeZone timeZone = new TimeZone();
        try {
            List queryForAll = stateTimeZone.queryForAll();
            if (queryForAll != null && queryForAll.size() > 0) {
                TreeMap treeMap = new TreeMap();
                for (int i = 0; i < queryForAll.size(); i++) {
                    StateTimeZone stateTimeZone2 = (StateTimeZone) queryForAll.get(i);
                    String state_Name = stateTimeZone2.getState_Name();
                    List query = shortState.getQueryBuilder().where().eq("State_Name", state_Name).query();
                    if (query != null && query.size() > 0) {
                        ShortState shortState2 = (ShortState) query.get(0);
                        boolean z = shortState2.getAuto_Change() == 2;
                        List query2 = timeZone.getQueryBuilder().where().eq("Timezone_Name", stateTimeZone2.getTimezone_Name()).query();
                        if (query2 != null && query2.size() > 0) {
                            TimeZone timeZone2 = (TimeZone) query2.get(0);
                            if (!z) {
                                switch (getCurrentWeek()) {
                                    case 1:
                                        if (shortState2.getSun() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getSun_Time()));
                                            break;
                                        }
                                    case 2:
                                        if (shortState2.getMon() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getMon_Time()));
                                            break;
                                        }
                                    case 3:
                                        if (shortState2.getTue() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getTue_Time()));
                                            break;
                                        }
                                    case 4:
                                        if (shortState2.getWed() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getWed_Time()));
                                            break;
                                        }
                                    case 5:
                                        if (shortState2.getThu() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getThu_Time()));
                                            break;
                                        }
                                    case 6:
                                        if (shortState2.getFri() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getFri_Time()));
                                            break;
                                        }
                                    case 7:
                                        if (shortState2.getSat() != 2) {
                                            break;
                                        } else {
                                            treeMap.put(state_Name, Integer.valueOf(timeZone2.getSat_Time()));
                                            break;
                                        }
                                }
                            } else {
                                treeMap.put(state_Name, Integer.valueOf(timeZone2.getSun_Time()));
                            }
                        }
                    }
                }
                ArrayList<Map.Entry> arrayList = new ArrayList<>(treeMap.entrySet());
                Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> entry, Map.Entry<String, Integer> entry2) {
                        return entry.getValue().compareTo(entry2.getValue());
                    }
                });
                for (Map.Entry entry : arrayList) {
                    Log.e("TAG", "transition: entry: " + ((String) entry.getKey()) + "--->" + entry.getValue());
                }
                this.state = null;
                this.timeX = 8;
                getState(getCurrentWeek());
                String str2 = this.state;
                if (str2 != null) {
                    str = str2;
                }
                int currentTime = getCurrentTime();
                if (currentTime >= 2400) {
                    currentTime -= 2400;
                }
                for (Map.Entry entry2 : arrayList) {
                    int intValue = ((Integer) entry2.getValue()).intValue();
                    if (intValue >= 2400) {
                        intValue -= 2400;
                    }
                    if (currentTime >= intValue) {
                        str = (String) entry2.getKey();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str == null || str.length() <= 0) {
            return 255;
        }
        return Integer.valueOf(str.substring(5)).intValue();
    }

    private void getState(int i) {
        int i2 = this.timeX - 1;
        this.timeX = i2;
        if (i2 > 0) {
            if (i == 1) {
                i = 8;
            }
            TreeMap treeMap = new TreeMap();
            try {
                List<StateTimeZone> queryForAll = new StateTimeZone().queryForAll();
                if (queryForAll != null && queryForAll.size() > 0) {
                    for (StateTimeZone stateTimeZone : queryForAll) {
                        ShortState shortState = (ShortState) new ShortState().getQueryBuilder().where().eq("State_Name", stateTimeZone.getState_Name()).queryForFirst();
                        TimeZone timeZone = (TimeZone) new TimeZone().getQueryBuilder().where().eq("Timezone_Name", stateTimeZone.getTimezone_Name()).queryForFirst();
                        if (shortState.getAuto_Change() != 2) {
                            switch (i - 1) {
                                case 1:
                                    if (shortState.getSun() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getSun_Time()));
                                        break;
                                    }
                                case 2:
                                    if (shortState.getMon() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getMon_Time()));
                                        break;
                                    }
                                case 3:
                                    if (shortState.getTue() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getTue_Time()));
                                        break;
                                    }
                                case 4:
                                    if (shortState.getWed() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getWed_Time()));
                                        break;
                                    }
                                case 5:
                                    if (shortState.getThu() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getThu_Time()));
                                        break;
                                    }
                                case 6:
                                    if (shortState.getFri() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getFri_Time()));
                                        break;
                                    }
                                case 7:
                                    if (shortState.getSat() != 2) {
                                        break;
                                    } else {
                                        treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getSat_Time()));
                                        break;
                                    }
                            }
                        } else {
                            treeMap.put(shortState.getState_Name(), Integer.valueOf(timeZone.getSun_Time()));
                        }
                    }
                }
                if (treeMap.size() > 0) {
                    ArrayList<Map.Entry> arrayList = new ArrayList<>(treeMap.entrySet());
                    Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {
                        public int compare(Map.Entry<String, Integer> entry, Map.Entry<String, Integer> entry2) {
                            return entry.getValue().compareTo(entry2.getValue());
                        }
                    });
                    for (Map.Entry entry : arrayList) {
                        Log.e("TAG", "getState: " + ((String) entry.getKey()) + "--->" + entry.getValue());
                    }
                    this.state = (String) ((Map.Entry) arrayList.get(arrayList.size() - 1)).getKey();
                    return;
                }
                getState(i - 1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getCurrentWeek() {
        return Calendar.getInstance().get(7);
    }

    private int getCurrentTime() {
        String str;
        Calendar instance = Calendar.getInstance();
        int i = instance.get(11);
        int i2 = instance.get(12);
        if (i == 0) {
            i = 24;
        }
        if (i2 < 10) {
            str = String.valueOf(i) + "0" + String.valueOf(i2);
        } else {
            str = String.valueOf(i) + String.valueOf(i2);
        }
        return Integer.valueOf(str).intValue();
    }

    private String getTimeByInt(int i) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format((double) (((float) i) / 100.0f)).replace(".", "：");
    }

    /* access modifiers changed from: private */
    public void clearShare(Context context, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor edit = context.getSharedPreferences("STATE_MODEL_SP", 0).edit();
        edit.clear();
        edit.commit();
    }
}
