// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, LinkAPICall, AmazonFreshBase

public class SearchRefinement extends FreshAPICall
{

    public SearchRefinement(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public Integer getCount()
    {
        return count;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(displayName))).append(" (").append(count).append(")").toString();
    }

    Integer count;
    String displayName;
    String name;
    LinkAPICall url;
}
