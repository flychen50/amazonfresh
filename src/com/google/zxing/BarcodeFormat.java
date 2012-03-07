// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;

import java.util.Hashtable;

public final class BarcodeFormat
{

    private BarcodeFormat(String s)
    {
        name = s;
        VALUES.put(s, this);
    }

    public static BarcodeFormat valueOf(String s)
    {
        BarcodeFormat barcodeformat = (BarcodeFormat)VALUES.get(s);
        if(barcodeformat == null)
            throw new IllegalArgumentException();
        else
            return barcodeformat;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return name;
    }

    public static final BarcodeFormat CODE_128 = new BarcodeFormat("CODE_128");
    public static final BarcodeFormat CODE_39 = new BarcodeFormat("CODE_39");
    public static final BarcodeFormat DATAMATRIX = new BarcodeFormat("DATAMATRIX");
    public static final BarcodeFormat EAN_13 = new BarcodeFormat("EAN_13");
    public static final BarcodeFormat EAN_8 = new BarcodeFormat("EAN_8");
    public static final BarcodeFormat ITF = new BarcodeFormat("ITF");
    public static final BarcodeFormat PDF417 = new BarcodeFormat("PDF417");
    public static final BarcodeFormat QR_CODE = new BarcodeFormat("QR_CODE");
    public static final BarcodeFormat UPC_A = new BarcodeFormat("UPC_A");
    public static final BarcodeFormat UPC_E = new BarcodeFormat("UPC_E");
    private static final Hashtable VALUES = new Hashtable();
    private final String name;

}
