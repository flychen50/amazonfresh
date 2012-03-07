// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.qrcode.detector;

import com.google.zxing.*;
import com.google.zxing.common.*;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.google.zxing.qrcode.detector:
//            FinderPattern, FinderPatternInfo

public class FinderPatternFinder
{
    private static class CenterComparator
        implements Comparator
    {

        public int compare(Object obj, Object obj1)
        {
            return ((FinderPattern)obj1).getCount() - ((FinderPattern)obj).getCount();
        }

        private CenterComparator()
        {
        }

    }


    public FinderPatternFinder(BitMatrix bitmatrix)
    {
        image = bitmatrix;
    }

    private static float centerFromEnd(int ai[], int i)
    {
        return (float)(i - ai[4] - ai[3]) - (float)ai[2] / 2.0F;
    }

    private float crossCheckHorizontal(int i, int j, int k, int l)
    {
        BitMatrix bitmatrix = image;
        int i1 = bitmatrix.getWidth();
        int ai[] = getCrossCheckStateCount();
        int j1;
        for(j1 = i; j1 >= 0 && bitmatrix.get(j1, j); j1--)
            ai[2] = 1 + ai[2];

        float f;
        if(j1 < 0)
        {
            f = (0.0F / 0.0F);
        } else
        {
            for(; j1 >= 0 && !bitmatrix.get(j1, j) && ai[1] <= k; j1--)
                ai[1] = 1 + ai[1];

            if(j1 < 0 || ai[1] > k)
            {
                f = (0.0F / 0.0F);
            } else
            {
                for(; j1 >= 0 && bitmatrix.get(j1, j) && ai[0] <= k; j1--)
                    ai[0] = 1 + ai[0];

                if(ai[0] > k)
                {
                    f = (0.0F / 0.0F);
                } else
                {
                    int k1;
                    for(k1 = i + 1; k1 < i1 && bitmatrix.get(k1, j); k1++)
                        ai[2] = 1 + ai[2];

                    if(k1 == i1)
                    {
                        f = (0.0F / 0.0F);
                    } else
                    {
                        for(; k1 < i1 && !bitmatrix.get(k1, j) && ai[3] < k; k1++)
                            ai[3] = 1 + ai[3];

                        if(k1 == i1 || ai[3] >= k)
                        {
                            f = (0.0F / 0.0F);
                        } else
                        {
                            for(; k1 < i1 && bitmatrix.get(k1, j) && ai[4] < k; k1++)
                                ai[4] = 1 + ai[4];

                            if(ai[4] >= k)
                                f = (0.0F / 0.0F);
                            else
                            if(5 * Math.abs((ai[0] + ai[1] + ai[2] + ai[3] + ai[4]) - l) >= l)
                                f = (0.0F / 0.0F);
                            else
                            if(foundPatternCross(ai))
                                f = centerFromEnd(ai, k1);
                            else
                                f = (0.0F / 0.0F);
                        }
                    }
                }
            }
        }
        return f;
    }

    private float crossCheckVertical(int i, int j, int k, int l)
    {
        BitMatrix bitmatrix = image;
        int i1 = bitmatrix.getHeight();
        int ai[] = getCrossCheckStateCount();
        int j1;
        for(j1 = i; j1 >= 0 && bitmatrix.get(j, j1); j1--)
            ai[2] = 1 + ai[2];

        float f;
        if(j1 < 0)
        {
            f = (0.0F / 0.0F);
        } else
        {
            for(; j1 >= 0 && !bitmatrix.get(j, j1) && ai[1] <= k; j1--)
                ai[1] = 1 + ai[1];

            if(j1 < 0 || ai[1] > k)
            {
                f = (0.0F / 0.0F);
            } else
            {
                for(; j1 >= 0 && bitmatrix.get(j, j1) && ai[0] <= k; j1--)
                    ai[0] = 1 + ai[0];

                if(ai[0] > k)
                {
                    f = (0.0F / 0.0F);
                } else
                {
                    int k1;
                    for(k1 = i + 1; k1 < i1 && bitmatrix.get(j, k1); k1++)
                        ai[2] = 1 + ai[2];

                    if(k1 == i1)
                    {
                        f = (0.0F / 0.0F);
                    } else
                    {
                        for(; k1 < i1 && !bitmatrix.get(j, k1) && ai[3] < k; k1++)
                            ai[3] = 1 + ai[3];

                        if(k1 == i1 || ai[3] >= k)
                        {
                            f = (0.0F / 0.0F);
                        } else
                        {
                            for(; k1 < i1 && bitmatrix.get(j, k1) && ai[4] < k; k1++)
                                ai[4] = 1 + ai[4];

                            if(ai[4] >= k)
                                f = (0.0F / 0.0F);
                            else
                            if(5 * Math.abs((ai[0] + ai[1] + ai[2] + ai[3] + ai[4]) - l) >= l)
                                f = (0.0F / 0.0F);
                            else
                            if(foundPatternCross(ai))
                                f = centerFromEnd(ai, k1);
                            else
                                f = (0.0F / 0.0F);
                        }
                    }
                }
            }
        }
        return f;
    }

