// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class LoginResponse extends FreshAPICall
{

    public LoginResponse(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        super(amazonfreshbase, "/customer/login", "POST");
        Object aobj[] = new Object[4];
        aobj[0] = "emailAddress";
        aobj[1] = s;
        aobj[2] = "password";
        aobj[3] = s1;
        init(new APIArgs(aobj));
    }

    String customerId;
    String customerName;
    String status;
}
