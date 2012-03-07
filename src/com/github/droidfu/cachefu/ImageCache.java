// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.*;

// Referenced classes of package com.github.droidfu.cachefu:
//            AbstractCache, CacheHelper

public class ImageCache extends AbstractCache
{

    public ImageCache(int i, long l, int j)
    {
        super("ImageCache", i, l, j);
    }

    /**
     * @deprecated Method getBitmap is deprecated
     */

    public Bitmap getBitmap(Object obj)
    {
        this;
        JVM INSTR monitorenter ;
        byte abyte0[] = (byte[])super.get(obj);
        if(abyte0 != null) goto _L2; else goto _L1
_L1:
        Bitmap bitmap1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return bitmap1;
_L2:
        Bitmap bitmap = BitmapFactory.decodeByteArray(abyte0, 0, abyte0.length);
        bitmap1 = bitmap;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public volatile String getFileNameForKey(Object obj)
    {
        return getFileNameForKey((String)obj);
    }

    public String getFileNameForKey(String s)
    {
        return CacheHelper.getFileNameFromUrl(s);
    }

    protected volatile Object readValueFromDisk(File file)
        throws IOException
    {
        return readValueFromDisk(file);
    }

    protected byte[] readValueFromDisk(File file)
        throws IOException
    {
        BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(file));
        long l = file.length();
        if(l > 0x7fffffffL)
        {
            throw new IOException("Cannot read files larger than 2147483647 bytes");
        } else
        {
            int i = (int)l;
            byte abyte0[] = new byte[i];
            bufferedinputstream.read(abyte0, 0, i);
            bufferedinputstream.close();
            return abyte0;
        }
    }

    protected volatile void writeValueToDisk(File file, Object obj)
        throws IOException
    {
        writeValueToDisk(file, (byte[])obj);
    }

    protected void writeValueToDisk(File file, byte abyte0[])
        throws IOException
    {
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedoutputstream.write(abyte0);
        bufferedoutputstream.close();
    }
}
