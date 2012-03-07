// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing;


public final class ReaderException extends Exception
{

    private ReaderException()
    {
    }

    public static ReaderException getInstance()
    {
        return instance;
    }

    public Throwable fillInStackTrace()
    {
        return null;
    }

    private static final ReaderException instance = new ReaderException();

}
