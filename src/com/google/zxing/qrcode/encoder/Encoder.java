// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.*;
import com.google.zxing.common.reedsolomon.GF256;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.*;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.google.zxing.qrcode.encoder:
//            BitVector, MaskUtil, MatrixUtil, QRCode

public final class Encoder
{
    private static final class BlockPair
    {

        public ByteArray getDataBytes()
        {
            return dataBytes;
        }

        public ByteArray getErrorCorrectionBytes()
        {
            return errorCorrectionBytes;
        }

        private final ByteArray dataBytes;
        private final ByteArray errorCorrectionBytes;

        BlockPair(ByteArray bytearray, ByteArray bytearray1)
        {
            dataBytes = bytearray;
            errorCorrectionBytes = bytearray1;
        }
    }


    private Encoder()
    {
    }

    static void append8BitBytes(String s, BitVector bitvector, String s1)
        throws WriterException
    {
        byte abyte0[];
        int i;
        try
        {
            abyte0 = s.getBytes(s1);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new WriterException(unsupportedencodingexception.toString());
        }
        for(i = 0; i < abyte0.length; i++)
            bitvector.appendBits(abyte0[i], 8);

    }

    static void appendAlphanumericBytes(String s, BitVector bitvector)
        throws WriterException
    {
        int i = s.length();
        for(int j = 0; j < i;)
        {
            int k = getAlphanumericCode(s.charAt(j));
            if(k == -1)
                throw new WriterException();
            if(j + 1 < i)
            {
                int l = getAlphanumericCode(s.charAt(j + 1));
                if(l == -1)
                    throw new WriterException();
                bitvector.appendBits(l + k * 45, 11);
                j += 2;
            } else
            {
                bitvector.appendBits(k, 6);
                j++;
            }
        }

    }

    static void appendBytes(String s, Mode mode, BitVector bitvector, String s1)
        throws WriterException
    {
        if(mode.equals(Mode.NUMERIC))
            appendNumericBytes(s, bitvector);
        else
        if(mode.equals(Mode.ALPHANUMERIC))
            appendAlphanumericBytes(s, bitvector);
        else
        if(mode.equals(Mode.BYTE))
            append8BitBytes(s, bitvector, s1);
        else
        if(mode.equals(Mode.KANJI))
            appendKanjiBytes(s, bitvector);
        else
            throw new WriterException("Invalid mode: " + mode);
    }

    private static void appendECI(CharacterSetECI characterseteci, BitVector bitvector)
    {
        bitvector.appendBits(Mode.ECI.getBits(), 4);
        bitvector.appendBits(characterseteci.getValue(), 8);
    }

    static void appendKanjiBytes(String s, BitVector bitvector)
        throws WriterException
    {
        int j;
        int l;
        int i1;
        byte abyte0[];
        int i;
        int k;
        try
        {
            abyte0 = s.getBytes("Shift_JIS");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new WriterException(unsupportedencodingexception.toString());
        }
        i = abyte0.length;
        j = 0;
_L7:
        if(j >= i) goto _L2; else goto _L1
_L1:
        k = 0xff & abyte0[j];
        l = 0xff & abyte0[j + 1] | k << 8;
        i1 = -1;
        if(l < 33088 || l > 40956) goto _L4; else goto _L3
_L3:
        i1 = l - 33088;
_L6:
        if(i1 == -1)
            throw new WriterException("Invalid byte sequence");
        break; /* Loop/switch isn't completed */
_L4:
        if(l >= 57408 && l <= 60351)
            i1 = l - 49472;
        if(true) goto _L6; else goto _L5
_L5:
        bitvector.appendBits(192 * (i1 >> 8) + (i1 & 0xff), 13);
        j += 2;
          goto _L7
_L2:
    }

    static void appendLengthInfo(int i, int j, Mode mode, BitVector bitvector)
        throws WriterException
    {
        int k = mode.getCharacterCountBits(Version.getVersionForNumber(j));
        if(i > (1 << k) - 1)
        {
            throw new WriterException(i + "is bigger than" + ((1 << k) - 1));
        } else
        {
            bitvector.appendBits(i, k);
            return;
        }
    }

    static void appendModeInfo(Mode mode, BitVector bitvector)
    {
        bitvector.appendBits(mode.getBits(), 4);
    }

