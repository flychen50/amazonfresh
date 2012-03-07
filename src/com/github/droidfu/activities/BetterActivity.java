// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import com.github.droidfu.dialogs.DialogClickListener;
import java.util.List;

public interface BetterActivity
{

    public abstract Intent getCurrentIntent();

    public abstract int getWindowFeatures();

    public abstract boolean isApplicationBroughtToBackground();

    public abstract boolean isLandscapeMode();

    public abstract boolean isLaunching();

    public abstract boolean isPortraitMode();

    public abstract boolean isRestoring();

    public abstract boolean isResuming();

    public abstract AlertDialog newAlertDialog(int i, int j);

    public abstract AlertDialog newErrorHandlerDialog(int i, Exception exception);

    public abstract AlertDialog newErrorHandlerDialog(Exception exception);

    public abstract AlertDialog newInfoDialog(int i, int j);

    public abstract Dialog newListDialog(String s, List list, DialogClickListener dialogclicklistener, boolean flag);

    public abstract AlertDialog newYesNoDialog(int i, int j, android.content.DialogInterface.OnClickListener onclicklistener);

    public abstract void setProgressDialogMsgId(int i);

    public abstract void setProgressDialogTitleId(int i);
}
