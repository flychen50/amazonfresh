// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;

// Referenced classes of package com.google.zxing.client.result:
//            ResultParser, GeoParsedResult

final class GeoResultParser extends ResultParser
{

    private GeoResultParser()
    {
    }

    public static GeoParsedResult parse(Result result)
    {
        String s = result.getText();
        if(s != null && (s.startsWith("geo:") || s.startsWith("GEO:"))) goto _L2; else goto _L1
_L1:
        GeoParsedResult geoparsedresult = null;
_L7:
        return geoparsedresult;
_L2:
        String s1;
        int j;
        int k;
        int i = s.indexOf('?', 4);
        if(i < 0)
            s1 = s.substring(4);
        else
            s1 = s.substring(4, i);
        j = s1.indexOf(',');
        if(j < 0)
        {
            geoparsedresult = null;
            continue; /* Loop/switch isn't completed */
        }
        k = s1.indexOf(',', j + 1);
        double d = Double.parseDouble(s1.substring(0, j));
        if(k >= 0) goto _L4; else goto _L3
_L3:
        double d4 = Double.parseDouble(s1.substring(j + 1));
        double d1;
        double d3;
        d1 = d4;
        d3 = 0.0D;
_L5:
        NumberFormatException numberformatexception;
        int l;
        double d2;
        String s2;
        if(s.startsWith("GEO:"))
            s2 = "geo:" + s.substring(4);
        else
            s2 = s;
        geoparsedresult = new GeoParsedResult(s2, d, d1, d3);
        continue; /* Loop/switch isn't completed */
_L4:
        l = j + 1;
        d1 = Double.parseDouble(s1.substring(l, k));
        d2 = Double.parseDouble(s1.substring(k + 1));
        d3 = d2;
          goto _L5
        numberformatexception;
        geoparsedresult = null;
        if(true) goto _L7; else goto _L6
_L6:
    }
}
