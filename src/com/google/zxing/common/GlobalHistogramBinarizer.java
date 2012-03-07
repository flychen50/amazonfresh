// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common;

import com.google.zxing.*;

// Referenced classes of package com.google.zxing.common:
//            BitMatrix, BitArray

public final class GlobalHistogramBinarizer extends Binarizer
{

    public GlobalHistogramBinarizer(LuminanceSource luminancesource)
    {
        super(luminancesource);
        luminances = null;
        buckets = null;
    }

    private static int estimateBlackPoint(int ai[])
        throws ReaderException
    {
        int i = ai.length;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        while(j < i) 
        {
            int j1;
            int k1;
            int l1;
            int i2;
            int j2;
            int k2;
            int l2;
            int i3;
            int j3;
            int k3;
            int l3;
            int i4;
            int j4;
            int k4;
            int l4;
            int i5;
            int k5;
            int l5;
            int i6;
            int j6;
            if(ai[j] > l)
            {
                k5 = j;
                l5 = ai[j];
            } else
            {
                int j5 = l;
                k5 = i1;
                l5 = j5;
            }
            if(ai[j] > k)
                i6 = ai[j];
            else
                i6 = k;
            j++;
            k = i6;
            j6 = k5;
            l = l5;
            i1 = j6;
        }
        j1 = 0;
        k1 = 0;
        l1 = 0;
        while(j1 < i) 
        {
            j4 = j1 - i1;
            k4 = j4 * (j4 * ai[j1]);
            if(k4 > k1)
            {
                l4 = j1;
                i5 = k4;
            } else
            {
                l4 = l1;
                i5 = k1;
            }
            j1++;
            k1 = i5;
            l1 = l4;
        }
        if(i1 > l1)
        {
            l3 = i1;
            i4 = l1;
            i2 = l3;
            j2 = i4;
        } else
        {
            i2 = l1;
            j2 = i1;
        }
        if(i2 - j2 <= i >> 4)
            throw ReaderException.getInstance();
        k2 = i2 - 1;
        l2 = -1;
        for(i3 = i2 - 1; i3 > j2; i3--)
        {
            j3 = i3 - j2;
            k3 = j3 * j3 * (i2 - i3) * (k - ai[i3]);
            if(k3 > l2)
            {
                k2 = i3;
                l2 = k3;
            }
        }

        return k2 << 3;
    }

    private void initArrays(int i)
    {
        if(luminances == null || luminances.length < i)
            luminances = new byte[i];
        if(buckets == null)
        {
            buckets = new int[32];
        } else
        {
            int j = 0;
            while(j < 32) 
            {
                buckets[j] = 0;
                j++;
            }
        }
    }

    public Binarizer createBinarizer(LuminanceSource luminancesource)
    {
        return new GlobalHistogramBinarizer(luminancesource);
    }

    public BitMatrix getBlackMatrix()
        throws ReaderException
    {
        LuminanceSource luminancesource = getLuminanceSource();
        int i = luminancesource.getWidth();
        int j = luminancesource.getHeight();
        BitMatrix bitmatrix = new BitMatrix(i, j);
        initArrays(i);
        int ai[] = buckets;
        for(int k = 1; k < 5; k++)
        {
            byte abyte1[] = luminancesource.getRow((j * k) / 5, luminances);
            int l1 = (i << 2) / 5;
            for(int i2 = i / 5; i2 < l1; i2++)
            {
                int j2 = (0xff & abyte1[i2]) >> 3;
                ai[j2] = 1 + ai[j2];
            }

        }

        int l = estimateBlackPoint(ai);
        byte abyte0[] = luminancesource.getMatrix();
        for(int i1 = 0; i1 < j; i1++)
        {
            int j1 = i1 * i;
            for(int k1 = 0; k1 < i; k1++)
                if((0xff & abyte0[j1 + k1]) < l)
                    bitmatrix.set(k1, i1);

        }

        return bitmatrix;
    }

    public BitArray getBlackRow(int i, BitArray bitarray)
        throws ReaderException
    {
        LuminanceSource luminancesource = getLuminanceSource();
        int j = luminancesource.getWidth();
        byte abyte0[];
        int ai[];
        if(bitarray == null || bitarray.getSize() < j)
            bitarray = new BitArray(j);
        else
            bitarray.clear();
        initArrays(j);
        abyte0 = luminancesource.getRow(i, luminances);
        ai = buckets;
        for(int k = 0; k < j; k++)
        {
            int i2 = (0xff & abyte0[k]) >> 3;
            ai[i2] = 1 + ai[i2];
        }

        int l = estimateBlackPoint(ai);
        int i1 = 0xff & abyte0[0];
        int j1 = 0xff & abyte0[1];
        for(int k1 = 1; k1 < j - 1; k1++)
        {
            int l1 = 0xff & abyte0[k1 + 1];
            if((j1 << 2) - i1 - l1 >> 1 < l)
                bitarray.set(k1);
            i1 = j1;
            j1 = l1;
        }

        return bitarray;
    }

    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private int buckets[];
    private byte luminances[];
}
