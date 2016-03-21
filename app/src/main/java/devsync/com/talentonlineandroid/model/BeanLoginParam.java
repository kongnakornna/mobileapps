package devsync.com.talentonlineandroid.model;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class BeanLoginParam {

    private String email = "";
    private String password = "";
    private String noti_token = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
