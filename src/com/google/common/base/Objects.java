// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.common.base;

import java.util.Arrays;

public final class Objects
{

    private Objects()
    {
    }

    public static boolean equal(Object obj, Object obj1)
    {
        boolean flag;
        if(obj != obj1 && (obj == null || !obj.equals(obj1)))
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static transient int hashCode(Object aobj[])
    {
        return Arrays.hashCode(aobj);
    }
}
