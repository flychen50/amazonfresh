// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.QuickActionHelpers;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import java.util.ArrayList;

// Referenced classes of package com.demiroot.amazonfresh.QuickActionHelpers:
//            CustomPopupWindow, ActionItem

public class QuickAction extends CustomPopupWindow
{

    public QuickAction(View view)
    {
        super(view);
        actionList = new ArrayList();
        context = view.getContext();
        inflater = (LayoutInflater)context.getSystemService("layout_inflater");
        root = (ViewGroup)inflater.inflate(0x7f03001c, null);
        mArrowDown = (ImageView)root.findViewById(0x7f0b0095);
        mArrowUp = (ImageView)root.findViewById(0x7f0b0091);
        setContentView(root);
        mTrackAnim = AnimationUtils.loadAnimation(view.getContext(), 0x7f040006);
        mTrackAnim.setInterpolator(new Interpolator() {

            public float getInterpolation(float f)
            {
                float f1 = 1.55F * f - 1.1F;
                return 1.2F - f1 * f1;
            }

            final QuickAction this$0;

            
            {
                this$0 = QuickAction.this;
                super();
            }
        }
);
        mTrack = (ViewGroup)root.findViewById(0x7f0b0093);
        animStyle = 4;
        animateTrack = true;
    }

    private void createActionList()
    {
        int i = 1;
        int j = 0;
        do
        {
            if(j >= actionList.size())
                return;
            View view = getActionItem(((ActionItem)actionList.get(j)).getTitle(), ((ActionItem)actionList.get(j)).getIcon(), ((ActionItem)actionList.get(j)).getListener());
            view.setFocusable(true);
            view.setClickable(true);
            mTrack.addView(view, i);
            i++;
            j++;
        } while(true);
    }

    private View getActionItem(String s, Drawable drawable, android.view.View.OnClickListener onclicklistener)
    {
        LinearLayout linearlayout = (LinearLayout)inflater.inflate(0x7f030001, null);
        ImageView imageview = (ImageView)linearlayout.findViewById(0x7f0b000b);
        TextView textview = (TextView)linearlayout.findViewById(0x7f0b000c);
        if(drawable != null)
            imageview.setImageDrawable(drawable);
        else
            imageview.setVisibility(8);
        if(s != null)
            textview.setText(s);
        else
            textview.setVisibility(8);
        if(onclicklistener != null)
            linearlayout.setOnClickListener(onclicklistener);
        return linearlayout;
    }

    private void setAnimationStyle(int i, int j, boolean flag)
    {
        int k;
        int l;
        k = 0x7f080020;
        l = j - mArrowUp.getMeasuredWidth() / 2;
        animStyle;
        JVM INSTR tableswitch 1 4: default 52
    //                   1 53
    //                   2 84
    //                   3 108
    //                   4 139;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return;
_L2:
        PopupWindow popupwindow5 = window;
        int l1;
        if(flag)
            l1 = 0x7f080023;
        else
            l1 = 0x7f08001f;
        popupwindow5.setAnimationStyle(l1);
        continue; /* Loop/switch isn't completed */
_L3:
        PopupWindow popupwindow4 = window;
        if(flag)
            k = 0x7f080024;
        popupwindow4.setAnimationStyle(k);
        continue; /* Loop/switch isn't completed */
_L4:
        PopupWindow popupwindow3 = window;
        int k1;
        if(flag)
            k1 = 0x7f080025;
        else
            k1 = 0x7f080021;
        popupwindow3.setAnimationStyle(k1);
        continue; /* Loop/switch isn't completed */
_L5:
        if(l <= i / 4)
        {
            PopupWindow popupwindow2 = window;
            int j1;
            if(flag)
                j1 = 0x7f080023;
            else
                j1 = 0x7f08001f;
            popupwindow2.setAnimationStyle(j1);
        } else
        if(l > i / 4 && l < 3 * (i / 4))
        {
            PopupWindow popupwindow1 = window;
            int i1;
            if(flag)
                i1 = 0x7f080025;
            else
                i1 = 0x7f080021;
            popupwindow1.setAnimationStyle(i1);
        } else
        {
            PopupWindow popupwindow = window;
            if(!flag);
            popupwindow.setAnimationStyle(k);
        }
        if(true) goto _L1; else goto _L6
_L6:
    }

    private void showArrow(int i, int j)
    {
        ImageView imageview;
        ImageView imageview1;
        int k;
        if(i == 0x7f0b0091)
            imageview = mArrowUp;
        else
            imageview = mArrowDown;
        if(i == 0x7f0b0091)
            imageview1 = mArrowDown;
        else
            imageview1 = mArrowUp;
        k = mArrowUp.getMeasuredWidth();
        imageview.setVisibility(0);
        ((android.view.ViewGroup.MarginLayoutParams)imageview.getLayoutParams()).leftMargin = j - k / 2;
        imageview1.setVisibility(4);
    }

    public void addActionItem(ActionItem actionitem)
    {
        actionList.add(actionitem);
    }

    public void animateTrack(boolean flag)
    {
        animateTrack = flag;
    }

    public void setAnimStyle(int i)
    {
        animStyle = i;
    }

    public void show()
    {
        preShow();
        int ai[] = new int[2];
        anchor.getLocationOnScreen(ai);
        Rect rect = new Rect(ai[0], ai[1], ai[0] + anchor.getWidth(), ai[1] + anchor.getHeight());
        root.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        root.measure(-2, -2);
        int i = root.getMeasuredWidth();
        int j = root.getMeasuredHeight();
        int k = windowManager.getDefaultDisplay().getWidth();
        int l = (k - i) / 2;
        int i1 = rect.top - j;
        boolean flag = true;
        if(j > anchor.getTop())
        {
            i1 = rect.bottom;
            flag = false;
        }
        int j1;
        if(flag)
            j1 = 0x7f0b0095;
        else
            j1 = 0x7f0b0091;
        showArrow(j1, rect.centerX());
        setAnimationStyle(k, rect.centerX(), flag);
        createActionList();
        window.showAtLocation(anchor, 0, l, i1);
        if(animateTrack)
            mTrack.startAnimation(mTrackAnim);
    }

    public static final int ANIM_AUTO = 4;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    private ArrayList actionList;
    private int animStyle;
    private boolean animateTrack;
    private final Context context;
    private final LayoutInflater inflater;
    private final ImageView mArrowDown;
    private final ImageView mArrowUp;
    private ViewGroup mTrack;
    private final Animation mTrackAnim;
    private final View root;
}
