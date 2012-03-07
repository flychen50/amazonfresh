// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import java.util.LinkedList;
import java.util.List;

public class BetterScrollingListView extends ListView
{

    public BetterScrollingListView(Context context)
    {
        super(context);
        listeners = new LinkedList();
    }

    public BetterScrollingListView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        listeners = new LinkedList();
    }

    public BetterScrollingListView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        listeners = new LinkedList();
    }

    public void addListener(Runnable runnable)
    {
        listeners.add(runnable);
    }

    public boolean removeListener(Runnable runnable)
    {
        return listeners.remove(runnable);
    }

    List listeners;
}
