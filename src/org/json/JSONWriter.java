// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.IOException;
import java.io.Writer;

// Referenced classes of package org.json:
//            JSONObject, JSONException

public class JSONWriter
{

    public JSONWriter(Writer writer1)
    {
        comma = false;
        mode = 'i';
        stack = new JSONObject[20];
        top = 0;
        writer = writer1;
    }

    private JSONWriter append(String s)
        throws JSONException
    {
        if(s == null)
            throw new JSONException("Null pointer");
        if(mode == 'o' || mode == 'a')
        {
            try
            {
                if(comma && mode == 'a')
                    writer.write(44);
                writer.write(s);
            }
            catch(IOException ioexception)
            {
                throw new JSONException(ioexception);
            }
            if(mode == 'o')
                mode = 'k';
            comma = true;
            return this;
        } else
        {
            throw new JSONException("Value out of sequence.");
        }
    }

    private JSONWriter end(char c, char c1)
        throws JSONException
    {
        if(mode != c)
        {
            String s;
            if(c == 'a')
                s = "Misplaced endArray.";
            else
                s = "Misplaced endObject.";
            throw new JSONException(s);
        }
        pop(c);
        try
        {
            writer.write(c1);
        }
        catch(IOException ioexception)
        {
            throw new JSONException(ioexception);
        }
        comma = true;
        return this;
    }

    private void pop(char c)
        throws JSONException
    {
        if(top <= 0)
            throw new JSONException("Nesting error.");
        byte byte0;
        if(stack[top - 1] == null)
            byte0 = 97;
        else
            byte0 = 107;
        if(byte0 != c)
            throw new JSONException("Nesting error.");
        top = top - 1;
        char c1;
        if(top == 0)
            c1 = 'd';
        else
        if(stack[top - 1] == null)
            c1 = 'a';
        else
            c1 = 'k';
        mode = c1;
    }

    private void push(JSONObject jsonobject)
        throws JSONException
    {
        if(top >= 20)
            throw new JSONException("Nesting too deep.");
        stack[top] = jsonobject;
        char c;
        if(jsonobject == null)
            c = 'a';
        else
            c = 'k';
        mode = c;
        top = 1 + top;
    }

    public JSONWriter array()
        throws JSONException
    {
        if(mode == 'i' || mode == 'o' || mode == 'a')
        {
            push(null);
            append("[");
            comma = false;
            return this;
        } else
        {
            throw new JSONException("Misplaced array.");
        }
    }

    public JSONWriter endArray()
        throws JSONException
    {
        return end('a', ']');
    }

    public JSONWriter endObject()
        throws JSONException
    {
        return end('k', '}');
    }

    public JSONWriter key(String s)
        throws JSONException
    {
        if(s == null)
            throw new JSONException("Null key.");
        if(mode == 'k')
        {
            try
            {
                stack[top - 1].putOnce(s, Boolean.TRUE);
                if(comma)
                    writer.write(44);
                writer.write(JSONObject.quote(s));
                writer.write(58);
                comma = false;
                mode = 'o';
            }
            catch(IOException ioexception)
            {
                throw new JSONException(ioexception);
            }
            return this;
        } else
        {
            throw new JSONException("Misplaced key.");
        }
    }

    public JSONWriter object()
        throws JSONException
    {
        if(mode == 'i')
            mode = 'o';
        if(mode == 'o' || mode == 'a')
        {
            append("{");
            push(new JSONObject());
            comma = false;
            return this;
        } else
        {
            throw new JSONException("Misplaced object.");
        }
    }

    public JSONWriter value(double d)
        throws JSONException
    {
        return value(new Double(d));
    }

    public JSONWriter value(long l)
        throws JSONException
    {
        return append(Long.toString(l));
    }

    public JSONWriter value(Object obj)
        throws JSONException
    {
        return append(JSONObject.valueToString(obj));
    }

    public JSONWriter value(boolean flag)
        throws JSONException
    {
        String s;
        if(flag)
            s = "true";
        else
            s = "false";
        return append(s);
    }

    private static final int maxdepth = 20;
    private boolean comma;
    protected char mode;
    private JSONObject stack[];
    private int top;
    protected Writer writer;
}
