// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class GatewayContent extends FreshAPICall
{

    public GatewayContent(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase, "/gateway", "GET");
        init();
    }

    public List getFeatured()
    {
        return featured;
    }

    public List getNewForYou()
    {
        return newForYou;
    }

    public List getOurPicks()
    {
        return ourPicks;
    }

    public List getSpotlight()
    {
        return spotlight;
    }

    String backgroundColor;
    String backgroundUrl;
    List featured;
    List newForYou;
    List ourPicks;
    List spotlight;
}
