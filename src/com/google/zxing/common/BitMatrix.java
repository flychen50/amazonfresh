// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common;


// Referenced classes of package com.google.zxing.common:
//            BitArray

public final class BitMatrix
{

    public BitMatrix(int i)
    {
        this(i, i);
    }

    public BitMatrix(int i, int j)
    {
        if(i < 1 || j < 1)
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        width = i;
        height = j;
        int k = i >> 5;
        if((i & 0x1f) != 0)
            k++;
        rowSize = k;
        bits = new int[k * j];
    }

    public void clear()
    {
        int i = bits.length;
        for(int j = 0; j < i; j++)
            bits[j] = 0;

    }

    public void flip(int i, int j)
    {
        int k = j * rowSize + (i >> 5);
        int ai[] = bits;
        ai[k] = ai[k] ^ 1 << (i & 0x1f);
    }

    public boolean get(int i, int j)
    {
        int k = j * rowSize + (i >> 5);
        boolean flag;
        if((1 & bits[k] >>> (i & 0x1f)) != 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public int getDimension()
    {
        if(width != height)
            throw new RuntimeException("Can't call getDimension() on a non-square matrix");
        else
            return width;
    }

    public int getHeight()
    {
        return height;
    }

    public BitArray getRow(int i, BitArray bitarray)
    {
        if(bitarray == null || bitarray.getSize() < width)
            bitarray = new BitArray(width);
        int j = i * rowSize;
        for(int k = 0; k < rowSize; k++)
            bitarray.setBulk(k << 5, bits[j + k]);

        return bitarray;
    }

    public int getWidth()
    {
        return width;
    }

    public void set(int i, int j)
    {
        int k = j * rowSize + (i >> 5);
        int ai[] = bits;
        ai[k] = ai[k] | 1 << (i & 0x1f);
    }

    public void setRegion(int i, int j, int k, int l)
    {
        if(j < 0 || i < 0)
            throw new IllegalArgumentException("Left and top must be nonnegative");
        if(l < 1 || k < 1)
            throw new IllegalArgumentException("Height and width must be at least 1");
        int i1 = i + k;
        int j1 = j + l;
        if(j1 > height || i1 > width)
            throw new IllegalArgumentException("The region must fit inside the matrix");
        for(int k1 = j; k1 < j1; k1++)
        {
            int l1 = k1 * rowSize;
            for(int i2 = i; i2 < i1; i2++)
            {
                int ai[] = bits;
                int j2 = l1 + (i2 >> 5);
                ai[j2] = ai[j2] | 1 << (i2 & 0x1f);
            }

        }

    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(height * (1 + width));
        for(int i = 0; i < height; i++)
        {
            int j = 0;
            while(j < width) 
            {
                String s;
                if(get(j, i))
                    s = "X ";
                else
                    s = "  ";
                stringbuffer.append(s);
                j++;
            }
            stringbuffer.append('\n');
        }

        return stringbuffer.toString();
    }

    public final int bits[];
    public final int height;
    public final int rowSize;
    public final int width;
}
