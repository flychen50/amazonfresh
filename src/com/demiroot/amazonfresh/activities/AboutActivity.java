// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFApplication;
import com.demiroot.freshclient.AmazonFreshBase;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            StaticContentActivity

public class AboutActivity extends ListActivity
{

    public AboutActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        final String appVersion = ((AFApplication)getApplication()).getAppVersion();
        entries[0] = (new StringBuilder("Version ")).append(appVersion).toString();
        setListAdapter(new ArrayAdapter(this, 0x7f030000, entries));
        ListView listview = getListView();
        listview.setTextFilterEnabled(false);
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                if(i < 1 || i >= 4) goto _L2; else goto _L1
_L1:
                Intent intent2 = new Intent(AboutActivity.this, com/demiroot/amazonfresh/activities/StaticContentActivity);
                intent2.putExtra("content_key", i);
                startActivity(intent2);
_L4:
                return;
_L2:
                String s;
                if(i == 4)
                {
                    String s1 = Build.MODEL;
                    if(s1 == null)
                        s1 = Build.DEVICE;
                    String s2 = android.os.Build.VERSION.RELEASE;
                    String s3 = ((AFApplication)getApplication()).getAmazonFreshBase().getCustomerEmail();
                    Intent intent1 = new Intent("android.intent.action.SEND");
                    String as[] = new String[1];
                    as[0] = getString(0x7f0700a9);
                    intent1.putExtra("android.intent.extra.EMAIL", as);
                    intent1.putExtra("android.intent.extra.SUBJECT", getString(0x7f0700aa));
                    intent1.setType("plain/text");
                    AboutActivity aboutactivity = AboutActivity.this;
                    Object aobj[] = new Object[4];
                    aobj[0] = s1;
                    aobj[1] = s2;
                    aobj[2] = s3;
                    aobj[3] = appVersion;
                    intent1.putExtra("android.intent.extra.TEXT", aboutactivity.getString(0x7f0700ab, aobj));
                    startActivity(Intent.createChooser(intent1, "Send your email in:"));
                    continue; /* Loop/switch isn't completed */
                }
                if(i != 5 && i != 6)
                    continue; /* Loop/switch isn't completed */
                s = "";
                if(i != 5)
                    break; /* Loop/switch isn't completed */
                s = getString(0x7f0700c5);
_L6:
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(s));
                try
                {
                    startActivity(intent);
                }
                catch(Exception exception)
                {
                    Toast.makeText(AboutActivity.this, getString(0x7f0700cb), 0).show();
                }
                if(true) goto _L4; else goto _L3
_L3:
                if(i != 6) goto _L6; else goto _L5
_L5:
                s = getString(0x7f0700c6);
                  goto _L6
            }

            final AboutActivity this$0;
            private final String val$appVersion;

            
            {
                this$0 = AboutActivity.this;
                appVersion = s;
                super();
            }
        }
);
    }

    public static final int ABOUT = 2;
    public static final String CONTENT_KEY = "content_key";
    public static final int FAQ = 3;
    public static final int LEGAL = 1;
    private static String entries[];

    static 
    {
        String as[] = new String[7];
        as[0] = "Version: potato";
        as[1] = "Legal";
        as[2] = "About";
        as[3] = "FAQ";
        as[4] = "Send Feedback";
        as[5] = "Rate app in Amazon Appstore";
        as[6] = "Rate app in Android Market";
        entries = as;
    }
}
