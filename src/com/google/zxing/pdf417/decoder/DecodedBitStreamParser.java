// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.pdf417.decoder;

import com.google.zxing.ReaderException;
import com.google.zxing.common.DecoderResult;

final class DecodedBitStreamParser
{

    private DecodedBitStreamParser()
    {
    }

    private static StringBuffer add(String s, String s1)
    {
        StringBuffer stringbuffer = new StringBuffer(5);
        StringBuffer stringbuffer1 = new StringBuffer(5);
        StringBuffer stringbuffer2 = new StringBuffer(s.length());
        for(int i = 0; i < s.length(); i++)
            stringbuffer2.append('0');

        int j = 0;
        for(int k = s.length() - 3; k > -1; k -= 3)
        {
            stringbuffer.setLength(0);
            stringbuffer.append(s.charAt(k));
            stringbuffer.append(s.charAt(k + 1));
            stringbuffer.append(s.charAt(k + 2));
            stringbuffer1.setLength(0);
            stringbuffer1.append(s1.charAt(k));
            stringbuffer1.append(s1.charAt(k + 1));
            stringbuffer1.append(s1.charAt(k + 2));
            int l = Integer.parseInt(stringbuffer.toString());
            int i1 = Integer.parseInt(stringbuffer1.toString());
            int j1 = (j + (l + i1)) % 1000;
            j = (j + (l + i1)) / 1000;
            stringbuffer2.setCharAt(k + 2, (char)(48 + j1 % 10));
            stringbuffer2.setCharAt(k + 1, (char)(48 + (j1 / 10) % 10));
            stringbuffer2.setCharAt(k, (char)(48 + j1 / 100));
        }

        return stringbuffer2;
    }

    private static int byteCompaction(int i, int ai[], int j, StringBuffer stringbuffer)
    {
        if(i == 901)
        {
            char ac1[] = new char[6];
            int ai1[] = new int[6];
            int k2 = 0;
            long l2 = 0L;
            boolean flag5;
            for(boolean flag3 = false; j < ai[0] && !flag3; flag3 = flag5)
            {
                int j3 = j + 1;
                int k3 = ai[j];
                int l3;
                boolean flag4;
                long l4;
                if(k3 < 900)
                {
                    ai1[k2] = k3;
                    int j4 = k2 + 1;
                    long l5 = l2 * 900L + (long)k3;
                    j = j3;
                    l3 = j4;
                    flag4 = flag3;
                    l4 = l5;
                } else
                {
                    if(k3 != 900 && k3 != 901 && k3 != 902 && k3 != 924 && k3 != 928 && k3 != 923)
                        if(k3 != 922);
                    j = j3 - 1;
                    l3 = k2;
                    flag4 = true;
                    l4 = l2;
                }
                if(l3 % 5 == 0 && l3 > 0)
                {
                    for(int i4 = 0; i4 < 6; i4++)
                    {
                        ac1[5 - i4] = (char)(int)(l4 % 256L);
                        l4 >>= 8;
                    }

                    stringbuffer.append(ac1);
                    l3 = 0;
                }
                flag5 = flag4;
                k2 = l3;
                l2 = l4;
            }

            for(int i3 = 5 * (k2 / 5); i3 < k2; i3++)
                stringbuffer.append((char)ai1[i3]);

        } else
        if(i == 924)
        {
            long l = 0L;
            boolean flag = false;
            int k = 0;
            boolean flag2;
            for(; j < ai[0] && !flag; flag = flag2)
            {
                int i1 = j + 1;
                int j1 = ai[j];
                int k1;
                boolean flag1;
                if(j1 < 900)
                {
                    int j2 = k + 1;
                    l = l * 900L + (long)j1;
                    flag1 = flag;
                    k1 = j2;
                    j = i1;
                } else
                {
                    if(j1 != 900 && j1 != 901 && j1 != 902 && j1 != 924 && j1 != 928 && j1 != 923)
                        if(j1 != 922);
                    j = i1 - 1;
                    k1 = k;
                    flag1 = true;
                }
                if(k1 % 5 == 0 && k1 > 0)
                {
                    char ac[] = new char[6];
                    for(int i2 = 0; i2 < 6; i2++)
                    {
                        ac[5 - i2] = (char)(int)(l % 256L);
                        l >>= 8;
                    }

                    stringbuffer.append(ac);
                }
                long l1 = l;
                flag2 = flag1;
                k = k1;
                l = l1;
            }

        }
        return j;
    }

