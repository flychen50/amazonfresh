// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.app.Activity;
import android.content.*;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import com.demiroot.amazonfresh.activities.CartActivity;
import com.demiroot.amazonfresh.activities.GatewayMenuActivity;
import com.demiroot.amazonfresh.util.SearchActionBarActivity;
import com.demiroot.freshclient.Order;
import com.markupartist.android.widget.ActionBar;

public class ActionBarHelper extends BroadcastReceiver
{

    public ActionBarHelper(Activity activity)
    {
        mActivity = activity;
    }

    private Intent createIntent(Context context)
    {
        Intent intent = new Intent(context, com/demiroot/amazonfresh/activities/GatewayMenuActivity);
        intent.addFlags(0x4000000);
        return intent;
    }

    private int dipToPixel(int i)
    {
        return (int)TypedValue.applyDimension(1, i, mActivity.getResources().getDisplayMetrics());
    }

    private Bitmap getCartIcon(int i)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), 0x7f020012);
        android.graphics.Bitmap.Config config = bitmap.getConfig();
        if(config == null)
            config = android.graphics.Bitmap.Config.ARGB_8888;
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(bitmap1);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        if(i > 0)
        {
            int j = canvas.getHeight() / 4;
            int k = j + 3;
            int l = canvas.getHeight() - j - 3 * 2;
            canvas.drawCircle(k, l, j, getCircleBorderPaint());
            canvas.drawCircle(k, l, j - 2, getCirclePaint());
            canvas.drawText((new StringBuilder(String.valueOf(i))).toString(), k, l + j / 3, getTextPaint(j));
        }
        return bitmap1;
    }

    private Paint getCircleBorderPaint()
    {
        Paint paint = new Paint();
        paint.setColor(mActivity.getResources().getColor(0x7f090016));
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getCirclePaint()
    {
        Paint paint = new Paint();
        paint.setColor(mActivity.getResources().getColor(0x7f090015));
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setAntiAlias(true);
        return paint;
    }

    private Paint getTextPaint(int i)
    {
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setTextAlign(android.graphics.Paint.Align.CENTER);
        paint.setTextSize(i);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        return paint;
    }

    public void display()
    {
        ActionBar actionbar = (ActionBar)mActivity.findViewById(0x7f0b007d);
        actionbar.setHomeAction(new com.markupartist.android.widget.ActionBar.IntentAction(mActivity, createIntent(mActivity), 0x7f02001b));
        actionbar.addAction(new SearchActionBarActivity() {

            public void performAction(View view)
            {
                mActivity.onSearchRequested();
            }

            final ActionBarHelper this$0;

            
            {
                this$0 = ActionBarHelper.this;
                super();
            }
        }
);
        cartAction = new com.markupartist.android.widget.ActionBar.IntentAction(mActivity, new Intent(mActivity, com/demiroot/amazonfresh/activities/CartActivity), 0x7f020012);
        actionbar.addAction(cartAction);
    }

    public void onReceive(Context context, Intent intent)
    {
        Order order = (Order)intent.getSerializableExtra("response");
        ActionBar actionbar = (ActionBar)mActivity.findViewById(0x7f0b007d);
        if(order != null && cartAction != null)
            ((ImageButton)actionbar.findViewWithTag(cartAction)).setImageBitmap(getCartIcon(order.getTotalQuantity()));
_L1:
        return;
        Exception exception;
        exception;
        context.removeStickyBroadcast(new Intent(com/demiroot/freshclient/Order.getName()));
        Log.e("ERROR_MSG", "Error recieving msg", exception);
          goto _L1
    }

    public void setTitle(String s)
    {
        ActionBar actionbar = (ActionBar)mActivity.findViewById(0x7f0b007d);
        if("show_logo_as_title".equals(s))
        {
            View view = actionbar.findViewById(0x7f0b0015);
            view.setBackgroundResource(0x7f02001a);
            view.setPadding(dipToPixel(500), dipToPixel(5), dipToPixel(500), dipToPixel(5));
        } else
        {
            actionbar.setTitle(s);
        }
    }

    public static final String SHOW_LOGO_AS_TITLE = "show_logo_as_title";
    private com.markupartist.android.widget.ActionBar.IntentAction cartAction;
    private final Activity mActivity;

}
