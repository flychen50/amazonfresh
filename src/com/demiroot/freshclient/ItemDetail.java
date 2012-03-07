// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            DisplayItem, NutritionFacts, AmazonFreshBase

public class ItemDetail extends DisplayItem
{

    public ItemDetail(AmazonFreshBase amazonfreshbase, String s)
    {
        super(amazonfreshbase, (new StringBuilder("/product/")).append(s).toString(), "GET");
        init();
    }

    public List getCpsiaWarnings()
    {
        return cpsiaWarnings;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public List getLegalDisclaimers()
    {
        return legalDisclaimers;
    }

    public NutritionFacts getNutritionFacts()
    {
        return nutritionFacts;
    }

    public List getProductDetails()
    {
        return productDetails;
    }

    public List getProductFeatures()
    {
        return productFeatures;
    }

    private List cpsiaWarnings;
    private String ingredients;
    private List legalDisclaimers;
    private NutritionFacts nutritionFacts;
    private List productDetails;
    private List productFeatures;
}
