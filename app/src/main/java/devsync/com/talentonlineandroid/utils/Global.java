package devsync.com.talentonlineandroid.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import devsync.com.talentonlineandroid.HomeActivity;
import devsync.com.talentonlineandroid.MainActivity;
import devsync.com.talentonlineandroid.RegisterActivity;
import devsync.com.talentonlineandroid.SignInActivity;
import devsync.com.talentonlineandroid.view.AlertConfirmDialog;
import devsync.com.talentonlineandroid.view.AlertDialog;

/**
 * Created by Thanisak Piyasaksiri on 3/19/2016 AD.
 */
public class Global {

    public static int getScreenWidth(Context context) {

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    public static int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int convertDP2PX(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int convertPx2Dp(Context context, int px) {

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));

        return dp;
    }

    public static void printLog(boolean enabled, String tag, String msg) {

        if (enabled)
            Log.e(tag, msg);
    }

    public static boolean validateEmail(String email) {

        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean isNumberic(String digit) {

        try {
            Integer.parseInt(digit);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static Dialog showAlertDialog(Context context, String message, AlertDialog.AlertDialogListener listener) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(new AlertDialog(context, message, listener));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showAlertConfirmDialog(Context context, String message, AlertConfirmDialog.AlertConfirmDialogListener listener) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(new AlertConfirmDialog(context, message, "", "", listener));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void startMainActivity(Context context) {

        ((AppCompatActivity) context).startActivity(new Intent(context, MainActivity.class));
    }

    public static void startSignInActivity(Context context) {

        ((AppCompatActivity) context).startActivity(new Intent(context, SignInActivity.class));
    }

    public static void startRegisterActivity(Context context) {

        ((AppCompatActivity) context).startActivity(new Intent(context, RegisterActivity.class));
    }

    public static void startHomeActivity(Context context) {

        ((AppCompatActivity) context).startActivity(new Intent(context, HomeActivity.class));
    }

    public static String printKeyHash(Context context) {

        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                Log.e("Key Hash=", key);
            }

        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
