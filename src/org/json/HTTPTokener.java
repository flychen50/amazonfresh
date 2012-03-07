// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;


// Referenced classes of package org.json:
//            JSONTokener, JSONException

public class HTTPTokener extends JSONTokener
{

    public HTTPTokener(String s)
    {
        super(s);
    }

    public String nextToken()
        throws JSONException
    {
        StringBuffer stringbuffer;
        char c;
        char c1;
        stringbuffer = new StringBuffer();
        do
            c = next();
        while(Character.isWhitespace(c));
        if(c != '"' && c != '\'')
            break MISSING_BLOCK_LABEL_79;
        c1 = c;
_L3:
        char c2;
        c2 = next();
        if(c2 < ' ')
            throw syntaxError("Unterminated string.");
        if(c2 != c1) goto _L2; else goto _L1
_L1:
        String s = stringbuffer.toString();
_L4:
        return s;
_L2:
        stringbuffer.append(c2);
          goto _L3
        for(; c != 0 && !Character.isWhitespace(c); c = next())
            stringbuffer.append(c);

        s = stringbuffer.toString();
          goto _L4
    }
}
