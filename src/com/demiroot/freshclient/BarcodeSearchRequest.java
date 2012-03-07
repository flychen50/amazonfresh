// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            SearchRequest, BarcodeSearchResult, AmazonFreshBase, SearchResult

public class BarcodeSearchRequest extends SearchRequest
{

    public BarcodeSearchRequest()
    {
        setUpcPrefix(false);
    }

    public boolean loadNextPageInParellel()
    {
        boolean flag;
        if(isUpcPrefix())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public SearchRequest nextPage()
    {
        SearchRequest searchrequest;
        if(!isUpcPrefix())
        {
            SearchRequest searchrequest1 = clone();
            searchrequest1.clearPageNum();
            searchrequest1.setUpcPrefix(true);
            searchrequest1.setResultsPerPage(null);
            searchrequest = searchrequest1;
        } else
        {
            searchrequest = super.nextPage();
        }
        return searchrequest;
    }

    public SearchRequest prevPage()
    {
        throw new IllegalStateException("This does not work going backwards, becuase I am being lazy");
    }

    public SearchResult search(AmazonFreshBase amazonfreshbase)
    {
        return new BarcodeSearchResult(amazonfreshbase, this);
    }
}
