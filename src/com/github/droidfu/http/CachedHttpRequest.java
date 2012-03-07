// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import java.net.ConnectException;
import org.apache.http.client.methods.HttpUriRequest;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpRequest, CachedHttpResponse, BetterHttpResponse

public class CachedHttpRequest
    implements BetterHttpRequest
{

    public CachedHttpRequest(String s)
    {
        url = s;
    }

    public transient BetterHttpRequest expecting(Integer ainteger[])
    {
        return this;
    }

    public String getRequestUrl()
    {
        return url;
    }

    public BetterHttpRequest retries(int i)
    {
        return this;
    }

    public BetterHttpResponse send()
        throws ConnectException
    {
        return new CachedHttpResponse(url);
    }

    public HttpUriRequest unwrap()
    {
        return null;
    }

    public BetterHttpRequest withTimeout(int i)
    {
        return this;
    }

    private String url;
}
