// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.detector.*;
import java.util.Hashtable;
import java.util.Vector;

final class MultiFinderPatternFinder extends FinderPatternFinder
{
    private static class ModuleSizeComparator
        implements Comparator
    {

        public int compare(Object obj, Object obj1)
        {
            float f = ((FinderPattern)obj1).getEstimatedModuleSize() - ((FinderPattern)obj).getEstimatedModuleSize();
            byte byte0;
            if((double)f < 0.0D)
                byte0 = -1;
            else
            if((double)f > 0.0D)
                byte0 = 1;
            else
                byte0 = 0;
            return byte0;
        }

        private ModuleSizeComparator()
        {
        }

    }


    MultiFinderPatternFinder(BitMatrix bitmatrix)
    {
        super(bitmatrix);
    }

    private FinderPattern[][] selectBestPatterns()
        throws ReaderException
    {
        Vector vector;
        int i;
        vector = getPossibleCenters();
        i = vector.size();
        if(i < 3)
            throw ReaderException.getInstance();
        if(i != 3) goto _L2; else goto _L1
_L1:
        FinderPattern afinderpattern1[][];
        afinderpattern1 = new FinderPattern[1][];
        FinderPattern afinderpattern3[] = new FinderPattern[3];
        afinderpattern3[0] = (FinderPattern)vector.elementAt(0);
        afinderpattern3[1] = (FinderPattern)vector.elementAt(1);
        afinderpattern3[2] = (FinderPattern)vector.elementAt(2);
        afinderpattern1[0] = afinderpattern3;
_L17:
        return afinderpattern1;
_L2:
        Vector vector1;
        int j;
        Collections.insertionSort(vector, new ModuleSizeComparator());
        vector1 = new Vector();
        j = 0;
_L4:
        FinderPattern finderpattern;
        int k = i - 2;
        if(j >= k)
            break MISSING_BLOCK_LABEL_509;
        finderpattern = (FinderPattern)vector.elementAt(j);
        if(finderpattern != null)
            break; /* Loop/switch isn't completed */
_L6:
        j++;
        if(true) goto _L4; else goto _L3
_L3:
        int j1 = j + 1;
_L9:
        int k1 = i - 1;
        if(j1 >= k1) goto _L6; else goto _L5
_L5:
        FinderPattern finderpattern1 = (FinderPattern)vector.elementAt(j1);
        if(finderpattern1 != null) goto _L8; else goto _L7
_L7:
        j1++;
          goto _L9
_L8:
        float f = (finderpattern.getEstimatedModuleSize() - finderpattern1.getEstimatedModuleSize()) / Math.min(finderpattern.getEstimatedModuleSize(), finderpattern1.getEstimatedModuleSize());
        if(Math.abs(finderpattern.getEstimatedModuleSize() - finderpattern1.getEstimatedModuleSize()) > 0.5F && f >= 0.05F) goto _L6; else goto _L10
_L10:
        int l1 = j1 + 1;
_L14:
        if(l1 >= i) goto _L7; else goto _L11
_L11:
        FinderPattern finderpattern2 = (FinderPattern)vector.elementAt(l1);
        if(finderpattern2 != null) goto _L13; else goto _L12
_L12:
        l1++;
          goto _L14
_L13:
        float f1 = (finderpattern1.getEstimatedModuleSize() - finderpattern2.getEstimatedModuleSize()) / Math.min(finderpattern1.getEstimatedModuleSize(), finderpattern2.getEstimatedModuleSize());
        if(Math.abs(finderpattern1.getEstimatedModuleSize() - finderpattern2.getEstimatedModuleSize()) > 0.5F && f1 >= 0.05F) goto _L7; else goto _L15
_L15:
        FinderPattern afinderpattern2[] = new FinderPattern[3];
        afinderpattern2[0] = finderpattern;
        afinderpattern2[1] = finderpattern1;
        afinderpattern2[2] = finderpattern2;
        ResultPoint.orderBestPatterns(afinderpattern2);
        FinderPatternInfo finderpatterninfo = new FinderPatternInfo(afinderpattern2);
        float f2 = ResultPoint.distance(finderpatterninfo.getTopLeft(), finderpatterninfo.getBottomLeft());
        float f3 = ResultPoint.distance(finderpatterninfo.getTopRight(), finderpatterninfo.getBottomLeft());
        float f4 = ResultPoint.distance(finderpatterninfo.getTopLeft(), finderpatterninfo.getTopRight());
        float f5 = (f2 + f4) / finderpattern.getEstimatedModuleSize() / 2.0F;
        if(f5 <= 180F && f5 >= 9F && Math.abs((f2 - f4) / Math.min(f2, f4)) < 0.1F)
        {
            float f6 = (float)Math.sqrt(f2 * f2 + f4 * f4);
            if(Math.abs((f3 - f6) / Math.min(f3, f6)) < 0.1F)
                vector1.addElement(afinderpattern2);
        }
          goto _L12
        if(!vector1.isEmpty())
        {
            FinderPattern afinderpattern[][] = new FinderPattern[vector1.size()][];
            int l = 0;
            do
            {
                int i1 = vector1.size();
                if(l >= i1)
                    break;
                afinderpattern[l] = (FinderPattern[])(FinderPattern[])vector1.elementAt(l);
                l++;
            } while(true);
            afinderpattern1 = afinderpattern;
        } else
        {
            throw ReaderException.getInstance();
        }
        if(true) goto _L17; else goto _L16
_L16:
    }

