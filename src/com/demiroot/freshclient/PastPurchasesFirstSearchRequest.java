// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            SearchRequest, PastPurchasesFirstSearchResult, SearchResult, DisplayItem, 
//            AmazonFreshBase

public class PastPurchasesFirstSearchRequest extends SearchRequest
{

    public PastPurchasesFirstSearchRequest(Integer integer)
    {
        setFilterByPP(true);
        setResultsPerPage(integer);
    }

    public boolean loadNextPageInParellel()
    {
        return isFilterByPP();
    }

    public SearchRequest nextPage()
    {
        SearchRequest searchrequest;
        if(isFilterByPP())
        {
            SearchRequest searchrequest1 = clone();
            searchrequest1.clearPageNum();
            searchrequest1.setFilterByPP(false);
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
        PastPurchasesFirstSearchResult pastpurchasesfirstsearchresult = new PastPurchasesFirstSearchResult(amazonfreshbase, this);
        if(isFilterByPP())
        {
            int i = pastpurchasesfirstsearchresult.getItems().size() - 1;
            while(i >= 0 && !((DisplayItem)pastpurchasesfirstsearchresult.getItems().get(i)).isInStock()) 
            {
                pastpurchasesfirstsearchresult.getItems().remove(i);
                i--;
            }
        }
        return pastpurchasesfirstsearchresult;
    }
}
