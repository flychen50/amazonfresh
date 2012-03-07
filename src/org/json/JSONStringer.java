// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.StringWriter;

// Referenced classes of package org.json:
//            JSONWriter

public class JSONStringer extends JSONWriter
{

    public JSONStringer()
    {
        super(new StringWriter());
    }

    public String toString()
    {
        String s;
        if(mode == 'd')
            s = writer.toString();
        else
            s = null;
        return s;
    }
}
