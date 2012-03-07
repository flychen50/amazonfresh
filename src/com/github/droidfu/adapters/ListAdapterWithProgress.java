// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.adapters;

import android.app.*;
import android.view.*;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapterWithProgress extends BaseAdapter
{

    public ListAdapterWithProgress(Activity activity, AbsListView abslistview, int i)
    {
        data = new ArrayList();
        listView = abslistview;
        progressView = activity.getLayoutInflater().inflate(i, abslistview, false);
    }

    public ListAdapterWithProgress(ExpandableListActivity expandablelistactivity, int i)
    {
        this(((Activity) (expandablelistactivity)), ((AbsListView) (expandablelistactivity.getExpandableListView())), i);
    }

    public ListAdapterWithProgress(ListActivity listactivity, int i)
    {
        this(((Activity) (listactivity)), ((AbsListView) (listactivity.getListView())), i);
    }

    private boolean isPositionOfProgressElement(int i)
    {
        boolean flag;
        if(isLoadingData && i == data.size())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void addAll(List list)
    {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List list, boolean flag)
    {
        data.addAll(list);
        if(flag)
            notifyDataSetChanged();
    }

    public boolean areAllItemsEnabled()
    {
        return false;
    }

    public void clear()
    {
        data.clear();
        notifyDataSetChanged();
    }

    protected abstract View doGetView(int i, View view, ViewGroup viewgroup);

    public int getCount()
    {
        int i = 0;
        if(data != null)
            i = 0 + data.size();
        if(isLoadingData)
            i++;
        return i;
    }

    public ArrayList getData()
    {
        return data;
    }

    public Object getItem(int i)
    {
        Object obj;
        if(data == null)
            obj = null;
        else
            obj = data.get(i);
        return obj;
    }

    public int getItemCount()
    {
        int i;
        if(data != null)
            i = data.size();
        else
            i = 0;
        return i;
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getItemViewType(int i)
    {
        byte byte0;
        if(isPositionOfProgressElement(i))
            byte0 = -1;
        else
            byte0 = 0;
        return byte0;
    }

    public AbsListView getListView()
    {
        return listView;
    }

    public View getProgressView()
    {
        return progressView;
    }

    public final View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        if(isPositionOfProgressElement(i))
            view1 = progressView;
        else
            view1 = doGetView(i, view, viewgroup);
        return view1;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean hasItems()
    {
        boolean flag;
        if(data != null && !data.isEmpty())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(getCount() == 0 && !isLoadingData)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i)
    {
        boolean flag;
        if(isPositionOfProgressElement(i))
            flag = false;
        else
            flag = true;
        return flag;
    }

    public boolean isLoadingData()
    {
        return isLoadingData;
    }

    public void remove(int i)
    {
        data.remove(i);
        notifyDataSetChanged();
    }

    public void setIsLoadingData(boolean flag)
    {
        setIsLoadingData(flag, true);
    }

    public void setIsLoadingData(boolean flag, boolean flag1)
    {
        isLoadingData = flag;
        if(flag1)
            notifyDataSetChanged();
    }

    private ArrayList data;
    private boolean isLoadingData;
    private AbsListView listView;
    private View progressView;
}
