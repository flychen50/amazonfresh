// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class PastPurchases extends FreshAPICall
{

    public PastPurchases(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase, "/product/pastPurchases", "GET");
        startInit();
    }

    public PastPurchases(AmazonFreshBase amazonfreshbase, String s, int i)
    {
        super(amazonfreshbase, "/product/pastPurchases", "GET");
        input = s;
        currentPage = Integer.valueOf(i);
        startInit();
    }

    private void startInit()
    {
        APIArgs apiargs = new APIArgs(new Object[0]);
        apiargs.put("searchPastText", input);
        apiargs.put("pageNumber", currentPage);
        init(apiargs);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof PastPurchases))
        {
            flag = false;
        } else
        {
            PastPurchases pastpurchases = (PastPurchases)obj;
            if((input == null && pastpurchases.input == null || input.equals(pastpurchases.input)) && (currentPage == null && pastpurchases.currentPage == null || currentPage.equals(pastpurchases.currentPage)) && (totalPages == null && pastpurchases.totalPages == null || totalPages.equals(pastpurchases.totalPages)) && (items == null && pastpurchases.items == null || items.equals(pastpurchases.items)))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public List getItems()
    {
        return items;
    }

    public int getPage()
    {
        return currentPage.intValue();
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

    public PastPurchases nextPage()
    {
        int i;
        PastPurchases pastpurchases;
        if(currentPage == null)
            i = 1;
        else
            i = currentPage.intValue();
        if(i == totalPages.intValue())
            pastpurchases = null;
        else
            pastpurchases = new PastPurchases(amazonFreshBase, input, i + 1);
        return pastpurchases;
    }

    public PastPurchases prevPage()
    {
        int i;
        PastPurchases pastpurchases;
        if(currentPage == null)
            i = 0;
        else
            i = currentPage.intValue();
        if(i == 1)
            pastpurchases = null;
        else
            pastpurchases = new PastPurchases(amazonFreshBase, input, i - 1);
        return pastpurchases;
    }

    private Integer currentPage;
    private String input;
    private List items;
    private Integer totalPages;
}
