package com.hrd.tolapheng.knongdai_app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hrd.tolapheng.knongdai_app.model.User;

/**
 * Created by tolapheng on 12/27/16.
 */
public class Session {

    /* KEY */
    private static final String USER_ID = "USER_ID";
    private static final String STATUS = "STATUS";
    private static final String USERNAME = "USERNAME";
    private static final String EMAIL = "EMAIL";
    private static final String USER_HASH = "USER_HASH";
    private static final String GENDER = "GENDER";
    private static final String REGISTERED_DATE = "REGISTERED_DATE";
    private static final String USER_IMAGE_URL = "USER_IMAGE_URL";
    private static final String PHONE_NUMBER = "PHONENUMBER";
    private static final String POINT = "POINT";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String DATE_OF_BIRTH = "DARE_OF_BIRTH";

    /* Preference */
    private static String sharedPrefName = "userSession";
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor sharedPrefEditor;
    private static Context mContext;

    public static int userId;
    public static String username;
    public static String email;
    public static String gender;
    public static String dateOfBirth;
    public static String phoneNumber;
    public static String password;
    public static int point;
    public static String userImageUrl;
    public static String registeredDate;
    public static String userHash;
    public static boolean isLogin;

    public static void setContext(Context ctx) {
        mContext = ctx;
    }

    // Save User info to SharedPreferenceFile
    public static void saveUserSession(Context context, User user) {

        mContext = context;

        sharedPref = mContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPref.edit();
        sharedPrefEditor.putInt(USER_ID, user.getUserId());
        sharedPrefEditor.putString(STATUS, user.getGender());
        sharedPrefEditor.putString(EMAIL, user.getEmail());
        sharedPrefEditor.putString(USERNAME, user.getUsername());
        sharedPrefEditor.putString(USER_HASH, user.getUserHash());
        sharedPrefEditor.putString(GENDER, user.getGender());
        sharedPrefEditor.putString(REGISTERED_DATE, user.getRegisteredDate());
        sharedPrefEditor.putString(USER_IMAGE_URL, user.getUserImageUrl());
        sharedPrefEditor.putString(PHONE_NUMBER, user.getPhoneNumber());
        sharedPrefEditor.putString(DATE_OF_BIRTH, user.getDateOfBirth());
        sharedPrefEditor.putInt(POINT, user.getPoint());
        sharedPrefEditor.putBoolean(IS_LOGIN, true);
        sharedPrefEditor.apply();
    }

    // Read User info to SharedPreferenceFile
    public static void readUserSession(Context context) {
        mContext = context;
        sharedPref = mContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        userId = sharedPref.getInt(USER_ID, 0);
        username = sharedPref.getString(USERNAME, null);
        email = sharedPref.getString(GENDER, null);
        gender = sharedPref.getString(EMAIL, null);
        dateOfBirth = sharedPref.getString(DATE_OF_BIRTH, null);
        phoneNumber = sharedPref.getString(PHONE_NUMBER, null);
        point = sharedPref.getInt(POINT, 0);
        userImageUrl = sharedPref.getString(USER_IMAGE_URL, null);
        registeredDate = sharedPref.getString(DATE_OF_BIRTH, null);
        userHash = sharedPref.getString(USER_HASH, null);
        isLogin = sharedPref.getBoolean(IS_LOGIN, false);
    }

    // Clear Session or clean all data from SharedPreferenceFile
    public static void clearSession() {
        mContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).edit().clear().apply();
    }




}
