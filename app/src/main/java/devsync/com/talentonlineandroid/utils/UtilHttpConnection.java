package devsync.com.talentonlineandroid.utils;

import android.content.Context;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import java.util.Map;
import devsync.com.talentonlineandroid.listener.HttpConnectionListener;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class UtilHttpConnection {

    private AQuery aq;
    private final Context context;
    static private UtilHttpConnection instance;

    static public UtilHttpConnection getInstance(Context context) {

        if(instance == null)
            instance = new UtilHttpConnection(context);

        return instance;
    }

    private UtilHttpConnection(Context context) {

        this.context = context;
        this.aq = new AQuery(context);
    }

    public void get( final String key, final String url, final HttpConnectionListener listener ) {

        aq.ajax(url, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String data, AjaxStatus status) {

                if (status.getCode() == 200) {

                    if( listener != null )
                        listener.onHttpSuccess( key, data, status );

                } else {

                    if( listener != null )
                        listener.onHttpError( key, status.getMessage() );
                }
            }
        });
    }

    public void post( final String key, final String url, final Map<String, Object> params, final HttpConnectionListener listener ) {

        aq.ajax(url, params, String.class, new AjaxCallback<String>() {
            @Override
            public void callback(String url, String data, AjaxStatus status) {

                if (status.getCode() == 200) {

                    if( listener != null )
                        listener.onHttpSuccess( key, data, status );

                } else {

                    if( listener != null )
                        listener.onHttpError( key, status.getMessage() );
                }
            }
        });
    }
}