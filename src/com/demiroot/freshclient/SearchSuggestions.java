// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class SearchSuggestions extends FreshAPICall
{

    public SearchSuggestions(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase, "/product/commonTerms/", "GET");
        init();
    }

    public List getCommonTerms()
    {
        return list;
    }

    private List list;
}