    static void appendNumericBytes(String s, BitVector bitvector)
    {
        int i = s.length();
        for(int j = 0; j < i;)
        {
            int k = s.charAt(j) - 48;
            if(j + 2 < i)
            {
                int l = s.charAt(j + 1) - 48;
                bitvector.appendBits((s.charAt(j + 2) - 48) + (k * 100 + l * 10), 10);
                j += 3;
            } else
            if(j + 1 < i)
            {
                bitvector.appendBits((s.charAt(j + 1) - 48) + k * 10, 7);
                j += 2;
            } else
            {
                bitvector.appendBits(k, 4);
                j++;
            }
        }

    }

    private static int calculateMaskPenalty(ByteMatrix bytematrix)
    {
        return 0 + MaskUtil.applyMaskPenaltyRule1(bytematrix) + MaskUtil.applyMaskPenaltyRule2(bytematrix) + MaskUtil.applyMaskPenaltyRule3(bytematrix) + MaskUtil.applyMaskPenaltyRule4(bytematrix);
    }

    private static int chooseMaskPattern(BitVector bitvector, ErrorCorrectionLevel errorcorrectionlevel, int i, ByteMatrix bytematrix)
        throws WriterException
    {
        int j = 0x7fffffff;
        int k = -1;
        for(int l = 0; l < 8; l++)
        {
            MatrixUtil.buildMatrix(bitvector, errorcorrectionlevel, i, l, bytematrix);
            int i1 = calculateMaskPenalty(bytematrix);
            if(i1 < j)
            {
                j = i1;
                k = l;
            }
        }

        return k;
    }

    public static Mode chooseMode(String s)
    {
        return chooseMode(s, null);
    }

