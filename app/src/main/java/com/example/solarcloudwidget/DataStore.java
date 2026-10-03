package com.example.solarcloudwidget;

import android.content.Context;
import android.content.SharedPreferences;

public final class DataStore {
    private static final String P = "solar_data";
    private static SharedPreferences p(Context c) { return c.getSharedPreferences(P, Context.MODE_PRIVATE); }
    public static void put(Context c, String key, String value) { p(c).edit().putString(key, value).apply(); }
    public static String get(Context c, String key, String def) { return p(c).getString(key, def); }
    public static void clear(Context c) { p(c).edit().clear().apply(); }
}
