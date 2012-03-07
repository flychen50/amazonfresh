// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.common.collect;

import java.util.Timer;

class ExpirationTimer
{

    ExpirationTimer()
    {
    }

    static Timer instance = new Timer(true);

}
