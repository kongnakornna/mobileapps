package devsync.com.talentonlineandroid;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.androidquery.AQuery;
import devsync.com.talentonlineandroid.shares.AppSharedPreferences;
import devsync.com.talentonlineandroid.utils.APIs;
import devsync.com.talentonlineandroid.utils.Global;
import devsync.com.talentonlineandroid.view.AlertDialog;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 * Modified by THanisak Piyasaksiri on 3/20/2016 AD.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.AlertDialogListener {

    private AQuery aq;
    private Dialog dialog = null;

    private boolean LOGGER = false;
    private static long back_pressed;

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.alert_backpress), Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        aq = new AQuery(this);
        init();
    }

    private void init() {

        aq.id(R.id.home_webview).getWebView().clearCache(true);
        aq.id(R.id.home_webview).getWebView().getSettings().setJavaScriptEnabled(true);
        aq.id(R.id.home_webview).getWebView().getSettings().setDefaultTextEncodingName("UTF-8");
        aq.id(R.id.home_webview).getWebView().getSettings().setLoadWithOverviewMode(true);

        aq.id(R.id.home_webview).getWebView().setVerticalScrollBarEnabled(true);
        aq.id(R.id.home_webview).getWebView().setScrollContainer(true);
        aq.id(R.id.home_webview).getWebView().setBackgroundColor(0x00000000);

        aq.id(R.id.home_webview).getWebView().setWebViewClient(getWebViewClient());
        aq.id(R.id.home_webview).getWebView().setWebChromeClient(getWebChromeClient());

        setProgressbarVisibility(true);
        aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_ME + AppSharedPreferences.getInstance(this).getCandidateID());

        aq.id(R.id.home_tab_home).visibility(View.GONE);
        aq.id(R.id.home_tab_home).clicked(this);

        aq.id(R.id.home_tab_jobmatch).clicked(this);
        aq.id(R.id.home_tab_me).clicked(this);
        aq.id(R.id.home_tab_message).clicked(this);
        aq.id(R.id.home_tab_search).clicked(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.home_tab_home:
                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_HOME + AppSharedPreferences.getInstance(this).getCandidateID());
                break;
            case R.id.home_tab_jobmatch:
                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_JOBMATCH + AppSharedPreferences.getInstance(this).getCandidateID());
                break;
            case R.id.home_tab_me:
                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_ME + AppSharedPreferences.getInstance(this).getCandidateID());
                break;
            case R.id.home_tab_message:
                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_MESSAGE + AppSharedPreferences.getInstance(this).getCandidateID());
                break;
            case R.id.home_tab_search:
                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(APIs.WEBVIEW_URL_SEARCH + AppSharedPreferences.getInstance(this).getCandidateID());
                break;
            default:
                break;
        }
    }

    private WebViewClient getWebViewClient() {

        return new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                setProgressbarVisibility(true);
                aq.id(R.id.home_webview).getWebView().loadUrl(url);
                return true;
            }

            public void onLoadResource (WebView view, String url) {

            }

            public void onPageFinished(WebView view, String url) {

                if(view.getId() == aq.id(R.id.home_webview).getWebView().getId())
                    setProgressbarVisibility(false);
            }
        };
    }

    private WebChromeClient getWebChromeClient() {

        return new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {

                if(progress == 100)
                    setProgressbarVisibility(false);
            }

            public boolean onJsAlert(WebView view, String url, String message, android.webkit.JsResult result) {

                onJSAlert(message);
                result.cancel();
                return true;
            }
        };
    }

    private void onJSAlert(String message) {

        dialog = Global.showAlertDialog(this, message, this);
        dialog.show();
    }

    private void setProgressbarVisibility(boolean enabled) {

        if(enabled)
            aq.id(R.id.home_progress_frame).visibility(View.VISIBLE);
        else
            aq.id(R.id.home_progress_frame).visibility(View.GONE);
    }

    @Override
    public void onAlertClick() {

        if(dialog != null)
            dialog.dismiss();

        dialog = null;
    }
}
