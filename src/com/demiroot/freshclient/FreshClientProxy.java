// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.io.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.*;

// Referenced classes of package com.demiroot.freshclient:
//            AmazonFreshBase, SearchRequest, FreshAPILoginException, APICallException, 
//            FreshAPIException, APIArgs, SeralizableCookie

public class FreshClientProxy
    implements Serializable
{
    static interface FreshAPIExceptionListener
        extends Serializable
    {

        public abstract void onAPIExeption(FreshAPIException freshapiexception);
    }


    public FreshClientProxy(String s, String s1)
    {
        FRESH_URL = "https://fresh.amazon.com/api";
        applicationId = s;
        exceptionListeners = new LinkedList();
        cookies = new LinkedList();
        userAgent = s1;
    }

    public static void main(String args[])
        throws Exception
    {
        HashMap hashmap = new HashMap();
        hashmap.put("password", "password");
        hashmap.put("emailAddress", "seoc+test@amazon.com");
        AmazonFreshBase amazonfreshbase = new AmazonFreshBase(new AmazonFreshBase.SimpleFreshProxyGetter("79471700793667725575"));
        amazonfreshbase.login("seoc+test@amazon.com", "password");
        (new SearchRequest("fish")).search(amazonfreshbase);
    }

    private JSONObject parseResponseJSON(String s)
    {
        JSONObject jsonobject = new JSONObject(s);
        if(jsonobject.getString("status").equals("success") && jsonobject.getJSONArray("errors").length() <= 0) goto _L2; else goto _L1
_L1:
        int i;
        String s1;
        JSONObject jsonobject1 = jsonobject.getJSONArray("errors").getJSONObject(0);
        i = jsonobject1.getInt("code");
        s1 = jsonobject1.getString("message");
        if(i != 17 && i != 16) goto _L4; else goto _L3
_L3:
        Object obj = new FreshAPILoginException(i, s1, s);
_L12:
        Iterator iterator = exceptionListeners.iterator();
_L6:
        JSONException jsonexception;
        if(!iterator.hasNext())
            throw obj;
          goto _L5
_L4:
        obj = new FreshAPIException(i, s1, s);
        continue; /* Loop/switch isn't completed */
_L5:
        ((FreshAPIExceptionListener)iterator.next()).onAPIExeption(((FreshAPIException) (obj)));
          goto _L6
_L2:
        if(!jsonobject.has("resource")) goto _L8; else goto _L7
_L7:
        JSONObject jsonobject4 = jsonobject.getJSONObject("resource");
        JSONObject jsonobject3 = jsonobject4;
_L10:
        return jsonobject3;
_L8:
        jsonobject3 = null;
        continue; /* Loop/switch isn't completed */
        JSONException jsonexception1;
        jsonexception1;
        JSONObject jsonobject2;
        try
        {
            JSONArray jsonarray = jsonobject.getJSONArray("resource");
            jsonobject2 = new JSONObject();
            jsonobject2.put("list", jsonarray);
        }
        // Misplaced declaration of an exception variable
        catch(JSONException jsonexception)
        {
            throw new APICallException((new StringBuilder("Could not parse json: ")).append(s).toString(), jsonexception);
        }
        jsonobject3 = jsonobject2;
        if(true) goto _L10; else goto _L9
_L9:
        if(true) goto _L12; else goto _L11
_L11:
    }

    public void addFreshAPIExceptionListener(FreshAPIExceptionListener freshapiexceptionlistener)
    {
        exceptionListeners.add(freshapiexceptionlistener);
    }

    public void clear()
    {
        cookies.clear();
    }

    public JSONObject get(String s, Map map)
    {
        return request(s, "GET", map);
    }

    public void get(String s)
    {
        get(s, ((Map) (new APIArgs(new Object[0]))));
    }

    public List getCookies()
    {
        return cookies;
    }

    public JSONObject post(String s, Map map)
    {
        return request(s, "POST", map);
    }

    public void post(String s)
    {
        post(s, ((Map) (new APIArgs(new Object[0]))));
    }

    public boolean removeFreshAPIExceptionListener(FreshAPIExceptionListener freshapiexceptionlistener)
    {
        return exceptionListeners.remove(freshapiexceptionlistener);
    }

    public JSONObject request(String s, String s1, Map map)
    {
        BasicCookieStore basiccookiestore;
        Iterator iterator;
        basiccookiestore = new BasicCookieStore();
        iterator = cookies.iterator();
_L13:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        DefaultHttpClient defaulthttpclient;
        BasicHttpContext basichttpcontext;
        HttpEntity httpentity;
        defaulthttpclient = new DefaultHttpClient();
        basichttpcontext = new BasicHttpContext();
        basichttpcontext.setAttribute("http.cookie-store", basiccookiestore);
        httpentity = null;
        String s2;
        LinkedList linkedlist;
        LinkedList linkedlist1;
        s2 = (new StringBuilder(String.valueOf(FRESH_URL))).append(s).toString();
        linkedlist = new LinkedList();
        linkedlist1 = new LinkedList();
        if(!s1.equals("POST")) goto _L4; else goto _L3
_L3:
        LinkedList linkedlist2 = linkedlist1;
_L14:
        Iterator iterator1 = map.entrySet().iterator();
_L15:
        if(iterator1.hasNext()) goto _L6; else goto _L5
_L5:
        StringBuilder stringbuilder;
        linkedlist.add(new BasicNameValuePair("applicationId", applicationId));
        stringbuilder = new StringBuilder(String.valueOf(s2));
        if(!s2.contains("?")) goto _L8; else goto _L7
_L7:
        String s3 = "&";
_L16:
        String s4 = (new StringBuilder(String.valueOf(stringbuilder.append(s3).toString()))).append(URLEncodedUtils.format(linkedlist, "UTF-8")).toString();
        if(!s1.equals("POST")) goto _L10; else goto _L9
_L9:
        Object obj;
        obj = new HttpPost(s4);
        HttpPost httppost = (HttpPost)obj;
        UrlEncodedFormEntity urlencodedformentity = new UrlEncodedFormEntity(linkedlist1, "UTF-8");
        httppost.setEntity(urlencodedformentity);
_L17:
        HttpResponse httpresponse;
        Iterator iterator2;
        String s5 = userAgent;
        ((HttpUriRequest) (obj)).setHeader("User-Agent", s5);
        httpresponse = defaulthttpclient.execute(((HttpUriRequest) (obj)), basichttpcontext);
        cookies.clear();
        iterator2 = basiccookiestore.getCookies().iterator();
_L18:
        if(iterator2.hasNext()) goto _L12; else goto _L11
_L11:
        BufferedReader bufferedreader;
        StringBuilder stringbuilder1;
        httpentity = httpresponse.getEntity();
        bufferedreader = new BufferedReader(new InputStreamReader(httpentity.getContent()));
        stringbuilder1 = new StringBuilder();
_L19:
        String s6;
        JSONObject jsonobject;
        s6 = bufferedreader.readLine();
        if(s6 != null)
            break MISSING_BLOCK_LABEL_604;
        jsonobject = parseResponseJSON(stringbuilder1.toString());
        Exception exception;
        Exception exception1;
        IOException ioexception;
        APICallException apicallexception;
        java.util.Map.Entry entry;
        BasicNameValuePair basicnamevaluepair;
        Cookie cookie;
        List list;
        SeralizableCookie seralizablecookie;
        if(httpentity != null)
            try
            {
                httpentity.getContent().close();
            }
            catch(Exception exception2) { }
        return jsonobject;
_L2:
        basiccookiestore.addCookie((Cookie)iterator.next());
          goto _L13
_L4:
        linkedlist2 = linkedlist;
          goto _L14
_L6:
        entry = (java.util.Map.Entry)iterator1.next();
        basicnamevaluepair = new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue());
        linkedlist2.add(basicnamevaluepair);
          goto _L15
        ioexception;
        apicallexception = new APICallException("Error making API Call", ioexception);
        throw apicallexception;
        exception;
        if(httpentity != null)
            try
            {
                httpentity.getContent().close();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception1) { }
        throw exception;
_L8:
        s3 = "?";
          goto _L16
_L10:
        obj = new HttpGet(s4);
          goto _L17
_L12:
        cookie = (Cookie)iterator2.next();
        list = cookies;
        seralizablecookie = new SeralizableCookie(cookie);
        list.add(seralizablecookie);
          goto _L18
        stringbuilder1.append(s6).append('\n');
          goto _L19
    }

    public JSONObject search(String s)
    {
        HashMap hashmap = new HashMap();
        hashmap.put("input", s);
        return get((new StringBuilder(String.valueOf(FRESH_URL))).append("/product/search").toString(), hashmap);
    }

    public void setCookies(List list)
    {
        cookies = list;
    }

    public boolean setQtyInCart(String s, int i)
    {
        HashMap hashmap = new HashMap();
        hashmap.put("quantity", (new StringBuilder(String.valueOf(i))).toString());
        post((new StringBuilder(String.valueOf(FRESH_URL))).append("/cartItem/").append(s).toString(), hashmap);
        return true;
    }

    String FRESH_URL;
    String applicationId;
    List cookies;
    List exceptionListeners;
    String userAgent;
}
