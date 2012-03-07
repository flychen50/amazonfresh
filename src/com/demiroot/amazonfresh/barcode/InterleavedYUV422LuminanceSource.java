// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            BaseLuminanceSource

public final class InterleavedYUV422LuminanceSource extends BaseLuminanceSource
{

    public InterleavedYUV422LuminanceSource(byte abyte0[], int i, int j, int k, int l, int i1, int j1)
    {
        super(i1, j1);
        if(k + i1 > i || l + j1 > j)
        {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        } else
        {
            yuvData = abyte0;
            dataWidth = i;
            dataHeight = j;
            left = k;
            top = l;
            return;
        }
    }

    public LuminanceSource crop(int i, int j, int k, int l)
    {
        return new InterleavedYUV422LuminanceSource(yuvData, dataWidth, dataHeight, i, j, k, l);
    }

    public int getDataHeight()
    {
        return dataHeight;
    }

    public int getDataWidth()
    {
        return dataWidth;
    }

    public byte[] getMatrix()
    {
        int i;
        int j;
        byte abyte0[];
        int k;
        byte abyte1[];
        int l;
        i = getWidth();
        j = getHeight();
        abyte0 = new byte[i * j];
        k = 2 * (top * dataWidth) + 2 * left;
        abyte1 = yuvData;
        l = 0;
_L2:
        if(l >= j)
            return abyte0;
        int i1 = l * i;
        int j1 = 0;
        do
        {
label0:
            {
                if(j1 < i)
                    break label0;
                k += 2 * dataWidth;
                l++;
            }
            if(true)
                continue;
            abyte0[i1 + j1] = abyte1[k + (j1 << 1)];
            j1++;
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public byte[] getRow(int i, byte abyte0[])
    {
        if(i < 0 || i >= getHeight())
            throw new IllegalArgumentException((new StringBuilder("Requested row is outside the image: ")).append(i).toString());
        int j = getWidth();
        if(abyte0 == null || abyte0.length < j)
            abyte0 = new byte[j];
        int k = 2 * ((i + top) * dataWidth) + 2 * left;
        byte abyte1[] = yuvData;
        int l = 0;
        do
        {
            if(l >= j)
                return abyte0;
            abyte0[l] = abyte1[k + (l << 1)];
            l++;
        } while(true);
    }

    public boolean isCropSupported()
    {
        return true;
    }

    public Bitmap renderCroppedGreyscaleBitmap()
    {
        int i;
        int j;
        int ai[];
        byte abyte0[];
        int k;
        int l;
        i = getWidth();
        j = getHeight();
        ai = new int[i * j];
        abyte0 = yuvData;
        k = 2 * (top * dataWidth) + 2 * left;
        l = 0;
_L2:
        if(l >= j)
        {
            Bitmap bitmap = Bitmap.createBitmap(i, j, android.graphics.Bitmap.Config.ARGB_8888);
            bitmap.setPixels(ai, 0, i, 0, 0, i, j);
            return bitmap;
        }
        int i1 = l * i;
        int j1 = 0;
        do
        {
label0:
            {
                if(j1 < i)
                    break label0;
                k += 2 * dataWidth;
                l++;
            }
            if(true)
                continue;
            int k1 = 0xff & abyte0[k + (j1 << 1)];
            ai[i1 + j1] = 0xff000000 | 0x10101 * k1;
            j1++;
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Bitmap renderFullColorBitmap(boolean flag)
    {
        return null;
    }

    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final int top;
    private final byte yuvData[];
}
