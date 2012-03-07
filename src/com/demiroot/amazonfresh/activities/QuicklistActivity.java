// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.os.Bundle;
import android.text.Editable;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.listeners.EnterKeyListener;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.QuicklistListAdapter;
import com.demiroot.freshclient.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            SearchUsingWidgetActivity

public class QuicklistActivity extends AFBaseActivity
{

    public QuicklistActivity()
    {
        localList = new ArrayList();
        quicklistReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                ArrayList arraylist = (ArrayList)intent.getSerializableExtra("response");
                localList.clear();
                localList.addAll(arraylist);
                listAdapter.notifyDataSetChanged();
            }

            final QuicklistActivity this$0;

            
            {
                this$0 = QuicklistActivity.this;
                super(afbaseactivity);
            }
        }
;
    }

    public void addTerm(String s)
    {
        if(s.length() <= 0) goto _L2; else goto _L1
_L1:
        if(!localList.contains(s)) goto _L4; else goto _L3
_L3:
        Object aobj[] = new Object[1];
        aobj[0] = s;
        showToast(getString(0x7f0700ad, aobj), 0);
_L2:
        textBar.setText("");
        return;
_L4:
        updateQuicklist("add", s);
        String as[] = s.split(",");
        int i = as.length - 1;
        do
        {
label0:
            {
                if(i >= 0)
                    break label0;
                listAdapter.notifyDataSetChanged();
            }
            if(true)
                continue;
            localList.add(0, as[i]);
            i--;
        } while(true);
        if(true) goto _L2; else goto _L5
_L5:
    }

    public void onAddClick(View view)
    {
        addTerm(textBar.getText().toString().trim().toLowerCase());
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03001d);
        loadActionBar("Quicklist");
        textBar = (EditText)findViewById(0x7f0b0096);
        textBar.setOnKeyListener(new EnterKeyListener() {

            public void onEnter()
            {
                onAddClick(null);
            }

            final QuicklistActivity this$0;

            
            {
                this$0 = QuicklistActivity.this;
                super();
            }
        }
);
        ListView listview = (ListView)findViewById(0x102000a);
        listAdapter = new QuicklistListAdapter(0, localList, listview) {

            public void notifyDataSetChanged()
            {
                super.notifyDataSetChanged();
                if(localList.size() == 0)
                    list.setEmptyView(findViewById(0x1020004));
                else
                    list.setEmptyView(null);
            }

            final QuicklistActivity this$0;
            private final ListView val$list;

            
            {
                this$0 = QuicklistActivity.this;
                list = listview;
                super(final_context, i, list1);
            }
        }
;
        listview.setAdapter(listAdapter);
        if(localList.size() == 0)
            listview.setEmptyView(findViewById(0x1020004));
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0c0002, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131427521 2131427521: default 24
    //                   2131427521 32;
           goto _L1 _L2
_L1:
        boolean flag = super.onOptionsItemSelected(menuitem);
_L4:
        return flag;
_L2:
        removeAll();
        listAdapter.notifyDataSetChanged();
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onResume()
    {
        super.onResume();
        reload();
    }

    public void onStart()
    {
        super.onStart();
        registerReceiver(quicklistReceiver, new IntentFilter(com/demiroot/freshclient/QuickList.getName()));
    }

    public void onStop()
    {
        super.onStop();
        unregisterReceiver(quicklistReceiver);
    }

    public ArrayList reload()
    {
        showLoadingBar(getString(0x7f070056));
        broadcastQuicklist();
        return localList;
    }

    public void removeAll()
    {
        updateQuicklist("removeAll", "");
        localList.clear();
        listAdapter.notifyDataSetChanged();
    }

    public void removeTerm(String s)
    {
        updateQuicklist("remove", s);
        localList.remove(s);
        listAdapter.notifyDataSetChanged();
    }

    public void startQuicklist(View view)
    {
        if(localList.size() > 0)
        {
            Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
            intent.putExtra("query", (String)localList.get(0));
            intent.putStringArrayListExtra("QUICKLIST", localList);
            intent.putExtra("QUICKLIST_POSITION", 0);
            startActivity(intent);
        } else
        {
            Toast.makeText(this, getString(0x7f0700b3), 0).show();
        }
    }

    public void updateQuicklist(final String action, final String term)
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                FreshClientProxy freshclientproxy = getAmazonFreshBase().getClientProxy();
                String s = (new StringBuilder("/quickList/")).append(action).toString();
                Object aobj[] = new Object[2];
                aobj[0] = "term";
                aobj[1] = term;
                freshclientproxy.post(s, new APIArgs(aobj));
            }

            final QuicklistActivity this$0;
            private final String val$action;
            private final String val$term;

            
            {
                this$0 = QuicklistActivity.this;
                action = s;
                term = s1;
                super();
            }
        }
).start();
    }

    public static final int VOICE_ADD_TO_QUICKLIST = 13;
    QuicklistListAdapter listAdapter;
    private ArrayList localList;
    private BroadcastReceiver quicklistReceiver;
    EditText textBar;

}
