// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.freshclient.AmazonFreshBase;

public class StaticContentActivity extends AFBaseActivity
{

    public StaticContentActivity()
    {
        content = null;
        loadContentRunnable = new Runnable() {

            public void run()
            {
                loadContent();
            }

            final StaticContentActivity this$0;

            
            {
                this$0 = StaticContentActivity.this;
                Object();
            }
        }
;
    }

    private void loadContent()
    {
        ((WebView)findViewById(0x7f0b00b6)).loadData(content, "text/html", "UTF-8");
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030029);
        Bundle bundle1 = getIntent().getExtras();
        if(bundle1 == null)
        {
            finish();
        } else
        {
            final int key = bundle1.getInt("content_key");
            if(key != 2 && key != 3 && key != 1)
                finish();
            else
                AsyncRequest.buildRequest(this, getHandler(), new Runnable() {

                    public void run()
                    {
                        if(key != 2) goto _L2; else goto _L1
_L1:
                        content = getAmazonFreshBase().about();
_L4:
                        return;
_L2:
                        if(key == 3)
                            content = getAmazonFreshBase().faq();
                        else
                        if(key == 1)
                            content = getAmazonFreshBase().legal();
                        if(true) goto _L4; else goto _L3
_L3:
                    }

                    final StaticContentActivity this$0;
                    private final int val$key;

            
            {
                this$0 = StaticContentActivity.this;
                key = i;
                Object();
            }
                }
).setSuccessAction(loadContentRunnable).setWaitMessage(getString(0x7f070056)).execute();
        }
    }

    private String content;
    private Runnable loadContentRunnable;


}
