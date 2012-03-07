// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.NotificationManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.alarm.AFAlarmReceiver;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.MerchandisingGallery;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.*;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            SearchUsingWidgetActivity, CartActivity, OrderCompleteActivity, SettingsActivity, 
//            AboutActivity, PastPurchasesActivity, QuicklistActivity, SelectSlotActivity, 
//            LoginActivity, ViewOrderActivity

public class GatewayMenuActivity extends AFBaseActivity
{
    protected class EditButtonClickListener
        implements android.view.View.OnClickListener
    {

        public void onClick(View view)
        {
            view.setEnabled(false);
            AsyncRequest.buildRequest(GatewayMenuActivity.this, , new Runnable() {

                public void run()
                {
                    order.edit();
                    broadcastCart();
                    loadUpcomingOrders();
                    return;
                    Exception exception;
                    exception;
                    loadUpcomingOrders();
                    throw exception;
                }

                final EditButtonClickListener this$1;

                
                {
                    this$1 = EditButtonClickListener.this;
                    super();
                }
            }
).setSuccessAction(ack).execute();
        }

        private Runnable ack;
        private Order order;
        final GatewayMenuActivity this$0;



        public EditButtonClickListener(Order order1)
        {
            this$0 = GatewayMenuActivity.this;
            super();
            ack = new Runnable() {

                public void run()
                {
                    showToast(getString(0x7f070085), 1);
                }

                final EditButtonClickListener this$1;

                
                {
                    this$1 = EditButtonClickListener.this;
                    super();
                }
            }
;
            order = order1;
        }
    }


    public GatewayMenuActivity()
    {
        merchandisingReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                ArrayList arraylist = (ArrayList)intent.getSerializableExtra("response");
                setupMerchandising(arraylist);
            }

            final GatewayMenuActivity this$0;

            
            {
                this$0 = GatewayMenuActivity.this;
                super(afbaseactivity);
            }
        }
;
    }

    private void displayNoOrders()
    {
        findViewById(0x7f0b0062).setVisibility(8);
        findViewById(0x7f0b0061).setVisibility(8);
    }

    private void displayUpcomingOrders(List list)
    {
        ViewGroup viewgroup = (ViewGroup)findViewById(0x7f0b0067);
        viewgroup.removeAllViews();
        findViewById(0x7f0b0065).setVisibility(8);
        TextView textview = (TextView)findViewById(0x7f0b0066);
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(list.size());
        textview.setText(getString(0x7f070039, aobj));
        textview.setVisibility(0);
        Iterator iterator = list.iterator();
        do
        {
            do
            {
                if(!iterator.hasNext())
                    return;
                final Order order = (Order)iterator.next();
                View view = getLayoutInflater().inflate(0x7f030016, null);
                String s = StringUtils.slot(order.getSelectedSlot(), this);
                Button button = (Button)view.findViewById(0x7f0b0078);
                TextView textview1;
                View view1;
                android.widget.LinearLayout.LayoutParams layoutparams;
                if(!order.isBeingModified())
                {
                    EditButtonClickListener editbuttonclicklistener = new EditButtonClickListener(order);
                    button.setOnClickListener(editbuttonclicklistener);
                    button.setText(getString(0x7f070016));
                    button.setEnabled(true);
                } else
                {
                    button.setEnabled(false);
                    button.setText(getString(0x7f070017));
                }
                textview1 = (TextView)view.findViewById(0x7f0b0077);
                textview1.setText(s);
                if(!order.isBeingModified())
                {
                    android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

                        public void onClick(View view2)
                        {
                            Intent intent = new Intent(GatewayMenuActivity.this, com/demiroot/amazonfresh/activities/ViewOrderActivity);
                            intent.putExtra("orderId", order.getOrderId());
                            startActivity(intent);
                        }

                        final GatewayMenuActivity this$0;
                        private final Order val$order;

            
            {
                this$0 = GatewayMenuActivity.this;
                order = order1;
                super();
            }
                    }
;
                    textview1.setOnClickListener(onclicklistener);
                } else
                {
                    android.view.View.OnClickListener onclicklistener1 = new android.view.View.OnClickListener() {

                        public void onClick(View view2)
                        {
                            Intent intent = new Intent(GatewayMenuActivity.this, com/demiroot/amazonfresh/activities/CartActivity);
                            startActivity(intent);
                        }

                        final GatewayMenuActivity this$0;

            
            {
                this$0 = GatewayMenuActivity.this;
                super();
            }
                    }
;
                    textview1.setOnClickListener(onclicklistener1);
                }
                viewgroup.addView(view);
            } while(!iterator.hasNext());
            view1 = new View(this);
            layoutparams = new android.widget.LinearLayout.LayoutParams(-1, 1);
            view1.setLayoutParams(layoutparams);
            view1.setBackgroundColor(-1);
            viewgroup.addView(view1);
        } while(true);
    }

    public static boolean isIntentAvailable(Context context, String s)
    {
        boolean flag;
        if(context.getPackageManager().queryIntentActivities(new Intent(s), 0x10000).size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void loadUpcomingOrders()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                final List orders = getAmazonFreshBase().getEditiableOrders();
                updateNotifications(orders);
                
// JavaClassFileOutputException: get_constant: invalid tag

            final GatewayMenuActivity this$0;


            
            {
                this$0 = GatewayMenuActivity.this;
                super();
            }

            // Unreferenced inner class com/demiroot/amazonfresh/activities/GatewayMenuActivity$4$1

/* anonymous class */
        class _cls1
            implements Runnable
        {

            public void run()
            {
                if(orders != null && !orders.isEmpty())
                    displayUpcomingOrders(orders);
                else
                    displayNoOrders();
            }

            final _cls4 this$1;
            private final List val$orders;

                    
                    {
                        this$1 = _cls4.this;
                        orders = list;
                        super();
                    }
        }

        }
).start();
    }

    private void setupMerchandising(List list)
    {
        MerchandisingGallery merchandisinggallery = new MerchandisingGallery(this, list);
        Gallery gallery = (Gallery)findViewById(0x7f0b005a);
        gallery.setAdapter(merchandisinggallery);
        gallery.setSelection(0x3fffffff - 0x3fffffff % list.size());
        gallery.setOnItemClickListener(merchandisinggallery.itemClickListener);
    }

    private void updateNotifications(List list)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                Collections.sort(arraylist);
                AFAlarmReceiver.setNextAlarm(this, arraylist);
                return;
            }
            arraylist.add(((Order)iterator.next()).getSelectedSlot().getStartTime());
        } while(true);
    }

    public void onBarcodeSearchClick(View view)
    {
        if(isIntentAvailable(this, "android.media.action.IMAGE_CAPTURE"))
        {
            Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
            intent.putExtra("query", "BARCODE_SEARCH_KEYWORD");
            startActivity(intent);
        } else
        {
            showToast("Your device doesn't have a camera; bar code scanning is disabled.", 1);
        }
    }

    public void onCartClick(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/CartActivity));
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if(getIntent().getBooleanExtra("orderComplete", false))
            if(getSharedPreferences("SHARED_PREFS_KEY", 1).getBoolean("rate_app", true))
                startActivity(new Intent(this, com/demiroot/amazonfresh/activities/OrderCompleteActivity));
            else
                showToast(getString(0x7f070065), 1);
        setContentView(0x7f030010);
        setDefaultKeyMode(3);
        loadActionBar("show_logo_as_title");
        showLoadingBar(getString(0x7f070056));
        broadcastMerchandising();
        if(!isIntentAvailable(this, "android.media.action.IMAGE_CAPTURE"))
            findViewById(0x7f0b005f).setVisibility(8);
        updateSearchSuggestions(getAmazonFreshBase());
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(0x7f0c0001, menu);
        return true;
    }

    public void onMySettingsClick(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/SettingsActivity));
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131427518 2131427520: default 32
    //                   2131427518 36
    //                   2131427519 83
    //                   2131427520 101;
           goto _L1 _L2 _L3 _L4
