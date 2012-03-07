// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.QuickActionHelpers;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.PopupWindow;

public class CustomPopupWindow
{

    public CustomPopupWindow(View view)
    {
        background = null;
        anchor = view;
        window = new PopupWindow(view.getContext());
        window.setTouchInterceptor(new android.view.View.OnTouchListener() {

            public boolean onTouch(View view1, MotionEvent motionevent)
            {
                boolean flag;
                if(motionevent.getAction() == 4)
                {
                    window.dismiss();
                    flag = true;
                } else
                {
                    flag = false;
                }
                return flag;
            }

            final CustomPopupWindow this$0;

            
            {
                this$0 = CustomPopupWindow.this;
                super();
            }
        }
);
        windowManager = (WindowManager)view.getContext().getSystemService("window");
        onCreate();
    }

    public void dismiss()
    {
        window.dismiss();
    }

    protected void onCreate()
    {
    }

    protected void onShow()
    {
    }

    protected void preShow()
    {
        if(root == null)
            throw new IllegalStateException("setContentView was not called with a view to display.");
        onShow();
        if(background == null)
            window.setBackgroundDrawable(new BitmapDrawable());
        else
            window.setBackgroundDrawable(background);
        window.setWidth(-2);
        window.setHeight(-2);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setContentView(root);
    }

    public void setBackgroundDrawable(Drawable drawable)
    {
        background = drawable;
    }

    public void setContentView(int i)
    {
        setContentView(((LayoutInflater)anchor.getContext().getSystemService("layout_inflater")).inflate(i, null));
    }

    public void setContentView(View view)
    {
        root = view;
        window.setContentView(view);
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener ondismisslistener)
    {
        window.setOnDismissListener(ondismisslistener);
    }

    public void showDropDown()
    {
        showDropDown(0, 0);
    }

    public void showDropDown(int i, int j)
    {
        preShow();
        window.setAnimationStyle(0x7f08001f);
        window.showAsDropDown(anchor, i, j);
    }

    public void showLikeQuickAction()
    {
        showLikeQuickAction(0, 0);
    }

    public void showLikeQuickAction(int i, int j)
    {
        preShow();
        window.setAnimationStyle(0x7f080025);
        int ai[] = new int[2];
        anchor.getLocationOnScreen(ai);
        Rect rect = new Rect(ai[0], ai[1], ai[0] + anchor.getWidth(), ai[1] + anchor.getHeight());
        root.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        root.measure(-2, -2);
        int k = root.getMeasuredWidth();
        int l = root.getMeasuredHeight();
        int i1 = i + (windowManager.getDefaultDisplay().getWidth() - k) / 2;
        int j1 = j + (rect.top - l);
        if(l > anchor.getTop())
        {
            j1 = j + rect.bottom;
            window.setAnimationStyle(0x7f080021);
        }
        window.showAtLocation(anchor, 0, i1, j1);
    }

    protected final View anchor;
    private Drawable background;
    private View root;
    protected final PopupWindow window;
    protected final WindowManager windowManager;
}
