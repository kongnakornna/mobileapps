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
public class AlertConfirmDialog extends RelativeLayout {

    private Context context;
    private String message = "";
    private String cancelLabel = "";
    private String okLabel = "";

    private View v = null;
    private AQuery aq = null;
    private LayoutInflater vi = null;
    private AlertConfirmDialogListener listener = null;

    public AlertConfirmDialog(Context context, String message, String cancelLabel, String okLabel, AlertConfirmDialogListener listener) {

        super(context);
        this.context = context;
        this.message = message;
        this.listener = listener;

        this.okLabel = okLabel;
        this.cancelLabel = cancelLabel;

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.view_dialogconfirm, null);
        v.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT ));
        addView(v);

        init();
    }

    private void init() {

        aq = new AQuery(v);
        aq.id(R.id.view_alert_text).text(this.message);

        if(!this.okLabel.equalsIgnoreCase(""))
            aq.id(R.id.view_alert_btn_ok).text(this.okLabel);

        if(!this.cancelLabel.equalsIgnoreCase(""))
            aq.id(R.id.view_alert_btn_cancel).text(this.cancelLabel);

        aq.id(R.id.view_alert_btn_ok).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onAlertConfirmAcceptClick();
            }
        });

        aq.id(R.id.view_alert_btn_cancel).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onAlertConfirmCancelClick();
            }
        });
    }

    public interface AlertConfirmDialogListener {
        void onAlertConfirmAcceptClick();
        void onAlertConfirmCancelClick();
    }
}
