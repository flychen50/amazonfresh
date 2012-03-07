// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

// Referenced classes of package com.google.zxing.oned:
//            OneDReader

public interface UPCEANReader
    extends OneDReader
{

    public abstract Result decodeRow(int i, BitArray bitarray, int ai[])
        throws ReaderException;
}
