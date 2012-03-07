// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.activities;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.view.KeyEvent;
import com.github.droidfu.DroidFuApplication;
import com.github.droidfu.dialogs.DialogClickListener;
import com.github.droidfu.exception.ResourceMessageException;
import com.github.droidfu.support.DiagnosticSupport;
import com.github.droidfu.support.IntentSupport;
import java.util.List;

public class BetterActivityHelper
{

    public BetterActivityHelper()
    {
    }

    public static ProgressDialog createProgressDialog(final Activity activity, int i, int j)
    {
        ProgressDialog progressdialog = new ProgressDialog(activity);
        if(i <= 0)
            i = activity.getResources().getIdentifier("droidfu_progress_dialog_title", "string", activity.getPackageName());
        progressdialog.setTitle(i);
        if(j <= 0)
            j = activity.getResources().getIdentifier("droidfu_progress_dialog_message", "string", activity.getPackageName());
        progressdialog.setMessage(activity.getString(j));
        progressdialog.setIndeterminate(true);
        progressdialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {

            public boolean onKey(DialogInterface dialoginterface, int k, KeyEvent keyevent)
            {
                activity.onKeyDown(k, keyevent);
                return false;
            }

            private final Activity val$activity;

            
            {
                activity = activity1;
                super();
            }
        }
);
        return progressdialog;
    }

    public static int getWindowFeatures(Activity activity)
    {
        if(activity.getWindow() != null);
        return 0;
    }

    static void handleApplicationClosing(Context context, int i)
    {
        if(i == 4)
        {
            List list = ((ActivityManager)context.getSystemService("activity")).getRunningTasks(2);
            android.app.ActivityManager.RunningTaskInfo runningtaskinfo = (android.app.ActivityManager.RunningTaskInfo)list.get(0);
            android.app.ActivityManager.RunningTaskInfo runningtaskinfo1 = (android.app.ActivityManager.RunningTaskInfo)list.get(1);
            if(runningtaskinfo.topActivity.equals(runningtaskinfo.baseActivity) && runningtaskinfo1.baseActivity.getPackageName().startsWith("com.android.launcher"))
                ((DroidFuApplication)context.getApplicationContext()).onClose();
        }
    }

    public static boolean isApplicationBroughtToBackground(Context context)
    {
        List list = ((ActivityManager)context.getSystemService("activity")).getRunningTasks(1);
        boolean flag;
        if(!list.isEmpty() && !((android.app.ActivityManager.RunningTaskInfo)list.get(0)).topActivity.getPackageName().equals(context.getPackageName()))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static AlertDialog newErrorHandlerDialog(final Activity activity, String s, Exception exception)
    {
        String s1;
        android.app.AlertDialog.Builder builder;
        if(exception instanceof ResourceMessageException)
            s1 = activity.getString(((ResourceMessageException)exception).getClientMessageResourceId());
        else
            s1 = exception.getLocalizedMessage();
        builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.setIcon(0x1080027);
        builder.setCancelable(false);
        builder.setPositiveButton(activity.getString(0x104000a), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
            }

        }
);
        if(IntentSupport.isIntentAvailable(activity, "android.intent.action.SEND", "message/rfc822"))
            builder.setNegativeButton(activity.getString(activity.getResources().getIdentifier("droidfu_dialog_button_send_error_report", "string", activity.getPackageName())), new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int i)
                {
                    dialoginterface.dismiss();
                    activity.startActivity(intent);
                }

                private final Activity val$activity;
                private final Intent val$intent;

            
            {
                activity = activity1;
                intent = intent1;
                super();
            }
            }
);
        return builder.create();
    }

    public static Dialog newListDialog(Activity activity, String s, List list, DialogClickListener dialogclicklistener, boolean flag)
    {
        return newListDialog(activity, s, list, dialogclicklistener, flag, 0);
    }

    public static Dialog newListDialog(Activity activity, String s, final List elements, final DialogClickListener listener, final boolean closeOnSelect, int i)
    {
        int j = elements.size();
        String as[] = new String[j];
        int k = 0;
        do
        {
            if(k >= j)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                if(s != null)
                    builder.setTitle(s);
                builder.setSingleChoiceItems(as, i, new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int l)
                    {
                        if(closeOnSelect)
                            dialoginterface.dismiss();
                        listener.onClick(l, elements.get(l));
                    }

                    private final boolean val$closeOnSelect;
                    private final List val$elements;
                    private final DialogClickListener val$listener;

            
            {
                closeOnSelect = flag;
                listener = dialogclicklistener;
                elements = list;
                super();
            }
                }
);
                return builder.create();
            }
            as[k] = elements.get(k).toString();
            k++;
        } while(true);
    }

    public static AlertDialog newMessageDialog(Context context, String s, String s1, int i)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("Okay", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

        }
);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.setIcon(i);
        return builder.create();
    }

    public static AlertDialog newYesNoDialog(Context context, String s, String s1, int i, android.content.DialogInterface.OnClickListener onclicklistener)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton(0x1040013, onclicklistener);
        builder.setNegativeButton(0x1040009, onclicklistener);
        builder.setTitle(s);
        builder.setMessage(s1);
        builder.setIcon(i);
        return builder.create();
    }

    public static final String ERROR_DIALOG_TITLE_RESOURCE = "droidfu_error_dialog_title";
    private static final String PROGRESS_DIALOG_MESSAGE_RESOURCE = "droidfu_progress_dialog_message";
    private static final String PROGRESS_DIALOG_TITLE_RESOURCE = "droidfu_progress_dialog_title";
}
