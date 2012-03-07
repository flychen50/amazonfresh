// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//            JSONException, XMLTokener, XML, JSONArray, 
//            JSONObject

public class JSONML
{

    public JSONML()
    {
    }

    private static Object parse(XMLTokener xmltokener, boolean flag, JSONArray jsonarray)
        throws JSONException
    {
_L9:
        Object obj = xmltokener.nextContent();
        if(obj != XML.LT) goto _L2; else goto _L1
_L1:
        Object obj2 = xmltokener.nextToken();
        if(!(obj2 instanceof Character)) goto _L4; else goto _L3
_L3:
        if(obj2 != XML.SLASH) goto _L6; else goto _L5
_L5:
        Object obj6;
        Object obj8 = xmltokener.nextToken();
        if(!(obj8 instanceof String))
            throw new JSONException((new StringBuilder("Expected a closing name instead of '")).append(obj8).append("'.").toString());
        if(xmltokener.nextToken() != XML.GT)
            throw xmltokener.syntaxError("Misshaped close tag");
        obj6 = obj8;
_L7:
        return obj6;
_L6:
        if(obj2 == XML.BANG)
        {
            char c = xmltokener.next();
            if(c == '-')
            {
                if(xmltokener.next() == '-')
                    xmltokener.skipPast("-->");
                xmltokener.back();
                continue; /* Loop/switch isn't completed */
            }
            if(c == '[')
            {
                if(xmltokener.nextToken().equals("CDATA") && xmltokener.next() == '[')
                {
                    if(jsonarray != null)
                        jsonarray.put(xmltokener.nextCDATA());
                } else
                {
                    throw xmltokener.syntaxError("Expected 'CDATA['");
                }
                continue; /* Loop/switch isn't completed */
            }
            int i = 1;
            do
            {
                Object obj7 = xmltokener.nextMeta();
                if(obj7 == null)
                    throw xmltokener.syntaxError("Missing '>' after '<!'.");
                if(obj7 == XML.LT)
                    i++;
                else
                if(obj7 == XML.GT)
                    i--;
            } while(i > 0);
            continue; /* Loop/switch isn't completed */
        } else
        {
            if(obj2 == XML.QUEST)
                xmltokener.skipPast("?>");
            else
                throw xmltokener.syntaxError("Misshaped tag");
            continue; /* Loop/switch isn't completed */
        }
_L4:
        if(!(obj2 instanceof String))
            throw xmltokener.syntaxError((new StringBuilder("Bad tagName '")).append(obj2).append("'.").toString());
        String s = (String)obj2;
        JSONArray jsonarray1 = new JSONArray();
        JSONObject jsonobject = new JSONObject();
        Object obj3;
        if(flag)
        {
            jsonarray1.put(s);
            if(jsonarray != null)
                jsonarray.put(jsonarray1);
        } else
        {
            jsonobject.put("tagName", s);
            if(jsonarray != null)
                jsonarray.put(jsonobject);
        }
        obj3 = null;
        do
        {
label0:
            {
                Object obj1;
                Object obj4;
                String s1;
                String s2;
                if(obj3 == null)
                    obj4 = xmltokener.nextToken();
                else
                    obj4 = obj3;
                if(obj4 == null)
                    throw xmltokener.syntaxError("Misshaped tag");
                if(!(obj4 instanceof String))
                {
                    if(flag && jsonobject.length() > 0)
                        jsonarray1.put(jsonobject);
                    if(obj4 != XML.SLASH)
                        break MISSING_BLOCK_LABEL_586;
                    if(xmltokener.nextToken() != XML.GT)
                        throw xmltokener.syntaxError("Misshaped tag");
                    break label0;
                }
                s1 = (String)obj4;
                if(!flag && (s1 == "tagName" || s1 == "childNode"))
                    throw xmltokener.syntaxError("Reserved attribute.");
                obj3 = xmltokener.nextToken();
                if(obj3 == XML.EQ)
                {
                    Object obj5 = xmltokener.nextToken();
                    if(!(obj5 instanceof String))
                        throw xmltokener.syntaxError("Missing value");
                    jsonobject.accumulate(s1, JSONObject.stringToValue((String)obj5));
                    obj3 = null;
                } else
                {
                    jsonobject.accumulate(s1, "");
                }
            }
        } while(true);
        if(jsonarray != null)
            continue; /* Loop/switch isn't completed */
        if(flag)
            obj6 = jsonarray1;
        else
            obj6 = jsonobject;
        continue; /* Loop/switch isn't completed */
        if(obj4 != XML.GT)
            throw xmltokener.syntaxError("Misshaped tag");
        s2 = (String)parse(xmltokener, flag, jsonarray1);
        if(s2 == null)
            continue; /* Loop/switch isn't completed */
        if(!s2.equals(s))
            throw xmltokener.syntaxError((new StringBuilder("Mismatched '")).append(s).append("' and '").append(s2).append("'").toString());
        if(!flag && jsonarray1.length() > 0)
            jsonobject.put("childNodes", jsonarray1);
        if(jsonarray != null)
            continue; /* Loop/switch isn't completed */
        if(flag)
            obj6 = jsonarray1;
        else
            obj6 = jsonobject;
        if(true) goto _L7; else goto _L2
_L2:
        if(jsonarray != null)
        {
            if(obj instanceof String)
                obj1 = JSONObject.stringToValue((String)obj);
            else
                obj1 = obj;
            jsonarray.put(obj1);
        }
        if(true) goto _L9; else goto _L8
_L8:
    }

