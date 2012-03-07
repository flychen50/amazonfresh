// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.github.droidfu.DroidFuApplication;

public class BetterService extends Service
{

    public BetterService()
    {
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ((DroidFuApplication)getApplication()).setActiveContext(getClass().getCanonicalName(), this);
    }
}
