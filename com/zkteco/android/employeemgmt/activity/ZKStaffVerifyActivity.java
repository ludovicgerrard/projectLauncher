package com.zkteco.android.employeemgmt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.ZKStaffBaseActivity;
import com.zkteco.android.employeemgmt.adapter.ZKStaffVertificationAdapter;
import com.zkteco.android.employeemgmt.model.ZKStaffVerifyBean;
import com.zkteco.android.zkcore.view.ZKToolbar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZKStaffVerifyActivity extends ZKStaffBaseActivity implements View.OnClickListener {
    private static final int NULl_SELECT = 15;
    /* access modifiers changed from: private */
    public ZKStaffVertificationAdapter adapter;
    private HashMap<Integer, Boolean> bolHashmap = new HashMap<>();
    private List<Button> btnList = new ArrayList();
    private final DataManager dataManager = DBManager.getInstance();
    private HashMap<List<Boolean>, List<Integer>> hashMap = new HashMap<>();
    /* access modifiers changed from: private */
    public int isKeySameBefore;
    /* access modifiers changed from: private */
    public List<ZKStaffVerifyBean> listVeriType;
    private ListView listView;
    private List<ZKStaffVerifyBean> mData = new ArrayList();
    private UserInfo userInfo;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_staff_edit_vertification);
        initToolBar();
        this.adapter = new ZKStaffVertificationAdapter(this, this.mData);
        ListView listView2 = (ListView) findViewById(R.id.listview_verify);
        this.listView = listView2;
        listView2.setAdapter(this.adapter);
        init();
    }

    private void initToolBar() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.verifytoolbar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffVerifyActivity.this.finish();
            }
        }, getString(R.string.zk_staff_vertify_tit));
        zKToolbar.setRightView();
    }

    private void init() {
        Button button = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        this.btnList.add(button);
        this.btnList.add(button2);
        this.btnList.add(button3);
        this.btnList.add(button4);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        ((Button) findViewById(R.id.btnleft)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnright)).setOnClickListener(this);
        this.listVeriType = createList();
        this.hashMap = createMap();
        long longExtra = getIntent().getLongExtra("userInfo_id", 0);
        if (longExtra != 0) {
            try {
                this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(longExtra));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        InitView(this.userInfo.getVerify_Type());
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ZKStaffVerifyActivity.this.adapter.setHasSelectStatus(true);
                ZKStaffVerifyActivity.this.adapter.setSelected(i);
                ZKStaffVerifyActivity.this.adapter.notifyDataSetChanged();
                String string = ZKStaffVerifyActivity.this.adapter.getmData().get(i).getString();
                for (int i2 = 0; i2 < ZKStaffVerifyActivity.this.listVeriType.size(); i2++) {
                    if (string.equals(ZKStaffVerifyActivity.this.listVeriType.get(i2))) {
                        int unused = ZKStaffVerifyActivity.this.isKeySameBefore = i2;
                        return;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.adapter.setmData(this.mData);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0095, code lost:
        if (r3 != 0) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a4, code lost:
        if (r3 != 0) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a6, code lost:
        r8.convertPushFree(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a9, code lost:
        finish();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onClick(android.view.View r8) {
        /*
            r7 = this;
            int r8 = r8.getId()
            r0 = 0
            switch(r8) {
                case 2131296360: goto L_0x00b6;
                case 2131296361: goto L_0x0035;
                case 2131296362: goto L_0x002b;
                case 2131296363: goto L_0x0020;
                case 2131296364: goto L_0x0015;
                case 2131296365: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x00b9
        L_0x000a:
            r8 = 3
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> r0 = r7.bolHashmap
            r7.setButtonBG(r8, r0)
            r7.udpateList()
            goto L_0x00b9
        L_0x0015:
            r8 = 2
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> r0 = r7.bolHashmap
            r7.setButtonBG(r8, r0)
            r7.udpateList()
            goto L_0x00b9
        L_0x0020:
            r8 = 1
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> r0 = r7.bolHashmap
            r7.setButtonBG(r8, r0)
            r7.udpateList()
            goto L_0x00b9
        L_0x002b:
            java.util.HashMap<java.lang.Integer, java.lang.Boolean> r8 = r7.bolHashmap
            r7.setButtonBG(r0, r8)
            r7.udpateList()
            goto L_0x00b9
        L_0x0035:
            com.zkteco.android.db.orm.tna.UserInfo r8 = r7.userInfo
            int r1 = r7.isKeySameBefore
            r8.setVerify_Type(r1)
            com.zkteco.android.core.sdk.HubProtocolManager r8 = new com.zkteco.android.core.sdk.HubProtocolManager
            r8.<init>(r7)
            r1 = 0
            com.zkteco.android.db.orm.tna.UserInfo r3 = r7.userInfo     // Catch:{ SQLException -> 0x009d, all -> 0x009a }
            r3.setSEND_FLAG(r0)     // Catch:{ SQLException -> 0x009d, all -> 0x009a }
            com.zkteco.android.db.orm.tna.UserInfo r3 = r7.userInfo     // Catch:{ SQLException -> 0x009d, all -> 0x009a }
            r3.update()     // Catch:{ SQLException -> 0x009d, all -> 0x009a }
            long r3 = r8.convertPushInit()     // Catch:{ SQLException -> 0x009d, all -> 0x009a }
            java.lang.String r5 = "USER_INFO"
            r8.setPushTableName(r3, r5)     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r5 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x0098 }
            r8.setPushStrField(r3, r5, r6)     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r5 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x0098 }
            int r6 = r6.getSEND_FLAG()     // Catch:{ SQLException -> 0x0098 }
            r8.setPushIntField(r3, r5, r6)     // Catch:{ SQLException -> 0x0098 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x0098 }
            r5.<init>()     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r6 = "(User_PIN='"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x0098 }
            com.zkteco.android.db.orm.tna.UserInfo r6 = r7.userInfo     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r6 = r6.getUser_PIN()     // Catch:{ SQLException -> 0x0098 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r6 = "')"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r5 = r5.toString()     // Catch:{ SQLException -> 0x0098 }
            r8.setPushCon(r3, r5)     // Catch:{ SQLException -> 0x0098 }
            java.lang.String r5 = ""
            r8.sendHubAction(r0, r3, r5)     // Catch:{ SQLException -> 0x0098 }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00a9
            goto L_0x00a6
        L_0x0098:
            r0 = move-exception
            goto L_0x009f
        L_0x009a:
            r0 = move-exception
            r3 = r1
            goto L_0x00ae
        L_0x009d:
            r0 = move-exception
            r3 = r1
        L_0x009f:
            r0.printStackTrace()     // Catch:{ all -> 0x00ad }
            int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x00a9
        L_0x00a6:
            r8.convertPushFree(r3)
        L_0x00a9:
            r7.finish()
            goto L_0x00b9
        L_0x00ad:
            r0 = move-exception
        L_0x00ae:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x00b5
            r8.convertPushFree(r3)
        L_0x00b5:
            throw r0
        L_0x00b6:
            r7.finish()
        L_0x00b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.activity.ZKStaffVerifyActivity.onClick(android.view.View):void");
    }

    private ArrayList<ZKStaffVerifyBean> createList() {
        ArrayList<ZKStaffVerifyBean> arrayList = new ArrayList<>();
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list0), 0));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list1), 1));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list2), 2));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list3), 3));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list4), 4));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list5), 5));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list6), 6));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list7), 7));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list8), 8));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list9), 9));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list10), 10));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list11), 11));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list12), 12));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list13), 13));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list14), 14));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list15), 15));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list16), 16));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list17), 17));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list18), 18));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list19), 19));
        arrayList.add(new ZKStaffVerifyBean(getString(R.string.zk_staff_vertify_list20), 20));
        return arrayList;
    }

    private HashMap<List<Boolean>, List<Integer>> createMap() {
        HashMap<List<Boolean>, List<Integer>> hashMap2 = new HashMap<>();
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, false, false, false})), new ArrayList(Collections.singletonList(1)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{false, true, false, false})), new ArrayList(Collections.singletonList(4)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{false, false, true, false})), new ArrayList(Collections.singletonList(3)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{false, false, false, true})), new ArrayList(Collections.singletonList(2)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, true, false, false})), new ArrayList(Arrays.asList(new Integer[]{6, 10})));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, false, true, false})), new ArrayList(Arrays.asList(new Integer[]{5, 9})));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, false, false, true})), new ArrayList(Collections.singletonList(8)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{false, true, true, false})), new ArrayList(Arrays.asList(new Integer[]{7, 11})));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, true, true, false})), new ArrayList(Arrays.asList(new Integer[]{0, 12})));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, false, true, true})), new ArrayList(Collections.singletonList(13)));
        hashMap2.put(new ArrayList(Arrays.asList(new Boolean[]{true, true, false, true})), new ArrayList(Collections.singletonList(14)));
        return hashMap2;
    }

    private void InitView(int i) {
        if (i < 0) {
            i = 0;
        }
        List arrayList = new ArrayList();
        this.isKeySameBefore = i;
        Iterator<Map.Entry<List<Boolean>, List<Integer>>> it = this.hashMap.entrySet().iterator();
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry next = it.next();
            List list = (List) next.getValue();
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                } else if (((Integer) list.get(i2)).intValue() == i) {
                    this.adapter.setHasSelectStatus(true);
                    this.adapter.setSelected(i2);
                    z = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                List list2 = (List) next.getKey();
                if (list2.size() > 0) {
                    for (int i3 = 0; i3 < list2.size(); i3++) {
                        this.bolHashmap.put(Integer.valueOf(i3), (Boolean) list2.get(i3));
                    }
                }
                arrayList = list;
            } else {
                this.adapter.setHasSelectStatus(false);
                this.adapter.setSelected(15);
                arrayList = list;
            }
        }
        if (arrayList.size() > 0) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                if (((Integer) arrayList.get(i4)).intValue() == i) {
                    this.mData.add(new ZKStaffVerifyBean(true, this.listVeriType.get(((Integer) arrayList.get(i4)).intValue()).getString()));
                } else {
                    this.mData.add(new ZKStaffVerifyBean(false, this.listVeriType.get(((Integer) arrayList.get(i4)).intValue()).getString()));
                }
            }
        }
        for (Map.Entry next2 : this.bolHashmap.entrySet()) {
            if (((Boolean) next2.getValue()).booleanValue()) {
                this.btnList.get(((Integer) next2.getKey()).intValue()).setBackgroundResource(R.drawable.shape_mgreen_btn);
            } else {
                this.btnList.get(((Integer) next2.getKey()).intValue()).setBackgroundResource(R.drawable.shape_mgrey_btn);
            }
        }
    }

    private void setButtonBG(int i, HashMap<Integer, Boolean> hashMap2) {
        if (hashMap2.containsKey(Integer.valueOf(i))) {
            if (hashMap2.get(Integer.valueOf(i)).booleanValue()) {
                hashMap2.remove(Integer.valueOf(i));
                hashMap2.put(Integer.valueOf(i), false);
            } else {
                hashMap2.remove(Integer.valueOf(i));
                hashMap2.put(Integer.valueOf(i), true);
            }
        }
        for (Map.Entry next : hashMap2.entrySet()) {
            if (((Boolean) next.getValue()).booleanValue()) {
                this.btnList.get(((Integer) next.getKey()).intValue()).setBackgroundResource(R.drawable.shape_mgreen_btn);
            } else {
                this.btnList.get(((Integer) next.getKey()).intValue()).setBackgroundResource(R.drawable.shape_mgrey_btn);
            }
        }
    }

    public void udpateList() {
        Iterator<Map.Entry<List<Boolean>, List<Integer>>> it = this.hashMap.entrySet().iterator();
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry next = it.next();
            List list = (List) next.getKey();
            if (list.size() > 0) {
                int i = 0;
                while (true) {
                    if (i >= list.size()) {
                        break;
                    }
                    z = this.bolHashmap.containsKey(Integer.valueOf(i));
                    if (z) {
                        if (this.bolHashmap.get(Integer.valueOf(i)).booleanValue()) {
                            if (!((Boolean) list.get(i)).booleanValue()) {
                                break;
                            }
                        } else if (((Boolean) list.get(i)).booleanValue()) {
                            break;
                        }
                    }
                    i++;
                }
                z = false;
            }
            if (z) {
                List list2 = (List) next.getValue();
                if (list2.size() > 0) {
                    this.mData = new ArrayList();
                    boolean z2 = false;
                    for (int i2 = 0; i2 < list2.size(); i2++) {
                        if (((Integer) list2.get(i2)).intValue() == this.isKeySameBefore) {
                            this.adapter.setHasSelectStatus(true);
                            this.adapter.setSelected(i2);
                            this.mData.add(new ZKStaffVerifyBean(true, this.listVeriType.get(((Integer) list2.get(i2)).intValue()).getString()));
                            z2 = true;
                        } else {
                            this.mData.add(new ZKStaffVerifyBean(false, this.listVeriType.get(((Integer) list2.get(i2)).intValue()).getString()));
                        }
                    }
                    if (!z2) {
                        this.adapter.setHasSelectStatus(false);
                        this.adapter.setSelected(15);
                    }
                    this.adapter.setmData(this.mData);
                }
            } else {
                this.adapter.setHasSelectStatus(false);
                this.adapter.setSelected(15);
            }
        }
        if (!z) {
            this.mData = new ArrayList();
            this.adapter.setHasSelectStatus(false);
            this.adapter.setSelected(15);
            this.adapter.setmData(this.mData);
        }
    }
}
