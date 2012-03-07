// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.Date;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, AmazonFreshBase

public class BigRadishStatus extends FreshAPICall
{

    public BigRadishStatus(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase, "/customer/bigRadish", "GET");
        init(new APIArgs(new Object[0]));
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public double getMaintenanceAmount()
    {
        return maintenanceAmount;
    }

    public boolean isBigRadish()
    {
        return isBigRadish;
    }

    Date expiryDate;
    boolean isBigRadish;
    double maintenanceAmount;
}
