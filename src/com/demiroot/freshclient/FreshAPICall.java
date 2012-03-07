// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.io.Serializable;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.demiroot.freshclient:
//            LinkAPICall, APICallException, AmazonFreshBase, APIArgs, 
//            FreshClientProxy

public abstract class FreshAPICall
    implements Serializable
{

    public FreshAPICall(AmazonFreshBase amazonfreshbase)
    {
        amazonFreshBase = amazonfreshbase;
        callMethod = null;
        callUrl = null;
    }

    public FreshAPICall(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        amazonFreshBase = amazonfreshbase;
        callUrl = s;
        callMethod = s1;
    }

    private Object createList(Type type, JSONArray jsonarray)
        throws Exception
    {
        Type type1;
        LinkedList linkedlist;
        int i;
        if(!(type instanceof ParameterizedType))
            break MISSING_BLOCK_LABEL_77;
        type1 = ((ParameterizedType)type).getActualTypeArguments()[0];
        linkedlist = new LinkedList();
        i = 0;
_L3:
        if(i < jsonarray.length()) goto _L2; else goto _L1
_L1:
        Object obj = linkedlist;
_L4:
        return obj;
_L2:
        linkedlist.add(getValueForType((Class)type1, type1, jsonarray.get(i)));
        i++;
          goto _L3
        obj = PLACE_HOLDER;
          goto _L4
    }

    private Object getValueForType(Class class1, Type type, Object obj)
        throws Exception
    {
        int i = 0;
        if(!(obj instanceof String) || !class1.equals(java/lang/Integer) && !class1.equals(Integer.TYPE)) goto _L2; else goto _L1
_L1:
        Object obj1 = Integer.valueOf(Integer.parseInt((String)obj));
_L4:
        return obj1;
_L2:
        Class aclass1[];
        int j;
        if((obj instanceof String) && (class1.equals(java/lang/Double) || class1.equals(Double.TYPE)))
        {
            obj1 = Double.valueOf(Double.parseDouble((String)obj));
            continue; /* Loop/switch isn't completed */
        }
        if((obj instanceof Integer) && (class1.equals(Double.TYPE) || class1.equals(java/lang/Double)))
        {
            obj1 = Double.valueOf(((Integer)obj).doubleValue());
            continue; /* Loop/switch isn't completed */
        }
        if(class1.equals(java/lang/String) || class1.equals(Integer.TYPE) || class1.equals(Boolean.TYPE) || class1.equals(java/lang/Integer) || class1.equals(java/lang/Double))
        {
            obj1 = obj;
            continue; /* Loop/switch isn't completed */
        }
        if(class1.equals(Double.TYPE))
        {
            if(obj.getClass().equals(java/lang/Double))
                obj1 = obj;
            else
                obj1 = Double.valueOf(Double.parseDouble(obj.toString()));
            continue; /* Loop/switch isn't completed */
        }
        if(class1.equals(java/math/BigDecimal))
        {
            obj1 = new BigDecimal((String)obj);
            continue; /* Loop/switch isn't completed */
        }
        if(type.equals(java/util/Date))
        {
            Date date;
            try
            {
                date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse((String)obj);
            }
            catch(ParseException parseexception)
            {
                obj1 = (new SimpleDateFormat("yyyy-MM-dd")).parse((String)obj);
                continue; /* Loop/switch isn't completed */
            }
            obj1 = date;
            continue; /* Loop/switch isn't completed */
        }
        Class class2 = class1;
        do
        {
            if(class2 == null)
            {
                if(!class1.equals(java/util/List))
                    break;
                obj1 = createList(type, (JSONArray)obj);
                continue; /* Loop/switch isn't completed */
            }
            if(class2.equals(com/demiroot/freshclient/LinkAPICall))
            {
                if(type instanceof ParameterizedType)
                {
                    Class class3 = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
                    obj1 = new LinkAPICall(amazonFreshBase, class3, (String)obj);
                } else
                {
                    throw new APICallException("I have no idea");
                }
                continue; /* Loop/switch isn't completed */
            }
            if(class2.equals(com/demiroot/freshclient/FreshAPICall))
            {
                Class aclass[] = new Class[1];
                aclass[i] = com/demiroot/freshclient/AmazonFreshBase;
                Constructor constructor = class1.getConstructor(aclass);
                Object aobj[] = new Object[1];
                aobj[i] = amazonFreshBase;
                FreshAPICall freshapicall = (FreshAPICall)constructor.newInstance(aobj);
                freshapicall.loadFromJSON((JSONObject)obj);
                obj1 = freshapicall;
                continue; /* Loop/switch isn't completed */
            }
            class2 = class2.getSuperclass();
        } while(true);
        aclass1 = class1.getInterfaces();
        j = aclass1.length;
_L5:
        if(i >= j)
        {
            obj1 = PLACE_HOLDER;
        } else
        {
label0:
            {
                if(!aclass1[i].equals(java/util/List))
                    break label0;
                obj1 = createList(type, (JSONArray)obj);
            }
        }
        if(true) goto _L4; else goto _L3
_L3:
        i++;
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    private void load(String s, JSONObject jsonobject)
    {
        Field field = null;
        Class class1 = getClass();
_L5:
        if(!class1.equals(java/lang/Object)) goto _L2; else goto _L1
_L1:
        if(field == null) goto _L4; else goto _L3
_L3:
        Object obj;
        Object obj1;
        obj = getValueForType(field.getType(), field.getGenericType(), jsonobject.get(s));
        obj1 = PLACE_HOLDER;
        NoSuchFieldException nosuchfieldexception;
        Field field1;
        if(obj != obj1)
        {
            boolean flag = field.isAccessible();
            if(!flag)
                field.setAccessible(true);
            field.set(this, obj);
            if(!flag)
                field.setAccessible(false);
        }
_L4:
        return;
_L2:
label0:
        {
            if(!s.equals("children") || (jsonobject.get(s) instanceof JSONArray))
                break label0;
            field = class1.getDeclaredField("aisleChildren");
        }
          goto _L1
        field1 = class1.getDeclaredField(s);
        field = field1;
          goto _L1
        nosuchfieldexception;
        class1 = class1.getSuperclass();
          goto _L5
        Exception exception;
        exception;
          goto _L4
    }

    public void dropCookies()
    {
        amazonFreshBase.dropCookies();
    }

    protected void init()
    {
        init(new APIArgs(new Object[0]));
    }

    protected void init(APIArgs apiargs)
    {
        if(callMethod == null)
        {
            throw new APICallException("Called init on object that was not setup to be loaded");
        } else
        {
            loadFromJSON(amazonFreshBase.getClientProxy().request(callUrl, callMethod, apiargs));
            return;
        }
    }

    protected void loadFromJSON(JSONObject jsonobject)
    {
        if(jsonobject != null)
        {
            Iterator iterator = jsonobject.keys();
            while(iterator.hasNext()) 
                load((String)iterator.next(), jsonobject);
        }
    }

    public void setAmazonFreshBase(AmazonFreshBase amazonfreshbase)
    {
        amazonFreshBase = amazonfreshbase;
    }

    public static final String KEY_BROWSE_TREE_AISLECHILDREN = "aisleChildren";
    public static final String KEY_BROWSE_TREE_CHILDREN = "children";
    private static final Object PLACE_HOLDER = new Object();
    protected AmazonFreshBase amazonFreshBase;
    private String callMethod;
    private String callUrl;

}
