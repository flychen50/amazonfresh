// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpResponse

public class BetterHttpResponseImpl
    implements BetterHttpResponse
{

    public BetterHttpResponseImpl(HttpResponse httpresponse)
        throws IOException
    {
        response = httpresponse;
        HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity != null)
            entity = new BufferedHttpEntity(httpentity);
    }

    public String getHeader(String s)
    {
        String s1;
        if(!response.containsHeader(s))
            s1 = null;
        else
            s1 = response.getFirstHeader(s).getValue();
        return s1;
    }

    public InputStream getResponseBody()
        throws IOException
    {
        return entity.getContent();
    }

    public byte[] getResponseBodyAsBytes()
        throws IOException
    {
        return EntityUtils.toByteArray(entity);
    }

    public String getResponseBodyAsString()
        throws IOException
    {
        return EntityUtils.toString(entity);
    }

    public int getStatusCode()
    {
        return response.getStatusLine().getStatusCode();
    }

    public HttpResponse unwrap()
    {
        return response;
    }

    private HttpEntity entity;
    private HttpResponse response;
}
