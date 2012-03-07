// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.Date;
import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class SlotDay extends FreshAPICall
{

    public SlotDay(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public Date getDate()
    {
        return date;
    }

    public List getDeliveryWindows()
    {
        return deliveryTimeWindows;
    }

    Date date;
    List deliveryTimeWindows;
}
