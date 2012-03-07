// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import com.demiroot.freshclient.AmazonFreshBase;
import com.demiroot.freshclient.FreshClientProxy;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh:
//            AndroidFreshProxyGetter

public class AFApplication extends Application
{

    public AFApplication()
    {
    }

    public AmazonFreshBase getAmazonFreshBase()
    {
        return base;
    }

    public String getAppVersion()
    {
        String s = "1.0r";
        try
        {
            s = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        }
        catch(Exception exception) { }
        return s;
    }

    public void onCreate()
    {
        super.onCreate();
        String s = getAppVersion();
        userAgent = (new StringBuilder("FreshAndroidApp-")).append(s).toString();
        proxy = new FreshClientProxy(getResources().getString(0x7f070004), userAgent);
        try
        {
            List list = (List)(new ObjectInputStream(openFileInput("cookies"))).readObject();
            proxy.setCookies(list);
        }
        catch(Exception exception)
        {
            Log.e("ERROR_MSG", "sean: Error reloading cookies, forceing login", exception);
        }
        try
        {
            base = (AmazonFreshBase)(new ObjectInputStream(openFileInput("state"))).readObject();
        }
        catch(Exception exception1)
        {
            Log.e("ERROR_MSG", "Error resuming, starting over", exception1);
        }
        if(base == null)
        {
            base = new AmazonFreshBase(new AndroidFreshProxyGetter(getResources().getString(0x7f070004), userAgent));
            save();
        }
    }

    public void save()
    {
        ObjectOutputStream objectoutputstream1;
        try
        {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(openFileOutput("state", 0));
            objectoutputstream.writeObject(base);
            objectoutputstream.close();
        }
        catch(Exception exception)
        {
            Log.e("ERROR_MSG", "Error saving", exception);
        }
        objectoutputstream1 = new ObjectOutputStream(openFileOutput("cookies", 0));
        objectoutputstream1.writeObject(proxy.getCookies());
        objectoutputstream1.close();
_L1:
        return;
        Exception exception1;
        exception1;
        Log.e("ERROR_MSG", "Error saving", exception1);
          goto _L1
    }

    public static final String ERROR_KEY = "ERROR_MSG";
    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    private static volatile AmazonFreshBase base;
    protected static FreshClientProxy proxy;
    private String userAgent;
}
