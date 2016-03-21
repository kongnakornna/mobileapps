package devsync.com.talentonlineandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.androidquery.AQuery;
import devsync.com.talentonlineandroid.shares.AppSharedPreferences;
import devsync.com.talentonlineandroid.utils.Global;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AQuery aq;

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
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);
        aq.id(R.id.main_btn_join).clicked(this);
        aq.id(R.id.main_btn_sign_in).clicked(this);

        if(!AppSharedPreferences.getInstance(this).getCandidateID().equalsIgnoreCase("")) {

            Global.startHomeActivity(this);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.main_btn_join:

                Global.startRegisterActivity(this);
                finish();
                break;
            case R.id.main_btn_sign_in:

                Global.startSignInActivity(this);
                finish();
                break;
            default:
                break;
        }
    }
}
