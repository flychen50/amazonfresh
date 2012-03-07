// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.listeners;

import android.content.*;
import com.demiroot.amazonfresh.AFBaseActivity;

public abstract class RemoveLoadingBarReceiver extends BroadcastReceiver
{

    public RemoveLoadingBarReceiver(AFBaseActivity afbaseactivity)
    {
        mActivity = afbaseactivity;
    }

    public final void onReceive(Context context, Intent intent)
    {
        long l = intent.getLongExtra("uid", 0L);
        if(l != lastUid && l > createdAt)
        {
            lastUid = l;
            mActivity.removeLoadingBar();
        }
        onReceiveSafe(context, intent);
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public abstract void onReceiveSafe(Context context, Intent intent)
        throws Exception;

    public static final String BROADCAST_UID_KEY = "uid";
    private final long createdAt = System.currentTimeMillis();
    private long lastUid;
    final AFBaseActivity mActivity;
}
