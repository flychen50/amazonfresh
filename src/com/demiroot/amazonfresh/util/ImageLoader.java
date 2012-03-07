// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import com.demiroot.freshclient.DisplayItem;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader
{

    public ImageLoader()
    {
    }

    public static int getRatingImageId(DisplayItem displayitem)
    {
        if(displayitem.getProduceRating() == null) goto _L2; else goto _L1
_L1:
        if(!displayitem.getProduceRating().equals("Fantastic")) goto _L4; else goto _L3
_L3:
        int i = 0x7f02003d;
_L7:
        return i;
_L4:
        if(displayitem.getProduceRating().equals("Great"))
        {
            i = 0x7f02003c;
            continue; /* Loop/switch isn't completed */
        }
        if(displayitem.getProduceRating().equals("Good"))
        {
            i = 0x7f02003b;
            continue; /* Loop/switch isn't completed */
        }
        if(displayitem.getProduceRating().equals("Average"))
        {
            i = 0x7f02003a;
            continue; /* Loop/switch isn't completed */
        }
        if(displayitem.getProduceRating().equals("Below Average"))
        {
            i = 0x7f02003c;
            continue; /* Loop/switch isn't completed */
        }
          goto _L5
_L2:
        if(displayitem.getRatingType() != null && displayitem.getRatingType().equals("com"))
        {
            if(displayitem.getRating() == 5D)
            {
                i = 0x7f020038;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 4.5D)
            {
                i = 0x7f020037;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 4D)
            {
                i = 0x7f020036;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 3.5D)
            {
                i = 0x7f020035;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 3D)
            {
                i = 0x7f020034;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 2.5D)
            {
                i = 0x7f020033;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 2D)
            {
                i = 0x7f020032;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 1.5D)
            {
                i = 0x7f020031;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 1.0D)
            {
                i = 0x7f020030;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 0.5D)
            {
                i = 0x7f02002f;
                continue; /* Loop/switch isn't completed */
            }
            if(displayitem.getRating() == 0.0D)
            {
                i = 0x7f02002f;
                continue; /* Loop/switch isn't completed */
            }
        }
_L5:
        i = 0;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static void loadImageFromURL(final String imageUrl, Context context, final ImageView view)
    {
        final Handler h = new Handler();
        view.setImageResource(0x7f02004f);
        Runnable runnable = new Runnable() {

            public void run()
            {
                HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(imageUrl)).openConnection();
                httpurlconnection.setDoInput(true);
                httpurlconnection.connect();
                final Bitmap bmImg = BitmapFactory.decodeStream(httpurlconnection.getInputStream());
                h.post(new Runnable() {

                    public void run()
                    {
                        view.setImageBitmap(bmImg);
                    }

                    final _cls1 this$1;
                    private final Bitmap val$bmImg;
                    private final ImageView val$view;

                    
                    {
                        this$1 = _cls1.this;
                        view = imageview;
                        bmImg = bitmap;
                        super();
                    }
                }
);
_L1:
                return;
                Exception exception;
                exception;
                Log.e("ERROR_MSG", "Error loading Image", exception);
                  goto _L1
            }

            private final Handler val$h;
            private final String val$imageUrl;
            private final ImageView val$view;

            
            {
                imageUrl = s;
                h = handler;
                view = imageview;
                super();
            }
        }
;
        executor.execute(runnable);
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

}
