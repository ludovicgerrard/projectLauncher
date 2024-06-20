package com.zktechnology.android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.zktechnology.android.launcher.R;
import com.zkteco.android.zkcore.view.ZKToolbar;

public class IntroductionActivity extends Activity {
    public static final String ADVANCED_LANGUAGE = "Language";
    private static final int RESULT_OK = 102;
    private WebView webView;
    private ZKToolbar zkToolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_introduction);
        initView();
        initDate();
    }

    public static boolean isZh(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initDate() {
        /*
            r4 = this;
            com.zkteco.android.db.orm.manager.DataManager r0 = com.zktechnology.android.utils.DBManager.getInstance()     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "Language"
            r2 = 0
            int r0 = r0.getIntOption(r1, r2)     // Catch:{ SQLException -> 0x014d }
            com.zkteco.android.db.orm.tna.LanguageInfo r1 = new com.zkteco.android.db.orm.tna.LanguageInfo     // Catch:{ SQLException -> 0x014d }
            r1.<init>()     // Catch:{ SQLException -> 0x014d }
            com.j256.ormlite.stmt.QueryBuilder r1 = r1.getQueryBuilder()     // Catch:{ SQLException -> 0x014d }
            com.j256.ormlite.stmt.Where r1 = r1.where()     // Catch:{ SQLException -> 0x014d }
            java.lang.String r2 = "Language_Flag"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)     // Catch:{ SQLException -> 0x014d }
            com.j256.ormlite.stmt.Where r1 = r1.eq(r2, r3)     // Catch:{ SQLException -> 0x014d }
            java.util.List r1 = r1.query()     // Catch:{ SQLException -> 0x014d }
            if (r1 == 0) goto L_0x0151
            boolean r1 = r1.isEmpty()     // Catch:{ SQLException -> 0x014d }
            if (r1 != 0) goto L_0x0151
            r1 = 65
            if (r0 == r1) goto L_0x0145
            r1 = 66
            if (r0 == r1) goto L_0x013d
            r1 = 80
            if (r0 == r1) goto L_0x0135
            r1 = 86
            if (r0 == r1) goto L_0x012d
            r1 = 97
            if (r0 == r1) goto L_0x0125
            r1 = 101(0x65, float:1.42E-43)
            if (r0 == r1) goto L_0x011d
            r1 = 103(0x67, float:1.44E-43)
            if (r0 == r1) goto L_0x0113
            r1 = 105(0x69, float:1.47E-43)
            if (r0 == r1) goto L_0x0109
            r1 = 112(0x70, float:1.57E-43)
            if (r0 == r1) goto L_0x00ff
            r1 = 116(0x74, float:1.63E-43)
            if (r0 == r1) goto L_0x00f7
            r1 = 119(0x77, float:1.67E-43)
            java.lang.String r2 = "file:///android_asset/introduction/introduction_zh_tw.html"
            if (r0 == r1) goto L_0x00f1
            r1 = 89
            if (r0 == r1) goto L_0x00e7
            r1 = 90
            if (r0 == r1) goto L_0x00dc
            switch(r0) {
                case 68: goto L_0x00d1;
                case 69: goto L_0x00c8;
                case 70: goto L_0x00bf;
                case 71: goto L_0x00b4;
                case 72: goto L_0x00a9;
                case 73: goto L_0x00a0;
                case 74: goto L_0x0097;
                case 75: goto L_0x008e;
                case 76: goto L_0x0085;
                default: goto L_0x0067;
            }
        L_0x0067:
            switch(r0) {
                case 82: goto L_0x007c;
                case 83: goto L_0x0073;
                case 84: goto L_0x006c;
                default: goto L_0x006a;
            }
        L_0x006a:
            goto L_0x0151
        L_0x006c:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            r0.loadUrl(r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0073:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_cn.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x007c:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_ru.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0085:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_th.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x008e:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_ko.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0097:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_ja.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00a0:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_in.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00a9:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "iw"
            java.lang.String r2 = "IL"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00b4:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "de"
            java.lang.String r2 = "DE"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00bf:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_fr.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00c8:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_en.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00d1:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "nl"
            java.lang.String r2 = "NL"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00dc:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "cs"
            java.lang.String r2 = "CZ"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00e7:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "sk"
            java.lang.String r2 = "SK"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00f1:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            r0.loadUrl(r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00f7:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_tr.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x00ff:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "pl"
            java.lang.String r2 = "PL"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0109:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "it"
            java.lang.String r2 = "IT"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0113:
            java.util.Locale r0 = new java.util.Locale     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "el"
            java.lang.String r2 = "GR"
            r0.<init>(r1, r2)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x011d:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_es_mx.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0125:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_es.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x012d:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_vi.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0135:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_pt.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x013d:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_ar.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x0145:
            android.webkit.WebView r0 = r4.webView     // Catch:{ SQLException -> 0x014d }
            java.lang.String r1 = "file:///android_asset/introduction/introduction_fa.html"
            r0.loadUrl(r1)     // Catch:{ SQLException -> 0x014d }
            goto L_0x0151
        L_0x014d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0151:
            android.webkit.WebView r0 = r4.webView
            android.webkit.WebSettings r0 = r0.getSettings()
            r1 = 1
            r0.setJavaScriptEnabled(r1)
            android.webkit.WebView r0 = r4.webView
            android.webkit.WebSettings r0 = r0.getSettings()
            android.webkit.WebSettings$LayoutAlgorithm r2 = android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            r0.setLayoutAlgorithm(r2)
            android.webkit.WebView r0 = r4.webView
            android.webkit.WebSettings r0 = r0.getSettings()
            r0.setLoadWithOverviewMode(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.activity.IntroductionActivity.initDate():void");
    }

    private void initView() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.zk_toolbar);
        this.zkToolbar = zKToolbar;
        zKToolbar.setLeftView(getString(R.string.user_notice));
        this.webView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.agree_bt).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                IntroductionActivity.this.lambda$initView$0$IntroductionActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$IntroductionActivity(View view) {
        finish();
    }
}
