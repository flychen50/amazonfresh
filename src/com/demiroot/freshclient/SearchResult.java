// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, SearchRequest, SearchRefinementResults, BrowseTreeResult, 
//            SortTermResults, AmazonFreshBase

public class SearchResult extends FreshAPICall
{

    public SearchResult(AmazonFreshBase amazonfreshbase, SearchRequest searchrequest)
    {
        super(amazonfreshbase, "/product/search", "GET");
        searchRequest = searchrequest;
        init(searchrequest.getArgs());
    }

    public SearchResult(AmazonFreshBase amazonfreshbase, String s)
    {
        super(amazonfreshbase, s, "GET");
        searchRequest = SearchRequest.createFromURL(s);
        init();
    }

    public SearchRefinementResults getBrands()
    {
        return brands;
    }

    public BrowseTreeResult getBrowseTree()
    {
        return browseTree;
    }

    public List getItems()
    {
        return items;
    }

    public SearchRequest getSearchRequest()
    {
        return searchRequest;
    }

    public SearchRefinementResults getSpecialties()
    {
        return specialties;
    }

    public Integer getTotalResults()
    {
        return totalResults;
    }

    public boolean hasNextPage()
    {
        boolean flag;
        if(totalPages == null || currentPage == null || currentPage.intValue() >= totalPages.intValue())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public boolean hasPrevPage()
    {
        boolean flag;
        if(currentPage == null || currentPage.intValue() == 1)
            flag = false;
        else
            flag = true;
        return flag;
    }

    public SearchResult nextPage()
    {
        return new SearchResult(amazonFreshBase, searchRequest.nextPage());
    }

    public SearchResult prevPage()
    {
        SearchRequest searchrequest = searchRequest.prevPage();
        SearchResult searchresult;
        if(searchrequest == null)
            searchresult = null;
        else
            searchresult = new SearchResult(amazonFreshBase, searchrequest);
        return searchresult;
    }

    SearchRefinementResults brands;
    BrowseTreeResult browseTree;
    Integer currentPage;
    List items;
    SearchRequest searchRequest;
    SortTermResults sortTerms;
    SearchRefinementResults specialties;
    Integer totalPages;
    Integer totalResults;
}
