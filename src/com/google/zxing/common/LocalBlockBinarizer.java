// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import java.lang.reflect.Array;

// Referenced classes of package com.google.zxing.common:
//            BitMatrix, BitArray

public final class LocalBlockBinarizer extends Binarizer
{

    public LocalBlockBinarizer(LuminanceSource luminancesource)
    {
        super(luminancesource);
        matrix = null;
    }

    private void binarizeEntireImage()
    {
        if(matrix == null)
        {
            LuminanceSource luminancesource = getLuminanceSource();
            byte abyte0[] = luminancesource.getMatrix();
            int i = luminancesource.getWidth();
            int j = luminancesource.getHeight();
            sharpenRow(abyte0, i, j);
            int k = i >> 3;
            int l = j >> 3;
            int ai[][] = calculateBlackPoints(abyte0, k, l, i);
            matrix = new BitMatrix(i, j);
            calculateThresholdForBlock(abyte0, k, l, i, ai, matrix);
        }
    }

    private static int[][] calculateBlackPoints(byte abyte0[], int i, int j, int k)
    {
        int ai[] = new int[2];
        ai[0] = j;
        ai[1] = i;
        int ai1[][] = (int[][])Array.newInstance(Integer.TYPE, ai);
        for(int l = 0; l < j; l++)
        {
            int i1 = 0;
            while(i1 < i) 
            {
                int j1 = 255;
                int k1 = 0;
                int l1 = 0;
                int i2;
                int i3;
                for(i2 = 0; l1 < 8; i2 = i3)
                {
                    int k2 = k * (l1 + (l << 3)) + (i1 << 3);
                    int l2 = 0;
                    i3 = i2;
                    for(; l2 < 8; l2++)
                    {
                        int j3 = 0xff & abyte0[k2 + l2];
                        i3 += j3;
                        if(j3 < j1)
                            j1 = j3;
                        if(j3 > k1)
                            k1 = j3;
                    }

                    l1++;
                }

                int j2;
                if(k1 - j1 > 24)
                    j2 = i2 >> 6;
                else
                    j2 = j1 >> 1;
                ai1[l][i1] = j2;
                i1++;
            }
        }

        return ai1;
    }

    private static void calculateThresholdForBlock(byte abyte0[], int i, int j, int k, int ai[][], BitMatrix bitmatrix)
    {
        for(int l = 0; l < j; l++)
        {
            for(int i1 = 0; i1 < i; i1++)
            {
                int j1;
                int k1;
                int l1;
                int i2;
                if(i1 > 1)
                    j1 = i1;
                else
                    j1 = 2;
                if(j1 >= i - 2)
                    j1 = i - 3;
                if(l > 1)
                    k1 = l;
                else
                    k1 = 2;
                if(k1 < j - 2)
                    l1 = k1;
                else
                    l1 = j - 3;
                i2 = 0;
                for(int j2 = -2; j2 <= 2; j2++)
                    i2 = i2 + ai[l1 + j2][j1 - 2] + ai[l1 + j2][j1 - 1] + ai[l1 + j2][j1] + ai[l1 + j2][j1 + 1] + ai[l1 + j2][j1 + 2];

                int k2 = i2 / 25;
                threshold8x8Block(abyte0, i1 << 3, l << 3, k2, k, bitmatrix);
            }

        }

    }

    private static void sharpenRow(byte abyte0[], int i, int j)
    {
        int k = 0;
        do
        {
            if(k >= j)
                break;
            int l = k * i;
            int i1 = 0xff & abyte0[l];
            int j1 = 0xff & abyte0[l + 1];
            int k1 = 1;
            while(k1 < i - 1) 
            {
                int l1 = 0xff & abyte0[1 + (l + k1)];
                int i2 = (j1 << 2) - i1 - l1 >> 1;
                if(i2 > 255)
                    i2 = 255;
                else
                if(i2 < 0)
                    i2 = 0;
                abyte0[l + k1] = (byte)i2;
                i1 = j1;
                j1 = l1;
                k1++;
            }
            k++;
        } while(true);
    }

    private static void threshold8x8Block(byte abyte0[], int i, int j, int k, int l, BitMatrix bitmatrix)
    {
        for(int i1 = 0; i1 < 8; i1++)
        {
            int j1 = i + l * (j + i1);
            for(int k1 = 0; k1 < 8; k1++)
                if((0xff & abyte0[j1 + k1]) < k)
                    bitmatrix.set(i + k1, j + i1);

        }

    }

    public Binarizer createBinarizer(LuminanceSource luminancesource)
    {
        return new LocalBlockBinarizer(luminancesource);
    }

    public BitMatrix getBlackMatrix()
    {
        binarizeEntireImage();
        return matrix;
    }

    public BitArray getBlackRow(int i, BitArray bitarray)
    {
        binarizeEntireImage();
        return matrix.getRow(i, bitarray);
    }

    private BitMatrix matrix;
}
