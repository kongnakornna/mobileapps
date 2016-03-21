package devsync.com.talentonlineandroid.listener;

import com.androidquery.callback.AjaxStatus;

/**
 * Created by Thanisak Piyasaksiri on 9/16/15 AD.
 */
public interface HttpConnectionListener {

    void onHttpSuccess(String key, String data, AjaxStatus status);
    void onHttpError(String key, String error_message);
}
