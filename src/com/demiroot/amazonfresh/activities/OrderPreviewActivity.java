// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            CompleteOrderActivity, SettingsActivity, SelectSlotActivity

public class OrderPreviewActivity extends CompleteOrderActivity
{

    public OrderPreviewActivity()
    {
        cart = null;
        cartReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent)
            {
                Order order = (Order)intent.getSerializableExtra("response");
                if(order != null && !order.equals(previouslyReceivedOrder))
                {
                    loadOrderTotal(order);
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
            final OrderPreviewActivity this$0;

            
            {
                this$0 = OrderPreviewActivity.this;
                super();
            }
        }
;
    }

    private void loadOrderTotal(final Order cart)
    {
        handler.post(new Runnable() {

            public void run()
            {
                displayOrderInfo(cart);
            }

            final OrderPreviewActivity this$0;
            private final Order val$cart;

            
            {
                this$0 = OrderPreviewActivity.this;
                cart = order;
                super();
            }
        }
);
    }

    protected void displayOrderInfo(Order order)
    {
        String s = null;
        final List availablePaymentInstrumnets = order.getAvailablePaymentInstruments();
        Button button;
        if(availablePaymentInstrumnets != null && !availablePaymentInstrumnets.isEmpty())
        {
            Spinner spinner = (Spinner)findViewById(0x7f0b0089);
            spinner.setVisibility(0);
            paymentInstrumentId = ((PaymentInstrument)availablePaymentInstrumnets.get(0)).getPaymentInstrumentId();
            spinner.setAdapter(new ArrayAdapter(this, 0x7f03002d, availablePaymentInstrumnets));
            spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView adapterview, View view, int i, long l)
                {
                    paymentInstrumentId = ((PaymentInstrument)availablePaymentInstrumnets.get(i)).getPaymentInstrumentId();
                }

                public void onNothingSelected(AdapterView adapterview)
                {
                }

                final OrderPreviewActivity this$0;
                private final List val$availablePaymentInstrumnets;

            
            {
                this$0 = OrderPreviewActivity.this;
                availablePaymentInstrumnets = list;
                super();
            }
            }
);
        } else
        {
            TextView textview = (TextView)findViewById(0x7f0b008a);
            textview.setVisibility(0);
            textview.setMovementMethod(LinkMovementMethod.getInstance());
            s = (new StringBuilder(String.valueOf(null))).append(getString(0x7f07006b)).toString();
        }
        super.displayOrderInfo(order, s);
        ((TextView)findViewById(0x7f0b0087)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(0x7f0b008c)).setMovementMethod(LinkMovementMethod.getInstance());
        button = (Button)findViewById(0x7f0b0027);
        if(order.isModifyingOrder())
            button.setText(getString(0x7f07009d));
        else
            button.setText(getString(0x7f07003c));
    }

    public void editTip(View view)
    {
        findViewById(0x7f0b008d).setVisibility(0);
        findViewById(0x7f0b008c).setVisibility(8);
        EditText edittext = (EditText)findViewById(0x7f0b008e);
        edittext.setText("");
        findViewById(0x7f0b008d).requestFocus();
        ((InputMethodManager)getSystemService("input_method")).showSoftInput(edittext, 2);
    }

    public void goToSettingsPage(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/SettingsActivity));
    }

    public void goToSlotSelectionPage(View view)
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/SelectSlotActivity));
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03001b);
        Object aobj[] = new Object[1];
        aobj[0] = getAmazonFreshBase().getCustomerName();
        loadActionBar(getString(0x7f07003e, aobj));
        ((Button)findViewById(0x7f0b0027)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                checkout(paymentInstrumentId);
            }

            final OrderPreviewActivity this$0;

            
            {
                this$0 = OrderPreviewActivity.this;
                super();
            }
        }
);
    }

    public void onPause()
    {
        super.onPause();
        unregisterReceiver(cartReceiver);
        EditText edittext = (EditText)findViewById(0x7f0b008e);
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
    }

    protected void onResume()
    {
        super.onResume();
        broadcastCart();
    }

    public void onStart()
    {
        super.onStart();
        registerReceiver(cartReceiver, new IntentFilter("com.demiroot.freshclient.Order"));
    }

    public void onStop()
    {
        super.onStop();
        EditText edittext = (EditText)findViewById(0x7f0b008e);
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
    }

    public void saveTip(View view)
    {
        EditText edittext = (EditText)findViewById(0x7f0b008e);
        final TextView tip = (TextView)findViewById(0x7f0b002d);
        final String tipString = edittext.getText().toString();
        if(tipString.length() > 0)
        {
            final Double tipDouble = Double.valueOf(Double.parseDouble(tipString));
            AsyncRequest.buildRequest(this, handler, new Runnable() {

                public void run()
                {
                    cart.updateTip(tipString);
                }

                final OrderPreviewActivity this$0;
                private final String val$tipString;

            
            {
                this$0 = OrderPreviewActivity.this;
                tipString = s;
                super();
            }
            }
).setFailureAction(new com.demiroot.amazonfresh.Async.OnExceptionAction() {

                public boolean onException(Exception exception)
                {
                    boolean flag;
                    if(exception instanceof FreshAPIException)
                    {
                        String s = ((FreshAPIException)exception).getReason();
                        Toast.makeText(OrderPreviewActivity.this, s, 0).show();
                        flag = false;
                    } else
                    {
                        flag = true;
                    }
                    return flag;
                }

                final OrderPreviewActivity this$0;

            
            {
                this$0 = OrderPreviewActivity.this;
                super();
            }
            }
).setSuccessAction(new Runnable() {

                public void run()
                {
                    tip.setText(StringUtils.price(tipDouble.doubleValue()));
                    broadcastCart();
                }

                final OrderPreviewActivity this$0;
                private final TextView val$tip;
                private final Double val$tipDouble;

            
            {
                this$0 = OrderPreviewActivity.this;
                tip = textview;
                tipDouble = double1;
                super();
            }
            }
).setTopLoadingBar().setWaitMessage(getString(0x7f070055)).execute();
        }
        findViewById(0x7f0b008d).setVisibility(8);
        findViewById(0x7f0b008c).setVisibility(0);
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext.getWindowToken(), 0);
_L1:
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        Toast.makeText(this, getString(0x7f070052), 0).show();
        edittext.setText("");
          goto _L1
    }

    private Order cart;
    private BroadcastReceiver cartReceiver;
    private String paymentInstrumentId;





}
