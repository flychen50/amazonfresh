// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.*;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.google.zxing.oned:
//            AbstractOneDReader, MultiFormatUPCEANReader, Code39Reader, Code128Reader, 
//            ITFReader, OneDReader

public final class MultiFormatOneDReader extends AbstractOneDReader
{

    public MultiFormatOneDReader(Hashtable hashtable)
    {
        Vector vector;
        if(hashtable == null)
            vector = null;
        else
            vector = (Vector)hashtable.get(DecodeHintType.POSSIBLE_FORMATS);
        if(vector != null)
        {
            if(vector.contains(BarcodeFormat.EAN_13) || vector.contains(BarcodeFormat.UPC_A) || vector.contains(BarcodeFormat.EAN_8) || vector.contains(BarcodeFormat.UPC_E))
                readers.addElement(new MultiFormatUPCEANReader(hashtable));
            if(vector.contains(BarcodeFormat.CODE_39))
                readers.addElement(new Code39Reader());
            if(vector.contains(BarcodeFormat.CODE_128))
                readers.addElement(new Code128Reader());
            if(vector.contains(BarcodeFormat.ITF))
                readers.addElement(new ITFReader());
        }
        if(readers.isEmpty())
        {
            readers.addElement(new MultiFormatUPCEANReader(hashtable));
            readers.addElement(new Code39Reader());
            readers.addElement(new Code128Reader());
            readers.addElement(new ITFReader());
        }
    }

    public Result decodeRow(int i, BitArray bitarray, Hashtable hashtable)
        throws ReaderException
    {
        int j;
        int k;
        j = readers.size();
        k = 0;
_L2:
        OneDReader onedreader;
        if(k >= j)
            break; /* Loop/switch isn't completed */
        onedreader = (OneDReader)readers.elementAt(k);
        Result result = onedreader.decodeRow(i, bitarray, hashtable);
        return result;
        ReaderException readerexception;
        readerexception;
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        throw ReaderException.getInstance();
    }

    private final Vector readers = new Vector();
}
