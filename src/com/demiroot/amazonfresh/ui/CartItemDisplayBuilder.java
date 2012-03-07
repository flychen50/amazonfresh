// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.QuickActionHelpers.ActionItem;
import com.demiroot.amazonfresh.QuickActionHelpers.QuickAction;
import com.demiroot.amazonfresh.activities.CartActivity;
import com.demiroot.amazonfresh.util.StringUtils;
import com.demiroot.freshclient.*;
import java.util.concurrent.Callable;

// Referenced classes of package com.demiroot.amazonfresh.ui:
//            ItemDisplayBuilder

public class CartItemDisplayBuilder extends ItemDisplayBuilder
{
    private class CartButtonClickListener
        implements android.view.View.OnClickListener
    {

        public void onClick(View view)
        {
            quickAction.dismiss();
            AsyncRequest.buildRequest(baseActivity, baseActivity.getHandler(), new Runnable() {

                public void run()
                {
                    if((CartItem)actionDoer.call() != null)
                        baseActivity.showToastOnUIThread(msg, 0);
                    if(baseActivity instanceof CartActivity)
                        ((CartActivity)baseActivity).requestOrderTotalCart();
                    return;
                    RuntimeException runtimeexception;
                    runtimeexception;
                    throw runtimeexception;
                    Exception exception1;
                    exception1;
                    if(baseActivity instanceof CartActivity)
                        ((CartActivity)baseActivity).requestOrderTotalCart();
                    throw exception1;
                    Exception exception;
                    exception;
                    throw new RuntimeException(exception);
                }

                final CartButtonClickListener this$1;

                
                {
                    this$1 = CartButtonClickListener.this;
                    super();
                }
            }
).setWaitMessage(waitMessage).setTopLoadingBar().execute();
        }

        Callable actionDoer;
        AFBaseActivity baseActivity;
        String msg;
        QuickAction quickAction;
        final CartItemDisplayBuilder this$0;
        String waitMessage;

        public CartButtonClickListener(Callable callable, AFBaseActivity afbaseactivity, String s, String s1, QuickAction quickaction)
        {
            this$0 = CartItemDisplayBuilder.this;
            super();
            actionDoer = callable;
            baseActivity = afbaseactivity;
            msg = s;
            waitMessage = s1;
            quickAction = quickaction;
        }
    }


    public CartItemDisplayBuilder()
    {
    }

