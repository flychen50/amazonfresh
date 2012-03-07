// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, APIArgs, Order, AmazonFreshBase

public class DeliveryWindow extends FreshAPICall
{
    public static final class Availability extends Enum
    {

        public static Availability valueOf(String s)
        {
            return (Availability)Enum.valueOf(com/demiroot/freshclient/DeliveryWindow$Availability, s);
        }

        public static Availability[] values()
        {
            Availability aavailability[] = ENUM$VALUES;
            int i = aavailability.length;
            Availability aavailability1[] = new Availability[i];
            System.arraycopy(aavailability, 0, aavailability1, 0, i);
            return aavailability1;
        }

        public static final Availability AVAILABLE;
        private static final Availability ENUM$VALUES[];
        public static final Availability FULL;
        public static final Availability GREEN;
        public static final Availability SELECTED;
        public static final Availability UNAVAILABLE;
        public static final Availability UNKNOWN;

        static 
        {
            FULL = new Availability("FULL", 0);
            UNAVAILABLE = new Availability("UNAVAILABLE", 1);
            AVAILABLE = new Availability("AVAILABLE", 2);
            GREEN = new Availability("GREEN", 3);
            UNKNOWN = new Availability("UNKNOWN", 4);
            SELECTED = new Availability("SELECTED", 5);
            Availability aavailability[] = new Availability[6];
            aavailability[0] = FULL;
            aavailability[1] = UNAVAILABLE;
            aavailability[2] = AVAILABLE;
            aavailability[3] = GREEN;
            aavailability[4] = UNKNOWN;
            aavailability[5] = SELECTED;
            ENUM$VALUES = aavailability;
        }

        private Availability(String s, int i)
        {
            super(s, i);
        }
    }


    public DeliveryWindow(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    private DeliveryWindow(AmazonFreshBase amazonfreshbase, DeliveryWindow deliverywindow, Order order, String s)
    {
        StringBuilder stringbuilder = new StringBuilder("/slot");
        String s1;
        APIArgs apiargs;
        SimpleDateFormat simpledateformat;
        if(order == null)
            s1 = "";
        else
            s1 = (new StringBuilder("/")).append(order.getOrderId()).toString();
        super(amazonfreshbase, stringbuilder.append(s1).toString(), "POST");
        apiargs = new APIArgs(new Object[0]);
        apiargs.put("deliveryType", deliverywindow.deliveryType);
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        apiargs.put("startTime", simpledateformat.format(deliverywindow.startTime));
        apiargs.put("endTime", simpledateformat.format(deliverywindow.endTime));
        apiargs.put("externalAddressId", s);
        init(apiargs);
    }

    private DeliveryWindow(AmazonFreshBase amazonfreshbase, Order order)
    {
        StringBuilder stringbuilder = new StringBuilder("/slot/selected");
        String s;
        if(order == null)
            s = "";
        else
            s = (new StringBuilder("/")).append(order.getOrderId()).toString();
        super(amazonfreshbase, stringbuilder.append(s).toString(), "GET");
        init();
    }

    public static DeliveryWindow getSelectedSlotForCart(AmazonFreshBase amazonfreshbase)
    {
        return getSelectedSlotForOrder(amazonfreshbase, null);
    }

    public static DeliveryWindow getSelectedSlotForOrder(AmazonFreshBase amazonfreshbase, Order order)
    {
        return new DeliveryWindow(amazonfreshbase, order);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof DeliveryWindow))
        {
            flag = false;
        } else
        {
            DeliveryWindow deliverywindow = (DeliveryWindow)obj;
            if((deliveryType != null ? deliveryType.equals(deliverywindow.deliveryType) : deliverywindow.deliveryType == null) && (startTime != null ? startTime.equals(deliverywindow.startTime) : deliverywindow.startTime == null) && (endTime != null ? endTime.equals(deliverywindow.endTime) : deliverywindow.endTime == null) && (availability != null ? availability.equals(deliverywindow.availability) : deliverywindow.availability == null))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public Availability getAvailability()
    {
        Availability availability2 = Availability.valueOf(availability.toUpperCase());
        Availability availability1 = availability2;
_L2:
        return availability1;
        Exception exception;
        exception;
        availability1 = Availability.UNKNOWN;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public String getExternalAddressId()
    {
        return externalAddressId;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public String getType()
    {
        return deliveryType;
    }

    public boolean hasType()
    {
        boolean flag;
        if(deliveryType != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public DeliveryWindow useForCartOrder()
    {
        return useForOrder(null);
    }

    public DeliveryWindow useForOrder(Order order)
    {
        return new DeliveryWindow(amazonFreshBase, this, order, externalAddressId);
    }

    private String availability;
    private String deliveryType;
    private Date endTime;
    private String externalAddressId;
    private Date startTime;
}
