// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.demiroot.amazonfresh.activities.LoginActivity;
import com.demiroot.amazonfresh.db.CommonFoodsDB;
import com.demiroot.amazonfresh.listeners.ExceptionHandler;
import com.demiroot.amazonfresh.ui.ActionBarHelper;
import com.demiroot.freshclient.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.demiroot.amazonfresh:
//            AFApplication

public class AFBaseActivity extends Activity
{

    public AFBaseActivity()
    {
        mActionBarHelper = new ActionBarHelper(this);
        requiresLogin = true;
        logoutHandler = new Handler(new android.os.Handler.Callback() {

            public boolean handleMessage(Message message)
            {
                if(message.what != 1 || !AFBaseActivity.loggingIn) goto _L2; else goto _L1
_L1:
                Intent intent1 = new Intent("com.demiroot.freshclient.LoginActivity.Msg");
                intent1.putExtra("MESSAGE", (String)message.obj);
                sendBroadcast(intent1);
_L4:
                return false;
_L2:
                if(requiresLogin && !AFBaseActivity.loggingIn)
                {
                    String s = (String)message.obj;
                    String s1 = getAmazonFreshBase().getAuthCookie();
                    if(s == null || s.equals(s1))
                    {
                        AFBaseActivity.loggingIn = true;
                        Toast.makeText(AFBaseActivity.this, 0x7f07009b, 1).show();
                        getAmazonFreshBase().setLoginRequired();
                        Intent intent = new Intent(AFBaseActivity.this, com/demiroot/amazonfresh/activities/LoginActivity);
                        removeOldBroadcasts();
                        startActivityForResult(intent, 1);
                    }
                }
                if(true) goto _L4; else goto _L3
_L3:
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
);
        uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread thread, Throwable throwable)
            {
                Message message = exceptionHandler.obtainMessage(1, throwable);
                exceptionHandler.sendMessage(message);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
;
    }

    public static void sendBroadcastSync(Activity activity, Intent intent)
    {
        Object obj = BROADCAST_OBJECT_LOCK;
        obj;
        JVM INSTR monitorenter ;
        activity.sendBroadcast(intent);
_L1:
        return;
        ConcurrentModificationException concurrentmodificationexception;
        concurrentmodificationexception;
        Thread.yield();
        try
        {
            activity.sendBroadcast(intent);
        }
        catch(ConcurrentModificationException concurrentmodificationexception1)
        {
            Log.e("ERROR_MSG", "Error sending broadcase", concurrentmodificationexception1);
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public static void setStickyBroadcastSync(Activity activity, Intent intent)
    {
        Object obj = BROADCAST_OBJECT_LOCK;
        obj;
        JVM INSTR monitorenter ;
        activity.sendStickyBroadcast(intent);
_L1:
        return;
        ConcurrentModificationException concurrentmodificationexception;
        concurrentmodificationexception;
        Thread.yield();
        try
        {
            activity.sendStickyBroadcast(intent);
        }
        catch(ConcurrentModificationException concurrentmodificationexception1)
        {
            Log.e("ERROR_MSG", "Error sending broadcase", concurrentmodificationexception1);
        }
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public void broadcastAddressBook()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                AddressBook addressbook = new AddressBook(getAmazonFreshBase());
                Intent intent = new Intent(com/demiroot/freshclient/AddressBook.getName());
                intent.putExtra("response", new ArrayList(addressbook.getAddressBook()));
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
).start();
    }

    protected void broadcastAvailableSlotsForCart(final String externalAddressId)
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                DeliverySlots deliveryslots = getAmazonFreshBase().getAvailableSlotsForCart(externalAddressId);
                Intent intent = new Intent(com/demiroot/freshclient/DeliverySlots.getName());
                intent.putExtra("response", deliveryslots);
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;
            private final String val$externalAddressId;

            
            {
                this$0 = AFBaseActivity.this;
                externalAddressId = s;
                super();
            }
        }
).start();
    }

