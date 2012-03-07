// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.datamatrix.decoder;


// Referenced classes of package com.google.zxing.datamatrix.decoder:
//            Version

final class DataBlock
{

    private DataBlock(int i, byte abyte0[])
    {
        numDataCodewords = i;
        codewords = abyte0;
    }

    static DataBlock[] getDataBlocks(byte abyte0[], Version version)
    {
        Version.ECBlocks ecblocks = version.getECBlocks();
        int i = 0;
        Version.ECB aecb[] = ecblocks.getECBlocks();
        for(int j = 0; j < aecb.length; j++)
            i += aecb[j].getCount();

        DataBlock adatablock[] = new DataBlock[i];
        int k = 0;
        int l;
        int i6;
        for(l = 0; k < aecb.length; l = i6)
        {
            Version.ECB ecb = aecb[k];
            int l5 = 0;
            int l6;
            for(i6 = l; l5 < ecb.getCount(); i6 = l6)
            {
                int j6 = ecb.getDataCodewords();
                int k6 = j6 + ecblocks.getECCodewords();
                l6 = i6 + 1;
                adatablock[i6] = new DataBlock(j6, new byte[k6]);
                l5++;
            }

            k++;
        }

        int i1 = adatablock[0].codewords.length - ecblocks.getECCodewords();
        int j1 = i1 - 1;
        int k1 = 0;
        int l1;
        int j5;
        for(l1 = 0; k1 < j1; l1 = j5)
        {
            int i5 = 0;
            int k5;
            for(j5 = l1; i5 < l; j5 = k5)
            {
                byte abyte3[] = adatablock[i5].codewords;
                k5 = j5 + 1;
                abyte3[k1] = abyte0[j5];
                i5++;
            }

            k1++;
        }

        boolean flag;
        int i2;
        int j2;
        int k2;
        if(version.getVersionNumber() == 24)
            flag = true;
        else
            flag = false;
        if(flag)
            i2 = 8;
        else
            i2 = l;
        j2 = 0;
        int l4;
        for(k2 = l1; j2 < i2; k2 = l4)
        {
            byte abyte2[] = adatablock[j2].codewords;
            int k4 = i1 - 1;
            l4 = k2 + 1;
            abyte2[k4] = abyte0[k2];
            j2++;
        }

        int l2 = adatablock[0].codewords.length;
        int i3 = i1;
        int j3;
        int k3;
        for(j3 = k2; i3 < l2; j3 = k3)
        {
            k3 = j3;
            int l3 = 0;
            while(l3 < l) 
            {
                int i4;
                byte abyte1[];
                int j4;
                if(flag && l3 > 7)
                    i4 = i3 - 1;
                else
                    i4 = i3;
                abyte1 = adatablock[l3].codewords;
                j4 = k3 + 1;
                abyte1[i4] = abyte0[k3];
                l3++;
                k3 = j4;
            }
            i3++;
        }

        if(j3 != abyte0.length)
            throw new IllegalArgumentException();
        else
            return adatablock;
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
