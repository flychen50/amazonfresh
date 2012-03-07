// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.listeners.RemoveLoadingBarReceiver;
import com.demiroot.amazonfresh.ui.BetterScrollingListView;
import com.demiroot.amazonfresh.ui.DisplayItemAdapter;
import com.demiroot.freshclient.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class PastPurchasesActivity extends AFBaseActivity
{
    private class Searcher extends RemoveLoadingBarReceiver
    {

        private void displayResults(final List results)
        {
            
// JavaClassFileOutputException: get_constant: invalid tag

        private void preloadNext(final PastPurchases newResult)
        {
            if(nextPage != null)
                nextPage.cancel(true);
            nextPage = new FutureTask(new Callable() {

                public PastPurchases call()
                    throws Exception
                {
                    PastPurchases pastpurchases;
                    if(newResult.hasNextPage())
                    {
                        newResult.setAmazonFreshBase(getAmazonFreshBase());
                        pastpurchases = newResult.nextPage();
                    } else
                    {
                        pastpurchases = null;
                    }
                    return pastpurchases;
                }

                public volatile Object call()
                    throws Exception
                {
                    return call();
                }

                final Searcher this$1;
                private final PastPurchases val$newResult;

                
                {
                    this$1 = Searcher.this;
                    newResult = pastpurchases;
                    super();
                }
            }
);
            PastPurchasesActivity.mExecutor.execute(nextPage);
        }

        private void searchDone(PastPurchases pastpurchases, BrowseTree browsetree)
        {
            if(mTreeResult == null || !mTreeResult.equals(browsetree) || mPastPurchaseResult == null || !mPastPurchaseResult.equals(pastpurchases))
            {
                mTreeResult = browsetree;
                mPastPurchaseResult = pastpurchases;
                mHasNextPage = pastpurchases.hasNextPage();
                preloadNext(pastpurchases);
                displayResults(pastpurchases.getItems());
            }
        }

        private void searchDone(SearchResult searchresult)
        {
            if(searchresult != null)
            {
                BrowseTree browsetree = searchresult.getBrowseTree().getTree();
                mTreeResult = browsetree;
                displayResults(searchresult.getItems());
            }
        }

        public void onReceiveSafe(Context context, Intent intent)
        {
            if(browseNode == null)
                searchDone((PastPurchases)intent.getSerializableExtra("response"), (BrowseTree)intent.getSerializableExtra("filter"));
        }

        public void search()
        {
            showLoadingBar(getString(0x7f070056));
            broadcastPastPurchases();
        }

        public void search(String s)
        {
            browseNode = s;
            final SearchRequest activeRequest = new SearchRequest(null);
            activeRequest.setFilterByPP(true);
            activeRequest.setBrowseNode(s);
            AsyncRequest.buildRequest(PastPurchasesActivity.this, 
// JavaClassFileOutputException: get_constant: invalid tag

        public void showNextPage()
        {
            getWorkerThread(new Runnable() {

                public void run()
                {
                    boolean flag = nextPage.isDone();
                    if(!flag)
                        showLoadingBar(getString(0x7f070056));
                    searchDone((PastPurchases)nextPage.get(), mTreeResult);
                    if(!flag)
                        removeLoadingBar();
                    Message message1 = new Message();
                    message1.what = PastPurchasesActivity.LOADING_NEXT_PAGE_DONE;
                    pageLoadDelayHandler.sendMessageDelayed(message1, 100L);
_L1:
                    return;
                    FreshAPILoginException freshapiloginexception;
                    freshapiloginexception;
                    Message message = new Message();
                    message.obj = getAmazonFreshBase().getAuthCookie();
                    logoutHandler.sendMessage(message);
                      goto _L1
                    Exception exception;
                    exception;
                    Log.e("ERROR_MSG", "Error loading next page of past purchases", exception);
                      goto _L1
                }

                final Searcher this$1;

                
                {
                    this$1 = Searcher.this;
                    super();
                }
            }
).start();
        }

        volatile String browseNode;
        volatile FutureTask nextPage;
        final PastPurchasesActivity this$0;




        public Searcher()
        {
            this$0 = PastPurchasesActivity.this;
            super(PastPurchasesActivity.this);
        }

        // Unreferenced inner class com/demiroot/amazonfresh/activities/PastPurchasesActivity$Searcher$3

/* anonymous class */
        class _cls3
            implements Runnable
        {

            public void run()
            {
                SearchResult searchresult = activeRequest.search(getAmazonFreshBase());
                searchDone(searchresult);
            }

            final Searcher this$1;
            private final SearchRequest val$activeRequest;

                
                {
                    this$1 = Searcher.this;
                    activeRequest = searchrequest;
                    super();
                }
        }


        // Unreferenced inner class com/demiroot/amazonfresh/activities/PastPurchasesActivity$Searcher$4

/* anonymous class */
        class _cls4
            implements Runnable
        {

            public void run()
            {
                displayFilter();
                displaySearchResults(results);
            }

            final Searcher this$1;
            private final List val$results;

                
                {
                    this$1 = Searcher.this;
                    results = list;
                    super();
                }
        }

    }


    public PastPurchasesActivity()
    {
        mTreeResult = null;
        mSearcher = new Searcher();
        mSearchResultsAdapter = null;
        mIsLoadingNextPage = false;
        mHasNextPage = true;
        mPastPurchaseResult = null;
        pageLoadDelayHandler = new Handler() {

            public void handleMessage(Message message)
            {
                if(message.what == PastPurchasesActivity.LOADING_NEXT_PAGE_DONE)
                {
                    mIsLoadingNextPage = false;
                    findViewById(0x7f0b00a5).setVisibility(4);
                }
            }

            final PastPurchasesActivity this$0;

            
            {
                this$0 = PastPurchasesActivity.this;
                super();
            }
        }
;
        scrollBottomListener = new Runnable() {

            public void run()
            {
                if(!mIsLoadingNextPage && mHasNextPage)
                {
                    mIsLoadingNextPage = true;
                    mSearcher.showNextPage();
                }
            }

            final PastPurchasesActivity this$0;

            
            {
                this$0 = PastPurchasesActivity.this;
                super();
            }
        }
;
    }

    private void displaySearchResults(List list)
    {
        ListView listview = (ListView)findViewById(0x102000a);
        listview.setEmptyView(findViewById(0x1020004));
        if(!list.isEmpty())
            if(mSearchResultsAdapter == null)
            {
                mSearchResultsAdapter = new DisplayItemAdapter(this, 0, list);
                listview.setAdapter(mSearchResultsAdapter);
            } else
            {
                Iterator iterator = list.iterator();
                while(iterator.hasNext()) 
                {
                    DisplayItem displayitem = (DisplayItem)iterator.next();
                    mSearchResultsAdapter.add(displayitem);
                }
            }
    }

    public void chooseAisleFilter(View view)
    {
        if(mTreeResult != null)
        {
            final ArrayAdapter adapter = new ArrayAdapter(this, 0x7f03000d, mTreeResult.getChildren().toArray());
            LayoutInflater layoutinflater = getLayoutInflater();
            AlertDialog alertdialog = (new android.app.AlertDialog.Builder(this)).setCustomTitle(layoutinflater.inflate(0x7f03000e, null)).setAdapter(adapter, new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int i)
                {
                    performSearch(((BrowseTree)adapter.getItem(i)).getBrowseNode());
                    dialoginterface.dismiss();
                }

                final PastPurchasesActivity this$0;
                private final ArrayAdapter val$adapter;

            
            {
                this$0 = PastPurchasesActivity.this;
                adapter = arrayadapter;
                super();
            }
            }
).create();
            alertdialog.setCanceledOnTouchOutside(true);
            alertdialog.show();
            ((LinearLayout)alertdialog.findViewById(0x7f0b0054)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    resetFilter();
                }

                final PastPurchasesActivity this$0;

            
            {
                this$0 = PastPurchasesActivity.this;
                super();
            }
            }
);
            ((TextView)alertdialog.findViewById(0x7f0b0055)).setText(getString(0x7f07001a));
        }
    }

    public void displayFilter()
    {
        String s = null;
        if(mTreeResult != null)
            s = mTreeResult.getCurrentBrowseTree().getName();
        if(s != null && !mTreeResult.noAisleFilters())
            ((Button)findViewById(0x7f0b009d)).setText(s);
        findViewById(0x7f0b009c).setVisibility(0);
        findViewById(0x7f0b009e).setVisibility(8);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030022);
        loadActionBar(getString(0x7f070074));
        String s = (String)getIntent().getSerializableExtra("BrowseNode");
        if(s == null)
            mSearcher.search();
        else
            mSearcher.search(s);
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        BetterScrollingListView betterscrollinglistview = (BetterScrollingListView)findViewById(0x102000a);
        if(betterscrollinglistview != null)
        {
            betterscrollinglistview.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {

                public void onScroll(AbsListView abslistview, int i, int j, int k)
                {
                    if(j < k && i + j == k && i != priorFirst)
                    {
                        priorFirst = i;
                        mIsLoadingNextPage = true;
                        mSearcher.showNextPage();
                    }
                }

                public void onScrollStateChanged(AbsListView abslistview, int i)
                {
                }

                private int priorFirst;
                final PastPurchasesActivity this$0;

            
            {
                this$0 = PastPurchasesActivity.this;
                super();
                priorFirst = -1;
            }
            }
);
            betterscrollinglistview.addListener(scrollBottomListener);
        }
    }

    public void onStart()
    {
        super.onStart();
        registerReceiver(mSearcher, new IntentFilter(com/demiroot/freshclient/PastPurchases.getName()));
    }

    public void onStop()
    {
        super.onStop();
        unregisterReceiver(mSearcher);
    }

    public void performSearch(String s)
    {
        if(s != null && !s.equals(mTreeResult.getCurrentBrowseTree().getBrowseNode()))
        {
            Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/PastPurchasesActivity);
            intent.putExtra("BrowseNode", s);
            startActivity(intent);
        }
    }

    public void resetFilter()
    {
        startActivity(new Intent(this, com/demiroot/amazonfresh/activities/PastPurchasesActivity));
    }

    private static final String BROWSE_NODE = "BrowseNode";
    private static int LOADING_NEXT_PAGE_DONE = 9;
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean mHasNextPage;
    private boolean mIsLoadingNextPage;
    private volatile PastPurchases mPastPurchaseResult;
    private DisplayItemAdapter mSearchResultsAdapter;
    private Searcher mSearcher;
    private volatile BrowseTree mTreeResult;
    private Handler pageLoadDelayHandler;
    private Runnable scrollBottomListener;















}
