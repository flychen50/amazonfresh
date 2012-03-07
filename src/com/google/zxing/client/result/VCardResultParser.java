// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Vector;

// Referenced classes of package com.google.zxing.client.result:
//            ResultParser, AddressBookParsedResult

final class VCardResultParser extends ResultParser
{

    private VCardResultParser()
    {
    }

    private static String formatAddress(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.length();
            StringBuffer stringbuffer = new StringBuffer(i);
            int j = 0;
            while(j < i) 
            {
                char c = s.charAt(j);
                if(c == ';')
                    stringbuffer.append(' ');
                else
                    stringbuffer.append(c);
                j++;
            }
            s1 = stringbuffer.toString().trim();
        }
        return s1;
    }

    private static void formatNames(String as[])
    {
        if(as != null)
        {
            for(int i = 0; i < as.length; i++)
            {
                String s = as[i];
                String as1[] = new String[5];
                int j = 0;
                int k = 0;
                do
                {
                    int l = s.indexOf(';', j);
                    if(l <= 0)
                        break;
                    as1[k] = s.substring(j, l);
                    k++;
                    j = l + 1;
                } while(true);
                as1[k] = s.substring(j);
                StringBuffer stringbuffer = new StringBuffer();
                maybeAppendComponent(as1, 3, stringbuffer);
                maybeAppendComponent(as1, 1, stringbuffer);
                maybeAppendComponent(as1, 2, stringbuffer);
                maybeAppendComponent(as1, 0, stringbuffer);
                maybeAppendComponent(as1, 4, stringbuffer);
                as[i] = stringbuffer.toString().trim();
            }

        }
    }

    static String matchSingleVCardPrefixedField(String s, String s1, boolean flag)
    {
        String as[] = matchVCardPrefixedField(s, s1, flag);
        String s2;
        if(as == null)
            s2 = null;
        else
            s2 = as[0];
        return s2;
    }

    private static String[] matchVCardPrefixedField(String s, String s1, boolean flag)
    {
        Vector vector = null;
        int i = 0;
        int j = s1.length();
        do
        {
label0:
            {
                int k;
                if(i < j)
                {
                    k = s1.indexOf(s, i);
                    if(k >= 0)
                        break label0;
                }
                String as[];
                int l;
                int i1;
                String s2;
                if(vector == null || vector.isEmpty())
                    as = null;
                else
                    as = toStringArray(vector);
                return as;
            }
            if(k > 0 && s1.charAt(k - 1) != '\n')
            {
                i = k + 1;
            } else
            {
                i = k + s.length();
                if(s1.charAt(i) == ':' || s1.charAt(i) == ';')
                {
                    for(; s1.charAt(i) != ':'; i++);
                    l = i + 1;
                    i1 = s1.indexOf('\n', l);
                    if(i1 < 0)
                        i = j;
                    else
                    if(i1 > l)
                    {
                        if(vector == null)
                            vector = new Vector(3);
                        s2 = s1.substring(l, i1);
                        if(flag)
                            s2 = s2.trim();
                        vector.addElement(s2);
                        i = i1 + 1;
                    } else
                    {
                        i = i1 + 1;
                    }
                }
            }
        } while(true);
    }

    private static void maybeAppendComponent(String as[], int i, StringBuffer stringbuffer)
    {
        if(as[i] != null)
        {
            stringbuffer.append(' ');
            stringbuffer.append(as[i]);
        }
    }

    public static AddressBookParsedResult parse(Result result)
    {
        String s = result.getText();
        AddressBookParsedResult addressbookparsedresult;
        if(s == null || !s.startsWith("BEGIN:VCARD"))
        {
            addressbookparsedresult = null;
        } else
        {
            String as[] = matchVCardPrefixedField("FN", s, true);
            if(as == null)
            {
                as = matchVCardPrefixedField("N", s, true);
                formatNames(as);
            }
            String as1[] = matchVCardPrefixedField("TEL", s, true);
            String as2[] = matchVCardPrefixedField("EMAIL", s, true);
            String s1 = matchSingleVCardPrefixedField("NOTE", s, false);
            String s2 = formatAddress(matchSingleVCardPrefixedField("ADR", s, true));
            String s3 = matchSingleVCardPrefixedField("ORG", s, true);
            String s4 = matchSingleVCardPrefixedField("BDAY", s, true);
            if(s4 != null && !isStringOfDigits(s4, 8))
                addressbookparsedresult = null;
            else
                addressbookparsedresult = new AddressBookParsedResult(as, null, as1, as2, s1, s2, s3, s4, matchSingleVCardPrefixedField("TITLE", s, true), matchSingleVCardPrefixedField("URL", s, true));
        }
        return addressbookparsedresult;
    }
}
