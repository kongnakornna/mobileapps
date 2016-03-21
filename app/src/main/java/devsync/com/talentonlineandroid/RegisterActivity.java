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
import devsync.com.talentonlineandroid.model.BeanRegisterFBParam;
import devsync.com.talentonlineandroid.model.BeanRegisterParam;
import devsync.com.talentonlineandroid.shares.AppSharedPreferences;
import devsync.com.talentonlineandroid.utils.APIs;
import devsync.com.talentonlineandroid.utils.Global;
import devsync.com.talentonlineandroid.utils.UtilHttpConnection;
import devsync.com.talentonlineandroid.view.AlertDialog;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 * Modified by Thanisak Piyasaksiri on 3/21/2016 AD.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.AlertDialogListener, HttpConnectionListener {

    private AQuery aq;
    private String FB_ID = "";

    private JSONParser json;
    private Dialog dialog = null;
    private LoginButton register_btn_facebook;
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
        setContentView(R.layout.activity_register);

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

        setSupportActionBar((Toolbar) aq.id(R.id.register_toolbar).getView());
        getSupportActionBar().setTitle(getString(R.string.app_name));

        ((Toolbar) aq.id(R.id.register_toolbar).getView()).setTitleTextColor(Color.WHITE);

        register_btn_facebook = (LoginButton) aq.id(R.id.register_btn_facebook).getView();
        register_btn_facebook.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        register_btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {

                        if (response.getError() != null) {

                            setProgressbarVisibility(false);

                        } else {

                            FB_ID = me.optString("id");
                            String email = me.optString("email");
                            String first_name = me.optString("first_name");
                            String last_name = me.optString("last_name");
                            LoginManager.getInstance().logOut();

                            aq.id(R.id.register_edit_firstname).text(first_name);
                            aq.id(R.id.register_edit_lastname).text(last_name);
                            aq.id(R.id.register_edit_email).text(email);

                            aq.id(R.id.register_edit_email).enabled(false);
                            aq.id(R.id.register_edit_password).visibility(View.GONE);
                            aq.id(R.id.register_text_or).visibility(View.GONE);
                            register_btn_facebook.setVisibility(View.GONE);

                            //setProgressbarVisibility(true);
                            //LoginHelper.getInstance(LoginActivity.this).getLoginFacebook(LoginActivity.this, id, LoginActivity.this);
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

        aq.id(R.id.register_btn_signin).clicked(this);
        aq.id(R.id.register_btn_register).clicked(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.register_btn_register:

                register();
                break;
            case R.id.register_btn_signin:

                Global.startSignInActivity(this);
                finish();
                break;
            default:
                break;
        }
    }

    private void register() {

        if(FB_ID.equalsIgnoreCase("")){

            if(!String.valueOf(aq.id(R.id.register_edit_email).getText()).trim().equalsIgnoreCase("") &&
                    !String.valueOf(aq.id(R.id.register_edit_firstname).getText()).trim().equalsIgnoreCase("") &&
                        !String.valueOf(aq.id(R.id.register_edit_lastname).getText()).trim().equalsIgnoreCase("") &&
                            !String.valueOf(aq.id(R.id.register_edit_mobile).getText()).trim().equalsIgnoreCase("") &&
                                !String.valueOf(aq.id(R.id.register_edit_password).getText()).trim().equalsIgnoreCase("")) {

                BeanRegisterParam model = new BeanRegisterParam();
                model.setEmail(String.valueOf(aq.id(R.id.register_edit_email).getText()));
                model.setFirstname(String.valueOf(aq.id(R.id.register_edit_firstname).getText()));
                model.setLastname(String.valueOf(aq.id(R.id.register_edit_lastname).getText()));
                model.setMobile(String.valueOf(aq.id(R.id.register_edit_mobile).getText()));
                model.setPassword(String.valueOf(aq.id(R.id.register_edit_password).getText()));
                model.setNoti_token(AppSharedPreferences.getInstance(this.getApplicationContext()).getGCMRegisterToken());

                setProgressbarVisibility(true);
                String api = APIs.getRegisterAPI(model);
                Global.printLog(true, "getRegisterAPI", String.valueOf(api));
                UtilHttpConnection.getInstance(this).get(String.valueOf(api.hashCode()), api, this);
                //UtilHttpConnection.getInstance(this).post(String.valueOf(APIs.REGISTER.hashCode()), APIs.REGISTER, APIs.getRegisterParam(model), this);

            } else {

                if(!Global.validateEmail(String.valueOf(aq.id(R.id.register_edit_email).getText()).trim()))
                    aq.id(R.id.register_edit_email).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_firstname).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_firstname).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_lastname).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_lastname).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_mobile).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_mobile).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_password).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_password).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                showAlertDialog(getString(R.string.alert_miss_input));
            }

        } else {

            if(!String.valueOf(aq.id(R.id.register_edit_email).getText()).trim().equalsIgnoreCase("") &&
                    !String.valueOf(aq.id(R.id.register_edit_firstname).getText()).trim().equalsIgnoreCase("") &&
                        !String.valueOf(aq.id(R.id.register_edit_lastname).getText()).trim().equalsIgnoreCase("") &&
                            !String.valueOf(aq.id(R.id.register_edit_mobile).getText()).trim().equalsIgnoreCase("")) {

                BeanRegisterFBParam model = new BeanRegisterFBParam();
                model.setFb_id(FB_ID);
                model.setEmail(String.valueOf(aq.id(R.id.register_edit_email).getText()));
                model.setFirstname(String.valueOf(aq.id(R.id.register_edit_firstname).getText()));
                model.setLastname(String.valueOf(aq.id(R.id.register_edit_lastname).getText()));
                model.setMobile(String.valueOf(aq.id(R.id.register_edit_mobile).getText()));
                model.setNoti_token(AppSharedPreferences.getInstance(this.getApplicationContext()).getGCMRegisterToken());

                setProgressbarVisibility(true);
                String api = APIs.getRegisterFacebookAPI(model);
                Global.printLog(true, "getRegisterFacebookAPI", String.valueOf(api));
                UtilHttpConnection.getInstance(this).get(String.valueOf(api.hashCode()), api, this);
                //UtilHttpConnection.getInstance(this).post(String.valueOf(APIs.REGISTER_FB.hashCode()), APIs.REGISTER_FB, APIs.getRegisterFBParam(model), this);

            } else {

                if(String.valueOf(aq.id(R.id.register_edit_firstname).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_firstname).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_lastname).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_lastname).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                if(String.valueOf(aq.id(R.id.register_edit_mobile).getText()).trim().equalsIgnoreCase(""))
                    aq.id(R.id.register_edit_mobile).getEditText().setHintTextColor(ContextCompat.getColor(this, R.color.red));

                showAlertDialog(getString(R.string.alert_miss_input));
            }

        }
    }

    private void setProgressbarVisibility(boolean enabled) {

        if(enabled)
            aq.id(R.id.register_progress_frame).visibility(View.VISIBLE);
        else
            aq.id(R.id.register_progress_frame).visibility(View.GONE);
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
        data = data.replace("\n", "").replace("\r", "");

        if(status.getCode() == 200) {

            if(!data.trim().equalsIgnoreCase("")) {

                try {
                    json = new JSONParser(data);
                    HashMap<String, Object> resultObj = json.convertJson2HashMap();

                    if(String.valueOf(resultObj.get("code")).equalsIgnoreCase("200")) {

                        String candidate_id = String.valueOf(resultObj.get("candidate_id"));
                        if(!candidate_id.equalsIgnoreCase("") && !candidate_id.equalsIgnoreCase("null")) {

                            AppSharedPreferences.getInstance(RegisterActivity.this).setCandidateID(candidate_id);
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