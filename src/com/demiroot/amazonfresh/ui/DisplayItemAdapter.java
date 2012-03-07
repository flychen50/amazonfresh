// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.activities.DetailPageActivity;
import com.demiroot.freshclient.DisplayItem;
import com.github.droidfu.widgets.WebImageView;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.ui:
//            ItemDisplayBuilder

public class DisplayItemAdapter extends ArrayAdapter
{
    static class ViewHolder
    {

        TextView addToCartMessage;
        View cartButton;
        LinearLayout productDetails;
        WebImageView productImg;
        ImageView ratings;

        ViewHolder()
        {
        }
    }


    public DisplayItemAdapter(Context context, int i, List list)
    {
        super(context, i, list);
        searchArrayList = list;
        mBaseActivity = (AFBaseActivity)context;
        mInflater = LayoutInflater.from(context);
    }

    public void extend(List list)
    {
        searchArrayList.addAll(list);
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                return;
            add((DisplayItem)iterator.next());
        } while(true);
    }

    public int getCount()
    {
        return searchArrayList.size();
    }

    public DisplayItem getItem(int i)
    {
        return (DisplayItem)searchArrayList.get(i);
    }

    public volatile Object getItem(int i)
    {
        return getItem(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        ViewHolder viewholder;
        ItemDisplayBuilder itemdisplaybuilder;
        final DisplayItem item;
        String s;
        android.view.View.OnClickListener onclicklistener;
        TextView textview;
        String s1;
        if(view == null)
        {
            view = mInflater.inflate(0x7f030011, null);
            viewholder = new ViewHolder();
            viewholder.productImg = (WebImageView)view.findViewById(0x7f0b002f);
            viewholder.productDetails = (LinearLayout)view.findViewById(0x7f0b0030);
            viewholder.ratings = (ImageView)view.findViewById(0x7f0b003d);
            viewholder.cartButton = view.findViewById(0x7f0b003f);
            viewholder.addToCartMessage = (TextView)view.findViewById(0x7f0b0069);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        itemdisplaybuilder = new ItemDisplayBuilder();
        item = (DisplayItem)searchArrayList.get(i);
        viewholder.productImg.reset();
        s = itemdisplaybuilder.getImageURL(item);
        if(s != null)
        {
            viewholder.productImg.setImageUrl(s);
            viewholder.productImg.loadImage();
        }
        onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                Intent intent = new Intent(mBaseActivity, com/demiroot/amazonfresh/activities/DetailPageActivity);
                intent.putExtra("asin", item.getAsin());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("imageUrl", (new ItemDisplayBuilder()).getLargeURL(item));
                mBaseActivity.startActivity(intent);
            }

            final DisplayItemAdapter this$0;
            private final DisplayItem val$item;

            
            {
                this$0 = DisplayItemAdapter.this;
                item = displayitem;
                super();
            }
        }
;
        viewholder.productImg.setOnClickListener(onclicklistener);
        viewholder.productDetails.setOnClickListener(onclicklistener);
        ((TextView)viewholder.productDetails.findViewById(0x7f0b0032)).setText(itemdisplaybuilder.getTitle(item));
        itemdisplaybuilder.setPriceDisplay(viewholder.productDetails.findViewById(0x7f0b0039), item, mBaseActivity);
        textview = (TextView)viewholder.productDetails.findViewById(0x7f0b003c);
        s1 = itemdisplaybuilder.getMinQty(item, mBaseActivity);
        if(s1 != null)
        {
            textview.setText(s1);
            textview.setVisibility(0);
        } else
        {
            textview.setVisibility(8);
        }
        itemdisplaybuilder.loadRatings(item, viewholder.ratings, null);
        if(!item.isSellable(mBaseActivity.getAmazonFreshBase()))
        {
            viewholder.cartButton.setEnabled(false);
            viewholder.cartButton.setVisibility(8);
            viewholder.addToCartMessage.setVisibility(0);
            viewholder.addToCartMessage.setText(item.getOOSMessage(mBaseActivity.getAmazonFreshBase()));
        } else
        {
            viewholder.cartButton.setVisibility(0);
            viewholder.cartButton.setEnabled(true);
            viewholder.addToCartMessage.setVisibility(8);
            viewholder.cartButton.setOnClickListener(itemdisplaybuilder.addToCartListener(mBaseActivity, item));
        }
        view.invalidate();
        return view;
    }

    private AFBaseActivity mBaseActivity;
    private LayoutInflater mInflater;
    private List searchArrayList;

}
