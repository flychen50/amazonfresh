// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import com.github.droidfu.imageloader.ImageLoader;
import com.github.droidfu.imageloader.ImageLoaderHandler;
import java.io.PrintStream;

public class WebImageView extends ViewSwitcher
{
    private class DefaultImageLoaderHandler extends ImageLoaderHandler
    {

        protected boolean handleImageLoaded(Bitmap bitmap, Message message)
        {
            boolean flag = super.handleImageLoaded(bitmap, message);
            if(flag)
            {
                isLoaded = true;
                setDisplayedChild(1);
            }
            return flag;
        }

        final WebImageView this$0;

        public DefaultImageLoaderHandler()
        {
            this$0 = WebImageView.this;
            super(imageView, imageUrl, errorDrawable);
        }
    }


    public WebImageView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP;
        int i = attributeset.getAttributeResourceValue("http://github.com/droidfu/schema", "progressDrawable", 0);
        int j = attributeset.getAttributeResourceValue("http://github.com/droidfu/schema", "errorDrawable", 0);
        Drawable drawable = null;
        if(i > 0)
            drawable = context.getResources().getDrawable(i);
        Drawable drawable1 = null;
        if(j > 0)
            drawable1 = context.getResources().getDrawable(j);
        initialize(context, attributeset.getAttributeValue("http://github.com/droidfu/schema", "imageUrl"), drawable, drawable1, attributeset.getAttributeBooleanValue("http://github.com/droidfu/schema", "autoLoad", true));
    }

    public WebImageView(Context context, String s, Drawable drawable, Drawable drawable1, boolean flag)
    {
        super(context);
        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP;
        initialize(context, s, drawable, drawable1, flag);
    }

    public WebImageView(Context context, String s, Drawable drawable, boolean flag)
    {
        super(context);
        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP;
        initialize(context, s, drawable, null, flag);
    }

    public WebImageView(Context context, String s, boolean flag)
    {
        super(context);
        scaleType = android.widget.ImageView.ScaleType.CENTER_CROP;
        initialize(context, s, null, null, flag);
    }

    private void addImageView(Context context)
    {
        imageView = new ImageView(context);
        imageView.setScaleType(scaleType);
        android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2);
        layoutparams.gravity = 17;
        addView(imageView, 1, layoutparams);
    }

    private void addLoadingSpinnerView(Context context)
    {
        System.out.println("SETTING SPINNER==================");
        loadingSpinner = new ProgressBar(context);
        loadingSpinner.setIndeterminate(true);
        if(progressDrawable != null) goto _L2; else goto _L1
_L1:
        progressDrawable = loadingSpinner.getIndeterminateDrawable();
_L4:
        android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(progressDrawable.getIntrinsicWidth(), progressDrawable.getIntrinsicHeight());
        layoutparams.gravity = 17;
        addView(loadingSpinner, 0, layoutparams);
        return;
_L2:
        loadingSpinner.setIndeterminateDrawable(progressDrawable);
        if(progressDrawable instanceof AnimationDrawable)
            ((AnimationDrawable)progressDrawable).start();
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void initialize(Context context, String s, Drawable drawable, Drawable drawable1, boolean flag)
    {
        imageUrl = s;
        progressDrawable = drawable;
        errorDrawable = drawable1;
        ImageLoader.initialize(context);
        addLoadingSpinnerView(context);
        addImageView(context);
        if(flag && s != null)
            loadImage();
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public void loadImage()
    {
        if(imageUrl == null)
        {
            throw new IllegalStateException("image URL is null; did you forget to set it for this view?");
        } else
        {
            System.out.println((new StringBuilder("LOADING IMAGE ========================== ")).append(imageUrl).toString());
            loadingImg = ImageLoader.start(imageUrl, new DefaultImageLoaderHandler());
            return;
        }
    }

    protected void removeDetachedView(View view, boolean flag)
    {
        if(loadingImg != null)
            loadingImg.cancel();
        super.removeDetachedView(view, flag);
    }

    public void reset()
    {
        System.out.println((new StringBuilder("RESET CALLED---------------------------")).append(imageUrl).toString());
        if(loadingImg != null)
        {
            System.out.println("cancelling !!!!!!!!!!!!!!!!!!!!!!!");
            loadingImg.cancel();
        }
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public void setNoImageDrawable(int i)
    {
        imageView.setImageDrawable(getContext().getResources().getDrawable(i));
        setDisplayedChild(1);
    }

    public void setScaleType(android.widget.ImageView.ScaleType scaletype)
    {
        imageView.setScaleType(scaletype);
    }

    private Drawable errorDrawable;
    private String imageUrl;
    private ImageView imageView;
    private boolean isLoaded;
    private ImageLoader loadingImg;
    private ProgressBar loadingSpinner;
    private Drawable progressDrawable;
    private android.widget.ImageView.ScaleType scaleType;




}
