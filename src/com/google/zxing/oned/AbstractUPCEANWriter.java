// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.oned:
//            UPCEANWriter, AbstractUPCEANReader

public abstract class AbstractUPCEANWriter
    implements UPCEANWriter
{

    public AbstractUPCEANWriter()
    {
    }

    protected static int appendPattern(byte abyte0[], int i, int ai[], int j)
    {
        if(j != 0 && j != 1)
            throw new IllegalArgumentException("startColor must be either 0 or 1, but got: " + j);
        int k = (byte)j;
        int l = 0;
        for(int i1 = 0; i1 < ai.length; i1++)
        {
            for(int j1 = 0; j1 < ai[i1]; j1++)
            {
                abyte0[i] = k;
                i++;
                l++;
            }

            k ^= 1;
        }

        return l;
    }

    private static ByteMatrix renderResult(byte abyte0[], int i, int j)
    {
        int k = abyte0.length;
        int l = k + (AbstractUPCEANReader.START_END_PATTERN.length << 1);
        int i1 = Math.max(i, l);
        int j1 = Math.max(1, j);
        int k1 = i1 / l;
        int l1 = (i1 - k * k1) / 2;
        ByteMatrix bytematrix = new ByteMatrix(i1, j1);
        byte abyte1[][] = bytematrix.getArray();
        byte abyte2[] = new byte[i1];
        for(int i2 = 0; i2 < l1; i2++)
            abyte2[i2] = -1;

        int j2 = l1;
        for(int k2 = 0; k2 < k; k2++)
        {
            byte byte0;
            int j3;
            if(abyte0[k2] == 1)
                byte0 = 0;
            else
                byte0 = -1;
            for(j3 = 0; j3 < k1; j3++)
                abyte2[j2 + j3] = byte0;

            j2 += k1;
        }

        for(int l2 = l1 + k * k1; l2 < i1; l2++)
            abyte2[l2] = -1;

        for(int i3 = 0; i3 < j1; i3++)
            System.arraycopy(abyte2, 0, abyte1[i3], 0, i1);

        return bytematrix;
    }

    public ByteMatrix encode(String s, BarcodeFormat barcodeformat, int i, int j)
        throws WriterException
    {
        return encode(s, barcodeformat, i, j, null);
    }

    public ByteMatrix encode(String s, BarcodeFormat barcodeformat, int i, int j, Hashtable hashtable)
        throws WriterException
    {
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("Found empty contents");
        if(i < 0 || j < 0)
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + j);
        else
            return renderResult(encode(s), i, j);
    }
}
