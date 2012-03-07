// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.ui.CartItemAdapter;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.AmazonFreshBase;
import com.demiroot.freshclient.Order;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            GatewayMenuActivity

public class ViewOrderActivity extends AFBaseActivity
{

    public ViewOrderActivity()
    {
    }

    private void displayCartItems(List list)
    {
        ListView listview = (ListView)findViewById(0x102000a);
        listview.setEmptyView(findViewById(0x1020004));
        listview.setAdapter(new CartItemAdapter(this, 0, list));
    }

    private void displayOrderTotal(Order order)
    {
        ((TextView)findViewById(0x7f0b0086)).setText(StringUtils.slot(order.getSelectedSlot(), this));
        ((TextView)findViewById(0x7f0b0085)).setText(order.getStatus());
        ((TextView)findViewById(0x7f0b0083)).setText(order.getOrderId());
        TextView textview = (TextView)findViewById(0x7f0b0029);
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(order.getTotalQuantity());
        textview.setText(getString(0x7f070071, aobj));
        ((TextView)findViewById(0x7f0b002a)).setText(StringUtils.price(order.getSubtotal()));
        ((TextView)findViewById(0x7f0b002c)).setText(StringUtils.price(order.getTax()));
        ((TextView)findViewById(0x7f0b002b)).setText(StringUtils.price(order.getDeliveryFee()));
        ((TextView)findViewById(0x7f0b002d)).setText(StringUtils.price(order.getTip()));
        ((TextView)findViewById(0x7f0b002e)).setText(StringUtils.price(order.getTotal()));
    }

    private void loadOrderDetailView(final String orderId)
    {
        final ArrayList items = new ArrayList();
        AsyncRequest.buildRequest(this, handler, new Runnable() {

            public void run()
            {
                mOrder = getAmazonFreshBase().getOrder(orderId);
                loadOrderTotal(mOrder);
                items.addAll(mOrder.getItems());
            }

            final ViewOrderActivity this$0;
            private final List val$items;
            private final String val$orderId;

            
            {
                this$0 = ViewOrderActivity.this;
                orderId = s;
                items = list;
                super();
            }
        }
).setSuccessAction(new Runnable() {

            public void run()
            {
                displayCartItems(items);
            }

            final ViewOrderActivity this$0;
            private final List val$items;

            
            {
                this$0 = ViewOrderActivity.this;
                items = list;
                super();
            }
        }
).setTopLoadingBar().execute();
    }

    private void loadOrderTotal(final Order cart)
    {
        handler.post(new Runnable() {

            public void run()
            {
                displayOrderTotal(cart);
            }

            final ViewOrderActivity this$0;
            private final Order val$cart;

            
            {
                this$0 = ViewOrderActivity.this;
                cart = order;
                super();
            }
        }
);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03001a);
        loadActionBar(getString(0x7f07007f));
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0c0004, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131427523 2131427524: default 28
    //                   2131427523 30
    //                   2131427524 94;
           goto _L1 _L2 _L3
_L1:
        return false;
_L2:
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(getString(0x7f070080)).setCancelable(false).setPositiveButton(0x7f070081, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                AsyncRequest.buildRequest(ViewOrderActivity.this, 
// JavaClassFileOutputException: get_constant: invalid tag

            final ViewOrderActivity this$0;


            
            {
                this$0 = ViewOrderActivity.this;
                super();
            }

            // Unreferenced inner class com/demiroot/amazonfresh/activities/ViewOrderActivity$4$1

/* anonymous class */
        class _cls1
            implements Runnable
        {

            public void run()
            {
                mOrder.cancel();
            }

            final _cls4 this$1;

                    
                    {
                        this$1 = _cls4.this;
                        super();
                    }
        }


            // Unreferenced inner class com/demiroot/amazonfresh/activities/ViewOrderActivity$4$2

/* anonymous class */
        class _cls2
            implements Runnable
        {

            public void run()
            {
                Intent intent = new Intent(_fld0, com/demiroot/amazonfresh/activities/GatewayMenuActivity);
                startActivity(intent);
                showToast(getString(0x7f070084), 1);
            }

            final _cls4 this$1;

                    
                    {
                        this$1 = _cls4.this;
                        super();
                    }
        }

        }
).setNegativeButton(getString(0x7f070082), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

            final ViewOrderActivity this$0;

            
            {
                this$0 = ViewOrderActivity.this;
                super();
            }
        }
);
        builder.create().show();
        continue; /* Loop/switch isn't completed */
_L3:
        AsyncRequest.buildRequest(this, handler, new Runnable() {

            public void run()
            {
                mOrder.edit();
                .post(new Runnable() {

                    public void run()
                    {
                        showToast(getString(0x7f070085), 1);
                    }

                    final _cls6 this$1;

                    
                    {
                        this$1 = _cls6.this;
                        super();
                    }
                }
);
                broadcastCart();
            }

            final ViewOrderActivity this$0;


            
            {
                this$0 = ViewOrderActivity.this;
                super();
            }
        }
).execute();
        if(true) goto _L1; else goto _L4
_L4:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(!mOrder.canCancel())
            menu.removeItem(0x7f0b00c3);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onResume()
    {
        super.onResume();
        loadOrderDetailView(getIntent().getStringExtra("orderId"));
    }

    volatile Order mOrder;





}
