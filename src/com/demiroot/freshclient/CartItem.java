// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            DisplayItem, APIArgs, AmazonFreshBase

public class CartItem extends DisplayItem
{

    public CartItem(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public CartItem(AmazonFreshBase amazonfreshbase, String s)
    {
        this(amazonfreshbase, s, null, false);
    }

    public CartItem(AmazonFreshBase amazonfreshbase, String s, Integer integer, boolean flag)
    {
        StringBuilder stringbuilder = new StringBuilder("/cartItem/");
        String s1;
        Object aobj[];
        Object obj;
        if(flag)
            s1 = "set/";
        else
            s1 = "";
        super(amazonfreshbase, stringbuilder.append(s1).append(s).toString(), "POST");
        aobj = new Object[2];
        aobj[0] = "quantity";
        if(integer == null)
            obj = null;
        else
            obj = integer.toString();
        aobj[1] = obj;
        init(new APIArgs(aobj));
    }

    public double getExtendedPrice()
    {
        return extendedPrice;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void removeFromCart()
    {
        amazonFreshBase.deleteItem(asin);
    }

    double extendedPrice;
    int quantity;
    double tax;
    double tinyImageUrl;
}
