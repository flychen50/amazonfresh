// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ReaderException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

final class DecodedBitStreamParser
{

    private DecodedBitStreamParser()
    {
    }

    static DecoderResult decode(byte abyte0[])
        throws ReaderException
    {
        BitSource bitsource;
        StringBuffer stringbuffer;
        StringBuffer stringbuffer1;
        Vector vector;
        int i;
        bitsource = new BitSource(abyte0);
        stringbuffer = new StringBuffer();
        stringbuffer1 = new StringBuffer(0);
        vector = new Vector(1);
        i = 1;
_L3:
        if(i != 1) goto _L2; else goto _L1
_L1:
        i = decodeAsciiSegment(bitsource, stringbuffer, stringbuffer1);
_L10:
        if(i == 0 || bitsource.available() <= 0)
        {
            if(stringbuffer1.length() > 0)
                stringbuffer.append(stringbuffer1.toString());
            String s = stringbuffer.toString();
            Vector vector1;
            if(vector.isEmpty())
                vector1 = null;
            else
                vector1 = vector;
            return new DecoderResult(abyte0, s, vector1, null);
        }
        if(true) goto _L3; else goto _L2
_L2:
        i;
        JVM INSTR tableswitch 2 6: default 148
    //                   2 152
    //                   3 163
    //                   4 171
    //                   5 179
    //                   6 187;
           goto _L4 _L5 _L6 _L7 _L8 _L9
_L9:
        break MISSING_BLOCK_LABEL_187;
_L4:
        throw ReaderException.getInstance();
_L5:
        decodeC40Segment(bitsource, stringbuffer);
_L11:
        i = 1;
          goto _L10
_L6:
        decodeTextSegment(bitsource, stringbuffer);
          goto _L11
_L7:
        decodeAnsiX12Segment(bitsource, stringbuffer);
          goto _L11
_L8:
        decodeEdifactSegment(bitsource, stringbuffer);
          goto _L11
        decodeBase256Segment(bitsource, stringbuffer, vector);
          goto _L11
    }

    private static void decodeAnsiX12Segment(BitSource bitsource, StringBuffer stringbuffer)
        throws ReaderException
    {
        int ai[] = new int[3];
_L4:
        if(bitsource.available() != 8) goto _L2; else goto _L1
_L1:
        int i;
        return;
_L2:
        if((i = bitsource.readBits(8)) == 254) goto _L1; else goto _L3
_L3:
        parseTwoBytes(i, bitsource.readBits(8), ai);
        int j = 0;
        while(j < 3) 
        {
            int k = ai[j];
            if(k == 0)
                stringbuffer.append('\r');
            else
            if(k == 1)
                stringbuffer.append('*');
            else
            if(k == 2)
                stringbuffer.append('>');
            else
            if(k == 3)
                stringbuffer.append(' ');
            else
            if(k < 14)
                stringbuffer.append((char)(k + 44));
            else
            if(k < 40)
                stringbuffer.append((char)(k + 51));
            else
                throw ReaderException.getInstance();
            j++;
        }
        if(bitsource.available() > 0) goto _L4; else goto _L1
    }

    private static int decodeAsciiSegment(BitSource bitsource, StringBuffer stringbuffer, StringBuffer stringbuffer1)
        throws ReaderException
    {
        boolean flag = false;
_L4:
        int i;
        int j;
        i = bitsource.readBits(8);
        if(i == 0)
            throw ReaderException.getInstance();
        if(i <= 128)
        {
            if(flag)
                i += 128;
            stringbuffer.append((char)(i - 1));
            j = 1;
        } else
        {
label0:
            {
                if(i != 129)
                    break label0;
                j = 0;
            }
        }
_L5:
        return j;
        if(i > 229) goto _L2; else goto _L1
_L1:
        int k = i - 130;
        if(k < 10)
            stringbuffer.append('0');
        stringbuffer.append(k);
_L6:
        if(bitsource.available() > 0) goto _L4; else goto _L3
_L3:
        j = 1;
          goto _L5
_L2:
        if(i == 230)
        {
            j = 2;
        } else
        {
label1:
            {
                if(i != 231)
                    break label1;
                j = 6;
            }
        }
          goto _L5
        if(i != 232 && i != 233 && i != 234)
            if(i == 235)
                flag = true;
            else
            if(i == 236)
            {
                stringbuffer.append("[)>\03605\035");
                stringbuffer1.insert(0, "\036\004");
            } else
            {
label2:
                {
                    if(i != 237)
                        break label2;
                    stringbuffer.append("[)>\03606\035");
                    stringbuffer1.insert(0, "\036\004");
                }
            }
          goto _L6
        if(i == 238)
            j = 4;
        else
        if(i == 239)
        {
            j = 3;
        } else
        {
            if(i != 240)
                continue; /* Loop/switch isn't completed */
            j = 5;
        }
          goto _L5
        if(i == 241 || i < 242) goto _L6; else goto _L7
_L7:
        throw ReaderException.getInstance();
          goto _L5
    }

