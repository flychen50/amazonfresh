// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.CartItemAdapter;
import com.demiroot.amazonfresh.ui.CartItemDisplayBuilder;
import com.demiroot.freshclient.AmazonFreshBase;
import com.demiroot.freshclient.Order;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            CompleteOrderActivity, OrderPreviewActivity, SelectSlotActivity, GatewayMenuActivity

public class CartActivity extends CompleteOrderActivity
{

    public CartActivity()
    {
        cart = null;
        orderTotalHandler = new Handler() {

            public void handleMessage(Message message)
            {
                if(message.what == 3)
                    displayOrderInfo((Order)message.obj);
                else
                    super.handleMessage(message);
            }

            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super();
            }
        }
;
        cartReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent)
            {
                Order order = (Order)intent.getSerializableExtra("response");
                if(order != null && !order.equals(previouslyReceivedOrder))
                {
                    loadOrderTotal(order);
                    broadcastCartItems(order);
                    previouslyReceivedOrder = order;
                    cart = order;
                }
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            private Order previouslyReceivedOrder;
            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super();
            }
        }
;
        cartItemsReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                List list = (List)intent.getSerializableExtra("response");
                if(!list.equals(previousCartItems))
                {
                    displayCartItems(list);
                    previousCartItems = list;
                }
            }

            private List previousCartItems;
            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super(afbaseactivity);
            }
        }
;
    }

    private void clearCart()
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        int i;
        if(cart.isModifyingOrder())
            i = 0x7f0700a3;
        else
            i = 0x7f0700a2;
        builder.setMessage(getString(i)).setCancelable(false).setPositiveButton(0x7f070081, new android.content.DialogInterface.OnClickListener() ).setNegativeButton(getString(0x7f070082), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super();
            }
        }
);
        builder.create().show();
    }

    private void displayCartItems(List list)
    {
        ListView listview = (ListView)findViewById(0x102000a);
        listview.setEmptyView(findViewById(0x1020004));
        CartItemAdapter cartitemadapter = new CartItemAdapter(this, 0, list);
        listview.setAdapter(cartitemadapter);
        (new CartItemDisplayBuilder()).createQuickActions((ListView)findViewById(0x102000a), getResources(), this, cartitemadapter);
    }

    private void loadOrderTotal(final Order cart)
    {
        handler.post(new Runnable() {

            public void run()
            {
                displayOrderInfo(cart);
            }

            final CartActivity this$0;
            private final Order val$cart;

            
            {
                this$0 = CartActivity.this;
                cart = order;
                super();
            }
        }
);
    }

    protected void displayOrderInfo(Order order)
    {
        displayOrderInfo(order, null);
        TextView textview = (TextView)findViewById(0x7f0b0029);
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(order.getTotalQuantity());
        textview.setText(getString(0x7f070071, aobj));
        Button button = (Button)findViewById(0x7f0b0027);
        if(order.isModifyingOrder())
        {
            button.setText(getString(0x7f07009d));
            button.setBackgroundResource(0x7f02000e);
            findViewById(0x7f0b0026).setVisibility(8);
        } else
        {
            button.setText(getString(0x7f07003d));
        }
    }

    protected void onCreate(Bundle bundle)
    {
        onCreate(bundle);
        setContentView(0x7f030008);
        Object aobj[] = new Object[1];
        aobj[0] = getAmazonFreshBase().getCustomerName();
        loadActionBar(getString(0x7f070063, aobj));
        ((Button)findViewById(0x7f0b0027)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(cart.isModifyingOrder())
                    checkout(null);
                else
                    startActivity(new Intent(CartActivity.this, com/demiroot/amazonfresh/activities/OrderPreviewActivity));
            }

            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super();
            }
        }
);
        ((Button)findViewById(0x7f0b0026)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(CartActivity.this, com/demiroot/amazonfresh/activities/SelectSlotActivity);
                startActivity(intent);
            }

            final CartActivity this$0;

            
            {
                this$0 = CartActivity.this;
                super();
            }
        }
);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        boolean flag;
        if(cart == null)
        {
            flag = onCreateOptionsMenu(menu);
        } else
        {
            getMenuInflater().inflate(0x7f0c0000, menu);
            MenuItem menuitem = menu.findItem(0x7f0b00bd);
            if(cart.isModifyingOrder())
                menuitem.setTitle(0x7f07009f);
            else
                menuitem.setTitle(0x7f07009e);
            flag = true;
        }
        return flag;
    }

    public boolean onMenuItemSelected(int i, MenuItem menuitem)
    {
        boolean flag;
        if(menuitem.getItemId() == 0x7f0b00bd)
        {
            if(cart == null)
            {
                flag = false;
            } else
            {
                clearCart();
                flag = true;
            }
        } else
        {
            flag = false;
        }
        return flag;
    }

    protected void onResume()
    {
        onResume();
        showLoadingBar(getString(0x7f070068));
        broadcastCart();
    }

    public void onStart()
    {
        onStart();
        registerReceiver(cartReceiver, new IntentFilter("com.demiroot.freshclient.Order"));
        registerReceiver(cartItemsReceiver, new IntentFilter("com.demiroot.freshclient.CartItems"));
    }

    public void onStop()
    {
        onStop();
        unregisterReceiver(cartItemsReceiver);
        unregisterReceiver(cartReceiver);
    }

    public void requestOrderTotalCart()
    {
        Order order = getAmazonFreshBase().cart();
        orderTotalHandler.removeMessages(3);
        Message message = orderTotalHandler.obtainMessage(3, order);
        orderTotalHandler.sendMessage(message);
    }

    private static final int ORDER_TOTAL_RELOAD_MESSAGE = 3;
    private Order cart;
    private BroadcastReceiver cartItemsReceiver;
    private BroadcastReceiver cartReceiver;
    Handler orderTotalHandler;






}