    public FinderPatternInfo[] findMulti(Hashtable hashtable)
        throws ReaderException
    {
        boolean flag;
        BitMatrix bitmatrix;
        int i;
        int j;
        int k;
        int ai[];
        if(hashtable != null && hashtable.containsKey(DecodeHintType.TRY_HARDER))
            flag = true;
        else
            flag = false;
        bitmatrix = getImage();
        i = bitmatrix.getHeight();
        j = bitmatrix.getWidth();
        k = (int)(3F * ((float)i / 228F));
        if(k < 3 || flag)
            k = 3;
        ai = new int[5];
        for(int l = k - 1; l < i; l += k)
        {
            ai[0] = 0;
            ai[1] = 0;
            ai[2] = 0;
            ai[3] = 0;
            ai[4] = 0;
            int i2 = 0;
            int j2 = 0;
            while(j2 < j) 
            {
                if(bitmatrix.get(j2, l))
                {
                    if((i2 & 1) == 1)
                        i2++;
                    ai[i2] = 1 + ai[i2];
                } else
                if((i2 & 1) == 0)
                {
                    if(i2 == 4)
                    {
                        if(foundPatternCross(ai))
                        {
                            if(!handlePossibleCenter(ai, l, j2))
                            {
                                while(++j2 < j && !bitmatrix.get(j2, l)) ;
                                j2--;
                            }
                            i2 = 0;
                            ai[0] = 0;
                            ai[1] = 0;
                            ai[2] = 0;
                            ai[3] = 0;
                            ai[4] = 0;
                        } else
                        {
                            ai[0] = ai[2];
                            ai[1] = ai[3];
                            ai[2] = ai[4];
                            ai[3] = 1;
                            ai[4] = 0;
                            i2 = 3;
                        }
                    } else
                    {
                        i2++;
                        ai[i2] = 1 + ai[i2];
                    }
                } else
                {
                    ai[i2] = 1 + ai[i2];
                }
                j2++;
            }
            if(foundPatternCross(ai))
                handlePossibleCenter(ai, l, j);
        }

        FinderPattern afinderpattern[][] = selectBestPatterns();
        Vector vector = new Vector();
        int i1 = 0;
        do
        {
            int j1 = afinderpattern.length;
            if(i1 >= j1)
                break;
            FinderPattern afinderpattern1[] = afinderpattern[i1];
            ResultPoint.orderBestPatterns(afinderpattern1);
            FinderPatternInfo finderpatterninfo = new FinderPatternInfo(afinderpattern1);
            vector.addElement(finderpatterninfo);
            i1++;
        } while(true);
        FinderPatternInfo afinderpatterninfo1[];
        if(vector.isEmpty())
        {
            afinderpatterninfo1 = EMPTY_RESULT_ARRAY;
        } else
        {
            FinderPatternInfo afinderpatterninfo[] = new FinderPatternInfo[vector.size()];
            int k1 = 0;
            do
            {
                int l1 = vector.size();
                if(k1 >= l1)
                    break;
                afinderpatterninfo[k1] = (FinderPatternInfo)vector.elementAt(k1);
                k1++;
            } while(true);
            afinderpatterninfo1 = afinderpatterninfo;
        }
        return afinderpatterninfo1;
    }

    private static final float DIFF_MODSIZE_CUTOFF = 0.5F;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05F;
    private static final FinderPatternInfo EMPTY_RESULT_ARRAY[] = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180F;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9F;

}
