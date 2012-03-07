// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            GatewayMenuActivity

public class CompleteOrderActivity extends AFBaseActivity
{

    public CompleteOrderActivity()
    {
    }

    protected void checkout(final String paymentInstrumentId)
    {
        AsyncRequest.buildRequest(this, handler, new Runnable() {

            public void run()
            {
                getAmazonFreshBase().checkout(paymentInstrumentId);
            }

            final CompleteOrderActivity this$0;
            private final String val$paymentInstrumentId;

            
            {
                this$0 = CompleteOrderActivity.this;
                paymentInstrumentId = s;
                Object();
            }
        }
).setSuccessAction(new Runnable() {

            public void run()
            {
                Intent intent = new Intent(CompleteOrderActivity.this, com/demiroot/amazonfresh/activities/GatewayMenuActivity);
                intent.putExtra("orderComplete", true);
                startActivity(intent);
                finish();
            }

            final CompleteOrderActivity this$0;

            
            {
                this$0 = CompleteOrderActivity.this;
                Object();
            }
        }
).setFailureAction(new com.demiroot.amazonfresh.Async.OnExceptionAction() {

            public boolean onException(Exception exception)
            {
                String s = getString(0x7f070066);
                if(exception instanceof FreshAPILoginException)
                {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = getString(0x7f070067);
                    logoutHandler.sendMessage(message);
                } else
                {
                    if(exception instanceof FreshAPIException)
                        s = (new StringBuilder(String.valueOf(s))).append(". ").append(((FreshAPIException)exception).getReason()).toString();
                    showMessage(s);
                }
                return false;
            }

            final CompleteOrderActivity this$0;

            
            {
                this$0 = CompleteOrderActivity.this;
                Object();
            }
        }
).setWaitMessage(getString(0x7f070064)).execute();
    }

    protected void displayOrderInfo(Order order, String s)
    {
        String s1 = null;
        String s2;
        TextView textview;
        boolean flag;
        if(order.hasSelectedSlot())
        {
            Button button;
            int i;
            if(!order.canCheckout())
                if(order.getSubtotal() > order.getMinimumOrderThreshold())
                {
                    s1 = getString(0x7f070069);
                } else
                {
                    Object aobj[] = new Object[1];
                    aobj[0] = Integer.valueOf((int)order.getMinimumOrderThreshold());
                    s1 = getString(0x7f07006a, aobj);
                }
            if(order.getSelectedSlot().getType().equals("attended"))
                i = 0x7f07006f;
            else
                i = 0x7f07006d;
            s2 = (new StringBuilder(String.valueOf((new StringBuilder(String.valueOf(getString(i)))).append("<br />").append(StringUtils.slotForOrderSummary(order.getSelectedSlot(), this)).toString()))).append("<br />").append(StringUtils.getAddressHtml(order.getAddress(), false, true)).toString();
        } else
        {
            s2 = (new StringBuilder(String.valueOf(""))).append(getString(0x7f070070)).toString();
        }
        ((TextView)findViewById(0x7f0b0024)).setText(Html.fromHtml(s2));
        if(s != null)
        {
            String s3;
            if(s1 == null)
                s3 = "";
            else
                s3 = (new StringBuilder(String.valueOf(s1))).append("<br />").toString();
            s1 = (new StringBuilder(String.valueOf(s3))).append(s).toString();
        }
        textview = (TextView)findViewById(0x7f0b0025);
        if(s1 != null)
        {
            textview.setVisibility(0);
            textview.setText(Html.fromHtml(s1));
        } else
        {
            textview.setVisibility(8);
        }
        ((TextView)findViewById(0x7f0b002a)).setText(StringUtils.price(order.getSubtotal()));
        ((TextView)findViewById(0x7f0b002c)).setText(StringUtils.price(order.getTax()));
        ((TextView)findViewById(0x7f0b002b)).setText(StringUtils.price(order.getDeliveryFee()));
        ((TextView)findViewById(0x7f0b002d)).setText(StringUtils.price(order.getTip()));
        ((TextView)findViewById(0x7f0b002e)).setText(StringUtils.price(order.getTotal()));
        button = (Button)findViewById(0x7f0b0027);
        if(order.canCheckout() && order.hasSelectedSlot() && s1 == null)
            flag = true;
        else
            flag = false;
        button.setEnabled(flag);
    }
}
