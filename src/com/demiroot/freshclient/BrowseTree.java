// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.util.List;

// Referenced classes of package com.demiroot.freshclient:
//            FreshAPICall, LinkAPICall, AmazonFreshBase

public class BrowseTree extends FreshAPICall
{

    public BrowseTree(AmazonFreshBase amazonfreshbase)
    {
        super(amazonfreshbase);
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(obj == null || !(obj instanceof BrowseTree))
        {
            flag = false;
        } else
        {
            BrowseTree browsetree = (BrowseTree)obj;
            if((browseNode == null && browsetree.browseNode == null || browseNode.equals(browsetree.browseNode)) && (name == null && browsetree.name == null || name.equals(browsetree.name)) && (count == null && browsetree.count == null || count.equals(browsetree.count)) && (children == null && browsetree.children == null || children.equals(browsetree.children)) && (aisleChildren == null && browsetree.aisleChildren == null || aisleChildren.equals(browsetree.aisleChildren)))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public String getBrowseNode()
    {
        return browseNode;
    }

    public List getChildren()
    {
        List list;
        if(children == null && aisleChildren != null)
            list = aisleChildren.getChildren();
        else
            list = children;
        return list;
    }

    public Integer getCount()
    {
        return count;
    }

    public BrowseTree getCurrentBrowseTree()
    {
        BrowseTree browsetree;
        if(children == null && aisleChildren != null)
            browsetree = aisleChildren.getCurrentBrowseTree();
        else
            browsetree = this;
        return browsetree;
    }

    public String getName()
    {
        return name;
    }

    public LinkAPICall getUrl()
    {
        return url;
    }

    public boolean noAisleFilters()
    {
        boolean flag;
        if(browseNode.equals("306094011") && aisleChildren == null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(name))).append(" (").append(count).append(")").toString();
    }

    private static final String PARENT_NODE = "306094011";
    BrowseTree aisleChildren;
    String browseNode;
    List children;
    Integer count;
    String name;
    LinkAPICall url;
}
