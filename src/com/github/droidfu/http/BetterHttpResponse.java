// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;

public interface BetterHttpResponse
{

    public abstract String getHeader(String s);

    public abstract InputStream getResponseBody()
        throws IOException;

    public abstract byte[] getResponseBodyAsBytes()
        throws IOException;

    public abstract String getResponseBodyAsString()
        throws IOException;

    public abstract int getStatusCode();

    public abstract HttpResponse unwrap();
}
