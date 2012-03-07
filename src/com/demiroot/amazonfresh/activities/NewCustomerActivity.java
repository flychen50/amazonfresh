// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.demiroot.amazonfresh.AFApplication;
import com.demiroot.amazonfresh.AFBaseActivity;

public class NewCustomerActivity extends AFBaseActivity
{

    public NewCustomerActivity()
    {
    }

    public void newCustomerFlow(View view)
    {
        String s = ((AFApplication)getApplication()).getAppVersion();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse((new StringBuilder("https://fresh.amazon.com/m/splashDelivery?mobileApp=android&version=")).append(s).toString())));
        finish();
    }

    public void onCreate(Bundle bundle)
    {
        requiresLogin = false;
        super.onCreate(bundle);
        setContentView(0x7f030015);
    }

    WebView mWebView;
}
