// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.support;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Locale;

public class DiagnosticSupport
{

    public DiagnosticSupport()
    {
    }

    public static String createDiagnosis(Activity activity, Exception exception)
    {
        StringBuilder stringbuilder;
        android.content.ContentResolver contentresolver;
        stringbuilder = new StringBuilder();
        stringbuilder.append((new StringBuilder("Application version: ")).append(getApplicationVersionString(activity)).append("\n").toString());
        stringbuilder.append((new StringBuilder("Device locale: ")).append(Locale.getDefault().toString()).append("\n\n").toString());
        stringbuilder.append((new StringBuilder("Android ID: ")).append(getAndroidId(activity, "n/a")).toString());
        stringbuilder.append("PHONE SPECS\n");
        stringbuilder.append((new StringBuilder("model: ")).append(Build.MODEL).append("\n").toString());
        stringbuilder.append((new StringBuilder("brand: ")).append(Build.BRAND).append("\n").toString());
        stringbuilder.append((new StringBuilder("product: ")).append(Build.PRODUCT).append("\n").toString());
        stringbuilder.append((new StringBuilder("device: ")).append(Build.DEVICE).append("\n\n").toString());
        stringbuilder.append("PLATFORM INFO\n");
        stringbuilder.append((new StringBuilder("Android ")).append(android.os.Build.VERSION.RELEASE).append(" ").append(Build.ID).append(" (build ").append(android.os.Build.VERSION.INCREMENTAL).append(")\n").toString());
        stringbuilder.append((new StringBuilder("build tags: ")).append(Build.TAGS).append("\n").toString());
        stringbuilder.append((new StringBuilder("build type: ")).append(Build.TYPE).append("\n\n").toString());
        stringbuilder.append("SYSTEM SETTINGS\n");
        contentresolver = activity.getContentResolver();
        if(android.provider.Settings.Secure.getInt(contentresolver, "wifi_on") != 0) goto _L2; else goto _L1
_L1:
        String s = "DATA";
_L3:
        stringbuilder.append((new StringBuilder("network mode: ")).append(s).append("\n").toString());
        stringbuilder.append((new StringBuilder("HTTP proxy: ")).append(android.provider.Settings.Secure.getString(contentresolver, "http_proxy")).append("\n\n").toString());
_L4:
        stringbuilder.append("STACK TRACE FOLLOWS\n\n");
        StringWriter stringwriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringwriter));
        stringbuilder.append(stringwriter.toString());
        return stringbuilder.toString();
_L2:
        s = "WIFI";
          goto _L3
        android.provider.Settings.SettingNotFoundException settingnotfoundexception;
        settingnotfoundexception;
        settingnotfoundexception.printStackTrace();
          goto _L4
    }

    public static String getAndroidId(Context context)
    {
        String s = android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
        if(s == null)
            s = android.provider.Settings.System.getString(context.getContentResolver(), "android_id");
        return s;
    }

    public static String getAndroidId(Context context, String s)
    {
        String s1 = getAndroidId(context);
        if(s1 == null)
            s1 = s;
        return s1;
    }

    public static String getApplicationVersionString(Context context)
    {
        String s1;
        PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        s1 = (new StringBuilder("v")).append(packageinfo.versionName).toString();
        String s = s1;
_L2:
        return s;
        Exception exception;
        exception;
        exception.printStackTrace();
        s = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static final int ANDROID_API_LEVEL;

    static 
    {
        int j = android/os/Build$VERSION.getField("SDK_INT").getInt(null);
        int i = j;
_L2:
        ANDROID_API_LEVEL = i;
        return;
        Exception exception;
        exception;
        i = Integer.parseInt(android.os.Build.VERSION.SDK);
        if(true) goto _L2; else goto _L1
_L1:
    }
}
