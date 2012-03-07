// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.markupartist.android.widget;

import android.content.*;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import java.util.LinkedList;

public class ActionBar extends RelativeLayout
    implements android.view.View.OnClickListener
{
    public static abstract class AbstractAction
        implements Action
    {

        public int getDrawable()
        {
            return mDrawable;
        }

        private final int mDrawable;

        public AbstractAction(int i)
        {
            mDrawable = i;
        }
    }

    public static interface Action
    {

        public abstract int getDrawable();

        public abstract void performAction(View view);
    }

    public static class ActionList extends LinkedList
    {

        public ActionList()
        {
        }
    }

    public static class IntentAction extends AbstractAction
    {

        public void performAction(View view)
        {
            mContext.startActivity(mIntent);
_L1:
            return;
            ActivityNotFoundException activitynotfoundexception;
            activitynotfoundexception;
            Toast.makeText(mContext, mContext.getText(0x7f070003), 0).show();
              goto _L1
        }

        private Context mContext;
        private Intent mIntent;

        public IntentAction(Context context, Intent intent, int i)
        {
            super(i);
            mContext = context;
            mIntent = intent;
        }
    }


    public ActionBar(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        mBarView = (RelativeLayout)mInflater.inflate(0x7f030002, null);
        addView(mBarView);
        mLogoView = (ImageView)mBarView.findViewById(0x7f0b000f);
        mHomeLayout = (RelativeLayout)mBarView.findViewById(0x7f0b0010);
        mHomeBtn = (ImageButton)mBarView.findViewById(0x7f0b0011);
        mBackIndicator = mBarView.findViewById(0x7f0b0012);
        mTitleView = (TextView)mBarView.findViewById(0x7f0b0015);
        mActionsView = (LinearLayout)mBarView.findViewById(0x7f0b0013);
        mProgress = (ProgressBar)mBarView.findViewById(0x7f0b0014);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, com.markupartist.android.widget.actionbar.R.styleable.ActionBar);
        String s = typedarray.getString(0);
        if(s != null)
            setTitle(s);
        typedarray.recycle();
    }

    private View inflateAction(Action action)
    {
        View view = mInflater.inflate(0x7f030003, mActionsView, false);
        ((ImageButton)view.findViewById(0x7f0b0016)).setImageResource(action.getDrawable());
        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    public void addAction(Action action)
    {
        addAction(action, mActionsView.getChildCount());
    }

    public void addAction(Action action, int i)
    {
        mActionsView.addView(inflateAction(action), i);
    }

    public void addActions(ActionList actionlist)
    {
        int i = actionlist.size();
        int j = 0;
        do
        {
            if(j >= i)
                return;
            addAction((Action)actionlist.get(j));
            j++;
        } while(true);
    }

    public void clearHomeAction()
    {
        mHomeLayout.setVisibility(8);
    }

    public int getActionCount()
    {
        return mActionsView.getChildCount();
    }

    public int getProgressBarVisibility()
    {
        return mProgress.getVisibility();
    }

    public void onClick(View view)
    {
        Object obj = view.getTag();
        if(obj instanceof Action)
            ((Action)obj).performAction(view);
    }

    public void removeAction(Action action)
    {
        int i = mActionsView.getChildCount();
        int j = 0;
        do
        {
            if(j >= i)
                return;
            View view = mActionsView.getChildAt(j);
            if(view != null)
            {
                Object obj = view.getTag();
                if((obj instanceof Action) && obj.equals(action))
                    mActionsView.removeView(view);
            }
            j++;
        } while(true);
    }

    public void removeActionAt(int i)
    {
        mActionsView.removeViewAt(i);
    }

    public void removeAllActions()
    {
        mActionsView.removeAllViews();
    }

    public void setDisplayHomeAsUpEnabled(boolean flag)
    {
        View view = mBackIndicator;
        int i;
        if(flag)
            i = 0;
        else
            i = 8;
        view.setVisibility(i);
    }

    public void setHomeAction(Action action)
    {
        mHomeBtn.setOnClickListener(this);
        mHomeBtn.setTag(action);
        mHomeBtn.setImageResource(action.getDrawable());
        mHomeLayout.setVisibility(0);
    }

    public void setHomeLogo(int i)
    {
        mLogoView.setImageResource(i);
        mLogoView.setVisibility(0);
        mHomeLayout.setVisibility(8);
    }

    public void setOnTitleClickListener(android.view.View.OnClickListener onclicklistener)
    {
        mTitleView.setOnClickListener(onclicklistener);
    }

    public void setProgressBarVisibility(int i)
    {
        mProgress.setVisibility(i);
    }

    public void setTitle(int i)
    {
        mTitleView.setText(i);
    }

    public void setTitle(CharSequence charsequence)
    {
        mTitleView.setText(charsequence);
    }

    private LinearLayout mActionsView;
    private View mBackIndicator;
    private RelativeLayout mBarView;
    private ImageButton mHomeBtn;
    private RelativeLayout mHomeLayout;
    private LayoutInflater mInflater;
    private ImageView mLogoView;
    private ProgressBar mProgress;
    private TextView mTitleView;
}
