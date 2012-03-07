// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.*;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            AddressBookActivity

public class SelectSlotActivity extends AFBaseActivity
{

    static int[] $SWITCH_TABLE$com$demiroot$freshclient$DeliveryWindow$Availability()
    {
        int ai[] = $SWITCH_TABLE$com$demiroot$freshclient$DeliveryWindow$Availability;
        if(ai == null)
        {
            ai = new int[com.demiroot.freshclient.DeliveryWindow.Availability.values().length];
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.AVAILABLE.ordinal()] = 3;
            }
            catch(NoSuchFieldError nosuchfielderror) { }
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.FULL.ordinal()] = 1;
            }
            catch(NoSuchFieldError nosuchfielderror1) { }
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.GREEN.ordinal()] = 4;
            }
            catch(NoSuchFieldError nosuchfielderror2) { }
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.SELECTED.ordinal()] = 6;
            }
            catch(NoSuchFieldError nosuchfielderror3) { }
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.UNAVAILABLE.ordinal()] = 2;
            }
            catch(NoSuchFieldError nosuchfielderror4) { }
            try
            {
                ai[com.demiroot.freshclient.DeliveryWindow.Availability.UNKNOWN.ordinal()] = 5;
            }
            catch(NoSuchFieldError nosuchfielderror5) { }
            $SWITCH_TABLE$com$demiroot$freshclient$DeliveryWindow$Availability = ai;
        }
        return ai;
    }

    public SelectSlotActivity()
    {
        showingAttended = false;
        activeDay = null;
        slotReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                try
                {
                    slots = (DeliverySlots)intent.getSerializableExtra("response");
                    Address address = slots.getAddress();
                    if(address == null)
                    {
                        findViewById(0x7f0b00a7).setVisibility(8);
                    } else
                    {
                        String s = address.getAddress1();
                        if(address.getAddress2() != null)
                            s = (new StringBuilder(String.valueOf(s))).append(address.getAddress2()).toString();
                        if(s.length() > 26)
                            s = (new StringBuilder(String.valueOf(s.substring(0, 23)))).append("...").toString();
                        ((TextView)findViewById(0x7f0b001b)).setText((new StringBuilder(String.valueOf(getString(0x7f0700d1)))).append(" ").append(s).toString());
                        ((TextView)findViewById(0x7f0b00a8)).setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }
                catch(Exception exception)
                {
                    Log.e("ERROR_MSG", "Error loading slots", exception);
                    break MISSING_BLOCK_LABEL_267;
                }
                if(!showingAttended) goto _L2; else goto _L1
_L1:
                loadSlots(slots.getAttended(), 0x7f0b00ae);
_L4:
                showHideSlots();
                break MISSING_BLOCK_LABEL_267;
_L2:
                loadSlots(slots.getUnattended(), 0x7f0b00af);
                if(true) goto _L4; else goto _L3
_L3:
            }

            final SelectSlotActivity this$0;

            
            {
                this$0 = SelectSlotActivity.this;
                super(afbaseactivity);
            }
        }
