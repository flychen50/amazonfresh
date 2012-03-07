// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.content.*;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.ItemDisplayBuilder;
import com.demiroot.freshclient.*;
import com.github.droidfu.widgets.WebImageView;
import java.util.Iterator;
import java.util.List;

public class DetailPageActivity extends AFBaseActivity
{

    public DetailPageActivity()
    {
        detailReceiver = new RemoveLoadingBarReceiver(this) {

            public void onReceiveSafe(Context context, Intent intent)
            {
                ItemDetail itemdetail = (ItemDetail)intent.getSerializableExtra("response");
                if(asin != null && asin.equals(itemdetail.getAsin()))
                {
                    mDetail = itemdetail;
                    loadDetailView();
                }
            }

            final DetailPageActivity this$0;

            
            {
                this$0 = DetailPageActivity.this;
                RemoveLoadingBarReceiver(afbaseactivity);
            }
        }
;
    }

    private void addBorderLineToTable(TableLayout tablelayout, int i)
    {
        TableRow tablerow = new TableRow(this);
        tablerow.setPadding(0, 0, 0, i);
        tablerow.setBackgroundResource(0x7f090019);
        tablelayout.addView(tablerow);
    }

    private void addDailyValueHeader(TableLayout tablelayout)
    {
        addBorderLineToTable(tablelayout, 2);
        tablelayout.addView(getLayoutInflater().inflate(0x7f030017, null));
        addBorderLineToTable(tablelayout, 1);
    }

    private void addRowToTable(TableLayout tablelayout, NutritionInfo nutritioninfo, int i)
    {
        View view = getLayoutInflater().inflate(0x7f030018, null);
        TextView textview = (TextView)view.findViewById(0x7f0b007a);
        textview.setText(nutritioninfo.getName());
        if(nutritioninfo.isShouldHighlightName())
        {
            textview.setTypeface(Typeface.defaultFromStyle(1));
            textview.setPadding(5, 0, 0, 0);
        }
        List list = nutritioninfo.getValues();
        TextView textview1 = (TextView)view.findViewById(0x7f0b007b);
        if(i > 1)
            textview1.setText((CharSequence)list.get(0));
        else
            textview1.setText(" ");
        ((TextView)view.findViewById(0x7f0b007c)).setText((CharSequence)list.get(i - 1));
        tablelayout.addView(view);
    }