    private static void decodeBase256Segment(BitSource bitsource, StringBuffer stringbuffer, Vector vector)
    {
        int i = bitsource.readBits(8);
        int j;
        byte abyte0[];
        if(i == 0)
            j = bitsource.available() / 8;
        else
        if(i < 250)
            j = i;
        else
            j = 250 * (i - 249) + bitsource.readBits(8);
        abyte0 = new byte[j];
        for(int k = 0; k < j; k++)
            abyte0[k] = unrandomize255State(bitsource.readBits(8), k);

        vector.addElement(abyte0);
        try
        {
            stringbuffer.append(new String(abyte0, "ISO8859_1"));
            return;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new RuntimeException("Platform does not support required encoding: " + unsupportedencodingexception);
        }
    }

    private static void decodeC40Segment(BitSource bitsource, StringBuffer stringbuffer)
        throws ReaderException
    {
        boolean flag;
        int ai[];
        flag = false;
        ai = new int[3];
_L12:
        if(bitsource.available() != 8) goto _L2; else goto _L1
_L1:
        int i;
        return;
_L2:
        if((i = bitsource.readBits(8)) == 254) goto _L1; else goto _L3
_L3:
        int j;
        int k;
        parseTwoBytes(i, bitsource.readBits(8), ai);
        j = 0;
        k = 0;
_L10:
        int l;
        if(k >= 3)
            continue; /* Loop/switch isn't completed */
        l = ai[k];
        j;
        JVM INSTR tableswitch 0 3: default 96
    //                   0 100
    //                   1 157
    //                   2 191
    //                   3 267;
           goto _L4 _L5 _L6 _L7 _L8
_L8:
        break MISSING_BLOCK_LABEL_267;
_L6:
        break; /* Loop/switch isn't completed */
_L4:
        throw ReaderException.getInstance();
_L5:
        if(l < 3)
            j = l + 1;
        else
        if(flag)
        {
            stringbuffer.append((char)(128 + C40_BASIC_SET_CHARS[l]));
            flag = false;
        } else
        {
            stringbuffer.append(C40_BASIC_SET_CHARS[l]);
        }
_L11:
        k++;
        if(true) goto _L10; else goto _L9
_L9:
        if(flag)
        {
            stringbuffer.append((char)(l + 128));
            flag = false;
        } else
        {
            stringbuffer.append(l);
        }
        j = 0;
          goto _L11
_L7:
        if(l < 27)
        {
            if(flag)
            {
                stringbuffer.append((char)(128 + C40_SHIFT2_SET_CHARS[l]));
                flag = false;
            } else
            {
                stringbuffer.append(C40_SHIFT2_SET_CHARS[l]);
            }
        } else
        {
            if(l == 27)
                throw ReaderException.getInstance();
            if(l == 30)
                flag = true;
            else
                throw ReaderException.getInstance();
        }
        j = 0;
          goto _L11
        if(flag)
        {
            stringbuffer.append((char)(l + 224));
            flag = false;
        } else
        {
            stringbuffer.append((char)(l + 96));
        }
        j = 0;
          goto _L11
        if(bitsource.available() > 0) goto _L12; else goto _L1
    }

    private static void decodeEdifactSegment(BitSource bitsource, StringBuffer stringbuffer)
    {
        boolean flag = false;
_L3:
        if(bitsource.available() > 16) goto _L2; else goto _L1
_L1:
        return;
_L2:
        for(int i = 0; i < 4; i++)
        {
            int j = bitsource.readBits(6);
            if(j == 11111)
                flag = true;
            if(flag)
                continue;
            if((j & 0x20) == 0)
                j |= 0x40;
            stringbuffer.append(j);
        }

        if(!flag && bitsource.available() > 0) goto _L3; else goto _L1
    }

