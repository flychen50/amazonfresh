// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.datamatrix.detector;

import com.google.zxing.ReaderException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.*;
import com.google.zxing.common.detector.MonochromeRectangleDetector;
import java.util.*;

public final class Detector
{
    private static class ResultPointsAndTransitionsComparator
        implements Comparator
    {

        public int compare(Object obj, Object obj1)
        {
            return ((ResultPointsAndTransitions)obj).getTransitions() - ((ResultPointsAndTransitions)obj1).getTransitions();
        }

        private ResultPointsAndTransitionsComparator()
        {
        }

    }

    private static class ResultPointsAndTransitions
    {

        public ResultPoint getFrom()
        {
            return from;
        }

        public ResultPoint getTo()
        {
            return to;
        }

        public int getTransitions()
        {
            return transitions;
        }

        public String toString()
        {
            return from + "/" + to + '/' + transitions;
        }

        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint resultpoint, ResultPoint resultpoint1, int i)
        {
            from = resultpoint;
            to = resultpoint1;
            transitions = i;
        }

    }


    public Detector(BitMatrix bitmatrix)
    {
        image = bitmatrix;
        rectangleDetector = new MonochromeRectangleDetector(bitmatrix);
    }

    private static void increment(Hashtable hashtable, ResultPoint resultpoint)
    {
        Integer integer = (Integer)hashtable.get(resultpoint);
        Integer integer1;
        if(integer == null)
            integer1 = INTEGERS[1];
        else
            integer1 = INTEGERS[1 + integer.intValue()];
        hashtable.put(resultpoint, integer1);
    }

    private static BitMatrix sampleGrid(BitMatrix bitmatrix, ResultPoint resultpoint, ResultPoint resultpoint1, ResultPoint resultpoint2, int i)
        throws ReaderException
    {
        float f = (resultpoint2.getX() - resultpoint1.getX()) + resultpoint.getX();
        float f1 = (resultpoint2.getY() - resultpoint1.getY()) + resultpoint.getY();
        return GridSampler.getInstance().sampleGrid(bitmatrix, i, 0.0F, 0.0F, i, 0.0F, i, i, 0.0F, i, resultpoint.getX(), resultpoint.getY(), f, f1, resultpoint2.getX(), resultpoint2.getY(), resultpoint1.getX(), resultpoint1.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultpoint, ResultPoint resultpoint1)
        throws ReaderException
    {
        int i = (int)resultpoint.getX();
        int j = (int)resultpoint.getY();
        int k = (int)resultpoint1.getX();
        int l = (int)resultpoint1.getY();
        boolean flag;
        int i1;
        int j1;
        int k1;
        int l1;
        int i2;
        int j2;
        BitMatrix bitmatrix;
        int k2;
        int l2;
        boolean flag1;
        int i3;
        int j3;
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
            l1 = 1;
        else
            l1 = -1;
        if(i < k)
            i2 = 1;
        else
            i2 = -1;
        j2 = 0;
        bitmatrix = image;
        if(flag)
            k2 = j;
        else
            k2 = i;
        if(flag)
            l2 = i;
        else
            l2 = j;
        flag1 = bitmatrix.get(k2, l2);
        i3 = i;
        j3 = j;
        while(i3 != k) 
        {
            BitMatrix bitmatrix1 = image;
            int k3;
            int l3;
            boolean flag2;
            if(flag)
                k3 = j3;
            else
                k3 = i3;
            if(flag)
                l3 = i3;
            else
                l3 = j3;
            flag2 = bitmatrix1.get(k3, l3);
            if(flag2 != flag1)
            {
                j2++;
                flag1 = flag2;
            }
            k1 += j1;
            if(k1 > 0)
            {
                j3 += l1;
                k1 -= i1;
            }
            i3 += i2;
        }
        ResultPointsAndTransitions resultpointsandtransitions = new ResultPointsAndTransitions(resultpoint, resultpoint1, j2);
        return resultpointsandtransitions;
    }

    public DetectorResult detect()
        throws ReaderException
    {
        ResultPoint aresultpoint[] = rectangleDetector.detect();
        ResultPoint resultpoint = aresultpoint[0];
        ResultPoint resultpoint1 = aresultpoint[1];
        ResultPoint resultpoint2 = aresultpoint[2];
        ResultPoint resultpoint3 = aresultpoint[3];
        Vector vector = new Vector(4);
        vector.addElement(transitionsBetween(resultpoint, resultpoint1));
        vector.addElement(transitionsBetween(resultpoint, resultpoint2));
        vector.addElement(transitionsBetween(resultpoint1, resultpoint3));
        vector.addElement(transitionsBetween(resultpoint2, resultpoint3));
        Collections.insertionSort(vector, new ResultPointsAndTransitionsComparator());
        ResultPointsAndTransitions resultpointsandtransitions = (ResultPointsAndTransitions)vector.elementAt(0);
        ResultPointsAndTransitions resultpointsandtransitions1 = (ResultPointsAndTransitions)vector.elementAt(1);
        Hashtable hashtable = new Hashtable();
        increment(hashtable, resultpointsandtransitions.getFrom());
        increment(hashtable, resultpointsandtransitions.getTo());
        increment(hashtable, resultpointsandtransitions1.getFrom());
        increment(hashtable, resultpointsandtransitions1.getTo());
        ResultPoint resultpoint4 = null;
        ResultPoint resultpoint5 = null;
        ResultPoint resultpoint6 = null;
        for(Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();)
        {
            ResultPoint resultpoint11 = (ResultPoint)enumeration.nextElement();
            if(((Integer)hashtable.get(resultpoint11)).intValue() == 2)
                resultpoint5 = resultpoint11;
            else
            if(resultpoint4 == null)
                resultpoint4 = resultpoint11;
            else
                resultpoint6 = resultpoint11;
        }

        if(resultpoint4 == null || resultpoint5 == null || resultpoint6 == null)
            throw ReaderException.getInstance();
        ResultPoint aresultpoint1[] = new ResultPoint[3];
        aresultpoint1[0] = resultpoint4;
        aresultpoint1[1] = resultpoint5;
        aresultpoint1[2] = resultpoint6;
        ResultPoint.orderBestPatterns(aresultpoint1);
        ResultPoint resultpoint7 = aresultpoint1[0];
        ResultPoint resultpoint8 = aresultpoint1[1];
        ResultPoint resultpoint9 = aresultpoint1[2];
        ResultPoint resultpoint10;
        int i;
        int j;
        BitMatrix bitmatrix;
        ResultPoint aresultpoint2[];
        DetectorResult detectorresult;
        if(!hashtable.containsKey(resultpoint))
            resultpoint10 = resultpoint;
        else
        if(!hashtable.containsKey(resultpoint1))
            resultpoint10 = resultpoint1;
        else
        if(!hashtable.containsKey(resultpoint2))
            resultpoint10 = resultpoint2;
        else
            resultpoint10 = resultpoint3;
        i = Math.min(transitionsBetween(resultpoint9, resultpoint10).getTransitions(), transitionsBetween(resultpoint7, resultpoint10).getTransitions());
        if((i & 1) == 1)
            i++;
        j = i + 2;
        bitmatrix = sampleGrid(image, resultpoint9, resultpoint8, resultpoint7, j);
        aresultpoint2 = new ResultPoint[4];
        aresultpoint2[0] = resultpoint;
        aresultpoint2[1] = resultpoint1;
        aresultpoint2[2] = resultpoint2;
        aresultpoint2[3] = resultpoint3;
        detectorresult = new DetectorResult(bitmatrix, aresultpoint2);
        return detectorresult;
    }

    private static final Integer INTEGERS[];
    private final BitMatrix image;
    private final MonochromeRectangleDetector rectangleDetector;

    static 
    {
        Integer ainteger[] = new Integer[5];
        ainteger[0] = new Integer(0);
        ainteger[1] = new Integer(1);
        ainteger[2] = new Integer(2);
        ainteger[3] = new Integer(3);
        ainteger[4] = new Integer(4);
        INTEGERS = ainteger;
    }
}
