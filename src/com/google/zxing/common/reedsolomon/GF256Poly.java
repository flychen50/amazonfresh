// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.common.reedsolomon;


// Referenced classes of package com.google.zxing.common.reedsolomon:
//            GF256

final class GF256Poly
{

    GF256Poly(GF256 gf256, int ai[])
    {
        if(ai == null || ai.length == 0)
            throw new IllegalArgumentException();
        field = gf256;
        int i = ai.length;
        if(i > 1 && ai[0] == 0)
        {
            int j;
            for(j = 1; j < i && ai[j] == 0; j++);
            if(j == i)
            {
                coefficients = gf256.getZero().coefficients;
            } else
            {
                coefficients = new int[i - j];
                System.arraycopy(ai, j, coefficients, 0, coefficients.length);
            }
        } else
        {
            coefficients = ai;
        }
    }

    GF256Poly addOrSubtract(GF256Poly gf256poly)
    {
        if(!field.equals(gf256poly.field))
            throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
        GF256Poly gf256poly1;
        if(isZero())
            gf256poly1 = gf256poly;
        else
        if(gf256poly.isZero())
        {
            gf256poly1 = this;
        } else
        {
            int ai[] = coefficients;
            int ai1[] = gf256poly.coefficients;
            if(ai.length > ai1.length)
            {
                int ai3[] = ai;
                ai = ai1;
                ai1 = ai3;
            }
            int ai2[] = new int[ai1.length];
            int i = ai1.length - ai.length;
            System.arraycopy(ai1, 0, ai2, 0, i);
            for(int j = i; j < ai1.length; j++)
                ai2[j] = GF256.addOrSubtract(ai[j - i], ai1[j]);

            gf256poly1 = new GF256Poly(field, ai2);
        }
        return gf256poly1;
    }

    GF256Poly[] divide(GF256Poly gf256poly)
    {
        if(!field.equals(gf256poly.field))
            throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
        if(gf256poly.isZero())
            throw new IllegalArgumentException("Divide by 0");
        GF256Poly gf256poly1 = field.getZero();
        GF256Poly gf256poly2 = this;
        int i = gf256poly.getCoefficient(gf256poly.getDegree());
        int j = field.inverse(i);
        GF256Poly gf256poly3;
        for(; gf256poly2.getDegree() >= gf256poly.getDegree() && !gf256poly2.isZero(); gf256poly2 = gf256poly2.addOrSubtract(gf256poly3))
        {
            int k = gf256poly2.getDegree() - gf256poly.getDegree();
            int l = field.multiply(gf256poly2.getCoefficient(gf256poly2.getDegree()), j);
            gf256poly3 = gf256poly.multiplyByMonomial(k, l);
            gf256poly1 = gf256poly1.addOrSubtract(field.buildMonomial(k, l));
        }

        GF256Poly agf256poly[] = new GF256Poly[2];
        agf256poly[0] = gf256poly1;
        agf256poly[1] = gf256poly2;
        return agf256poly;
    }

    int evaluateAt(int i)
    {
        int i1;
        if(i == 0)
        {
            i1 = getCoefficient(0);
        } else
        {
            int j = coefficients.length;
            if(i == 1)
            {
                int j1 = 0;
                for(int k1 = 0; k1 < j; k1++)
                    j1 = GF256.addOrSubtract(j1, coefficients[k1]);

                i1 = j1;
            } else
            {
                int k = coefficients[0];
                for(int l = 1; l < j; l++)
                    k = GF256.addOrSubtract(field.multiply(i, k), coefficients[l]);

                i1 = k;
            }
        }
        return i1;
    }

    int getCoefficient(int i)
    {
        return coefficients[coefficients.length - 1 - i];
    }

    int[] getCoefficients()
    {
        return coefficients;
    }

    int getDegree()
    {
        return coefficients.length - 1;
    }

    boolean isZero()
    {
        boolean flag;
        if(coefficients[0] == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    GF256Poly multiply(int i)
    {
        GF256Poly gf256poly;
        if(i == 0)
            gf256poly = field.getZero();
        else
        if(i == 1)
        {
            gf256poly = this;
        } else
        {
            int j = coefficients.length;
            int ai[] = new int[j];
            for(int k = 0; k < j; k++)
                ai[k] = field.multiply(coefficients[k], i);

            gf256poly = new GF256Poly(field, ai);
        }
        return gf256poly;
    }

    GF256Poly multiply(GF256Poly gf256poly)
    {
        if(!field.equals(gf256poly.field))
            throw new IllegalArgumentException("GF256Polys do not have same GF256 field");
        GF256Poly gf256poly1;
        if(isZero() || gf256poly.isZero())
        {
            gf256poly1 = field.getZero();
        } else
        {
            int ai[] = coefficients;
            int i = ai.length;
            int ai1[] = gf256poly.coefficients;
            int j = ai1.length;
            int ai2[] = new int[(i + j) - 1];
            for(int k = 0; k < i; k++)
            {
                int l = ai[k];
                for(int i1 = 0; i1 < j; i1++)
                    ai2[k + i1] = GF256.addOrSubtract(ai2[k + i1], field.multiply(l, ai1[i1]));

            }

            gf256poly1 = new GF256Poly(field, ai2);
        }
        return gf256poly1;
    }

    GF256Poly multiplyByMonomial(int i, int j)
    {
        if(i < 0)
            throw new IllegalArgumentException();
        GF256Poly gf256poly;
        if(j == 0)
        {
            gf256poly = field.getZero();
        } else
        {
            int k = coefficients.length;
            int ai[] = new int[k + i];
            for(int l = 0; l < k; l++)
                ai[l] = field.multiply(coefficients[l], j);

            gf256poly = new GF256Poly(field, ai);
        }
        return gf256poly;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer(8 * getDegree());
        int i = getDegree();
        do
        {
            if(i >= 0)
            {
                int j = getCoefficient(i);
                if(j != 0)
                {
                    if(j < 0)
                    {
                        stringbuffer.append(" - ");
                        j = -j;
                    } else
                    if(stringbuffer.length() > 0)
                        stringbuffer.append(" + ");
                    if(i == 0 || j != 1)
                    {
                        int k = field.log(j);
                        if(k == 0)
                            stringbuffer.append('1');
                        else
                        if(k == 1)
                        {
                            stringbuffer.append('a');
                        } else
                        {
                            stringbuffer.append("a^");
                            stringbuffer.append(k);
                        }
                    }
                    if(i != 0)
                        if(i == 1)
                        {
                            stringbuffer.append('x');
                        } else
                        {
                            stringbuffer.append("x^");
                            stringbuffer.append(i);
                        }
                }
                i--;
                continue;
            }
            return stringbuffer.toString();
        } while(true);
    }

    private final int coefficients[];
    private final GF256 field;
}
