// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class LinkAPICall extends FreshAPICall
{

    public LinkAPICall(AmazonFreshBase amazonfreshbase, Class class1, String s)
    {
        super(amazonfreshbase);
        type = class1;
        if(s.startsWith("/api/"))
            s = s.replaceAll("^/api", "");
        if(s.startsWith("/str/api/"))
            s = s.replaceAll("^/str/api", "");
        url = s;
    }

    public FreshAPICall get()
    {
        if(error != null)
            throw error;
        if(object == null) goto _L2; else goto _L1
_L1:
        FreshAPICall freshapicall1 = object;
_L4:
        return freshapicall1;
_L2:
        FreshAPICall freshapicall;
        try
        {
            Class class1 = type;
            Class aclass[] = new Class[2];
            aclass[0] = com/demiroot/freshclient/AmazonFreshBase;
            aclass[1] = java/lang/String;
            Constructor constructor = class1.getConstructor(aclass);
            Object aobj[] = new Object[2];
            aobj[0] = amazonFreshBase;
            aobj[1] = url;
            freshapicall = (FreshAPICall)constructor.newInstance(aobj);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            if(invocationtargetexception.getCause() != null && (invocationtargetexception.getCause() instanceof RuntimeException))
            {
                error = (RuntimeException)invocationtargetexception.getCause();
                throw error;
            } else
            {
                error = new RuntimeException(invocationtargetexception);
                throw error;
            }
        }
        catch(RuntimeException runtimeexception)
        {
            error = runtimeexception;
            throw runtimeexception;
        }
        catch(Exception exception)
        {
            error = new RuntimeException(exception);
            throw error;
        }
        freshapicall1 = freshapicall;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public List test()
    {
        return null;
    }

    RuntimeException error;
    FreshAPICall object;
    Class type;
    String url;
}
