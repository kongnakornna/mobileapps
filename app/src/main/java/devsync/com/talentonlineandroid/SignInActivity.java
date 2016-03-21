package devsync.com.talentonlineandroid;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.HashMap;
import devsync.com.talentonlineandroid.json.JSONParser;
import devsync.com.talentonlineandroid.listener.HttpConnectionListener;
import devsync.com.talentonlineandroid.model.BeanLoginFBParam;
import devsync.com.talentonlineandroid.model.BeanLoginParam;
import devsync.com.talentonlineandroid.shares.AppSharedPreferences;
import devsync.com.talentonlineandroid.utils.APIs;
import devsync.com.talentonlineandroid.utils.Global;
import devsync.com.talentonlineandroid.utils.UtilHttpConnection;
import devsync.com.talentonlineandroid.view.AlertDialog;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 * Modified by Thanisak Piyasaksiri on 3/20/2016 AD.
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.AlertDialogListener, HttpConnectionListener {

    private AQuery aq;

    private JSONParser json;
    private Dialog dialog = null;
    private LoginButton login_button;
    private CallbackManager callbackManager;

    private boolean LOGGER = false;

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        Global.startMainActivity(this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_sign_in);

        aq = new AQuery(this);
        init();
    }

    @Override
    protected void onResume() {

        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private void init() {

        setSupportActionBar((Toolbar) aq.id(R.id.login_toolbar).getView());
        getSupportActionBar().setTitle(getString(R.string.app_name));

        ((Toolbar) aq.id(R.id.login_toolbar).getView()).setTitleTextColor(Color.WHITE);

        login_button = (LoginButton) aq.id(R.id.login_btn_facebook).getView();
        login_button.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {

                        if (response.getError() != null) {

                            setProgressbarVisibility(false);

                        } else {

                            String id = me.optString("id");
                            String email = me.optString("email");
                            LoginManager.getInstance().logOut();

                            BeanLoginFBParam model = new BeanLoginFBParam();
                            model.setFb_id(String.valueOf(id));
                            model.setEmail(String.valueOf(email));
                            model.setNoti_token(AppSharedPreferences.getInstance(SignInActivity.this.getApplicationContext()).getGCMRegisterToken());

                            setProgressbarVisibility(true);
                            UtilHttpConnection.getInstance(SignInActivity.this).post(String.valueOf(APIs.LOGIN_FB.hashCode()), APIs.LOGIN_FB, APIs.getLoginFBAPIParam(model), SignInActivity.this);
                        }
                    }

                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                Global.printLog(LOGGER, "Facebook Canceled", "true");
            }

            @Override
            public void onError(FacebookException e) {

                Global.printLog(LOGGER, "Facebook Exception", String.valueOf(e.getMessage()));
            }
        });

        aq.id(R.id.login_btn_signin).clicked(this);
        aq.id(R.id.login_btn_forgetpassword).clicked(this);
        aq.id(R.id.login_btn_register).clicked(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.login_btn_signin:

                login();
                break;
            case R.id.login_btn_forgetpassword:
                break;
            case R.id.login_btn_register:

                Global.startRegisterActivity(this);
                finish();
                break;
            default:
                break;
        }
    }

    private void login() {

        if(Global.validateEmail(String.valueOf(aq.id(R.id.login_edit_username).getText()).trim()) &&
                !String.valueOf(aq.id(R.id.login_edit_password).getText()).trim().equalsIgnoreCase("")) {

            BeanLoginParam model = new BeanLoginParam();
            model.setEmail(String.valueOf(aq.id(R.id.login_edit_username).getText()).trim());
            model.setPassword(String.valueOf(aq.id(R.id.login_edit_password).getText()).trim());
            model.setNoti_token(AppSharedPreferences.getInstance(this.getApplicationContext()).getGCMRegisterToken());

            setProgressbarVisibility(true);
            UtilHttpConnection.getInstance(this).post(String.valueOf(APIs.LOGIN.hashCode()), APIs.LOGIN, APIs.getLoginAPIParam(model), this);

        } else {

            if(!Global.validateEmail(String.valueOf(aq.id(R.id.login_edit_username).getText()).trim()))
                aq.id(R.id.login_edit_username).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

            if(String.valueOf(aq.id(R.id.login_edit_password).getText()).trim().equalsIgnoreCase(""))
                aq.id(R.id.login_edit_password).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

            showAlertDialog(getString(R.string.alert_login_miss_input));
        }
    }

    private void setProgressbarVisibility(boolean enabled) {

        if(enabled)
            aq.id(R.id.login_progress_frame).visibility(View.VISIBLE);
        else
            aq.id(R.id.login_progress_frame).visibility(View.GONE);
    }

    private void showAlertDialog(String msg) {

        dialog = Global.showAlertDialog(this, msg, this);
        dialog.show();
    }

    @Override
    public void onAlertClick() {

        if(dialog != null)
            dialog.dismiss();

        dialog = null;
    }

    @Override
    public void onHttpSuccess(String key, String data, AjaxStatus status) {

        setProgressbarVisibility(false);
        Global.printLog(true, "SignInActivity", String.valueOf(data));

        if(status.getCode() == 200) {

            if(!data.trim().equalsIgnoreCase("")) {

                try {
                    json = new JSONParser(data);
                    HashMap<String, Object> resultObj = json.convertJson2HashMap();

                    if(String.valueOf(resultObj.get("code")).equalsIgnoreCase("200")) {

                        String candidate_id = String.valueOf(resultObj.get("candidate_id"));
                        if(!candidate_id.equalsIgnoreCase("") && !candidate_id.equalsIgnoreCase("null")) {

                            AppSharedPreferences.getInstance(SignInActivity.this).setCandidateID(candidate_id);
                            Global.startHomeActivity(this);
                            finish();

                        } else {
                            showAlertDialog(getString(R.string.alert_system_error));
                        }

                    } else {
                        showAlertDialog(String.valueOf(resultObj.get("desc")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showAlertDialog(getString(R.string.alert_system_error));
                }

            } else {
                showAlertDialog(getString(R.string.alert_system_error));
            }

        } else {
            showAlertDialog(status.getError());
        }
    }

    @Override
    public void onHttpError(String key, String error_message) {

        setProgressbarVisibility(false);
        showAlertDialog(error_message);
    }
}