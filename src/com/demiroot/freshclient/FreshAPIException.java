// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


// Referenced classes of package com.demiroot.freshclient:
//            APICallException

public class FreshAPIException extends APICallException
{

    public FreshAPIException(int i, String s, String s1)
    {
        super((new StringBuilder("API ERROR: ")).append(i).append(" (").append(s).append(")").toString());
        errorCode = i;
        reason = s;
        json = s1;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getJson()
    {
        return json;
    }

    public String getReason()
    {
        return reason;
    }

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String json;
    private String reason;
}
