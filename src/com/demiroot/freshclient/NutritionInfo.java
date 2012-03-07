// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class NutritionInfo extends FreshAPICall
{

    public NutritionInfo(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
        shouldHighlightName = false;
    }

    public String getName()
    {
        return name;
    }

    public List getValues()
    {
        return values;
    }

    public boolean isShouldHighlightName()
    {
        return shouldHighlightName;
    }

    private String name;
    private boolean shouldHighlightName;
    private List values;
}
