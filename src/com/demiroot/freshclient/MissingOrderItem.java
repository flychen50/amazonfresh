// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            Item, AmazonFreshBase

public class MissingOrderItem extends Item
{

    public MissingOrderItem(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    int requestedQty;
    int reservedQty;
}
