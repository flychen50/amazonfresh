// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.io.Serializable;

// Referenced classes of package com.demiroot.freshclient:
//            APIArgs, SearchResult, AmazonFreshBase

public class SearchRequest
    implements Cloneable, Serializable
{

    public SearchRequest()
    {
        searchTerm = null;
        browseNode = null;
        specialty = null;
        brand = null;
        listId = null;
        sort = null;
        pageNumber = null;
        resultsPerPage = null;
        filterByPP = false;
        upcPrefix = false;
        totalPages = null;
    }

    public SearchRequest(String s)
    {
        searchTerm = null;
        browseNode = null;
        specialty = null;
        brand = null;
        listId = null;
        sort = null;
        pageNumber = null;
        resultsPerPage = null;
        filterByPP = false;
        upcPrefix = false;
        totalPages = null;
        searchTerm = s;
    }

    public static SearchRequest createFromURL(String s)
    {
        SearchRequest searchrequest;
        String as[];
        int i;
        int j;
        searchrequest = new SearchRequest();
        as = s.replaceAll(".*[Ss]earch\\?", "").split("&");
        i = as.length;
        j = 0;
_L2:
        String as1[];
        if(j >= i)
            return searchrequest;
        as1 = as[j].split("=");
        if(!as1[0].equals("input"))
            break; /* Loop/switch isn't completed */
        searchrequest.searchTerm = as1[1];
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        if(as1[0].equals("browseNode"))
            searchrequest.browseNode = as1[1];
        else
        if(as1[0].equals("specialty"))
            searchrequest.specialty = as1[1];
        else
        if(as1[0].equals("brand"))
            searchrequest.brand = as1[1];
        else
        if(as1[0].equals("listId"))
            searchrequest.listId = Integer.valueOf(Integer.parseInt(as1[1]));
        else
        if(as1[0].equals("pageNumber"))
            searchrequest.pageNumber = Integer.valueOf(Integer.parseInt(as1[1]));
        else
        if(as1[0].equals("resultsPerPage"))
            searchrequest.resultsPerPage = Integer.valueOf(Integer.parseInt(as1[1]));
        else
        if(as1[0].equals("filterByPP"))
            searchrequest.filterByPP = Boolean.parseBoolean(as1[1]);
        else
        if(as1[0].equals("upcPrefix"))
            searchrequest.upcPrefix = Boolean.parseBoolean(as1[1]);
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public void clearPageNum()
    {
        pageNumber = null;
    }

    public SearchRequest clone()
    {
        SearchRequest searchrequest = (SearchRequest)super.clone();
        if(pageNumber != null) goto _L2; else goto _L1
_L1:
        int j = 1;
_L3:
        searchrequest.pageNumber = Integer.valueOf(j);
        return searchrequest;
_L2:
        int i = pageNumber.intValue();
        j = i;
          goto _L3
        CloneNotSupportedException clonenotsupportedexception;
        clonenotsupportedexception;
        throw new IllegalStateException("Clone failed in a Cloneable class. WTF! This should NEVER happen!", clonenotsupportedexception);
    }

    public volatile Object clone()
        throws CloneNotSupportedException
    {
        return clone();
    }

    public APIArgs getArgs()
    {
        APIArgs apiargs = new APIArgs(new Object[0]);
        apiargs.put("input", searchTerm);
        apiargs.put("browseNode", browseNode);
        apiargs.put("specialty", specialty);
        apiargs.put("brand", brand);
        apiargs.put("listId", listId);
        apiargs.put("pageNumber", pageNumber);
        apiargs.put("resultsPerPage", resultsPerPage);
        apiargs.put("filterByPP", filterByPP);
        apiargs.put("upcPrefix", upcPrefix);
        if(sort != null)
            apiargs.put("sort", sort);
        return apiargs;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getBrowseNode()
    {
        return browseNode;
    }

    public Integer getListId()
    {
        return listId;
    }

    public String getSearchText()
    {
        return searchTerm;
    }

    public String getSpecialty()
    {
        return specialty;
    }

    public boolean isFilterByPP()
    {
        return filterByPP;
    }

    public boolean isUpcPrefix()
    {
        return upcPrefix;
    }

    public boolean loadNextPageInParellel()
    {
        return false;
    }

    public SearchRequest nextPage()
    {
        SearchRequest searchrequest1;
        if(totalPages != null && totalPages.intValue() >= pageNumber.intValue())
        {
            searchrequest1 = null;
        } else
        {
            SearchRequest searchrequest = clone();
            searchrequest.pageNumber = Integer.valueOf(1 + searchrequest.pageNumber.intValue());
            searchrequest1 = searchrequest;
        }
        return searchrequest1;
    }

    public SearchRequest prevPage()
    {
        SearchRequest searchrequest;
        if(pageNumber == null || pageNumber.intValue() == 1)
        {
            searchrequest = null;
        } else
        {
            SearchRequest searchrequest1 = clone();
            searchrequest1.pageNumber = Integer.valueOf(searchrequest1.pageNumber.intValue() - 1);
            searchrequest = searchrequest1;
        }
        return searchrequest;
    }

    public SearchResult search(AmazonFreshBase amazonfreshbase)
    {
        return new SearchResult(amazonfreshbase, this);
    }

    public void setBrand(String s)
    {
        brand = s;
    }

    public void setBrowseNode(String s)
    {
        browseNode = s;
    }

    public void setFilterByPP(boolean flag)
    {
        filterByPP = flag;
    }

    public void setListId(Integer integer)
    {
        listId = integer;
    }

    public void setResultsPerPage(Integer integer)
    {
        resultsPerPage = integer;
    }

    public void setSearchText(String s)
    {
        searchTerm = s;
    }

    public void setSort(String s)
    {
        sort = s;
    }

    public void setSpecialty(String s)
    {
        specialty = s;
    }

    public void setUpcPrefix(boolean flag)
    {
        upcPrefix = flag;
    }

    private String brand;
    private String browseNode;
    private boolean filterByPP;
    private Integer listId;
    private Integer pageNumber;
    private Integer resultsPerPage;
    private String searchTerm;
    private String sort;
    private String specialty;
    private Integer totalPages;
    private boolean upcPrefix;
}
