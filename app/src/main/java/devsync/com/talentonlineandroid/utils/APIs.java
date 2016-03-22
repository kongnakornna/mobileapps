package devsync.com.talentonlineandroid.utils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import devsync.com.talentonlineandroid.model.BeanLoginFBParam;
import devsync.com.talentonlineandroid.model.BeanLoginParam;
import devsync.com.talentonlineandroid.model.BeanRegisterFBParam;
import devsync.com.talentonlineandroid.model.BeanRegisterParam;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 * Modified by Thanisak Piyasaksiri on 3/22/2016 AD.
 */
public class APIs {

    public static final String LOGIN = "http://www.talentonline.com/mobile/check_login";
    public static final String LOGIN_FB = "http://www.talentonline.com/mobile/check_login_fackbook";
    public static final String REGISTER = "http://www.talentonline.com/mobile/register_login";
    public static final String REGISTER_FB = "http://www.talentonline.com/mobile/register_facebookid";

    public static final String WEBVIEW_URL_HOME = "http://www.talentonline.com/";
    public static final String WEBVIEW_URL_JOBMATCH = "http://www.talentonline.com/mobile/matchjob/";
    public static final String WEBVIEW_URL_ME = "http://www.talentonline.com/mobile/me/";
    public static final String WEBVIEW_URL_MESSAGE = "http://www.talentonline.com/mobile/message/";
    public static final String WEBVIEW_URL_SEARCH = "http://www.talentonline.com/mobile/search/";

    public static String getLoginAPI(BeanLoginParam model) {

        String email = "";

        try {
            email = URLEncoder.encode(model.getEmail(), "UTF-8");
        } catch(Exception e) {
            email = "";
        }

        StringBuffer api = new StringBuffer();
        api.append(LOGIN);
        api.append("?");
        api.append("email=" + email + "&");
        api.append("password=" + model.getPassword() + "&");
        api.append("noti_token=" + model.getNoti_token());

        return api.toString();
    }

    public static String getLoginFacebookAPI(BeanLoginFBParam model) {

        String email = "";

        try {
            email = URLEncoder.encode(model.getEmail(), "UTF-8");
        } catch(Exception e) {
            email = "";
        }

        StringBuffer api = new StringBuffer();
        api.append(LOGIN_FB);
        api.append("?");
        api.append("id_fackbook=" + model.getFb_id() + "&");
        api.append("email_fackbook=" + email + "&");
        api.append("noti_token=" + model.getNoti_token());

        return api.toString();
    }

    public static String getRegisterAPI(BeanRegisterParam model) {

        String email = "";

        try {
            email = URLEncoder.encode(model.getEmail(), "UTF-8");
        } catch(Exception e) {
            email = "";
        }

        StringBuffer api = new StringBuffer();
        api.append(REGISTER);
        api.append("?");
        api.append("firstname_register=" + model.getFirstname() + "&");
        api.append("lastname_register=" + model.getLastname() + "&");
        api.append("mobile=" + model.getMobile() + "&");
        api.append("email=" + email + "&");
        api.append("password=" + model.getPassword() + "&");
        api.append("noti_token=" + model.getNoti_token());

        return api.toString();
    }

    public static String getRegisterFacebookAPI(BeanRegisterFBParam model) {

        String email = "";

        try {
            email = URLEncoder.encode(model.getEmail(), "UTF-8");
        } catch(Exception e) {
            email = "";
        }

        StringBuffer api = new StringBuffer();
        api.append(REGISTER_FB);
        api.append("?");
        api.append("facebook_user_id=" + model.getFb_id() + "&");
        api.append("firstname_register=" + model.getFirstname() + "&");
        api.append("lastname_register=" + model.getLastname() + "&");
        api.append("mobile=" + model.getMobile() + "&");
        api.append("email=" + email + "&");
        api.append("noti_token=" + model.getNoti_token());

        return api.toString();
    }

    public static Map<String, Object> getLoginAPIParam(BeanLoginParam model) {

        Map<String, Object> obj = null;

        obj = new HashMap<String, Object>();
        obj.put("email", model.getEmail());
        obj.put("password", model.getPassword());
        obj.put("noti_token", model.getNoti_token());

        return obj;
    }

    public static Map<String, Object> getLoginFBAPIParam(BeanLoginFBParam model) {

        Map<String, Object> obj = null;

        obj = new HashMap<String, Object>();
        obj.put("id_fackbook", model.getFb_id());
        obj.put("email_fackbook", model.getEmail());
        obj.put("noti_token", model.getNoti_token());

        return obj;
    }

    public static Map<String, Object> getRegisterParam(BeanRegisterParam model) {

        Map<String, Object> obj = null;

        obj = new HashMap<String, Object>();
        obj.put("firstname_register", model.getFirstname());
        obj.put("lastname_register", model.getLastname());
        obj.put("mobile", model.getMobile());
        obj.put("email", model.getEmail());
        obj.put("password", model.getPassword());
        obj.put("noti_token", model.getNoti_token());

        return obj;
    }

    public static Map<String, Object> getRegisterFBParam(BeanRegisterFBParam model) {

        Map<String, Object> obj = null;

        obj = new HashMap<String, Object>();
        obj.put("facebook_user_id", model.getFb_id());
        obj.put("firstname_register", model.getFirstname());
        obj.put("lastname_register", model.getLastname());
        obj.put("mobile", model.getMobile());
        obj.put("email", model.getEmail());
        obj.put("noti_token", model.getNoti_token());

        return obj;
    }
}
