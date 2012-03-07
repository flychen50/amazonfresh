// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.util.Log;
import android.widget.ImageView;
import com.github.droidfu.cachefu.ImageCache;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

// Referenced classes of package com.github.droidfu.imageloader:
//            ImageLoaderHandler

public class ImageLoader
    implements Runnable
{

    private ImageLoader(String s, ImageLoaderHandler imageloaderhandler)
    {
        cancel = false;
        imageUrl = s;
        handler = imageloaderhandler;
    }

    public static void clearCache()
    {
        imageCache.clear();
    }

    public static ImageCache getImageCache()
    {
        return imageCache;
    }

    /**
     * @deprecated Method initialize is deprecated
     */

    public static void initialize(Context context)
    {
        com/github/droidfu/imageloader/ImageLoader;
        JVM INSTR monitorenter ;
        if(executor == null)
            executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(3);
        if(imageCache == null)
        {
            imageCache = new ImageCache(25, 1440L, 3);
            imageCache.enableDiskCache(context, 1);
        }
        com/github/droidfu/imageloader/ImageLoader;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public static void setMaxDownloadAttempts(int i)
    {
        numRetries = i;
    }

    public static void setThreadPoolSize(int i)
    {
        executor.setMaximumPoolSize(i);
    }

    private static ImageLoader start(String s, ImageView imageview, ImageLoaderHandler imageloaderhandler, Drawable drawable, Drawable drawable1)
    {
        if(imageview == null) goto _L2; else goto _L1
_L1:
        if(s != null) goto _L4; else goto _L3
_L3:
        ImageLoader imageloader1;
        imageview.setTag(null);
        imageview.setImageDrawable(drawable);
        imageloader1 = null;
_L6:
        return imageloader1;
_L4:
        if(s.equals((String)imageview.getTag()))
        {
            imageloader1 = null;
            continue; /* Loop/switch isn't completed */
        }
        imageview.setImageDrawable(drawable);
        imageview.setTag(s);
_L2:
        if(imageCache.containsKeyInMemory(s))
        {
            imageloaderhandler.handleImageLoaded(imageCache.getBitmap(s), null);
            imageloader1 = null;
        } else
        {
            ImageLoader imageloader = new ImageLoader(s, imageloaderhandler);
            executor.execute(imageloader);
            imageloader1 = imageloader;
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static ImageLoader start(String s, ImageLoaderHandler imageloaderhandler)
    {
        return start(s, imageloaderhandler.getImageView(), imageloaderhandler, null, null);
    }

    public static void start(String s, ImageView imageview)
    {
        start(s, imageview, new ImageLoaderHandler(imageview, s), null, null);
    }

    public static void start(String s, ImageView imageview, Drawable drawable, Drawable drawable1)
    {
        start(s, imageview, new ImageLoaderHandler(imageview, s, drawable1), drawable, drawable1);
    }

    public static void start(String s, ImageLoaderHandler imageloaderhandler, Drawable drawable, Drawable drawable1)
    {
        start(s, imageloaderhandler.getImageView(), imageloaderhandler, drawable, drawable1);
    }

    public void cancel()
    {
        cancel = true;
    }

    protected Bitmap downloadImage()
    {
        int i = 1;
        if(imageUrl.startsWith("http")) goto _L2; else goto _L1
_L1:
        Bitmap bitmap = null;
_L6:
        return bitmap;
_L4:
        Bitmap bitmap1;
        byte abyte0[] = retrieveImageData();
        if(abyte0 == null)
            break; /* Loop/switch isn't completed */
        imageCache.put(imageUrl, abyte0);
        bitmap1 = BitmapFactory.decodeByteArray(abyte0, 0, abyte0.length);
        bitmap = bitmap1;
        continue; /* Loop/switch isn't completed */
        Throwable throwable;
        throwable;
        Log.w("Droid-Fu/ImageLoader", (new StringBuilder("download for ")).append(imageUrl).append(" failed (attempt ").append(i).append(")").toString());
        throwable.printStackTrace();
        SystemClock.sleep(1000L);
        i++;
_L2:
        if(i <= numRetries) goto _L4; else goto _L3
_L3:
        bitmap = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void notifyImageLoaded(String s, Bitmap bitmap)
    {
        Message message = new Message();
        message.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString("droidfu:extra_image_url", s);
        bundle.putParcelable("droidfu:extra_bitmap", bitmap);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    protected byte[] retrieveImageData()
        throws IOException
    {
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(imageUrl);
        byte abyte0[];
        if(defaulthttpclient == null || httpget == null)
            abyte0 = new byte[0];
        else
            abyte0 = EntityUtils.toByteArray(defaulthttpclient.execute(httpget).getEntity());
        return abyte0;
    }

    public void run()
    {
        if(!cancel)
        {
            Bitmap bitmap = imageCache.getBitmap(imageUrl);
            if(bitmap == null)
                bitmap = downloadImage();
            notifyImageLoaded(imageUrl, bitmap);
        }
    }

    public static final String BITMAP_EXTRA = "droidfu:extra_bitmap";
    private static final int DEFAULT_NUM_RETRIES = 3;
    private static final int DEFAULT_POOL_SIZE = 3;
    private static final int DEFAULT_RETRY_HANDLER_SLEEP_TIME = 1000;
    private static final int DEFAULT_TTL_MINUTES = 1440;
    public static final int HANDLER_MESSAGE_ID = 0;
    public static final String IMAGE_URL_EXTRA = "droidfu:extra_image_url";
    private static final String LOG_TAG = "Droid-Fu/ImageLoader";
    private static ThreadPoolExecutor executor;
    private static ImageCache imageCache;
    private static int numRetries = 3;
    private volatile boolean cancel;
    private ImageLoaderHandler handler;
    private String imageUrl;

}
