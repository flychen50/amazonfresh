// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;


// Referenced classes of package com.google.zxing.client.result:
//            ParsedResult, ParsedResultType

public final class SMSParsedResult extends ParsedResult
{

    public SMSParsedResult(String s, String s1, String s2, String s3, String s4, String s5)
    {
        super(ParsedResultType.SMS);
        smsURI = s;
        number = s1;
        via = s2;
        subject = s3;
        body = s4;
        title = s5;
    }

    public String getBody()
    {
        return body;
    }

    public String getDisplayResult()
    {
        StringBuffer stringbuffer = new StringBuffer();
        maybeAppend(number, stringbuffer);
        maybeAppend(via, stringbuffer);
        maybeAppend(subject, stringbuffer);
        maybeAppend(body, stringbuffer);
        maybeAppend(title, stringbuffer);
        return stringbuffer.toString();
    }

    public String getNumber()
    {
        return number;
    }

    public String getSMSURI()
    {
        return smsURI;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getTitle()
    {
        return title;
    }

    public String getVia()
    {
        return via;
    }

    private final String body;
    private final String number;
    private final String smsURI;
    private final String subject;
    private final String title;
    private final String via;
}