    private int findRowSkip()
    {
        int i = possibleCenters.size();
        if(i > 1) goto _L2; else goto _L1
_L1:
        int k = 0;
_L4:
        return k;
_L2:
label0:
        {
            FinderPattern finderpattern = null;
            int j = 0;
            FinderPattern finderpattern1;
            do
            {
                if(j >= i)
                    break label0;
                finderpattern1 = (FinderPattern)possibleCenters.elementAt(j);
                if(finderpattern1.getCount() >= 2)
                {
                    if(finderpattern != null)
                        break;
                    finderpattern = finderpattern1;
                }
                j++;
            } while(true);
            hasSkipped = true;
            k = (int)(Math.abs(finderpattern.getX() - finderpattern1.getX()) - Math.abs(finderpattern.getY() - finderpattern1.getY())) / 2;
            continue; /* Loop/switch isn't completed */
        }
        k = 0;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected static boolean foundPatternCross(int ai[])
    {
        int i;
        int j;
        i = 0;
        j = 0;
_L3:
        int i1;
        if(j >= 5)
            break MISSING_BLOCK_LABEL_36;
        i1 = ai[j];
        if(i1 != 0) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        i += i1;
        j++;
          goto _L3
        if(i < 7)
        {
            flag = false;
        } else
        {
            int k = (i << 8) / 7;
            int l = k / 2;
            if(Math.abs(k - (ai[0] << 8)) < l && Math.abs(k - (ai[1] << 8)) < l && Math.abs(k * 3 - (ai[2] << 8)) < l * 3 && Math.abs(k - (ai[3] << 8)) < l && Math.abs(k - (ai[4] << 8)) < l)
                flag = true;
            else
                flag = false;
        }
          goto _L4
    }

    private int[] getCrossCheckStateCount()
    {
        crossCheckStateCount[0] = 0;
        crossCheckStateCount[1] = 0;
        crossCheckStateCount[2] = 0;
        crossCheckStateCount[3] = 0;
        crossCheckStateCount[4] = 0;
        return crossCheckStateCount;
    }

    private boolean haveMultiplyConfirmedCenters()
    {
        int i = 0;
        float f = 0.0F;
        int j = possibleCenters.size();
        for(int k = 0; k < j; k++)
        {
            FinderPattern finderpattern = (FinderPattern)possibleCenters.elementAt(k);
            if(finderpattern.getCount() >= 2)
            {
                i++;
                f += finderpattern.getEstimatedModuleSize();
            }
        }

        boolean flag;
        if(i < 3)
        {
            flag = false;
        } else
        {
            float f1 = f / (float)j;
            float f2 = 0.0F;
            for(int l = 0; l < j; l++)
                f2 += Math.abs(((FinderPattern)possibleCenters.elementAt(l)).getEstimatedModuleSize() - f1);

            if(f2 <= 0.05F * f)
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    private FinderPattern[] selectBestPatterns()
        throws ReaderException
    {
        int i = possibleCenters.size();
        if(i < 3)
            throw ReaderException.getInstance();
        if(i > 3)
        {
            float f = 0.0F;
            for(int j = 0; j < i; j++)
                f += ((FinderPattern)possibleCenters.elementAt(j)).getEstimatedModuleSize();

            float f1 = f / (float)i;
            for(int k = 0; k < possibleCenters.size() && possibleCenters.size() > 3; k++)
                if(Math.abs(((FinderPattern)possibleCenters.elementAt(k)).getEstimatedModuleSize() - f1) > 0.2F * f1)
                {
                    possibleCenters.removeElementAt(k);
                    k--;
                }

        }
        if(possibleCenters.size() > 3)
        {
            Collections.insertionSort(possibleCenters, new CenterComparator());
            possibleCenters.setSize(3);
        }
        FinderPattern afinderpattern[] = new FinderPattern[3];
        afinderpattern[0] = (FinderPattern)possibleCenters.elementAt(0);
        afinderpattern[1] = (FinderPattern)possibleCenters.elementAt(1);
        afinderpattern[2] = (FinderPattern)possibleCenters.elementAt(2);
        return afinderpattern;
    }

    FinderPatternInfo find(Hashtable hashtable)
        throws ReaderException
    {
        boolean flag;
        int i;
        int j;
        int k;
        boolean flag1;
        int ai[];
        if(hashtable != null && hashtable.containsKey(DecodeHintType.TRY_HARDER))
            flag = true;
        else
            flag = false;
        i = image.getHeight();
        j = image.getWidth();
        k = (i * 3) / 228;
        if(k < 3 || flag)
            k = 3;
        flag1 = false;
        ai = new int[5];
        for(int l = k - 1; l < i && !flag1; l += k)
        {
            ai[0] = 0;
            ai[1] = 0;
            ai[2] = 0;
            ai[3] = 0;
            ai[4] = 0;
            int i1 = 0;
            int j1 = 0;
            while(j1 < j) 
            {
                if(image.get(j1, l))
                {
                    if((i1 & 1) == 1)
                        i1++;
                    ai[i1] = 1 + ai[i1];
                } else
                if((i1 & 1) == 0)
                {
                    if(i1 == 4)
                    {
                        if(foundPatternCross(ai))
                        {
                            if(handlePossibleCenter(ai, l, j1))
                            {
                                k = 2;
                                if(hasSkipped)
                                {
                                    flag1 = haveMultiplyConfirmedCenters();
                                } else
                                {
                                    int k1 = findRowSkip();
                                    if(k1 > ai[2])
                                    {
                                        l += k1 - ai[2] - k;
                                        j1 = j - 1;
                                    }
                                }
                            } else
                            {
                                while(++j1 < j && !image.get(j1, l)) ;
                                j1--;
                            }
                            i1 = 0;
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
                            i1 = 3;
                        }
                    } else
                    {
                        i1++;
                        ai[i1] = 1 + ai[i1];
                    }
                } else
                {
                    ai[i1] = 1 + ai[i1];
                }
                j1++;
            }
            if(!foundPatternCross(ai) || !handlePossibleCenter(ai, l, j))
                continue;
            k = ai[0];
            if(hasSkipped)
                flag1 = haveMultiplyConfirmedCenters();
        }

        FinderPattern afinderpattern[] = selectBestPatterns();
        ResultPoint.orderBestPatterns(afinderpattern);
        return new FinderPatternInfo(afinderpattern);
    }

    protected BitMatrix getImage()
    {
        return image;
    }

    protected Vector getPossibleCenters()
    {
        return possibleCenters;
    }

    protected boolean handlePossibleCenter(int ai[], int i, int j)
        throws ReaderException
    {
        float f1;
        float f2;
        float f3;
        boolean flag1;
        int l;
        int i1;
        int k = ai[0] + ai[1] + ai[2] + ai[3] + ai[4];
        float f = centerFromEnd(ai, j);
        f1 = crossCheckVertical(i, (int)f, ai[2], k);
        if(Float.isNaN(f1))
            break MISSING_BLOCK_LABEL_179;
        f2 = crossCheckHorizontal((int)f, (int)f1, ai[2], k);
        if(Float.isNaN(f2))
            break MISSING_BLOCK_LABEL_179;
        f3 = (float)k / 7F;
        flag1 = false;
        l = possibleCenters.size();
        i1 = 0;
_L5:
        if(i1 >= l) goto _L2; else goto _L1
_L1:
        FinderPattern finderpattern = (FinderPattern)possibleCenters.elementAt(i1);
        if(!finderpattern.aboutEquals(f3, f1, f2)) goto _L4; else goto _L3
_L3:
        finderpattern.incrementCount();
        flag1 = true;
_L2:
        boolean flag;
        if(!flag1)
            possibleCenters.addElement(new FinderPattern(f2, f1, f3));
        flag = true;
_L6:
        return flag;
_L4:
        i1++;
          goto _L5
        flag = false;
          goto _L6
    }

    private static final int CENTER_QUORUM = 2;
    private static final int INTEGER_MATH_SHIFT = 8;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int crossCheckStateCount[] = new int[5];
    private boolean hasSkipped;
    private final BitMatrix image;
    private final Vector possibleCenters = new Vector();
}
