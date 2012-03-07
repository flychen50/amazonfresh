// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.activities;

import android.app.AlertDialog;
import android.content.*;
import android.net.Uri;
import android.os.*;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.demiroot.amazonfresh.AFBaseActivity;
import com.demiroot.amazonfresh.AsyncRequest;
import com.demiroot.amazonfresh.barcode.CaptureActivity;
import com.demiroot.amazonfresh.ui.*;
import com.demiroot.freshclient.*;
import java.util.*;
import java.util.concurrent.*;

// Referenced classes of package com.demiroot.amazonfresh.activities:
//            CartActivity, ReceiveListActivity, DetailPageActivity

public class SearchUsingWidgetActivity extends AFBaseActivity
{
    private class Searcher
    {

        private void preloadNext(final SearchRequest newRequest)
        {
            if(nextPage != null)
                nextPage.cancel(true);
            nextPage = new FutureTask(new Callable() {

                public SearchResult call()
                    throws Exception
                {
                    return newRequest.search(getAmazonFreshBase());
                }

                public volatile Object call()
                    throws Exception
                {
                    return call();
                }

                final Searcher this$1;
                private final SearchRequest val$newRequest;

                
                {
                    this$1 = Searcher.this;
                    newRequest = searchrequest;
                    super();
                }
            }
);
            SearchUsingWidgetActivity.mExecutor.execute(nextPage);
        }

        private void preloadNext(final SearchResult newResult)
        {
            if(nextPage != null)
                nextPage.cancel(true);
            nextPage = new FutureTask(new Callable() {

                public SearchResult call()
                    throws Exception
                {
                    SearchResult searchresult;
                    if(newResult.hasNextPage())
                        searchresult = activeRequest.nextPage().search(getAmazonFreshBase());
                    else
                        searchresult = null;
                    return searchresult;
                }

                public volatile Object call()
                    throws Exception
                {
                    return call();
                }

                final Searcher this$1;
                private final SearchResult val$newResult;

                
                {
                    this$1 = Searcher.this;
                    newResult = searchresult;
                    super();
                }
            }
);
            SearchUsingWidgetActivity.mExecutor.execute(nextPage);
        }

        private void searchDone(final SearchResult results)
        {
            if(!loadFirstResult)
            {
                activeRequest = results.getSearchRequest();
                mRequest = activeRequest;
                mResult = results;
                setupSearchFilters();
                mHasNextPage = results.hasNextPage();
                if(!activeRequest.loadNextPageInParellel())
                    preloadNext(results);
            }
            start.post(new Runnable() {

                public void run()
                {
                    if(!loadFirstResult || results.getTotalResults().intValue() == 0)
                    {
                        displaySearchResults(results);
                        loadFirstResult = false;
                        if(activeRequest.loadNextPageInParellel())
                            showNextPage();
                    } else
                    {
                        loadFirstResult = false;
                        DisplayItem displayitem = (DisplayItem)results.getItems().get(0);
                        Intent intent = new Intent(_fld0, com/demiroot/amazonfresh/activities/DetailPageActivity);
                        intent.putExtra("asin", displayitem.getAsin());
                        intent.putExtra("title", displayitem.getTitle());
                        intent.putExtra("imageUrl", (new ItemDisplayBuilder()).getLargeURL(displayitem));
                        startActivity(intent);
                        finish();
                    }
                }

                final Searcher this$1;
                private final SearchResult val$results;

                
                {
                    this$1 = Searcher.this;
                    results = searchresult;
                    super();
                }
            }
);
        }

        public void search(SearchRequest searchrequest)
        {
            activeRequest = searchrequest;
            if(searchrequest != null)
            {
                AsyncRequest.buildRequest(SearchUsingWidgetActivity.this, start, searcherRunnable).setWaitMessage(getString(0x7f070077)).setTopLoadingBar().execute();
                if(searchrequest.loadNextPageInParellel())
                    preloadNext(searchrequest.nextPage());
            }
        }

        public void showNextPage()
        {
            getWorkerThread(new Runnable() {

                public void run()
                {
                    boolean flag = nextPage.isDone();
                    if(!flag)
                        showLoadingBar(getString(0x7f070077));
                    searchDone((SearchResult)nextPage.get());
                    if(!flag)
                        removeLoadingBar();
                    Message message1 = new Message();
                    message1.what = SearchUsingWidgetActivity.LOADING_NEXT_PAGE_DONE;
                    pageLoadDelayHandler.sendMessageDelayed(message1, 100L);
_L2:
                    return;
                    FreshAPILoginException freshapiloginexception;
                    freshapiloginexception;
                    Message message = new Message();
                    message.obj = getAmazonFreshBase().getAuthCookie();
                    logoutHandler.sendMessage(message);
                    continue; /* Loop/switch isn't completed */
                    Exception exception;
                    exception;
                    if(true) goto _L2; else goto _L1
_L1:
                }

                final Searcher this$1;

                
                {
                    this$1 = Searcher.this;
                    super();
                }
            }
).start();
        }

