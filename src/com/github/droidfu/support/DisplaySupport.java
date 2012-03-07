// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.support;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import java.lang.reflect.Field;

public class DisplaySupport
{

    public DisplaySupport()
    {
    }

    public static int dipToPx(Context context, int i)
    {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return (int)(0.5F + (float)i * displaymetrics.density);
    }

    public static int getScreenDensity(Context context)
    {
        if(screenDensity == -1)
        {
            DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
            try
            {
                screenDensity = android/util/DisplayMetrics.getField("densityDpi").getInt(displaymetrics);
            }
            catch(Exception exception)
            {
                screenDensity = 160;
            }
        }
        return screenDensity;
    }

    public static Drawable scaleDrawable(Context context, int i, int j, int k)
    {
        return new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), i), j, k, true));
    }

    public static final int SCREEN_DENSITY_HIGH = 240;
    public static final int SCREEN_DENSITY_LOW = 120;
    public static final int SCREEN_DENSITY_MEDIUM = 160;
    private static int screenDensity = -1;

}
