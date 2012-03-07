// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.github.droidfu.support.StringSupport;
import com.google.common.collect.MapMaker;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCache
    implements Map
{

    public AbstractCache(String s, int i, long l, int j)
    {
        name = s;
        MapMaker mapmaker = new MapMaker();
        mapmaker.initialCapacity(i);
        mapmaker.expiration(60L * l, TimeUnit.SECONDS);
        mapmaker.concurrencyLevel(j);
        mapmaker.softValues();
        cache = mapmaker.makeMap();
    }

    private void cacheToDisk(Object obj, Object obj1)
    {
        File file = new File((new StringBuilder(String.valueOf(diskCacheDirectory))).append("/").append(getFileNameForKey(obj)).toString());
        file.createNewFile();
        file.deleteOnExit();
        writeValueToDisk(file, obj1);
_L1:
        return;
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
        filenotfoundexception.printStackTrace();
          goto _L1
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
          goto _L1
    }

    private File getFileForKey(Object obj)
    {
        return new File((new StringBuilder(String.valueOf(diskCacheDirectory))).append("/").append(getFileNameForKey(obj)).toString());
    }

    private void setRootDir(String s)
    {
        diskCacheDirectory = (new StringBuilder(String.valueOf(s))).append("/cachefu/").append(StringSupport.underscore(name.replaceAll("\\s", ""))).toString();
    }

    /**
     * @deprecated Method clear is deprecated
     */

    public void clear()
    {
        this;
        JVM INSTR monitorenter ;
        cache.clear();
        if(!isDiskCacheEnabled) goto _L2; else goto _L1
_L1:
        File afile[] = (new File(diskCacheDirectory)).listFiles();
        if(afile != null) goto _L4; else goto _L3
_L3:
        this;
        JVM INSTR monitorexit ;
        return;
_L4:
        int i;
        int j;
        i = afile.length;
        j = 0;
          goto _L5
_L2:
        Log.d("Droid-Fu[CacheFu]", "Cache cleared");
        if(true) goto _L3; else goto _L6
_L6:
        Exception exception;
        exception;
        throw exception;
_L7:
        afile[j].delete();
        j++;
_L5:
        if(j < i) goto _L7; else goto _L2
    }

    /**
     * @deprecated Method containsKey is deprecated
     */

    public boolean containsKey(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        if(cache.containsKey(obj)) goto _L2; else goto _L1
_L1:
        if(!isDiskCacheEnabled) goto _L4; else goto _L3
_L3:
        boolean flag1 = getFileForKey(obj).exists();
        if(flag1) goto _L2; else goto _L4
_L4:
        boolean flag = false;
_L6:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        flag = true;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method containsKeyInMemory is deprecated
     */

    public boolean containsKeyInMemory(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = cache.containsKey(obj);
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method containsValue is deprecated
     */

    public boolean containsValue(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = cache.containsValue(obj);
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean enableDiskCache(Context context, int i)
    {
        Context context1 = context.getApplicationContext();
        if(i != 1 || !"mounted".equals(Environment.getExternalStorageState())) goto _L2; else goto _L1
_L1:
        String s = (new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()))).append("/Android/data/").append(context1.getPackageName()).append("/cache").toString();
_L5:
        setRootDir(s);
        File file1 = new File(diskCacheDirectory);
        if(file1.mkdirs())
        {
            File file2 = new File(diskCacheDirectory, ".nomedia");
            File file;
            boolean flag;
            try
            {
                file2.createNewFile();
            }
            catch(IOException ioexception)
            {
                Log.e("Droid-Fu[CacheFu]", "Failed creating .nomedia file");
            }
        }
        isDiskCacheEnabled = file1.exists();
        if(!isDiskCacheEnabled)
            Log.w("Droid-Fu[CacheFu]", (new StringBuilder("Failed creating disk cache directory ")).append(diskCacheDirectory).toString());
        else
            Log.d(name, (new StringBuilder("enabled write through to ")).append(diskCacheDirectory).toString());
        flag = isDiskCacheEnabled;
_L4:
        return flag;
_L2:
        file = context1.getCacheDir();
        if(file != null)
            break; /* Loop/switch isn't completed */
        flag = false;
        isDiskCacheEnabled = false;
        if(true) goto _L4; else goto _L3
_L3:
        s = file.getAbsolutePath();
          goto _L5
    }

    public Set entrySet()
    {
        return cache.entrySet();
    }

    /**
     * @deprecated Method get is deprecated
     */

    public Object get(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj1 = cache.get(obj);
        if(obj1 == null) goto _L2; else goto _L1
_L1:
        Log.d(name, (new StringBuilder("MEM cache hit for ")).append(obj.toString()).toString());
        Object obj2 = obj1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return obj2;
_L2:
        File file;
        file = getFileForKey(obj);
        if(!file.exists())
            break MISSING_BLOCK_LABEL_146;
        Log.d(name, (new StringBuilder("DISK cache hit for ")).append(obj.toString()).toString());
        Object obj3 = readValueFromDisk(file);
        if(obj3 == null)
        {
            obj2 = null;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_126;
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
        obj2 = null;
        continue; /* Loop/switch isn't completed */
        cache.put(obj, obj3);
        obj2 = obj3;
        continue; /* Loop/switch isn't completed */
        obj2 = null;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public String getDiskCacheDirectory()
    {
        return diskCacheDirectory;
    }

    public abstract String getFileNameForKey(Object obj);

    public boolean isDiskCacheEnabled()
    {
        return isDiskCacheEnabled;
    }

    /**
     * @deprecated Method isEmpty is deprecated
     */

    public boolean isEmpty()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = cache.isEmpty();
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    public Set keySet()
    {
        return cache.keySet();
    }

    /**
     * @deprecated Method put is deprecated
     */

    public Object put(Object obj, Object obj1)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj2;
        if(isDiskCacheEnabled)
            cacheToDisk(obj, obj1);
        obj2 = cache.put(obj, obj1);
        this;
        JVM INSTR monitorexit ;
        return obj2;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method putAll is deprecated
     */

    public void putAll(Map map)
    {
        this;
        JVM INSTR monitorenter ;
        throw new UnsupportedOperationException();
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected abstract Object readValueFromDisk(File file)
        throws IOException;

    /**
     * @deprecated Method remove is deprecated
     */

    public Object remove(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj1;
        obj1 = removeKey(obj);
        if(isDiskCacheEnabled)
        {
            File file = getFileForKey(obj);
            if(file.exists())
                file.delete();
        }
        this;
        JVM INSTR monitorexit ;
        return obj1;
        Exception exception;
        exception;
        throw exception;
    }

    public Object removeKey(Object obj)
    {
        return cache.remove(obj);
    }

    public void setDiskCacheEnabled(String s)
    {
        if(s != null && s.length() > 0)
        {
            setRootDir(s);
            isDiskCacheEnabled = true;
        } else
        {
            isDiskCacheEnabled = false;
        }
    }

    /**
     * @deprecated Method size is deprecated
     */

    public int size()
    {
        this;
        JVM INSTR monitorenter ;
        int i = cache.size();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public Collection values()
    {
        return cache.values();
    }

    protected abstract void writeValueToDisk(File file, Object obj)
        throws IOException;

    public static final int DISK_CACHE_INTERNAL = 0;
    public static final int DISK_CACHE_SDCARD = 1;
    private static final String LOG_TAG = "Droid-Fu[CacheFu]";
    private ConcurrentMap cache;
    protected String diskCacheDirectory;
    private boolean isDiskCacheEnabled;
    private String name;
}