        volatile SearchRequest activeRequest;
        volatile FutureTask nextPage;
        Runnable searcherRunnable = new Runnable() {

            public void run()
            {
                SearchResult searchresult = activeRequest.search(getAmazonFreshBase());
                searchDone(searchresult);
            }

            final Searcher this$1;

                
                {
                    this$1 = Searcher.this;
                    super();
                }
        }
;
        final SearchUsingWidgetActivity this$0;



        private Searcher()
        {
            this$0 = SearchUsingWidgetActivity.this;
            super();
        }

        Searcher(Searcher searcher)
        {
            this();
        }
    }


    public SearchUsingWidgetActivity()
    {
        mRequest = null;
        mResult = null;
        mSearchResultsAdapter = null;
        mIsLoadingNextPage = false;
        mHasNextPage = true;
        displayedAsins = new HashSet();
        loadFirstResult = false;
        pageLoadDelayHandler = new Handler() {

            public void handleMessage(Message message)
            {
                if(message.what == SearchUsingWidgetActivity.LOADING_NEXT_PAGE_DONE)
                {
                    mIsLoadingNextPage = false;
                    findViewById(0x7f0b00a5).setVisibility(4);
                }
            }

            final SearchUsingWidgetActivity this$0;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
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

            final SearchUsingWidgetActivity this$0;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
                super();
            }
        }
;
    }

    private void displaySearchResults(SearchResult searchresult)
    {
        ListView listview;
        listview = (ListView)findViewById(0x102000a);
        View view = findViewById(0x1020004);
        if(searchresult.getItems().isEmpty() && loadFirstResult)
        {
            findViewById(0x7f0b00a2).setVisibility(0);
            findViewById(0x7f0b00a4).setVisibility(0);
        }
        if(!searchresult.getSearchRequest().loadNextPageInParellel())
        {
            if(loadFirstResult)
                ((TextView)findViewById(0x7f0b00a3)).setVisibility(0);
            listview.setEmptyView(view);
        }
        if(searchresult.getItems().isEmpty())
            break MISSING_BLOCK_LABEL_269;
        if(mSearchResultsAdapter != null) goto _L2; else goto _L1
_L1:
        Iterator iterator1;
        mSearchResultsAdapter = new DisplayItemAdapter(this, 0, searchresult.getItems());
        iterator1 = searchresult.getItems().iterator();
_L5:
        if(iterator1.hasNext()) goto _L4; else goto _L3
_L3:
        listview.setAdapter(mSearchResultsAdapter);
_L6:
        searchresult.hasNextPage();
_L7:
        return;
_L4:
        DisplayItem displayitem1 = (DisplayItem)iterator1.next();
        displayedAsins.add(displayitem1.getAsin());
          goto _L5
_L2:
        Iterator iterator = searchresult.getItems().iterator();
        while(iterator.hasNext()) 
        {
            DisplayItem displayitem = (DisplayItem)iterator.next();
            if(!displayedAsins.contains(displayitem.getAsin()))
            {
                displayedAsins.add(displayitem.getAsin());
                mSearchResultsAdapter.add(displayitem);
            }
        }
          goto _L6
        if(!loadFirstResult && searchresult.getTotalResults().intValue() == 0)
            findViewById(0x7f0b00a2).setVisibility(8);
          goto _L7
    }

    private void doMySearch(String s)
    {
        boolean flag = getSharedPreferences("SHARED_PREFS_KEY", 1).getBoolean("combine_pp_pref", true);
        if(loadFirstResult)
            mRequest = new BarcodeSearchRequest();
        else
        if(!flag)
            mRequest = new SearchRequest();
        else
            mRequest = new PastPurchasesFirstSearchRequest(null);
        mRequest.clearPageNum();
        mRequest.setSearchText(s);
        mSearcher.search(mRequest);
    }

    private String fixQuery(String s)
    {
        String s1 = (new StringBuilder(String.valueOf(getString(0x7f0700be)))).append("s?").toString();
        if(s.startsWith(s1))
            s = s.substring(s1.length());
        return s;
    }

    private String quicklistItemText(String s)
    {
        String s1;
        if(s.length() <= 12)
            s1 = s;
        else
            s1 = s.substring(0, 12);
        return s1;
    }

    private void resetFilterAndSearch(String s)
    {
        SearchRequest searchrequest;
        searchrequest = mRequest.clone();
        searchrequest.clearPageNum();
        if(s != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!s.equals("brands_button"))
            break; /* Loop/switch isn't completed */
        searchrequest.setBrand(null);
_L5:
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        intent.putExtra("SearchRequest", searchrequest);
        intent.putExtra("SearchTitle", searchrequest.getSearchText());
        startActivity(intent);
        if(true) goto _L1; else goto _L3
_L3:
        if(!s.equals("aisle_button")) goto _L1; else goto _L4
_L4:
        searchrequest.setBrowseNode(null);
          goto _L5
    }

    private String setupQuicklist(Intent intent, String s)
    {
        quicklist = intent.getStringArrayListExtra("QUICKLIST");
        int i = intent.getIntExtra("QUICKLIST_POSITION", 0);
        View view = findViewById(0x7f0b009f);
        if(quicklist != null)
        {
            firstQuicklistPos = intent.getIntExtra("QUICKLIST_FIRST_POSITION", i);
            view.setVisibility(0);
            TextView textview = (TextView)view.findViewById(0x7f0b00a1);
            nextQuicklistPos = (i + 1) % quicklist.size();
            TextView textview1;
            Object aobj2[];
            if(nextQuicklistPos == firstQuicklistPos)
            {
                nextQuicklistPos = -1;
                textview.setText(0x7f0700af);
            } else
            {
                Object aobj[] = new Object[1];
                aobj[0] = quicklistItemText((String)quicklist.get(nextQuicklistPos));
                textview.setText(getString(0x7f0700b1, aobj));
            }
            textview1 = (TextView)view.findViewById(0x7f0b00a0);
            prevQuicklistPos = ((i - 1) + quicklist.size()) % quicklist.size();
            if(i == firstQuicklistPos)
            {
                prevQuicklistPos = -1;
                textview1.setText(0x7f0700b0);
            } else
            {
                Object aobj1[] = new Object[1];
                aobj1[0] = quicklistItemText((String)quicklist.get(prevQuicklistPos));
                textview1.setText(getString(0x7f0700b2, aobj1));
            }
            aobj2 = new Object[3];
            aobj2[0] = s;
            aobj2[1] = Integer.valueOf(i + 1);
            aobj2[2] = Integer.valueOf(quicklist.size());
            s = getString(0x7f0700b6, aobj2);
        } else
        {
            view.setVisibility(8);
        }
        return s;
    }

    private void setupSearchFilters()
    {
        String s = mRequest.getBrand();
        if(s != null)
            ((Button)findViewById(0x7f0b009e)).setText(s);
        String s1 = mResult.getBrowseTree().getTree().getCurrentBrowseTree().getName();
        if(!mResult.getBrowseTree().getTree().noAisleFilters() && s1 != null)
            ((Button)findViewById(0x7f0b009d)).setText(s1);
        runOnUiThread(new Runnable() {

            public void run()
            {
                findViewById(0x7f0b009c).setVisibility(0);
            }

            final SearchUsingWidgetActivity this$0;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
                super();
            }
        }
);
        if(mResult.getBrowseTree().getTree().getChildren() == null)
            findViewById(0x7f0b009d).setVisibility(8);
    }

    private String translateBarCode(String s)
    {
        if(s.length() == 8) goto _L2; else goto _L1
_L1:
        String s2 = s;
_L4:
        return s2;
_L2:
        String s1;
        if(s.charAt(6) >= '3')
            break; /* Loop/switch isn't completed */
        s1 = (new StringBuilder()).append(s.charAt(6)).append("0000").append(s.charAt(3)).append(s.charAt(4)).append(s.charAt(5)).toString();
_L5:
        s2 = (new StringBuilder(String.valueOf(s.charAt(0) + s.charAt(1) + s.charAt(2)))).append(s1).append(s.charAt(7)).toString();
        if(true) goto _L4; else goto _L3
_L3:
        if(s.charAt(6) == '3')
            s1 = (new StringBuilder()).append(s.charAt(3)).append("00000").append(s.charAt(4)).append(s.charAt(5)).toString();
        else
        if(s.charAt(6) == '4')
            s1 = (new StringBuilder()).append(s.charAt(3)).append(s.charAt(4)).append("00000").append(s.charAt(5)).toString();
        else
            s1 = (new StringBuilder()).append(s.charAt(3)).append(s.charAt(4)).append(s.charAt(5)).append("0000").append(s.charAt(6)).toString();
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    private void updateSearchWithFilter(String s, Object obj)
    {
        SearchRequest searchrequest;
        searchrequest = mRequest.clone();
        searchrequest.clearPageNum();
        if(s != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!s.equals("brands_button"))
            break; /* Loop/switch isn't completed */
        String s3 = ((SearchRefinement)obj).getName();
        String s4 = searchrequest.getBrand();
        if(s4 != null && s4.equals(s3))
            continue; /* Loop/switch isn't completed */
        searchrequest.setBrand(s3);
_L6:
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        intent.putExtra("SearchRequest", searchrequest);
        intent.putExtra("SearchTitle", searchrequest.getSearchText());
        startActivity(intent);
        if(true) goto _L1; else goto _L3
_L3:
        if(!s.equals("aisle_button")) goto _L1; else goto _L4
_L4:
        String s1;
        String s2;
        s1 = ((BrowseTree)obj).getBrowseNode();
        s2 = searchrequest.getBrowseNode();
        if(s2 != null && s2.equals(s1)) goto _L1; else goto _L5
_L5:
        searchrequest.setBrowseNode(s1);
          goto _L6
    }

    public void barcodeScan(View view)
    {
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        intent.putExtra("query", "BARCODE_SEARCH_KEYWORD");
        startActivity(intent);
    }

    public void buildFilterList(Object aobj[], final String type, String s)
    {
        final ArrayAdapter adapter = new ArrayAdapter(this, 0x7f03000d, aobj);
        LayoutInflater layoutinflater = getLayoutInflater();
        final AlertDialog filters = (new android.app.AlertDialog.Builder(this)).setCustomTitle(layoutinflater.inflate(0x7f03000e, null)).setAdapter(adapter, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                updateSearchWithFilter(type, adapter.getItem(i));
                dialoginterface.dismiss();
            }

            final SearchUsingWidgetActivity this$0;
            private final ArrayAdapter val$adapter;
            private final String val$type;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
                type = s;
                adapter = arrayadapter;
                super();
            }
        }
).create();
        filters.setCanceledOnTouchOutside(true);
        filters.show();
        ((LinearLayout)filters.findViewById(0x7f0b0054)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(isResetFilterNecessary(type))
                    resetFilterAndSearch(type);
                else
                    filters.dismiss();
            }

            final SearchUsingWidgetActivity this$0;
            private final AlertDialog val$filters;
            private final String val$type;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
                type = s;
                filters = alertdialog;
                super();
            }
        }
);
        ((TextView)filters.findViewById(0x7f0b0055)).setText(s);
    }

    public void chooseAisleFilter(View view)
    {
        if(mResult != null && mResult.getBrowseTree() != null && mResult.getBrowseTree().getTree() != null)
        {
            List list = mResult.getBrowseTree().getTree().getChildren();
            if(list != null)
                buildFilterList(list.toArray(), "aisle_button", getString(0x7f07001a));
            else
                findViewById(0x7f0b009d).setVisibility(8);
        }
    }

    public void chooseBrandFilter(View view)
    {
        if(mResult != null && mResult.getBrands() != null)
            buildFilterList(mResult.getBrands().getRefinements().toArray(), "brands_button", getString(0x7f07001b));
    }

    public boolean isResetFilterNecessary(String s)
    {
        boolean flag;
        if(s == null)
            flag = false;
        else
        if((!s.equals("brands_button") || mRequest.getBrand() == null) && (!s.equals("aisle_button") || mRequest.getBrowseNode() == null))
            flag = false;
        else
            flag = true;
        return flag;
    }

    public void nextQuicklistItem(View view)
    {
        Object obj = view.getTag();
        if(obj != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i = 0;
        if(obj.equals("next"))
        {
            i = nextQuicklistPos;
            if(i == -1)
            {
                startActivity(new Intent(this, com/demiroot/amazonfresh/activities/CartActivity));
                continue; /* Loop/switch isn't completed */
            }
        } else
        if(obj.equals("prev"))
        {
            i = prevQuicklistPos;
            if(i == -1)
                continue; /* Loop/switch isn't completed */
        }
        Intent intent = new Intent(this, com/demiroot/amazonfresh/activities/SearchUsingWidgetActivity);
        intent.putExtra("query", (String)quicklist.get(i));
        intent.putStringArrayListExtra("QUICKLIST", quicklist);
        intent.putExtra("QUICKLIST_POSITION", i);
        intent.putExtra("QUICKLIST_FIRST_POSITION", firstQuicklistPos);
        startActivity(intent);
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(i != 0) goto _L2; else goto _L1
_L1:
        if(intent != null && intent.hasExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT"))
        {
            String s = intent.getStringExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT");
            boolean flag;
            String s1;
            if(s.startsWith(getString(0x7f0700be)))
                flag = false;
            else
                flag = true;
            loadFirstResult = flag;
            s1 = fixQuery(s);
            if(s1.startsWith((new StringBuilder(String.valueOf(getString(0x7f0700be)))).append("list?").toString()))
            {
                Intent intent1 = new Intent(this, com/demiroot/amazonfresh/activities/ReceiveListActivity);
                intent1.setAction("android.intent.action.VIEW");
                intent1.setData(Uri.parse(s1));
                startActivity(intent1);
                finish();
            } else
            {
                String s2 = translateBarCode(s1);
                loadActionBar(s2);
                doMySearch(s2);
            }
        } else
        {
            showToastOnUIThread(getString(0x7f070076), 0);
            finish();
        }
_L4:
        return;
_L2:
        if(i == 11)
            if(j == -1)
            {
                ArrayList arraylist = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
                setActionBarTitle((String)arraylist.get(0));
                doMySearch((String)arraylist.get(0));
            } else
            {
                finish();
            }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mSearcher = new Searcher(null);
        setContentView(0x7f030022);
        Intent intent = getIntent();
        String s;
        if("android.intent.action.VIEW".equals(intent.getAction()))
            s = fixQuery(intent.getDataString());
        else
            s = intent.getStringExtra("query");
        if(s == null)
        {
            SearchRequest searchrequest;
            if(intent.getBooleanExtra("merchSearch", false))
                searchrequest = SearchRequest.createFromURL(intent.getStringExtra("merchUrl"));
            else
                searchrequest = (SearchRequest)intent.getSerializableExtra("SearchRequest");
            if(searchrequest != null)
            {
                mRequest = searchrequest;
                mSearcher.search(mRequest);
                loadActionBar(intent.getStringExtra("SearchTitle"));
            }
        } else
        if("BARCODE_SEARCH_KEYWORD".equals(s) && !isUserAMonkey())
        {
            startActivityForResult(new Intent(this, com/demiroot/amazonfresh/barcode/CaptureActivity), 0);
        } else
        {
            loadActionBar(setupQuicklist(intent, s));
            doMySearch(s);
        }
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onRestart()
    {
        super.onRestart();
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
                final SearchUsingWidgetActivity this$0;

            
            {
                this$0 = SearchUsingWidgetActivity.this;
                super();
                priorFirst = -1;
            }
            }
);
            betterscrollinglistview.addListener(scrollBottomListener);
        }
    }

    public static final String ADD_FILTER_KEYWORD = "ADD_FILTER_KEYWORD";
    public static final String BARCODE_SEARCH_KEYWORD = "BARCODE_SEARCH_KEYWORD";
    public static final String COMBINE_PP_PREF = "combine_pp_pref";
    private static int LOADING_NEXT_PAGE_DONE = 0;
    public static final String QUICKLIST = "QUICKLIST";
    public static final String QUICKLIST_FIRST_POSITION = "QUICKLIST_FIRST_POSITION";
    public static final String QUICKLIST_POSITION = "QUICKLIST_POSITION";
    public static final String SEARCH_REQUEST = "SearchRequest";
    public static final String SEARCH_TITLE = "SearchTitle";
    public static final int VOICE_SEARCH_REQUEST_CODE = 11;
    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final String AISLE_BUTTON = "aisle_button";
    private final String BRANDS_BUTTON = "brands_button";
    private Set displayedAsins;
    private int firstQuicklistPos;
    private boolean loadFirstResult;
    private volatile boolean mHasNextPage;
    private boolean mIsLoadingNextPage;
    private volatile SearchRequest mRequest;
    private SearchResult mResult;
    private DisplayItemAdapter mSearchResultsAdapter;
    private Searcher mSearcher;
    private int nextQuicklistPos;
    private Handler pageLoadDelayHandler;
    private int prevQuicklistPos;
    private ArrayList quicklist;
    private Runnable scrollBottomListener;

    static 
    {
        LOADING_NEXT_PAGE_DONE = 9;
    }
















}
