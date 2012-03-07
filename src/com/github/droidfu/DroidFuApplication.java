// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu;

import android.app.Application;
import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class DroidFuApplication extends Application
{

    public DroidFuApplication()
    {
        contextObjects = new HashMap();
    }

    /**
     * @deprecated Method getActiveContext is deprecated
     */

    public Context getActiveContext(String s)
    {
        this;
        JVM INSTR monitorenter ;
        WeakReference weakreference = (WeakReference)contextObjects.get(s);
        if(weakreference != null) goto _L2; else goto _L1
_L1:
        Context context1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return context1;
_L2:
        Context context;
        context = (Context)weakreference.get();
        if(context == null)
            contextObjects.remove(s);
        context1 = context;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void onClose()
    {
    }

    /**
     * @deprecated Method resetActiveContext is deprecated
     */

    public void resetActiveContext(String s)
    {
        this;
        JVM INSTR monitorenter ;
        contextObjects.remove(s);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method setActiveContext is deprecated
     */

    public void setActiveContext(String s, Context context)
    {
        this;
        JVM INSTR monitorenter ;
        WeakReference weakreference = new WeakReference(context);
        contextObjects.put(s, weakreference);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private HashMap contextObjects;
}
