// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;

import com.google.zxing.common.ByteMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing:
//            Writer, WriterException, BarcodeFormat

public final class MultiFormatWriter
    implements Writer
{

    public MultiFormatWriter()
    {
    }

    public ByteMatrix encode(String s, BarcodeFormat barcodeformat, int i, int j)
        throws WriterException
    {
        return encode(s, barcodeformat, i, j, null);
    }

    public ByteMatrix encode(String s, BarcodeFormat barcodeformat, int i, int j, Hashtable hashtable)
        throws WriterException
    {
        ByteMatrix bytematrix;
        if(barcodeformat == BarcodeFormat.EAN_8)
            bytematrix = (new EAN8Writer()).encode(s, barcodeformat, i, j, hashtable);
        else
        if(barcodeformat == BarcodeFormat.EAN_13)
            bytematrix = (new EAN13Writer()).encode(s, barcodeformat, i, j, hashtable);
        else
        if(barcodeformat == BarcodeFormat.QR_CODE)
            bytematrix = (new QRCodeWriter()).encode(s, barcodeformat, i, j, hashtable);
        else
            throw new IllegalArgumentException("No encoder available for format " + barcodeformat);
        return bytematrix;
    }
}
