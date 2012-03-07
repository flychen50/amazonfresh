// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.util.concurrent.FutureTask;

// Referenced classes of package com.demiroot.amazonfresh:
//            AFBaseActivity, Async

public class AsyncRequest
{
    public static interface LoadingBarAction
    {

        public abstract void removeLoadingBar(Context context1);

        public abstract void showLoadingBar(String s, Context context1);
    }


    private AsyncRequest(Context context1, Handler handler1, Runnable runnable)
    {
        context = context1;
        handler = handler1;
        request = runnable;
        loadingBarAction = overlayAction;
        message = context1.getString(0x7f07009c);
    }

    public static AsyncRequest buildRequest(Context context1, Handler handler1, Runnable runnable)
    {
        return new AsyncRequest(context1, handler1, runnable);
    }

    private LoadingBarAction makeTopSpinnerLoadingAction(final AFBaseActivity activity)
    {
        if(activity.findViewById(0x7f0b00bb) == null)
            throw new IllegalStateException("Context must be a AFBaseActivity with the loading bar!");
        else
            return new LoadingBarAction() {

                public void removeLoadingBar(Context context1)
                {
                    activity.removeLoadingBar(tag);
                }

                public void showLoadingBar(String s, Context context1)
                {
                    activity.showLoadingBar(s, tag);
                }

                Object tag;
                final AsyncRequest this$0;
                private final AFBaseActivity val$activity;

            
            {
                this$0 = AsyncRequest.this;
                activity = afbaseactivity;
                super();
                tag = new Object();
            }
            }
;
    }

    public void execute()
    {
        Async.run(this);
    }

    public AsyncRequest setFailureAction(Async.OnExceptionAction onexceptionaction)
    {
        failure = onexceptionaction;
        return this;
    }

    public AsyncRequest setLoadingBarAction(LoadingBarAction loadingbaraction)
    {
        loadingBarAction = loadingbaraction;
        return this;
    }

    public AsyncRequest setSuccessAction(Runnable runnable)
    {
        success = runnable;
        return this;
    }

    public AsyncRequest setTaskToCancelOnError(FutureTask futuretask)
    {
        taskToCancelOnError = futuretask;
        return this;
    }

    public AsyncRequest setTopLoadingBar()
    {
        setLoadingBarAction(makeTopSpinnerLoadingAction((AFBaseActivity)context));
        return this;
    }

    public AsyncRequest setWaitMessage(String s)
    {
        message = s;
        return this;
    }

    Context context;
    Async.OnExceptionAction failure;
    Handler handler;
    LoadingBarAction loadingBarAction;
    String message;
    public final LoadingBarAction overlayAction = new LoadingBarAction() {

        public void removeLoadingBar(Context context2)
        {
            if(localProgressBar != null && localProgressBar.isShowing())
                localProgressBar.dismiss();
_L1:
            return;
            Exception exception;
            exception;
            Log.e("ERROR_MSG", "Error removing progess bar", exception);
              goto _L1
        }

        public void showLoadingBar(String s, Context context2)
        {
            localProgressBar = ProgressDialog.show(context2, "", s);
        }

        private ProgressDialog localProgressBar;
        final AsyncRequest this$0;

            
            {
                this$0 = AsyncRequest.this;
                super();
            }
    }
;
    Runnable request;
    Runnable success;
    FutureTask taskToCancelOnError;
}
