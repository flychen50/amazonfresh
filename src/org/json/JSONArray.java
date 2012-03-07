// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.*;

// Referenced classes of package org.json:
//            JSONException, JSONObject, JSONTokener

public class JSONArray
{

    public JSONArray()
    {
        myArrayList = new ArrayList();
    }

    public JSONArray(Object obj)
        throws JSONException
    {
        this();
        if(obj.getClass().isArray())
        {
            int i = Array.getLength(obj);
            int j = 0;
            do
            {
                if(j >= i)
                    return;
                put(JSONObject.wrap(Array.get(obj, j)));
                j++;
            } while(true);
        } else
        {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
    }

    public JSONArray(String s)
        throws JSONException
    {
        this(new JSONTokener(s));
    }

    public JSONArray(Collection collection)
    {
        myArrayList = new ArrayList();
        if(collection == null) goto _L2; else goto _L1
_L1:
        Iterator iterator = collection.iterator();
_L5:
        if(iterator.hasNext()) goto _L3; else goto _L2
_L2:
        return;
_L3:
        Object obj = iterator.next();
        myArrayList.add(JSONObject.wrap(obj));
        if(true) goto _L5; else goto _L4
_L4:
    }

    public JSONArray(JSONTokener jsontokener)
        throws JSONException
    {
        char c1;
        this();
        char c = jsontokener.nextClean();
        if(c == '[')
            c1 = ']';
        else
        if(c == '(')
            c1 = ')';
        else
            throw jsontokener.syntaxError("A JSONArray text must start with '['");
        if(jsontokener.nextClean() != ']') goto _L2; else goto _L1
_L1:
        return;
_L2:
        jsontokener.back();
_L7:
        char c2;
        if(jsontokener.nextClean() == ',')
        {
            jsontokener.back();
            myArrayList.add(null);
        } else
        {
            jsontokener.back();
            myArrayList.add(jsontokener.nextValue());
        }
        c2 = jsontokener.nextClean();
        c2;
        JVM INSTR lookupswitch 4: default 124
    //                   41: 166
    //                   44: 150
    //                   59: 150
    //                   93: 166;
           goto _L3 _L4 _L5 _L5 _L4
_L4:
        continue; /* Loop/switch isn't completed */
_L3:
        throw jsontokener.syntaxError("Expected a ',' or ']'");
_L5:
        if(jsontokener.nextClean() == ']') goto _L1; else goto _L6
_L6:
        jsontokener.back();
          goto _L7
        if(c1 == c2) goto _L1; else goto _L8
_L8:
        throw jsontokener.syntaxError((new StringBuilder("Expected a '")).append(new Character(c1)).append("'").toString());
    }

    public Object get(int i)
        throws JSONException
    {
        Object obj = opt(i);
        if(obj == null)
            throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] not found.").toString());
        else
            return obj;
    }

