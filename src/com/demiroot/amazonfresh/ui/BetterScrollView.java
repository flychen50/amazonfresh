// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import java.util.*;

public class BetterScrollView extends ScrollView
{

    public BetterScrollView(Context context)
    {
        super(context);
        listeners = new LinkedList();
    }

    public BetterScrollView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        listeners = new LinkedList();
    }

    public BetterScrollView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        listeners = new LinkedList();
    }

    public void addListener(Runnable runnable)
    {
        listeners.add(runnable);
    }

    protected void onScrollChanged(int i, int j, int k, int l)
    {
        if(getChildAt(getChildCount() - 1).getBottom() - (getHeight() + getScrollY()) != 0) goto _L2; else goto _L1
_L1:
        Iterator iterator = listeners.iterator();
_L5:
        if(iterator.hasNext()) goto _L3; else goto _L2
_L2:
        super.onScrollChanged(i, j, k, l);
        return;
_L3:
        ((Runnable)iterator.next()).run();
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean removeListener(Runnable runnable)
    {
        return listeners.remove(runnable);
    }

    List listeners;
}
