// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.detector;

import com.google.zxing.ReaderException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.qrcode.detector:
//            AlignmentPatternFinder, FinderPatternFinder, FinderPatternInfo, FinderPattern, 
//            AlignmentPattern

public class Detector
{

    public Detector(BitMatrix bitmatrix)
    {
        image = bitmatrix;
    }

    private float calculateModuleSize(ResultPoint resultpoint, ResultPoint resultpoint1, ResultPoint resultpoint2)
        throws ReaderException
    {
        return (calculateModuleSizeOneWay(resultpoint, resultpoint1) + calculateModuleSizeOneWay(resultpoint, resultpoint2)) / 2.0F;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultpoint, ResultPoint resultpoint1)
    {
        float f = sizeOfBlackWhiteBlackRunBothWays((int)resultpoint.getX(), (int)resultpoint.getY(), (int)resultpoint1.getX(), (int)resultpoint1.getY());
        float f1 = sizeOfBlackWhiteBlackRunBothWays((int)resultpoint1.getX(), (int)resultpoint1.getY(), (int)resultpoint.getX(), (int)resultpoint.getY());
        float f2;
        if(Float.isNaN(f))
            f2 = f1;
        else
        if(Float.isNaN(f1))
            f2 = f;
        else
            f2 = (f + f1) / 14F;
        return f2;
    }

    private static int computeDimension(ResultPoint resultpoint, ResultPoint resultpoint1, ResultPoint resultpoint2, float f)
        throws ReaderException
    {
        int i = 7 + (round(ResultPoint.distance(resultpoint, resultpoint1) / f) + round(ResultPoint.distance(resultpoint, resultpoint2) / f) >> 1);
        i & 3;
        JVM INSTR tableswitch 0 3: default 64
    //                   0 67
    //                   1 64
    //                   2 73
    //                   3 79;
           goto _L1 _L2 _L1 _L3 _L4
_L1:
        return i;
_L2:
        i++;
        continue; /* Loop/switch isn't completed */
_L3:
        i--;
        if(true) goto _L1; else goto _L4
_L4:
        throw ReaderException.getInstance();
    }

    private AlignmentPattern findAlignmentInRegion(float f, int i, int j, float f1)
        throws ReaderException
    {
        int k = (int)(f1 * f);
        int l = Math.max(0, i - k);
        int i1 = Math.min(image.getWidth() - 1, i + k);
        if((float)(i1 - l) < 3F * f)
        {
            throw ReaderException.getInstance();
        } else
        {
            int j1 = Math.max(0, j - k);
            int k1 = Math.min(image.getHeight() - 1, j + k);
            return (new AlignmentPatternFinder(image, l, j1, i1 - l, k1 - j1, f)).find();
        }
    }

    private static int round(float f)
    {
        return (int)(0.5F + f);
    }

    private static BitMatrix sampleGrid(BitMatrix bitmatrix, ResultPoint resultpoint, ResultPoint resultpoint1, ResultPoint resultpoint2, ResultPoint resultpoint3, int i)
        throws ReaderException
    {
        float f = (float)i - 3.5F;
        float f1;
        float f2;
        float f3;
        float f4;
        if(resultpoint3 != null)
        {
            f1 = resultpoint3.getX();
            f2 = resultpoint3.getY();
            f3 = f - 3F;
            f4 = f3;
        } else
        {
            f1 = (resultpoint1.getX() - resultpoint.getX()) + resultpoint2.getX();
            f2 = (resultpoint1.getY() - resultpoint.getY()) + resultpoint2.getY();
            f3 = f;
            f4 = f;
        }
        return GridSampler.getInstance().sampleGrid(bitmatrix, i, 3.5F, 3.5F, f, 3.5F, f4, f3, 3.5F, f, resultpoint.getX(), resultpoint.getY(), resultpoint1.getX(), resultpoint1.getY(), f1, f2, resultpoint2.getX(), resultpoint2.getY());
    }

