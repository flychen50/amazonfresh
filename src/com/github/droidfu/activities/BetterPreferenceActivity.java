// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.*;
import com.github.droidfu.DroidFuApplication;
import com.github.droidfu.dialogs.DialogClickListener;
import java.util.List;

// Referenced classes of package com.github.droidfu.activities:
//            BetterActivity, BetterActivityHelper

public class BetterPreferenceActivity extends PreferenceActivity
    implements BetterActivity
{

    public BetterPreferenceActivity()
    {
    }

    public Intent getCurrentIntent()
    {
        return currentIntent;
    }

    public int getWindowFeatures()
    {
        return BetterActivityHelper.getWindowFeatures(this);
    }

    public boolean isApplicationBroughtToBackground()
    {
        return BetterActivityHelper.isApplicationBroughtToBackground(this);
    }

    public boolean isLandscapeMode()
    {
        boolean flag;
        if(getWindowManager().getDefaultDisplay().getOrientation() == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isLaunching()
    {
        boolean flag;
        if(!wasInterrupted && wasCreated)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isPortraitMode()
    {
        boolean flag;
        if(isLandscapeMode())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public boolean isRestoring()
    {
        return wasInterrupted;
    }

    public boolean isResuming()
    {
        boolean flag;
        if(wasCreated)
            flag = false;
        else
            flag = true;
        return flag;
    }

    public AlertDialog newAlertDialog(int i, int j)
    {
        return BetterActivityHelper.newMessageDialog(this, getString(i), getString(j), 0x1080027);
    }

    public AlertDialog newErrorHandlerDialog(int i, Exception exception)
    {
        return BetterActivityHelper.newErrorHandlerDialog(this, getString(i), exception);
    }

    public AlertDialog newErrorHandlerDialog(Exception exception)
    {
        return newErrorHandlerDialog(getResources().getIdentifier("droidfu_error_dialog_title", "string", getPackageName()), exception);
    }

    public AlertDialog newInfoDialog(int i, int j)
    {
        return BetterActivityHelper.newMessageDialog(this, getString(i), getString(j), 0x108009b);
    }

    public Dialog newListDialog(String s, List list, DialogClickListener dialogclicklistener, boolean flag)
    {
        return BetterActivityHelper.newListDialog(this, s, list, dialogclicklistener, flag);
    }

    public AlertDialog newYesNoDialog(int i, int j, android.content.DialogInterface.OnClickListener onclicklistener)
    {
        return BetterActivityHelper.newYesNoDialog(this, getString(i), getString(j), 0x108009b, onclicklistener);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        wasCreated = true;
        currentIntent = getIntent();
        android.app.Application application = getApplication();
        if(application instanceof DroidFuApplication)
            ((DroidFuApplication)application).setActiveContext(getClass().getCanonicalName(), this);
    }

    protected Dialog onCreateDialog(int i)
    {
        return BetterActivityHelper.createProgressDialog(this, progressDialogTitleId, progressDialogMsgId);
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        BetterActivityHelper.handleApplicationClosing(this, i);
        return super.onKeyDown(i, keyevent);
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        currentIntent = intent;
    }

    protected void onPause()
    {
        super.onPause();
        wasInterrupted = false;
        wasCreated = false;
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        wasInterrupted = true;
    }

    public void setProgressDialogMsgId(int i)
    {
        progressDialogMsgId = i;
    }

    public void setProgressDialogTitleId(int i)
    {
        progressDialogTitleId = i;
    }

    private Intent currentIntent;
    private int progressDialogMsgId;
    private int progressDialogTitleId;
    private boolean wasCreated;
    private boolean wasInterrupted;
}
