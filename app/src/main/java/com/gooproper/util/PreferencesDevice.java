package com.gooproper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesDevice {
    static final String KeyToken = "Token";

    public static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setKeyToken(Context context, String Token) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KeyToken, Token);
        editor.apply();
    }

    public static String getKeyToken(Context context) {
        return getSharedPreference(context).getString(KeyToken, "");
    }
}
