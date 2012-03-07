// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.decoder;

import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;

// Referenced classes of package com.google.zxing.qrcode.decoder:
//            FormatInformation, DataMask, Version

final class BitMatrixParser
{

    BitMatrixParser(BitMatrix bitmatrix)
        throws ReaderException
    {
        int i = bitmatrix.getDimension();
        if(i < 21 || (i & 3) != 1)
        {
            throw ReaderException.getInstance();
        } else
        {
            bitMatrix = bitmatrix;
            return;
        }
    }

    private int copyBit(int i, int j, int k)
    {
        int l;
        if(bitMatrix.get(j, i))
            l = 1 | k << 1;
        else
            l = k << 1;
        return l;
    }

    byte[] readCodewords()
        throws ReaderException
    {
        Version version;
        int i;
        BitMatrix bitmatrix;
        boolean flag;
        byte abyte0[];
        int j;
        int k;
        int l;
        int i1;
        FormatInformation formatinformation = readFormatInformation();
        version = readVersion();
        DataMask datamask = DataMask.forReference(formatinformation.getDataMask());
        i = bitMatrix.getDimension();
        datamask.unmaskBitMatrix(bitMatrix, i);
        bitmatrix = version.buildFunctionPattern();
        flag = true;
        abyte0 = new byte[version.getTotalCodewords()];
        j = 0;
        k = 0;
        l = 0;
        i1 = i - 1;
_L8:
        if(i1 <= 0) goto _L2; else goto _L1
_L1:
        int k1;
        if(i1 == 6)
            i1--;
        k1 = 0;
_L7:
        if(k1 >= i) goto _L4; else goto _L3
_L3:
        int j2;
        int k2;
        int l1;
        int i2;
        if(flag)
            l1 = i - 1 - k1;
        else
            l1 = k1;
        i2 = 0;
        j2 = j;
        if(i2 >= 2)
            break; /* Loop/switch isn't completed */
        if(bitmatrix.get(i1 - i2, l1))
            break MISSING_BLOCK_LABEL_254;
        l++;
        k <<= 1;
        if(bitMatrix.get(i1 - i2, l1))
            k |= 1;
        if(l != 8)
            break MISSING_BLOCK_LABEL_254;
        k2 = j2 + 1;
        abyte0[j2] = (byte)k;
        l = 0;
        k = 0;
_L9:
        i2++;
        j2 = k2;
        if(true) goto _L6; else goto _L5
_L6:
        break MISSING_BLOCK_LABEL_115;
_L5:
        k1++;
        j = j2;
          goto _L7
_L4:
        flag ^= true;
        i1 -= 2;
          goto _L8
_L2:
        int j1 = version.getTotalCodewords();
        if(j != j1)
            throw ReaderException.getInstance();
        else
            return abyte0;
        k2 = j2;
          goto _L9
    }

    FormatInformation readFormatInformation()
        throws ReaderException
    {
        FormatInformation formatinformation;
        if(parsedFormatInfo != null)
        {
            formatinformation = parsedFormatInfo;
        } else
        {
            int i = 0;
            for(int j = 0; j < 6; j++)
                i = copyBit(8, j, i);

            int k = copyBit(7, 8, copyBit(8, 8, copyBit(8, 7, i)));
            for(int l = 5; l >= 0; l--)
                k = copyBit(l, 8, k);

            parsedFormatInfo = FormatInformation.decodeFormatInformation(k);
            if(parsedFormatInfo != null)
            {
                formatinformation = parsedFormatInfo;
            } else
            {
                int i1 = bitMatrix.getDimension();
                int j1 = 0;
                int k1 = i1 - 8;
                for(int l1 = i1 - 1; l1 >= k1; l1--)
                    j1 = copyBit(l1, 8, j1);

                for(int i2 = i1 - 7; i2 < i1; i2++)
                    j1 = copyBit(8, i2, j1);

                parsedFormatInfo = FormatInformation.decodeFormatInformation(j1);
                if(parsedFormatInfo != null)
                    formatinformation = parsedFormatInfo;
                else
                    throw ReaderException.getInstance();
            }
        }
        return formatinformation;
    }

    Version readVersion()
        throws ReaderException
    {
        Version version;
        if(parsedVersion != null)
        {
            version = parsedVersion;
        } else
        {
            int i = bitMatrix.getDimension();
            int j = i - 17 >> 2;
            if(j <= 6)
            {
                version = Version.getVersionForNumber(j);
            } else
            {
                int k = 0;
                for(int l = 5; l >= 0; l--)
                {
                    int i2 = i - 11;
                    for(int j2 = i - 9; j2 >= i2; j2--)
                        k = copyBit(l, j2, k);

                }

                parsedVersion = Version.decodeVersionInformation(k);
                if(parsedVersion != null && parsedVersion.getDimensionForVersion() == i)
                {
                    version = parsedVersion;
                } else
                {
                    int i1 = 0;
                    for(int j1 = 5; j1 >= 0; j1--)
                    {
                        int k1 = i - 11;
                        for(int l1 = i - 9; l1 >= k1; l1--)
                            i1 = copyBit(l1, j1, i1);

                    }

                    parsedVersion = Version.decodeVersionInformation(i1);
                    if(parsedVersion != null && parsedVersion.getDimensionForVersion() == i)
                        version = parsedVersion;
                    else
                        throw ReaderException.getInstance();
                }
            }
        }
        return version;
    }

    private final BitMatrix bitMatrix;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;
}
