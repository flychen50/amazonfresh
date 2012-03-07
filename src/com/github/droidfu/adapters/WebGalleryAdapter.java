// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.github.droidfu.widgets.WebImageView;
import java.util.List;

public class WebGalleryAdapter extends BaseAdapter
{
    private static final class ViewHolder
    {

        private WebImageView webImageView;



        private ViewHolder()
        {
        }

        ViewHolder(ViewHolder viewholder)
        {
            this();
        }
    }


    public WebGalleryAdapter(Context context1)
    {
        initialize(context1, null, null, null);
    }

    public WebGalleryAdapter(Context context1, List list)
    {
        initialize(context1, list, null, null);
    }

    public WebGalleryAdapter(Context context1, List list, int i)
    {
        initialize(context1, list, context1.getResources().getDrawable(i), null);
    }

    public WebGalleryAdapter(Context context1, List list, int i, int j)
    {
        Drawable drawable;
        Drawable drawable1;
        if(i == -1)
            drawable = null;
        else
            drawable = context1.getResources().getDrawable(i);
        if(j == -1)
            drawable1 = null;
        else
            drawable1 = context1.getResources().getDrawable(j);
        initialize(context1, list, drawable, drawable1);
    }

    private void initialize(Context context1, List list, Drawable drawable, Drawable drawable1)
    {
        imageUrls = list;
        context = context1;
        progressDrawable = drawable;
        errorDrawable = drawable1;
    }

    public int getCount()
    {
        return imageUrls.size();
    }

    public List getImageUrls()
    {
        return imageUrls;
    }

    public Object getItem(int i)
    {
        return imageUrls.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public Drawable getProgressDrawable()
    {
        return progressDrawable;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        String s = (String)getItem(i);
        WebImageView webimageview;
        if(view == null)
        {
            webimageview = new WebImageView(context, null, progressDrawable, errorDrawable, false);
            android.widget.FrameLayout.LayoutParams layoutparams = new android.widget.FrameLayout.LayoutParams(-2, -2);
            layoutparams.gravity = 17;
            webimageview.setLayoutParams(layoutparams);
            FrameLayout framelayout = new FrameLayout(context);
            framelayout.setLayoutParams(new android.widget.Gallery.LayoutParams(-1, -1));
            framelayout.addView(webimageview, 0);
            view = framelayout;
            ViewHolder viewholder = new ViewHolder(null);
            viewholder.webImageView = webimageview;
            view.setTag(viewholder);
        } else
        {
            webimageview = ((ViewHolder)view.getTag()).webImageView;
        }
        webimageview.reset();
        webimageview.setImageUrl(s);
        webimageview.loadImage();
        onGetView(i, view, viewgroup);
        return view;
    }

    protected void onGetView(int i, View view, ViewGroup viewgroup)
    {
    }

    public void setImageUrls(List list)
    {
        imageUrls = list;
    }

    public void setProgressDrawable(Drawable drawable)
    {
        progressDrawable = drawable;
    }

    public static final int NO_DRAWABLE = -1;
    private Context context;
    private Drawable errorDrawable;
    private List imageUrls;
    private Drawable progressDrawable;
}