    public static Mode chooseMode(String s, String s1)
    {
        if(!"Shift_JIS".equals(s1)) goto _L2; else goto _L1
_L1:
        Mode mode = Mode.KANJI;
_L4:
        return mode;
_L2:
        boolean flag = false;
        boolean flag1 = false;
        int i = 0;
        while(i < s.length()) 
        {
            char c = s.charAt(i);
            if(c >= '0' && c <= '9')
                flag = true;
            else
            if(getAlphanumericCode(c) != -1)
            {
                flag1 = true;
            } else
            {
                mode = Mode.BYTE;
                continue; /* Loop/switch isn't completed */
            }
            i++;
        }
        if(flag1)
            mode = Mode.ALPHANUMERIC;
        else
        if(flag)
            mode = Mode.NUMERIC;
        else
            mode = Mode.BYTE;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static void encode(String s, ErrorCorrectionLevel errorcorrectionlevel, QRCode qrcode)
        throws WriterException
    {
        encode(s, errorcorrectionlevel, null, qrcode);
    }

    public static void encode(String s, ErrorCorrectionLevel errorcorrectionlevel, Hashtable hashtable, QRCode qrcode)
        throws WriterException
    {
        String s1;
        Mode mode;
        BitVector bitvector;
        BitVector bitvector1;
        int i;
        BitVector bitvector2;
        ByteMatrix bytematrix;
        if(hashtable == null)
            s1 = null;
        else
            s1 = (String)hashtable.get(EncodeHintType.CHARACTER_SET);
        if(s1 == null)
            s1 = "ISO-8859-1";
        mode = chooseMode(s, s1);
        bitvector = new BitVector();
        appendBytes(s, mode, bitvector, s1);
        initQRCode(bitvector.sizeInBytes(), errorcorrectionlevel, mode, qrcode);
        bitvector1 = new BitVector();
        if(mode == Mode.BYTE && !"ISO-8859-1".equals(s1))
        {
            CharacterSetECI characterseteci = CharacterSetECI.getCharacterSetECIByName(s1);
            if(characterseteci != null)
                appendECI(characterseteci, bitvector1);
        }
        appendModeInfo(mode, bitvector1);
        if(mode.equals(Mode.BYTE))
            i = bitvector.sizeInBytes();
        else
            i = s.length();
        appendLengthInfo(i, qrcode.getVersion(), mode, bitvector1);
        bitvector1.appendBitVector(bitvector);
        terminateBits(qrcode.getNumDataBytes(), bitvector1);
        bitvector2 = new BitVector();
        interleaveWithECBytes(bitvector1, qrcode.getNumTotalBytes(), qrcode.getNumDataBytes(), qrcode.getNumRSBlocks(), bitvector2);
        bytematrix = new ByteMatrix(qrcode.getMatrixWidth(), qrcode.getMatrixWidth());
        qrcode.setMaskPattern(chooseMaskPattern(bitvector2, qrcode.getECLevel(), qrcode.getVersion(), bytematrix));
        MatrixUtil.buildMatrix(bitvector2, qrcode.getECLevel(), qrcode.getVersion(), qrcode.getMaskPattern(), bytematrix);
        qrcode.setMatrix(bytematrix);
        if(!qrcode.isValid())
            throw new WriterException("Invalid QR code: " + qrcode.toString());
        else
            return;
    }

    static ByteArray generateECBytes(ByteArray bytearray, int i)
    {
        int j = bytearray.size();
        int ai[] = new int[j + i];
        for(int k = 0; k < j; k++)
            ai[k] = bytearray.at(k);

        (new ReedSolomonEncoder(GF256.QR_CODE_FIELD)).encode(ai, i);
        ByteArray bytearray1 = new ByteArray(i);
        for(int l = 0; l < i; l++)
            bytearray1.set(l, ai[j + l]);

        return bytearray1;
    }

    static int getAlphanumericCode(int i)
    {
        int j;
        if(i < ALPHANUMERIC_TABLE.length)
            j = ALPHANUMERIC_TABLE[i];
        else
            j = -1;
        return j;
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int i, int j, int k, int l, int ai[], int ai1[])
        throws WriterException
    {
        if(l >= k)
            throw new WriterException("Block ID too large");
        int i1 = i % k;
        int j1 = k - i1;
        int k1 = i / k;
        int l1 = k1 + 1;
        int i2 = j / k;
        int j2 = i2 + 1;
        int k2 = k1 - i2;
        int l2 = l1 - j2;
        if(k2 != l2)
            throw new WriterException("EC bytes mismatch");
        if(k != j1 + i1)
            throw new WriterException("RS blocks mismatch");
        if(i != j1 * (i2 + k2) + i1 * (j2 + l2))
            throw new WriterException("Total bytes mismatch");
        if(l < j1)
        {
            ai[0] = i2;
            ai1[0] = k2;
        } else
        {
            ai[0] = j2;
            ai1[0] = l2;
        }
    }

    private static void initQRCode(int i, ErrorCorrectionLevel errorcorrectionlevel, Mode mode, QRCode qrcode)
        throws WriterException
    {
        qrcode.setECLevel(errorcorrectionlevel);
        qrcode.setMode(mode);
        for(int j = 1; j <= 40; j++)
        {
            Version version = Version.getVersionForNumber(j);
            int k = version.getTotalCodewords();
            com.google.zxing.qrcode.decoder.Version.ECBlocks ecblocks = version.getECBlocksForLevel(errorcorrectionlevel);
            int l = ecblocks.getTotalECCodewords();
            int i1 = ecblocks.getNumBlocks();
            int j1 = k - l;
            if(j1 >= i + 3)
            {
                qrcode.setVersion(j);
                qrcode.setNumTotalBytes(k);
                qrcode.setNumDataBytes(j1);
                qrcode.setNumRSBlocks(i1);
                qrcode.setNumECBytes(l);
                qrcode.setMatrixWidth(version.getDimensionForVersion());
                return;
            }
        }

        throw new WriterException("Cannot find proper rs block info (input data too big?)");
    }

    static void interleaveWithECBytes(BitVector bitvector, int i, int j, int k, BitVector bitvector1)
        throws WriterException
    {
        if(bitvector.sizeInBytes() != j)
            throw new WriterException("Number of bits and data bytes does not match");
        Vector vector = new Vector(k);
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1;
        int j3;
        for(k1 = 0; l < k; k1 = j3)
        {
            int ai[] = new int[1];
            int ai1[] = new int[1];
            getNumDataBytesAndNumECBytesForBlockID(i, j, k, l, ai, ai1);
            ByteArray bytearray2 = new ByteArray();
            bytearray2.set(bitvector.getArray(), k1, ai[0]);
            ByteArray bytearray3 = generateECBytes(bytearray2, ai1[0]);
            vector.addElement(new BlockPair(bytearray2, bytearray3));
            int l2 = Math.max(j1, bytearray2.size());
            int i3 = Math.max(i1, bytearray3.size());
            j3 = k1 + ai[0];
            l++;
            i1 = i3;
            j1 = l2;
        }

        if(j != k1)
            throw new WriterException("Data bytes does not match offset");
        for(int l1 = 0; l1 < j1; l1++)
        {
            for(int k2 = 0; k2 < vector.size(); k2++)
            {
                ByteArray bytearray1 = ((BlockPair)vector.elementAt(k2)).getDataBytes();
                if(l1 < bytearray1.size())
                    bitvector1.appendBits(bytearray1.at(l1), 8);
            }

        }

        for(int i2 = 0; i2 < i1; i2++)
        {
            for(int j2 = 0; j2 < vector.size(); j2++)
            {
                ByteArray bytearray = ((BlockPair)vector.elementAt(j2)).getErrorCorrectionBytes();
                if(i2 < bytearray.size())
                    bitvector1.appendBits(bytearray.at(i2), 8);
            }

        }

        if(i != bitvector1.sizeInBytes())
            throw new WriterException("Interleaving error: " + i + " and " + bitvector1.sizeInBytes() + " differ.");
        else
            return;
    }

    static void terminateBits(int i, BitVector bitvector)
        throws WriterException
    {
        int j = i << 3;
        if(bitvector.size() > j)
            throw new WriterException("data bits cannot fit in the QR Code" + bitvector.size() + " > " + j);
        for(int k = 0; k < 4 && bitvector.size() < j; k++)
            bitvector.appendBit(0);

        int l = bitvector.size() % 8;
        if(l > 0)
        {
            int k1 = 8 - l;
            for(int l1 = 0; l1 < k1; l1++)
                bitvector.appendBit(0);

        }
        if(bitvector.size() % 8 != 0)
            throw new WriterException("Number of bits is not a multiple of 8");
        int i1 = i - bitvector.sizeInBytes();
        int j1 = 0;
        while(j1 < i1) 
        {
            if(j1 % 2 == 0)
                bitvector.appendBits(236, 8);
            else
                bitvector.appendBits(17, 8);
            j1++;
        }
        if(bitvector.size() != j)
            throw new WriterException("Bits size does not equal capacity");
        else
            return;
    }

    private static final int ALPHANUMERIC_TABLE[];
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    static 
    {
        int ai[] = new int[96];
        ai[0] = -1;
        ai[1] = -1;
        ai[2] = -1;
        ai[3] = -1;
        ai[4] = -1;
        ai[5] = -1;
        ai[6] = -1;
        ai[7] = -1;
        ai[8] = -1;
        ai[9] = -1;
        ai[10] = -1;
        ai[11] = -1;
        ai[12] = -1;
        ai[13] = -1;
        ai[14] = -1;
        ai[15] = -1;
        ai[16] = -1;
        ai[17] = -1;
        ai[18] = -1;
        ai[19] = -1;
        ai[20] = -1;
        ai[21] = -1;
        ai[22] = -1;
        ai[23] = -1;
        ai[24] = -1;
        ai[25] = -1;
        ai[26] = -1;
        ai[27] = -1;
        ai[28] = -1;
        ai[29] = -1;
        ai[30] = -1;
        ai[31] = -1;
        ai[32] = 36;
        ai[33] = -1;
        ai[34] = -1;
        ai[35] = -1;
        ai[36] = 37;
        ai[37] = 38;
        ai[38] = -1;
        ai[39] = -1;
        ai[40] = -1;
        ai[41] = -1;
        ai[42] = 39;
        ai[43] = 40;
        ai[44] = -1;
        ai[45] = 41;
        ai[46] = 42;
        ai[47] = 43;
        ai[48] = 0;
        ai[49] = 1;
        ai[50] = 2;
        ai[51] = 3;
        ai[52] = 4;
        ai[53] = 5;
        ai[54] = 6;
        ai[55] = 7;
        ai[56] = 8;
        ai[57] = 9;
        ai[58] = 44;
        ai[59] = -1;
        ai[60] = -1;
        ai[61] = -1;
        ai[62] = -1;
        ai[63] = -1;
        ai[64] = -1;
        ai[65] = 10;
        ai[66] = 11;
        ai[67] = 12;
        ai[68] = 13;
        ai[69] = 14;
        ai[70] = 15;
        ai[71] = 16;
        ai[72] = 17;
        ai[73] = 18;
        ai[74] = 19;
        ai[75] = 20;
        ai[76] = 21;
        ai[77] = 22;
        ai[78] = 23;
        ai[79] = 24;
        ai[80] = 25;
        ai[81] = 26;
        ai[82] = 27;
        ai[83] = 28;
        ai[84] = 29;
        ai[85] = 30;
        ai[86] = 31;
        ai[87] = 32;
        ai[88] = 33;
        ai[89] = 34;
        ai[90] = 35;
        ai[91] = -1;
        ai[92] = -1;
        ai[93] = -1;
        ai[94] = -1;
        ai[95] = -1;
        ALPHANUMERIC_TABLE = ai;
    }
}
