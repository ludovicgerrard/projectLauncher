package com.zktechnology.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.core.view.ViewConfigurationCompat;
import com.zktechnology.android.activity.EditWidgetActivity;
import com.zktechnology.android.bean.WidgetInfo;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.launcher2.ZKEventLauncher;
import com.zktechnology.android.launcher2.ZKLauncher;
import com.zktechnology.android.launcher2.ZKVideoIntercomLauncher;
import com.zktechnology.android.push.util.FileLogUtils;
import com.zktechnology.android.utils.CanVerifyUtil;
import com.zktechnology.android.utils.DBManager;
import com.zktechnology.android.utils.LogUtils;
import com.zktechnology.android.verify.bean.process.ZKMarkTypeBean;
import com.zktechnology.android.verify.bean.process.ZKVerifyType;
import com.zktechnology.android.verify.controller.ZKVerController;
import com.zktechnology.android.verify.utils.ZKDBConfig;
import com.zktechnology.android.verify.utils.ZKVerConConst;
import com.zktechnology.android.verify.utils.ZKVerConState;
import com.zktechnology.android.verify.utils.ZKVerProcessPar;
import com.zkteco.android.core.model.ActionModel;
import com.zkteco.android.core.model.ButtonWidgetInfo;
import com.zkteco.android.core.sdk.BtnWidgetManager;
import com.zkteco.android.db.orm.tna.ShortState;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomWidgetView extends FrameLayout implements AdapterView.OnItemClickListener {
    public static final String ACTION_TOUCH_ATT_CHANGE = "com.zktechnology.android.touch.changed";
    /* access modifiers changed from: private */
    public static CountDownTimer timer;
    private int SPluginAction = 0;
    private AnimationSet animationSetdown;
    private AnimationSet animationSetup;
    private Rect callRect = new Rect();
    /* access modifiers changed from: private */
    public boolean gotoEditActivity = false;
    private GridView gridView;
    private List<ButtonWidgetInfo> hiddenWidgetInfo = new ArrayList();
    private boolean initStatus = true;
    /* access modifiers changed from: private */
    public boolean isMoveUp = false;
    /* access modifiers changed from: private */
    public boolean isOpenStatus = false;
    private long lasttime;
    /* access modifiers changed from: private */
    public Object lock = new Object();
    /* access modifiers changed from: private */
    public int mAPBFO;
    /* access modifiers changed from: private */
    public int mAccessRuleType;
    /* access modifiers changed from: private */
    public int mAntiPassbackOn;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public boolean mDragging;
    private TextView mEdit;
    /* access modifiers changed from: private */
    public int mLastY;
    private LinearLayout mLlHomeIcon;
    /* access modifiers changed from: private */
    public int mLockFunOn;
    /* access modifiers changed from: private */
    public Scroller mScroller;
    /* access modifiers changed from: private */
    public int mTouchSlop;
    /* access modifiers changed from: private */
    public int maxScrollHeight = 0;
    /* access modifiers changed from: private */
    public View menu;
    /* access modifiers changed from: private */
    public Rect menuRect = new Rect();
    /* access modifiers changed from: private */
    public View middleAndBottom;
    private MyAdapter myAdapter;
    private View root;
    /* access modifiers changed from: private */
    public int selectPosition = -1;
    /* access modifiers changed from: private */
    public List<ButtonWidgetInfo> showedWidgetInfo = new ArrayList();
    /* access modifiers changed from: private */
    public int topDip = 15;
    private View topView;
    /* access modifiers changed from: private */
    public ImageView upArrow;
    /* access modifiers changed from: private */
    public Rect upArrowRect = new Rect();
    /* access modifiers changed from: private */
    public View user;
    /* access modifiers changed from: private */
    public Rect userRect = new Rect();
    /* access modifiers changed from: private */
    public BtnWidgetManager widgetManager;

    public CustomWidgetView(Context context) {
        super(context);
        init(context);
    }

    public CustomWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.view_custom_widget, this);
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        this.middleAndBottom = findViewById(R.id.middleAndBottom);
        this.upArrow = (ImageView) findViewById(R.id.upArrow);
        this.topView = findViewById(R.id.topView);
        this.mScroller = new Scroller(this.mContext);
        this.mLlHomeIcon = (LinearLayout) findViewById(R.id.ll_home_icon);
        this.menu = findViewById(R.id.menu);
        this.user = findViewById(R.id.user);
        this.root = findViewById(R.id.root);
        this.gridView = (GridView) findViewById(R.id.gridView);
        TextView textView = (TextView) findViewById(R.id.edit);
        this.mEdit = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((Activity) CustomWidgetView.this.mContext).startActivity(new Intent(CustomWidgetView.this.mContext, EditWidgetActivity.class));
                boolean unused = CustomWidgetView.this.gotoEditActivity = true;
            }
        });
        changeMenuUI();
        this.upArrow.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CustomWidgetView.this.upArrow.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CustomWidgetView.this.upArrowRect.left = (int) CustomWidgetView.this.upArrow.getX();
                CustomWidgetView.this.upArrowRect.top = ((int) CustomWidgetView.this.upArrow.getY()) + ((int) TypedValue.applyDimension(1, (float) CustomWidgetView.this.topDip, CustomWidgetView.this.mContext.getResources().getDisplayMetrics()));
                CustomWidgetView.this.upArrowRect.right = CustomWidgetView.this.upArrowRect.left + CustomWidgetView.this.upArrow.getMeasuredWidth();
                CustomWidgetView.this.upArrowRect.bottom = CustomWidgetView.this.upArrowRect.top + CustomWidgetView.this.upArrow.getMeasuredHeight();
            }
        });
    }

    private void changeMenuUI() {
        this.menu.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CustomWidgetView.this.menu.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CustomWidgetView.this.menuRect.left = (int) CustomWidgetView.this.menu.getX();
                CustomWidgetView.this.menuRect.top = ((int) CustomWidgetView.this.menu.getY()) + ((int) TypedValue.applyDimension(1, (float) CustomWidgetView.this.topDip, CustomWidgetView.this.mContext.getResources().getDisplayMetrics()));
                CustomWidgetView.this.menuRect.right = CustomWidgetView.this.menuRect.left + CustomWidgetView.this.menu.getMeasuredWidth();
                CustomWidgetView.this.menuRect.bottom = CustomWidgetView.this.menuRect.top + CustomWidgetView.this.menu.getMeasuredHeight();
            }
        });
        this.user.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CustomWidgetView.this.user.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CustomWidgetView.this.userRect.left = (int) CustomWidgetView.this.user.getX();
                CustomWidgetView.this.userRect.top = ((int) CustomWidgetView.this.user.getY()) + ((int) TypedValue.applyDimension(1, (float) CustomWidgetView.this.topDip, CustomWidgetView.this.mContext.getResources().getDisplayMetrics()));
                CustomWidgetView.this.userRect.right = CustomWidgetView.this.userRect.left + CustomWidgetView.this.user.getMeasuredWidth();
                CustomWidgetView.this.userRect.bottom = CustomWidgetView.this.userRect.top + CustomWidgetView.this.user.getMeasuredHeight();
            }
        });
        int i = 1;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) != 1) {
            i = 0;
        }
        int i2 = 5;
        this.mLlHomeIcon.setGravity(i != 0 ? 5 : 3);
        this.mLlHomeIcon.setLayoutDirection(i);
        GridView gridView2 = this.gridView;
        if (i == 0) {
            i2 = 3;
        }
        gridView2.setGravity(i2);
        this.gridView.setLayoutDirection(i);
    }

    private void getNeedParameter() {
        this.mAccessRuleType = DBManager.getInstance().getIntOption("AccessRuleType", 0);
        this.mLockFunOn = DBManager.getInstance().getIntOption("~LockFunOn", 0);
        this.mAntiPassbackOn = DBManager.getInstance().getIntOption(ZKDBConfig.ANTI_PASSBACK_TYPE, 0);
        this.mAPBFO = DBManager.getInstance().getIntOption("~APBFO", 0);
    }

    public void changeUI() {
        getNeedParameter();
        if (this.mAccessRuleType == 0) {
            if (this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0) {
                this.upArrow.setAnimation(this.animationSetup);
                this.upArrow.setVisibility(0);
            } else {
                this.upArrow.setAnimation((Animation) null);
                this.upArrow.setVisibility(8);
            }
        }
        setSPluginAction();
        changeMenuUI();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((TextView) findViewById(R.id.select_widget_title)).setText(getResources().getString(R.string.zk_select_widget_text));
        ((TextView) findViewById(R.id.edit)).setText(getResources().getString(R.string.zk_edit));
    }

    public void initData() {
        getNeedParameter();
        if (this.mAccessRuleType == 0 && (this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0)) {
            this.upArrow.setAnimation(this.animationSetup);
            this.upArrow.setVisibility(0);
        } else {
            this.upArrow.setVisibility(8);
        }
        this.widgetManager = new BtnWidgetManager(this.mContext);
        MyAdapter myAdapter2 = new MyAdapter();
        this.myAdapter = myAdapter2;
        this.gridView.setAdapter(myAdapter2);
        this.gridView.setOnItemClickListener(this);
        setSPluginAction();
        changeMenuUI();
        this.topView.setOnTouchListener(new View.OnTouchListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
                if (r0 != 3) goto L_0x0116;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
                /*
                    r5 = this;
                    float r6 = r7.getY()
                    int r6 = (int) r6
                    com.zktechnology.android.view.CustomWidgetView r0 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = r0.mAccessRuleType
                    r1 = 1
                    if (r0 != 0) goto L_0x0111
                    com.zktechnology.android.view.CustomWidgetView r0 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = r0.mLockFunOn
                    r2 = 15
                    if (r0 != r2) goto L_0x0028
                    com.zktechnology.android.view.CustomWidgetView r0 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = r0.mAPBFO
                    if (r0 == 0) goto L_0x0028
                    com.zktechnology.android.view.CustomWidgetView r0 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = r0.mAntiPassbackOn
                    if (r0 != 0) goto L_0x0111
                L_0x0028:
                    int r0 = r7.getAction()
                    if (r0 == 0) goto L_0x010b
                    r3 = 0
                    if (r0 == r1) goto L_0x006f
                    r4 = 2
                    if (r0 == r4) goto L_0x0039
                    r6 = 3
                    if (r0 == r6) goto L_0x006f
                    goto L_0x0116
                L_0x0039:
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    int r7 = r7.mLastY
                    int r6 = r6 - r7
                    int r7 = java.lang.Math.abs(r6)
                    com.zktechnology.android.view.CustomWidgetView r0 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = r0.mTouchSlop
                    if (r7 <= r0) goto L_0x0051
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r7.mDragging = r1
                L_0x0051:
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean r7 = r7.mDragging
                    if (r7 == 0) goto L_0x0116
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    int r0 = -r6
                    r7.scrollBy(r3, r0)
                    if (r6 >= 0) goto L_0x0068
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r6.isMoveUp = r1
                    goto L_0x0116
                L_0x0068:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r6.isMoveUp = r3
                    goto L_0x0116
                L_0x006f:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean r6 = r6.mDragging
                    if (r6 == 0) goto L_0x0100
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean r6 = r6.isMoveUp
                    if (r6 == 0) goto L_0x00bf
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    r6.showWidget()
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r6.isOpenStatus = r1
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAccessRuleType
                    if (r6 != 0) goto L_0x0105
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mLockFunOn
                    if (r6 != r2) goto L_0x00a9
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAPBFO
                    if (r6 == 0) goto L_0x00a9
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAntiPassbackOn
                    if (r6 != 0) goto L_0x0105
                L_0x00a9:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    android.widget.ImageView r6 = r6.upArrow
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    android.content.res.Resources r7 = r7.getResources()
                    r0 = 2131558400(0x7f0d0000, float:1.8742115E38)
                    android.graphics.drawable.Drawable r7 = r7.getDrawable(r0)
                    r6.setBackground(r7)
                    goto L_0x0105
                L_0x00bf:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    r6.hindWidget()
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r6.isOpenStatus = r3
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAccessRuleType
                    if (r6 != 0) goto L_0x0105
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mLockFunOn
                    if (r6 != r2) goto L_0x00e9
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAPBFO
                    if (r6 == 0) goto L_0x00e9
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    int r6 = r6.mAntiPassbackOn
                    if (r6 != 0) goto L_0x0105
                L_0x00e9:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    android.widget.ImageView r6 = r6.upArrow
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    android.content.res.Resources r7 = r7.getResources()
                    r0 = 2131558401(0x7f0d0001, float:1.8742117E38)
                    android.graphics.drawable.Drawable r7 = r7.getDrawable(r0)
                    r6.setBackground(r7)
                    goto L_0x0105
                L_0x0100:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    r6.checkTouchRange(r7)
                L_0x0105:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    boolean unused = r6.mDragging = r3
                    goto L_0x0116
                L_0x010b:
                    com.zktechnology.android.view.CustomWidgetView r7 = com.zktechnology.android.view.CustomWidgetView.this
                    int unused = r7.mLastY = r6
                    goto L_0x0116
                L_0x0111:
                    com.zktechnology.android.view.CustomWidgetView r6 = com.zktechnology.android.view.CustomWidgetView.this
                    r6.checkTouchRange(r7)
                L_0x0116:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.view.CustomWidgetView.AnonymousClass5.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    public void checkTouchRange(MotionEvent motionEvent) {
        if (Math.abs(SystemClock.elapsedRealtime() - this.lasttime) > 500) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (checkPointInRect(x, y, this.userRect)) {
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    onClickKeyboardButton();
                    this.lasttime = SystemClock.elapsedRealtime();
                }
            } else if (checkPointInRect(x, y, this.callRect)) {
                if (ZKLauncher.videoIntercomFunOn == 1) {
                    onClickCallButton();
                    this.lasttime = SystemClock.elapsedRealtime();
                }
            } else if (checkPointInRect(x, y, this.menuRect)) {
                if (CanVerifyUtil.getInstance().isCanVerify()) {
                    onClickShowAllApps();
                    this.lasttime = SystemClock.elapsedRealtime();
                }
            } else if (this.mAccessRuleType != 0 || (!(this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0) || !checkPointInRect(x, y, this.upArrowRect))) {
                this.lasttime = SystemClock.elapsedRealtime();
            } else {
                if (this.isOpenStatus) {
                    hindWidget();
                    this.isOpenStatus = false;
                    if (this.mAccessRuleType == 0 && (this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0)) {
                        this.upArrow.setBackground(getResources().getDrawable(R.mipmap.arrow_up));
                    }
                } else {
                    showWidget();
                    this.isOpenStatus = true;
                    if (this.mAccessRuleType == 0 && (this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0)) {
                        this.upArrow.setBackground(getResources().getDrawable(R.mipmap.arrow_down));
                    }
                }
                this.lasttime = SystemClock.elapsedRealtime();
            }
        }
    }

    public boolean checkPointInRect(int i, int i2, Rect rect) {
        return i >= rect.left && i <= rect.right && i2 >= rect.top && i2 <= rect.bottom;
    }

    public void showWidget() {
        this.mScroller.startScroll(0, getScrollY(), 0, this.maxScrollHeight - getScrollY(), 300);
        postInvalidate();
    }

    public void hindWidget() {
        this.mScroller.startScroll(0, getScrollY(), 0, (-this.maxScrollHeight) - getScrollY(), 300);
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void scrollTo(int i, int i2) {
        if (i2 >= 0) {
            i2 = 0;
        }
        int i3 = this.maxScrollHeight;
        if (i2 <= (-i3)) {
            i2 = -i3;
        }
        if (this.initStatus && i2 == (-i3)) {
            this.root.setVisibility(0);
            this.initStatus = false;
        }
        super.scrollTo(i, i2);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.selectPosition = i;
        MyAdapter myAdapter2 = this.myAdapter;
        if (myAdapter2 != null) {
            myAdapter2.notifyDataSetChanged();
        }
    }

    class MyAdapter extends BaseAdapter {
        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        MyAdapter() {
        }

        public int getCount() {
            return CustomWidgetView.this.showedWidgetInfo.size();
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(CustomWidgetView.this.mContext, R.layout.item_widget, (ViewGroup) null);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            TextView textView = (TextView) view.findViewById(R.id.text);
            ButtonWidgetInfo buttonWidgetInfo = (ButtonWidgetInfo) CustomWidgetView.this.showedWidgetInfo.get(i);
            if (CustomWidgetView.this.selectPosition == i) {
                imageView.setBackgroundResource(WidgetInfo.valueOf(buttonWidgetInfo.getName()).selectedImageId);
                if (CustomWidgetView.this.widgetManager != null) {
                    CustomWidgetView.this.widgetManager.savePressedWidgetId(0, i);
                }
                Intent intent = new Intent();
                intent.setAction(CustomWidgetView.ACTION_TOUCH_ATT_CHANGE);
                intent.putExtra("selectedId", i);
                CustomWidgetView.this.mContext.sendBroadcast(intent);
                CustomWidgetView.this.startClickWidgetTimer(imageView);
            } else {
                imageView.setBackgroundResource(WidgetInfo.valueOf(buttonWidgetInfo.getName()).unselectedImageId);
            }
            textView.setText(WidgetInfo.valueOf(buttonWidgetInfo.getName()).nameId);
            return view;
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        stopClickWidgetTimer();
        return super.onSaveInstanceState();
    }

    /* access modifiers changed from: private */
    public void startClickWidgetTimer(View view) {
        synchronized (this.lock) {
            CountDownTimer countDownTimer = timer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer = null;
            }
        }
        final View view2 = view;
        timer = new CountDownTimer((long) (DBManager.getInstance().getIntOption("SpluginSelectTimeout", 1) * 1000), 1000) {
            public void onTick(long j) {
            }

            public void onFinish() {
                synchronized (CustomWidgetView.this.lock) {
                    try {
                        view2.setBackgroundResource(WidgetInfo.valueOf(((ButtonWidgetInfo) CustomWidgetView.this.showedWidgetInfo.get(CustomWidgetView.this.selectPosition)).getName()).unselectedImageId);
                        int unused = CustomWidgetView.this.selectPosition = -1;
                        if (CustomWidgetView.this.widgetManager != null) {
                            CustomWidgetView.this.widgetManager.removePressedId();
                        }
                        Intent intent = new Intent();
                        intent.setAction(CustomWidgetView.ACTION_TOUCH_ATT_CHANGE);
                        intent.putExtra("selectedId", -1);
                        CustomWidgetView.this.mContext.sendBroadcast(intent);
                        CountDownTimer unused2 = CustomWidgetView.timer = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void stopClickWidgetTimer() {
        synchronized (this.lock) {
            CountDownTimer countDownTimer = timer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timer = null;
                this.selectPosition = -1;
                BtnWidgetManager btnWidgetManager = this.widgetManager;
                if (btnWidgetManager != null) {
                    btnWidgetManager.removePressedId();
                }
                Intent intent = new Intent();
                intent.setAction(ACTION_TOUCH_ATT_CHANGE);
                intent.putExtra("selectedId", -1);
                this.mContext.sendBroadcast(intent);
                MyAdapter myAdapter2 = this.myAdapter;
                if (myAdapter2 != null) {
                    myAdapter2.notifyDataSetChanged();
                }
            }
        }
    }

    public void onClickCallButton() {
        ((ZKVideoIntercomLauncher) this.mContext).setCallStatus(1);
    }

    public void onClickKeyboardButton() {
        Launcher.State workSpaceState = ((Launcher) getContext()).getWorkSpaceState();
        if (ZKVerProcessPar.ACTION_BEAN.isBolKeyboard() && workSpaceState == Launcher.State.WORKSPACE) {
            LogUtils.e(LogUtils.TAG_VERIFY, "工号验证");
            ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
            FileLogUtils.writeTouchLog("setFTouchAction: onClickKeyboardButton");
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ZKMarkTypeBean(ZKVerifyType.PIN.getValue(), false));
            ZKVerProcessPar.CON_MARK_BEAN.setVerifyTypeList(arrayList);
            ZKVerProcessPar.KEY_BOARD_1V1 = true;
            ZKVerProcessPar.CON_MARK_BUNDLE.putInt(ZKVerConConst.BUNDLE_1V1_RETRY, ZKLauncher.sFPRetry);
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WANT);
        } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
        } else {
            if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            }
        }
    }

    private void onClickShowAllApps() {
        Launcher.State workSpaceState = ((Launcher) getContext()).getWorkSpaceState();
        if (ZKVerProcessPar.ACTION_BEAN.isBolMenu() && workSpaceState == Launcher.State.WORKSPACE) {
            ZKVerProcessPar.ACTION_BEAN.setFTouchAction();
            FileLogUtils.writeTouchLog("setFTouchAction: onClickShowAllApps");
            ZKVerController.getInstance().changeState(ZKVerConState.STATE_WANT);
            ZKVerProcessPar.CON_MARK_BEAN.setIntent(1);
        } else if (!"wait".equals(ZKVerController.synchronizedstate)) {
        } else {
            if (ZKVerController.getInstance().getState() != ZKVerConState.STATE_WAIT || !ZKEventLauncher.show) {
                ZKVerProcessPar.ACTION_BEAN.setTTouchAction();
            }
        }
    }

    public void setSPluginAction() {
        this.SPluginAction = DBManager.getInstance().getIntOption("SPluginAction", 0);
        setMiddleAndBottom();
        setShowWidget();
        setButtonShow();
        setTipShow();
        MyAdapter myAdapter2 = this.myAdapter;
        if (myAdapter2 != null) {
            myAdapter2.notifyDataSetChanged();
        }
    }

    private void setTipShow() {
        findViewById(R.id.tip).setVisibility(this.SPluginAction == 1 ? 0 : 8);
    }

    private void setShowWidget() {
        BtnWidgetManager btnWidgetManager;
        int i;
        if (this.mAccessRuleType == 0 && ((this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0) && this.SPluginAction == 1 && (btnWidgetManager = this.widgetManager) != null)) {
            this.showedWidgetInfo = btnWidgetManager.getBtnWidgets(0);
            this.hiddenWidgetInfo = this.widgetManager.getBtnWidgets(1);
            if (this.showedWidgetInfo == null) {
                this.showedWidgetInfo = new ArrayList();
                List arrayList = new ArrayList();
                try {
                    arrayList = new ShortState().queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                WidgetInfo[] values = WidgetInfo.values();
                if (arrayList.size() < values.length) {
                    i = arrayList.size();
                } else {
                    i = values.length;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    ButtonWidgetInfo buttonWidgetInfo = new ButtonWidgetInfo();
                    buttonWidgetInfo.setName(values[i2].name());
                    ActionModel actionModel = new ActionModel();
                    actionModel.setActionType(1);
                    actionModel.setActionParams(String.valueOf(((ShortState) arrayList.get(i2)).getState_No()));
                    actionModel.setActionName(values[i2].name());
                    buttonWidgetInfo.setAction(actionModel);
                    this.showedWidgetInfo.add(buttonWidgetInfo);
                }
                this.widgetManager.saveBtnWidgets(0, this.showedWidgetInfo);
            }
            if (this.hiddenWidgetInfo == null) {
                ArrayList arrayList2 = new ArrayList();
                this.hiddenWidgetInfo = arrayList2;
                this.widgetManager.saveBtnWidgets(1, arrayList2);
                return;
            }
            return;
        }
        this.showedWidgetInfo.clear();
        this.hiddenWidgetInfo.clear();
    }

    private void setMiddleAndBottom() {
        this.middleAndBottom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                CustomWidgetView.this.middleAndBottom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CustomWidgetView customWidgetView = CustomWidgetView.this;
                int unused = customWidgetView.maxScrollHeight = customWidgetView.middleAndBottom.getMeasuredHeight();
                if (!CustomWidgetView.this.gotoEditActivity) {
                    CustomWidgetView.this.mScroller.startScroll(0, 0, 0, -CustomWidgetView.this.maxScrollHeight, 0);
                    if (CustomWidgetView.this.mAccessRuleType == 0 && (CustomWidgetView.this.mLockFunOn != 15 || CustomWidgetView.this.mAPBFO == 0 || CustomWidgetView.this.mAntiPassbackOn == 0)) {
                        CustomWidgetView.this.upArrow.setBackground(CustomWidgetView.this.getResources().getDrawable(R.mipmap.arrow_up));
                    }
                }
                boolean unused2 = CustomWidgetView.this.gotoEditActivity = false;
            }
        });
    }

    private void setButtonShow() {
        if (this.mAccessRuleType == 0 && ((this.mLockFunOn != 15 || this.mAPBFO == 0 || this.mAntiPassbackOn == 0) && this.SPluginAction == 1)) {
            this.mEdit.setVisibility(0);
        } else {
            this.mEdit.setVisibility(8);
        }
    }
}
