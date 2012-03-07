// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.util.ImageLoader;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.*;

public class ItemDisplayBuilder
{

    public ItemDisplayBuilder()
    {
    }

    private void addCartAddOnClick(final AFBaseActivity activity, final Item item, final int quantityToAdd, final int quantityToShow)
    {
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(quantityToShow);
        String s = activity.getString(0x7f0700a8, aobj);
        AsyncRequest.buildRequest(activity, activity.getHandler(), new Runnable() {

            public void run()
            {
                item.setAmazonFreshBase(activity.getAmazonFreshBase());
                CartItem cartitem = item.adjustQuantity(quantityToAdd);
                activity.broadcastCart();
                AFBaseActivity afbaseactivity = activity;
                AFBaseActivity afbaseactivity1 = activity;
                Object aobj1[] = new Object[2];
                aobj1[0] = Integer.valueOf(quantityToShow);
                aobj1[1] = cartitem.getTitle();
                afbaseactivity.showToastOnUIThread(afbaseactivity1.getString(0x7f070091, aobj1), 0);
_L1:
                return;
                FreshAPILoginException freshapiloginexception;
                freshapiloginexception;
                activity.redirectToSignin(activity.getAmazonFreshBase().getAuthCookie());
                  goto _L1
                FreshAPIException freshapiexception;
                freshapiexception;
                activity.showToastOnUIThread(freshapiexception.getReason(), 1);
                  goto _L1
            }

            final ItemDisplayBuilder this$0;
            private final AFBaseActivity val$activity;
            private final Item val$item;
            private final int val$quantityToAdd;
            private final int val$quantityToShow;

            
            {
                this$0 = ItemDisplayBuilder.this;
                item = item1;
                activity = afbaseactivity;
                quantityToAdd = i;
                quantityToShow = j;
                super();
            }
        }
).setWaitMessage(s).setTopLoadingBar().execute();
    }

    public android.view.View.OnClickListener addToCartListener(final AFBaseActivity activity, final DisplayItem item)
    {
        return new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                addCartAddOnClick(activity, item, item.getMinimumQuantity(), item.getMinimumQuantity());
            }

            final ItemDisplayBuilder this$0;
            private final AFBaseActivity val$activity;
            private final DisplayItem val$item;

            
            {
                this$0 = ItemDisplayBuilder.this;
                activity = afbaseactivity;
                item = displayitem;
                super();
            }
        }
