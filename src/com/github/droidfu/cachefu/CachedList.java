// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import android.os.Parcel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.github.droidfu.cachefu:
//            CachedModel, ModelCache

public class CachedList extends CachedModel
{

    public CachedList()
    {
        list = new ArrayList();
    }

    public CachedList(Parcel parcel)
        throws IOException
    {
        super(parcel);
    }

    public CachedList(Class class1)
    {
        initList(class1);
        list = new ArrayList();
    }

    public CachedList(Class class1, int i)
    {
        initList(class1);
        list = new ArrayList(i);
    }

    public CachedList(Class class1, String s)
    {
        super(s);
        initList(class1);
        list = new ArrayList();
    }

    private void initList(Class class1)
    {
        clazz = class1;
    }

    public String createKey(String s)
    {
        return (new StringBuilder("list_")).append(s).toString();
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(!(obj instanceof CachedList))
        {
            flag = false;
        } else
        {
            CachedList cachedlist = (CachedList)obj;
            if(clazz.equals(cachedlist.clazz) && list.equals(cachedlist.list))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public ArrayList getList()
    {
        return list;
    }

    public void readFromParcel(Parcel parcel)
        throws IOException
    {
        String s;
        super.readFromParcel(parcel);
        s = parcel.readString();
        clazz = Class.forName(s);
        list = parcel.createTypedArrayList((android.os.Parcelable.Creator)clazz.getField("CREATOR").get(this));
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
          goto _L1
    }

    public boolean reloadAll(ModelCache modelcache)
    {
        boolean flag = reload(modelcache);
        Iterator iterator = list.iterator();
        do
        {
            do
                if(!iterator.hasNext())
                    return flag;
            while(!((CachedModel)iterator.next()).reload(modelcache));
            flag = true;
        } while(true);
    }

    public boolean reloadFromCachedModel(ModelCache modelcache, CachedModel cachedmodel)
    {
        CachedList cachedlist = (CachedList)cachedmodel;
        clazz = cachedlist.clazz;
        list = cachedlist.list;
        return false;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        super.writeToParcel(parcel, i);
        parcel.writeString(clazz.getCanonicalName());
        parcel.writeTypedList(list);
    }

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public CachedList createFromParcel(Parcel parcel)
        {
            CachedList cachedlist;
            try
            {
                cachedlist = new CachedList(parcel);
            }
            catch(IOException ioexception)
            {
                ioexception.printStackTrace();
                cachedlist = null;
            }
            return cachedlist;
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public CachedList[] newArray(int i)
        {
            return new CachedList[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    }
;
    protected Class clazz;
    protected ArrayList list;

}
