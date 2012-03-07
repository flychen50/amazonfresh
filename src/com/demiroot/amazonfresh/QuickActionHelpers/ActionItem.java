// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.QuickActionHelpers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ActionItem
{

    public ActionItem()
    {
    }

    public ActionItem(Drawable drawable)
    {
        icon = drawable;
    }

    public Drawable getIcon()
    {
        return icon;
    }

    public android.view.View.OnClickListener getListener()
    {
        return listener;
    }

    public Bitmap getThumb()
    {
        return thumb;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setIcon(Drawable drawable)
    {
        icon = drawable;
    }

    public void setOnClickListener(android.view.View.OnClickListener onclicklistener)
    {
        listener = onclicklistener;
    }

    public void setSelected(boolean flag)
    {
        selected = flag;
    }

    public void setThumb(Bitmap bitmap)
    {
        thumb = bitmap;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    private Drawable icon;
    private android.view.View.OnClickListener listener;
    private boolean selected;
    private Bitmap thumb;
    private String title;
}
