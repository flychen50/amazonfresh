// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class Address extends FreshAPICall
{

    public Address(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public String getAddress1()
    {
        return address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public String getAddress3()
    {
        return address3;
    }

    public String getAddressId()
    {
        return addressId;
    }

    public String getAddressName()
    {
        return addressName;
    }

    public String getBuildingType()
    {
        return buildingType;
    }

    public String getCity()
    {
        return city;
    }

    public String getDeliveryNotes()
    {
        return deliveryNotes;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getState()
    {
        return state;
    }

    public String getZip()
    {
        return zip;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public void setAddress1(String s)
    {
        address1 = s;
    }

    public void setAddress2(String s)
    {
        address2 = s;
    }

    public void setAddress3(String s)
    {
        address3 = s;
    }

    public void setAddressId(String s)
    {
        addressId = s;
    }

    public void setAddressName(String s)
    {
        addressName = s;
    }

    public void setBuildingType(String s)
    {
        buildingType = s;
    }

    public void setCity(String s)
    {
        city = s;
    }

    public void setDefault(boolean flag)
    {
        isDefault = flag;
    }

    public void setDeliveryNotes(String s)
    {
        deliveryNotes = s;
    }

    public void setPhone(String s)
    {
        phone = s;
    }

    public void setState(String s)
    {
        state = s;
    }

    public void setZip(String s)
    {
        zip = s;
    }

    private String address1;
    private String address2;
    private String address3;
    private String addressId;
    private String addressName;
    private String buildingType;
    private String city;
    private String deliveryNotes;
    private boolean isDefault;
    private String phone;
    private String state;
    private String zip;
}
