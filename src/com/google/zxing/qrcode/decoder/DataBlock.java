// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.decoder;


// Referenced classes of package com.google.zxing.qrcode.decoder:
//            Version, ErrorCorrectionLevel

final class DataBlock
{

    private DataBlock(int i, byte abyte0[])
    {
        numDataCodewords = i;
        codewords = abyte0;
    }

    static DataBlock[] getDataBlocks(byte abyte0[], Version version, ErrorCorrectionLevel errorcorrectionlevel)
    {
        Version.ECBlocks ecblocks;
        DataBlock adatablock[];
        int l;
        int i1;
        int j1;
        if(abyte0.length != version.getTotalCodewords())
            throw new IllegalArgumentException();
        ecblocks = version.getECBlocksForLevel(errorcorrectionlevel);
        int i = 0;
        Version.ECB aecb[] = ecblocks.getECBlocks();
        for(int j = 0; j < aecb.length; j++)
            i += aecb[j].getCount();

        adatablock = new DataBlock[i];
        int k = 0;
        int k5;
        for(l = 0; k < aecb.length; l = k5)
        {
            Version.ECB ecb = aecb[k];
            int j5 = 0;
            int j6;
            for(k5 = l; j5 < ecb.getCount(); k5 = j6)
            {
                int l5 = ecb.getDataCodewords();
                int i6 = l5 + ecblocks.getECCodewordsPerBlock();
                j6 = k5 + 1;
                adatablock[k5] = new DataBlock(l5, new byte[i6]);
                j5++;
            }

            k++;
        }

        i1 = adatablock[0].codewords.length;
        j1 = adatablock.length - 1;
_L6:
        if(j1 >= 0 && adatablock[j1].codewords.length != i1) goto _L2; else goto _L1
_L1:
        int k1;
        int l1;
        int i2;
        int j2;
        k1 = j1 + 1;
        l1 = i1 - ecblocks.getECCodewordsPerBlock();
        i2 = 0;
        j2 = 0;
_L4:
        if(i2 >= l1)
            break; /* Loop/switch isn't completed */
        int k4 = 0;
        int l4;
        int i5;
        for(l4 = j2; k4 < l; l4 = i5)
        {
            byte abyte3[] = adatablock[k4].codewords;
            i5 = l4 + 1;
            abyte3[i2] = abyte0[l4];
            k4++;
        }

        i2++;
        j2 = l4;
        continue; /* Loop/switch isn't completed */
_L2:
        j1--;
        continue; /* Loop/switch isn't completed */
        if(true) goto _L4; else goto _L3
_L3:
        for(int k2 = k1; k2 < l;)
        {
            byte abyte2[] = adatablock[k2].codewords;
            int j4 = j2 + 1;
            abyte2[l1] = abyte0[j2];
            k2++;
            j2 = j4;
        }

        int l2 = adatablock[0].codewords.length;
        for(int i3 = l1; i3 < l2;)
        {
            int j3 = j2;
            int k3 = 0;
            while(k3 < l) 
            {
                int l3;
                byte abyte1[];
                int i4;
                if(k3 < k1)
                    l3 = i3;
                else
                    l3 = i3 + 1;
                abyte1 = adatablock[k3].codewords;
                i4 = j3 + 1;
                abyte1[l3] = abyte0[j3];
                k3++;
                j3 = i4;
            }
            i3++;
            j2 = j3;
        }

        return adatablock;
        if(true) goto _L6; else goto _L5
_L5:
    }

    byte[] getCodewords()
    {
        return codewords;
    }

    int getNumDataCodewords()
    {
        return numDataCodewords;
    }

    private final byte codewords[];
    private final int numDataCodewords;
}
