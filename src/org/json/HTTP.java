// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//            JSONException, JSONObject, HTTPTokener

public class HTTP
{

    public HTTP()
    {
    }

    public static JSONObject toJSONObject(String s)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject();
        HTTPTokener httptokener = new HTTPTokener(s);
        String s1 = httptokener.nextToken();
        if(s1.toUpperCase().startsWith("HTTP"))
        {
            jsonobject.put("HTTP-Version", s1);
            jsonobject.put("Status-Code", httptokener.nextToken());
            jsonobject.put("Reason-Phrase", httptokener.nextTo('\0'));
            httptokener.next();
        } else
        {
            jsonobject.put("Method", s1);
            jsonobject.put("Request-URI", httptokener.nextToken());
            jsonobject.put("HTTP-Version", httptokener.nextToken());
        }
        do
        {
            if(!httptokener.more())
                return jsonobject;
            String s2 = httptokener.nextTo(':');
            httptokener.next(':');
            jsonobject.put(s2, httptokener.nextTo('\0'));
            httptokener.next();
        } while(true);
    }

    public static String toString(JSONObject jsonobject)
        throws JSONException
    {
        Iterator iterator = jsonobject.keys();
        StringBuffer stringbuffer = new StringBuffer();
        if(jsonobject.has("Status-Code") && jsonobject.has("Reason-Phrase"))
        {
            stringbuffer.append(jsonobject.getString("HTTP-Version"));
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("Status-Code"));
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("Reason-Phrase"));
        } else
        if(jsonobject.has("Method") && jsonobject.has("Request-URI"))
        {
            stringbuffer.append(jsonobject.getString("Method"));
            stringbuffer.append(' ');
            stringbuffer.append('"');
            stringbuffer.append(jsonobject.getString("Request-URI"));
            stringbuffer.append('"');
            stringbuffer.append(' ');
            stringbuffer.append(jsonobject.getString("HTTP-Version"));
        } else
        {
            throw new JSONException("Not enough material for an HTTP header.");
        }
        stringbuffer.append("\r\n");
        do
        {
            String s;
            do
            {
                if(!iterator.hasNext())
                {
                    stringbuffer.append("\r\n");
                    return stringbuffer.toString();
                }
                s = iterator.next().toString();
            } while(s.equals("HTTP-Version") || s.equals("Status-Code") || s.equals("Reason-Phrase") || s.equals("Method") || s.equals("Request-URI") || jsonobject.isNull(s));
            stringbuffer.append(s);
            stringbuffer.append(": ");
            stringbuffer.append(jsonobject.getString(s));
            stringbuffer.append("\r\n");
        } while(true);
    }

    public static final String CRLF = "\r\n";
}
