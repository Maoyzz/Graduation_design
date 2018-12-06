package myz.graduation_design.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    public static final String SESSION_KEY= "session_key";
    public static final String USERNAME_KEY= "username_key";
    public static final String REALNAME_KEY= "realname_key";
    public static final String USERID_KEY= "userid_key";
    public static final String FIRST_KEY= "first_run_key";
    public static final String LAST_LAT_KEY= "last_latitude";
    public static final String LAST_LON_KEY= "last_longitude";
    public static final String LAST_CITY= "last_city";
    public static final String USER_AVATAR= "user_avatar";
    public static final String IS_LOGIN = "is_login";
    public static final String USERPHONE_KEY= "userphone_key";
    public static final String ORGID_KEY= "orgid_key";
    public static final String USERPWD_KEY= "userpwd_key";
    public static final String PAYPWD_KEY= "paypwd_key";
    public static final String USERMONEY_KEY= "usermoney_key";
    public static final String USERLATITUDE_KEY= "latitude_key";
    public static final String USERLONGITUDE_KEY= "longitude_key";
    public static final String USERROLE_KEY= "userrole_key";


    public static final String PROJECT_NAME= "qiyunlogistics";


    public static void putString(Context mContext, String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context mContext, String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);
    }

    public static void putBool(Context mContext, String key, Boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static Boolean getBool(Context mContext, String key, Boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, value);
    }

    public static void putStingMulti(Context mContext, String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_MULTI_PROCESS);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getStingMulti(Context mContext, String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PROJECT_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(key, value);
    }
}