;
        slotLoadListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                int i = view.getId();
                if(0x7f0b00ab == i)
                {
                    showingAttended = true;
                    findViewById(0x7f0b00ac).setVisibility(4);
                    findViewById(0x7f0b00ad).setVisibility(0);
                } else
                if(0x7f0b00aa == i)
                {
                    showingAttended = false;
                    findViewById(0x7f0b00ac).setVisibility(0);
                    findViewById(0x7f0b00ad).setVisibility(4);
                } else
                if(0x7f0b00b3 == i)
                {
                    GregorianCalendar gregoriancalendar = new GregorianCalendar();
                    gregoriancalendar.setTime(activeDay);
                    gregoriancalendar.add(6, -1);
                    activeDay = gregoriancalendar.getTime();
                    if(activeDay.before(getFirstDate(slots.getAttended())))
                        activeDay = getFirstDate(slots.getAttended());
                } else
                if(0x7f0b00b5 == i)
                {
                    GregorianCalendar gregoriancalendar1 = new GregorianCalendar();
                    gregoriancalendar1.setTime(activeDay);
                    gregoriancalendar1.add(6, 1);
                    activeDay = gregoriancalendar1.getTime();
                    if(activeDay.after(getLastDate(slots.getAttended())))
                    {
                        Toast.makeText(SelectSlotActivity.this, "no more next", 1).show();
                        activeDay = getLastDate(slots.getAttended());
                    }
                }
                if(slots != null)
                {
                    SelectSlotActivity selectslotactivity = SelectSlotActivity.this;
                    List list;
                    int j;
                    if(showingAttended)
                        list = slots.getAttended();
                    else
                        list = slots.getUnattended();
                    if(showingAttended)
                        j = 0x7f0b00ae;
                    else
                        j = 0x7f0b00af;
                    selectslotactivity.loadSlots(list, j);
                }
                showHideSlots();
            }

            final SelectSlotActivity this$0;

            
            {
                this$0 = SelectSlotActivity.this;
                super();
            }
        }
