// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;


public class APICallException extends RuntimeException
{

    public APICallException(String s)
    {
        super(s);
    }

    public APICallException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 1L;
}
