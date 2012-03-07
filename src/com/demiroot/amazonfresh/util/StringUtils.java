// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.util;

import android.content.Context;
import com.demiroot.freshclient.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtils
{

    public StringUtils()
    {
    }

    public static String getAddressHtml(Address address, boolean flag, boolean flag1)
    {
        String s = address.getAddress1();
        String s1 = address.getAddress2();
        if(s1 != null && !"".equals(s1))
            s = (new StringBuilder(String.valueOf(s))).append(" ").append(s1).toString();
        String s2;
        String s3;
        if(flag)
            s2 = (new StringBuilder(String.valueOf(s))).append("<br />").toString();
        else
            s2 = (new StringBuilder(String.valueOf(s))).append(", ").toString();
        s3 = address.getZip();
        if(flag1)
            s3 = s3.substring(0, 5);
        return (new StringBuilder(String.valueOf(s2))).append(address.getCity()).append(", ").append(address.getState()).append(" ").append(s3).toString();
    }

    public static int getLogoResourceId()
    {
        return 0x7f020017;
    }

    public static String getSaleUnitPrice(DisplayItem displayitem, Context context)
    {
        String s = displayitem.getUnitOfMeasure();
        double d = displayitem.getSpecialPricePerUnit();
        String s1;
        if(d == 0.0D || s == null)
        {
            s1 = null;
        } else
        {
            Object aobj[] = new Object[2];
            aobj[0] = price(d);
            aobj[1] = s;
            s1 = context.getString(0x7f07009a, aobj);
        }
        return s1;
    }

    public static String getUnitPrice(DisplayItem displayitem, Context context)
    {
        String s = displayitem.getUnitOfMeasure();
        double d = displayitem.getPricePerUnit();
        String s1;
        if(d == 0.0D || s == null)
        {
            s1 = null;
        } else
        {
            Object aobj[] = new Object[2];
            aobj[0] = price(d);
            aobj[1] = s;
            s1 = context.getString(0x7f070099, aobj);
        }
        return s1;
    }

    public static String hour(Date date)
    {
        return (new SimpleDateFormat("ha")).format(date);
    }

    public static String hourAndMinutes(Date date)
    {
        return (new SimpleDateFormat("h:mma")).format(date);
    }

    public static String longDate(Date date)
    {
        return (new SimpleDateFormat("EEE MMM d, yyyy")).format(date);
    }

    public static String longerDateNoYear(Date date)
    {
        return (new SimpleDateFormat("EEEE, MMMMM d")).format(date);
    }

    public static String price(double d)
    {
        StringBuilder stringbuilder = new StringBuilder("$");
        Formatter formatter = new Formatter();
        Object aobj[] = new Object[1];
        aobj[0] = Double.valueOf(d);
        return stringbuilder.append(formatter.format("%.2f", aobj).toString()).toString();
    }

    public static String slot(DeliveryWindow deliverywindow, Context context)
    {
        Object aobj[] = new Object[4];
        aobj[0] = longDate(deliverywindow.getStartTime());
        aobj[1] = hour(deliverywindow.getStartTime());
        aobj[2] = hour(deliverywindow.getEndTime());
        aobj[3] = windowType(deliverywindow, context);
        return context.getString(0x7f070097, aobj);
    }

    public static CharSequence slotDate(Calendar calendar)
    {
        return (new SimpleDateFormat("EEE MMM d")).format(calendar.getTime());
    }

    public static String slotForOrderSummary(DeliveryWindow deliverywindow, Context context)
    {
        Object aobj[] = new Object[3];
        aobj[0] = (new StringBuilder(String.valueOf(longerDateNoYear(deliverywindow.getStartTime())))).append(",").toString();
        aobj[1] = hour(deliverywindow.getStartTime()).toLowerCase();
        aobj[2] = hour(deliverywindow.getEndTime()).toLowerCase();
        return context.getString(0x7f070098, aobj);
    }

    public static String timeWindow(DeliveryWindow deliverywindow)
    {
        return (new StringBuilder(String.valueOf(hour(deliverywindow.getStartTime())))).append(" -\n").append(hour(deliverywindow.getEndTime())).toString();
    }

    public static Object windowType(DeliveryWindow deliverywindow, Context context)
    {
        String s;
        if(deliverywindow == null || deliverywindow.getType() == null)
            s = null;
        else
        if(deliverywindow.getType().equals("unattended"))
            s = context.getString(0x7f07006d);
        else
            s = context.getString(0x7f07006e);
        return s;
    }
}
