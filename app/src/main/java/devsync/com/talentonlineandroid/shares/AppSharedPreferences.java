package devsync.com.talentonlineandroid.shares;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class AppSharedPreferences {

    private final Context ctx;
    static private AppSharedPreferences instance;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static final String STATE_SETTING_REMEMBER_APP_GLOBAL = "devsync.com.talentonlineandroid.remember.app.global";
    public static final String GCM_REGISTER_TOKEN = "devsync.com.talentonlineandroid.shares.gcmtoken";
    public static final String CANDIDATE_ID = "candidate_id";

    static public AppSharedPreferences getInstance(Context ctx) {

        if(instance == null)
            instance = new AppSharedPreferences(ctx);

        return instance;
    }

    private AppSharedPreferences(Context ctx) {

        this.ctx = ctx;
        sharedPref = this.ctx.getSharedPreferences(STATE_SETTING_REMEMBER_APP_GLOBAL, Context.MODE_PRIVATE);
    }

    public void setGCMRegisterToken(String token) {

        editor = sharedPref.edit();
        editor.putString(GCM_REGISTER_TOKEN, token);
        editor.commit();
    }

    public String getGCMRegisterToken() {

        return sharedPref.getString(GCM_REGISTER_TOKEN, "");
    }

    public void removeGCMRegisterToken() {

        editor = sharedPref.edit();
        editor.remove(GCM_REGISTER_TOKEN);
        editor.commit();
    }

    public void setCandidateID(String token) {

        editor = sharedPref.edit();
        editor.putString(CANDIDATE_ID, token);
        editor.commit();
    }

    public String getCandidateID() {

        return sharedPref.getString(CANDIDATE_ID, "");
    }

    public void removeCandidateID() {

        editor = sharedPref.edit();
        editor.remove(CANDIDATE_ID);
        editor.commit();
    }
}
