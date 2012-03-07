// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common;


public final class BitArray
{

    public BitArray(int i)
    {
        if(i < 1)
        {
            throw new IllegalArgumentException("size must be at least 1");
        } else
        {
            size = i;
            bits = makeArray(i);
            return;
        }
    }

    private static int[] makeArray(int i)
    {
        int j = i >> 5;
        if((i & 0x1f) != 0)
            j++;
        return new int[j];
    }

    public void clear()
    {
        int i = bits.length;
        for(int j = 0; j < i; j++)
            bits[j] = 0;

    }

    public void flip(int i)
    {
        int ai[] = bits;
        int j = i >> 5;
        ai[j] = ai[j] ^ 1 << (i & 0x1f);
    }

    public boolean get(int i)
    {
        boolean flag;
        if((bits[i >> 5] & 1 << (i & 0x1f)) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int[] getBitArray()
    {
        return bits;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isRange(int i, int j, boolean flag)
    {
        if(j < i)
            throw new IllegalArgumentException();
        if(j != i) goto _L2; else goto _L1
_L1:
        boolean flag1 = true;
_L4:
        return flag1;
_L2:
        int k = j - 1;
        int l = i >> 5;
        int i1 = k >> 5;
        int j1 = l;
        do
        {
            if(j1 > i1)
                break;
            int k1;
            int l1;
            int i2;
            int k2;
            byte byte0;
            if(j1 > l)
                k1 = 0;
            else
                k1 = i & 0x1f;
            if(j1 < i1)
                l1 = 31;
            else
                l1 = k & 0x1f;
            if(k1 == 0 && l1 == 31)
            {
                i2 = -1;
            } else
            {
                i2 = 0;
                int j2 = k1;
                while(j2 <= l1) 
                {
                    i2 |= 1 << j2;
                    j2++;
                }
            }
            k2 = i2 & bits[j1];
            if(flag)
                byte0 = i2;
            else
                byte0 = 0;
            if(k2 != byte0)
            {
                flag1 = false;
                continue; /* Loop/switch isn't completed */
            }
            j1++;
        } while(true);
        flag1 = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void reverse()
    {
        int ai[] = new int[bits.length];
        int i = size;
        for(int j = 0; j < i; j++)
            if(get(i - j - 1))
            {
                int k = j >> 5;
                ai[k] = ai[k] | 1 << (j & 0x1f);
            }

        bits = ai;
    }

    public void set(int i)
    {
        int ai[] = bits;
        int j = i >> 5;
        ai[j] = ai[j] | 1 << (i & 0x1f);
    }

    public void setBulk(int i, int j)
    {
        bits[i >> 5] = j;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(size);
        int i = 0;
        while(i < size) 
        {
            if((i & 7) == 0)
                stringbuffer.append(' ');
            char c;
            if(get(i))
                c = 'X';
            else
                c = '.';
            stringbuffer.append(c);
            i++;
        }
        return stringbuffer.toString();
    }

    public int bits[];
    public final int size;
}
