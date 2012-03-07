// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import com.demiroot.amazonfresh.AFApplication;
import com.demiroot.amazonfresh.AFBaseActivity;

public class SettingsActivity extends AFBaseActivity
{

    public SettingsActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        onCreate(bundle);
        setContentView(0x7f030024);
    }

    protected void onResume()
    {
        onResume();
        SharedPreferences sharedpreferences = getSharedPreferences("SHARED_PREFS_KEY", 1);
        boolean flag = sharedpreferences.getBoolean("enable_notices", true);
        ((CheckBox)findViewById(0x7f0b00b0)).setChecked(flag);
        boolean flag1 = sharedpreferences.getBoolean("combine_pp_pref", true);
        ((CheckBox)findViewById(0x7f0b00b1)).setChecked(flag1);
    }

    public void saveSettings(View view)
    {
        SharedPreferences sharedpreferences = getSharedPreferences("SHARED_PREFS_KEY", 2);
        CheckBox checkbox = (CheckBox)findViewById(0x7f0b00b1);
        CheckBox checkbox1 = (CheckBox)findViewById(0x7f0b00b0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("enable_notices", checkbox1.isChecked());
        editor.putBoolean("combine_pp_pref", checkbox.isChecked());
        editor.commit();
        Toast.makeText(this, getString(0x7f070062), 0).show();
    }

    public void setDefaultAddress(View view)
    {
        String s = ((AFApplication)getApplication()).getAppVersion();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse((new StringBuilder("https://fresh.amazon.com/m/splashDelivery?mobileApp=android&version=")).append(s).toString())));
        finish();
    }

    public void setDefaultPayment(View view)
    {
        String s = ((AFApplication)getApplication()).getAppVersion();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse((new StringBuilder("https://fresh.amazon.com/m/selectDefaultPayment?mobileApp=android&version=")).append(s).toString())));
        finish();
    }
}
