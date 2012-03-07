// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.freshclient;

import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.Cookie;

public class SeralizableCookie
    implements Cookie, Serializable
{

    public SeralizableCookie(Cookie cookie)
    {
        comment = cookie.getComment();
        commentURL = cookie.getCommentURL();
        domain = cookie.getDomain();
        expiryDate = cookie.getExpiryDate();
        name = cookie.getName();
        path = cookie.getPath();
        ports = cookie.getPorts();
        value = cookie.getValue();
        version = cookie.getVersion();
        isSecure = cookie.isSecure();
        isPersistent = cookie.isPersistent();
    }

    public String getComment()
    {
        return comment;
    }

    public String getCommentURL()
    {
        return commentURL;
    }

    public String getDomain()
    {
        return domain;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return path;
    }

    public int[] getPorts()
    {
        return ports;
    }

    public String getValue()
    {
        return value;
    }

    public int getVersion()
    {
        return version;
    }

    public boolean isExpired(Date date)
    {
        boolean flag;
        if(getExpiryDate() == null)
            flag = false;
        else
            flag = getExpiryDate().before(date);
        return flag;
    }

    public boolean isPersistent()
    {
        return isPersistent;
    }

    public boolean isSecure()
    {
        return isSecure;
    }

    public void setValue(String s)
    {
        value = s;
    }

    public String toString()
    {
        return (new StringBuilder("[version: ")).append(version).append("][name: ").append(name).append("][value: ").append(value).append("][domain: ").append(domain).append("][path: ").append(path).append("][expiry: ").append(expiryDate).append("]").toString();
    }

    private String comment;
    private String commentURL;
    private String domain;
    private Date expiryDate;
    private boolean isPersistent;
    private boolean isSecure;
    private String name;
    private String path;
    private int ports[];
    private String value;
    private int version;
}