;
    }

    private List createRows(LayoutInflater layoutinflater, List list)
    {
        int ai[] = new int[4];
        ai[0] = 0x7f0b00b2;
        ai[1] = 0x7f0b00b3;
        ai[2] = 0x7f0b00b4;
        ai[3] = 0x7f0b00b5;
        TreeMap treemap = new TreeMap();
        int i = 0;
        Iterator iterator = list.iterator();
        do
        {
            SlotDay slotday;
            do
            {
                if(!iterator.hasNext())
                    return new LinkedList(treemap.values());
                slotday = (SlotDay)iterator.next();
                i++;
            } while(slotday == null);
            Iterator iterator1 = slotday.getDeliveryWindows().iterator();
            while(iterator1.hasNext()) 
            {
                DeliveryWindow deliverywindow = (DeliveryWindow)iterator1.next();
                TableRow tablerow = (TableRow)treemap.get(Integer.valueOf(getStartHour(deliverywindow)));
                if(tablerow == null)
                {
                    tablerow = (TableRow)layoutinflater.inflate(0x7f030027, null);
                    treemap.put(Integer.valueOf(getStartHour(deliverywindow)), tablerow);
                    ((TextView)tablerow.findViewById(0x7f0b00b2)).setText(StringUtils.timeWindow(deliverywindow));
                }
                loadButton(tablerow, (Button)tablerow.findViewById(ai[i]), deliverywindow);
            }
        } while(true);
    }

    private Date getFirstDate(List list)
    {
        Date date = null;
        Date date1 = null;
        int i = 0;
label0:
        do
        {
            if(list.size() <= i || date != null && date1 != null && !date1.before(new Date()))
                return date;
            int j = i + 1;
            SlotDay slotday = (SlotDay)list.get(i);
            date = slotday.getDate();
            Iterator iterator = slotday.getDeliveryWindows().iterator();
            DeliveryWindow deliverywindow;
            do
            {
                if(!iterator.hasNext())
                {
                    i = j;
                    continue label0;
                }
                deliverywindow = (DeliveryWindow)iterator.next();
            } while(deliverywindow.getAvailability() != com.demiroot.freshclient.DeliveryWindow.Availability.AVAILABLE && deliverywindow.getAvailability() != com.demiroot.freshclient.DeliveryWindow.Availability.GREEN);
            date1 = deliverywindow.getStartTime();
            i = j;
        } while(true);
    }

    private Date getLastDate(List list)
    {
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        gregoriancalendar.setTime(((SlotDay)list.get(Math.max(list.size() - 3, 0))).getDate());
        return gregoriancalendar.getTime();
    }

    private TableRow getNavRow(LayoutInflater layoutinflater)
    {
        TableRow tablerow = (TableRow)layoutinflater.inflate(0x7f030027, null);
        Button button = (Button)tablerow.findViewById(0x7f0b00b3);
        button.setText("<");
        button.setOnClickListener(slotLoadListener);
        boolean flag;
        Button button1;
        boolean flag1;
        if(activeDay.equals(getFirstDate(slots.getAttended())))
            flag = false;
        else
            flag = true;
        button.setEnabled(flag);
        button1 = (Button)tablerow.findViewById(0x7f0b00b5);
        button1.setText(">");
        button1.setOnClickListener(slotLoadListener);
        if(activeDay.equals(getLastDate(slots.getAttended())))
            flag1 = false;
        else
            flag1 = true;
        button1.setEnabled(flag1);
        ((Button)tablerow.findViewById(0x7f0b00b4)).setVisibility(4);
        tablerow.setVisibility(0);
        return tablerow;
    }

    private int getStartHour(DeliveryWindow deliverywindow)
    {
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        gregoriancalendar.setTime(deliverywindow.getStartTime());
        return gregoriancalendar.get(11);
    }

    private void loadButton(TableRow tablerow, Button button, final DeliveryWindow dw)
    {
        $SWITCH_TABLE$com$demiroot$freshclient$DeliveryWindow$Availability()[dw.getAvailability().ordinal()];
        JVM INSTR tableswitch 3 6: default 40
    //                   3 159
    //                   4 139
    //                   5 221
    //                   6 173;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        button.setText(getString(0x7f07007c));
        button.getBackground().setColorFilter(new LightingColorFilter(0xf2f2f2, 0x444444));
        button.setTextColor(getResources().getColorStateList(0x7f090025));
        button.setEnabled(false);
_L7:
        if(dw.getAvailability() == com.demiroot.freshclient.DeliveryWindow.Availability.GREEN || dw.getAvailability() == com.demiroot.freshclient.DeliveryWindow.Availability.AVAILABLE)
            button.setOnClickListener(new android.view.View.OnClickListener() );
        if(dw.getAvailability() != com.demiroot.freshclient.DeliveryWindow.Availability.UNAVAILABLE)
            tablerow.setVisibility(0);
        return;
_L3:
        button.getBackground().setColorFilter(new LightingColorFilter(0x99cc33, 0x282828));
_L2:
        button.setText(getString(0x7f07007a));
        continue; /* Loop/switch isn't completed */
_L5:
        button.setText(getString(0x7f07007b));
        button.setTextColor(getResources().getColorStateList(0x7f090026));
        button.getBackground().setColorFilter(new LightingColorFilter(0x363636, 0x363636));
        continue; /* Loop/switch isn't completed */
_L4:
        button.setText("?");
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void loadSlots(List list, int i)
    {
        TableLayout tablelayout = (TableLayout)findViewById(i);
        if(activeDay != null) goto _L2; else goto _L1
_L1:
        if(list != null && !list.isEmpty()) goto _L4; else goto _L3
_L3:
        showMessage(getString(0x7f070079));
_L10:
        return;
_L4:
        activeDay = getFirstDate(list);
_L2:
        LayoutInflater layoutinflater;
        TableRow tablerow;
        GregorianCalendar gregoriancalendar;
        int ai[];
        int j;
        tablelayout.removeAllViews();
        layoutinflater = LayoutInflater.from(this);
        tablerow = (TableRow)layoutinflater.inflate(0x7f030026, tablelayout, false);
        gregoriancalendar = new GregorianCalendar();
        gregoriancalendar.setTime(activeDay);
        ai = new int[3];
        ai[0] = 0x7f0b00b3;
        ai[1] = 0x7f0b00b4;
        ai[2] = 0x7f0b00b5;
        j = 0;
_L11:
        if(j < 3) goto _L6; else goto _L5
_L5:
        LinkedList linkedlist;
        int k;
        GregorianCalendar gregoriancalendar1;
        layoutinflater.inflate(0x7f030025, tablelayout, true);
        tablelayout.addView(tablerow);
        tablelayout.addView(getNavRow(layoutinflater));
        linkedlist = new LinkedList();
        k = 0;
        gregoriancalendar1 = new GregorianCalendar();
        gregoriancalendar1.setTime(activeDay);
_L12:
        if(linkedlist.size() >= 3) goto _L8; else goto _L7
_L7:
        int l = list.size();
        if(k < l) goto _L9; else goto _L8
_L8:
        boolean flag;
        Iterator iterator;
        flag = true;
        iterator = createRows(layoutinflater, linkedlist).iterator();
_L13:
        if(iterator.hasNext())
            break MISSING_BLOCK_LABEL_386;
        tablelayout.getParent().requestLayout();
          goto _L10
_L6:
        ((TextView)tablerow.findViewById(ai[j])).setText(StringUtils.slotDate(gregoriancalendar));
        gregoriancalendar.add(6, 1);
        j++;
          goto _L11
_L9:
        if(((SlotDay)list.get(k)).getDate().equals(gregoriancalendar1.getTime()))
        {
            linkedlist.add((SlotDay)list.get(k));
            gregoriancalendar1.add(6, 1);
        } else
        if(((SlotDay)list.get(k)).getDate().after(gregoriancalendar1.getTime()))
        {
            linkedlist.add(null);
            gregoriancalendar1.add(6, 1);
        }
        k++;
          goto _L12
        TableRow tablerow1 = (TableRow)iterator.next();
        if(tablerow1.getVisibility() == 0)
        {
            tablelayout.addView(tablerow1);
            flag = false;
        } else
        if(!flag)
        {
            layoutinflater.inflate(0x7f030025, tablelayout, true);
            flag = true;
        }
          goto _L13
    }

    private void showHideSlots()
    {
        View view = findViewById(0x7f0b00af);
        byte byte0;
        View view1;
        int i;
        View view2;
        boolean flag;
        if(showingAttended)
            byte0 = 8;
        else
            byte0 = 0;
        view.setVisibility(byte0);
        view1 = findViewById(0x7f0b00ae);
        if(showingAttended)
            i = 0;
        else
            i = 8;
        view1.setVisibility(i);
        findViewById(0x7f0b00aa).setEnabled(showingAttended);
        view2 = findViewById(0x7f0b00ab);
        if(showingAttended)
            flag = false;
        else
            flag = true;
        view2.setEnabled(flag);
    }

    public void changeAddress(View view)
    {
        startActivityForResult(new Intent(this, com/demiroot/amazonfresh/activities/AddressBookActivity), 0);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        onActivityResult(i, j, intent);
        if(i == 0 && j == -1)
        {
            externalAddressId = (String)intent.getSerializableExtra("externalAddressId");
            showLoadingBar(getString(0x7f070056));
            broadcastAvailableSlotsForCart(externalAddressId);
        }
    }

    protected void onCreate(Bundle bundle)
    {
        onCreate(bundle);
        setContentView(0x7f030023);
        loadActionBar(getString(0x7f070078));
        ((Button)findViewById(0x7f0b00ab)).setOnClickListener(slotLoadListener);
        ((Button)findViewById(0x7f0b00aa)).setOnClickListener(slotLoadListener);
    }

    protected void onResume()
    {
        onResume();
        showLoadingBar(getString(0x7f070056));
        broadcastAvailableSlotsForCart(externalAddressId);
    }

    protected void onStart()
    {
        onStart();
        registerReceiver(slotReceiver, new IntentFilter("com.demiroot.freshclient.DeliverySlots"));
    }

    protected void onStop()
    {
        onStop();
        unregisterReceiver(slotReceiver);
    }

    private static int $SWITCH_TABLE$com$demiroot$freshclient$DeliveryWindow$Availability[];
    Date activeDay;
    private String externalAddressId;
    boolean showingAttended;
    private android.view.View.OnClickListener slotLoadListener;
    private BroadcastReceiver slotReceiver;
    volatile DeliverySlots slots;






}
