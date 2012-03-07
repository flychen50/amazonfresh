// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.listeners;

import android.view.KeyEvent;
import android.view.View;

public abstract class EnterKeyListener
    implements android.view.View.OnKeyListener
{

    public EnterKeyListener()
    {
    }

    public abstract void onEnter();

    public boolean onKey(View view, int i, KeyEvent keyevent)
    {
        boolean flag;
        if(keyevent.getAction() == 0 && i == 66)
        {
            onEnter();
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }
}
