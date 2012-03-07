// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class StaticContent extends FreshAPICall
{

    public StaticContent(AmazonFreshBase amazonfreshbase, String s)
    {
        super(amazonfreshbase, (new StringBuilder("/static/")).append(s).toString(), "GET");
        init();
    }

    String content;
}
