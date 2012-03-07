// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.decoder;

import com.google.zxing.ReaderException;
import com.google.zxing.common.*;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

// Referenced classes of package com.google.zxing.qrcode.decoder:
//            Mode, Version, ErrorCorrectionLevel

final class DecodedBitStreamParser
{

    private DecodedBitStreamParser()
    {
    }

    static DecoderResult decode(byte abyte0[], Version version, ErrorCorrectionLevel errorcorrectionlevel)
        throws ReaderException
    {
        BitSource bitsource;
        StringBuffer stringbuffer;
        CharacterSetECI characterseteci;
        boolean flag;
        Vector vector;
        bitsource = new BitSource(abyte0);
        stringbuffer = new StringBuffer();
        characterseteci = null;
        flag = false;
        vector = new Vector(1);
_L5:
        Mode mode1;
        if(bitsource.available() < 4)
        {
            mode1 = Mode.TERMINATOR;
        } else
        {
            Mode mode;
            try
            {
                mode = Mode.forBits(bitsource.readBits(4));
            }
            catch(IllegalArgumentException illegalargumentexception)
            {
                throw ReaderException.getInstance();
            }
            mode1 = mode;
        }
        if(mode1.equals(Mode.TERMINATOR)) goto _L2; else goto _L1
_L1:
        if(!mode1.equals(Mode.FNC1_FIRST_POSITION) && !mode1.equals(Mode.FNC1_SECOND_POSITION)) goto _L4; else goto _L3
_L3:
        flag = true;
_L2:
        if(mode1.equals(Mode.TERMINATOR))
        {
            String s = stringbuffer.toString();
            Vector vector1;
            int i;
            if(vector.isEmpty())
                vector1 = null;
            else
                vector1 = vector;
            return new DecoderResult(abyte0, s, vector1, errorcorrectionlevel);
        }
        if(true) goto _L5; else goto _L4
_L4:
        if(mode1.equals(Mode.STRUCTURED_APPEND))
            bitsource.readBits(16);
        else
        if(mode1.equals(Mode.ECI))
        {
            characterseteci = CharacterSetECI.getCharacterSetECIByValue(parseECIValue(bitsource));
            if(characterseteci == null)
                throw ReaderException.getInstance();
        } else
        {
            i = bitsource.readBits(mode1.getCharacterCountBits(version));
            if(mode1.equals(Mode.NUMERIC))
                decodeNumericSegment(bitsource, stringbuffer, i);
            else
            if(mode1.equals(Mode.ALPHANUMERIC))
                decodeAlphanumericSegment(bitsource, stringbuffer, i, flag);
            else
            if(mode1.equals(Mode.BYTE))
                decodeByteSegment(bitsource, stringbuffer, i, characterseteci, vector);
            else
            if(mode1.equals(Mode.KANJI))
                decodeKanjiSegment(bitsource, stringbuffer, i);
            else
                throw ReaderException.getInstance();
        }
          goto _L2
    }

    private static void decodeAlphanumericSegment(BitSource bitsource, StringBuffer stringbuffer, int i, boolean flag)
    {
        int j = stringbuffer.length();
        for(; i > 1; i -= 2)
        {
            int l = bitsource.readBits(11);
            stringbuffer.append(ALPHANUMERIC_CHARS[l / 45]);
            stringbuffer.append(ALPHANUMERIC_CHARS[l % 45]);
        }

        if(i == 1)
            stringbuffer.append(ALPHANUMERIC_CHARS[bitsource.readBits(6)]);
        if(flag)
        {
            int k = j;
            while(k < stringbuffer.length()) 
            {
                if(stringbuffer.charAt(k) == '%')
                    if(k < stringbuffer.length() - 1 && stringbuffer.charAt(k + 1) == '%')
                        stringbuffer.deleteCharAt(k + 1);
                    else
                        stringbuffer.setCharAt(k, '\035');
                k++;
            }
        }
    }

    private static void decodeByteSegment(BitSource bitsource, StringBuffer stringbuffer, int i, CharacterSetECI characterseteci, Vector vector)
        throws ReaderException
    {
        byte abyte0[] = new byte[i];
        if(i << 3 > bitsource.available())
            throw ReaderException.getInstance();
        for(int j = 0; j < i; j++)
            abyte0[j] = (byte)bitsource.readBits(8);

        String s;
        if(characterseteci == null)
            s = guessEncoding(abyte0);
        else
            s = characterseteci.getEncodingName();
        try
        {
            stringbuffer.append(new String(abyte0, s));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw ReaderException.getInstance();
        }
        vector.addElement(abyte0);
    }

    private static void decodeKanjiSegment(BitSource bitsource, StringBuffer stringbuffer, int i)
        throws ReaderException
    {
        byte abyte0[] = new byte[i * 2];
        int j = 0;
        while(i > 0) 
        {
            int k = bitsource.readBits(13);
            int l = k / 192 << 8 | k % 192;
            int i1;
            if(l < 7936)
                i1 = l + 33088;
            else
                i1 = l + 49472;
            abyte0[j] = (byte)(i1 >> 8);
            abyte0[j + 1] = (byte)i1;
            j += 2;
            i--;
        }
        try
        {
            stringbuffer.append(new String(abyte0, "SJIS"));
            return;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw ReaderException.getInstance();
        }
    }

