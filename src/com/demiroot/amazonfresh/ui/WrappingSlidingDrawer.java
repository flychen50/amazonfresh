// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SlidingDrawer;

public class WrappingSlidingDrawer extends SlidingDrawer
{

    public WrappingSlidingDrawer(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        int i = attributeset.getAttributeIntValue("android", "orientation", 1);
        mTopOffset = attributeset.getAttributeIntValue("android", "topOffset", 0);
        boolean flag;
        if(i == 1)
            flag = true;
        else
            flag = false;
        mVertical = flag;
    }

    public WrappingSlidingDrawer(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        int j = attributeset.getAttributeIntValue("android", "orientation", 1);
        mTopOffset = attributeset.getAttributeIntValue("android", "topOffset", 0);
        boolean flag;
        if(j == 1)
            flag = true;
        else
            flag = false;
        mVertical = flag;
    }

    protected void onMeasure(int i, int j)
    {
        int k;
        int l;
        int i1;
        int j1;
        View view;
        View view1;
        k = android.view.View.MeasureSpec.getMode(i);
        l = android.view.View.MeasureSpec.getSize(i);
        i1 = android.view.View.MeasureSpec.getMode(j);
        j1 = android.view.View.MeasureSpec.getSize(j);
        if(k == 0 || i1 == 0)
            throw new RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
        view = getHandle();
        view1 = getContent();
        measureChild(view, i, j);
        if(!mVertical) goto _L2; else goto _L1
_L1:
        int l1;
        int i2;
        view1.measure(i, android.view.View.MeasureSpec.makeMeasureSpec(j1 - view.getMeasuredHeight() - mTopOffset, i1));
        i2 = view.getMeasuredHeight() + mTopOffset + view1.getMeasuredHeight();
        l1 = view1.getMeasuredWidth();
        if(view.getMeasuredWidth() > l1)
            l1 = view.getMeasuredWidth();
_L4:
        setMeasuredDimension(l1, i2);
        return;
_L2:
        int k1 = l - view.getMeasuredWidth() - mTopOffset;
        getContent().measure(android.view.View.MeasureSpec.makeMeasureSpec(k1, k), j);
        l1 = view.getMeasuredWidth() + mTopOffset + view1.getMeasuredWidth();
        i2 = view1.getMeasuredHeight();
        if(view.getMeasuredHeight() > i2)
            i2 = view.getMeasuredHeight();
        if(true) goto _L4; else goto _L3
_L3:
    }

    private int mTopOffset;
    private boolean mVertical;
}
