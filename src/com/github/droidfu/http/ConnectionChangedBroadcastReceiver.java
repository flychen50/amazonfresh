// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.http;

import android.content.*;

// Referenced classes of package com.github.droidfu.http:
//            BetterHttp

public class ConnectionChangedBroadcastReceiver extends BroadcastReceiver
{

    public ConnectionChangedBroadcastReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        BetterHttp.setContext(context);
        BetterHttp.updateProxySettings();
    }
}
