// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttpRequestBase

class HttpGet extends BetterHttpRequestBase
{

    HttpGet(AbstractHttpClient abstracthttpclient, String s, HashMap hashmap)
    {
        super(abstracthttpclient);
        request = new org.apache.http.client.methods.HttpGet(s);
        Iterator iterator = hashmap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                return;
            String s1 = (String)iterator.next();
            request.setHeader(s1, (String)hashmap.get(s1));
        } while(true);
    }
}
