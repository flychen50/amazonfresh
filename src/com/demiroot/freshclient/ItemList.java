// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class ItemList extends FreshAPICall
{

    public ItemList(AmazonFreshBase amazonfreshbase, String s)
    {
        super(amazonfreshbase, s, "GET");
        init();
    }

    List list;
}
