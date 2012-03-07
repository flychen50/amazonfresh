// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode;

import com.google.zxing.*;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Hashtable;

public final class QRCodeWriter
    implements Writer
{

    public QRCodeWriter()
    {
    }

    private static ByteMatrix renderResult(QRCode qrcode, int i, int j)
    {
        ByteMatrix bytematrix = qrcode.getMatrix();
        int k = bytematrix.getWidth();
        int l = bytematrix.getHeight();
        int i1 = k + 8;
        int j1 = l + 8;
        int k1 = Math.max(i, i1);
        int l1 = Math.max(j, j1);
        int i2 = Math.min(k1 / i1, l1 / j1);
        int j2 = (k1 - k * i2) / 2;
        int k2 = (l1 - l * i2) / 2;
        ByteMatrix bytematrix1 = new ByteMatrix(k1, l1);
        byte abyte0[][] = bytematrix1.getArray();
        byte abyte1[] = new byte[k1];
        for(int l2 = 0; l2 < k2; l2++)
            setRowColor(abyte0[l2], (byte)-1);

        byte abyte2[][] = bytematrix.getArray();
        for(int i3 = 0; i3 < l; i3++)
        {
            for(int k3 = 0; k3 < j2; k3++)
                abyte1[k3] = -1;

            int l3 = j2;
            for(int i4 = 0; i4 < k; i4++)
            {
                byte byte0;
                int i5;
                if(abyte2[i3][i4] == 1)
                    byte0 = 0;
                else
                    byte0 = -1;
                for(i5 = 0; i5 < i2; i5++)
                    abyte1[l3 + i5] = byte0;

                l3 += i2;
            }

            for(int j4 = j2 + k * i2; j4 < k1; j4++)
                abyte1[j4] = -1;

            int k4 = k2 + i3 * i2;
            for(int l4 = 0; l4 < i2; l4++)
                System.arraycopy(abyte1, 0, abyte0[k4 + l4], 0, k1);

        }

        for(int j3 = k2 + l * i2; j3 < l1; j3++)
            setRowColor(abyte0[j3], (byte)-1);

        return bytematrix1;
    }

    private static void setRowColor(byte abyte0[], byte byte0)
    {
        for(int i = 0; i < abyte0.length; i++)
            abyte0[i] = byte0;

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
        if(barcodeformat != BarcodeFormat.QR_CODE)
            throw new IllegalArgumentException("Can only encode QR_CODE, but got " + barcodeformat);
        if(i < 0 || j < 0)
            throw new IllegalArgumentException("Requested dimensions are too small: " + i + 'x' + j);
        ErrorCorrectionLevel errorcorrectionlevel = ErrorCorrectionLevel.L;
        if(hashtable != null)
        {
            ErrorCorrectionLevel errorcorrectionlevel1 = (ErrorCorrectionLevel)hashtable.get(EncodeHintType.ERROR_CORRECTION);
            if(errorcorrectionlevel1 != null)
                errorcorrectionlevel = errorcorrectionlevel1;
        }
        QRCode qrcode = new QRCode();
        Encoder.encode(s, errorcorrectionlevel, hashtable, qrcode);
        return renderResult(qrcode, i, j);
    }

    private static final int QUIET_ZONE_SIZE = 4;
}