    static DecoderResult decode(int ai[])
        throws ReaderException
    {
        StringBuffer stringbuffer;
        int j;
        int k;
        stringbuffer = new StringBuffer(100);
        int i = 1 + 1;
        j = ai[1];
        k = i;
_L7:
        if(k >= ai[0])
            break MISSING_BLOCK_LABEL_183;
        j;
        JVM INSTR lookupswitch 5: default 80
    //                   900: 116
    //                   901: 128
    //                   902: 141
    //                   913: 153
    //                   924: 166;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        int l = textCompaction(ai, k - 1, stringbuffer);
_L8:
        if(l < ai.length)
        {
            int i1 = l + 1;
            j = ai[l];
            k = i1;
        } else
        {
            throw ReaderException.getInstance();
        }
        if(true) goto _L7; else goto _L2
_L2:
        l = textCompaction(ai, k, stringbuffer);
          goto _L8
_L3:
        l = byteCompaction(j, ai, k, stringbuffer);
          goto _L8
_L4:
        l = numericCompaction(ai, k, stringbuffer);
          goto _L8
_L5:
        l = byteCompaction(j, ai, k, stringbuffer);
          goto _L8
_L6:
        l = byteCompaction(j, ai, k, stringbuffer);
          goto _L8
        return new DecoderResult(null, stringbuffer.toString(), null, null);
    }

    private static String decodeBase900toBase10(int ai[], int i)
    {
        StringBuffer stringbuffer = null;
        int j = 0;
        while(j < i) 
        {
            StringBuffer stringbuffer1 = multiply(EXP900[i - j - 1], ai[j]);
            if(stringbuffer == null)
                stringbuffer = stringbuffer1;
            else
                stringbuffer = add(stringbuffer.toString(), stringbuffer1.toString());
            j++;
        }
        String s = null;
        int k = 0;
        do
        {
label0:
            {
                if(k < stringbuffer.length())
                {
                    if(stringbuffer.charAt(k) != '1')
                        break label0;
                    s = stringbuffer.toString().substring(k + 1);
                }
                if(s == null)
                    s = stringbuffer.toString();
                return s;
            }
            k++;
        } while(true);
    }

    private static void decodeTextCompaction(int ai[], int ai1[], int i, StringBuffer stringbuffer)
    {
        int j;
        int k;
        int l;
        j = 0;
        k = 0;
        l = 0;
_L8:
        int i1;
        char c;
        if(l >= i)
            break MISSING_BLOCK_LABEL_460;
        i1 = ai[l];
        c = '\0';
        j;
        JVM INSTR tableswitch 0 4: default 60
    //                   0 78
    //                   1 174
    //                   2 270
    //                   3 373
    //                   4 425;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L6:
        break MISSING_BLOCK_LABEL_425;
_L1:
        break; /* Loop/switch isn't completed */
_L2:
        break; /* Loop/switch isn't completed */
_L9:
        if(c != 0)
            stringbuffer.append(c);
        l++;
        if(true) goto _L8; else goto _L7
_L7:
        if(i1 < 26)
            c = (char)(i1 + 65);
        else
        if(i1 == 26)
            c = ' ';
        else
        if(i1 == 27)
            j = 1;
        else
        if(i1 == 28)
            j = 2;
        else
        if(i1 == 29)
        {
            k = j;
            j = 4;
        } else
        if(i1 == 913)
            stringbuffer.append((char)ai1[l]);
          goto _L9
_L3:
        if(i1 < 26)
            c = (char)(i1 + 97);
        else
        if(i1 == 26)
            c = ' ';
        else
        if(i1 == 28)
            j = 0;
        else
        if(i1 == 28)
            j = 2;
        else
        if(i1 == 29)
        {
            k = j;
            j = 4;
        } else
        if(i1 == 913)
            stringbuffer.append((char)ai1[l]);
          goto _L9
_L4:
        if(i1 < 25)
            c = MIXED_CHARS[i1];
        else
        if(i1 == 25)
            j = 3;
        else
        if(i1 == 26)
            c = ' ';
        else
        if(i1 != 27)
            if(i1 == 28)
                j = 0;
            else
            if(i1 == 29)
            {
                k = j;
                j = 4;
            } else
            if(i1 == 913)
                stringbuffer.append((char)ai1[l]);
          goto _L9
_L5:
        if(i1 < 29)
            c = PUNCT_CHARS[i1];
        else
        if(i1 == 29)
            j = 0;
        else
        if(i1 == 913)
            stringbuffer.append((char)ai1[l]);
          goto _L9
        j = k;
        if(i1 < 29)
            c = PUNCT_CHARS[i1];
        else
        if(i1 == 29)
            j = 0;
          goto _L9
    }

    private static StringBuffer multiply(String s, int i)
    {
        StringBuffer stringbuffer = new StringBuffer(s.length());
        for(int j = 0; j < s.length(); j++)
            stringbuffer.append('0');

        int k = i / 100;
        int l = (i / 10) % 10;
        int i1 = i % 10;
        for(int j1 = 0; j1 < i1; j1++)
            stringbuffer = add(stringbuffer.toString(), s);

        for(int k1 = 0; k1 < l; k1++)
            stringbuffer = add(stringbuffer.toString(), (s + '0').substring(1));

        for(int l1 = 0; l1 < k; l1++)
            stringbuffer = add(stringbuffer.toString(), (s + "00").substring(2));

        return stringbuffer;
    }

