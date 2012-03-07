// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;

import java.util.Hashtable;

// Referenced classes of package com.google.zxing:
//            BarcodeFormat, ResultPoint, ResultMetadataType

public final class Result
{

    public Result(String s, byte abyte0[], ResultPoint aresultpoint[], BarcodeFormat barcodeformat)
    {
        if(s == null && abyte0 == null)
        {
            throw new IllegalArgumentException("Text and bytes are null");
        } else
        {
            text = s;
            rawBytes = abyte0;
            resultPoints = aresultpoint;
            format = barcodeformat;
            resultMetadata = null;
            return;
        }
    }

    public BarcodeFormat getBarcodeFormat()
    {
        return format;
    }

    public byte[] getRawBytes()
    {
        return rawBytes;
    }

    public Hashtable getResultMetadata()
    {
        return resultMetadata;
    }

    public ResultPoint[] getResultPoints()
    {
        return resultPoints;
    }

    public String getText()
    {
        return text;
    }

    public void putMetadata(ResultMetadataType resultmetadatatype, Object obj)
    {
        if(resultMetadata == null)
            resultMetadata = new Hashtable(3);
        resultMetadata.put(resultmetadatatype, obj);
    }

    public String toString()
    {
        String s;
        if(text == null)
            s = "[" + rawBytes.length + " bytes]";
        else
            s = text;
        return s;
    }

    private final BarcodeFormat format;
    private final byte rawBytes[];
    private Hashtable resultMetadata;
    private final ResultPoint resultPoints[];
    private final String text;
}
