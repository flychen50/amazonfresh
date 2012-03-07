// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.activities.QuicklistActivity;
import com.demiroot.amazonfresh.activities.SearchUsingWidgetActivity;
import java.util.List;

public class QuicklistListAdapter extends ArrayAdapter
{
    static class ViewHolder
    {

        ImageButton goButton;
        Button removeButton;
        TextView searchTerm;

        ViewHolder()
        {
        }
    }


    public QuicklistListAdapter(Context context, int i, List list)
    {
        super(context, i, list);
        inflater = LayoutInflater.from(context);
        activity = (AFBaseActivity)context;
        localList = list;
    }

    public int getCount()
    {
        return localList.size();
    }

    public volatile Object getItem(int i)
    {
        return getItem(i);
    }

    public String getItem(int i)
    {
        return (String)localList.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(final int position, View view, ViewGroup viewgroup)
    {
        ViewHolder viewholder;
        if(view == null)
        {
            view = inflater.inflate(0x7f03001e, null);
            viewholder = new ViewHolder();
            viewholder.goButton = (ImageButton)view.findViewById(0x7f0b0098);
            viewholder.removeButton = (Button)view.findViewById(0x7f0b009a);
            viewholder.searchTerm = (TextView)view.findViewById(0x7f0b0099);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.goButton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                Intent intent = new Intent(activity, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
                intent.putExtra("query", getItem(position));
                activity.startActivity(intent);
            }

            final QuicklistListAdapter this$0;
            private final int val$position;

            
            {
                this$0 = QuicklistListAdapter.this;
                position = i;
                super();
            }
        }
);
        viewholder.removeButton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                ((QuicklistActivity)activity).removeTerm(getItem(position));
                notifyDataSetChanged();
            }

            final QuicklistListAdapter this$0;
            private final int val$position;

            
            {
                this$0 = QuicklistListAdapter.this;
                position = i;
                super();
            }
        }
);
        viewholder.searchTerm.setText(getItem(position));
        view.invalidate();
        return view;
    }

    AFBaseActivity activity;
    LayoutInflater inflater;
    List localList;
}
