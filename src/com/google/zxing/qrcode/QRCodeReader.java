// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.detector.Detector;
import java.util.Hashtable;

public class QRCodeReader
    implements Reader
{

    public QRCodeReader()
    {
    }

    private static BitMatrix extractPureBits(BitMatrix bitmatrix)
        throws ReaderException
    {
        int i = bitmatrix.getHeight();
        int j = bitmatrix.getWidth();
        int k = Math.min(i, j);
        int l;
        for(l = 0; l < k && !bitmatrix.get(l, l); l++);
        if(l == k)
            throw ReaderException.getInstance();
        int i1;
        for(i1 = l; i1 < k && bitmatrix.get(i1, i1); i1++);
        if(i1 == k)
            throw ReaderException.getInstance();
        int j1 = i1 - l;
        int k1;
        for(k1 = j - 1; k1 >= 0 && !bitmatrix.get(k1, l); k1--);
        if(k1 < 0)
            throw ReaderException.getInstance();
        int l1 = k1 + 1;
        if((l1 - l) % j1 != 0)
            throw ReaderException.getInstance();
        int i2 = (l1 - l) / j1;
        int j2 = l + (j1 >> 1);
        int k2 = j2 + j1 * (i2 - 1);
        if(k2 >= j || k2 >= i)
            throw ReaderException.getInstance();
        BitMatrix bitmatrix1 = new BitMatrix(i2);
        for(int l2 = 0; l2 < i2; l2++)
        {
            int i3 = j2 + l2 * j1;
            for(int j3 = 0; j3 < i2; j3++)
                if(bitmatrix.get(j2 + j3 * j1, i3))
                    bitmatrix1.set(j3, l2);

        }

        return bitmatrix1;
    }

    public Result decode(BinaryBitmap binarybitmap)
        throws ReaderException
    {
        return decode(binarybitmap, null);
    }

    public Result decode(BinaryBitmap binarybitmap, Hashtable hashtable)
        throws ReaderException
    {
        DecoderResult decoderresult;
        ResultPoint aresultpoint[];
        Result result;
        if(hashtable != null && hashtable.containsKey(DecodeHintType.PURE_BARCODE))
        {
            BitMatrix bitmatrix = extractPureBits(binarybitmap.getBlackMatrix());
            decoderresult = decoder.decode(bitmatrix);
            aresultpoint = NO_POINTS;
        } else
        {
            DetectorResult detectorresult = (new Detector(binarybitmap.getBlackMatrix())).detect(hashtable);
            decoderresult = decoder.decode(detectorresult.getBits());
            aresultpoint = detectorresult.getPoints();
        }
        result = new Result(decoderresult.getText(), decoderresult.getRawBytes(), aresultpoint, BarcodeFormat.QR_CODE);
        if(decoderresult.getByteSegments() != null)
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, decoderresult.getByteSegments());
        if(decoderresult.getECLevel() != null)
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, decoderresult.getECLevel().toString());
        return result;
    }

    protected Decoder getDecoder()
    {
        return decoder;
    }

    private static final ResultPoint NO_POINTS[] = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

}
