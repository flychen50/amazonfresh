// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import com.github.droidfu.cachefu.HttpResponseCache;
import java.io.*;
import org.apache.http.HttpResponse;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpResponse, BetterHttp

public class CachedHttpResponse
    implements BetterHttpResponse
{
    public static final class ResponseData
    {

        public byte[] getResponseBody()
        {
            return responseBody;
        }

        public int getStatusCode()
        {
            return statusCode;
        }

        private byte responseBody[];
        private int statusCode;



        public ResponseData(int i, byte abyte0[])
        {
            statusCode = i;
            responseBody = abyte0;
        }
    }


    public CachedHttpResponse(String s)
    {
        responseCache = BetterHttp.getResponseCache();
        cachedData = (ResponseData)responseCache.get(s);
    }

    public String getHeader(String s)
    {
        return null;
    }

    public InputStream getResponseBody()
        throws IOException
    {
        return new ByteArrayInputStream(cachedData.responseBody);
    }

    public byte[] getResponseBodyAsBytes()
        throws IOException
    {
        return cachedData.responseBody;
    }

    public String getResponseBodyAsString()
        throws IOException
    {
        return new String(cachedData.responseBody);
    }

    public int getStatusCode()
    {
        return cachedData.statusCode;
    }

    public HttpResponse unwrap()
    {
        return null;
    }

    private ResponseData cachedData;
    private HttpResponseCache responseCache;
}
