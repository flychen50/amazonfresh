// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.*;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.oned:
//            AbstractOneDReader

public final class Code39Reader extends AbstractOneDReader
{

    public Code39Reader()
    {
        usingCheckDigit = false;
        extendedMode = false;
    }

    public Code39Reader(boolean flag)
    {
        usingCheckDigit = flag;
        extendedMode = false;
    }

    public Code39Reader(boolean flag, boolean flag1)
    {
        usingCheckDigit = flag;
        extendedMode = flag1;
    }

    private static String decodeExtended(String s)
        throws ReaderException
    {
        int i;
        StringBuffer stringbuffer;
        int j;
        i = s.length();
        stringbuffer = new StringBuffer(i);
        j = 0;
_L8:
        char c;
        char c1;
        int k;
        if(j >= i)
            break MISSING_BLOCK_LABEL_288;
        c = s.charAt(j);
        if(c != '+' && c != '$' && c != '%' && c != '/')
            break MISSING_BLOCK_LABEL_278;
        c1 = s.charAt(j + 1);
        k = 0;
        c;
        JVM INSTR lookupswitch 4: default 112
    //                   36: 156
    //                   37: 184
    //                   43: 128
    //                   47: 236;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        stringbuffer.append(k);
        j++;
_L6:
        j++;
        continue; /* Loop/switch isn't completed */
_L4:
        if(c1 >= 'A' && c1 <= 'Z')
            k = c1 + 32;
        else
            throw ReaderException.getInstance();
          goto _L1
_L2:
        if(c1 >= 'A' && c1 <= 'Z')
            k = c1 - 64;
        else
            throw ReaderException.getInstance();
          goto _L1
_L3:
        if(c1 >= 'A' && c1 <= 'E')
            k = c1 - 38;
        else
        if(c1 >= 'F' && c1 <= 'W')
            k = c1 - 11;
        else
            throw ReaderException.getInstance();
          goto _L1
_L5:
        if(c1 >= 'A' && c1 <= 'O')
            k = c1 - 32;
        else
        if(c1 == 'Z')
            k = 58;
        else
            throw ReaderException.getInstance();
          goto _L1
        stringbuffer.append(c);
          goto _L6
        return stringbuffer.toString();
        if(true) goto _L8; else goto _L7
_L7:
    }

    private static int[] findAsteriskPattern(BitArray bitarray)
        throws ReaderException
    {
        int i;
        int j;
        i = bitarray.getSize();
        j = 0;
_L8:
        if(j < i && !bitarray.get(j)) goto _L2; else goto _L1
_L1:
        int k;
        int ai[];
        int l;
        boolean flag;
        int i1;
        int j1;
        k = 0;
        ai = new int[9];
        l = j;
        flag = false;
        i1 = ai.length;
        j1 = j;
_L4:
        if(j1 >= i)
            break MISSING_BLOCK_LABEL_228;
        if(!(flag ^ bitarray.get(j1)))
            break; /* Loop/switch isn't completed */
        ai[k] = 1 + ai[k];
_L5:
        j1++;
        if(true) goto _L4; else goto _L3
_L2:
        j++;
        continue; /* Loop/switch isn't completed */
_L3:
        if(k != i1 - 1)
            break MISSING_BLOCK_LABEL_222;
        int ai1[];
        if(toNarrowWidePattern(ai) != ASTERISK_ENCODING || !bitarray.isRange(Math.max(0, l - (j1 - l) / 2), l, false))
            break MISSING_BLOCK_LABEL_147;
        ai1 = new int[2];
        ai1[0] = l;
        ai1[1] = j1;
        return ai1;
        ReaderException readerexception;
        readerexception;
        l += ai[0] + ai[1];
        for(int k1 = 2; k1 < i1; k1++)
            ai[k1 - 2] = ai[k1];

        ai[i1 - 2] = 0;
        ai[i1 - 1] = 0;
        k--;
_L6:
        ai[k] = 1;
        flag ^= true;
          goto _L5
        k++;
          goto _L6
        throw ReaderException.getInstance();
        if(true) goto _L8; else goto _L7
_L7:
    }

    private static char patternToChar(int i)
        throws ReaderException
    {
        for(int j = 0; j < CHARACTER_ENCODINGS.length; j++)
            if(CHARACTER_ENCODINGS[j] == i)
                return ALPHABET[j];

        throw ReaderException.getInstance();
    }

