// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.listeners;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.freshclient.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;

public class ExceptionHandler extends Handler
{

    public ExceptionHandler(AFBaseActivity afbaseactivity)
    {
        context = afbaseactivity;
    }

    public void handleMessage(Message message)
    {
        String s;
        s = null;
        if(message.obj instanceof FreshAPILoginException)
        {
            Message message1 = new Message();
            message1.obj = context.getAmazonFreshBase().getAuthCookie();
            context.logoutHandler.sendMessage(message1);
        } else
        if(message.obj instanceof FreshAPIException)
        {
            FreshAPIException freshapiexception = (FreshAPIException)message.obj;
            s = (new StringBuilder(String.valueOf(freshapiexception.getReason()))).append(" (E-").append(freshapiexception.getErrorCode()).append(")").toString();
        } else
        if(message.obj instanceof APICallException)
        {
            APICallException apicallexception = (APICallException)message.obj;
            if(apicallexception.getCause() instanceof IOException)
            {
                s = context.getString(0x7f070088);
            } else
            {
                AFBaseActivity afbaseactivity1 = context;
                Object aobj1[] = new Object[1];
                aobj1[0] = apicallexception.getCause().getMessage();
                s = afbaseactivity1.getString(0x7f070089, aobj1);
                Log.e("ERROR_MSG", "API Error making request", apicallexception);
            }
        } else
        if((message.obj instanceof IOException) || (message.obj instanceof UnknownHostException))
        {
            s = context.getString(0x7f070088);
        } else
        {
            String s1 = ((Throwable)message.obj).getMessage();
            AFBaseActivity afbaseactivity = context;
            Object aobj[] = new Object[1];
            aobj[0] = (new StringBuilder()).append(message.obj.getClass()).append(" -> ").append(s1).toString();
            s = afbaseactivity.getString(0x7f070089, aobj);
            Log.e("ERROR_MSG", "Unknown error", (Throwable)message.obj);
        }
        if(s == null)
            break MISSING_BLOCK_LABEL_58;
        context.showMessage(s);
_L1:
        return;
        android.view.WindowManager.BadTokenException badtokenexception;
        badtokenexception;
        System.err.println((new StringBuilder("Unshowable error: ")).append(s).toString());
          goto _L1
    }

    private AFBaseActivity context;
}
