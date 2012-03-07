// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned;

import com.google.zxing.*;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.oned:
//            OneDReader

public abstract class AbstractOneDReader
    implements OneDReader
{

    public AbstractOneDReader()
    {
    }

    private Result doDecode(BinaryBitmap binarybitmap, Hashtable hashtable)
        throws ReaderException
    {
        int i;
        BitArray bitarray;
        int j1;
        int i2;
        i = binarybitmap.getWidth();
        int j = binarybitmap.getHeight();
        bitarray = new BitArray(i);
        int k = j >> 1;
        boolean flag;
        byte byte0;
        int l;
        int i1;
        if(hashtable != null && hashtable.containsKey(DecodeHintType.TRY_HARDER))
            flag = true;
        else
            flag = false;
        if(flag)
            byte0 = 7;
        else
            byte0 = 4;
        l = Math.max(1, j >> byte0);
        if(flag)
            i1 = j;
        else
            i1 = 9;
        j1 = 0;
_L1:
label0:
        {
            if(j1 < i1)
            {
                int k1 = j1 + 1 >> 1;
                boolean flag1;
                int l1;
                if((j1 & 1) == 0)
                    flag1 = true;
                else
                    flag1 = false;
                if(flag1)
                    l1 = k1;
                else
                    l1 = -k1;
                i2 = k + l1 * l;
                if(i2 >= 0 && i2 < j)
                    break label0;
            }
            throw ReaderException.getInstance();
        }
        BitArray bitarray1 = binarybitmap.getBlackRow(i2, bitarray);
        int j2;
        bitarray = bitarray1;
        j2 = 0;
_L2:
        if(j2 >= 2)
            break MISSING_BLOCK_LABEL_310;
        if(j2 == 1)
            bitarray.reverse();
        Result result;
        result = decodeRow(i2, bitarray, hashtable);
        if(j2 == 1)
        {
            result.putMetadata(ResultMetadataType.ORIENTATION, new Integer(180));
            ResultPoint aresultpoint[] = result.getResultPoints();
            aresultpoint[0] = new ResultPoint((float)i - aresultpoint[0].getX() - 1.0F, aresultpoint[0].getY());
            aresultpoint[1] = new ResultPoint((float)i - aresultpoint[1].getX() - 1.0F, aresultpoint[1].getY());
        }
        return result;
        ReaderException readerexception;
        readerexception;
        j1++;
          goto _L1
        ReaderException readerexception1;
        readerexception1;
        j2++;
          goto _L2
    }

    static int patternMatchVariance(int ai[], int ai1[], int i)
    {
        int j;
        int k;
        int l;
        j = ai.length;
        k = 0;
        l = 0;
        for(int i1 = 0; i1 < j; i1++)
        {
            k += ai[i1];
            l += ai1[i1];
        }

        if(k >= l) goto _L2; else goto _L1
_L1:
        int j2 = 0x7fffffff;
_L4:
        return j2;
_L2:
        int j1 = (k << 8) / l;
        int k1 = i * j1 >> 8;
        int l1 = 0;
        int i2 = 0;
        do
        {
            if(i2 >= j)
                break;
            int k2 = ai[i2] << 8;
            int l2 = j1 * ai1[i2];
            int i3;
            if(k2 > l2)
                i3 = k2 - l2;
            else
                i3 = l2 - k2;
            if(i3 > k1)
            {
                j2 = 0x7fffffff;
                continue; /* Loop/switch isn't completed */
            }
            l1 += i3;
            i2++;
        } while(true);
        j2 = l1 / k;
        if(true) goto _L4; else goto _L3
_L3:
    }

    static void recordPattern(BitArray bitarray, int i, int ai[])
        throws ReaderException
    {
        int j;
        int l;
        boolean flag;
        int i1;
        int j1;
        j = ai.length;
        for(int k = 0; k < j; k++)
            ai[k] = 0;

        l = bitarray.getSize();
        if(i >= l)
            throw ReaderException.getInstance();
        if(!bitarray.get(i))
            flag = true;
        else
            flag = false;
        i1 = 0;
        j1 = i;
        if(j1 >= l) goto _L2; else goto _L1
_L1:
        if(!(flag ^ bitarray.get(j1))) goto _L4; else goto _L3
_L3:
        ai[i1] = 1 + ai[i1];
_L6:
        j1++;
        break MISSING_BLOCK_LABEL_56;
_L4:
        if(++i1 != j)
            break MISSING_BLOCK_LABEL_131;
_L2:
        if(i1 != j && (i1 != j - 1 || j1 != l))
            throw ReaderException.getInstance();
        else
            return;
        ai[i1] = 1;
        flag ^= true;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public final Result decode(BinaryBitmap binarybitmap)
        throws ReaderException
    {
        return decode(binarybitmap, null);
    }

    public final Result decode(BinaryBitmap binarybitmap, Hashtable hashtable)
        throws ReaderException
    {
        Result result2 = doDecode(binarybitmap, hashtable);
        Result result1 = result2;
_L2:
        return result1;
        ReaderException readerexception;
        readerexception;
        boolean flag;
        if(hashtable != null && hashtable.containsKey(DecodeHintType.TRY_HARDER))
            flag = true;
        else
            flag = false;
        if(flag && binarybitmap.isRotateSupported())
        {
            Result result = doDecode(binarybitmap.rotateCounterClockwise(), hashtable);
            Hashtable hashtable1 = result.getResultMetadata();
            int i = 270;
            if(hashtable1 != null && hashtable1.containsKey(ResultMetadataType.ORIENTATION))
                i = (i + ((Integer)hashtable1.get(ResultMetadataType.ORIENTATION)).intValue()) % 360;
            result.putMetadata(ResultMetadataType.ORIENTATION, new Integer(i));
            result1 = result;
        } else
        {
            throw readerexception;
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public abstract Result decodeRow(int i, BitArray bitarray, Hashtable hashtable)
        throws ReaderException;

    private static final int INTEGER_MATH_SHIFT = 8;
    static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;
}
