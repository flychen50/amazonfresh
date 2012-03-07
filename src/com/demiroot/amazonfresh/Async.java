// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh;

import android.app.Activity;
import android.os.*;
import android.util.Log;
import android.widget.Toast;
import com.demiroot.freshclient.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.FutureTask;

// Referenced classes of package com.demiroot.amazonfresh:
//            AsyncRequest, AFBaseActivity

public class Async extends AsyncTask
{
    public static interface OnExceptionAction
    {

        public abstract boolean onException(Exception exception);
    }


    private Async(AsyncRequest asyncrequest)
    {
        request = asyncrequest;
    }

    Async(AsyncRequest asyncrequest, Async async)
    {
        this(asyncrequest);
    }

    public static void run(final AsyncRequest request)
    {
        request.handler.post(new Runnable() {

            public void run()
            {
                (new Async(request, null)).execute(new Void[0]);
            }

            private final AsyncRequest val$request;

            
            {
                request = asyncrequest;
                super();
            }
        }
);
    }

    public volatile transient Object doInBackground(Object aobj[])
    {
        return doInBackground((Void[])aobj);
    }

    public transient Void doInBackground(Void avoid[])
    {
        try
        {
            request.request.run();
        }
        catch(Exception exception)
        {
            exceptionThatNeedsToBeHandled = exception;
        }
        return null;
    }

    public void onCancelled()
    {
        request.loadingBarAction.removeLoadingBar(request.context);
    }

    public volatile void onPostExecute(Object obj)
    {
        onPostExecute((Void)obj);
    }

    public void onPostExecute(Void void1)
    {
        Activity activity = (Activity)request.context;
        if(exceptionThatNeedsToBeHandled != null)
        {
            if(request.taskToCancelOnError != null)
                request.taskToCancelOnError.cancel(true);
            Log.e("ERROR_MSG", "Error on Async task", exceptionThatNeedsToBeHandled);
            Object obj = null;
            try
            {
                throw exceptionThatNeedsToBeHandled;
            }
            catch(FreshAPILoginException freshapiloginexception)
            {
                if(activity instanceof AFBaseActivity)
                {
                    AFBaseActivity afbaseactivity = (AFBaseActivity)activity;
                    Message message = new Message();
                    message.obj = afbaseactivity.getAmazonFreshBase().getAuthCookie();
                    afbaseactivity.logoutHandler.sendMessage(message);
                }
            }
            catch(FreshAPIException freshapiexception)
            {
                obj = (new StringBuilder(String.valueOf(freshapiexception.getReason()))).append(" (E-").append(freshapiexception.getErrorCode()).append(")").toString();
            }
            catch(APICallException apicallexception)
            {
                if(apicallexception.getCause() instanceof IOException)
                    obj = activity.getString(0x7f070088);
                else
                    obj = (new StringBuilder(String.valueOf(activity.getString(0x7f070089)))).append(": ").append(apicallexception.getCause().getMessage()).toString();
            }
            catch(Exception exception)
            {
                obj = (new StringBuilder(String.valueOf(activity.getString(0x7f070089)))).append(": ").append(exception.getCause().getMessage()).toString();
            }
            if((request.failure == null || request.failure.onException(exceptionThatNeedsToBeHandled)) && obj != null)
                try
                {
                    Toast.makeText(request.context, ((CharSequence) (obj)), 1).show();
                }
                catch(android.view.WindowManager.BadTokenException badtokenexception)
                {
                    System.err.println((new StringBuilder("Unshowable error: ")).append(((String) (obj))).toString());
                }
        }
        request.loadingBarAction.removeLoadingBar(request.context);
        if(request.success != null && exceptionThatNeedsToBeHandled == null)
            request.success.run();
    }

    public void onPreExecute()
    {
        request.loadingBarAction.showLoadingBar(request.message, request.context);
    }

    private Exception exceptionThatNeedsToBeHandled;
    private final AsyncRequest request;
}