    private static void decodeNumericSegment(BitSource bitsource, StringBuffer stringbuffer, int i)
        throws ReaderException
    {
        for(; i >= 3; i -= 3)
        {
            int l = bitsource.readBits(10);
            if(l >= 1000)
                throw ReaderException.getInstance();
            stringbuffer.append(ALPHANUMERIC_CHARS[l / 100]);
            stringbuffer.append(ALPHANUMERIC_CHARS[(l / 10) % 10]);
            stringbuffer.append(ALPHANUMERIC_CHARS[l % 10]);
        }

        if(i != 2) goto _L2; else goto _L1
_L1:
        int k = bitsource.readBits(7);
        if(k >= 100)
            throw ReaderException.getInstance();
        stringbuffer.append(ALPHANUMERIC_CHARS[k / 10]);
        stringbuffer.append(ALPHANUMERIC_CHARS[k % 10]);
_L4:
        return;
_L2:
        if(i == 1)
        {
            int j = bitsource.readBits(4);
            if(j >= 10)
                throw ReaderException.getInstance();
            stringbuffer.append(ALPHANUMERIC_CHARS[j]);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static String guessEncoding(byte abyte0[])
    {
        String s;
        if(ASSUME_SHIFT_JIS)
            s = "SJIS";
        else
        if(abyte0.length > 3 && abyte0[0] == -17 && abyte0[1] == -69 && abyte0[2] == -65)
        {
            s = "UTF8";
        } else
        {
            int i = abyte0.length;
            boolean flag = true;
            boolean flag1 = true;
            int j = 0;
            int k = 0;
            boolean flag2 = false;
            int l = 0;
            boolean flag3 = false;
            while(l < i && (flag || flag1)) 
            {
                int i1 = 0xff & abyte0[l];
                if((i1 == 194 || i1 == 195) && l < i - 1)
                {
                    int k1 = 0xff & abyte0[l + 1];
                    if(k1 <= 191 && (i1 == 194 && k1 >= 160 || i1 == 195 && k1 >= 128))
                        flag3 = true;
                }
                if(i1 >= 127 && i1 <= 159)
                    flag = false;
                if(i1 >= 161 && i1 <= 223 && !flag2)
                    k++;
                if(!flag2 && (i1 >= 240 && i1 <= 255 || i1 == 128 || i1 == 160))
                    flag1 = false;
                if(i1 >= 129 && i1 <= 159 || i1 >= 224 && i1 <= 239)
                {
                    if(flag2)
                    {
                        flag2 = false;
                    } else
                    {
                        flag2 = true;
                        if(l >= abyte0.length - 1)
                        {
                            flag1 = false;
                        } else
                        {
                            int j1 = 0xff & abyte0[l + 1];
                            if(j1 < 64 || j1 > 252)
                                flag1 = false;
                            else
                                j++;
                        }
                    }
                } else
                {
                    flag2 = false;
                }
                l++;
            }
            if(flag1 && (j >= 3 || k * 20 > i))
                s = "SJIS";
            else
            if(!flag3 && flag)
                s = "ISO8859_1";
            else
                s = "UTF8";
        }
        return s;
    }

    private static int parseECIValue(BitSource bitsource)
    {
        int i = bitsource.readBits(8);
        int j;
        if((i & 0x80) == 0)
            j = i & 0x7f;
        else
        if((i & 0xc0) == 128)
            j = bitsource.readBits(8) | (i & 0x3f) << 8;
        else
        if((i & 0xe0) == 192)
            j = bitsource.readBits(16) | (i & 0x1f) << 16;
        else
            throw new IllegalArgumentException("Bad ECI bits starting with byte " + i);
        return j;
    }

    private static final char ALPHANUMERIC_CHARS[];
    private static final boolean ASSUME_SHIFT_JIS = false;
    private static final String EUC_JP = "EUC_JP";
    private static final String ISO88591 = "ISO8859_1";
    private static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static 
    {
        char ac[] = new char[45];
        ac[0] = '0';
        ac[1] = '1';
        ac[2] = '2';
        ac[3] = '3';
        ac[4] = '4';
        ac[5] = '5';
        ac[6] = '6';
        ac[7] = '7';
        ac[8] = '8';
        ac[9] = '9';
        ac[10] = 'A';
        ac[11] = 'B';
        ac[12] = 'C';
        ac[13] = 'D';
        ac[14] = 'E';
        ac[15] = 'F';
        ac[16] = 'G';
        ac[17] = 'H';
        ac[18] = 'I';
        ac[19] = 'J';
        ac[20] = 'K';
        ac[21] = 'L';
        ac[22] = 'M';
        ac[23] = 'N';
        ac[24] = 'O';
        ac[25] = 'P';
        ac[26] = 'Q';
        ac[27] = 'R';
        ac[28] = 'S';
        ac[29] = 'T';
        ac[30] = 'U';
        ac[31] = 'V';
        ac[32] = 'W';
        ac[33] = 'X';
        ac[34] = 'Y';
        ac[35] = 'Z';
        ac[36] = ' ';
        ac[37] = '$';
        ac[38] = '%';
        ac[39] = '*';
        ac[40] = '+';
        ac[41] = '-';
        ac[42] = '.';
        ac[43] = '/';
        ac[44] = ':';
        ALPHANUMERIC_CHARS = ac;
        String s = System.getProperty("file.encoding");
        boolean flag;
        if("SJIS".equalsIgnoreCase(s) || "EUC_JP".equalsIgnoreCase(s))
            flag = true;
        else
            flag = false;
        ASSUME_SHIFT_JIS = flag;
    }
}
