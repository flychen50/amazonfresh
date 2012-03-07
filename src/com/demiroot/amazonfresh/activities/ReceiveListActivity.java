// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.freshclient.*;
import java.net.URLDecoder;
import java.util.*;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            QuicklistActivity, SearchUsingWidgetActivity

public class ReceiveListActivity extends AFBaseActivity
{

    public ReceiveListActivity()
    {
    }

    private void loadShoppinglist(String s, List list)
    {
        if(s != null)
            setActionBarTitle(s);
        ((ListView)findViewById(0x102000a)).setAdapter(new ArrayAdapter(this, 0x7f03002b, list));
    }

    public void onAddToQuicklist(View view)
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                ArrayList arraylist = new ArrayList();
                StringBuilder stringbuilder = new StringBuilder();
                int i = items.size() - 1;
                do
                {
                    if(i < 0)
                    {
                        FreshClientProxy freshclientproxy = getAmazonFreshBase().getClientProxy();
                        Object aobj[] = new Object[1];
                        aobj[0] = stringbuilder.toString();
                        freshclientproxy.post("/quickList/add", new APIArgs(aobj));
                        return;
                    }
                    String s = ((String)items.get(i)).trim().toLowerCase();
                    if(s.length() > 0 && !s.startsWith("_") && !arraylist.contains(s))
                    {
                        arraylist.add(s);
                        stringbuilder.append((new StringBuilder(",")).append(s).toString());
                    }
                    i--;
                } while(true);
            }

            final ReceiveListActivity this$0;

            
            {
                this$0 = ReceiveListActivity.this;
                super();
            }
        }
).start();
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/QuicklistActivity));
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030020);
        loadActionBar(getString(0x7f0700bd));
    }

    protected void onResume()
    {
        Intent intent;
        String s;
        super.onResume();
        intent = getIntent();
        items = new ArrayList();
        s = "";
        ArrayList arraylist;
        s = intent.getStringExtra("android.intent.extra.TITLE");
        arraylist = new ArrayList();
        if(!intent.getAction().equals("android.intent.action.VIEW") || intent.getData() == null) goto _L2; else goto _L1
_L1:
        String s4 = intent.getData().getEncodedQuery();
        if(s4 != null && s4.trim().length() > 0)
            arraylist = new ArrayList(Arrays.asList(URLDecoder.decode(s4).split("\\|")));
_L6:
        Iterator iterator = arraylist.iterator();
_L7:
        boolean flag = iterator.hasNext();
        if(flag) goto _L4; else goto _L3
_L3:
        Exception exception;
        String s1;
        String s2;
        String s3;
        if(!items.isEmpty())
        {
            loadShoppinglist(s, items);
        } else
        {
            Toast.makeText(this, getString(0x7f0700bc), 0).show();
            finish();
        }
        return;
_L2:
        if(!intent.getAction().equals("android.intent.action.SEND")) goto _L6; else goto _L5
_L5:
        s1 = intent.getStringExtra("android.intent.extra.TEXT");
        s2 = "\\|";
        if(!s1.contains("|"))
            s2 = "\n";
        if(s1.trim().length() > 0)
            arraylist = new ArrayList(Arrays.asList(s1.split(s2)));
          goto _L6
_L4:
        s3 = (String)iterator.next();
        if(s3.startsWith("_0"))
            s = s3.substring(2);
        if(!s3.startsWith("_"))
        {
            if(s3.contains("_"))
                s3 = s3.substring(0, s3.indexOf('_'));
            items.add(s3);
        }
          goto _L7
        exception;
        exception.printStackTrace();
        items.clear();
          goto _L3
    }

    public void onSearch(View view)
    {
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        intent.putExtra("query", (String)items.get(0));
        intent.putStringArrayListExtra("QUICKLIST", items);
        intent.putExtra("QUICKLIST_POSITION", 0);
        startActivity(intent);
    }

    ArrayList items;
}
