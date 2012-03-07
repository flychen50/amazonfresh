// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase

public class PaymentInstrument extends FreshAPICall
{

    public PaymentInstrument(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public PaymentInstrument(AmazonFreshBase amazonfreshbase, boolean flag)
    {
        super(amazonfreshbase, "/customer/defaultPaymentInstrument", "GET");
        init();
    }

    public String getBrand()
    {
        return brand;
    }

    public String getName()
    {
        return name;
    }

    public String getPaymentInstrumentId()
    {
        return paymentInstrumentId;
    }

    public String getTail()
    {
        return tail;
    }

    public void setBrand(String s)
    {
        brand = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setTail(String s)
    {
        tail = s;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(getBrand()))).append(" XXXX XXXX XXXX ").append(getTail()).toString();
    }

    String brand;
    String name;
    String paymentInstrumentId;
    String tail;
}
