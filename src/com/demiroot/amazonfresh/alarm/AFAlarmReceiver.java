// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.alarm;

import android.app.*;
import android.content.*;
import com.demiroot.amazonfresh.activities.GatewayMenuActivity;
import java.util.*;

public class AFAlarmReceiver extends BroadcastReceiver
{

    public AFAlarmReceiver()
    {
    }

    private static String getCutoffHour(Calendar calendar)
    {
        int i = calendar.get(10);
        String s;
        if(i == 0)
            s = "2:00am";
        else
        if(i < 6)
            s = "midnight";
        else
        if(i < 12)
            s = "3:00am";
        else
            s = "1:00pm";
        return s;
    }

    private static Calendar getCutoffTime(Calendar calendar)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(calendar.getTime());
        int i = calendar.get(10);
        if(i == 0)
        {
            calendar1 = Calendar.getInstance();
            calendar1.add(13, 20);
        } else
        if(i < 6)
            calendar1.set(10, 0);
        else
        if(i < 12)
            calendar1.set(10, 3);
        else
            calendar1.set(10, 13);
        return calendar1;
    }

    public static void setNextAlarm(Context context, ArrayList arraylist)
    {
        AlarmManager alarmmanager = (AlarmManager)context.getSystemService("alarm");
        alarmmanager.cancel(PendingIntent.getBroadcast(context, 0xe84ba, new Intent(), 0x10000000));
        if(arraylist != null && !arraylist.isEmpty())
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)arraylist.get(0));
            setToLastCallTime(calendar);
            if(calendar.before(Calendar.getInstance()))
            {
                arraylist.remove(0);
                setNextAlarm(context, arraylist);
            } else
            {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime((Date)arraylist.get(0));
                Intent intent = new Intent(context, com/demiroot/amazonfresh/alarm/AFAlarmReceiver);
                intent.setAction("show_note");
                intent.putExtra("orders", arraylist);
                PendingIntent pendingintent = PendingIntent.getBroadcast(context, 0xe84ba, intent, 0x8000000);
                alarmmanager.set(0, calendar.getTimeInMillis(), pendingintent);
                Intent intent1 = new Intent(context, com/demiroot/amazonfresh/alarm/AFAlarmReceiver);
                intent1.setAction("hide_note");
                PendingIntent pendingintent1 = PendingIntent.getBroadcast(context, 0xe84ba, intent1, 0x8000000);
                alarmmanager.set(0, getCutoffTime(calendar1).getTimeInMillis(), pendingintent1);
            }
        }
    }

    private static void setToLastCallTime(Calendar calendar)
    {
        int i = calendar.get(10);
        if(i == 0)
        {
            calendar.setTime(new Date());
            calendar.add(13, 5);
        } else
        if(i < 12)
        {
            calendar.add(6, -1);
            calendar.set(10, 22);
        } else
        {
            calendar.set(10, 11);
        }
    }

    public void onReceive(Context context, Intent intent)
    {
        if(context.getSharedPreferences("SHARED_PREFS_KEY", 1).getBoolean("enable_notices", true)) goto _L2; else goto _L1
_L1:
        return;
_L2:
        NotificationManager notificationmanager = (NotificationManager)context.getSystemService("notification");
        if(intent.getAction().equals("show_note"))
        {
            ArrayList arraylist = (ArrayList)intent.getSerializableExtra("orders");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date)arraylist.remove(0));
            setNextAlarm(context, arraylist);
            Notification notification = new Notification(0x7f020017, context.getString(0x7f070086), System.currentTimeMillis());
            PendingIntent pendingintent = PendingIntent.getActivity(context, 0, new Intent(context, com/demiroot/amazonfresh/activities/GatewayMenuActivity), 0);
            String s = context.getString(0x7f070086);
            Object aobj[] = new Object[1];
            aobj[0] = getCutoffHour(calendar);
            notification.setLatestEventInfo(context, s, context.getString(0x7f070087, aobj), pendingintent);
            notificationmanager.notify(0xe84ba, notification);
        } else
        if(intent.getAction().equals("hide_note"))
            notificationmanager.cancel(0xe84ba);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static final String ENABLE_NOTICES_PREF = "enable_notices";
    public static final String HIDE_NOTE_ACTION = "hide_note";
    public static final int ORDER_ALARM_REQUEST_CODE = 0xe84ba;
    public static final String ORDER_LIST_KEY = "orders";
    public static final String SHOW_NOTE_ACTION = "show_note";
}
