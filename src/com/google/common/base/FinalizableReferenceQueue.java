// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.common.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package com.google.common.base:
//            FinalizableReference

public class FinalizableReferenceQueue
{
    static class DecoupledLoader
        implements FinalizerLoader
    {

        URL getBaseUrl()
            throws IOException
        {
            String s = (new StringBuilder(String.valueOf("com.google.common.base.internal.Finalizer".replace('.', '/')))).append(".class").toString();
            URL url = getClass().getClassLoader().getResource(s);
            if(url == null)
                throw new FileNotFoundException(s);
            String s1 = url.toString();
            if(!s1.endsWith(s))
                throw new IOException((new StringBuilder("Unsupported path style: ")).append(s1).toString());
            else
                return new URL(url, s1.substring(0, s1.length() - s.length()));
        }

        public Class loadFinalizer()
        {
            Class class2 = newLoader(getBaseUrl()).loadClass("com.google.common.base.internal.Finalizer");
            Class class1 = class2;
_L2:
            return class1;
            Exception exception;
            exception;
            FinalizableReferenceQueue.logger.log(Level.WARNING, "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Google Collections to your system class path.", exception);
            class1 = null;
            if(true) goto _L2; else goto _L1
_L1:
        }

        URLClassLoader newLoader(URL url)
        {
            URL aurl[] = new URL[1];
            aurl[0] = url;
            return new URLClassLoader(aurl);
        }

        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Google Collections to your system class path.";

        DecoupledLoader()
        {
        }
    }

    static class DirectLoader
        implements FinalizerLoader
    {

        public Class loadFinalizer()
        {
            Class class1;
            try
            {
                class1 = Class.forName("com.google.common.base.internal.Finalizer");
            }
            catch(ClassNotFoundException classnotfoundexception)
            {
                throw new AssertionError(classnotfoundexception);
            }
            return class1;
        }

        DirectLoader()
        {
        }
    }

    static interface FinalizerLoader
    {

        public abstract Class loadFinalizer();
    }

    static class SystemLoader
        implements FinalizerLoader
    {

        public Class loadFinalizer()
        {
            Class class1;
            ClassLoader classloader;
            try
            {
                classloader = ClassLoader.getSystemClassLoader();
            }
            catch(SecurityException securityexception)
            {
                FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
                class1 = null;
                continue; /* Loop/switch isn't completed */
            }
            if(classloader == null) goto _L2; else goto _L1
_L1:
            Class class2;
            try
            {
                class2 = classloader.loadClass("com.google.common.base.internal.Finalizer");
            }
            catch(ClassNotFoundException classnotfoundexception)
            {
                class1 = null;
                continue; /* Loop/switch isn't completed */
            }
            class1 = class2;
_L4:
            return class1;
_L2:
            class1 = null;
            if(true) goto _L4; else goto _L3
_L3:
        }

        SystemLoader()
        {
        }
    }


    public FinalizableReferenceQueue()
    {
        boolean flag;
        flag = false;
        ReferenceQueue referencequeue;
        Method method = startFinalizer;
        Object aobj[] = new Object[2];
        aobj[0] = com/google/common/base/FinalizableReference;
        aobj[1] = this;
        referencequeue = (ReferenceQueue)method.invoke(null, aobj);
        flag = true;
_L2:
        queue = referencequeue;
        threadStarted = flag;
        return;
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        throw new AssertionError(illegalaccessexception);
        Throwable throwable;
        throwable;
        logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", throwable);
        referencequeue = new ReferenceQueue();
        if(true) goto _L2; else goto _L1
_L1:
    }

    static Method getStartFinalizer(Class class1)
    {
        Method method;
        try
        {
            Class aclass[] = new Class[2];
            aclass[0] = java/lang/Class;
            aclass[1] = java/lang/Object;
            method = class1.getMethod("startFinalizer", aclass);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new AssertionError(nosuchmethodexception);
        }
        return method;
    }

    private static transient Class loadFinalizer(FinalizerLoader afinalizerloader[])
    {
        int i = afinalizerloader.length;
        int j = 0;
        do
        {
            if(j >= i)
                throw new AssertionError();
            Class class1 = afinalizerloader[j].loadFinalizer();
            if(class1 != null)
                return class1;
            j++;
        } while(true);
    }

    void cleanUp()
    {
        if(!threadStarted) goto _L2; else goto _L1
_L1:
        return;
_L3:
        Reference reference;
        reference.clear();
        try
        {
            ((FinalizableReference)reference).finalizeReferent();
        }
        catch(Throwable throwable)
        {
            logger.log(Level.SEVERE, "Error cleaning up after reference.", throwable);
        }
_L2:
        reference = queue.poll();
        if(reference != null) goto _L3; else goto _L1
    }

    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    private static final Logger logger = Logger.getLogger(com/google/common/base/FinalizableReferenceQueue.getName());
    private static final Method startFinalizer;
    final ReferenceQueue queue;
    final boolean threadStarted;

    static 
    {
        FinalizerLoader afinalizerloader[] = new FinalizerLoader[3];
        afinalizerloader[0] = new SystemLoader();
        afinalizerloader[1] = new DecoupledLoader();
        afinalizerloader[2] = new DirectLoader();
        startFinalizer = getStartFinalizer(loadFinalizer(afinalizerloader));
    }

}
