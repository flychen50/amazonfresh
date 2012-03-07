// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common;


public final class ByteArray
{

    public ByteArray()
    {
        bytes = null;
        size = 0;
    }

    public ByteArray(int i)
    {
        bytes = new byte[i];
        size = i;
    }

    public ByteArray(byte abyte0[])
    {
        bytes = abyte0;
        size = bytes.length;
    }

    public void appendByte(int i)
    {
        if(size == 0 || size >= bytes.length)
            reserve(Math.max(32, size << 1));
        bytes[size] = (byte)i;
        size = 1 + size;
    }

    public int at(int i)
    {
        return 0xff & bytes[i];
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(size == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void reserve(int i)
    {
        if(bytes == null || bytes.length < i)
        {
            byte abyte0[] = new byte[i];
            if(bytes != null)
                System.arraycopy(bytes, 0, abyte0, 0, bytes.length);
            bytes = abyte0;
        }
    }

    public void set(int i, int j)
    {
        bytes[i] = (byte)j;
    }

    public void set(byte abyte0[], int i, int j)
    {
        bytes = new byte[j];
        size = j;
        for(int k = 0; k < j; k++)
            bytes[k] = abyte0[i + k];

    }

    public int size()
    {
        return size;
    }

    private static final int INITIAL_SIZE = 32;
    private byte bytes[];
    private int size;
}
