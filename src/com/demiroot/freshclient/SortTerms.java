// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, LinkAPICall, AmazonFreshBase

public class SortTerms extends FreshAPICall
{

    public SortTerms(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    String displayName;
    String internalName;
    LinkAPICall url;
}
