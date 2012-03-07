// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Hashtable;

// Referenced classes of package com.google.zxing.client.result:
//            ResultParser, SMSParsedResult

final class SMSMMSResultParser extends ResultParser
{

    private SMSMMSResultParser()
    {
    }

    public static SMSParsedResult parse(Result result)
    {
        String s = result.getText();
        if(s != null) goto _L2; else goto _L1
_L1:
        SMSParsedResult smsparsedresult = null;
_L4:
        return smsparsedresult;
_L2:
        byte byte0;
        Hashtable hashtable;
        String s1;
        String s2;
        boolean flag;
        int i;
        String s3;
        int j;
        String s4;
        String s6;
        if(s.startsWith("sms:") || s.startsWith("SMS:") || s.startsWith("mms:") || s.startsWith("MMS:"))
        {
            byte0 = 4;
        } else
        {
label0:
            {
                if(!s.startsWith("smsto:") && !s.startsWith("SMSTO:") && !s.startsWith("mmsto:") && !s.startsWith("MMSTO:"))
                    break label0;
                byte0 = 6;
            }
        }
        hashtable = parseNameValuePairs(s);
        s1 = null;
        s2 = null;
        flag = false;
        if(hashtable != null && !hashtable.isEmpty())
        {
            s1 = (String)hashtable.get("subject");
            s2 = (String)hashtable.get("body");
            flag = true;
        }
        i = s.indexOf('?', byte0);
        if(i < 0 || !flag)
            s3 = s.substring(byte0);
        else
            s3 = s.substring(byte0, i);
        j = s3.indexOf(';');
        if(j < 0)
        {
            s4 = s3;
            s6 = null;
        } else
        {
            s4 = s3.substring(0, j);
            String s5 = s3.substring(j + 1);
            if(s5.startsWith("via="))
                s6 = s5.substring(4);
            else
                s6 = null;
        }
        if(s2 == null)
        {
            int k = s4.indexOf(':');
            if(k >= 0)
            {
                s2 = s4.substring(k + 1);
                s4 = s4.substring(0, k);
            }
        }
        smsparsedresult = new SMSParsedResult("sms:" + s4, s4, s6, s1, s2, null);
        continue; /* Loop/switch isn't completed */
        smsparsedresult = null;
        if(true) goto _L4; else goto _L3
_L3:
    }
}
