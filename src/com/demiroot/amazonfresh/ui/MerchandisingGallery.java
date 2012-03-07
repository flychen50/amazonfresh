// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.activities.SearchUsingWidgetActivity;
import com.demiroot.freshclient.GatewayPane;
import com.github.droidfu.widgets.WebImageView;
import java.util.List;

public class MerchandisingGallery extends BaseAdapter
{
    static class ViewHolder
    {

        View box;
        WebImageView merchImage;
        TextView merchTitle;

        ViewHolder()
        {
        }
    }


    public MerchandisingGallery(Context context, List list)
    {
        itemClickListener = new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                int j = convertPosition(i);
                GatewayPane gatewaypane = (GatewayPane)panes.get(j);
                Toast.makeText(mBaseActivity, gatewaypane.getTagline(), 1).show();
                Intent intent = new Intent(mBaseActivity, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
                intent.putExtra("merchSearch", true);
                intent.putExtra("merchUrl", gatewaypane.getResourceUrl());
                intent.putExtra("SearchTitle", gatewaypane.getTitle());
                mBaseActivity.startActivity(intent);
            }

            final MerchandisingGallery this$0;

            
            {
                this$0 = MerchandisingGallery.this;
                super();
            }
        }
;
        itemSelectListener = new android.widget.AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView adapterview, View view, int i, long l)
            {
                if(lastSelectedView != null)
                {
                    lastSelectedView.setLayoutParams(new android.widget.Gallery.LayoutParams(outOfFocus, outOfFocus + offset));
                    lastSelectedView.setPadding(0, 0, 0, 0);
                }
                view.setLayoutParams(new android.widget.Gallery.LayoutParams(inFocus, inFocus));
                view.setPadding(offset / 3, 0, 0, 0);
                lastSelectedView = view;
            }

            public void onNothingSelected(AdapterView adapterview)
            {
            }

            View lastSelectedView;
            final MerchandisingGallery this$0;

            
            {
                this$0 = MerchandisingGallery.this;
                super();
                lastSelectedView = null;
            }
        }
;
        mContext = context;
        mBaseActivity = (AFBaseActivity)context;
        panes = list;
        mContext.obtainStyledAttributes(com.demiroot.amazonfresh.R.styleable.MerchandisingGallery).recycle();
        mInflater = LayoutInflater.from(context);
        outOfFocus = dipToPixel(100);
        inFocus = dipToPixel(125);
        offset = dipToPixel(25);
    }

    private int dipToPixel(int i)
    {
        return (int)TypedValue.applyDimension(1, i, mBaseActivity.getResources().getDisplayMetrics());
    }

    public int convertPosition(int i)
    {
        int j = i % panes.size();
        if(j < 0)
            j += panes.size();
        return j;
    }

    public int getCount()
    {
        return 0x7fffffff;
    }

    public Object getItem(int i)
    {
        return Integer.valueOf(convertPosition(i));
    }

    public long getItemId(int i)
    {
        return (long)convertPosition(i);
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        int j = convertPosition(i);
        GatewayPane gatewaypane = (GatewayPane)panes.get(j);
        ViewHolder viewholder;
        String s;
        if(view == null)
        {
            view = mInflater.inflate(0x7f03000f, null);
            viewholder = new ViewHolder();
            viewholder.box = view.findViewById(0x7f0b0056);
            viewholder.merchImage = (WebImageView)view.findViewById(0x7f0b0057);
            viewholder.merchImage.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            viewholder.merchTitle = (TextView)view.findViewById(0x7f0b0058);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.merchTitle.setText(gatewaypane.getTitle());
        s = gatewaypane.getImageUrl();
        viewholder.merchImage.reset();
        if(s != null)
        {
            viewholder.merchImage.setImageUrl(s);
            viewholder.merchImage.loadImage();
        }
        return view;
    }

    int inFocus;
    public android.widget.AdapterView.OnItemClickListener itemClickListener;
    public android.widget.AdapterView.OnItemSelectedListener itemSelectListener;
    private final AFBaseActivity mBaseActivity;
    private Context mContext;
    private LayoutInflater mInflater;
    int offset;
    int outOfFocus;
    private List panes;


}
