package devsync.com.talentonlineandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.androidquery.AQuery;
import devsync.com.talentonlineandroid.R;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class AlertDialog extends RelativeLayout {

    private Context context;
    private String message = "";

    private View v = null;
    private AQuery aq = null;
    private LayoutInflater vi = null;
    private AlertDialogListener listener = null;

    public AlertDialog(Context context, String message, AlertDialogListener listener) {

        super(context);
        this.context = context;
        this.message = message;
        this.listener = listener;

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.view_alert, null);
        v.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ));
        addView(v);

        init();
    }

    private void init() {

        aq = new AQuery(v);
        aq.id(R.id.view_alert_text).text(this.message);
        aq.id(R.id.view_alert_btn_ok).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onAlertClick();
            }
        });
    }

    public interface AlertDialogListener {
        void onAlertClick();
    }
}
