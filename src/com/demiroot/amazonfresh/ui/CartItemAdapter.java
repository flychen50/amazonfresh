// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.CartItem;
import com.github.droidfu.widgets.WebImageView;
import java.util.List;

// Referenced classes of package com.demiroot.amazonfresh.ui:
//            ItemDisplayBuilder

public class CartItemAdapter extends ArrayAdapter
{
    static class ViewHolder
    {

        TextView extendedPrice;
        LinearLayout productDetails;
        WebImageView productImg;

        ViewHolder()
        {
        }
    }


    public CartItemAdapter(Context context, int i, List list)
    {
        super(context, i, list);
        mCartArrayList = list;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return mCartArrayList.size();
    }

    public CartItem getItem(int i)
    {
        return (CartItem)mCartArrayList.get(i);
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
        CartItem cartitem;
        String s;
        if(view == null)
        {
            view = mInflater.inflate(0x7f030009, null);
            viewholder = new ViewHolder();
            viewholder.productImg = (WebImageView)view.findViewById(0x7f0b002f);
            viewholder.productDetails = (LinearLayout)view.findViewById(0x7f0b0030);
            viewholder.extendedPrice = (TextView)view.findViewById(0x7f0b0031);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        itemdisplaybuilder = new ItemDisplayBuilder();
        cartitem = (CartItem)mCartArrayList.get(i);
        viewholder.productImg.reset();
        s = itemdisplaybuilder.getTinyURL(cartitem);
        if(s != null)
        {
            viewholder.productImg.setImageUrl(s);
            viewholder.productImg.loadImage();
        }
        ((TextView)viewholder.productDetails.findViewById(0x7f0b0032)).setText(itemdisplaybuilder.getTitle(cartitem));
        itemdisplaybuilder.setQty(cartitem, (TextView)viewholder.productDetails.findViewById(0x7f0b0034));
        ((TextView)viewholder.productDetails.findViewById(0x7f0b0035)).setText(itemdisplaybuilder.getPrice(cartitem));
        viewholder.extendedPrice.setText(StringUtils.price(cartitem.getExtendedPrice()));
        return view;
    }

    private List mCartArrayList;
    private LayoutInflater mInflater;
}
