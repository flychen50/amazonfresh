// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class BetterHttpRequestRetryHandler
    implements HttpRequestRetryHandler
{

    public BetterHttpRequestRetryHandler(int i)
    {
        maxRetries = i;
    }

    public int getTimesRetried()
    {
        return timesRetried;
    }

    public boolean retryRequest(IOException ioexception, int i, HttpContext httpcontext)
    {
        timesRetried = i;
        Boolean boolean1 = (Boolean)httpcontext.getAttribute("http.request_sent");
        boolean flag;
        boolean flag1;
        if(boolean1 != null && boolean1.booleanValue())
            flag = true;
        else
            flag = false;
        if(i > maxRetries)
            flag1 = false;
        else
        if(exceptionBlacklist.contains(ioexception.getClass()))
            flag1 = false;
        else
        if(exceptionWhitelist.contains(ioexception.getClass()))
            flag1 = true;
        else
        if(!flag)
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
        {
            Log.e("BetterHttp", (new StringBuilder("request failed (")).append(ioexception.getClass().getCanonicalName()).append(": ").append(ioexception.getMessage()).append(" / attempt ").append(i).append("), will retry in ").append(1.5D).append(" seconds").toString());
            SystemClock.sleep(1500L);
        } else
        {
            Log.e("BetterHttp", (new StringBuilder("request failed after ")).append(i).append(" attempts").toString());
            ioexception.printStackTrace();
        }
        return flag1;
    }

    private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
    private static HashSet exceptionBlacklist;
    private static HashSet exceptionWhitelist;
    private int maxRetries;
    private int timesRetried;

    static 
    {
        exceptionWhitelist = new HashSet();
        exceptionBlacklist = new HashSet();
        exceptionWhitelist.add(org/apache/http/NoHttpResponseException);
        exceptionWhitelist.add(java/net/UnknownHostException);
        exceptionWhitelist.add(java/net/SocketException);
        exceptionBlacklist.add(java/io/InterruptedIOException);
        exceptionBlacklist.add(javax/net/ssl/SSLHandshakeException);
    }
}