    private static int toNarrowWidePattern(int ai[])
        throws ReaderException
    {
        int i = ai.length;
        int j = 0;
        int k1;
        do
        {
            int k = 0x7fffffff;
            for(int l = 0; l < i; l++)
            {
                int l2 = ai[l];
                if(l2 < k && l2 > j)
                    k = l2;
            }

            j = k;
            int i1 = 0;
            int j1 = 0;
            k1 = 0;
            for(int l1 = 0; l1 < i; l1++)
            {
                int k2 = ai[l1];
                if(ai[l1] > j)
                {
                    k1 |= 1 << i - 1 - l1;
                    i1++;
                    j1 += k2;
                }
            }

            if(i1 == 3)
            {
                for(int i2 = 0; i2 < i && i1 > 0; i2++)
                {
                    int j2 = ai[i2];
                    if(ai[i2] > j)
                    {
                        i1--;
                        if(j2 << 1 >= j1)
                            throw ReaderException.getInstance();
                    }
                }

                break;
            }
            if(i1 <= 3)
                throw ReaderException.getInstance();
        } while(true);
        return k1;
    }

    public Result decodeRow(int i, BitArray bitarray, Hashtable hashtable)
        throws ReaderException
    {
        int ai[] = findAsteriskPattern(bitarray);
        int j = ai[1];
        int k;
        for(k = bitarray.getSize(); j < k && !bitarray.get(j); j++);
        StringBuffer stringbuffer = new StringBuffer();
        int ai1[] = new int[9];
        char c;
        int l;
        do
        {
            recordPattern(bitarray, j, ai1);
            c = patternToChar(toNarrowWidePattern(ai1));
            stringbuffer.append(c);
            l = j;
            int i1 = 0;
            do
            {
                int j1 = ai1.length;
                if(i1 >= j1)
                    break;
                j += ai1[i1];
                i1++;
            } while(true);
            for(; j < k && !bitarray.get(j); j++);
        } while(c != '*');
        stringbuffer.deleteCharAt(stringbuffer.length() - 1);
        int k1 = 0;
        int l1 = 0;
        do
        {
            int i2 = ai1.length;
            if(l1 >= i2)
                break;
            k1 += ai1[l1];
            l1++;
        } while(true);
        int j2 = j - l - k1;
        if(j != k && j2 / 2 < k1)
            throw ReaderException.getInstance();
        if(usingCheckDigit)
        {
            int k2 = stringbuffer.length() - 1;
            int l2 = 0;
            for(int i3 = 0; i3 < k2; i3++)
                l2 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(stringbuffer.charAt(i3));

            if(l2 % 43 != "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(stringbuffer.charAt(k2)))
                throw ReaderException.getInstance();
            stringbuffer.deleteCharAt(k2);
        }
        String s = stringbuffer.toString();
        if(extendedMode)
            s = decodeExtended(s);
        if(s.length() == 0)
        {
            throw ReaderException.getInstance();
        } else
        {
            float f = (float)(ai[1] + ai[0]) / 2.0F;
            float f1 = (float)(j + l) / 2.0F;
            ResultPoint aresultpoint[] = new ResultPoint[2];
            ResultPoint resultpoint = new ResultPoint(f, i);
            aresultpoint[0] = resultpoint;
            ResultPoint resultpoint1 = new ResultPoint(f1, i);
            aresultpoint[1] = resultpoint1;
            BarcodeFormat barcodeformat = BarcodeFormat.CODE_39;
            Result result = new Result(s, null, aresultpoint, barcodeformat);
            return result;
        }
    }

    private static final char ALPHABET[] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    private static final int ASTERISK_ENCODING;
    private static final int CHARACTER_ENCODINGS[];
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static 
    {
        int ai[] = new int[44];
        ai[0] = 52;
        ai[1] = 289;
        ai[2] = 97;
        ai[3] = 352;
        ai[4] = 49;
        ai[5] = 304;
        ai[6] = 112;
        ai[7] = 37;
        ai[8] = 292;
        ai[9] = 100;
        ai[10] = 265;
        ai[11] = 73;
        ai[12] = 328;
        ai[13] = 25;
        ai[14] = 280;
        ai[15] = 88;
        ai[16] = 13;
        ai[17] = 268;
        ai[18] = 76;
        ai[19] = 28;
        ai[20] = 259;
        ai[21] = 67;
        ai[22] = 322;
        ai[23] = 19;
        ai[24] = 274;
        ai[25] = 82;
        ai[26] = 7;
        ai[27] = 262;
        ai[28] = 70;
        ai[29] = 22;
        ai[30] = 385;
        ai[31] = 193;
        ai[32] = 448;
        ai[33] = 145;
        ai[34] = 400;
        ai[35] = 208;
        ai[36] = 133;
        ai[37] = 388;
        ai[38] = 196;
        ai[39] = 148;
        ai[40] = 168;
        ai[41] = 162;
        ai[42] = 138;
        ai[43] = 42;
        CHARACTER_ENCODINGS = ai;
        ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];
    }
}
