// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase, ItemDetail, CartItem

public class Item extends FreshAPICall
{

    public Item(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public Item(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        super(amazonfreshbase, s, s1);
    }

    public CartItem adjustQuantity(int i)
    {
        return amazonFreshBase.adjustCartItem(asin, i);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof Item))
        {
            flag = false;
        } else
        {
            String s = ((Item)obj).asin;
            if(asin != s && (asin == null || !asin.equals(s)))
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public String getAsin()
    {
        return asin;
    }

    public CartItem getCartItem()
    {
        return amazonFreshBase.getCartItem(asin);
    }

    public ItemDetail getItemDetail()
    {
        return new ItemDetail(amazonFreshBase, asin);
    }

    public double getPrice()
    {
        return price;
    }

    public String getTitle()
    {
        return title;
    }

    public CartItem setAmountInCart(int i)
    {
        return amazonFreshBase.setItem(asin, i);
    }

    String asin;
    double price;
    String title;
}
