// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.decoder;

import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.*;

// Referenced classes of package com.google.zxing.qrcode.decoder:
//            BitMatrixParser, FormatInformation, DataBlock, DecodedBitStreamParser

public final class Decoder
{

    public Decoder()
    {
        rsDecoder = new ReedSolomonDecoder(GF256.QR_CODE_FIELD);
    }

    private void correctErrors(byte abyte0[], int i)
        throws ReaderException
    {
        int j = abyte0.length;
        int ai[] = new int[j];
        for(int k = 0; k < j; k++)
            ai[k] = 0xff & abyte0[k];

        int l = abyte0.length - i;
        int i1;
        try
        {
            rsDecoder.decode(ai, l);
        }
        catch(ReedSolomonException reedsolomonexception)
        {
            throw ReaderException.getInstance();
        }
        for(i1 = 0; i1 < i; i1++)
            abyte0[i1] = (byte)ai[i1];

    }

    public DecoderResult decode(BitMatrix bitmatrix)
        throws ReaderException
    {
        BitMatrixParser bitmatrixparser = new BitMatrixParser(bitmatrix);
        Version version = bitmatrixparser.readVersion();
        ErrorCorrectionLevel errorcorrectionlevel = bitmatrixparser.readFormatInformation().getErrorCorrectionLevel();
        DataBlock adatablock[] = DataBlock.getDataBlocks(bitmatrixparser.readCodewords(), version, errorcorrectionlevel);
        int i = 0;
        int j = 0;
        do
        {
            int k = adatablock.length;
            if(j >= k)
                break;
            i += adatablock[j].getNumDataCodewords();
            j++;
        } while(true);
        byte abyte0[] = new byte[i];
        int l = 0;
        int i1 = 0;
        do
        {
            int j1 = adatablock.length;
            if(i1 < j1)
            {
                DataBlock datablock = adatablock[i1];
                byte abyte1[] = datablock.getCodewords();
                int k1 = datablock.getNumDataCodewords();
                correctErrors(abyte1, k1);
                int l1 = 0;
                int i2;
                int j2;
                for(i2 = l; l1 < k1; i2 = j2)
                {
                    j2 = i2 + 1;
                    abyte0[i2] = abyte1[l1];
                    l1++;
                }

                i1++;
                l = i2;
            } else
            {
                return DecodedBitStreamParser.decode(abyte0, version, errorcorrectionlevel);
            }
        } while(true);
    }

    public DecoderResult decode(boolean aflag[][])
        throws ReaderException
    {
        int i = aflag.length;
        BitMatrix bitmatrix = new BitMatrix(i);
        for(int j = 0; j < i; j++)
        {
            for(int k = 0; k < i; k++)
                if(aflag[j][k])
                    bitmatrix.set(k, j);

        }

        return decode(bitmatrix);
    }

    private final ReedSolomonDecoder rsDecoder;
}
