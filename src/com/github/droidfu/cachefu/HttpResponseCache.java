// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

// Referenced classes of package com.github.droidfu.cachefu:
//            AbstractCache, CacheHelper

public class HttpResponseCache extends AbstractCache
{

    public HttpResponseCache(int i, long l, int j)
    {
        super("HttpCache", i, l, j);
    }

    private void removeExpiredCache(final String urlPrefix)
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
                if(file.equals(cacheDir) && s.startsWith(getFileNameForKey(urlPrefix)))
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            final HttpResponseCache this$0;
            private final File val$cacheDir;
            private final String val$urlPrefix;

            
            {
                this$0 = HttpResponseCache.this;
                cacheDir = file;
                urlPrefix = s;
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

    protected com.github.droidfu.http.CachedHttpResponse.ResponseData readValueFromDisk(File file)
        throws IOException
    {
        BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
        long l = file.length();
        if(l > 0x7fffffffL)
        {
            throw new IOException("Cannot read files larger than 2147483647 bytes");
        } else
        {
            int i = bufferedinputstream.read();
            int j = (int)l - 1;
            byte abyte0[] = new byte[j];
            bufferedinputstream.read(abyte0, 0, j);
            bufferedinputstream.close();
            return new com.github.droidfu.http.CachedHttpResponse.ResponseData(i, abyte0);
        }
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

    protected void writeValueToDisk(File file, com.github.droidfu.http.CachedHttpResponse.ResponseData responsedata)
        throws IOException
    {
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedoutputstream.write(responsedata.getStatusCode());
        bufferedoutputstream.write(responsedata.getResponseBody());
        bufferedoutputstream.close();
    }

    protected volatile void writeValueToDisk(File file, Object obj)
        throws IOException
    {
        writeValueToDisk(file, (com.github.droidfu.http.CachedHttpResponse.ResponseData)obj);
    }
}
