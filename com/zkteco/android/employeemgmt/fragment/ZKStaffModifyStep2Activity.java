package com.zkteco.android.employeemgmt.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.orm.manager.DataManager;
import com.zkteco.android.db.orm.tna.UserInfo;
import com.zkteco.android.employeemgmt.activity.base.BaseActivity;
import com.zkteco.android.employeemgmt.view.DataTimeDialog;
import com.zkteco.android.zkcore.view.ZKToolbar;
import com.zkteco.android.zkcore.view.alert.ZKEditDialog;
import com.zkteco.android.zkcore.view.util.PixelUtil;
import com.zkteco.android.zkcore.view.wheelview.DateDialog;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ZKStaffModifyStep2Activity extends BaseActivity implements View.OnClickListener {
    private static final int ALL_GONE = 4;
    private static final String DATE_DIAPLAY_FORMAT = "yyyy-MM-dd";
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String DATE_TIME_DISPLAY_FORMAT = "yyyy-MM-dd HH:mm";
    private static final int RULE_COUNT = 2;
    private static final int RULE_DURATION = 1;
    private static final int RULE_FORBID = 0;
    private static final int RULE_PLUS = 3;
    private static final String TAG = "ZKStaffModifyStep2Activity";
    private final DataManager dataManager = DBManager.getInstance();
    private int dateFmtFunOn;
    private String day;
    /* access modifiers changed from: private */
    public String endDate;
    /* access modifiers changed from: private */
    public int expires;
    private String hour;
    private boolean isValiditySwitchOpened = false;
    private LinearLayout linChoose;
    private int mAccessRuleTyle;
    private List<View> mAllOutViewList = new ArrayList();
    private HashMap<Integer, List<View>> mHashMap = new HashMap<>();
    private List<ImageView> mInnnerImageList = new ArrayList();
    private String minute;
    private String month;
    private RelativeLayout relCount3;
    private RelativeLayout relDuration2;
    private RelativeLayout relEnd;
    private RelativeLayout relForbid1;
    private RelativeLayout relPlus4;
    private RelativeLayout relStart;
    private RelativeLayout relSwitch;
    private LinearLayout relTop;
    private RelativeLayout relValid;
    /* access modifiers changed from: private */
    public String startDate;
    private ImageView switchImage;
    private TextView tvEndDate;
    private TextView tvRules;
    private TextView tvStartDate;
    /* access modifiers changed from: private */
    public TextView tvValidCount;
    private TextView tv_top_rules;
    /* access modifiers changed from: private */
    public UserInfo userInfo;
    /* access modifiers changed from: private */
    public int validCount;
    private String year;

    public int getLayoutResId() {
        return R.layout.activity_z_k_staff_modify_step2;
    }

    public void initPresenter() {
    }

    public void initUI() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.toolBar);
        zKToolbar.setLeftView((View.OnClickListener) new View.OnClickListener() {
            public void onClick(View view) {
                ZKStaffModifyStep2Activity.this.finish();
            }
        }, getString(R.string.zk_staff_validate));
        zKToolbar.setRightView();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rel_top);
        this.relTop = linearLayout;
        this.mAllOutViewList.add(linearLayout);
        this.tv_top_rules = (TextView) findViewById(R.id.tv_top_rules);
        this.tvRules = (TextView) findViewById(R.id.tv_rules);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.lin_choose);
        this.linChoose = linearLayout2;
        this.mAllOutViewList.add(linearLayout2);
        this.relForbid1 = (RelativeLayout) findViewById(R.id.rel_forbid1);
        this.mInnnerImageList.add((ImageView) findViewById(R.id.iv_check1));
        this.relDuration2 = (RelativeLayout) findViewById(R.id.rel_duration2);
        this.mInnnerImageList.add((ImageView) findViewById(R.id.iv_check2));
        this.relCount3 = (RelativeLayout) findViewById(R.id.rel_count3);
        this.mInnnerImageList.add((ImageView) findViewById(R.id.iv_check3));
        this.relPlus4 = (RelativeLayout) findViewById(R.id.rel_plus4);
        this.mInnnerImageList.add((ImageView) findViewById(R.id.iv_check4));
        this.relSwitch = (RelativeLayout) findViewById(R.id.rel_switch);
        this.switchImage = (ImageView) findViewById(R.id.switch_image);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_start);
        this.relStart = relativeLayout;
        this.mAllOutViewList.add(relativeLayout);
        this.tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.rel_end);
        this.relEnd = relativeLayout2;
        this.mAllOutViewList.add(relativeLayout2);
        this.tvEndDate = (TextView) findViewById(R.id.tv_end_date);
        this.relValid = (RelativeLayout) findViewById(R.id.rel_valid);
        this.tvValidCount = (TextView) findViewById(R.id.tv_valid_count);
        this.mAllOutViewList.add(this.relValid);
    }

    public void initListener() {
        initClickListener();
    }

    public void initData() {
        try {
            this.userInfo = (UserInfo) new UserInfo().queryForId(Long.valueOf(getIntent().getLongExtra("id", 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.mAccessRuleTyle = DBManager.getInstance().getIntOption("AccessRuleType", 0);
        this.dateFmtFunOn = DBManager.getInstance().getIntOption("DateFmtFunOn", 0);
        initmRelHashMap();
        this.expires = this.userInfo.getExpires();
        this.validCount = this.userInfo.getVaildCount();
        setExpiresOut(this.expires);
        setInnerView(Integer.valueOf(this.expires));
        setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
        String startDatetime = this.userInfo.getStartDatetime();
        String endDatetime = this.userInfo.getEndDatetime();
        if (startDatetime.equals("0") || endDatetime.equals("0")) {
            this.isValiditySwitchOpened = false;
            this.switchImage.setImageResource(R.mipmap.ic_switch_close);
            this.relStart.setVisibility(8);
            this.relEnd.setVisibility(8);
        } else {
            String date = getDate(startDatetime);
            this.startDate = date;
            this.tvStartDate.setText(date);
            String date2 = getDate(endDatetime);
            this.endDate = date2;
            this.tvEndDate.setText(date2);
            this.isValiditySwitchOpened = true;
            this.switchImage.setImageResource(R.mipmap.ic_switch_open);
            this.relStart.setVisibility(0);
            this.relEnd.setVisibility(0);
        }
        if (this.mAccessRuleTyle == 1) {
            this.expires = 1;
            setInnerView(1);
            setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
            setExpiresOut(this.expires);
            setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
        }
        if (this.mAccessRuleTyle == 0) {
            this.relSwitch.setVisibility(8);
        } else {
            this.relSwitch.setVisibility(0);
        }
    }

    private void initmRelHashMap() {
        for (int i = 0; i < 5; i++) {
            ArrayList arrayList = new ArrayList();
            if (i != 0) {
                if (i == 1) {
                    if (this.mAccessRuleTyle == 0) {
                        arrayList.add(this.relTop);
                    }
                    arrayList.add(this.relStart);
                    arrayList.add(this.relEnd);
                } else if (i == 2) {
                    if (this.mAccessRuleTyle == 0) {
                        arrayList.add(this.relTop);
                    }
                    arrayList.add(this.relValid);
                } else if (i == 3) {
                    if (this.mAccessRuleTyle == 0) {
                        arrayList.add(this.relTop);
                    }
                    arrayList.add(this.relStart);
                    arrayList.add(this.relEnd);
                    arrayList.add(this.relValid);
                }
            } else if (this.mAccessRuleTyle == 0) {
                arrayList.add(this.relTop);
            }
            this.mHashMap.put(Integer.valueOf(i), arrayList);
        }
    }

    private void initClickListener() {
        this.relTop.setOnClickListener(this);
        this.relForbid1.setOnClickListener(this);
        this.relDuration2.setOnClickListener(this);
        this.relCount3.setOnClickListener(this);
        this.relPlus4.setOnClickListener(this);
        this.relSwitch.setOnClickListener(this);
        this.relStart.setOnClickListener(this);
        this.relEnd.setOnClickListener(this);
        this.relValid.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_count3:
                this.isValiditySwitchOpened = false;
                closeValidity();
                this.expires = 2;
                setInnerView(2);
                this.linChoose.setVisibility(8);
                setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
                setExpiresOut(this.expires);
                setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
                return;
            case R.id.rel_duration2:
                this.isValiditySwitchOpened = true;
                openValidity();
                this.expires = 1;
                setInnerView(1);
                this.linChoose.setVisibility(8);
                setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
                setExpiresOut(this.expires);
                setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
                return;
            case R.id.rel_end:
                int i = this.dateFmtFunOn;
                if (i == 0) {
                    showDateDialog(false);
                    return;
                } else if (i == 1) {
                    showDateTimeDialog(false);
                    return;
                } else {
                    return;
                }
            case R.id.rel_forbid1:
                this.isValiditySwitchOpened = false;
                closeValidity();
                this.expires = 0;
                setInnerView(0);
                this.linChoose.setVisibility(8);
                setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
                setExpiresOut(this.expires);
                setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
                return;
            case R.id.rel_plus4:
                this.isValiditySwitchOpened = true;
                openValidity();
                this.expires = 3;
                setInnerView(3);
                this.linChoose.setVisibility(8);
                setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
                setExpiresOut(this.expires);
                setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
                return;
            case R.id.rel_start:
                int i2 = this.dateFmtFunOn;
                if (i2 == 0) {
                    showDateDialog(true);
                    return;
                } else if (i2 == 1) {
                    showDateTimeDialog(true);
                    return;
                } else {
                    return;
                }
            case R.id.rel_switch:
                if (this.isValiditySwitchOpened) {
                    this.isValiditySwitchOpened = false;
                    closeValidity();
                    this.switchImage.setImageResource(R.mipmap.ic_switch_close);
                    return;
                }
                this.isValiditySwitchOpened = true;
                openValidity();
                this.switchImage.setImageResource(R.mipmap.ic_switch_open);
                return;
            case R.id.rel_top:
                setExpiresOut(4);
                this.linChoose.setVisibility(0);
                setInnerView(Integer.valueOf(this.expires));
                this.relStart.setVisibility(8);
                this.relEnd.setVisibility(8);
                return;
            case R.id.rel_valid:
                final ZKEditDialog zKEditDialog = new ZKEditDialog(this);
                zKEditDialog.show();
                zKEditDialog.setBtnType(2, getString(R.string.zk_staff_cancel), getString(R.string.zk_staff_ok));
                zKEditDialog.setMessage(getString(R.string.zk_staff_input_validcount));
                zKEditDialog.setContentType(1, "");
                zKEditDialog.setEditText(2, 12, String.valueOf(this.tvValidCount.getText()));
                zKEditDialog.setListener(new ZKEditDialog.ResultListener() {
                    public void failure() {
                    }

                    public void success() {
                        if (zKEditDialog.getInputText().isEmpty()) {
                            zKEditDialog.setHint(ZKStaffModifyStep2Activity.this.getString(R.string.zk_staff_input_validcount_notnull));
                        } else if (Long.parseLong(zKEditDialog.getInputText()) > 10000) {
                            zKEditDialog.setHint(ZKStaffModifyStep2Activity.this.getString(R.string.zk_staff_input_validcount_prompt));
                        } else {
                            ZKStaffModifyStep2Activity zKStaffModifyStep2Activity = ZKStaffModifyStep2Activity.this;
                            zKStaffModifyStep2Activity.setExpiresToData(zKStaffModifyStep2Activity.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, Integer.valueOf(zKEditDialog.getInputText()).intValue());
                            ZKStaffModifyStep2Activity.this.tvValidCount.setText(String.valueOf(ZKStaffModifyStep2Activity.this.userInfo.getVaildCount()));
                            zKEditDialog.dismiss();
                        }
                    }
                });
                return;
            default:
                return;
        }
    }

    private void closeValidity() {
        this.startDate = "0";
        this.endDate = "0";
        this.userInfo.setStartDatetime("0");
        this.userInfo.setEndDatetime(this.endDate);
        try {
            this.userInfo.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.relStart.setVisibility(8);
        this.relEnd.setVisibility(8);
    }

    private void openValidity() {
        String startDatetime = this.userInfo.getStartDatetime();
        String endDatetime = this.userInfo.getEndDatetime();
        if (startDatetime.equals("0") || endDatetime.equals("0")) {
            String date = getDate(startDatetime);
            this.startDate = date;
            this.tvStartDate.setText(date);
            String date2 = getDate(endDatetime);
            this.endDate = date2;
            this.tvEndDate.setText(date2);
            setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
            setExpiresOut(this.expires);
            setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
        }
        this.relStart.setVisibility(0);
        this.relEnd.setVisibility(0);
    }

    private void showDateDialog(boolean z) {
        DateDialog dateDialog = new DateDialog(this);
        if (z) {
            getDate(this.userInfo.getStartDatetime());
            dateDialog.setDate(this.year, this.month, this.day);
            dateDialog.show();
            dateDialog.setOnDateListener(new DateDialog.OnDateListener() {
                public void onClick(String str, String str2, String str3) {
                    String unused = ZKStaffModifyStep2Activity.this.startDate = str + "-" + str2 + "-" + str3;
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity = ZKStaffModifyStep2Activity.this;
                    if (zKStaffModifyStep2Activity.compareDate(zKStaffModifyStep2Activity.startDate, ZKStaffModifyStep2Activity.this.endDate)) {
                        ZKStaffModifyStep2Activity zKStaffModifyStep2Activity2 = ZKStaffModifyStep2Activity.this;
                        String unused2 = zKStaffModifyStep2Activity2.endDate = zKStaffModifyStep2Activity2.startDate;
                    }
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity3 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity3.setTextOut(zKStaffModifyStep2Activity3.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, String.valueOf(ZKStaffModifyStep2Activity.this.validCount));
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity4 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity4.setExpiresOut(zKStaffModifyStep2Activity4.expires);
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity5 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity5.setExpiresToData(zKStaffModifyStep2Activity5.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, ZKStaffModifyStep2Activity.this.validCount);
                }
            });
            return;
        }
        getDate(this.userInfo.getEndDatetime());
        dateDialog.setDate(this.year, this.month, this.day);
        dateDialog.show();
        dateDialog.setOnDateListener(new DateDialog.OnDateListener() {
            public void onClick(String str, String str2, String str3) {
                String unused = ZKStaffModifyStep2Activity.this.endDate = str + "-" + str2 + "-" + str3;
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity = ZKStaffModifyStep2Activity.this;
                if (zKStaffModifyStep2Activity.compareDate(zKStaffModifyStep2Activity.startDate, ZKStaffModifyStep2Activity.this.endDate)) {
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity2 = ZKStaffModifyStep2Activity.this;
                    String unused2 = zKStaffModifyStep2Activity2.endDate = zKStaffModifyStep2Activity2.startDate;
                }
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity3 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity3.setTextOut(zKStaffModifyStep2Activity3.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, String.valueOf(ZKStaffModifyStep2Activity.this.validCount));
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity4 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity4.setExpiresOut(zKStaffModifyStep2Activity4.expires);
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity5 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity5.setExpiresToData(zKStaffModifyStep2Activity5.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, ZKStaffModifyStep2Activity.this.validCount);
            }
        });
    }

    private void showDateTimeDialog(boolean z) {
        DataTimeDialog dataTimeDialog = new DataTimeDialog(this);
        if (z) {
            getDate(this.userInfo.getStartDatetime());
            dataTimeDialog.setDate(this.year, this.month, this.day, this.hour, this.minute);
            dataTimeDialog.show();
            dataTimeDialog.setOnBtnListener(new DataTimeDialog.OnBtnListener() {
                public void onCancelClick() {
                }

                public void onSureClick(String str, String str2, String str3, String str4, String str5) {
                    String unused = ZKStaffModifyStep2Activity.this.startDate = String.format("%s-%s-%s %s:%s", new Object[]{str, str2, str3, str4, str5});
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity = ZKStaffModifyStep2Activity.this;
                    if (zKStaffModifyStep2Activity.compareDate(zKStaffModifyStep2Activity.startDate, ZKStaffModifyStep2Activity.this.endDate)) {
                        ZKStaffModifyStep2Activity zKStaffModifyStep2Activity2 = ZKStaffModifyStep2Activity.this;
                        String unused2 = zKStaffModifyStep2Activity2.endDate = zKStaffModifyStep2Activity2.startDate;
                    }
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity3 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity3.setTextOut(zKStaffModifyStep2Activity3.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, String.valueOf(ZKStaffModifyStep2Activity.this.validCount));
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity4 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity4.setExpiresOut(zKStaffModifyStep2Activity4.expires);
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity5 = ZKStaffModifyStep2Activity.this;
                    zKStaffModifyStep2Activity5.setExpiresToData(zKStaffModifyStep2Activity5.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, ZKStaffModifyStep2Activity.this.validCount);
                }
            });
            return;
        }
        getDate(this.userInfo.getEndDatetime());
        dataTimeDialog.setDate(this.year, this.month, this.day, this.hour, this.minute);
        dataTimeDialog.show();
        dataTimeDialog.setOnBtnListener(new DataTimeDialog.OnBtnListener() {
            public void onCancelClick() {
            }

            public void onSureClick(String str, String str2, String str3, String str4, String str5) {
                String unused = ZKStaffModifyStep2Activity.this.endDate = String.format("%s-%s-%s %s:%s", new Object[]{str, str2, str3, str4, str5});
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity = ZKStaffModifyStep2Activity.this;
                if (zKStaffModifyStep2Activity.compareDate(zKStaffModifyStep2Activity.startDate, ZKStaffModifyStep2Activity.this.endDate)) {
                    ZKStaffModifyStep2Activity zKStaffModifyStep2Activity2 = ZKStaffModifyStep2Activity.this;
                    String unused2 = zKStaffModifyStep2Activity2.endDate = zKStaffModifyStep2Activity2.startDate;
                }
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity3 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity3.setTextOut(zKStaffModifyStep2Activity3.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, String.valueOf(ZKStaffModifyStep2Activity.this.validCount));
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity4 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity4.setExpiresOut(zKStaffModifyStep2Activity4.expires);
                ZKStaffModifyStep2Activity zKStaffModifyStep2Activity5 = ZKStaffModifyStep2Activity.this;
                zKStaffModifyStep2Activity5.setExpiresToData(zKStaffModifyStep2Activity5.expires, ZKStaffModifyStep2Activity.this.startDate, ZKStaffModifyStep2Activity.this.endDate, ZKStaffModifyStep2Activity.this.validCount);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setExpiresOut(int i) {
        Iterator<Map.Entry<Integer, List<View>>> it = this.mHashMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry next = it.next();
            if (i == ((Integer) next.getKey()).intValue()) {
                List list = (List) next.getValue();
                for (View next2 : this.mAllOutViewList) {
                    if (list.contains(next2)) {
                        next2.setVisibility(0);
                    } else {
                        next2.setVisibility(8);
                    }
                }
            }
        }
        if (this.isValiditySwitchOpened) {
            this.relStart.setVisibility(0);
            this.relEnd.setVisibility(0);
            return;
        }
        this.relStart.setVisibility(8);
        this.relEnd.setVisibility(8);
    }

    private void setInnerView(Integer num) {
        for (int i = 0; i < this.mInnnerImageList.size(); i++) {
            if (num.intValue() == i) {
                this.mInnnerImageList.get(i).setImageResource(R.mipmap.ic_light);
            } else {
                this.mInnnerImageList.get(i).setImageResource(R.mipmap.ic_unlight);
            }
        }
    }

    public void setTextOut(int i, String str, String str2, String str3) {
        if (i == 0) {
            this.tvRules.setText(R.string.zk_staff_check_forbid);
        } else if (i == 1) {
            this.tvRules.setText(R.string.zk_staff_check_duration);
        } else if (i == 2) {
            this.tvRules.setText(R.string.zk_staff_check_count);
        } else if (i == 3) {
            this.tvRules.setText(R.string.zk_staff_check_plus);
        }
        if (this.tv_top_rules.getText().toString().length() + this.tvRules.getText().toString().length() > 40) {
            this.tv_top_rules.setMaxWidth(PixelUtil.dp2px(220.0f, this));
        } else {
            this.tv_top_rules.setMaxWidth(PixelUtil.dp2px(400.0f, this));
        }
        this.tvStartDate.setText(str);
        this.tvEndDate.setText(str2);
        this.tvValidCount.setText(str3);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setExpiresToData(int r5, java.lang.String r6, java.lang.String r7, int r8) {
        /*
            r4 = this;
            int r0 = r4.mAccessRuleTyle
            if (r0 != 0) goto L_0x0009
            com.zkteco.android.db.orm.tna.UserInfo r0 = r4.userInfo
            r0.setExpires(r5)
        L_0x0009:
            if (r6 == 0) goto L_0x0010
            com.zkteco.android.db.orm.tna.UserInfo r0 = r4.userInfo
            r0.setStartDatetime(r6)
        L_0x0010:
            if (r7 == 0) goto L_0x0017
            com.zkteco.android.db.orm.tna.UserInfo r6 = r4.userInfo
            r6.setEndDatetime(r7)
        L_0x0017:
            r6 = 2
            if (r5 == r6) goto L_0x001d
            r6 = 3
            if (r5 != r6) goto L_0x0022
        L_0x001d:
            com.zkteco.android.db.orm.tna.UserInfo r5 = r4.userInfo
            r5.setVaildCount(r8)
        L_0x0022:
            com.zkteco.android.db.orm.tna.UserInfo r5 = r4.userInfo
            r6 = 0
            r5.setSEND_FLAG(r6)
            com.zkteco.android.db.orm.tna.UserInfo r5 = r4.userInfo     // Catch:{ SQLException -> 0x002e }
            r5.update()     // Catch:{ SQLException -> 0x002e }
            goto L_0x0032
        L_0x002e:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0032:
            com.zkteco.android.core.sdk.HubProtocolManager r5 = new com.zkteco.android.core.sdk.HubProtocolManager
            r5.<init>(r4)
            r7 = 0
            com.zkteco.android.db.orm.tna.UserInfo r0 = r4.userInfo     // Catch:{ SQLException -> 0x0093, all -> 0x0090 }
            r0.setSEND_FLAG(r6)     // Catch:{ SQLException -> 0x0093, all -> 0x0090 }
            com.zkteco.android.db.orm.tna.UserInfo r0 = r4.userInfo     // Catch:{ SQLException -> 0x0093, all -> 0x0090 }
            r0.update()     // Catch:{ SQLException -> 0x0093, all -> 0x0090 }
            long r0 = r5.convertPushInit()     // Catch:{ SQLException -> 0x0093, all -> 0x0090 }
            java.lang.String r2 = "USER_INFO"
            r5.setPushTableName(r0, r2)     // Catch:{ SQLException -> 0x008e }
            java.lang.String r2 = "User_PIN"
            com.zkteco.android.db.orm.tna.UserInfo r3 = r4.userInfo     // Catch:{ SQLException -> 0x008e }
            java.lang.String r3 = r3.getUser_PIN()     // Catch:{ SQLException -> 0x008e }
            r5.setPushStrField(r0, r2, r3)     // Catch:{ SQLException -> 0x008e }
            java.lang.String r2 = "SEND_FLAG"
            com.zkteco.android.db.orm.tna.UserInfo r3 = r4.userInfo     // Catch:{ SQLException -> 0x008e }
            int r3 = r3.getSEND_FLAG()     // Catch:{ SQLException -> 0x008e }
            r5.setPushIntField(r0, r2, r3)     // Catch:{ SQLException -> 0x008e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SQLException -> 0x008e }
            r2.<init>()     // Catch:{ SQLException -> 0x008e }
            java.lang.String r3 = "(User_PIN='"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x008e }
            com.zkteco.android.db.orm.tna.UserInfo r3 = r4.userInfo     // Catch:{ SQLException -> 0x008e }
            java.lang.String r3 = r3.getUser_PIN()     // Catch:{ SQLException -> 0x008e }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x008e }
            java.lang.String r3 = "')"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ SQLException -> 0x008e }
            java.lang.String r2 = r2.toString()     // Catch:{ SQLException -> 0x008e }
            r5.setPushCon(r0, r2)     // Catch:{ SQLException -> 0x008e }
            java.lang.String r2 = ""
            r5.sendHubAction(r6, r0, r2)     // Catch:{ SQLException -> 0x008e }
            int r6 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r6 == 0) goto L_0x009f
            goto L_0x009c
        L_0x008e:
            r6 = move-exception
            goto L_0x0095
        L_0x0090:
            r6 = move-exception
            r0 = r7
            goto L_0x00a1
        L_0x0093:
            r6 = move-exception
            r0 = r7
        L_0x0095:
            r6.printStackTrace()     // Catch:{ all -> 0x00a0 }
            int r6 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r6 == 0) goto L_0x009f
        L_0x009c:
            r5.convertPushFree(r0)
        L_0x009f:
            return
        L_0x00a0:
            r6 = move-exception
        L_0x00a1:
            int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x00a8
            r5.convertPushFree(r0)
        L_0x00a8:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.android.employeemgmt.fragment.ZKStaffModifyStep2Activity.setExpiresToData(int, java.lang.String, java.lang.String, int):void");
    }

    public void backToOut() {
        setInnerView(Integer.valueOf(this.expires));
        this.linChoose.setVisibility(8);
        setTextOut(this.expires, this.startDate, this.endDate, String.valueOf(this.validCount));
        setExpiresOut(this.expires);
        setExpiresToData(this.expires, this.startDate, this.endDate, this.validCount);
    }

    private String getDate(String str) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        int i = this.dateFmtFunOn;
        if (i == 0) {
            if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                str = str.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str.length() - 9);
            }
            try {
                instance.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(str));
            } catch (ParseException e) {
                Log.v(TAG, e.getLocalizedMessage());
            }
        } else if (i == 1) {
            try {
                if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                    str = str.replace(ExifInterface.GPS_DIRECTION_TRUE, " ").substring(0, str.length() - 3);
                }
                instance.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(str));
            } catch (Exception e2) {
                Log.v(TAG, e2.getLocalizedMessage());
            }
        }
        this.year = String.valueOf(instance.get(1));
        int i2 = instance.get(2) + 1;
        this.month = String.valueOf(i2);
        if (i2 < 10) {
            this.month = "0" + this.month;
        }
        int i3 = instance.get(5);
        this.day = String.valueOf(i3);
        if (i3 < 10) {
            this.day = "0" + this.day;
        }
        int i4 = instance.get(11);
        this.hour = String.valueOf(i4);
        if (i4 < 10) {
            this.hour = "0" + this.hour;
        }
        int i5 = instance.get(12);
        this.minute = String.valueOf(i5);
        if (i5 < 10) {
            this.minute = "0" + this.minute;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        if (this.dateFmtFunOn == 1) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        }
        return simpleDateFormat.format(instance.getTime());
    }

    /* access modifiers changed from: private */
    public boolean compareDate(String str, String str2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (this.dateFmtFunOn == 1) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            }
            Date parse = simpleDateFormat.parse(str);
            Date parse2 = simpleDateFormat.parse(str2);
            if (parse.after(parse2)) {
                return true;
            }
            if (parse.equals(parse2)) {
            }
            return false;
        } catch (ParseException unused) {
            return true;
        }
    }
}
