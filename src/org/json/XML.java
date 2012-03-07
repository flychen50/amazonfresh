// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//            JSONException, XMLTokener, JSONObject, JSONArray

public class XML
{

    public XML()
    {
    }

    public static String escape(String s)
    {
        StringBuffer stringbuffer;
        int i;
        int j;
        stringbuffer = new StringBuffer();
        i = 0;
        j = s.length();
_L7:
        char c;
        if(i >= j)
            return stringbuffer.toString();
        c = s.charAt(i);
        c;
        JVM INSTR lookupswitch 4: default 76
    //                   34: 119
    //                   38: 89
    //                   60: 99
    //                   62: 109;
           goto _L1 _L2 _L3 _L4 _L5
_L2:
        break MISSING_BLOCK_LABEL_119;
_L3:
        break; /* Loop/switch isn't completed */
_L1:
        stringbuffer.append(c);
_L8:
        i++;
        if(true) goto _L7; else goto _L6
_L6:
        stringbuffer.append("&amp;");
          goto _L8
_L4:
        stringbuffer.append("&lt;");
          goto _L8
_L5:
        stringbuffer.append("&gt;");
          goto _L8
        stringbuffer.append("&quot;");
          goto _L8
    }

    public static void noSpace(String s)
        throws JSONException
    {
        int i = s.length();
        if(i == 0)
            throw new JSONException("Empty string.");
        int j = 0;
        do
        {
            if(j >= i)
                return;
            if(Character.isWhitespace(s.charAt(j)))
                throw new JSONException((new StringBuilder("'")).append(s).append("' contains a space character.").toString());
            j++;
        } while(true);
    }

