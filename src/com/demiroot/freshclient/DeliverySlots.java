// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, SlotDay, Address, 
//            AmazonFreshBase

public class DeliverySlots extends FreshAPICall
{

    public DeliverySlots(AmazonFreshBase amazonfreshbase)
    {
        this(amazonfreshbase, null, null);
    }

    public DeliverySlots(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder("/slot/available");
        String s2;
        APIArgs apiargs;
        if(s == null)
            s2 = "";
        else
            s2 = (new StringBuilder("/")).append(s).toString();
        super(amazonfreshbase, stringbuilder.append(s2).toString(), "GET");
        apiargs = new APIArgs(new Object[0]);
        if(s1 != null)
            apiargs.put("externalAddressId", s1);
        init(apiargs);
    }

    private SlotDay getForDate(List list, Date date)
    {
        DateFormat dateformat;
        Iterator iterator;
        dateformat = SimpleDateFormat.getDateInstance();
        iterator = list.iterator();
_L2:
        SlotDay slotday1;
        if(!iterator.hasNext())
        {
            slotday1 = null;
        } else
        {
            SlotDay slotday = (SlotDay)iterator.next();
            if(!dateformat.format(date).equals(dateformat.format(slotday.getDate())))
                continue; /* Loop/switch isn't completed */
            slotday1 = slotday;
        }
        return slotday1;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Address getAddress()
    {
        return address;
    }

    public List getAttended()
    {
        return attended;
    }

    public SlotDay getAttendedForDay(Date date)
    {
        return getForDate(attended, date);
    }

    public List getUnattended()
    {
        return unattended;
    }

    public SlotDay getUnattendedForDay(Date date)
    {
        return getForDate(unattended, date);
    }

    Address address;
    List attended;
    List unattended;
}