;
    }

    protected void buildMoreInfoMenu(AFBaseActivity afbaseactivity, DisplayItem displayitem, LinearLayout linearlayout, ViewGroup viewgroup)
    {
        linearlayout.removeAllViews();
        LayoutInflater layoutinflater = LayoutInflater.from(afbaseactivity);
        TableLayout tablelayout = (TableLayout)layoutinflater.inflate(0x7f030014, linearlayout, false);
        Iterator iterator = getAllMoreInfoRows(layoutinflater, displayitem, linearlayout, afbaseactivity, viewgroup).iterator();
        do
        {
            if(!iterator.hasNext())
            {
                linearlayout.addView(tablelayout);
                linearlayout.getParent().requestLayout();
                return;
            }
            View view = (View)iterator.next();
            tablelayout.addView(getSpacer(layoutinflater, linearlayout));
            tablelayout.addView(view);
        } while(true);
    }

    protected List getAllMoreInfoRows(LayoutInflater layoutinflater, DisplayItem displayitem, ViewGroup viewgroup, AFBaseActivity afbaseactivity, ViewGroup viewgroup1)
    {
        List list = getHeaderInfoRows(layoutinflater, displayitem, viewgroup, afbaseactivity, viewgroup1);
        list.addAll(getRegularInfoRows(layoutinflater, displayitem, viewgroup, afbaseactivity, viewgroup1));
        list.addAll(getFooterInfoRows(layoutinflater, displayitem, viewgroup, afbaseactivity, viewgroup1));
        return list;
    }

    protected List getFooterInfoRows(LayoutInflater layoutinflater, DisplayItem displayitem, ViewGroup viewgroup, AFBaseActivity afbaseactivity, ViewGroup viewgroup1)
    {
        return new LinkedList();
    }

    protected List getHeaderInfoRows(LayoutInflater layoutinflater, DisplayItem displayitem, ViewGroup viewgroup, AFBaseActivity afbaseactivity, ViewGroup viewgroup1)
    {
        return new LinkedList();
    }

    protected String getImageURL(DisplayItem displayitem)
    {
        return displayitem.getImageUrlSmall();
    }

    public String getLargeURL(DisplayItem displayitem)
    {
        String s = displayitem.getImageUrlLarge();
        return (new StringBuilder(String.valueOf(s.substring(0, s.indexOf('_'))))).append("_SS280_.jpg").toString();
    }

    public String getMinQty(DisplayItem displayitem, Context context)
    {
        String s;
        if(displayitem.getMinimumQuantity() > 1)
        {
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(displayitem.getMinimumQuantity());
            s = context.getString(0x7f070092, aobj);
        } else
        {
            s = null;
        }
        return s;
    }

    protected String getPrice(DisplayItem displayitem)
    {
        return StringUtils.price(displayitem.getPrice());
    }

    protected List getRegularInfoRows(LayoutInflater layoutinflater, DisplayItem displayitem, ViewGroup viewgroup, AFBaseActivity afbaseactivity, ViewGroup viewgroup1)
    {
        LinkedList linkedlist = new LinkedList();
        linkedlist.add(makeTextTextTableRow(layoutinflater, afbaseactivity.getString(0x7f070093), StringUtils.price(displayitem.getPrice()), viewgroup));
        String s = StringUtils.getUnitPrice(displayitem, afbaseactivity);
        if(s != null)
            linkedlist.add(makeTextTextTableRow(layoutinflater, afbaseactivity.getString(0x7f070094), s, viewgroup));
        return linkedlist;
    }

    protected View getSpacer(LayoutInflater layoutinflater, ViewGroup viewgroup)
    {
        return layoutinflater.inflate(0x7f030028, viewgroup, false);
    }

    protected String getTinyURL(DisplayItem displayitem)
    {
        String s = displayitem.getTinyImageURL();
        return (new StringBuilder(String.valueOf(s.substring(0, s.indexOf('_'))))).append("_SS50_.jpg").toString();
    }

    protected CharSequence getTitle(DisplayItem displayitem)
    {
        return displayitem.getTitle();
    }

    public void loadRatings(DisplayItem displayitem, ImageView imageview, TextView textview)
    {
        if(displayitem.getProduceRating() == null) goto _L2; else goto _L1
_L1:
        int j = ImageLoader.getRatingImageId(displayitem);
        if(j != 0)
        {
            imageview.setImageResource(j);
            if(textview != null)
            {
                textview.setText(displayitem.getProduceRating());
                textview.setVisibility(0);
            }
        }
        imageview.setVisibility(0);
_L4:
        return;
_L2:
        if(displayitem.getRatingCount() != 0)
        {
            int i = ImageLoader.getRatingImageId(displayitem);
            if(i != 0)
            {
                imageview.setImageResource(i);
                if(textview != null)
                {
                    textview.setText((new StringBuilder(" (")).append(displayitem.getRatingCount()).append(")").toString());
                    textview.setVisibility(0);
                }
                imageview.setVisibility(0);
            } else
            {
                imageview.setVisibility(8);
            }
        } else
        {
            imageview.setVisibility(8);
            if(textview != null)
                textview.setVisibility(8);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected View makeRatingView(LayoutInflater layoutinflater, DisplayItem displayitem, ViewGroup viewgroup, AFBaseActivity afbaseactivity, ViewGroup viewgroup1)
    {
        TableRow tablerow = (TableRow)layoutinflater.inflate(0x7f03002a, viewgroup, false);
        ((TextView)tablerow.findViewById(0x7f0b00b7)).setText(afbaseactivity.getString(0x7f070095));
        LinearLayout linearlayout = (LinearLayout)tablerow.findViewById(0x7f0b00b8);
        LinearLayout linearlayout1 = (LinearLayout)layoutinflater.inflate(0x7f03001f, linearlayout, false);
        ImageView imageview = (ImageView)linearlayout1.findViewById(0x7f0b003d);
        int i = ImageLoader.getRatingImageId(displayitem);
        if(i != 0)
        {
            imageview.setImageResource(i);
            TextView textview = (TextView)linearlayout1.findViewById(0x7f0b009b);
            if(displayitem.getProduceRating() == null)
            {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(displayitem.getRatingCount());
                textview.setText(afbaseactivity.getString(0x7f070096, aobj));
            } else
            {
                textview.setText(displayitem.getProduceRating());
            }
        }
        linearlayout.addView(linearlayout1);
        return tablerow;
    }

    protected TableRow makeTextTextTableRow(LayoutInflater layoutinflater, String s, String s1, ViewGroup viewgroup)
    {
        TableRow tablerow = (TableRow)layoutinflater.inflate(0x7f03002c, viewgroup, false);
        ((TextView)tablerow.findViewById(0x7f0b00b7)).setText(s);
        ((TextView)tablerow.findViewById(0x7f0b00b9)).setText(s1);
        return tablerow;
    }

    public void setPriceDisplay(View view, DisplayItem displayitem, Context context)
    {
        String s = StringUtils.price(displayitem.getPrice());
        String s1 = StringUtils.getUnitPrice(displayitem, context);
        String s2 = null;
        if(displayitem.getSpecialPrice() > 0.0D)
        {
            s2 = StringUtils.price(displayitem.getSpecialPrice());
            s1 = StringUtils.getSaleUnitPrice(displayitem, context);
        }
        TextView textview = (TextView)view.findViewById(0x7f0b0035);
        textview.setText(s);
        TextView textview1 = (TextView)view.findViewById(0x7f0b003a);
        TextView textview2;
        if(s2 != null)
        {
            textview1.setText(s2);
            textview.setPaintFlags(0x10 | textview.getPaintFlags());
            textview1.setVisibility(0);
        } else
        {
            textview.setPaintFlags(0xffffffef & textview.getPaintFlags());
            textview1.setVisibility(8);
        }
        textview2 = (TextView)view.findViewById(0x7f0b003b);
        if(s1 != null)
        {
            textview2.setText((new StringBuilder("(")).append(s1).append(")").toString());
            textview2.setVisibility(0);
        } else
        {
            textview2.setVisibility(8);
        }
    }

    protected void setQty(DisplayItem displayitem, TextView textview)
    {
        textview.setText((new StringBuilder(String.valueOf(displayitem.getQuantity()))).append("x ").toString());
        if(displayitem.getQuantity() > 1)
            textview.setTextColor(0xffff0000);
        else
            textview.setTextColor(0xff000000);
    }

}
