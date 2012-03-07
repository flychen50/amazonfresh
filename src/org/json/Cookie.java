// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;


// Referenced classes of package org.json:
//            JSONException, JSONObject, JSONTokener

public class Cookie
{

    public Cookie()
    {
    }

    public static String escape(String s)
    {
        String s1 = s.trim();
        StringBuffer stringbuffer = new StringBuffer();
        int i = s1.length();
        int j = 0;
        do
        {
            if(j >= i)
                return stringbuffer.toString();
            char c = s1.charAt(j);
            if(c < ' ' || c == '+' || c == '%' || c == '=' || c == ';')
            {
                stringbuffer.append('%');
                stringbuffer.append(Character.forDigit(0xf & c >>> 4, 16));
                stringbuffer.append(Character.forDigit(c & 0xf, 16));
            } else
            {
                stringbuffer.append(c);
            }
            j++;
        } while(true);
    }

    public static JSONObject toJSONObject(String s)
        throws JSONException
    {
        JSONObject jsonobject;
        JSONTokener jsontokener;
        jsonobject = new JSONObject();
        jsontokener = new JSONTokener(s);
        jsonobject.put("name", jsontokener.nextTo('='));
        jsontokener.next('=');
        jsonobject.put("value", jsontokener.nextTo(';'));
        jsontokener.next();
_L2:
        String s1;
        Object obj;
        if(!jsontokener.more())
            return jsonobject;
        s1 = unescape(jsontokener.nextTo("=;"));
        if(jsontokener.next() == '=')
            break MISSING_BLOCK_LABEL_118;
        if(!s1.equals("secure"))
            break; /* Loop/switch isn't completed */
        obj = Boolean.TRUE;
_L3:
        jsonobject.put(s1, obj);
        if(true) goto _L2; else goto _L1
_L1:
        throw jsontokener.syntaxError("Missing '=' in cookie parameter.");
        obj = unescape(jsontokener.nextTo(';'));
        jsontokener.next();
          goto _L3
    }

    public static String toString(JSONObject jsonobject)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(escape(jsonobject.getString("name")));
        stringbuffer.append("=");
        stringbuffer.append(escape(jsonobject.getString("value")));
        if(jsonobject.has("expires"))
        {
            stringbuffer.append(";expires=");
            stringbuffer.append(jsonobject.getString("expires"));
        }
        if(jsonobject.has("domain"))
        {
            stringbuffer.append(";domain=");
            stringbuffer.append(escape(jsonobject.getString("domain")));
        }
        if(jsonobject.has("path"))
        {
            stringbuffer.append(";path=");
            stringbuffer.append(escape(jsonobject.getString("path")));
        }
        if(jsonobject.optBoolean("secure"))
            stringbuffer.append(";secure");
        return stringbuffer.toString();
    }

    public static String unescape(String s)
    {
        int i;
        StringBuffer stringbuffer;
        int j;
        i = s.length();
        stringbuffer = new StringBuffer();
        j = 0;
_L2:
        char c;
        if(j >= i)
            return stringbuffer.toString();
        c = s.charAt(j);
        if(c != '+')
            break; /* Loop/switch isn't completed */
        c = ' ';
_L4:
        stringbuffer.append(c);
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        if(c != '%' || j + 2 >= i) goto _L4; else goto _L3
_L3:
        int k = JSONTokener.dehexchar(s.charAt(j + 1));
        int l = JSONTokener.dehexchar(s.charAt(j + 2));
        if(k >= 0 && l >= 0)
        {
            c = (char)(l + k * 16);
            j += 2;
        }
          goto _L4
    }
}