_L1:
        boolean flag = false;
_L5:
        return flag;
_L2:
        if(isUserAMonkey())
        {
            flag = true;
        } else
        {
            AsyncRequest.buildRequest(this, getHandler(), new Runnable() {

                public void run()
                {
                    removeOldBroadcasts();
                    getAmazonFreshBase().logout();
                }

                final GatewayMenuActivity this$0;

            
            {
                this$0 = GatewayMenuActivity.this;
                super();
            }
            }
).setSuccessAction(new Runnable() {

                public void run()
                {
                    getAmazonFreshBase().setLoggedOut();
                    Intent intent = new Intent(GatewayMenuActivity.this, com/demiroot/amazonfresh/activities/LoginActivity);
                    startActivity(intent);
                }

                final GatewayMenuActivity this$0;

            
            {
                this$0 = GatewayMenuActivity.this;
                super();
            }
            }
).execute();
            flag = true;
        }
        if(true) goto _L5; else goto _L3
_L3:
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/AboutActivity));
        continue; /* Loop/switch isn't completed */
_L4:
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/SettingsActivity));
        if(true) goto _L1; else goto _L6
_L6:
    }

    public void onPPClick(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/PastPurchasesActivity));
    }

    public void onProduceClick(View view)
    {
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        SearchRequest searchrequest = new SearchRequest();
        searchrequest.setBrowseNode("331736011");
        searchrequest.setSort("produce_rating");
        intent.putExtra("SearchRequest", searchrequest);
        intent.putExtra("SearchTitle", getString(0x7f070073));
        startActivity(intent);
    }

    public void onQuicklistClick(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/QuicklistActivity));
    }

    protected void onResume()
    {
        super.onResume();
        ((NotificationManager)getSystemService("notification")).cancel(0xe84ba);
        loadUpcomingOrders();
    }

    public void onSearchRequested(View view)
    {
        onSearchRequested();
    }

    public void onSlotSelectClick(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/SelectSlotActivity));
    }

    public void onStart()
    {
        super.onStart();
        registerReceiver(merchandisingReceiver, new IntentFilter(com/demiroot/freshclient/GatewayContent.getName()));
    }

    public void onStop()
    {
        super.onStop();
        unregisterReceiver(merchandisingReceiver);
    }

    public static final String CLEAR_NOTICE = "clear_notice";
    private BroadcastReceiver merchandisingReceiver;







}