    private static void decodeTextSegment(BitSource bitsource, StringBuffer stringbuffer)
        throws ReaderException
    {
        boolean flag;
        int ai[];
        flag = false;
        ai = new int[3];
_L12:
        if(bitsource.available() != 8) goto _L2; else goto _L1
_L1:
        int i;
        return;
_L2:
        if((i = bitsource.readBits(8)) == 254) goto _L1; else goto _L3
_L3:
        int j;
        int k;
        parseTwoBytes(i, bitsource.readBits(8), ai);
        j = 0;
        k = 0;
_L10:
        int l;
        if(k >= 3)
            continue; /* Loop/switch isn't completed */
        l = ai[k];
        j;
        JVM INSTR tableswitch 0 3: default 96
    //                   0 100
    //                   1 157
    //                   2 191
    //                   3 267;
           goto _L4 _L5 _L6 _L7 _L8
_L8:
        break MISSING_BLOCK_LABEL_267;
_L6:
        break; /* Loop/switch isn't completed */
_L4:
        throw ReaderException.getInstance();
_L5:
        if(l < 3)
            j = l + 1;
        else
        if(flag)
        {
            stringbuffer.append((char)(128 + TEXT_BASIC_SET_CHARS[l]));
            flag = false;
        } else
        {
            stringbuffer.append(TEXT_BASIC_SET_CHARS[l]);
        }
_L11:
        k++;
        if(true) goto _L10; else goto _L9
_L9:
        if(flag)
        {
            stringbuffer.append((char)(l + 128));
            flag = false;
        } else
        {
            stringbuffer.append(l);
        }
        j = 0;
          goto _L11
_L7:
        if(l < 27)
        {
            if(flag)
            {
                stringbuffer.append((char)(128 + C40_SHIFT2_SET_CHARS[l]));
                flag = false;
            } else
            {
                stringbuffer.append(C40_SHIFT2_SET_CHARS[l]);
            }
        } else
        {
            if(l == 27)
                throw ReaderException.getInstance();
            if(l == 30)
                flag = true;
            else
                throw ReaderException.getInstance();
        }
        j = 0;
          goto _L11
        if(flag)
        {
            stringbuffer.append((char)(128 + TEXT_SHIFT3_SET_CHARS[l]));
            flag = false;
        } else
        {
            stringbuffer.append(TEXT_SHIFT3_SET_CHARS[l]);
        }
        j = 0;
          goto _L11
        if(bitsource.available() > 0) goto _L12; else goto _L1
    }

    private static void parseTwoBytes(int i, int j, int ai[])
    {
        int k = (j + (i << 8)) - 1;
        int l = k / 1600;
        ai[0] = l;
        int i1 = k - l * 1600;
        int j1 = i1 / 40;
        ai[1] = j1;
        ai[2] = i1 - j1 * 40;
    }

    private static byte unrandomize255State(int i, int j)
    {
        int k = i - (1 + (j * 149) % 255);
        int l;
        if(k >= 0)
            l = k;
        else
            l = k + 256;
        return (byte)l;
    }

    private static final int ANSIX12_ENCODE = 4;
    private static final int ASCII_ENCODE = 1;
    private static final int BASE256_ENCODE = 6;
    private static final char C40_BASIC_SET_CHARS[];
    private static final int C40_ENCODE = 2;
    private static final char C40_SHIFT2_SET_CHARS[];
    private static final int EDIFACT_ENCODE = 5;
    private static final int PAD_ENCODE = 0;
    private static final char TEXT_BASIC_SET_CHARS[];
    private static final int TEXT_ENCODE = 3;
    private static final char TEXT_SHIFT3_SET_CHARS[];