    private float sizeOfBlackWhiteBlackRun(int i, int j, int k, int l)
    {
        int i1;
        int j1;
        int k1;
        byte byte0;
        byte byte1;
        int l1;
        int i2;
        int j2;
        float f;
        boolean flag;
        int k3;
        int l3;
        if(Math.abs(l - j) > Math.abs(k - i))
            flag = true;
        else
            flag = false;
        if(flag)
        {
            int i4 = i;
            i = j;
            j = i4;
            int j4 = k;
            k = l;
            l = j4;
        }
        i1 = Math.abs(k - i);
        j1 = Math.abs(l - j);
        k1 = -i1 >> 1;
        if(j < l)
            byte0 = 1;
        else
            byte0 = -1;
        if(i < k)
            byte1 = 1;
        else
            byte1 = -1;
        l1 = 0;
        i2 = i;
        j2 = j;
_L5:
        if(i2 == k) goto _L2; else goto _L1
_L1:
        int i3;
        int j3;
        if(flag)
            i3 = j2;
        else
            i3 = i2;
        if(flag)
            j3 = i2;
        else
            j3 = j2;
        if(l1 == 1)
        {
            if(image.get(i3, j3))
                l1++;
        } else
        if(!image.get(i3, j3))
            l1++;
        if(l1 != 3) goto _L4; else goto _L3
_L3:
        k3 = i2 - i;
        l3 = j2 - j;
        f = (float)Math.sqrt(k3 * k3 + l3 * l3);
_L6:
        return f;
_L4:
        k1 += j1;
        if(k1 > 0)
        {
            j2 += byte0;
            k1 -= i1;
        }
        i2 += byte1;
          goto _L5
_L2:
        int k2 = k - i;
        int l2 = l - j;
        f = (float)Math.sqrt(k2 * k2 + l2 * l2);
          goto _L6
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int i, int j, int k, int l)
    {
        float f = sizeOfBlackWhiteBlackRun(i, j, k, l);
        int i1 = i - (k - i);
        int j1;
        if(i1 < 0)
            i1 = -1;
        else
        if(i1 >= image.getWidth())
            i1 = image.getWidth();
        j1 = j - (l - j);
        if(j1 < 0)
            j1 = -1;
        else
        if(j1 >= image.getHeight())
            j1 = image.getHeight();
        return (f + sizeOfBlackWhiteBlackRun(i, j, i1, j1)) - 1.0F;
    }

    public DetectorResult detect()
        throws ReaderException
    {
        return detect(null);
    }

    public DetectorResult detect(Hashtable hashtable)
        throws ReaderException
    {
        return processFinderPatternInfo((new FinderPatternFinder(image)).find(hashtable));
    }

    protected BitMatrix getImage()
    {
        return image;
    }

    protected DetectorResult processFinderPatternInfo(FinderPatternInfo finderpatterninfo)
        throws ReaderException
    {
        FinderPattern finderpattern;
        FinderPattern finderpattern1;
        FinderPattern finderpattern2;
        float f;
        int i;
        Version version;
        int j;
        AlignmentPattern alignmentpattern;
        finderpattern = finderpatterninfo.getTopLeft();
        finderpattern1 = finderpatterninfo.getTopRight();
        finderpattern2 = finderpatterninfo.getBottomLeft();
        f = calculateModuleSize(finderpattern, finderpattern1, finderpattern2);
        if(f < 1.0F)
            throw ReaderException.getInstance();
        i = computeDimension(finderpattern, finderpattern1, finderpattern2, f);
        version = Version.getProvisionalVersionForDimension(i);
        j = version.getDimensionForVersion() - 7;
        alignmentpattern = null;
        if(version.getAlignmentPatternCenters().length <= 0) goto _L2; else goto _L1
_L1:
        int k;
        int l;
        int i1;
        float f1 = (finderpattern1.getX() - finderpattern.getX()) + finderpattern2.getX();
        float f2 = (finderpattern1.getY() - finderpattern.getY()) + finderpattern2.getY();
        float f3 = 1.0F - 3F / (float)j;
        k = (int)(finderpattern.getX() + f3 * (f1 - finderpattern.getX()));
        l = (int)(finderpattern.getY() + f3 * (f2 - finderpattern.getY()));
        i1 = 4;
_L5:
        if(i1 > 16) goto _L2; else goto _L3
_L3:
        float f4 = i1;
        AlignmentPattern alignmentpattern1 = findAlignmentInRegion(f, k, l, f4);
        alignmentpattern = alignmentpattern1;
_L2:
        BitMatrix bitmatrix = sampleGrid(image, finderpattern, finderpattern1, finderpattern2, alignmentpattern, i);
        ResultPoint aresultpoint[];
        DetectorResult detectorresult;
        ReaderException readerexception;
        if(alignmentpattern == null)
        {
            aresultpoint = new ResultPoint[3];
            aresultpoint[0] = finderpattern2;
            aresultpoint[1] = finderpattern;
            aresultpoint[2] = finderpattern1;
        } else
        {
            aresultpoint = new ResultPoint[4];
            aresultpoint[0] = finderpattern2;
            aresultpoint[1] = finderpattern;
            aresultpoint[2] = finderpattern1;
            aresultpoint[3] = alignmentpattern;
        }
        detectorresult = new DetectorResult(bitmatrix, aresultpoint);
        return detectorresult;
        readerexception;
        i1 <<= 1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private final BitMatrix image;
}
