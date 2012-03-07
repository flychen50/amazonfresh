// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.*;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.oned:
//            AbstractOneDReader, UPCEANReader

public abstract class AbstractUPCEANReader extends AbstractOneDReader
    implements UPCEANReader
{

    protected AbstractUPCEANReader()
    {
    }

    private static boolean checkStandardUPCEANChecksum(String s)
        throws ReaderException
    {
        int i = s.length();
        boolean flag;
        if(i == 0)
        {
            flag = false;
        } else
        {
            int j = 0;
            for(int k = i - 2; k >= 0; k += -2)
            {
                int k1 = s.charAt(k) - 48;
                if(k1 < 0 || k1 > 9)
                    throw ReaderException.getInstance();
                j += k1;
            }

            int l = j * 3;
            for(int i1 = i - 1; i1 >= 0; i1 += -2)
            {
                int j1 = s.charAt(i1) - 48;
                if(j1 < 0 || j1 > 9)
                    throw ReaderException.getInstance();
                l += j1;
            }

            if(l % 10 == 0)
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    static int decodeDigit(BitArray bitarray, int ai[], int i, int ai1[][])
        throws ReaderException
    {
        recordPattern(bitarray, i, ai);
        int j = 107;
        int k = -1;
        int l = ai1.length;
        for(int i1 = 0; i1 < l; i1++)
        {
            int j1 = patternMatchVariance(ai, ai1[i1], 179);
            if(j1 < j)
            {
                j = j1;
                k = i1;
            }
        }

        if(k >= 0)
            return k;
        else
            throw ReaderException.getInstance();
    }

    static int[] findGuardPattern(BitArray bitarray, int i, boolean flag, int ai[])
        throws ReaderException
    {
        int j = ai.length;
        int ai1[] = new int[j];
        int k = bitarray.getSize();
        boolean flag1 = false;
        do
        {
label0:
            {
label1:
                {
                    int i1;
                    int j1;
                    boolean flag2;
                    int k1;
                    if(i < k)
                    {
                        int l;
                        if(!bitarray.get(i))
                            flag1 = true;
                        else
                            flag1 = false;
                        if(flag != flag1)
                            break label1;
                    }
                    l = i;
                    i1 = i;
                    j1 = 0;
                    flag2 = flag1;
                    k1 = l;
                    while(i1 < k) 
                    {
                        if(flag2 ^ bitarray.get(i1))
                        {
                            ai1[j1] = 1 + ai1[j1];
                        } else
                        {
                            if(j1 == j - 1)
                            {
                                if(patternMatchVariance(ai1, ai, 179) < 107)
                                {
                                    int ai2[] = new int[2];
                                    ai2[0] = k1;
                                    ai2[1] = i1;
                                    return ai2;
                                }
                                k1 += ai1[0] + ai1[1];
                                for(int l1 = 2; l1 < j; l1++)
                                    ai1[l1 - 2] = ai1[l1];

                                ai1[j - 2] = 0;
                                ai1[j - 1] = 0;
                                j1--;
                            } else
                            {
                                j1++;
                            }
                            ai1[j1] = 1;
                            flag2 ^= true;
                        }
                        i1++;
                    }
                    break label0;
                }
                i++;
                continue;
            }
            throw ReaderException.getInstance();
        } while(true);
    }

    static int[] findStartGuardPattern(BitArray bitarray)
        throws ReaderException
    {
        boolean flag = false;
        int ai[] = null;
        int i = 0;
        do
        {
            if(flag)
                break;
            ai = findGuardPattern(bitarray, i, false, START_END_PATTERN);
            int j = ai[0];
            i = ai[1];
            int k = j - (i - j);
            if(k >= 0)
                flag = bitarray.isRange(k, j, false);
        } while(true);
        return ai;
    }

    boolean checkChecksum(String s)
        throws ReaderException
    {
        return checkStandardUPCEANChecksum(s);
    }

    int[] decodeEnd(BitArray bitarray, int i)
        throws ReaderException
    {
        return findGuardPattern(bitarray, i, false, START_END_PATTERN);
    }

    protected abstract int decodeMiddle(BitArray bitarray, int ai[], StringBuffer stringbuffer)
        throws ReaderException;

    public final Result decodeRow(int i, BitArray bitarray, Hashtable hashtable)
        throws ReaderException
    {
        return decodeRow(i, bitarray, findStartGuardPattern(bitarray));
    }

    public final Result decodeRow(int i, BitArray bitarray, int ai[])
        throws ReaderException
    {
        StringBuffer stringbuffer = decodeRowStringBuffer;
        stringbuffer.setLength(0);
        int ai1[] = decodeEnd(bitarray, decodeMiddle(bitarray, ai, stringbuffer));
        int j = ai1[1];
        int k = j + (j - ai1[0]);
        if(k >= bitarray.getSize() || !bitarray.isRange(j, k, false))
            throw ReaderException.getInstance();
        String s = stringbuffer.toString();
        if(!checkChecksum(s))
        {
            throw ReaderException.getInstance();
        } else
        {
            float f = (float)(ai[1] + ai[0]) / 2.0F;
            float f1 = (float)(ai1[1] + ai1[0]) / 2.0F;
            ResultPoint aresultpoint[] = new ResultPoint[2];
            ResultPoint resultpoint = new ResultPoint(f, i);
            aresultpoint[0] = resultpoint;
            ResultPoint resultpoint1 = new ResultPoint(f1, i);
            aresultpoint[1] = resultpoint1;
            return new Result(s, null, aresultpoint, getBarcodeFormat());
        }
    }

    abstract BarcodeFormat getBarcodeFormat();

    static final int L_AND_G_PATTERNS[][];
    static final int L_PATTERNS[][];
    private static final int MAX_AVG_VARIANCE = 107;
    private static final int MAX_INDIVIDUAL_VARIANCE = 179;
    static final int MIDDLE_PATTERN[];
    static final int START_END_PATTERN[];
    private final StringBuffer decodeRowStringBuffer = new StringBuffer(20);

    static 
    {
        int ai[] = new int[3];
        ai[0] = 1;
        ai[1] = 1;
        ai[2] = 1;
        START_END_PATTERN = ai;
        int ai1[] = new int[5];
        ai1[0] = 1;
        ai1[1] = 1;
        ai1[2] = 1;
        ai1[3] = 1;
        ai1[4] = 1;
        MIDDLE_PATTERN = ai1;
        int ai2[][] = new int[10][];
        int ai3[] = new int[4];
        ai3[0] = 3;
        ai3[1] = 2;
        ai3[2] = 1;
        ai3[3] = 1;
        ai2[0] = ai3;
        int ai4[] = new int[4];
        ai4[0] = 2;
        ai4[1] = 2;
        ai4[2] = 2;
        ai4[3] = 1;
        ai2[1] = ai4;
        int ai5[] = new int[4];
        ai5[0] = 2;
        ai5[1] = 1;
        ai5[2] = 2;
        ai5[3] = 2;
        ai2[2] = ai5;
        int ai6[] = new int[4];
        ai6[0] = 1;
        ai6[1] = 4;
        ai6[2] = 1;
        ai6[3] = 1;
        ai2[3] = ai6;
        int ai7[] = new int[4];
        ai7[0] = 1;
        ai7[1] = 1;
        ai7[2] = 3;
        ai7[3] = 2;
        ai2[4] = ai7;
        int ai8[] = new int[4];
        ai8[0] = 1;
        ai8[1] = 2;
        ai8[2] = 3;
        ai8[3] = 1;
        ai2[5] = ai8;
        int ai9[] = new int[4];
        ai9[0] = 1;
        ai9[1] = 1;
        ai9[2] = 1;
        ai9[3] = 4;
        ai2[6] = ai9;
        int ai10[] = new int[4];
        ai10[0] = 1;
        ai10[1] = 3;
        ai10[2] = 1;
        ai10[3] = 2;
        ai2[7] = ai10;
        int ai11[] = new int[4];
        ai11[0] = 1;
        ai11[1] = 2;
        ai11[2] = 1;
        ai11[3] = 3;
        ai2[8] = ai11;
        int ai12[] = new int[4];
        ai12[0] = 3;
        ai12[1] = 1;
        ai12[2] = 1;
        ai12[3] = 2;
        ai2[9] = ai12;
        L_PATTERNS = ai2;
        L_AND_G_PATTERNS = new int[20][];
        for(int i = 0; i < 10; i++)
            L_AND_G_PATTERNS[i] = L_PATTERNS[i];

        for(int j = 10; j < 20; j++)
        {
            int ai13[] = L_PATTERNS[j - 10];
            int ai14[] = new int[ai13.length];
            for(int k = 0; k < ai13.length; k++)
                ai14[k] = ai13[ai13.length - k - 1];

            L_AND_G_PATTERNS[j] = ai14;
        }

    }
}
