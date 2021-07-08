package sigma.telkomgroup.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import sigma.telkomgroup.connection.ConstantUtil;
import sigma.telkomgroup.controller.ControllerLogin;
import sigma.telkomgroup.controller.SplashScreen;

/**
 * Created by biting on 30/03/16.
 */
public class SessionManager {

    private SharedPreferences preferences;
    private Editor editor;
    private Context cont;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "RapimPref";
    public static final String KEY_USERNAME = "username";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // AlertDialog
    public static final String ALERT_DLG = "MyPrefsFile";


    // User name (make variable public to access from outside)
    public static final String KEY_API_KEY = "api_key";

    // Constructor
    public SessionManager(Context context){
        cont = context;
        preferences = cont.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();

    }

    public void createLogoutSession(){
        editor.putBoolean(IS_LOGIN, false);
    }

    public void createLoginSession(String id, String username, String pass, String name_user, String cfu,
                                   String pos, String qrcode, String api_key){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(ConstantUtil.LOGIN.TAG_ID, id);

        // Storing name in pref
        editor.putString(ConstantUtil.LOGIN.TAG_NAME, name_user);

        // Storing username in pref
        editor.putString(ConstantUtil.LOGIN.TAG_USER_NAME, username);

        // Storing password in pref
        editor.putString(ConstantUtil.LOGIN.TAG_PASS, pass);

        // Storing CFU in pref
        editor.putString(ConstantUtil.LOGIN.TAG_CFU, cfu);

        // Storing position in pref
        editor.putString(ConstantUtil.LOGIN.TAG_POSITION, pos);

        // Storing qrcode in pref
        editor.putString(ConstantUtil.LOGIN.TAG_QRCODE, qrcode);

        // Storing api in pref
        editor.putString(KEY_API_KEY, api_key);

        // commit changes
        editor.commit();
    }

    public void updatePassword(String password){
        editor.putString(ConstantUtil.LOGIN.TAG_PASS,password);
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(cont, SplashScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        cont.startActivity(i);
    }

    public void logoutUpdatePassword(){
        editor.putBoolean(IS_LOGIN,false);
        System.out.println("logout update is login?? : " + isLoggedIn());
        // After logout redirect user to Loing Activity
        Intent i = new Intent(cont, SplashScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        cont.startActivity(i);
    }

    public void logoutUpdate(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(ConstantUtil.LOGIN.TAG_ID, preferences.getString(ConstantUtil.LOGIN.TAG_ID, null));
//        Log.d("ini log session getUser", preferences.getString(ConstantUtil.LOGIN.TAG_ID, null));

        user.put(ConstantUtil.LOGIN.TAG_USER_NAME, preferences.getString(ConstantUtil.LOGIN.TAG_USER_NAME, null));

        user.put(ConstantUtil.LOGIN.TAG_NAME, preferences.getString(ConstantUtil.LOGIN.TAG_NAME, null));

        user.put(ConstantUtil.LOGIN.TAG_PASS, preferences.getString(ConstantUtil.LOGIN.TAG_PASS, null));

        user.put(ConstantUtil.LOGIN.TAG_CFU, preferences.getString(ConstantUtil.LOGIN.TAG_CFU, null));

        user.put(ConstantUtil.LOGIN.TAG_POSITION, preferences.getString(ConstantUtil.LOGIN.TAG_POSITION, null));

        user.put(ConstantUtil.LOGIN.TAG_QRCODE, preferences.getString(ConstantUtil.LOGIN.TAG_QRCODE, null));

        user.put(KEY_API_KEY, preferences.getString(KEY_API_KEY, null));

        // return user
        return user;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(cont, ControllerLogin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            cont.startActivity(i);
        }
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void createFeedbackSession(String tema){
        editor.putString("tema_feedback", tema);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getTema(){
        HashMap<String, String> thema = new HashMap<String, String>();
        // user name
        thema.put("tema_feedback", preferences.getString("tema_feedback", null));
//        Log.d("ini log session getUser", preferences.getString(ConstantUtil.LOGIN.TAG_ID, null));
        return thema;
    }
}
