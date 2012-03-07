// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.support;

import android.text.TextUtils;
import java.util.ArrayList;

public class StringSupport
{

    public StringSupport()
    {
    }

    private static String[] splitByCharacterType(String s, boolean flag)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        String as[] = null;
_L4:
        return as;
_L2:
        char ac[];
        ArrayList arraylist;
        int i;
        int j;
        int k;
        if(s.length() == 0)
        {
            as = new String[0];
            continue; /* Loop/switch isn't completed */
        }
        ac = s.toCharArray();
        arraylist = new ArrayList();
        i = 0;
        j = Character.getType(ac[0]);
        k = 0 + 1;
_L5:
label0:
        {
            if(k < ac.length)
                break label0;
            arraylist.add(new String(ac, i, ac.length - i));
            as = (String[])arraylist.toArray(new String[arraylist.size()]);
        }
        if(true) goto _L4; else goto _L3
_L3:
        int l = Character.getType(ac[k]);
        if(l != j)
        {
            if(flag && l == 2 && j == 1)
            {
                int i1 = k - 1;
                if(i1 != i)
                {
                    arraylist.add(new String(ac, i, i1 - i));
                    i = i1;
                }
            } else
            {
                arraylist.add(new String(ac, i, k - i));
                i = k;
            }
            j = l;
        }
        k++;
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    public static String[] splitByCharacterTypeCamelCase(String s)
    {
        return splitByCharacterType(s, true);
    }

    public static String underscore(String s)
    {
        return TextUtils.join("_", splitByCharacterTypeCamelCase(s)).toLowerCase();
    }
}
