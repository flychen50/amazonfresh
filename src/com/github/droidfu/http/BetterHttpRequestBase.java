// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import android.util.Log;
import com.github.droidfu.cachefu.HttpResponseCache;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpRequest, BetterHttpRequestRetryHandler, BetterHttpResponseImpl, BetterHttp, 
//            BetterHttpResponse

public abstract class BetterHttpRequestBase
    implements BetterHttpRequest, ResponseHandler
{

    BetterHttpRequestBase(AbstractHttpClient abstracthttpclient)
    {
        expectedStatusCodes = new ArrayList();
        maxRetries = 5;
        httpClient = abstracthttpclient;
    }

    private boolean retryRequest(BetterHttpRequestRetryHandler betterhttprequestretryhandler, IOException ioexception, HttpContext httpcontext)
    {
        Log.e("BetterHttp", "Intercepting exception that wasn't handled by HttpClient");
        executionCount = Math.max(executionCount, betterhttprequestretryhandler.getTimesRetried());
        int i = 1 + executionCount;
        executionCount = i;
        return betterhttprequestretryhandler.retryRequest(ioexception, i, httpcontext);
    }

    public volatile transient BetterHttpRequest expecting(Integer ainteger[])
    {
        return expecting(ainteger);
    }

    public transient BetterHttpRequestBase expecting(Integer ainteger[])
    {
        expectedStatusCodes = Arrays.asList(ainteger);
        return this;
    }

    public String getRequestUrl()
    {
        return request.getURI().toString();
    }

    public BetterHttpResponse handleResponse(HttpResponse httpresponse)
        throws IOException
    {
        int i = httpresponse.getStatusLine().getStatusCode();
        if(expectedStatusCodes != null && !expectedStatusCodes.isEmpty() && !expectedStatusCodes.contains(Integer.valueOf(i)))
            throw new HttpResponseException(i, (new StringBuilder("Unexpected status code: ")).append(i).toString());
        BetterHttpResponseImpl betterhttpresponseimpl = new BetterHttpResponseImpl(httpresponse);
        HttpResponseCache httpresponsecache = BetterHttp.getResponseCache();
        if(httpresponsecache != null)
        {
            CachedHttpResponse.ResponseData responsedata = new CachedHttpResponse.ResponseData(i, betterhttpresponseimpl.getResponseBodyAsBytes());
            httpresponsecache.put(getRequestUrl(), responsedata);
        }
        return betterhttpresponseimpl;
    }

    public volatile Object handleResponse(HttpResponse httpresponse)
        throws ClientProtocolException, IOException
    {
        return handleResponse(httpresponse);
    }

    public volatile BetterHttpRequest retries(int i)
    {
        return retries(i);
    }

    public BetterHttpRequestBase retries(int i)
    {
        if(i < 0)
            maxRetries = 0;
        else
        if(i > 5)
            maxRetries = 5;
        else
            maxRetries = i;
        return this;
    }

    public BetterHttpResponse send()
        throws ConnectException
    {
        BetterHttpRequestRetryHandler betterhttprequestretryhandler;
        BasicHttpContext basichttpcontext;
        boolean flag;
        Object obj;
        betterhttprequestretryhandler = new BetterHttpRequestRetryHandler(maxRetries);
        httpClient.setHttpRequestRetryHandler(betterhttprequestretryhandler);
        basichttpcontext = new BasicHttpContext();
        flag = true;
        obj = null;
_L4:
        if(!flag)
        {
            ConnectException connectexception = new ConnectException();
            connectexception.initCause(((Throwable) (obj)));
            throw connectexception;
        }
        BetterHttpResponse betterhttpresponse = (BetterHttpResponse)httpClient.execute(request, this, basichttpcontext);
        if(oldTimeout != BetterHttp.getSocketTimeout())
            BetterHttp.setSocketTimeout(oldTimeout);
        return betterhttpresponse;
        IOException ioexception1;
        ioexception1;
        IOException ioexception = ioexception1;
        boolean flag2 = retryRequest(betterhttprequestretryhandler, ioexception, basichttpcontext);
        flag = flag2;
        if(oldTimeout != BetterHttp.getSocketTimeout())
        {
            BetterHttp.setSocketTimeout(oldTimeout);
            obj = ioexception;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_236;
        NullPointerException nullpointerexception;
        nullpointerexception;
        ioexception = new IOException((new StringBuilder("NPE in HttpClient")).append(nullpointerexception.getMessage()).toString());
        boolean flag1 = retryRequest(betterhttprequestretryhandler, ioexception, basichttpcontext);
        flag = flag1;
        if(oldTimeout != BetterHttp.getSocketTimeout())
        {
            BetterHttp.setSocketTimeout(oldTimeout);
            obj = ioexception;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_236;
        Exception exception;
        exception;
        obj;
_L2:
        if(oldTimeout != BetterHttp.getSocketTimeout())
            BetterHttp.setSocketTimeout(oldTimeout);
        throw exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
        obj = ioexception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public HttpUriRequest unwrap()
    {
        return request;
    }

    public BetterHttpRequest withTimeout(int i)
    {
        oldTimeout = httpClient.getParams().getIntParameter("http.socket.timeout", 30000);
        BetterHttp.setSocketTimeout(i);
        return this;
    }

    protected static final String HTTP_CONTENT_TYPE_HEADER = "Content-Type";
    private static final int MAX_RETRIES = 5;
    private int executionCount;
    protected List expectedStatusCodes;
    protected AbstractHttpClient httpClient;
    protected int maxRetries;
    private int oldTimeout;
    protected HttpUriRequest request;
}
