// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class GatewayPane extends FreshAPICall
{

    public GatewayPane(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getResource()
    {
        return resource;
    }

    public String getResourceUrl()
    {
        return resourceUrl;
    }

    public String getTagline()
    {
        return tagline;
    }

    public String getTitle()
    {
        return title;
    }

    public String getType()
    {
        return type;
    }

    String imageUrl;
    String resource;
    String resourceUrl;
    String tagline;
    String title;
    String type;
}
