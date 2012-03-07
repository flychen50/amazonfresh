// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class OrderList extends FreshAPICall
{

    public OrderList(AmazonFreshBase amazonfreshbase, int i, boolean flag)
    {
        String s;
        if(flag)
            s = "/order/editable";
        else
            s = "/order";
        super(amazonfreshbase, s, "GET");
        if(i > 0 && !flag)
        {
            Object aobj[] = new Object[2];
            aobj[0] = "limit";
            aobj[1] = (new StringBuilder(String.valueOf(i))).toString();
            init(new APIArgs(aobj));
        } else
        {
            init();
        }
    }

    List list;
}
