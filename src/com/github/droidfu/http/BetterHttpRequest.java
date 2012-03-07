// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import java.net.ConnectException;
import org.apache.http.client.methods.HttpUriRequest;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpResponse

public interface BetterHttpRequest
{

    public transient abstract BetterHttpRequest expecting(Integer ainteger[]);

    public abstract String getRequestUrl();

    public abstract BetterHttpRequest retries(int i);

    public abstract BetterHttpResponse send()
        throws ConnectException;

    public abstract HttpUriRequest unwrap();

    public abstract BetterHttpRequest withTimeout(int i);
}
