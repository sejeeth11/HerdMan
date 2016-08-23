package provab.herdman.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import provab.herdman.activity.LoginActivity;
import provab.herdman.activity.MyApplication;
import provab.herdman.activity.SelectCategoryActivity;

/**
 * Created by ptblr-1168 on 21/1/16.
 */
public class SessionManager {

    public static final String UUID="UID";
    public static final String PASSWORD="PASSWORD";
    public static final String GROUP="GROUPS";
    public static final String HERD="HERD";
    public static final String COMPANYCODE="CompanyCode";
    public static final String APPTYPE="AppType";
    public static final String UPDATEDBY="UpdatedBy";
    public static final String UPDATEDAT="UpdatedAt";
    public static final String USERCODE="UserCode";
    public static final String QRCODE="QRCode";
    public static String IS_LOGGED_IN = "isLoggedIn";



    public static int PRIVATE_MODE = 0;
    public static String PREF_NAME = "HerdMan";
    SharedPreferences pref;
    public Editor editor;
    Context context;
    static SessionManager sessionManager;


    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String uid, String password, String groop, String herd, String companyCode,
     String appType, String updatedBy, String updatedAt, String  userCode, boolean qrCode) {

        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(UUID, uid);
        editor.putString(PASSWORD, password);
        editor.putString(GROUP, groop);
        editor.putString(HERD, herd);
        editor.putString(COMPANYCODE, companyCode);
        editor.putString(APPTYPE, appType);
        editor.putString(UPDATEDBY, updatedBy);
        editor.putString(UPDATEDAT,updatedAt);
        editor.putString(USERCODE, userCode);
        editor.putBoolean(QRCODE, qrCode);
        editor.commit();
    }

    public static SessionManager getSessionInstance(){
        if(sessionManager==null)
            sessionManager=new SessionManager(MyApplication.getContext());
        return sessionManager;
    }

    public void checkLogin() {

        if (isLogedIn()) {

            Intent i = new Intent(context, SelectCategoryActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        } else {

            Intent login = new Intent(context, LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(login);
        }
    }

    public boolean isLogedIn() {

        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public String getPrefData(String KEY) {
        return pref.getString(KEY, "");
    }

    public void setPrefData(String key, String val) {
        editor.putString(key, val);
        editor.commit();

    }

    public void logOut() {

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        context.startActivity(i);

    }
}
