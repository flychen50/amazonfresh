// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.graphics.Bitmap;
import com.google.zxing.LuminanceSource;

public abstract class BaseLuminanceSource extends LuminanceSource
{

    BaseLuminanceSource(int i, int j)
    {
        super(i, j);
    }

    public abstract int getDataHeight();

    public abstract int getDataWidth();

    public abstract Bitmap renderCroppedGreyscaleBitmap();

    public abstract Bitmap renderFullColorBitmap(boolean flag);
}
