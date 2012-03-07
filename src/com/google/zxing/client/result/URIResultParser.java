// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;

// Referenced classes of package com.google.zxing.client.result:
//            ResultParser, URIParsedResult

final class URIResultParser extends ResultParser
{

    private URIResultParser()
    {
    }

    static boolean isBasicallyValidURI(String s)
    {
        boolean flag;
        if(s == null || s.indexOf(' ') >= 0 || s.indexOf('\n') >= 0)
        {
            flag = false;
        } else
        {
            int i = s.indexOf('.');
            if(i < s.length() - 2 && (i >= 0 || s.indexOf(':') >= 0))
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public static URIParsedResult parse(Result result)
    {
        String s = result.getText();
        if(s != null && s.startsWith("URL:"))
            s = s.substring(4);
        URIParsedResult uriparsedresult;
        if(!isBasicallyValidURI(s))
            uriparsedresult = null;
        else
            uriparsedresult = new URIParsedResult(s, null);
        return uriparsedresult;
    }
}