    public void broadcastCart()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                Order order = getAmazonFreshBase().cart();
                Intent intent = new Intent(com/demiroot/freshclient/Order.getName());
                intent.putExtra("response", order);
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
).start();
    }

    protected void broadcastCartItems(final Order cart)
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                Intent intent = new Intent((new StringBuilder(String.valueOf(com/demiroot/freshclient/CartItem.getName()))).append("s").toString());
                intent.putExtra("response", new ArrayList(cart.getItems()));
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;
            private final Order val$cart;

            
            {
                this$0 = AFBaseActivity.this;
                cart = order;
                super();
            }
        }
).start();
    }

    protected void broadcastDetail(final String asin)
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                ItemDetail itemdetail = getAmazonFreshBase().getDetail(asin);
                Intent intent = new Intent(com/demiroot/freshclient/ItemDetail.getName());
                intent.putExtra("response", itemdetail);
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.sendBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;
            private final String val$asin;

            
            {
                this$0 = AFBaseActivity.this;
                asin = s;
                super();
            }
        }
).start();
    }

    public void broadcastMerchandising()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                GatewayContent gatewaycontent = new GatewayContent(getAmazonFreshBase());
                Intent intent = new Intent(com/demiroot/freshclient/GatewayContent.getName());
                ArrayList arraylist = new ArrayList(gatewaycontent.getFeatured());
                arraylist.addAll(gatewaycontent.getOurPicks());
                intent.putExtra("response", arraylist);
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
).start();
    }

    protected void broadcastPastPurchases()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                PastPurchases pastpurchases = getAmazonFreshBase().getPastPurchases();
                SearchRequest searchrequest = new SearchRequest(null);
                searchrequest.setFilterByPP(true);
                com.demiroot.freshclient.BrowseTree browsetree = searchrequest.search(getAmazonFreshBase()).getBrowseTree().getTree();
                Intent intent = new Intent(com/demiroot/freshclient/PastPurchases.getName());
                intent.putExtra("response", pastpurchases);
                intent.putExtra("filter", browsetree);
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
).start();
    }

    public void broadcastQuicklist()
    {
        getWorkerThread(new Runnable() {

            public void run()
            {
                QuickList quicklist = new QuickList(getAmazonFreshBase());
                Intent intent = new Intent(com/demiroot/freshclient/QuickList.getName());
                intent.putExtra("response", new ArrayList(quicklist.getTerms()));
                intent.putExtra("uid", System.currentTimeMillis());
                AFBaseActivity.setStickyBroadcastSync(AFBaseActivity.this, intent);
            }

            final AFBaseActivity this$0;

            
            {
                this$0 = AFBaseActivity.this;
                super();
            }
        }
).start();
    }

    public AFApplication getAFAppication()
    {
        return (AFApplication)getApplication();
    }

    public AmazonFreshBase getAmazonFreshBase()
    {
        return getAFAppication().getAmazonFreshBase();
    }

    public Handler getHandler()
    {
        return handler;
    }

    public Thread getWorkerThread(Runnable runnable)
    {
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        return thread;
    }

    public boolean isLoggedIn()
    {
        return getAmazonFreshBase().isLoggedIn();
    }

    public boolean isUserAMonkey()
    {
        return false;
    }

    protected void loadActionBar(String s)
    {
        mActionBarHelper.setTitle(s);
        mActionBarHelper.display();
        broadcastCart();
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        if(i == 1)
        {
            Intent intent1 = getIntent();
            finish();
            startActivity(intent1);
        } else
        {
            super.onActivityResult(i, j, intent);
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        exceptionHandler = new ExceptionHandler(this);
        handler = new Handler();
        activeDialog = null;
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        getAFAppication().save();
    }

    protected void onStart()
    {
        super.onStart();
        registerReceiver(mActionBarHelper, new IntentFilter(com/demiroot/freshclient/Order.getName()));
        if(requiresLogin && !isLoggedIn())
            startActivity(new Intent(this, com/demiroot/amazonfresh/activities/LoginActivity));
    }

    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(mActionBarHelper);
        if(activeDialog != null)
        {
            activeDialog.dismiss();
            activeDialog = null;
        }
    }

    public boolean post(Runnable runnable)
    {
        return handler.post(runnable);
    }

    public void redirectToSignin(String s)
    {
        Message message = new Message();
        message.obj = s;
        logoutHandler.sendMessage(message);
    }

    public void removeCartBroadcasts()
    {
        Object obj = BROADCAST_OBJECT_LOCK;
        obj;
        JVM INSTR monitorenter ;
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/CartItem.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/Order.getName()));
        return;
    }

    public void removeLoadingBar()
    {
        removeLoadingBar(null);
    }

    public void removeLoadingBar(final Object tag)
    {
        handler.post(new Runnable() {

            public void run()
            {
                View view = findViewById(0x7f0b00ba);
                if(tag == null || view.getTag() == tag)
                {
                    view.setVisibility(8);
                    findViewById(0x7f0b00bc).clearAnimation();
                }
            }

            final AFBaseActivity this$0;
            private final Object val$tag;

            
            {
                this$0 = AFBaseActivity.this;
                tag = obj;
                super();
            }
        }
);
    }

    public void removeOldBroadcasts()
    {
        Object obj = BROADCAST_OBJECT_LOCK;
        obj;
        JVM INSTR monitorenter ;
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/PastPurchases.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/ItemDetail.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/CartItem.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/Order.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/DeliverySlots.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/GatewayContent.getName()));
        removeStickyBroadcast(new Intent(com/demiroot/freshclient/AddressBook.getName()));
        return;
    }

    protected void setActionBarTitle(String s)
    {
        mActionBarHelper.setTitle(s);
    }

    public void showLoadingBar(String s)
    {
        showLoadingBar(s, null);
    }

    public void showLoadingBar(final String msg, final Object tag)
    {
        handler.post(new Runnable() {

            public void run()
            {
                View view = findViewById(0x7f0b00ba);
                view.setTag(tag);
                view.setVisibility(0);
                findViewById(0x7f0b00bc).setAnimation(AnimationUtils.loadAnimation(AFBaseActivity.this, 0x7f04000d));
                ((TextView)findViewById(0x7f0b00bb)).setText(msg);
            }

            final AFBaseActivity this$0;
            private final String val$msg;
            private final Object val$tag;

            
            {
                this$0 = AFBaseActivity.this;
                tag = obj;
                msg = s;
                super();
            }
        }
);
    }

    public void showMessage(String s)
    {
        activeDialog = (new android.app.AlertDialog.Builder(this)).setMessage(s).setPositiveButton(getString(0x7f070008), null).show();
    }

    protected void showToast(String s, int i)
    {
        Toast.makeText(this, s, i).show();
    }

    public void showToastOnUIThread(final String toast, final int duration)
    {
        handler.post(new Runnable() {

            public void run()
            {
                showToast(toast, duration);
            }

            final AFBaseActivity this$0;
            private final int val$duration;
            private final String val$toast;

            
            {
                this$0 = AFBaseActivity.this;
                toast = s;
                duration = i;
                super();
            }
        }
);
    }

    public void updateSearchSuggestions(final AmazonFreshBase base)
    {
        getWorkerThread(new Runnable() {

            /**
             * @deprecated Method run is deprecated
             */

            public void run()
            {
                this;
                JVM INSTR monitorenter ;
                boolean flag = AFBaseActivity.lock.tryLock();
                if(flag) goto _L2; else goto _L1
_L1:
                this;
                JVM INSTR monitorexit ;
                return;
_L2:
                SharedPreferences sharedpreferences = getSharedPreferences("SHARED_PREFS_KEY", 2);
                long l = sharedpreferences.getLong("common_terms_last_update", 0L);
                if(System.currentTimeMillis() > 0x48190800L + l)
                {
                    CommonFoodsDB.updateNewSuggestions(new ArrayList((new SearchSuggestions(base)).getCommonTerms()), getApplicationContext());
                    android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putLong("common_terms_last_update", (new Date(System.currentTimeMillis())).getTime());
                    editor.commit();
                }
                AFBaseActivity.lock.unlock();
                  goto _L1
                Exception exception;
                exception;
                throw exception;
                Exception exception2;
                exception2;
                Log.e("ERROR_MSG", "Error updating commong search terms", exception2);
                AFBaseActivity.lock.unlock();
                  goto _L1
                Exception exception1;
                exception1;
                AFBaseActivity.lock.unlock();
                throw exception1;
            }

            final AFBaseActivity this$0;
            private final AmazonFreshBase val$base;

            
            {
                this$0 = AFBaseActivity.this;
                base = amazonfreshbase;
                super();
            }
        }
).start();
    }

    private static final Object BROADCAST_OBJECT_LOCK = new Object();
    public static final String COMMON_TERMS_LAST_UPDATE = "common_terms_last_update";
    public static final long COMMON_TERMS_UPDATE_FRQ_IN_MILLIS = 0x48190800L;
    public static final int LOGIN_REQUEST_CODE = 1;
    public static final int REDIRECT_TO_LOGIN = 0;
    public static final int SHOW_MESSAGE_ON_LOGIN = 1;
    private static final ReentrantLock lock = new ReentrantLock();
    public static volatile boolean loggingIn = false;
    private Dialog activeDialog;
    private Handler exceptionHandler;
    protected Handler handler;
    public Handler logoutHandler;
    private ActionBarHelper mActionBarHelper;
    protected boolean requiresLogin;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;



}
