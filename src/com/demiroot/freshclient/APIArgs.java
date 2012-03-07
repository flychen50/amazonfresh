// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.*;

public class APIArgs extends HashMap
{

    public transient APIArgs(Object aobj[])
    {
        int i = 0;
        do
        {
            if(i >= aobj.length)
                return;
            put((String)aobj[i], aobj[i + 1]);
            i += 2;
        } while(true);
    }

    public volatile Object put(Object obj, Object obj1)
    {
        return put((String)obj, (String)obj1);
    }

    public String put(String s, Object obj)
    {
        String s1;
        if(obj == null)
            s1 = (String)super.remove(s);
        else
            s1 = put_real(s, obj.toString());
        return s1;
    }

    public String put(String s, String s1)
    {
        return put_real(s, s1);
    }

    public String put(String s, boolean flag)
    {
        return put_real(s, Boolean.toString(flag));
    }

    public String put_real(String s, String s1)
    {
        String s2;
        if(s1 == null)
            s2 = (String)super.remove(s);
        else
            s2 = (String)super.put(s, s1);
        return s2;
    }

    public String toString()
    {
        String s = "";
        Iterator iterator = entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                return s;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            s = (new StringBuilder(String.valueOf(s))).append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("\n").toString();
        } while(true);
    }

    private static final long serialVersionUID = 1L;
}