    public boolean getBoolean(int i)
        throws JSONException
    {
        Object obj = get(i);
        boolean flag;
        if(obj.equals(Boolean.FALSE) || (obj instanceof String) && ((String)obj).equalsIgnoreCase("false"))
            flag = false;
        else
        if(obj.equals(Boolean.TRUE) || (obj instanceof String) && ((String)obj).equalsIgnoreCase("true"))
            flag = true;
        else
            throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] is not a Boolean.").toString());
        return flag;
    }

    public double getDouble(int i)
        throws JSONException
    {
        double d1;
label0:
        {
            Object obj = get(i);
            double d;
            try
            {
                if(obj instanceof Number)
                {
                    d1 = ((Number)obj).doubleValue();
                    break label0;
                }
                d = Double.valueOf((String)obj).doubleValue();
            }
            catch(Exception exception)
            {
                throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] is not a number.").toString());
            }
            d1 = d;
        }
        return d1;
    }

    public int getInt(int i)
        throws JSONException
    {
        Object obj = get(i);
        int j;
        if(obj instanceof Number)
            j = ((Number)obj).intValue();
        else
            j = (int)getDouble(i);
        return j;
    }

    public JSONArray getJSONArray(int i)
        throws JSONException
    {
        Object obj = get(i);
        if(obj instanceof JSONArray)
            return (JSONArray)obj;
        else
            throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] is not a JSONArray.").toString());
    }

    public JSONObject getJSONObject(int i)
        throws JSONException
    {
        Object obj = get(i);
        if(obj instanceof JSONObject)
            return (JSONObject)obj;
        else
            throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] is not a JSONObject.").toString());
    }

    public long getLong(int i)
        throws JSONException
    {
        Object obj = get(i);
        long l;
        if(obj instanceof Number)
            l = ((Number)obj).longValue();
        else
            l = (long)getDouble(i);
        return l;
    }

    public String getString(int i)
        throws JSONException
    {
        return get(i).toString();
    }

    public boolean isNull(int i)
    {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String s)
        throws JSONException
    {
        int i = length();
        StringBuffer stringbuffer = new StringBuffer();
        int j = 0;
        do
        {
            if(j >= i)
                return stringbuffer.toString();
            if(j > 0)
                stringbuffer.append(s);
            stringbuffer.append(JSONObject.valueToString(myArrayList.get(j)));
            j++;
        } while(true);
    }

    public int length()
    {
        return myArrayList.size();
    }

    public Object opt(int i)
    {
        Object obj;
        if(i < 0 || i >= length())
            obj = null;
        else
            obj = myArrayList.get(i);
        return obj;
    }

    public boolean optBoolean(int i)
    {
        return optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean flag)
    {
        boolean flag2 = getBoolean(i);
        boolean flag1 = flag2;
_L2:
        return flag1;
        Exception exception;
        exception;
        flag1 = flag;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public double optDouble(int i)
    {
        return optDouble(i, (0.0D / 0.0D));
    }

    public double optDouble(int i, double d)
    {
        double d2 = getDouble(i);
        double d1 = d2;
_L2:
        return d1;
        Exception exception;
        exception;
        d1 = d;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public int optInt(int i)
    {
        return optInt(i, 0);
    }

    public int optInt(int i, int j)
    {
        int l = getInt(i);
        int k = l;
_L2:
        return k;
        Exception exception;
        exception;
        k = j;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public JSONArray optJSONArray(int i)
    {
        Object obj = opt(i);
        JSONArray jsonarray;
        if(obj instanceof JSONArray)
            jsonarray = (JSONArray)obj;
        else
            jsonarray = null;
        return jsonarray;
    }

    public JSONObject optJSONObject(int i)
    {
        Object obj = opt(i);
        JSONObject jsonobject;
        if(obj instanceof JSONObject)
            jsonobject = (JSONObject)obj;
        else
            jsonobject = null;
        return jsonobject;
    }

    public long optLong(int i)
    {
        return optLong(i, 0L);
    }

    public long optLong(int i, long l)
    {
        long l2 = getLong(i);
        long l1 = l2;
_L2:
        return l1;
        Exception exception;
        exception;
        l1 = l;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String optString(int i)
    {
        return optString(i, "");
    }

    public String optString(int i, String s)
    {
        Object obj = opt(i);
        String s1;
        if(obj != null)
            s1 = obj.toString();
        else
            s1 = s;
        return s1;
    }

    public JSONArray put(double d)
        throws JSONException
    {
        Double double1 = new Double(d);
        JSONObject.testValidity(double1);
        put(double1);
        return this;
    }

    public JSONArray put(int i)
    {
        put(new Integer(i));
        return this;
    }

    public JSONArray put(int i, double d)
        throws JSONException
    {
        put(i, new Double(d));
        return this;
    }

    public JSONArray put(int i, int j)
        throws JSONException
    {
        put(i, new Integer(j));
        return this;
    }

    public JSONArray put(int i, long l)
        throws JSONException
    {
        put(i, new Long(l));
        return this;
    }

    public JSONArray put(int i, Object obj)
        throws JSONException
    {
        JSONObject.testValidity(obj);
        if(i < 0)
            throw new JSONException((new StringBuilder("JSONArray[")).append(i).append("] not found.").toString());
        if(i < length())
        {
            myArrayList.set(i, obj);
        } else
        {
            for(; i != length(); put(JSONObject.NULL));
            put(obj);
        }
        return this;
    }

    public JSONArray put(int i, Collection collection)
        throws JSONException
    {
        put(i, new JSONArray(collection));
        return this;
    }

    public JSONArray put(int i, Map map)
        throws JSONException
    {
        put(i, new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, boolean flag)
        throws JSONException
    {
        Boolean boolean1;
        if(flag)
            boolean1 = Boolean.TRUE;
        else
            boolean1 = Boolean.FALSE;
        put(i, boolean1);
        return this;
    }

    public JSONArray put(long l)
    {
        put(new Long(l));
        return this;
    }

    public JSONArray put(Object obj)
    {
        myArrayList.add(obj);
        return this;
    }

    public JSONArray put(Collection collection)
    {
        put(new JSONArray(collection));
        return this;
    }

    public JSONArray put(Map map)
    {
        put(new JSONObject(map));
        return this;
    }

    public JSONArray put(boolean flag)
    {
        Boolean boolean1;
        if(flag)
            boolean1 = Boolean.TRUE;
        else
            boolean1 = Boolean.FALSE;
        put(boolean1);
        return this;
    }

    public Object remove(int i)
    {
        Object obj = opt(i);
        myArrayList.remove(i);
        return obj;
    }

    public JSONObject toJSONObject(JSONArray jsonarray)
        throws JSONException
    {
        if(jsonarray != null && jsonarray.length() != 0 && length() != 0) goto _L2; else goto _L1
_L1:
        JSONObject jsonobject = null;
_L4:
        return jsonobject;
_L2:
        JSONObject jsonobject1 = new JSONObject();
        int i = 0;
        do
        {
label0:
            {
                if(i < jsonarray.length())
                    break label0;
                jsonobject = jsonobject1;
            }
            if(true)
                continue;
            jsonobject1.put(jsonarray.getString(i), opt(i));
            i++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String toString()
    {
        String s1 = (new StringBuilder(String.valueOf('['))).append(join(",")).append(']').toString();
        String s = s1;
_L2:
        return s;
        Exception exception;
        exception;
        s = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String toString(int i)
        throws JSONException
    {
        return toString(i, 0);
    }

    String toString(int i, int j)
        throws JSONException
    {
        int k = length();
        if(k != 0) goto _L2; else goto _L1
_L1:
        String s = "[]";
_L4:
        return s;
_L2:
        StringBuffer stringbuffer;
        stringbuffer = new StringBuffer("[");
        if(k != 1)
            break; /* Loop/switch isn't completed */
        stringbuffer.append(JSONObject.valueToString(myArrayList.get(0), i, j));
_L5:
        stringbuffer.append(']');
        s = stringbuffer.toString();
        if(true) goto _L4; else goto _L3
_L3:
        int l;
        int i1;
        l = j + i;
        stringbuffer.append('\n');
        i1 = 0;
_L6:
label0:
        {
            if(i1 < k)
                break label0;
            stringbuffer.append('\n');
            int k1 = 0;
            while(k1 < j) 
            {
                stringbuffer.append(' ');
                k1++;
            }
        }
          goto _L5
        int j1;
        if(i1 > 0)
            stringbuffer.append(",\n");
        j1 = 0;
_L7:
label1:
        {
            if(j1 < l)
                break label1;
            stringbuffer.append(JSONObject.valueToString(myArrayList.get(i1), i, l));
            i1++;
        }
          goto _L6
        stringbuffer.append(' ');
        j1++;
          goto _L7
    }

    public Writer write(Writer writer)
        throws JSONException
    {
        boolean flag = false;
        int i;
        int j;
        i = length();
        writer.write(91);
        j = 0;
_L2:
        if(j >= i)
        {
            writer.write(93);
            return writer;
        }
        if(flag)
            writer.write(44);
        Object obj = myArrayList.get(j);
        if(obj instanceof JSONObject)
        {
            ((JSONObject)obj).write(writer);
            break MISSING_BLOCK_LABEL_114;
        }
        IOException ioexception;
        if(obj instanceof JSONArray)
        {
            ((JSONArray)obj).write(writer);
            break MISSING_BLOCK_LABEL_114;
        }
        try
        {
            writer.write(JSONObject.valueToString(obj));
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            throw new JSONException(ioexception);
        }
        flag = true;
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private ArrayList myArrayList;
}
