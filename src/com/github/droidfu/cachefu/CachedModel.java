// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.cachefu;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;

// Referenced classes of package com.github.droidfu.cachefu:
//            ModelCache

public abstract class CachedModel
    implements Parcelable
{

    public CachedModel()
    {
        transactionId = 0x8000000000000000L;
    }

    public CachedModel(Parcel parcel)
        throws IOException
    {
        transactionId = 0x8000000000000000L;
        readFromParcel(parcel);
    }

    public CachedModel(String s)
    {
        transactionId = 0x8000000000000000L;
        id = s;
    }

    public static CachedModel find(ModelCache modelcache, String s, Class class1)
    {
label0:
        {
            CachedModel cachedmodel;
            CachedModel cachedmodel1;
            try
            {
                cachedmodel1 = (CachedModel)class1.newInstance();
            }
            catch(Exception exception)
            {
                cachedmodel = null;
                if(false)
                    ;
                else
                    break label0;
            }
            cachedmodel1.setId(s);
            if(cachedmodel1.reload(modelcache))
                cachedmodel = cachedmodel1;
            else
                cachedmodel = null;
        }
        return cachedmodel;
    }

    public abstract String createKey(String s);

    public int describeContents()
    {
        return 0;
    }

    public String getId()
    {
        return id;
    }

    public String getKey()
    {
        String s;
        if(id == null)
            s = null;
        else
            s = createKey(id);
        return s;
    }

    public void readFromParcel(Parcel parcel)
        throws IOException
    {
        id = parcel.readString();
        transactionId = parcel.readLong();
    }

    public boolean reload(ModelCache modelcache)
    {
        String s = getKey();
        boolean flag;
        if(modelcache != null && s != null)
        {
            CachedModel cachedmodel = (CachedModel)modelcache.get(s);
            if(cachedmodel != null && cachedmodel.transactionId > transactionId)
            {
                reloadFromCachedModel(modelcache, cachedmodel);
                flag = true;
            } else
            {
                flag = false;
            }
        } else
        {
            flag = false;
        }
        return flag;
    }

    public abstract boolean reloadFromCachedModel(ModelCache modelcache, CachedModel cachedmodel);

    public boolean save(ModelCache modelcache)
    {
        return save(modelcache, getKey());
    }

    protected boolean save(ModelCache modelcache, String s)
    {
        boolean flag;
        if(modelcache != null && s != null)
        {
            modelcache.put(s, this);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public void setId(String s)
    {
        id = s;
    }

    void setTransactionId(long l)
    {
        transactionId = l;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(id);
        parcel.writeLong(transactionId);
    }

    private String id;
    private long transactionId;
}
