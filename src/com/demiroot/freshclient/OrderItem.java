// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            DisplayItem, AmazonFreshBase

public class OrderItem extends DisplayItem
{

    public OrderItem(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public int getQuantity()
    {
        return quantity;
    }

    double extendedPrice;
    int quantity;
    String tinyImageUrl;
}