    private void loadDetailView()
    {
        LinearLayout linearlayout;
        LinearLayout linearlayout1;
        LinearLayout linearlayout3;
        TableLayout tablelayout;
        int i;
        LinearLayout linearlayout5;
        StringBuilder stringbuilder;
        Iterator iterator;
        StringBuilder stringbuilder1;
        Iterator iterator1;
        int j;
        List list;
        Iterator iterator2;
        int k;
        Iterator iterator3;
        Iterator iterator4;
        StringBuilder stringbuilder2;
        Iterator iterator5;
        StringBuilder stringbuilder3;
        Iterator iterator6;
        ItemDisplayBuilder itemdisplaybuilder = new ItemDisplayBuilder();
        WebImageView webimageview = (WebImageView)findViewById(0x7f0b002f);
        String s = itemdisplaybuilder.getLargeURL(mDetail);
        if(s != null && !webimageview.isLoaded())
        {
            webimageview.setImageUrl(s);
            webimageview.loadImage();
        }
        itemdisplaybuilder.setPriceDisplay(findViewById(0x7f0b0039), mDetail, this);
        TextView textview = (TextView)findViewById(0x7f0b003c);
        String s1 = itemdisplaybuilder.getMinQty(mDetail, this);
        if(s1 != null)
        {
            textview.setVisibility(0);
            textview.setText(s1);
        }
        itemdisplaybuilder.loadRatings(mDetail, (ImageView)findViewById(0x7f0b003d), (TextView)findViewById(0x7f0b003e));
        Button button = (Button)findViewById(0x7f0b003f);
        button.setVisibility(0);
        LinearLayout linearlayout4;
        TextView textview1;
        TextView textview2;
        TextView textview3;
        NutritionFacts nutritionfacts;
        String s3;
        List list1;
        List list2;
        TextView textview5;
        TextView textview6;
        TextView textview7;
        if(!mDetail.isSellable(getAmazonFreshBase()))
        {
            button.setText(mDetail.getOOSMessage(getAmazonFreshBase()));
            button.setEnabled(false);
        } else
        {
            button.setOnClickListener(itemdisplaybuilder.addToCartListener(this, mDetail));
        }
        linearlayout = (LinearLayout)findViewById(0x7f0b0042);
        if(mDetail.getProductFeatures().isEmpty()) goto _L2; else goto _L1
_L1:
        textview7 = (TextView)linearlayout.findViewById(0x7f0b0043);
        linearlayout.setVisibility(0);
        stringbuilder3 = new StringBuilder();
        iterator6 = mDetail.getProductFeatures().iterator();
_L27:
        if(iterator6.hasNext()) goto _L4; else goto _L3
_L3:
        textview7.setText(Html.fromHtml(stringbuilder3.toString()).toString());
_L28:
        linearlayout1 = (LinearLayout)findViewById(0x7f0b0044);
        if(mDetail.getProductDetails().isEmpty()) goto _L6; else goto _L5
_L5:
        textview6 = (TextView)findViewById(0x7f0b0045);
        linearlayout1.setVisibility(0);
        stringbuilder2 = new StringBuilder();
        iterator5 = mDetail.getProductDetails().iterator();
_L29:
        if(iterator5.hasNext()) goto _L8; else goto _L7
_L7:
        textview6.setText(stringbuilder2.toString().replaceAll("&[^\\s]*;", ""));
_L30:
        LinearLayout linearlayout2 = (LinearLayout)findViewById(0x7f0b0046);
        String s6;
        if(mDetail.getIngredients() != null)
        {
            textview5 = (TextView)findViewById(0x7f0b0047);
            linearlayout2.setVisibility(0);
            textview5.setText((new StringBuilder(String.valueOf(mDetail.getIngredients()))).append("\n").toString());
        } else
        {
            linearlayout2.setVisibility(8);
        }
        linearlayout3 = (LinearLayout)findViewById(0x7f0b0048);
        tablelayout = (TableLayout)findViewById(0x7f0b004d);
        if(mDetail.getNutritionFacts() == null) goto _L10; else goto _L9
_L9:
        j = 0;
        linearlayout3.setVisibility(0);
        nutritionfacts = mDetail.getNutritionFacts();
        if(tablelayout.getChildCount() > 0) goto _L12; else goto _L11
_L11:
        s3 = nutritionfacts.getServingSize();
        if(s3 != null)
            ((TextView)findViewById(0x7f0b0049)).setText((new StringBuilder(String.valueOf(getString(0x7f070029)))).append(" ").append(s3).toString());
        list = nutritionfacts.getVariants();
        if(list == null) goto _L14; else goto _L13
_L13:
        iterator4 = list.iterator();
_L32:
        if(iterator4.hasNext()) goto _L15; else goto _L14
_L14:
        list1 = nutritionfacts.getNutrients();
        if(list1 == null) goto _L17; else goto _L16
_L16:
        k = 0;
        iterator3 = list1.iterator();
_L33:
        if(iterator3.hasNext()) goto _L18; else goto _L17
_L17:
        list2 = nutritionfacts.getExtraAttributes();
        if(list2 == null) goto _L12; else goto _L19
_L19:
        iterator2 = list2.iterator();
_L34:
        if(iterator2.hasNext()) goto _L20; else goto _L12
_L12:
        linearlayout4 = (LinearLayout)findViewById(0x7f0b004e);
        i = 0;
        if(mDetail.getLegalDisclaimers().isEmpty()) goto _L22; else goto _L21
_L21:
        textview3 = (TextView)linearlayout4.findViewById(0x7f0b004f);
        linearlayout4.setVisibility(0);
        stringbuilder1 = new StringBuilder();
        iterator1 = mDetail.getLegalDisclaimers().iterator();
_L35:
        if(iterator1.hasNext()) goto _L24; else goto _L23
_L23:
        textview3.setText(Html.fromHtml(stringbuilder1.toString().replace("<strong>", "<b>").replace("</strong>", "</b>")));
_L22:
        if(i == 0)
            linearlayout4.setVisibility(8);
        linearlayout5 = (LinearLayout)findViewById(0x7f0b0040);
        if(mDetail.getCpsiaWarnings().isEmpty())
            break MISSING_BLOCK_LABEL_1290;
        textview2 = (TextView)linearlayout5.findViewById(0x7f0b0041);
        linearlayout5.setVisibility(0);
        stringbuilder = new StringBuilder();
        iterator = mDetail.getCpsiaWarnings().iterator();
_L36:
        if(iterator.hasNext()) goto _L26; else goto _L25
_L25:
        textview2.setText(Html.fromHtml(stringbuilder.toString().replace("<strong>", "<b>").replace("</strong>", "</b>")));
_L37:
        textview1 = (TextView)findViewById(0x7f0b0050);
        textview1.setMovementMethod(LinkMovementMethod.getInstance());
        textview1.setVisibility(0);
        return;
_L4:
        s6 = (String)iterator6.next();
        stringbuilder3.append("&#8226; ").append(s6).append("<br />");
          goto _L27
_L2:
        linearlayout.setVisibility(8);
          goto _L28
_L8:
        stringbuilder2.append((String)iterator5.next()).append("\n");
          goto _L29
_L6:
        linearlayout1.setVisibility(8);
          goto _L30
_L15:
        String s4 = (String)iterator4.next();
        if(j == 2) goto _L14; else goto _L31
_L31:
        String s5;
        TextView textview4;
        if(s4.equals("Base"))
            s5 = "";
        else
            s5 = s4;
        if(list.size() > 1 && j == 0)
            textview4 = (TextView)findViewById(0x7f0b004b);
        else
            textview4 = (TextView)findViewById(0x7f0b004c);
        textview4.setText(s5);
        j++;
          goto _L32
_L18:
        NutritionInfo nutritioninfo = (NutritionInfo)iterator3.next();
        addRowToTable(tablelayout, nutritioninfo, j);
        addBorderLineToTable(tablelayout, 1);
        if(++k == 2 || nutritioninfo.getName().contains("Protein"))
            addDailyValueHeader(tablelayout);
          goto _L33
_L20:
        addRowToTable(tablelayout, (NutritionInfo)iterator2.next(), j);
        addBorderLineToTable(tablelayout, 1);
          goto _L34
_L10:
        linearlayout3.setVisibility(8);
          goto _L12
_L24:
        String s2 = (String)iterator1.next();
        if(!s2.contains("AmazonFresh guarantees every product will be delivered to your home") && !s2.contains("While we work to ensure that product information is correct, on occasion manufacturers may"))
        {
            stringbuilder1.append(s2).append("<br /><br />");
            i++;
        }
          goto _L35
_L26:
        stringbuilder.append((String)iterator.next()).append("<br />");
          goto _L36
        linearlayout5.setVisibility(8);
          goto _L37
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000b);
        loadActionBar(getString(0x7f070072));
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        Intent intent = getIntent();
        asin = intent.getStringExtra("asin");
        broadcastDetail(asin);
        ((TextView)findViewById(0x7f0b000c)).setText(intent.getStringExtra("title"));
        String s = intent.getStringExtra("imageUrl");
        if(s != null)
        {
            WebImageView webimageview = (WebImageView)findViewById(0x7f0b002f);
            webimageview.setImageUrl(s);
            webimageview.loadImage();
        }
        showLoadingBar(getString(0x7f070056));
    }

    protected void onStart()
    {
        super.onStart();
        registerReceiver(detailReceiver, new IntentFilter(com/demiroot/freshclient/ItemDetail.getName()));
    }

    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(detailReceiver);
    }

    private String asin;
    private BroadcastReceiver detailReceiver;
    private ItemDetail mDetail;



}