    private static boolean parse(XMLTokener xmltokener, JSONObject jsonobject, String s)
        throws JSONException
    {
        Object obj = xmltokener.nextToken();
        if(obj != BANG) goto _L2; else goto _L1
_L1:
        char c = xmltokener.next();
        if(c != '-') goto _L4; else goto _L3
_L3:
        if(xmltokener.next() != '-') goto _L6; else goto _L5
_L5:
        boolean flag;
        xmltokener.skipPast("-->");
        flag = false;
_L8:
        return flag;
_L6:
        xmltokener.back();
        break MISSING_BLOCK_LABEL_51;
_L4:
        if(c == '[')
        {
            if(xmltokener.nextToken().equals("CDATA") && xmltokener.next() == '[')
            {
                String s4 = xmltokener.nextCDATA();
                if(s4.length() > 0)
                    jsonobject.accumulate("content", s4);
                flag = false;
            } else
            {
                throw xmltokener.syntaxError("Expected 'CDATA['");
            }
            continue; /* Loop/switch isn't completed */
        }
        int i = 1;
        do
        {
            Object obj6 = xmltokener.nextMeta();
            if(obj6 == null)
                throw xmltokener.syntaxError("Missing '>' after '<!'.");
            if(obj6 == LT)
                i++;
            else
            if(obj6 == GT)
                i--;
        } while(i > 0);
        flag = false;
        continue; /* Loop/switch isn't completed */
_L2:
        if(obj == QUEST)
        {
            xmltokener.skipPast("?>");
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        if(obj == SLASH)
        {
            Object obj5 = xmltokener.nextToken();
            if(s == null)
                throw xmltokener.syntaxError((new StringBuilder("Mismatched close tag")).append(obj5).toString());
            if(!obj5.equals(s))
                throw xmltokener.syntaxError((new StringBuilder("Mismatched ")).append(s).append(" and ").append(obj5).toString());
            if(xmltokener.nextToken() != GT)
                throw xmltokener.syntaxError("Misshaped close tag");
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
        if(obj instanceof Character)
            throw xmltokener.syntaxError("Misshaped tag");
        String s1 = (String)obj;
        Object obj1 = null;
        JSONObject jsonobject1 = new JSONObject();
        do
        {
label0:
            {
                Object obj2;
                Object obj3;
                String s2;
                String s3;
                if(obj1 == null)
                    obj2 = xmltokener.nextToken();
                else
                    obj2 = obj1;
                if(!(obj2 instanceof String))
                    break label0;
                s3 = (String)obj2;
                obj1 = xmltokener.nextToken();
                if(obj1 == EQ)
                {
                    Object obj4 = xmltokener.nextToken();
                    if(!(obj4 instanceof String))
                        throw xmltokener.syntaxError("Missing value");
                    jsonobject1.accumulate(s3, JSONObject.stringToValue((String)obj4));
                    obj1 = null;
                } else
                {
                    jsonobject1.accumulate(s3, "");
                }
            }
        } while(true);
        if(obj2 == SLASH)
        {
            if(xmltokener.nextToken() != GT)
                throw xmltokener.syntaxError("Misshaped tag");
            if(jsonobject1.length() > 0)
                jsonobject.accumulate(s1, jsonobject1);
            else
                jsonobject.accumulate(s1, "");
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        if(obj2 != GT)
            break; /* Loop/switch isn't completed */
label1:
        do
            do
            {
                obj3 = xmltokener.nextContent();
                if(obj3 == null)
                {
                    if(s1 != null)
                        throw xmltokener.syntaxError((new StringBuilder("Unclosed tag ")).append(s1).toString());
                    flag = false;
                    continue; /* Loop/switch isn't completed */
                }
                if(!(obj3 instanceof String))
                    continue label1;
                s2 = (String)obj3;
                if(s2.length() > 0)
                    jsonobject1.accumulate("content", JSONObject.stringToValue(s2));
            } while(true);
        while(obj3 != LT || !parse(xmltokener, jsonobject1, s1));
        if(jsonobject1.length() == 0)
            jsonobject.accumulate(s1, "");
        else
        if(jsonobject1.length() == 1 && jsonobject1.opt("content") != null)
            jsonobject.accumulate(s1, jsonobject1.opt("content"));
        else
            jsonobject.accumulate(s1, jsonobject1);
        flag = false;
        if(true) goto _L8; else goto _L7
_L7:
        throw xmltokener.syntaxError("Misshaped tag");
    }

    public static JSONObject toJSONObject(String s)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject();
        XMLTokener xmltokener = new XMLTokener(s);
        do
        {
            if(!xmltokener.more() || !xmltokener.skipPast("<"))
                return jsonobject;
            parse(xmltokener, jsonobject, null);
        } while(true);
    }

    public static String toString(Object obj)
        throws JSONException
    {
        return toString(obj, null);
    }

    public static String toString(Object obj, String s)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        if(!(obj instanceof JSONObject)) goto _L2; else goto _L1
_L1:
        JSONObject jsonobject;
        Iterator iterator;
        if(s != null)
        {
            stringbuffer.append('<');
            stringbuffer.append(s);
            stringbuffer.append('>');
        }
        jsonobject = (JSONObject)obj;
        iterator = jsonobject.keys();
_L5:
        if(iterator.hasNext()) goto _L4; else goto _L3
_L3:
        String s2;
        if(s != null)
        {
            stringbuffer.append("</");
            stringbuffer.append(s);
            stringbuffer.append('>');
        }
        s2 = stringbuffer.toString();
_L6:
        return s2;
_L4:
        String s4 = iterator.next().toString();
        Object obj2 = jsonobject.opt(s4);
        if(obj2 == null)
            obj2 = "";
        if(obj2 instanceof String)
        {
            (String)obj2;
        }
        if(s4.equals("content"))
        {
            if(obj2 instanceof JSONArray)
            {
                JSONArray jsonarray2 = (JSONArray)obj2;
                int i1 = jsonarray2.length();
                int j1 = 0;
                while(j1 < i1) 
                {
                    if(j1 > 0)
                        stringbuffer.append('\n');
                    stringbuffer.append(escape(jsonarray2.get(j1).toString()));
                    j1++;
                }
            } else
            {
                stringbuffer.append(escape(obj2.toString()));
            }
        } else
        if(obj2 instanceof JSONArray)
        {
            JSONArray jsonarray1 = (JSONArray)obj2;
            int k = jsonarray1.length();
            int l = 0;
            while(l < k) 
            {
                Object obj3 = jsonarray1.get(l);
                if(obj3 instanceof JSONArray)
                {
                    stringbuffer.append('<');
                    stringbuffer.append(s4);
                    stringbuffer.append('>');
                    stringbuffer.append(toString(obj3));
                    stringbuffer.append("</");
                    stringbuffer.append(s4);
                    stringbuffer.append('>');
                } else
                {
                    stringbuffer.append(toString(obj3, s4));
                }
                l++;
            }
        } else
        if(obj2.equals(""))
        {
            stringbuffer.append('<');
            stringbuffer.append(s4);
            stringbuffer.append("/>");
        } else
        {
            stringbuffer.append(toString(obj2, s4));
        }
          goto _L5
_L2:
        JSONArray jsonarray;
        int i;
        int j;
        if(!(obj instanceof JSONArray))
            break MISSING_BLOCK_LABEL_487;
        jsonarray = (JSONArray)obj;
        i = jsonarray.length();
        j = 0;
_L7:
label0:
        {
            if(j < i)
                break label0;
            s2 = stringbuffer.toString();
        }
          goto _L6
        Object obj1 = jsonarray.opt(j);
        String s3;
        if(s == null)
            s3 = "array";
        else
            s3 = s;
        stringbuffer.append(toString(obj1, s3));
        j++;
          goto _L7
        String s1;
        if(obj == null)
            s1 = "null";
        else
            s1 = escape(obj.toString());
        if(s == null)
            s2 = (new StringBuilder("\"")).append(s1).append("\"").toString();
        else
        if(s1.length() == 0)
            s2 = (new StringBuilder("<")).append(s).append("/>").toString();
        else
            s2 = (new StringBuilder("<")).append(s).append(">").append(s1).append("</").append(s).append(">").toString();
          goto _L6
    }

    public static final Character AMP = new Character('&');
    public static final Character APOS = new Character('\'');
    public static final Character BANG = new Character('!');
    public static final Character EQ = new Character('=');
    public static final Character GT = new Character('>');
    public static final Character LT = new Character('<');
    public static final Character QUEST = new Character('?');
    public static final Character QUOT = new Character('"');
    public static final Character SLASH = new Character('/');

}
