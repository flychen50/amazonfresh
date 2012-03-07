// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh;

import com.demiroot.freshclient.FreshClientProxy;

// Referenced classes of package com.demiroot.amazonfresh:
//            AFApplication

public class AndroidFreshProxyGetter
    implements com.demiroot.freshclient.AmazonFreshBase.FreshProxyGetter
{

    public AndroidFreshProxyGetter(String s, String s1)
    {
        apiKey = s;
        userAgent = s1;
    }

    public FreshClientProxy getProxy()
    {
        if(AFApplication.proxy == null)
            AFApplication.proxy = new FreshClientProxy(apiKey, userAgent);
        return AFApplication.proxy;
    }

    public String apiKey;
    public String userAgent;
}
