// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class UpdateTip extends FreshAPICall
{

    private UpdateTip(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        super(amazonfreshbase, (new StringBuilder("/order/updateTip/")).append(s1).toString(), "POST");
        Object aobj[] = new Object[2];
        aobj[0] = "tip";
        aobj[1] = s;
        init(new APIArgs(aobj));
    }

    public static void updateTip(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        new UpdateTip(amazonfreshbase, s1, s);
    }
}