    public static JSONArray toJSONArray(String s)
        throws JSONException
    {
        return toJSONArray(new XMLTokener(s));
    }

    public static JSONArray toJSONArray(XMLTokener xmltokener)
        throws JSONException
    {
        return (JSONArray)parse(xmltokener, true, null);
    }

    public static JSONObject toJSONObject(String s)
        throws JSONException
    {
        return toJSONObject(new XMLTokener(s));
    }

    public static JSONObject toJSONObject(XMLTokener xmltokener)
        throws JSONException
    {
        return (JSONObject)parse(xmltokener, false, null);
    }

    public static String toString(JSONArray jsonarray)
        throws JSONException
    {
        StringBuffer stringbuffer;
        String s1;
        int i;
        JSONObject jsonobject;
        Iterator iterator;
        stringbuffer = new StringBuffer();
        String s = jsonarray.getString(0);
        XML.noSpace(s);
        s1 = XML.escape(s);
        stringbuffer.append('<');
        stringbuffer.append(s1);
        Object obj = jsonarray.opt(1);
        if(!(obj instanceof JSONObject))
            break MISSING_BLOCK_LABEL_192;
        i = 2;
        jsonobject = (JSONObject)obj;
        iterator = jsonobject.keys();
_L3:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        int j = jsonarray.length();
        String s2;
        String s3;
        if(i >= j)
        {
            stringbuffer.append('/');
            stringbuffer.append('>');
        } else
        {
            stringbuffer.append('>');
            do
            {
                Object obj1 = jsonarray.get(i);
                i++;
                if(obj1 != null)
                    if(obj1 instanceof String)
                        stringbuffer.append(XML.escape(obj1.toString()));
                    else
                    if(obj1 instanceof JSONObject)
                        stringbuffer.append(toString((JSONObject)obj1));
                    else
                    if(obj1 instanceof JSONArray)
                        stringbuffer.append(toString((JSONArray)obj1));
            } while(i < j);
            stringbuffer.append('<');
            stringbuffer.append('/');
            stringbuffer.append(s1);
            stringbuffer.append('>');
        }
        return stringbuffer.toString();
_L2:
        s2 = iterator.next().toString();
        XML.noSpace(s2);
        s3 = jsonobject.optString(s2);
        if(s3 != null)
        {
            stringbuffer.append(' ');
            stringbuffer.append(XML.escape(s2));
            stringbuffer.append('=');
            stringbuffer.append('"');
            stringbuffer.append(XML.escape(s3));
            stringbuffer.append('"');
        }
          goto _L3
        i = 1;
          goto _L1
    }

    public static String toString(JSONObject jsonobject)
        throws JSONException
    {
        StringBuffer stringbuffer;
        String s;
        stringbuffer = new StringBuffer();
        s = jsonobject.optString("tagName");
        if(s != null) goto _L2; else goto _L1
_L1:
        String s4 = XML.escape(jsonobject.toString());
_L7:
        return s4;
_L2:
        String s1;
        Iterator iterator;
        XML.noSpace(s);
        s1 = XML.escape(s);
        stringbuffer.append('<');
        stringbuffer.append(s1);
        iterator = jsonobject.keys();
_L8:
        if(iterator.hasNext()) goto _L4; else goto _L3
_L3:
        JSONArray jsonarray = jsonobject.optJSONArray("childNodes");
        if(jsonarray != null) goto _L6; else goto _L5
_L5:
        stringbuffer.append('/');
        stringbuffer.append('>');
_L9:
        s4 = stringbuffer.toString();
          goto _L7
_L4:
        String s2 = iterator.next().toString();
        if(!s2.equals("tagName") && !s2.equals("childNodes"))
        {
            XML.noSpace(s2);
            String s3 = jsonobject.optString(s2);
            if(s3 != null)
            {
                stringbuffer.append(' ');
                stringbuffer.append(XML.escape(s2));
                stringbuffer.append('=');
                stringbuffer.append('"');
                stringbuffer.append(XML.escape(s3));
                stringbuffer.append('"');
            }
        }
          goto _L8
_L6:
        int i;
        int j;
        stringbuffer.append('>');
        i = jsonarray.length();
        j = 0;
_L10:
label0:
        {
            if(j < i)
                break label0;
            stringbuffer.append('<');
            stringbuffer.append('/');
            stringbuffer.append(s1);
            stringbuffer.append('>');
        }
          goto _L9
        Object obj = jsonarray.get(j);
        if(obj != null)
            if(obj instanceof String)
                stringbuffer.append(XML.escape(obj.toString()));
            else
            if(obj instanceof JSONObject)
                stringbuffer.append(toString((JSONObject)obj));
            else
            if(obj instanceof JSONArray)
                stringbuffer.append(toString((JSONArray)obj));
        j++;
          goto _L10
    }
}