    static 
    {
        char ac[] = new char[40];
        ac[0] = '*';
        ac[1] = '*';
        ac[2] = '*';
        ac[3] = ' ';
        ac[4] = '0';
        ac[5] = '1';
        ac[6] = '2';
        ac[7] = '3';
        ac[8] = '4';
        ac[9] = '5';
        ac[10] = '6';
        ac[11] = '7';
        ac[12] = '8';
        ac[13] = '9';
        ac[14] = 'A';
        ac[15] = 'B';
        ac[16] = 'C';
        ac[17] = 'D';
        ac[18] = 'E';
        ac[19] = 'F';
        ac[20] = 'G';
        ac[21] = 'H';
        ac[22] = 'I';
        ac[23] = 'J';
        ac[24] = 'K';
        ac[25] = 'L';
        ac[26] = 'M';
        ac[27] = 'N';
        ac[28] = 'O';
        ac[29] = 'P';
        ac[30] = 'Q';
        ac[31] = 'R';
        ac[32] = 'S';
        ac[33] = 'T';
        ac[34] = 'U';
        ac[35] = 'V';
        ac[36] = 'W';
        ac[37] = 'X';
        ac[38] = 'Y';
        ac[39] = 'Z';
        C40_BASIC_SET_CHARS = ac;
        char ac1[] = new char[27];
        ac1[0] = '!';
        ac1[1] = '"';
        ac1[2] = '#';
        ac1[3] = '$';
        ac1[4] = '%';
        ac1[5] = '&';
        ac1[6] = '\'';
        ac1[7] = '(';
        ac1[8] = ')';
        ac1[9] = '*';
        ac1[10] = '+';
        ac1[11] = ',';
        ac1[12] = '-';
        ac1[13] = '.';
        ac1[14] = '/';
        ac1[15] = ':';
        ac1[16] = ';';
        ac1[17] = '<';
        ac1[18] = '=';
        ac1[19] = '>';
        ac1[20] = '?';
        ac1[21] = '@';
        ac1[22] = '[';
        ac1[23] = '\\';
        ac1[24] = ']';
        ac1[25] = '^';
        ac1[26] = '_';
        C40_SHIFT2_SET_CHARS = ac1;
        char ac2[] = new char[40];
        ac2[0] = '*';
        ac2[1] = '*';
        ac2[2] = '*';
        ac2[3] = ' ';
        ac2[4] = '0';
        ac2[5] = '1';
        ac2[6] = '2';
        ac2[7] = '3';
        ac2[8] = '4';
        ac2[9] = '5';
        ac2[10] = '6';
        ac2[11] = '7';
        ac2[12] = '8';
        ac2[13] = '9';
        ac2[14] = 'a';
        ac2[15] = 'b';
        ac2[16] = 'c';
        ac2[17] = 'd';
        ac2[18] = 'e';
        ac2[19] = 'f';
        ac2[20] = 'g';
        ac2[21] = 'h';
        ac2[22] = 'i';
        ac2[23] = 'j';
        ac2[24] = 'k';
        ac2[25] = 'l';
        ac2[26] = 'm';
        ac2[27] = 'n';
        ac2[28] = 'o';
        ac2[29] = 'p';
        ac2[30] = 'q';
        ac2[31] = 'r';
        ac2[32] = 's';
        ac2[33] = 't';
        ac2[34] = 'u';
        ac2[35] = 'v';
        ac2[36] = 'w';
        ac2[37] = 'x';
        ac2[38] = 'y';
        ac2[39] = 'z';
        TEXT_BASIC_SET_CHARS = ac2;
        char ac3[] = new char[32];
        ac3[0] = '\'';
        ac3[1] = 'A';
        ac3[2] = 'B';
        ac3[3] = 'C';
        ac3[4] = 'D';
        ac3[5] = 'E';
        ac3[6] = 'F';
        ac3[7] = 'G';
        ac3[8] = 'H';
        ac3[9] = 'I';
        ac3[10] = 'J';
        ac3[11] = 'K';
        ac3[12] = 'L';
        ac3[13] = 'M';
        ac3[14] = 'N';
        ac3[15] = 'O';
        ac3[16] = 'P';
        ac3[17] = 'Q';
        ac3[18] = 'R';
        ac3[19] = 'S';
        ac3[20] = 'T';
        ac3[21] = 'U';
        ac3[22] = 'V';
        ac3[23] = 'W';
        ac3[24] = 'X';
        ac3[25] = 'Y';
        ac3[26] = 'Z';
        ac3[27] = '{';
        ac3[28] = '|';
        ac3[29] = '}';
        ac3[30] = '~';
        ac3[31] = '\177';
        TEXT_SHIFT3_SET_CHARS = ac3;
    }
}