    private static int numericCompaction(int ai[], int i, StringBuffer stringbuffer)
    {
        int j = 0;
        boolean flag = false;
        int ai1[] = new int[15];
        do
            if(i < ai.length && !flag)
            {
                int k = i + 1;
                int l = ai[i];
                if(l < 900)
                {
                    ai1[j] = l;
                    j++;
                    i = k;
                } else
                {
                    if(l != 900 && l != 901 && l != 924 && l != 928 && l != 923)
                        if(l != 922);
                    i = k - 1;
                    flag = true;
                }
                if(j % 15 == 0 || l == 902)
                {
                    stringbuffer.append(decodeBase900toBase10(ai1, j));
                    j = 0;
                }
            } else
            {
                return i;
            }
        while(true);
    }

    private static int textCompaction(int ai[], int i, StringBuffer stringbuffer)
    {
        int ai1[] = new int[ai[0] << 1];
        int ai2[] = new int[ai[0] << 1];
        int j = 0;
label0:
        do
        {
            for(boolean flag = false; i < ai[0] && !flag;)
            {
                int k = i + 1;
                int l = ai[i];
                if(l < 900)
                {
                    ai1[j] = l / 30;
                    ai1[j + 1] = l % 30;
                    j += 2;
                    i = k;
                } else
                {
                    switch(l)
                    {
                    default:
                        i = k;
                        break;

                    case 900: 
                        i = k - 1;
                        flag = true;
                        break;

                    case 901: 
                        i = k - 1;
                        flag = true;
                        break;

                    case 902: 
                        i = k - 1;
                        flag = true;
                        break;

                    case 913: 
                        ai1[j] = 913;
                        ai2[j] = l;
                        j++;
                        i = k;
                        break;

                    case 924: 
                        i = k - 1;
                        flag = true;
                        break;
                    }
                    continue label0;
                }
            }

            decodeTextCompaction(ai1, ai2, j, stringbuffer);
            return i;
        } while(true);
    }

    private static final int AL = 28;
    private static final int ALPHA = 0;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final String EXP900[];
    private static final int LL = 27;
    private static final int LOWER = 1;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final int MIXED = 2;
    private static final char MIXED_CHARS[];
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final int PUNCT = 3;
    private static final char PUNCT_CHARS[];
    private static final int PUNCT_SHIFT = 4;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;

    static 
    {
        char ac[] = new char[29];
        ac[0] = ';';
        ac[1] = '<';
        ac[2] = '>';
        ac[3] = '@';
        ac[4] = '[';
        ac[5] = '\\';
        ac[6] = '}';
        ac[7] = '_';
        ac[8] = '`';
        ac[9] = '~';
        ac[10] = '!';
        ac[11] = '\r';
        ac[12] = '\t';
        ac[13] = ',';
        ac[14] = ':';
        ac[15] = '\n';
        ac[16] = '-';
        ac[17] = '.';
        ac[18] = '$';
        ac[19] = '/';
        ac[20] = '"';
        ac[21] = '|';
        ac[22] = '*';
        ac[23] = '(';
        ac[24] = ')';
        ac[25] = '?';
        ac[26] = '{';
        ac[27] = '}';
        ac[28] = '\'';
        PUNCT_CHARS = ac;
        char ac1[] = new char[25];
        ac1[0] = '0';
        ac1[1] = '1';
        ac1[2] = '2';
        ac1[3] = '3';
        ac1[4] = '4';
        ac1[5] = '5';
        ac1[6] = '6';
        ac1[7] = '7';
        ac1[8] = '8';
        ac1[9] = '9';
        ac1[10] = '&';
        ac1[11] = '\r';
        ac1[12] = '\t';
        ac1[13] = ',';
        ac1[14] = ':';
        ac1[15] = '#';
        ac1[16] = '-';
        ac1[17] = '.';
        ac1[18] = '$';
        ac1[19] = '/';
        ac1[20] = '+';
        ac1[21] = '%';
        ac1[22] = '*';
        ac1[23] = '=';
        ac1[24] = '^';
        MIXED_CHARS = ac1;
        String as[] = new String[16];
        as[0] = "000000000000000000000000000000000000000000001";
        as[1] = "000000000000000000000000000000000000000000900";
        as[2] = "000000000000000000000000000000000000000810000";
        as[3] = "000000000000000000000000000000000000729000000";
        as[4] = "000000000000000000000000000000000656100000000";
        as[5] = "000000000000000000000000000000590490000000000";
        as[6] = "000000000000000000000000000531441000000000000";
        as[7] = "000000000000000000000000478296900000000000000";
        as[8] = "000000000000000000000430467210000000000000000";
        as[9] = "000000000000000000387420489000000000000000000";
        as[10] = "000000000000000348678440100000000000000000000";
        as[11] = "000000000000313810596090000000000000000000000";
        as[12] = "000000000282429536481000000000000000000000000";
        as[13] = "000000254186582832900000000000000000000000000";
        as[14] = "000228767924549610000000000000000000000000000";
        as[15] = "205891132094649000000000000000000000000000000";
        EXP900 = as;
    }
}
