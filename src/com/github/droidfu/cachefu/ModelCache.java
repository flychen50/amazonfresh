// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.*;
import java.util.Iterator;
import java.util.Set;

// Referenced classes of package com.github.droidfu.cachefu:
//            AbstractCache, CacheHelper, CachedModel

public class ModelCache extends AbstractCache
{
    static class DescribedCachedModel
        implements Parcelable
    {

        public int describeContents()
        {
            return 0;
        }

        public CachedModel getCachedModel()
        {
            return cachedModel;
        }

        public void readFromParcel(Parcel parcel)
            throws IOException
        {
            String s = parcel.readString();
            if(s != null)
                try
                {
                    cachedModel = (CachedModel)parcel.readParcelable(Class.forName(s).getClassLoader());
                }
                catch(ClassNotFoundException classnotfoundexception)
                {
                    throw new IOException(classnotfoundexception.getMessage());
                }
        }

        public void setCachedModel(CachedModel cachedmodel)
        {
            cachedModel = cachedmodel;
        }

        public void writeToParcel(Parcel parcel, int i)
        {
            parcel.writeString(cachedModel.getClass().getCanonicalName());
            parcel.writeParcelable(cachedModel, i);
        }

        private CachedModel cachedModel;

        DescribedCachedModel()
        {
        }
    }


    public ModelCache(int i, long l, int j)
    {
        super("ModelCache", i, l, j);
        transactionCount = 0x8000000000000001L;
    }

    private void removeExpiredCache(final String keyPrefix)
    {
        final File cacheDir = new File(diskCacheDirectory);
        if(cacheDir.exists()) goto _L2; else goto _L1
_L1:
        class _cls1
            implements FilenameFilter
        {

            public boolean accept(File file, String s)
            {
                boolean flag;
                if(file.equals(cacheDir) && s.startsWith(getFileNameForKey(keyPrefix)))
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            final ModelCache this$0;
            private final File val$cacheDir;
            private final String val$keyPrefix;

            
            {
                this$0 = ModelCache.this;
                cacheDir = file;
                keyPrefix = s;
                super();
            }
        }

        File afile[];
        return;
_L2:
        if((afile = cacheDir.listFiles(new _cls1())) != null && afile.length != 0)
        {
            int i = afile.length;
            int j = 0;
            while(j < i) 
            {
                afile[j].delete();
                j++;
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public volatile String getFileNameForKey(Object obj)
    {
        return getFileNameForKey((String)obj);
    }

    public String getFileNameForKey(String s)
    {
        return CacheHelper.getFileNameFromUrl(s);
    }

    /**
     * @deprecated Method put is deprecated
     */

    public CachedModel put(String s, CachedModel cachedmodel)
    {
        this;
        JVM INSTR monitorenter ;
        CachedModel cachedmodel1;
        long l = transactionCount;
        transactionCount = 1L + l;
        cachedmodel.setTransactionId(l);
        cachedmodel1 = (CachedModel)super.put(s, cachedmodel);
        this;
        JVM INSTR monitorexit ;
        return cachedmodel1;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method put is deprecated
     */

    public volatile Object put(Object obj, Object obj1)
    {
        this;
        JVM INSTR monitorenter ;
        CachedModel cachedmodel = put((String)obj, (CachedModel)obj1);
        this;
        JVM INSTR monitorexit ;
        return cachedmodel;
        Exception exception;
        exception;
        throw exception;
    }

    protected CachedModel readValueFromDisk(File file)
        throws IOException
    {
        FileInputStream fileinputstream = new FileInputStream(file);
        byte abyte0[] = new byte[(int)file.length()];
        BufferedInputStream bufferedinputstream = new BufferedInputStream(fileinputstream);
        bufferedinputstream.read(abyte0);
        bufferedinputstream.close();
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(abyte0, 0, abyte0.length);
        parcel.setDataPosition(0);
        DescribedCachedModel describedcachedmodel = new DescribedCachedModel();
        describedcachedmodel.readFromParcel(parcel);
        return describedcachedmodel.getCachedModel();
    }

    protected volatile Object readValueFromDisk(File file)
        throws IOException
    {
        return readValueFromDisk(file);
    }

    /**
     * @deprecated Method removeAllWithPrefix is deprecated
     */

    public void removeAllWithPrefix(String s)
    {
        this;
        JVM INSTR monitorenter ;
        Iterator iterator = keySet().iterator();
_L1:
        if(iterator.hasNext())
            break MISSING_BLOCK_LABEL_36;
        if(isDiskCacheEnabled())
            removeExpiredCache(s);
        this;
        JVM INSTR monitorexit ;
        return;
        String s1 = (String)iterator.next();
        if(s1.startsWith(s))
            remove(s1);
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    protected void writeValueToDisk(File file, CachedModel cachedmodel)
        throws IOException
    {
        DescribedCachedModel describedcachedmodel = new DescribedCachedModel();
        describedcachedmodel.setCachedModel(cachedmodel);
        Parcel parcel = Parcel.obtain();
        describedcachedmodel.writeToParcel(parcel, 0);
        byte abyte0[] = parcel.marshall();
        (new BufferedOutputStream(new FileOutputStream(file))).write(abyte0);
    }

    protected volatile void writeValueToDisk(File file, Object obj)
        throws IOException
    {
        writeValueToDisk(file, (CachedModel)obj);
    }

    private long transactionCount;
}
