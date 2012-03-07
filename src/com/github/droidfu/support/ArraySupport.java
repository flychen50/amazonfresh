// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.support;

import java.lang.reflect.Array;

public class ArraySupport
{

    public ArraySupport()
    {
    }

    public static Object[] delete(Object aobj[], int i)
    {
        int j = aobj.length;
        if(i < 0 || i >= j)
            throw new IndexOutOfBoundsException((new StringBuilder("Index: ")).append(i).append(", Length: ").append(j).toString());
        Object aobj1[] = (Object[])Array.newInstance(((Object) (aobj)).getClass().getComponentType(), j - 1);
        System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj1)), 0, i);
        if(i < j - 1)
            System.arraycopy(((Object) (aobj)), i + 1, ((Object) (aobj1)), i, j - i - 1);
        return aobj1;
    }

    public static int find(Object aobj[], Object obj)
    {
        int i = 0;
_L6:
        if(i < aobj.length) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(!aobj[i].equals(obj))
            break; /* Loop/switch isn't completed */
        j = i;
        if(true) goto _L4; else goto _L3
_L3:
        i++;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static Object[] join(Object aobj[], Object aobj1[])
    {
        Object aobj3[];
        if(aobj == null)
            aobj3 = aobj1;
        else
        if(aobj1 == null)
        {
            aobj3 = aobj;
        } else
        {
            Object aobj2[] = (Object[])Array.newInstance(((Object) (aobj)).getClass().getComponentType(), aobj.length + aobj1.length);
            System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj2)), 0, aobj.length);
            System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj2)), aobj.length, aobj1.length);
            aobj3 = aobj2;
        }
        return aobj3;
    }
}
