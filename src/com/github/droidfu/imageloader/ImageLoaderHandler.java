// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.widget.ImageView;

public class ImageLoaderHandler extends Handler
{

    public ImageLoaderHandler(ImageView imageview, String s)
    {
        imageView = imageview;
        imageUrl = s;
    }

    public ImageLoaderHandler(ImageView imageview, String s, Drawable drawable)
    {
        this(imageview, s);
        errorDrawable = drawable;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public ImageView getImageView()
    {
        return imageView;
    }

    protected boolean handleImageLoaded(Bitmap bitmap, Message message)
    {
        String s = (String)imageView.getTag();
        boolean flag;
        if(imageUrl.equals(s))
        {
            Bitmap bitmap1;
            if(bitmap != null || errorDrawable == null)
                bitmap1 = bitmap;
            else
                bitmap1 = ((BitmapDrawable)errorDrawable).getBitmap();
            if(bitmap1 != null)
                imageView.setImageBitmap(bitmap1);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    protected final void handleImageLoadedMessage(Message message)
    {
        handleImageLoaded((Bitmap)message.getData().getParcelable("droidfu:extra_bitmap"), message);
    }

    public final void handleMessage(Message message)
    {
        if(message.what == 0)
            handleImageLoadedMessage(message);
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public void setImageView(ImageView imageview)
    {
        imageView = imageview;
    }

    private Drawable errorDrawable;
    private String imageUrl;
    private ImageView imageView;
}
