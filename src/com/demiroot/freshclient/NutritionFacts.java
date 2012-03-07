// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class NutritionFacts extends FreshAPICall
{

    public NutritionFacts(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public List getExtraAttributes()
    {
        return extraAttributes;
    }

    public List getNutrients()
    {
        return nutrients;
    }

    public String getServingSize()
    {
        return servingSize;
    }

    public List getVariants()
    {
        return variants;
    }

    private List extraAttributes;
    private List nutrients;
    private String servingSize;
    private List variants;
}
