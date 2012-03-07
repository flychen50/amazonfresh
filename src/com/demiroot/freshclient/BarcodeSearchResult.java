// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            SearchResult, SearchRequest, AmazonFreshBase

public class BarcodeSearchResult extends SearchResult
{

    public BarcodeSearchResult(AmazonFreshBase amazonfreshbase, SearchRequest searchrequest)
    {
        super(amazonfreshbase, searchrequest);
    }

    public boolean hasNextPage()
    {
        boolean flag;
        if(super.hasNextPage())
            flag = true;
        else
        if(searchRequest.isUpcPrefix())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean hasPrevPage()
    {
        throw new IllegalStateException("This does not work going backwards, becuase I am being lazy");
    }

    public SearchResult prevPage()
    {
        throw new IllegalStateException("This does not work going backwards, becuase I am being lazy");
    }
}
