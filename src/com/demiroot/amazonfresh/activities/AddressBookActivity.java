// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.demiroot.amazonfresh.AFApplication;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.AddressBookAdapter;
import com.demiroot.freshclient.AddressBook;
import java.util.ArrayList;

public class AddressBookActivity extends AFBaseActivity
{

    public AddressBookActivity()
    {
        addressBookReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                ArrayList arraylist = (ArrayList)intent.getSerializableExtra("response");
                if(arraylist != null)
                    ((ListView)findViewById(0x102000a)).setAdapter(new AddressBookAdapter(AddressBookActivity.this, arraylist));
            }

            final AddressBookActivity this$0;

            
            {
                this$0 = AddressBookActivity.this;
                super(afbaseactivity);
            }
        }
;
    }

    public void goToMobileSiteAddressBook(View view)
    {
        String s = ((AFApplication)getApplication()).getAppVersion();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse((new StringBuilder("https://fresh.amazon.com/m/splashDelivery?mobileApp=android&version=")).append(s).toString())));
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030004);
        loadActionBar(getString(0x7f0700cc));
        showLoadingBar(getString(0x7f070056));
        broadcastAddressBook();
    }

    public void onStart()
    {
        super.onStart();
        registerReceiver(addressBookReceiver, new IntentFilter(com/demiroot/freshclient/AddressBook.getName()));
    }

    public void onStop()
    {
        super.onStop();
        unregisterReceiver(addressBookReceiver);
    }

    private BroadcastReceiver addressBookReceiver;
}
