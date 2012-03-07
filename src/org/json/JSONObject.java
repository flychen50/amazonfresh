// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package org.json:
//            JSONException, JSONTokener, JSONString, JSONArray

public class JSONObject
{
    private static final class Null
    {

        protected final Object clone()
        {
            return this;
        }

        public boolean equals(Object obj)
        {
            boolean flag;
            if(obj != null && obj != this)
                flag = false;
            else
                flag = true;
            return flag;
        }

        public String toString()
        {
            return "null";
        }

        private Null()
        {
        }

        Null(Null null1)
        {
            this();
        }
    }


    public JSONObject()
    {
        map = new HashMap();
    }

    public JSONObject(Object obj)
    {
        this();
        populateMap(obj);
    }

    public JSONObject(Object obj, String as[])
    {
        this();
        Class class1 = obj.getClass();
        int i = 0;
        do
        {
            if(i >= as.length)
                return;
            String s = as[i];
            try
            {
                putOpt(s, class1.getField(s).get(obj));
            }
            catch(Exception exception) { }
            i++;
        } while(true);
    }

    public JSONObject(String s)
        throws JSONException
    {
        this(new JSONTokener(s));
    }

    public JSONObject(Map map1)
    {
        map = new HashMap();
        if(map1 == null) goto _L2; else goto _L1
_L1:
        Iterator iterator = map1.entrySet().iterator();
_L5:
        if(iterator.hasNext()) goto _L3; else goto _L2
_L2:
        return;
_L3:
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        map.put(entry.getKey(), wrap(entry.getValue()));
        if(true) goto _L5; else goto _L4
_L4:
    }

    public JSONObject(JSONObject jsonobject, String as[])
    {
        this();
        int i = 0;
        do
        {
            if(i >= as.length)
                return;
            try
            {
                putOnce(as[i], jsonobject.opt(as[i]));
            }
            catch(Exception exception) { }
            i++;
        } while(true);
    }

    public JSONObject(JSONTokener jsontokener)
        throws JSONException
    {
        this();
        if(jsontokener.nextClean() != '{')
            throw jsontokener.syntaxError("A JSONObject text must begin with '{'");
          goto _L1
_L11:
        jsontokener.back();
_L1:
        jsontokener.nextClean();
        JVM INSTR lookupswitch 2: default 56
    //                   0: 147
    //                   125: 176;
           goto _L2 _L3 _L4
_L2:
        String s;
        char c;
        jsontokener.back();
        s = jsontokener.nextValue().toString();
        c = jsontokener.nextClean();
        if(c != '=') goto _L6; else goto _L5
_L5:
        if(jsontokener.next() != '>')
            jsontokener.back();
_L10:
        putOnce(s, jsontokener.nextValue());
        jsontokener.nextClean();
        JVM INSTR lookupswitch 3: default 140
    //                   44: 167
    //                   59: 167
    //                   125: 176;
           goto _L7 _L8 _L8 _L4
_L7:
        throw jsontokener.syntaxError("Expected a ',' or '}'");
_L3:
        throw jsontokener.syntaxError("A JSONObject text must end with '}'");
_L6:
        if(c == ':') goto _L10; else goto _L9
_L9:
        throw jsontokener.syntaxError("Expected a ':' after a key");
_L8:
        if(jsontokener.nextClean() != '}') goto _L11; else goto _L4
_L4:
    }

    public static String doubleToString(double d)
    {
        String s;
        if(Double.isInfinite(d) || Double.isNaN(d))
        {
            s = "null";
        } else
        {
label0:
            {
                String s1 = Double.toString(d);
                if(s1.indexOf('.') > 0 && s1.indexOf('e') < 0 && s1.indexOf('E') < 0)
                {
                    for(; s1.endsWith("0"); s1 = s1.substring(0, s1.length() - 1))
                        break label0;

                    if(s1.endsWith("."))
                        s1 = s1.substring(0, s1.length() - 1);
                }
                s = s1;
            }
        }
        return s;
    }

