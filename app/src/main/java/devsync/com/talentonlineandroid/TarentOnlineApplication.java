package devsync.com.talentonlineandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.onesignal.OneSignal;
import org.json.JSONObject;
import devsync.com.talentonlineandroid.shares.AppSharedPreferences;
import devsync.com.talentonlineandroid.utils.UtilGenerator;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class TarentOnlineApplication extends MultiDexApplication {

    private NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {

        super.onCreate();
        OneSignal.startInit(this).init();
        Fabric.with(this, new Crashlytics());
        OneSignal.startInit(this).setNotificationOpenedHandler(new NotificationOpenedHandler()).init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {

                if (userId != null) {
                    AppSharedPreferences.getInstance(TarentOnlineApplication.this.getApplicationContext()).setGCMRegisterToken(userId);
                }
            }
        });


    }

    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.e("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                    //Log.e("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                    //Log.e("message", String.valueOf(message));
                    if(!AppSharedPreferences.getInstance(TarentOnlineApplication.this.getApplicationContext()).getCandidateID().equalsIgnoreCase(""))
                        showNotification(additionalData.getString("title"), String.valueOf(message));
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void showNotification(String title, String message) {

        Intent intent;
        Notification notification;
        PendingIntent contentIntent;
        NotificationCompat.Builder mBuilder;

        intent = new Intent(this, HomeActivity.class);
        contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(String.valueOf(message));
        mBuilder.setContentIntent(contentIntent);

        notification = mBuilder.build();
        notification.flags = PendingIntent.FLAG_UPDATE_CURRENT;
        notification.flags += Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        mNotificationManager.notify(UtilGenerator.genTransactionId().hashCode(), notification);
    }
}
