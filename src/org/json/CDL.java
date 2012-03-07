// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;


// Referenced classes of package org.json:
//            JSONException, JSONTokener, JSONArray, JSONObject

public class CDL
{

    public CDL()
    {
    }

    private static String getValue(JSONTokener jsontokener)
        throws JSONException
    {
        char c;
        do
            c = jsontokener.next();
        while(c == ' ' || c == '\t');
        c;
        JVM INSTR lookupswitch 4: default 60
    //                   0: 73
    //                   34: 78
    //                   39: 78
    //                   44: 161;
           goto _L1 _L2 _L3 _L3 _L4
_L1:
        String s;
        jsontokener.back();
        s = jsontokener.nextTo(',');
_L6:
        return s;
_L2:
        s = null;
        continue; /* Loop/switch isn't completed */
_L3:
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            char c1 = jsontokener.next();
            if(c1 == c)
            {
                s = stringbuffer.toString();
                continue; /* Loop/switch isn't completed */
            }
            if(c1 == 0 || c1 == '\n' || c1 == '\r')
                throw jsontokener.syntaxError((new StringBuilder("Missing close quote '")).append(c).append("'.").toString());
            stringbuffer.append(c1);
        } while(true);
_L4:
        jsontokener.back();
        s = "";
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static JSONArray rowToJSONArray(JSONTokener jsontokener)
        throws JSONException
    {
        JSONArray jsonarray = new JSONArray();
_L4:
        String s;
        char c;
        s = getValue(jsontokener);
        c = jsontokener.next();
        if(s != null && (jsonarray.length() != 0 || s.length() != 0 || c == ',')) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray1 = null;
_L5:
        return jsonarray1;
_L2:
        jsonarray.put(s);
_L6:
        if(c == ',') goto _L4; else goto _L3
_L3:
label0:
        {
            if(c == ' ')
                break label0;
            if(c == '\n' || c == '\r' || c == 0)
                jsonarray1 = jsonarray;
            else
                throw jsontokener.syntaxError((new StringBuilder("Bad character '")).append(c).append("' (").append(c).append(").").toString());
        }
          goto _L5
        c = jsontokener.next();
          goto _L6
    }

    public static JSONObject rowToJSONObject(JSONArray jsonarray, JSONTokener jsontokener)
        throws JSONException
    {
        JSONArray jsonarray1 = rowToJSONArray(jsontokener);
        JSONObject jsonobject;
        if(jsonarray1 != null)
            jsonobject = jsonarray1.toJSONObject(jsonarray);
        else
            jsonobject = null;
        return jsonobject;
    }

    public static String rowToString(JSONArray jsonarray)
    {
        StringBuffer stringbuffer;
        int i;
        stringbuffer = new StringBuffer();
        i = 0;
_L5:
        Object obj;
        if(i >= jsonarray.length())
        {
            stringbuffer.append('\n');
            return stringbuffer.toString();
        }
        if(i > 0)
            stringbuffer.append(',');
        obj = jsonarray.opt(i);
        if(obj == null) goto _L2; else goto _L1
_L1:
        String s;
        int j;
        int k;
        s = obj.toString();
        if(s.length() <= 0 || s.indexOf(',') < 0 && s.indexOf('\n') < 0 && s.indexOf('\r') < 0 && s.indexOf('\0') < 0 && s.charAt(0) != '"')
            break MISSING_BLOCK_LABEL_188;
        stringbuffer.append('"');
        j = s.length();
        k = 0;
_L6:
        if(k < j) goto _L4; else goto _L3
_L3:
        stringbuffer.append('"');
_L2:
        i++;
          goto _L5
_L4:
        char c = s.charAt(k);
        if(c >= ' ' && c != '"')
            stringbuffer.append(c);
        k++;
          goto _L6
        stringbuffer.append(s);
          goto _L2
    }

    public static JSONArray toJSONArray(String s)
        throws JSONException
    {
        return toJSONArray(new JSONTokener(s));
    }

    public static JSONArray toJSONArray(JSONArray jsonarray, String s)
        throws JSONException
    {
        return toJSONArray(jsonarray, new JSONTokener(s));
    }

    public static JSONArray toJSONArray(JSONArray jsonarray, JSONTokener jsontokener)
        throws JSONException
    {
        if(jsonarray != null && jsonarray.length() != 0) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray1 = null;
_L4:
        return jsonarray1;
_L2:
        JSONArray jsonarray2 = new JSONArray();
_L5:
label0:
        {
            JSONObject jsonobject = rowToJSONObject(jsonarray, jsontokener);
            if(jsonobject != null)
                break label0;
            if(jsonarray2.length() == 0)
                jsonarray1 = null;
            else
                jsonarray1 = jsonarray2;
        }
        if(true) goto _L4; else goto _L3
_L3:
        jsonarray2.put(jsonobject);
          goto _L5
    }

    public static JSONArray toJSONArray(JSONTokener jsontokener)
        throws JSONException
    {
        return toJSONArray(rowToJSONArray(jsontokener), jsontokener);
    }

    public static String toString(JSONArray jsonarray)
        throws JSONException
    {
        JSONObject jsonobject = jsonarray.optJSONObject(0);
        if(jsonobject == null) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray1 = jsonobject.names();
        if(jsonarray1 == null) goto _L2; else goto _L3
_L3:
        String s = (new StringBuilder(String.valueOf(rowToString(jsonarray1)))).append(toString(jsonarray1, jsonarray)).toString();
_L5:
        return s;
_L2:
        s = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static String toString(JSONArray jsonarray, JSONArray jsonarray1)
        throws JSONException
    {
        if(jsonarray != null && jsonarray.length() != 0) goto _L2; else goto _L1
_L1:
        String s = null;
_L4:
        return s;
_L2:
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        do
        {
label0:
            {
                if(i < jsonarray1.length())
                    break label0;
                s = stringbuffer.toString();
            }
            if(true)
                continue;
            JSONObject jsonobject = jsonarray1.optJSONObject(i);
            if(jsonobject != null)
                stringbuffer.append(rowToString(jsonobject.toJSONArray(jsonarray)));
            i++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }
}
