// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.Date;

// Referenced classes of package com.demiroot.freshclient:
//            Item, AmazonFreshBase

public class DisplayItem extends Item
{

    public DisplayItem(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
        available = false;
    }

    public DisplayItem(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        super(amazonfreshbase, s, s1);
        available = false;
    }

    public boolean alcoholRestricted(AmazonFreshBase amazonfreshbase)
    {
        boolean flag;
        if("ALCOHOL".equals(availabilityRestrictionType) && !amazonfreshbase.canOrderAlcohol())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getImageUrlLarge()
    {
        return imageUrlLarge;
    }

    public String getImageUrlSmall()
    {
        String s;
        if(tinyImageURL != null)
            s = tinyImageURL;
        else
            s = imageUrlSmall;
        return s;
    }

    public int getMinimumQuantity()
    {
        return minimumQuantity;
    }

    public String getOOSMessage(AmazonFreshBase amazonfreshbase)
    {
        String s;
        if(!isInStock())
            s = "OUT OF\nSTOCK";
        else
        if(alcoholRestricted(amazonfreshbase))
            s = "ATTENDED\nDELIVERY\nREQ'D FOR\nALCOHOL";
        else
            s = null;
        return s;
    }

    public double getPrice()
    {
        return price;
    }

    public double getPricePerUnit()
    {
        return pricePerUnit;
    }

    public String getProduceRating()
    {
        return produceRating;
    }

    public String getProduceRatingDescription()
    {
        return produceRatingDescription;
    }

    public int getQuantity()
    {
        return 0;
    }

    public double getRating()
    {
        return rating;
    }

    public int getRatingCount()
    {
        return ratingCount;
    }

    public String getRatingType()
    {
        return ratingType;
    }

    public double getSpecialPrice()
    {
        return specialPrice;
    }

    public double getSpecialPricePerUnit()
    {
        return specialPricePerUnit;
    }

    public String getTinyImageURL()
    {
        return tinyImageURL;
    }

    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public boolean isInStock()
    {
        return available;
    }

    public boolean isSellable(AmazonFreshBase amazonfreshbase)
    {
        boolean flag;
        if(isInStock() && !alcoholRestricted(amazonfreshbase))
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void setSpecialPrice(double d)
    {
        specialPrice = d;
    }

    String availabilityRestrictionType;
    boolean available;
    String brand;
    Date earliestExpiry;
    String imageUrl;
    String imageUrlLarge;
    String imageUrlSmall;
    boolean isAutomaticDelivery;
    int minimumQuantity;
    double pricePerUnit;
    String produceRating;
    String produceRatingDescription;
    double rating;
    int ratingCount;
    String ratingType;
    double specialPrice;
    double specialPricePerUnit;
    String tinyImageURL;
    String unitOfMeasure;
    double weightWatchersPoints;
}
