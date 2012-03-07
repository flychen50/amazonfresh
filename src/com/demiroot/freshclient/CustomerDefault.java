// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class CustomerDefault extends FreshAPICall
{

    private CustomerDefault(AmazonFreshBase amazonfreshbase, String s)
    {
        super(amazonfreshbase, "/customer/updateDefaultAddress/", "POST");
        APIArgs apiargs = new APIArgs(new Object[0]);
        apiargs.put("defaultAddressId", s);
        init(apiargs);
    }

    public static void updateDefaultAddressId(AmazonFreshBase amazonfreshbase, String s)
    {
        new CustomerDefault(amazonfreshbase, s);
    }
}
