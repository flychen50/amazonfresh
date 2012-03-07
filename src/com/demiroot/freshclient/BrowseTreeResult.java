// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, BrowseTree, AmazonFreshBase

public class BrowseTreeResult extends FreshAPICall
{

    public BrowseTreeResult(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public BrowseTree getTree()
    {
        return tree;
    }

    Integer currentBrowseNode;
    BrowseTree tree;
}
