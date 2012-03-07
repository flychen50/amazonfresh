// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.apache.http.cookie.Cookie;

// Referenced classes of package com.demiroot.freshclient:
//            FreshClientProxy, DeliveryWindow, StaticContent, CartItem, 
//            Order, CheckoutResponse, SeralizableCookie, DeliverySlots, 
//            BigRadishStatus, ItemDetail, OrderList, GatewayContent, 
//            PastPurchases, PaymentInstrument, LoginResponse, APIArgs, 
//            FreshAPIException

public class AmazonFreshBase
    implements Serializable
{
    public static interface FreshProxyGetter
        extends Serializable
    {

        public abstract FreshClientProxy getProxy();
    }

    public static class SimpleFreshProxyGetter
        implements FreshProxyGetter
    {

        public FreshClientProxy getProxy()
        {
            return proxy;
        }

        private FreshClientProxy proxy;

        public SimpleFreshProxyGetter(String s)
        {
            proxy = new FreshClientProxy(s, "Android-JavaClient");
        }
    }


    public AmazonFreshBase(FreshProxyGetter freshproxygetter)
    {
        deliveryWindowType = null;
        customerEmail = null;
        proxyGetter = freshproxygetter;
        proxyGetter.getProxy().addFreshAPIExceptionListener(new FreshClientProxy.FreshAPIExceptionListener() {

            public void onAPIExeption(FreshAPIException freshapiexception)
            {
                freshapiexception.getErrorCode();
                JVM INSTR lookupswitch 7: default 72
            //                           16: 73
            //                           17: 73
            //                           20: 73
            //                           21: 73
            //                           22: 73
            //                           23: 73
            //                           52: 84;
                   goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L3
_L1:
                return;
_L2:
                isLoggedIn = false;
                continue; /* Loop/switch isn't completed */
_L3:
                updateDeliveryWindowType();
                if(true) goto _L1; else goto _L4
_L4:
            }

            final AmazonFreshBase this$0;

            
            {
                this$0 = AmazonFreshBase.this;
                super();
            }
        }
);
        isLoggedIn = false;
    }

    public static void main(String args[])
    {
    }

    private void updateDeliveryWindowType()
    {
        deliveryWindowType = null;
        DeliveryWindow deliverywindow = getSelectedDeliveryWindowForCart();
        if(deliverywindow != null)
            deliveryWindowType = deliverywindow.getType();
_L2:
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public String about()
    {
        return (new StaticContent(this, "about")).content;
    }

    public CartItem adjustCartItem(String s, int i)
    {
        return new CartItem(this, s, Integer.valueOf(i), false);
    }

    public boolean canOrderAlcohol()
    {
        boolean flag;
        if(deliveryWindowType == null)
            flag = true;
        else
            flag = deliveryWindowType.equalsIgnoreCase("attended");
        return flag;
    }

    public Order cart()
    {
        return Order.getCartOrder(this);
    }

    public CheckoutResponse checkout()
    {
        return checkout(null, null, null, null);
    }

    public CheckoutResponse checkout(String s)
    {
        return checkout(s, null, null, null);
    }

    public CheckoutResponse checkout(String s, String s1, String s2, String s3)
    {
        return new CheckoutResponse(this, s, s1, s2, s3);
    }

    public void deleteItem(String s)
    {
        getClientProxy().post((new StringBuilder("/cartItem/remove/")).append(s).toString());
    }

    public void dropCookies()
    {
        System.out.println("sean DROPPING COOKIES!!!!");
        Iterator iterator = getClientProxy().cookies.iterator();
        do
        {
            if(!iterator.hasNext())
                return;
            ((SeralizableCookie)(Cookie)iterator.next()).setValue((new StringBuilder("XXXX")).append(Math.random()).toString());
        } while(true);
    }

    public String faq()
    {
        return (new StaticContent(this, "faq")).content;
    }

    public String getAuthCookie()
    {
        Iterator iterator = getClientProxy().cookies.iterator();
_L2:
        String s;
        if(!iterator.hasNext())
        {
            s = null;
        } else
        {
            Cookie cookie = (Cookie)iterator.next();
            if(!cookie.getName().equals("x-fresh-api"))
                continue; /* Loop/switch isn't completed */
            s = cookie.getValue();
        }
        return s;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public DeliverySlots getAvailableSlotsForCart(String s)
    {
        return new DeliverySlots(this, null, s);
    }

    public BigRadishStatus getBigRadishStatus()
    {
        return new BigRadishStatus(this);
    }

    public CartItem getCartItem(String s)
    {
        return new CartItem(this, s);
    }

    public FreshClientProxy getClientProxy()
    {
        return proxyGetter.getProxy();
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public ItemDetail getDetail(String s)
    {
        return new ItemDetail(this, s);
    }

    public List getEditiableOrders()
    {
        return (new OrderList(this, 0, true)).list;
    }

    public GatewayContent getGatewayContent()
    {
        return new GatewayContent(this);
    }

    public Order getOrder(String s)
    {
        return new Order(this, s);
    }

    public PastPurchases getPastPurchases()
    {
        return new PastPurchases(this);
    }

    public PaymentInstrument getPaymentInstrument()
    {
        return new PaymentInstrument(this, true);
    }

    public DeliveryWindow getSelectedDeliveryWindowForCart()
    {
        return DeliveryWindow.getSelectedSlotForCart(this);
    }

    public boolean isExistingCustomer()
    {
label0:
        {
            boolean flag;
            String s;
            try
            {
                s = (new PaymentInstrument(this, true)).getPaymentInstrumentId();
            }
            catch(Exception exception)
            {
                flag = false;
                if(false)
                    ;
                else
                    break label0;
            }
            if(s == null)
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    public boolean isLoggedIn()
    {
        boolean flag;
        if(getClientProxy() != null && isLoggedIn)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String legal()
    {
        return (new StaticContent(this, "legal")).content;
    }

    public boolean login(String s, String s1)
    {
        isLoggedIn = false;
        LoginResponse loginresponse = new LoginResponse(this, s, s1);
        boolean flag;
        if("failure".equals(loginresponse.status))
        {
            flag = false;
        } else
        {
            customerName = loginresponse.customerName;
            customerEmail = s;
            isLoggedIn = true;
            updateDeliveryWindowType();
            flag = true;
        }
        return flag;
    }

    public void logout()
    {
        isLoggedIn = false;
        customerName = null;
        customerEmail = null;
        getClientProxy().request("/customer/logout", "POST", new APIArgs(new Object[0]));
        setLoginRequired();
    }

    public OrderList orderList()
    {
        return orderList(0);
    }

    public OrderList orderList(int i)
    {
        return new OrderList(this, i, false);
    }

    public void setDeliveryWindowType(String s)
    {
        deliveryWindowType = s;
    }

    public CartItem setItem(String s, int i)
    {
        return new CartItem(this, s, Integer.valueOf(i), true);
    }

    public void setLoggedOut()
    {
        customerName = null;
        customerEmail = null;
        isLoggedIn = false;
    }

    public void setLoginRequired()
    {
        isLoggedIn = false;
        getClientProxy().clear();
    }

    private static final String AUTH_COOKIE_NAME = "x-fresh-api";
    public volatile String customerEmail;
    public volatile String customerName;
    public volatile String deliveryWindowType;
    private volatile boolean isLoggedIn;
    private FreshProxyGetter proxyGetter;


}
