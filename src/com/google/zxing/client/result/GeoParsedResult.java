// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;


// Referenced classes of package com.google.zxing.client.result:
//            ParsedResult, ParsedResultType

public final class GeoParsedResult extends ParsedResult
{

    GeoParsedResult(String s, double d, double d1, double d2)
    {
        super(ParsedResultType.GEO);
        geoURI = s;
        latitude = d;
        longitude = d1;
        altitude = d2;
    }

    public double getAltitude()
    {
        return altitude;
    }

    public String getDisplayResult()
    {
        StringBuffer stringbuffer = new StringBuffer(50);
        stringbuffer.append(latitude);
        stringbuffer.append(", ");
        stringbuffer.append(longitude);
        if(altitude > 0.0D)
        {
            stringbuffer.append(", ");
            stringbuffer.append(altitude);
            stringbuffer.append('m');
        }
        return stringbuffer.toString();
    }

    public String getGeoURI()
    {
        return geoURI;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    private final double altitude;
    private final String geoURI;
    private final double latitude;
    private final double longitude;
}
