// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Vector;

// Referenced classes of package com.google.zxing.client.result:
//            ResultParser, AddressBookParsedResult

final class AddressBookAUResultParser extends ResultParser
{

    AddressBookAUResultParser()
    {
    }

    private static String[] matchMultipleValuePrefix(String s, int i, String s1, boolean flag)
    {
        Vector vector = null;
        int j = 1;
        do
        {
label0:
            {
                String s2;
                if(j <= i)
                {
                    s2 = matchSinglePrefixedField(s + j + ':', s1, '\r', flag);
                    if(s2 != null)
                        break label0;
                }
                String as[];
                if(vector == null)
                    as = null;
                else
                    as = toStringArray(vector);
                return as;
            }
            if(vector == null)
                vector = new Vector(i);
            vector.addElement(s2);
            j++;
        } while(true);
    }

    public static AddressBookParsedResult parse(Result result)
    {
        String s = result.getText();
        AddressBookParsedResult addressbookparsedresult;
        if(s == null || s.indexOf("MEMORY") < 0 || s.indexOf("\r\n") < 0)
        {
            addressbookparsedresult = null;
        } else
        {
            String s1 = matchSinglePrefixedField("NAME1:", s, '\r', true);
            String s2 = matchSinglePrefixedField("NAME2:", s, '\r', true);
            String as[] = matchMultipleValuePrefix("TEL", 3, s, true);
            String as1[] = matchMultipleValuePrefix("MAIL", 3, s, true);
            String s3 = matchSinglePrefixedField("MEMORY:", s, '\r', false);
            String s4 = matchSinglePrefixedField("ADD:", s, '\r', true);
            addressbookparsedresult = new AddressBookParsedResult(maybeWrap(s1), s2, as, as1, s3, s4, null, null, null, null);
        }
        return addressbookparsedresult;
    }
}
