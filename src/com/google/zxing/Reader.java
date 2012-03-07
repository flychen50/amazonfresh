// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;

import java.util.Hashtable;

// Referenced classes of package com.google.zxing:
//            ReaderException, BinaryBitmap, Result

public interface Reader
{

    public abstract Result decode(BinaryBitmap binarybitmap)
        throws ReaderException;

    public abstract Result decode(BinaryBitmap binarybitmap, Hashtable hashtable)
        throws ReaderException;
}
