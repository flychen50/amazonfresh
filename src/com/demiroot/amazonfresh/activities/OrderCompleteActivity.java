// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.demiroot.amazonfresh.AFBaseActivity;

public class OrderCompleteActivity extends AFBaseActivity
{

    public OrderCompleteActivity()
    {
        saveSettings = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                saveSettings(view);
            }

            final OrderCompleteActivity this$0;

            
            {
                this$0 = OrderCompleteActivity.this;
                super();
            }
        }
;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030019);
        ((TextView)findViewById(0x7f0b0080)).setOnClickListener(saveSettings);
        ((TextView)findViewById(0x7f0b0081)).setOnClickListener(saveSettings);
        loadActionBar(getString(0x7f070038));
    }

    public void saveSettings(View view)
    {
        if(0x7f0b0080 == view.getId()) goto _L2; else goto _L1
_L1:
        android.content.SharedPreferences.Editor editor = getSharedPreferences("SHARED_PREFS_KEY", 2).edit();
        editor.putBoolean("rate_app", false);
        editor.commit();
        if(view.getId() != 0x7f0b007e) goto _L4; else goto _L3
_L3:
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(0x7f0700c5))));
_L2:
        finish();
        return;
_L4:
        if(view.getId() == 0x7f0b007f)
        {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getString(0x7f0700c6)));
            try
            {
                startActivity(intent);
            }
            catch(Exception exception)
            {
                Toast.makeText(this, getString(0x7f0700cb), 0).show();
            }
        }
        if(true) goto _L2; else goto _L5
_L5:
    }

    public static final String RATE_APP = "rate_app";
    android.view.View.OnClickListener saveSettings;
}