    public static String[] getNames(Object obj)
    {
        String as[] = null;
        if(obj != null) goto _L2; else goto _L1
_L1:
        return as;
_L2:
        Field afield[];
        int i;
        String as1[];
        int j;
        afield = obj.getClass().getFields();
        i = afield.length;
        if(i == 0)
            continue; /* Loop/switch isn't completed */
        as1 = new String[i];
        j = 0;
_L4:
label0:
        {
            if(j < i)
                break label0;
            as = as1;
        }
        if(true) goto _L1; else goto _L3
_L3:
        as1[j] = afield[j].getName();
        j++;
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    public static String[] getNames(JSONObject jsonobject)
    {
        int i = jsonobject.length();
        if(i != 0) goto _L2; else goto _L1
_L1:
        String as1[] = null;
_L4:
        return as1;
_L2:
        Iterator iterator = jsonobject.keys();
        String as[] = new String[i];
        int j = 0;
        do
        {
label0:
            {
                if(iterator.hasNext())
                    break label0;
                as1 = as;
            }
            if(true)
                continue;
            as[j] = (String)iterator.next();
            j++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String numberToString(Number number)
        throws JSONException
    {
        String s;
        if(number == null)
            throw new JSONException("Null pointer");
        testValidity(number);
        s = number.toString();
        if(s.indexOf('.') <= 0 || s.indexOf('e') >= 0 || s.indexOf('E') >= 0) goto _L2; else goto _L1
_L1:
        if(s.endsWith("0")) goto _L4; else goto _L3
_L3:
        if(s.endsWith("."))
            s = s.substring(0, s.length() - 1);
_L2:
        return s;
_L4:
        s = s.substring(0, s.length() - 1);
        if(true) goto _L1; else goto _L5
_L5:
    }

    private void populateMap(Object obj)
    {
        Method amethod[];
        int i;
        Class class1 = obj.getClass();
        boolean flag;
        if(class1.getClassLoader() != null)
            flag = true;
        else
            flag = false;
        if(flag)
            amethod = class1.getMethods();
        else
            amethod = class1.getDeclaredMethods();
        i = 0;
_L7:
        if(i >= amethod.length)
            return;
        String s1;
        Method method = amethod[i];
        if(!Modifier.isPublic(method.getModifiers()))
            break MISSING_BLOCK_LABEL_284;
        String s = method.getName();
        s1 = "";
        if(s.startsWith("get"))
        {
            Object obj1;
            if(s.equals("getClass") || s.equals("getDeclaringClass"))
                break MISSING_BLOCK_LABEL_277;
            s1 = s.substring(3);
        } else
        if(s.startsWith("is"))
            s1 = s.substring(2);
_L5:
        if(s1.length() <= 0 || !Character.isUpperCase(s1.charAt(0)) || method.getParameterTypes().length != 0)
            break MISSING_BLOCK_LABEL_284;
        if(s1.length() != 1) goto _L2; else goto _L1
_L1:
        s1 = s1.toLowerCase();
_L4:
        obj1 = method.invoke(obj, null);
        map.put(s1, wrap(obj1));
        break MISSING_BLOCK_LABEL_284;
_L2:
        if(Character.isUpperCase(s1.charAt(1))) goto _L4; else goto _L3
_L3:
        String s2 = (new StringBuilder(String.valueOf(s1.substring(0, 1).toLowerCase()))).append(s1.substring(1)).toString();
        s1 = s2;
          goto _L4
        Exception exception;
        exception;
        break MISSING_BLOCK_LABEL_284;
        s1 = "";
          goto _L5
        i++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static String quote(String s)
    {
        if(s != null && s.length() != 0) goto _L2; else goto _L1
_L1:
        String s1 = "\"\"";
_L4:
        return s1;
_L2:
        char c = '\0';
        int i = s.length();
        StringBuffer stringbuffer = new StringBuffer(i + 4);
        stringbuffer.append('"');
        int j = 0;
        do
        {
            if(j >= i)
            {
                stringbuffer.append('"');
                s1 = stringbuffer.toString();
                continue; /* Loop/switch isn't completed */
            }
label0:
            {
                {
                    int k = c;
                    c = s.charAt(j);
                    switch(c)
                    {
                    default:
                        if(c < ' ' || c >= '\200' && c < '\240' || c >= '\u2000' && c < '\u2100')
                        {
                            String s2 = (new StringBuilder("000")).append(Integer.toHexString(c)).toString();
                            stringbuffer.append((new StringBuilder("\\u")).append(s2.substring(s2.length() - 4)).toString());
                        } else
                        {
                            stringbuffer.append(c);
                        }
                        break;

                    case 8: // '\b'
                        break MISSING_BLOCK_LABEL_295;

                    case 9: // '\t'
                        break MISSING_BLOCK_LABEL_307;

                    case 10: // '\n'
                        break MISSING_BLOCK_LABEL_319;

                    case 12: // '\f'
                        break MISSING_BLOCK_LABEL_331;

                    case 13: // '\r'
                        break MISSING_BLOCK_LABEL_343;

                    case 34: // '"'
                    case 92: // '\\'
                        break label0;

                    case 47: // '/'
                        break MISSING_BLOCK_LABEL_270;
                    }
                }
                j++;
            }
        } while(true);
        stringbuffer.append('\\');
        stringbuffer.append(c);
        break MISSING_BLOCK_LABEL_246;
        if(true) goto _L4; else goto _L3
_L3:
        if(k == 60)
            stringbuffer.append('\\');
        stringbuffer.append(c);
        break MISSING_BLOCK_LABEL_246;
        stringbuffer.append("\\b");
        break MISSING_BLOCK_LABEL_246;
        stringbuffer.append("\\t");
        break MISSING_BLOCK_LABEL_246;
        stringbuffer.append("\\n");
        break MISSING_BLOCK_LABEL_246;
        stringbuffer.append("\\f");
        break MISSING_BLOCK_LABEL_246;
        stringbuffer.append("\\r");
        break MISSING_BLOCK_LABEL_246;
    }

    public static Object stringToValue(String s)
    {
        if(!s.equals("")) goto _L2; else goto _L1
_L1:
        Object obj = s;
_L4:
        return obj;
_L2:
        Long long1;
label0:
        {
            if(s.equalsIgnoreCase("true"))
            {
                obj = Boolean.TRUE;
                continue; /* Loop/switch isn't completed */
            }
            if(s.equalsIgnoreCase("false"))
            {
                obj = Boolean.FALSE;
                continue; /* Loop/switch isn't completed */
            }
            if(s.equalsIgnoreCase("null"))
            {
                obj = NULL;
                continue; /* Loop/switch isn't completed */
            }
            char c = s.charAt(0);
            if(c >= '0' && c <= '9' || c == '.' || c == '-' || c == '+')
            {
                if(c == '0' && s.length() > 2 && (s.charAt(1) == 'x' || s.charAt(1) == 'X'))
                    try
                    {
                        obj = new Integer(Integer.parseInt(s.substring(2), 16));
                        continue; /* Loop/switch isn't completed */
                    }
                    catch(Exception exception1) { }
                try
                {
                    if(s.indexOf('.') > -1 || s.indexOf('e') > -1 || s.indexOf('E') > -1)
                    {
                        obj = Double.valueOf(s);
                        continue; /* Loop/switch isn't completed */
                    }
                    long1 = new Long(s);
                    if(long1.longValue() == (long)long1.intValue())
                    {
                        obj = new Integer(long1.intValue());
                        continue; /* Loop/switch isn't completed */
                    }
                    break label0;
                }
                catch(Exception exception) { }
            }
            obj = s;
            continue; /* Loop/switch isn't completed */
        }
        obj = long1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    static void testValidity(Object obj)
        throws JSONException
    {
        if(obj != null)
            if(obj instanceof Double)
            {
                if(((Double)obj).isInfinite() || ((Double)obj).isNaN())
                    throw new JSONException("JSON does not allow non-finite numbers.");
            } else
            if((obj instanceof Float) && (((Float)obj).isInfinite() || ((Float)obj).isNaN()))
                throw new JSONException("JSON does not allow non-finite numbers.");
    }

    static String valueToString(Object obj)
        throws JSONException
    {
        String s;
        if(obj == null || obj.equals(null))
            s = "null";
        else
        if(obj instanceof JSONString)
        {
            String s1;
            try
            {
                s1 = ((JSONString)obj).toJSONString();
            }
            catch(Exception exception)
            {
                throw new JSONException(exception);
            }
            if(s1 instanceof String)
                s = (String)s1;
            else
                throw new JSONException((new StringBuilder("Bad value from toJSONString: ")).append(s1).toString());
        } else
        if(obj instanceof Number)
            s = numberToString((Number)obj);
        else
        if((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray))
            s = obj.toString();
        else
        if(obj instanceof Map)
            s = (new JSONObject((Map)obj)).toString();
        else
        if(obj instanceof Collection)
            s = (new JSONArray((Collection)obj)).toString();
        else
        if(obj.getClass().isArray())
            s = (new JSONArray(obj)).toString();
        else
            s = quote(obj.toString());
        return s;
    }

    static String valueToString(Object obj, int i, int j)
        throws JSONException
    {
        if(obj != null && !obj.equals(null)) goto _L2; else goto _L1
_L1:
        String s = "null";
_L4:
        return s;
_L2:
        String s2;
        if(!(obj instanceof JSONString))
            break MISSING_BLOCK_LABEL_58;
        String s1 = ((JSONString)obj).toJSONString();
        if(!(s1 instanceof String))
            break MISSING_BLOCK_LABEL_58;
        s2 = (String)s1;
        s = s2;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(obj instanceof Number)
            s = numberToString((Number)obj);
        else
        if(obj instanceof Boolean)
            s = obj.toString();
        else
        if(obj instanceof JSONObject)
            s = ((JSONObject)obj).toString(i, j);
        else
        if(obj instanceof JSONArray)
            s = ((JSONArray)obj).toString(i, j);
        else
        if(obj instanceof Map)
            s = (new JSONObject((Map)obj)).toString(i, j);
        else
        if(obj instanceof Collection)
            s = (new JSONArray((Collection)obj)).toString(i, j);
        else
        if(obj.getClass().isArray())
            s = (new JSONArray(obj)).toString(i, j);
        else
            s = quote(obj.toString());
        if(true) goto _L4; else goto _L3
_L3:
    }

    static Object wrap(Object obj)
    {
        if(obj != null) goto _L2; else goto _L1
_L1:
        Object obj1 = NULL;
          goto _L3
_L2:
        if(!(obj instanceof JSONObject) && !(obj instanceof JSONArray) && !NULL.equals(obj) && !(obj instanceof JSONString) && !(obj instanceof Byte) && !(obj instanceof Character) && !(obj instanceof Short) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Boolean) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof String)) goto _L5; else goto _L4
_L5:
        if(!(obj instanceof Collection)) goto _L7; else goto _L6
_L6:
        obj1 = new JSONArray((Collection)obj);
          goto _L3
_L7:
        if(!obj.getClass().isArray()) goto _L9; else goto _L8
_L8:
        obj1 = new JSONArray(obj);
          goto _L3
_L9:
        if(!(obj instanceof Map)) goto _L11; else goto _L10
_L10:
        obj1 = new JSONObject((Map)obj);
          goto _L3
_L11:
        Package package1 = obj.getClass().getPackage();
        if(package1 == null) goto _L13; else goto _L12
_L12:
        String s = package1.getName();
_L14:
        if(s.startsWith("java.") || s.startsWith("javax.") || obj.getClass().getClassLoader() == null)
            obj1 = obj.toString();
        else
            obj1 = new JSONObject(obj);
_L3:
        return obj1;
_L4:
        obj1 = obj;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        obj1 = null;
        if(true) goto _L3; else goto _L13
_L13:
        s = "";
          goto _L14
    }

    public JSONObject accumulate(String s, Object obj)
        throws JSONException
    {
        testValidity(obj);
        Object obj1 = opt(s);
        if(obj1 == null)
        {
            Object obj2;
            if(obj instanceof JSONArray)
                obj2 = (new JSONArray()).put(obj);
            else
                obj2 = obj;
            put(s, obj2);
        } else
        if(obj1 instanceof JSONArray)
            ((JSONArray)obj1).put(obj);
        else
            put(s, (new JSONArray()).put(obj1).put(obj));
        return this;
    }

    public JSONObject append(String s, Object obj)
        throws JSONException
    {
        testValidity(obj);
        Object obj1 = opt(s);
        if(obj1 == null)
            put(s, (new JSONArray()).put(obj));
        else
        if(obj1 instanceof JSONArray)
            put(s, ((JSONArray)obj1).put(obj));
        else
            throw new JSONException((new StringBuilder("JSONObject[")).append(s).append("] is not a JSONArray.").toString());
        return this;
    }

    public Object get(String s)
        throws JSONException
    {
        Object obj = opt(s);
        if(obj == null)
            throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] not found.").toString());
        else
            return obj;
    }

    public boolean getBoolean(String s)
        throws JSONException
    {
        Object obj = get(s);
        boolean flag;
        if(obj.equals(Boolean.FALSE) || (obj instanceof String) && ((String)obj).equalsIgnoreCase("false"))
            flag = false;
        else
        if(obj.equals(Boolean.TRUE) || (obj instanceof String) && ((String)obj).equalsIgnoreCase("true"))
            flag = true;
        else
            throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not a Boolean.").toString());
        return flag;
    }

    public double getDouble(String s)
        throws JSONException
    {
        double d1;
label0:
        {
            Object obj = get(s);
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
                throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not a number.").toString());
            }
            d1 = d;
        }
        return d1;
    }

    public int getInt(String s)
        throws JSONException
    {
        int j;
label0:
        {
            Object obj = get(s);
            int i;
            try
            {
                if(obj instanceof Number)
                {
                    j = ((Number)obj).intValue();
                    break label0;
                }
                i = Integer.parseInt((String)obj);
            }
            catch(Exception exception)
            {
                throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not an int.").toString());
            }
            j = i;
        }
        return j;
    }

    public JSONArray getJSONArray(String s)
        throws JSONException
    {
        Object obj = get(s);
        if(obj instanceof JSONArray)
            return (JSONArray)obj;
        else
            throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not a JSONArray.").toString());
    }

    public JSONObject getJSONObject(String s)
        throws JSONException
    {
        Object obj = get(s);
        if(obj instanceof JSONObject)
            return (JSONObject)obj;
        else
            throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not a JSONObject.").toString());
    }

    public long getLong(String s)
        throws JSONException
    {
        long l1;
label0:
        {
            Object obj = get(s);
            long l;
            try
            {
                if(obj instanceof Number)
                {
                    l1 = ((Number)obj).longValue();
                    break label0;
                }
                l = Long.parseLong((String)obj);
            }
            catch(Exception exception)
            {
                throw new JSONException((new StringBuilder("JSONObject[")).append(quote(s)).append("] is not a long.").toString());
            }
            l1 = l;
        }
        return l1;
    }

    public String getString(String s)
        throws JSONException
    {
        return get(s).toString();
    }

    public boolean has(String s)
    {
        return map.containsKey(s);
    }

    public JSONObject increment(String s)
        throws JSONException
    {
        Object obj = opt(s);
        if(obj == null)
            put(s, 1);
        else
        if(obj instanceof Integer)
            put(s, 1 + ((Integer)obj).intValue());
        else
        if(obj instanceof Long)
            put(s, 1L + ((Long)obj).longValue());
        else
        if(obj instanceof Double)
            put(s, 1.0D + ((Double)obj).doubleValue());
        else
        if(obj instanceof Float)
            put(s, 1.0F + ((Float)obj).floatValue());
        else
            throw new JSONException((new StringBuilder("Unable to increment [")).append(s).append("].").toString());
        return this;
    }

    public boolean isNull(String s)
    {
        return NULL.equals(opt(s));
    }

    public Iterator keys()
    {
        return map.keySet().iterator();
    }

    public int length()
    {
        return map.size();
    }

    public JSONArray names()
    {
        JSONArray jsonarray = new JSONArray();
        Iterator iterator = keys();
        do
        {
            if(!iterator.hasNext())
            {
                JSONArray jsonarray1;
                if(jsonarray.length() == 0)
                    jsonarray1 = null;
                else
                    jsonarray1 = jsonarray;
                return jsonarray1;
            }
            jsonarray.put(iterator.next());
        } while(true);
    }

    public Object opt(String s)
    {
        Object obj;
        if(s == null)
            obj = null;
        else
            obj = map.get(s);
        return obj;
    }

    public boolean optBoolean(String s)
    {
        return optBoolean(s, false);
    }

    public boolean optBoolean(String s, boolean flag)
    {
        boolean flag2 = getBoolean(s);
        boolean flag1 = flag2;
_L2:
        return flag1;
        Exception exception;
        exception;
        flag1 = flag;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public double optDouble(String s)
    {
        return optDouble(s, (0.0D / 0.0D));
    }

    public double optDouble(String s, double d)
    {
        double d1;
        double d2;
        Object obj = opt(s);
        if(obj instanceof Number)
        {
            d1 = ((Number)obj).doubleValue();
            break MISSING_BLOCK_LABEL_57;
        }
        d2 = (new Double((String)obj)).doubleValue();
        d1 = d2;
        break MISSING_BLOCK_LABEL_57;
        Exception exception;
        exception;
        d1 = d;
        return d1;
    }

    public int optInt(String s)
    {
        return optInt(s, 0);
    }

    public int optInt(String s, int i)
    {
        int k = getInt(s);
        int j = k;
_L2:
        return j;
        Exception exception;
        exception;
        j = i;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public JSONArray optJSONArray(String s)
    {
        Object obj = opt(s);
        JSONArray jsonarray;
        if(obj instanceof JSONArray)
            jsonarray = (JSONArray)obj;
        else
            jsonarray = null;
        return jsonarray;
    }

    public JSONObject optJSONObject(String s)
    {
        Object obj = opt(s);
        JSONObject jsonobject;
        if(obj instanceof JSONObject)
            jsonobject = (JSONObject)obj;
        else
            jsonobject = null;
        return jsonobject;
    }

    public long optLong(String s)
    {
        return optLong(s, 0L);
    }

    public long optLong(String s, long l)
    {
        long l2 = getLong(s);
        long l1 = l2;
_L2:
        return l1;
        Exception exception;
        exception;
        l1 = l;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String optString(String s)
    {
        return optString(s, "");
    }

    public String optString(String s, String s1)
    {
        Object obj = opt(s);
        String s2;
        if(obj != null)
            s2 = obj.toString();
        else
            s2 = s1;
        return s2;
    }

    public JSONObject put(String s, double d)
        throws JSONException
    {
        put(s, new Double(d));
        return this;
    }

    public JSONObject put(String s, int i)
        throws JSONException
    {
        put(s, new Integer(i));
        return this;
    }

    public JSONObject put(String s, long l)
        throws JSONException
    {
        put(s, new Long(l));
        return this;
    }

    public JSONObject put(String s, Object obj)
        throws JSONException
    {
        if(s == null)
            throw new JSONException("Null key.");
        if(obj != null)
        {
            testValidity(obj);
            map.put(s, obj);
        } else
        {
            remove(s);
        }
        return this;
    }

    public JSONObject put(String s, Collection collection)
        throws JSONException
    {
        put(s, new JSONArray(collection));
        return this;
    }

    public JSONObject put(String s, Map map1)
        throws JSONException
    {
        put(s, new JSONObject(map1));
        return this;
    }

    public JSONObject put(String s, boolean flag)
        throws JSONException
    {
        Boolean boolean1;
        if(flag)
            boolean1 = Boolean.TRUE;
        else
            boolean1 = Boolean.FALSE;
        put(s, boolean1);
        return this;
    }

    public JSONObject putOnce(String s, Object obj)
        throws JSONException
    {
        if(s != null && obj != null)
        {
            if(opt(s) != null)
                throw new JSONException((new StringBuilder("Duplicate key \"")).append(s).append("\"").toString());
            put(s, obj);
        }
        return this;
    }

    public JSONObject putOpt(String s, Object obj)
        throws JSONException
    {
        if(s != null && obj != null)
            put(s, obj);
        return this;
    }

    public Object remove(String s)
    {
        return map.remove(s);
    }

    public Iterator sortedKeys()
    {
        return (new TreeSet(map.keySet())).iterator();
    }

    public JSONArray toJSONArray(JSONArray jsonarray)
        throws JSONException
    {
        if(jsonarray != null && jsonarray.length() != 0) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray1 = null;
_L4:
        return jsonarray1;
_L2:
        JSONArray jsonarray2 = new JSONArray();
        int i = 0;
        do
        {
label0:
            {
                if(i < jsonarray.length())
                    break label0;
                jsonarray1 = jsonarray2;
            }
            if(true)
                continue;
            jsonarray2.put(opt(jsonarray.getString(i)));
            i++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String toString()
    {
        String s;
        try
        {
            Iterator iterator = keys();
            StringBuffer stringbuffer = new StringBuffer("{");
            do
            {
                if(!iterator.hasNext())
                {
                    stringbuffer.append('}');
                    s = stringbuffer.toString();
                    break;
                }
                if(stringbuffer.length() > 1)
                    stringbuffer.append(',');
                Object obj = iterator.next();
                stringbuffer.append(quote(obj.toString()));
                stringbuffer.append(':');
                stringbuffer.append(valueToString(map.get(obj)));
            } while(true);
        }
        catch(Exception exception)
        {
            s = null;
        }
        return s;
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
        String s = "{}";
_L5:
        return s;
_L2:
        Iterator iterator;
        StringBuffer stringbuffer;
        int l;
        iterator = sortedKeys();
        stringbuffer = new StringBuffer("{");
        l = j + i;
        if(k != 1) goto _L4; else goto _L3
_L3:
        Object obj1 = iterator.next();
        stringbuffer.append(quote(obj1.toString()));
        stringbuffer.append(": ");
        stringbuffer.append(valueToString(map.get(obj1), i, j));
_L8:
        stringbuffer.append('}');
        s = stringbuffer.toString();
          goto _L5
_L7:
        int j1;
        Object obj = iterator.next();
        int i1;
        if(stringbuffer.length() > 1)
            stringbuffer.append(",\n");
        else
            stringbuffer.append('\n');
        j1 = 0;
_L9:
        if(j1 < l)
            break MISSING_BLOCK_LABEL_261;
        stringbuffer.append(quote(obj.toString()));
        stringbuffer.append(": ");
        stringbuffer.append(valueToString(map.get(obj), i, l));
_L4:
        if(iterator.hasNext()) goto _L7; else goto _L6
_L6:
        if(stringbuffer.length() > 1)
        {
            stringbuffer.append('\n');
            i1 = 0;
            while(i1 < j) 
            {
                stringbuffer.append(' ');
                i1++;
            }
        }
          goto _L8
        stringbuffer.append(' ');
        j1++;
          goto _L9
    }

    public Writer write(Writer writer)
        throws JSONException
    {
        boolean flag = false;
        Iterator iterator;
        iterator = keys();
        writer.write(123);
_L2:
        if(!iterator.hasNext())
        {
            writer.write(125);
            return writer;
        }
        if(flag)
            writer.write(44);
        Object obj = iterator.next();
        writer.write(quote(obj.toString()));
        writer.write(58);
        Object obj1 = map.get(obj);
        if(obj1 instanceof JSONObject)
        {
            ((JSONObject)obj1).write(writer);
            break MISSING_BLOCK_LABEL_143;
        }
        IOException ioexception;
        if(obj1 instanceof JSONArray)
        {
            ((JSONArray)obj1).write(writer);
            break MISSING_BLOCK_LABEL_143;
        }
        try
        {
            writer.write(valueToString(obj1));
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            throw new JSONException(ioexception);
        }
        flag = true;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static final Object NULL = new Null(null);
    private Map map;

}
