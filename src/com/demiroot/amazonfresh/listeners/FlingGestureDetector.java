// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.listeners;

import android.view.*;

public class FlingGestureDetector extends android.view.GestureDetector.SimpleOnGestureListener
{
    public static final class Action extends Enum
    {

        public static Action valueOf(String s)
        {
            return (Action)Enum.valueOf(com/demiroot/amazonfresh/listeners/FlingGestureDetector$Action, s);
        }

        public static Action[] values()
        {
            Action aaction[] = ENUM$VALUES;
            int i = aaction.length;
            Action aaction1[] = new Action[i];
            System.arraycopy(aaction, 0, aaction1, 0, i);
            return aaction1;
        }

        private static final Action ENUM$VALUES[];
        public static final Action SWIPE_LEFT;
        public static final Action SWIPE_RIGHT;

        static 
        {
            SWIPE_RIGHT = new Action("SWIPE_RIGHT", 0);
            SWIPE_LEFT = new Action("SWIPE_LEFT", 1);
            Action aaction[] = new Action[2];
            aaction[0] = SWIPE_RIGHT;
            aaction[1] = SWIPE_LEFT;
            ENUM$VALUES = aaction;
        }

        private Action(String s, int i)
        {
            super(s, i);
        }
    }

    public static interface GestureResponse
    {

        public abstract void gested(Action action);
    }


    public FlingGestureDetector(GestureResponse gestureresponse)
    {
        callBack = gestureresponse;
    }

    public static android.view.View.OnTouchListener createGestureDetector(GestureResponse gestureresponse)
    {
        return new android.view.View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionevent)
            {
                boolean flag;
                if(gt.onTouchEvent(motionevent))
                    flag = true;
                else
                    flag = false;
                return flag;
            }

            private final GestureDetector val$gt;

            
            {
                gt = gesturedetector;
                super();
            }
        }
;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        try
        {
            if(Math.abs(motionevent.getY() - motionevent1.getY()) <= 250F)
                if(motionevent.getX() - motionevent1.getX() > 120F && Math.abs(f) > 200F)
                    callBack.gested(Action.SWIPE_RIGHT);
                else
                if(motionevent1.getX() - motionevent.getX() > 120F && Math.abs(f) > 200F)
                    callBack.gested(Action.SWIPE_LEFT);
        }
        catch(Exception exception) { }
        return false;
    }

    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureResponse callBack;
}
