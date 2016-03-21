package devsync.com.talentonlineandroid.model;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class BeanRegisterFBParam {

    private String fb_id = "";
    private String firstname = "";
    private String lastname = "";
    private String mobile = "";
    private String email = "";
    private String noti_token = "";

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoti_token() {
        return noti_token;
    }

    public void setNoti_token(String noti_token) {
        this.noti_token = noti_token;
    }
}
