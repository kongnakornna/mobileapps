package devsync.com.talentonlineandroid.model;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class BeanLoginFBParam {

    private String fb_id = "";
    private String email = "";
    private String noti_token = "";


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getNoti_token() {
        return noti_token;
    }

    public void setNoti_token(String noti_token) {
        this.noti_token = noti_token;
    }
}
