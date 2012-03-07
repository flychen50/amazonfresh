// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.concurrent;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.github.droidfu.DroidFuApplication;
import com.github.droidfu.activities.BetterActivity;

// Referenced classes of package com.github.droidfu.concurrent:
//            BetterAsyncTaskCallable

public abstract class BetterAsyncTask extends AsyncTask
{

    public BetterAsyncTask(Context context)
    {
        isTitleProgressIndeterminateEnabled = true;
        dialogId = 0;
        if(!(context.getApplicationContext() instanceof DroidFuApplication))
            throw new IllegalArgumentException("context bound to this task must be a DroidFu context (DroidFuApplication)");
        appContext = (DroidFuApplication)context.getApplicationContext();
        callerId = context.getClass().getCanonicalName();
        contextIsDroidFuActivity = context instanceof BetterActivity;
        appContext.setActiveContext(callerId, context);
        if(!contextIsDroidFuActivity) goto _L2; else goto _L1
_L1:
        int i = ((BetterActivity)context).getWindowFeatures();
        if(2 != (i & 2)) goto _L4; else goto _L3
_L3:
        isTitleProgressEnabled = true;
_L2:
        return;
_L4:
        if(5 == (i & 5))
            isTitleProgressIndeterminateEnabled = true;
        if(true) goto _L2; else goto _L5
_L5:
    }

    protected abstract void after(Context context, Object obj);

    protected void before(Context context)
    {
    }

    public void disableDialog()
    {
        dialogId = -1;
    }

    protected transient Object doCheckedInBackground(Context context, Object aobj[])
        throws Exception
    {
        Object obj;
        if(callable != null)
            obj = callable.call(this);
        else
            obj = null;
        return obj;
    }

    protected final transient Object doInBackground(Object aobj[])
    {
        Object obj;
        Context context;
        obj = null;
        context = getCallingContext();
        Object obj1 = doCheckedInBackground(context, aobj);
        obj = obj1;
_L2:
        return obj;
        Exception exception;
        exception;
        error = exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean failed()
    {
        boolean flag;
        if(error != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected Context getCallingContext()
    {
        Context context1 = appContext.getActiveContext(callerId);
        if(context1 == null || !callerId.equals(context1.getClass().getCanonicalName())) goto _L2; else goto _L1
_L1:
        if(!(context1 instanceof Activity)) goto _L4; else goto _L3
_L3:
        boolean flag = ((Activity)context1).isFinishing();
        if(!flag) goto _L4; else goto _L2
_L2:
        Context context = null;
_L6:
        return context;
_L4:
        context = context1;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        exception.printStackTrace();
        context = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected abstract void handleError(Context context, Exception exception);

    protected final void onPostExecute(Object obj)
    {
        Context context = getCallingContext();
        if(context != null) goto _L2; else goto _L1
_L1:
        Log.d(com/github/droidfu/concurrent/BetterAsyncTask.getSimpleName(), (new StringBuilder("skipping post-exec handler for task ")).append(hashCode()).append(" (context is null)").toString());
_L7:
        return;
_L2:
        if(!contextIsDroidFuActivity) goto _L4; else goto _L3
_L3:
        Activity activity;
        activity = (Activity)context;
        if(dialogId > -1)
            activity.removeDialog(dialogId);
        if(!isTitleProgressEnabled) goto _L6; else goto _L5
_L5:
        activity.setProgressBarVisibility(false);
_L4:
        if(failed())
            handleError(context, error);
        else
            after(context, obj);
        if(true) goto _L7; else goto _L6
_L6:
        if(isTitleProgressIndeterminateEnabled)
            activity.setProgressBarIndeterminateVisibility(false);
          goto _L4
    }

    protected final void onPreExecute()
    {
        Context context = getCallingContext();
        if(context != null) goto _L2; else goto _L1
_L1:
        Log.d(com/github/droidfu/concurrent/BetterAsyncTask.getSimpleName(), (new StringBuilder("skipping pre-exec handler for task ")).append(hashCode()).append(" (context is null)").toString());
        cancel(true);
_L4:
        return;
_L2:
        Activity activity;
        if(contextIsDroidFuActivity)
        {
            activity = (Activity)context;
            if(dialogId > -1)
                activity.showDialog(dialogId);
            if(!isTitleProgressEnabled)
                break; /* Loop/switch isn't completed */
            activity.setProgressBarVisibility(true);
        }
_L6:
        before(context);
        if(true) goto _L4; else goto _L3
_L3:
        if(!isTitleProgressIndeterminateEnabled) goto _L6; else goto _L5
_L5:
        activity.setProgressBarIndeterminateVisibility(true);
          goto _L6
    }

    public void setCallable(BetterAsyncTaskCallable betterasynctaskcallable)
    {
        callable = betterasynctaskcallable;
    }

    public void useCustomDialog(int i)
    {
        dialogId = i;
    }

    private final DroidFuApplication appContext;
    private BetterAsyncTaskCallable callable;
    private final String callerId;
    private final boolean contextIsDroidFuActivity;
    private int dialogId;
    private Exception error;
    private boolean isTitleProgressEnabled;
    private boolean isTitleProgressIndeterminateEnabled;
}
