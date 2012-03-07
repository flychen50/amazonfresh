// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;


// Referenced classes of package com.google.zxing.client.result:
//            ParsedResult, ParsedResultType

public final class AddressBookParsedResult extends ParsedResult
{

    public AddressBookParsedResult(String as[], String s, String as1[], String as2[], String s1, String s2, String s3, 
            String s4, String s5, String s6)
    {
        super(ParsedResultType.ADDRESSBOOK);
        names = as;
        pronunciation = s;
        phoneNumbers = as1;
        emails = as2;
        note = s1;
        address = s2;
        org = s3;
        birthday = s4;
        title = s5;
        url = s6;
    }

    public String getAddress()
    {
        return address;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getDisplayResult()
    {
        StringBuffer stringbuffer = new StringBuffer();
        maybeAppend(names, stringbuffer);
        maybeAppend(pronunciation, stringbuffer);
        maybeAppend(title, stringbuffer);
        maybeAppend(org, stringbuffer);
        maybeAppend(address, stringbuffer);
        maybeAppend(phoneNumbers, stringbuffer);
        maybeAppend(emails, stringbuffer);
        maybeAppend(url, stringbuffer);
        maybeAppend(birthday, stringbuffer);
        maybeAppend(note, stringbuffer);
        return stringbuffer.toString();
    }

    public String[] getEmails()
    {
        return emails;
    }

    public String[] getNames()
    {
        return names;
    }

    public String getNote()
    {
        return note;
    }

    public String getOrg()
    {
        return org;
    }

    public String[] getPhoneNumbers()
    {
        return phoneNumbers;
    }

    public String getPronunciation()
    {
        return pronunciation;
    }

    public String getTitle()
    {
        return title;
    }

    public String getURL()
    {
        return url;
    }

    private final String address;
    private final String birthday;
    private final String emails[];
    private final String names[];
    private final String note;
    private final String org;
    private final String phoneNumbers[];
    private final String pronunciation;
    private final String title;
    private final String url;
}