    public void createQuickActions(ListView listview, Resources resources, final AFBaseActivity context, final ArrayAdapter listAdapter)
    {
        final ActionItem addAction = new ActionItem();
        addAction.setTitle(context.getString(0x7f07008b));
        addAction.setIcon(resources.getDrawable(0x7f020024));
        final ActionItem minusAction = new ActionItem();
        minusAction.setTitle(context.getString(0x7f07008c));
        minusAction.setIcon(resources.getDrawable(0x7f020021));
        final ActionItem removeAction = new ActionItem();
        removeAction.setTitle(context.getString(0x7f07008d));
        removeAction.setIcon(resources.getDrawable(0x7f020029));
        final ActionItem closeAction = new ActionItem();
        closeAction.setTitle(context.getString(0x7f0700bf));
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, final int position, long l)
            {
                final QuickAction mQuickAction = new QuickAction(view);
                final CartItem cItem = (CartItem)adapterview.getItemAtPosition(position);
                ActionItem actionitem = addAction;
                CartButtonClickListener cartbuttonclicklistener = new CartButtonClickListener(new Callable() {

                    public CartItem call()
                        throws Exception
                    {
                        final CartItem newcItem = cItem.adjustQuantity(1);
                        context.broadcastCart();
                        context.runOnUiThread(new Runnable() {

                            public void run()
                            {
                                listAdapter.remove(cItem);
                                listAdapter.insert(newcItem, position);
                                listAdapter.notifyDataSetChanged();
                            }

                            final _cls1 this$2;
                            private final CartItem val$cItem;
                            private final ArrayAdapter val$listAdapter;
                            private final CartItem val$newcItem;
                            private final int val$position;

                        
                        {
                            this$2 = _cls1.this;
                            listAdapter = arrayadapter;
                            cItem = cartitem;
                            newcItem = cartitem1;
                            position = i;
                            super();
                        }
                        }
);
                        return cItem;
                    }

                    public volatile Object call()
                        throws Exception
                    {
                        return call();
                    }

                    final _cls1 this$1;
                    private final CartItem val$cItem;
                    private final AFBaseActivity val$context;
                    private final ArrayAdapter val$listAdapter;
                    private final int val$position;

                    
                    {
                        this$1 = _cls1.this;
                        cItem = cartitem;
                        context = afbaseactivity;
                        listAdapter = arrayadapter;
                        position = i;
                        super();
                    }
                }
, context, context.getString(0x7f07008e), context.getString(0x7f0700a5), mQuickAction);
                actionitem.setOnClickListener(cartbuttonclicklistener);
                ActionItem actionitem1 = minusAction;
                CartButtonClickListener cartbuttonclicklistener1 = new CartButtonClickListener(new Callable() {

                    public CartItem call()
                        throws Exception
                    {
                        final CartItem newcItem = cItem.adjustQuantity(-1);
                        context.broadcastCart();
                        context.runOnUiThread(new Runnable() {

                            public void run()
                            {
                                listAdapter.remove(cItem);
                                listAdapter.insert(newcItem, position);
                                listAdapter.notifyDataSetChanged();
                            }

                            final _cls2 this$2;
                            private final CartItem val$cItem;
                            private final ArrayAdapter val$listAdapter;
                            private final CartItem val$newcItem;
                            private final int val$position;

                        
                        {
                            this$2 = _cls2.this;
                            listAdapter = arrayadapter;
                            cItem = cartitem;
                            newcItem = cartitem1;
                            position = i;
                            super();
                        }
                        }
);
                        return cItem;
                    }

                    public volatile Object call()
                        throws Exception
                    {
                        return call();
                    }

                    final _cls1 this$1;
                    private final CartItem val$cItem;
                    private final AFBaseActivity val$context;
                    private final ArrayAdapter val$listAdapter;
                    private final int val$position;

                    
                    {
                        this$1 = _cls1.this;
                        cItem = cartitem;
                        context = afbaseactivity;
                        listAdapter = arrayadapter;
                        position = i;
                        super();
                    }
                }
, context, context.getString(0x7f07008f), context.getString(0x7f0700a6), mQuickAction);
                actionitem1.setOnClickListener(cartbuttonclicklistener1);
                removeAction.setOnClickListener(new CartButtonClickListener(new Callable() {

                    public CartItem call()
                        throws Exception
                    {
                        context.getAmazonFreshBase().deleteItem(cItem.getAsin());
                        context.broadcastCart();
                        context.runOnUiThread(new Runnable() {

                            public void run()
                            {
                                listAdapter.remove(cItem);
                                listAdapter.notifyDataSetChanged();
                            }

                            final _cls3 this$2;
                            private final CartItem val$cItem;
                            private final ArrayAdapter val$listAdapter;

                        
                        {
                            this$2 = _cls3.this;
                            listAdapter = arrayadapter;
                            cItem = cartitem;
                            super();
                        }
                        }
);
                        return null;
                    }

                    public volatile Object call()
                        throws Exception
                    {
                        return call();
                    }

                    final _cls1 this$1;
                    private final CartItem val$cItem;
                    private final AFBaseActivity val$context;
                    private final ArrayAdapter val$listAdapter;

                    
                    {
                        this$1 = _cls1.this;
                        context = afbaseactivity;
                        cItem = cartitem;
                        listAdapter = arrayadapter;
                        super();
                    }
                }
, context, context.getString(0x7f070090), context.getString(0x7f0700a7), mQuickAction));
                closeAction.setOnClickListener(new android.view.View.OnClickListener() {

                    public void onClick(View view1)
                    {
                        mQuickAction.dismiss();
                    }

                    final _cls1 this$1;
                    private final QuickAction val$mQuickAction;

                    
                    {
                        this$1 = _cls1.this;
                        mQuickAction = quickaction;
                        super();
                    }
                }
);
                mQuickAction.addActionItem(addAction);
                if(cItem.getQuantity() > cItem.getMinimumQuantity() && cItem.getQuantity() != 1)
                    mQuickAction.addActionItem(minusAction);
                mQuickAction.addActionItem(removeAction);
                mQuickAction.addActionItem(closeAction);
                mQuickAction.setAnimStyle(4);
                mQuickAction.setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() {

                    public void onDismiss()
                    {
                    }

                    final _cls1 this$1;

                    
                    {
                        this$1 = _cls1.this;
                        super();
                    }
                }
);
                mQuickAction.show();
            }

            final CartItemDisplayBuilder this$0;
            private final ActionItem val$addAction;
            private final ActionItem val$closeAction;
            private final AFBaseActivity val$context;
            private final ArrayAdapter val$listAdapter;
            private final ActionItem val$minusAction;
            private final ActionItem val$removeAction;

            
            {
                this$0 = CartItemDisplayBuilder.this;
                addAction = actionitem;
                context = afbaseactivity;
                minusAction = actionitem1;
                removeAction = actionitem2;
                closeAction = actionitem3;
                listAdapter = arrayadapter;
                super();
            }
        }
);
    }

    protected String getLeftTextString(DisplayItem displayitem, Context context)
    {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(displayitem.getQuantity());
        aobj[1] = StringUtils.price(displayitem.getPrice());
        return context.getString(0x7f07008a, aobj);
    }

    protected String getPrice(DisplayItem displayitem)
    {
        return StringUtils.price(displayitem.getPrice() * (double)displayitem.getQuantity());
    }

    protected void refreshLeftText(DisplayItem displayitem, View view, Context context)
    {
        ((TextView)view.findViewById(0x7f0b0036)).setText(getLeftTextString(displayitem, context));
    }
}
