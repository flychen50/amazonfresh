// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.detector;

import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import java.util.Vector;

// Referenced classes of package com.google.zxing.qrcode.detector:
//            AlignmentPattern

final class AlignmentPatternFinder
{

    AlignmentPatternFinder(BitMatrix bitmatrix, int i, int j, int k, int l, float f)
    {
        image = bitmatrix;
        startX = i;
        startY = j;
        width = k;
        height = l;
        moduleSize = f;
    }

    private static float centerFromEnd(int ai[], int i)
    {
        return (float)(i - ai[2]) - (float)ai[1] / 2.0F;
    }

    private float crossCheckVertical(int i, int j, int k, int l)
    {
        BitMatrix bitmatrix = image;
        int i1 = bitmatrix.getHeight();
        int ai[] = crossCheckStateCount;
        ai[0] = 0;
        ai[1] = 0;
        ai[2] = 0;
        int j1;
        for(j1 = i; j1 >= 0 && bitmatrix.get(j, j1) && ai[1] <= k; j1--)
            ai[1] = 1 + ai[1];

        float f;
        if(j1 < 0 || ai[1] > k)
        {
            f = (0.0F / 0.0F);
        } else
        {
            for(; j1 >= 0 && !bitmatrix.get(j, j1) && ai[0] <= k; j1--)
                ai[0] = 1 + ai[0];

            if(ai[0] > k)
            {
                f = (0.0F / 0.0F);
            } else
            {
                int k1;
                for(k1 = i + 1; k1 < i1 && bitmatrix.get(j, k1) && ai[1] <= k; k1++)
                    ai[1] = 1 + ai[1];

                if(k1 == i1 || ai[1] > k)
                {
                    f = (0.0F / 0.0F);
                } else
                {
                    for(; k1 < i1 && !bitmatrix.get(j, k1) && ai[2] <= k; k1++)
                        ai[2] = 1 + ai[2];

                    if(ai[2] > k)
                        f = (0.0F / 0.0F);
                    else
                    if(5 * Math.abs((ai[0] + ai[1] + ai[2]) - l) >= l)
                        f = (0.0F / 0.0F);
                    else
                    if(foundPatternCross(ai))
                        f = centerFromEnd(ai, k1);
                    else
                        f = (0.0F / 0.0F);
                }
            }
        }
        return f;
    }

    private boolean foundPatternCross(int ai[])
    {
        float f;
        float f1;
        int i;
        f = moduleSize;
        f1 = f / 2.0F;
        i = 0;
_L3:
        if(i >= 3)
            break MISSING_BLOCK_LABEL_45;
        if(Math.abs(f - (float)ai[i]) < f1) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        i++;
          goto _L3
        flag = true;
          goto _L4
    }

    private AlignmentPattern handlePossibleCenter(int ai[], int i, int j)
    {
        float f;
        float f1;
        float f2;
        int l;
        int i1;
        int k = ai[0] + ai[1] + ai[2];
        f = centerFromEnd(ai, j);
        f1 = crossCheckVertical(i, (int)f, 2 * ai[1], k);
        if(Float.isNaN(f1))
            break MISSING_BLOCK_LABEL_149;
        f2 = (float)(ai[0] + ai[1] + ai[2]) / 3F;
        l = possibleCenters.size();
        i1 = 0;
_L5:
        if(i1 >= l) goto _L2; else goto _L1
_L1:
        if(!((AlignmentPattern)possibleCenters.elementAt(i1)).aboutEquals(f2, f1, f)) goto _L4; else goto _L3
_L3:
        AlignmentPattern alignmentpattern = new AlignmentPattern(f, f1, f2);
_L6:
        return alignmentpattern;
_L4:
        i1++;
          goto _L5
_L2:
        possibleCenters.addElement(new AlignmentPattern(f, f1, f2));
        alignmentpattern = null;
          goto _L6
    }

    AlignmentPattern find()
        throws ReaderException
    {
        int i;
        int j;
        int k;
        int l;
        int ai[];
        int i1;
        i = startX;
        j = height;
        k = i + width;
        l = startY + (j >> 1);
        ai = new int[3];
        i1 = 0;
_L17:
        if(i1 >= j) goto _L2; else goto _L1
_L1:
        int k1;
        int l1;
        int i2;
        int j1;
        if((i1 & 1) == 0)
            j1 = i1 + 1 >> 1;
        else
            j1 = -(i1 + 1 >> 1);
        k1 = l + j1;
        ai[0] = 0;
        ai[1] = 0;
        ai[2] = 0;
        for(l1 = i; l1 < k && !image.get(l1, k1); l1++);
        i2 = 0;
_L9:
        if(l1 >= k) goto _L4; else goto _L3
_L3:
        if(!image.get(l1, k1)) goto _L6; else goto _L5
_L5:
        if(i2 != 1) goto _L8; else goto _L7
_L7:
        ai[i2] = 1 + ai[i2];
_L15:
        l1++;
          goto _L9
_L8:
        if(i2 != 2) goto _L11; else goto _L10
_L10:
        if(!foundPatternCross(ai)) goto _L13; else goto _L12
_L12:
        AlignmentPattern alignmentpattern2 = handlePossibleCenter(ai, k1, l1);
        if(alignmentpattern2 == null) goto _L13; else goto _L14
_L14:
        AlignmentPattern alignmentpattern = alignmentpattern2;
_L16:
        return alignmentpattern;
_L13:
        ai[0] = ai[2];
        ai[1] = 1;
        ai[2] = 0;
        i2 = 1;
          goto _L15
_L11:
        i2++;
        ai[i2] = 1 + ai[i2];
          goto _L15
_L6:
        if(i2 == 1)
            i2++;
        ai[i2] = 1 + ai[i2];
          goto _L15
_L4:
        if(!foundPatternCross(ai))
            continue; /* Loop/switch isn't completed */
        AlignmentPattern alignmentpattern1 = handlePossibleCenter(ai, k1, k);
        if(alignmentpattern1 == null)
            continue; /* Loop/switch isn't completed */
        alignmentpattern = alignmentpattern1;
          goto _L16
        i1++;
          goto _L17
_L2:
        if(!possibleCenters.isEmpty())
            alignmentpattern = (AlignmentPattern)possibleCenters.elementAt(0);
        else
            throw ReaderException.getInstance();
          goto _L16
    }

    private final int crossCheckStateCount[] = new int[3];
    private final int height;
    private final BitMatrix image;
    private final float moduleSize;
    private final Vector possibleCenters = new Vector(5);
    private final int startX;
    private final int startY;
    private final int width;
}
