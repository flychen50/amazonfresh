// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import android.content.Context;
import android.content.IntentFilter;
import android.net.*;
import android.util.Log;
import com.github.droidfu.cachefu.HttpResponseCache;
import com.github.droidfu.http.ssl.EasySSLSocketFactory;
import com.github.droidfu.support.DiagnosticSupport;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import org.apache.http.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package com.github.droidfu.http:
//            HttpDelete, CachedHttpRequest, HttpGet, HttpPost, 
//            HttpPut, ConnectionChangedBroadcastReceiver, BetterHttpRequest

public class BetterHttp
{
    private static class InflatingEntity extends HttpEntityWrapper
    {

        public InputStream getContent()
            throws IOException
        {
            return new GZIPInputStream(wrappedEntity.getContent());
        }

        public long getContentLength()
        {
            return -1L;
        }

        public InflatingEntity(HttpEntity httpentity)
        {
            super(httpentity);
        }
    }


    public BetterHttp()
    {
    }

    public static BetterHttpRequest delete(String s)
    {
        return new HttpDelete(httpClient, s, defaultHeaders);
    }

    public static void enableResponseCache(int i, long l, int j)
    {
        responseCache = new HttpResponseCache(i, l, j);
    }

    public static void enableResponseCache(Context context, int i, long l, int j, int k)
    {
        enableResponseCache(i, l, j);
        responseCache.enableDiskCache(context, k);
    }

    public static BetterHttpRequest get(String s)
    {
        return get(s, false);
    }

    public static BetterHttpRequest get(String s, boolean flag)
    {
        Object obj;
        if(flag && responseCache != null && responseCache.containsKey(s))
            obj = new CachedHttpRequest(s);
        else
            obj = new HttpGet(httpClient, s, defaultHeaders);
        return ((BetterHttpRequest) (obj));
    }

    public static HashMap getDefaultHeaders()
    {
        return defaultHeaders;
    }

    public static AbstractHttpClient getHttpClient()
    {
        return httpClient;
    }

    public static HttpResponseCache getResponseCache()
    {
        return responseCache;
    }

    public static int getSocketTimeout()
    {
        return socketTimeout;
    }

    public static BetterHttpRequest post(String s)
    {
        return new HttpPost(httpClient, s, defaultHeaders);
    }

    public static BetterHttpRequest post(String s, HttpEntity httpentity)
    {
        return new HttpPost(httpClient, s, httpentity, defaultHeaders);
    }

    public static BetterHttpRequest put(String s)
    {
        return new HttpPut(httpClient, s, defaultHeaders);
    }

    public static BetterHttpRequest put(String s, HttpEntity httpentity)
    {
        return new HttpPut(httpClient, s, httpentity, defaultHeaders);
    }

    public static void setContext(Context context)
    {
        if(appContext == null)
        {
            appContext = context.getApplicationContext();
            appContext.registerReceiver(new ConnectionChangedBroadcastReceiver(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public static void setDefaultHeader(String s, String s1)
    {
        defaultHeaders.put(s, s1);
    }

    public static void setHttpClient(AbstractHttpClient abstracthttpclient)
    {
        httpClient = abstracthttpclient;
    }

    public static void setMaximumConnections(int i)
    {
        maxConnections = i;
    }

    public static void setPortForScheme(String s, int i)
    {
        Scheme scheme = new Scheme(s, PlainSocketFactory.getSocketFactory(), i);
        httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
    }

    public static void setSocketTimeout(int i)
    {
        socketTimeout = i;
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), i);
    }

    public static void setUserAgent(String s)
    {
        httpUserAgent = s;
        HttpProtocolParams.setUserAgent(httpClient.getParams(), s);
    }

    public static void setupHttpClient()
    {
        BasicHttpParams basichttpparams = new BasicHttpParams();
        ConnManagerParams.setTimeout(basichttpparams, socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(basichttpparams, new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(basichttpparams, 4);
        HttpConnectionParams.setSoTimeout(basichttpparams, socketTimeout);
        HttpConnectionParams.setTcpNoDelay(basichttpparams, true);
        HttpProtocolParams.setVersion(basichttpparams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(basichttpparams, httpUserAgent);
        SchemeRegistry schemeregistry = new SchemeRegistry();
        schemeregistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        if(DiagnosticSupport.ANDROID_API_LEVEL >= 7)
            schemeregistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        else
            schemeregistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basichttpparams, schemeregistry), basichttpparams);
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

            public void process(HttpRequest httprequest, HttpContext httpcontext)
            {
                if(!httprequest.containsHeader("Accept-Encoding"))
                    httprequest.addHeader("Accept-Encoding", "gzip");
            }

        }
);
        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

            public void process(HttpResponse httpresponse, HttpContext httpcontext)
            {
                Header header = httpresponse.getEntity().getContentEncoding();
                if(header == null) goto _L2; else goto _L1
_L1:
                HeaderElement aheaderelement[];
                int i;
                int j;
                aheaderelement = header.getElements();
                i = aheaderelement.length;
                j = 0;
_L6:
                if(j < i) goto _L3; else goto _L2
_L2:
                return;
_L3:
                if(!aheaderelement[j].getName().equalsIgnoreCase("gzip"))
                    break; /* Loop/switch isn't completed */
                httpresponse.setEntity(new InflatingEntity(httpresponse.getEntity()));
                if(true) goto _L2; else goto _L4
_L4:
                j++;
                if(true) goto _L6; else goto _L5
_L5:
            }

        }
);
    }

    public static void updateProxySettings()
    {
        if(appContext != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        HttpParams httpparams = httpClient.getParams();
        NetworkInfo networkinfo = ((ConnectivityManager)appContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if(networkinfo != null)
        {
            Log.i("BetterHttp", networkinfo.toString());
            if(networkinfo.getType() == 0)
            {
                String s = Proxy.getHost(appContext);
                if(s == null)
                    s = Proxy.getDefaultHost();
                int i = Proxy.getPort(appContext);
                if(i == -1)
                    i = Proxy.getDefaultPort();
                if(s != null && i > -1)
                    httpparams.setParameter("http.route.default-proxy", new HttpHost(s, i));
                else
                    httpparams.setParameter("http.route.default-proxy", null);
            } else
            {
                httpparams.setParameter("http.route.default-proxy", null);
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static final String DEFAULT_HTTP_USER_AGENT = "Android/DroidFu";
    public static final int DEFAULT_MAX_CONNECTIONS = 4;
    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    static final String LOG_TAG = "BetterHttp";
    private static Context appContext;
    private static HashMap defaultHeaders = new HashMap();
    private static AbstractHttpClient httpClient;
    private static String httpUserAgent = "Android/DroidFu";
    private static int maxConnections = 4;
    private static HttpResponseCache responseCache;
    private static int socketTimeout = 30000;

}
