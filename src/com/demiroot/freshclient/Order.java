// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.Date;
import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, AmazonFreshBase, FreshClientProxy, DeliveryWindow, 
//            DeliverySlots, LinkAPICall, CartItemList, UpdateTip, 
//            Address, PaymentInstrument

public class Order extends FreshAPICall
{

    public Order(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public Order(AmazonFreshBase amazonfreshbase, String s)
    {
        this(amazonfreshbase, s, false);
    }

    private Order(AmazonFreshBase amazonfreshbase, String s, String s1)
    {
        super(amazonfreshbase, s, s1);
        init();
    }

    public Order(AmazonFreshBase amazonfreshbase, String s, boolean flag)
    {
        StringBuilder stringbuilder = new StringBuilder("/order/");
        String s1;
        String s2;
        String s3;
        if(flag)
            s1 = "edit/";
        else
            s1 = "";
        s2 = stringbuilder.append(s1).append(s).toString();
        if(flag)
            s3 = "POST";
        else
            s3 = "GET";
        super(amazonfreshbase, s2, s3);
        init();
    }

    public static Order getCartOrder(AmazonFreshBase amazonfreshbase)
    {
        return new Order(amazonfreshbase, "/cart", "GET");
    }

    public boolean canCancel()
    {
        return isCancelable;
    }

    public boolean canCheckout()
    {
        return isDeliverable;
    }

    public void cancel()
    {
        amazonFreshBase.getClientProxy().post((new StringBuilder("/order/cancel/")).append(orderId).toString());
    }

    public Order edit()
    {
        return new Order(amazonFreshBase, orderId, true);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof Order))
        {
            flag = false;
        } else
        {
            Order order = (Order)obj;
            if(orderId.equals(order.orderId) && totalQuantity == order.totalQuantity && total == order.total && status.equals(order.status) && (deliveryTimeWindow == null && order.deliveryTimeWindow == null || deliveryTimeWindow.equals(order.deliveryTimeWindow)))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public Address getAddress()
    {
        return address;
    }

    public List getAvailablePaymentInstruments()
    {
        return availablePaymentInstruments;
    }

    public DeliverySlots getAvailableSlots()
    {
        return new DeliverySlots(amazonFreshBase, orderId, null);
    }

    public double getDeliveryFee()
    {
        return deliveryCharge;
    }

    public List getItems()
    {
        return ((CartItemList)items.get()).list;
    }

    public double getMinimumOrderThreshold()
    {
        return minimumOrderThreshold;
    }

    public String getOrderId()
    {
        return orderId;
    }

    public PaymentInstrument getPaymentInstrument()
    {
        return paymentInstrument;
    }

    public DeliveryWindow getSelectedSlot()
    {
        return deliveryTimeWindow;
    }

    public String getStatus()
    {
        return status;
    }

    public double getSubtotal()
    {
        return subtotal;
    }

    public double getTax()
    {
        return tax;
    }

    public double getTip()
    {
        return tip;
    }

    public double getTotal()
    {
        return total;
    }

    public int getTotalQuantity()
    {
        return totalQuantity;
    }

    public boolean hasSelectedSlot()
    {
        boolean flag;
        if(deliveryTimeWindow != null && deliveryTimeWindow.hasType())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isBeingModified()
    {
        return isBeingModified;
    }

    public boolean isFreeDelivery()
    {
        boolean flag;
        if(deliveryCharge == 0.0D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isModifyingOrder()
    {
        return isModifyingOrder;
    }

    public void undoEdit()
    {
        amazonFreshBase.getClientProxy().post("/order/undoEdit");
    }

    public void updateTip(String s)
    {
        UpdateTip.updateTip(amazonFreshBase, orderId, s);
    }

    private Address address;
    private List availablePaymentInstruments;
    private String baggingPreference;
    private Date checkoutAt;
    private Date createdAt;
    private double deliveryCharge;
    private DeliveryWindow deliveryTimeWindow;
    private boolean isAutomaticDelivery;
    private boolean isBeingModified;
    private boolean isCancelable;
    private boolean isDeliverable;
    private boolean isModifyingOrder;
    LinkAPICall items;
    private double minimumOrderThreshold;
    private String orderId;
    private PaymentInstrument paymentInstrument;
    private double promotionTotal;
    private String status;
    private double subtotal;
    private double tax;
    private double tip;
    private double total;
    private int totalQuantity;
    private String totePickup;
}
