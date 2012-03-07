// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            CameraManager

public final class ViewfinderView extends View
{

    public ViewfinderView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        Resources resources = getResources();
        maskColor = resources.getColor(0x7f09001d);
        resultColor = resources.getColor(0x7f090023);
        frameColor = resources.getColor(0x7f09001b);
        laserColor = resources.getColor(0x7f09001c);
        scannerAlpha = 0;
    }

    public void drawResultBitmap(Bitmap bitmap)
    {
        resultBitmap = bitmap;
        invalidate();
    }

    public void drawViewfinder()
    {
        resultBitmap = null;
        invalidate();
    }

    public void onDraw(Canvas canvas)
    {
        Rect rect = CameraManager.get().getFramingRect();
        if(rect != null)
        {
            int i = canvas.getWidth();
            int j = canvas.getHeight();
            Paint paint1 = paint;
            int k;
            if(resultBitmap != null)
                k = resultColor;
            else
                k = maskColor;
            paint1.setColor(k);
            box.set(0, 0, i, rect.top);
            canvas.drawRect(box, paint);
            box.set(0, rect.top, rect.left, 1 + rect.bottom);
            canvas.drawRect(box, paint);
            box.set(1 + rect.right, rect.top, i, 1 + rect.bottom);
            canvas.drawRect(box, paint);
            box.set(0, 1 + rect.bottom, i, j);
            canvas.drawRect(box, paint);
            if(resultBitmap != null)
            {
                paint.setAlpha(255);
                canvas.drawBitmap(resultBitmap, rect.left, rect.top, paint);
            } else
            {
                paint.setColor(frameColor);
                box.set(rect.left, rect.top, 1 + rect.right, 2 + rect.top);
                canvas.drawRect(box, paint);
                box.set(rect.left, 2 + rect.top, 2 + rect.left, rect.bottom - 1);
                canvas.drawRect(box, paint);
                box.set(rect.right - 1, rect.top, 1 + rect.right, rect.bottom - 1);
                canvas.drawRect(box, paint);
                box.set(rect.left, rect.bottom - 1, 1 + rect.right, 1 + rect.bottom);
                canvas.drawRect(box, paint);
                paint.setColor(laserColor);
                paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
                scannerAlpha = (1 + scannerAlpha) % SCANNER_ALPHA.length;
                int l = rect.height() / 2 + rect.top;
                box.set(2 + rect.left, l - 1, rect.right - 1, l + 2);
                canvas.drawRect(box, paint);
                postInvalidateDelayed(100L, box.left, box.top, box.right, box.bottom);
            }
        }
    }

    private static final long ANIMATION_DELAY = 100L;
    private static final int SCANNER_ALPHA[];
    private final Rect box = new Rect();
    private final int frameColor;
    private final int laserColor;
    private final int maskColor;
    private final Paint paint = new Paint();
    private Bitmap resultBitmap;
    private final int resultColor;
    private int scannerAlpha;

    static 
    {
        int ai[] = new int[8];
        ai[1] = 64;
        ai[2] = 128;
        ai[3] = 192;
        ai[4] = 255;
        ai[5] = 192;
        ai[6] = 128;
        ai[7] = 64;
        SCANNER_ALPHA = ai;
    }
}
