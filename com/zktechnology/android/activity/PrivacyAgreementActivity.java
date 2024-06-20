package com.zktechnology.android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import com.zktechnology.android.launcher.R;
import com.zktechnology.android.launcher2.Launcher;
import com.zktechnology.android.utils.DBManager;
import com.zkteco.android.db.dao.LanguageInfoDao;
import com.zkteco.android.db.orm.tna.LanguageInfo;
import com.zkteco.android.zkcore.view.ZKToolbar;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;
import java.util.Locale;

public class PrivacyAgreementActivity extends Activity {
    public static final String ADVANCED_LANGUAGE = "Language";
    private static final int RESULT_OK = 102;
    private Button agreeBt;
    private Disposable countDownDisposable;
    private CheckBox noRepeat;
    private WebView webView;
    private ZKToolbar zkToolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Launcher.keyguardOperation(getWindow(), false);
        setContentView(R.layout.activity_privacy_agreement);
        initView();
        initDate();
    }

    public static boolean isZh(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }

    private void initDate() {
        try {
            checkLanguageInfoTable();
            int intOption = DBManager.getInstance().getIntOption("Language", 0);
            if (intOption == 65) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_fa.html");
            } else if (intOption == 66) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_ar.html");
            } else if (intOption == 80) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_pt.html");
            } else if (intOption == 86) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_vi.html");
            } else if (intOption == 97) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_es.html");
            } else if (intOption == 101) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_es_mx.html");
            } else if (intOption == 103) {
                new Locale("el", "GR");
            } else if (intOption == 105) {
                new Locale("it", "IT");
            } else if (intOption == 112) {
                new Locale("pl", "PL");
            } else if (intOption == 116) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_tr.html");
            } else if (intOption == 119) {
                this.webView.loadUrl("file:///android_asset/introduction/introduction_zh_tw.html");
            } else if (intOption == 89) {
                new Locale("sk", "SK");
            } else if (intOption != 90) {
                switch (intOption) {
                    case 68:
                        new Locale("nl", "NL");
                        break;
                    case 69:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_en.html");
                        break;
                    case 70:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_fr.html");
                        break;
                    case 71:
                        new Locale("de", "DE");
                        break;
                    case 72:
                        new Locale("iw", "IL");
                        break;
                    case 73:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_in.html");
                        break;
                    case 74:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_ja.html");
                        break;
                    case 75:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_ko.html");
                        break;
                    case 76:
                        this.webView.loadUrl("file:///android_asset/introduction/introduction_th.html");
                        break;
                    default:
                        switch (intOption) {
                            case 82:
                                this.webView.loadUrl("file:///android_asset/introduction/introduction_ru.html");
                                break;
                            case 83:
                                this.webView.loadUrl("file:///android_asset/introduction/introduction_cn.html");
                                break;
                            case 84:
                                this.webView.loadUrl("file:///android_asset/introduction/introduction_zh_tw.html");
                                break;
                            default:
                                this.webView.loadUrl("file:///android_asset/introduction/introduction_en.html");
                                break;
                        }
                }
            } else {
                new Locale("cs", "CZ");
            }
        } catch (Exception e) {
            this.webView.loadUrl("file:///android_asset/introduction/introduction_en.html");
            e.printStackTrace();
        }
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                return true;
            }
        });
        this.webView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webView.getSettings().setLoadWithOverviewMode(true);
    }

    private void checkLanguageInfoTable() {
        try {
            List query = new LanguageInfo().getQueryBuilder().query();
            if (query == null || query.isEmpty()) {
                resetLanguageInfoDataIfNull();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resetLanguageInfoDataIfNull();
        }
    }

    private void resetLanguageInfoDataIfNull() {
        try {
            LanguageInfoDao.resetLanguageInfoTable(DBManager.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        ZKToolbar zKToolbar = (ZKToolbar) findViewById(R.id.zk_toolbar);
        this.zkToolbar = zKToolbar;
        zKToolbar.setLeftView(getResources().getString(R.string.user_notice));
        this.webView = (WebView) findViewById(R.id.webview);
        this.agreeBt = (Button) findViewById(R.id.agree_bt);
        CheckBox checkBox = (CheckBox) findViewById(R.id.no_repeat);
        this.noRepeat = checkBox;
        checkBox.setClickable(false);
        findViewById(R.id.repeat_layout).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                PrivacyAgreementActivity.this.lambda$initView$0$PrivacyAgreementActivity(view);
            }
        });
        this.agreeBt.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                PrivacyAgreementActivity.this.lambda$initView$1$PrivacyAgreementActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$PrivacyAgreementActivity(View view) {
        CheckBox checkBox = this.noRepeat;
        checkBox.setChecked(!checkBox.isChecked());
    }

    public /* synthetic */ void lambda$initView$1$PrivacyAgreementActivity(View view) {
        if (this.noRepeat.isChecked()) {
            DBManager.getInstance().setIntOption("PrivacyAgreementShow", 0);
        } else {
            DBManager.getInstance().setIntOption("PrivacyAgreementShow", 1);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        CheckBox checkBox = this.noRepeat;
        boolean z = true;
        if (DBManager.getInstance().getIntOption("PrivacyAgreementShow", 1) != 0) {
            z = false;
        }
        checkBox.setChecked(z);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.countDownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.countDownDisposable.dispose();
        }
    }
}
