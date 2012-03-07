// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.common.collect;

import com.google.common.base.Objects;

abstract class AbstractMapEntry
    implements java.util.Map.Entry
{

    AbstractMapEntry()
    {
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj instanceof java.util.Map.Entry)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)obj;
            if(Objects.equal(getKey(), entry.getKey()) && Objects.equal(getValue(), entry.getValue()))
                flag = true;
            else
                flag = false;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public abstract Object getKey();

    public abstract Object getValue();

    public int hashCode()
    {
        int i = 0;
        Object obj = getKey();
        Object obj1 = getValue();
        int j;
        if(obj == null)
            j = 0;
        else
            j = obj.hashCode();
        if(obj1 != null)
            i = obj1.hashCode();
        return j ^ i;
    }

    public Object setValue(Object obj)
    {
        throw new UnsupportedOperationException();
    }

    public String toString()
    {
        return (new StringBuilder()).append(getKey()).append("=").append(getValue()).toString();
    }
}
