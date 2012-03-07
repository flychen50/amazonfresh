// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, Order, AmazonFreshBase

public class CheckoutResponse extends FreshAPICall
{

    public CheckoutResponse(AmazonFreshBase amazonfreshbase, String s, String s1, String s2, String s3)
    {
        super(amazonfreshbase, "/cart/checkout/", "POST");
        APIArgs apiargs = new APIArgs(new Object[0]);
        apiargs.put("paymentId", s);
        apiargs.put("bagingPref", s1);
        apiargs.put("totePickupPref", s2);
        apiargs.put("promoCode", s3);
        init(apiargs);
    }

    List missingItems;
    Order order;
}
