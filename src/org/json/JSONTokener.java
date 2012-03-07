// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.*;

// Referenced classes of package org.json:
//            JSONException, JSONObject, JSONArray

public class JSONTokener
{

    public JSONTokener(Reader reader1)
    {
        Object obj;
        if(reader1.markSupported())
            obj = reader1;
        else
            obj = new BufferedReader(reader1);
        reader = ((Reader) (obj));
        eof = false;
        usePrevious = false;
        previous = '\0';
        index = 0;
        character = 1;
        line = 1;
    }

    public JSONTokener(String s)
    {
        this(((Reader) (new StringReader(s))));
    }

    public static int dehexchar(char c)
    {
        int i;
        if(c >= '0' && c <= '9')
            i = c - 48;
        else
        if(c >= 'A' && c <= 'F')
            i = c - 55;
        else
        if(c >= 'a' && c <= 'f')
            i = c - 87;
        else
            i = -1;
        return i;
    }

    public void back()
        throws JSONException
    {
        if(usePrevious || index <= 0)
        {
            throw new JSONException("Stepping back two steps is not supported");
        } else
        {
            index = index - 1;
            character = character - 1;
            usePrevious = true;
            eof = false;
            return;
        }
    }

    public boolean end()
    {
        boolean flag;
        if(eof && !usePrevious)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean more()
        throws JSONException
    {
        next();
        boolean flag;
        if(end())
        {
            flag = false;
        } else
        {
            back();
            flag = true;
        }
        return flag;
    }

    public char next()
        throws JSONException
    {
        int j;
        if(usePrevious)
        {
            usePrevious = false;
            j = previous;
        } else
        {
            int i;
            try
            {
                i = reader.read();
            }
            catch(IOException ioexception)
            {
                throw new JSONException(ioexception);
            }
            j = i;
            if(j <= 0)
            {
                eof = true;
                j = 0;
            }
        }
        index = 1 + index;
        if(previous == '\r')
        {
            line = 1 + line;
            int k;
            if(j == 10)
                k = 0;
            else
                k = 1;
            character = k;
        } else
        if(j == 10)
        {
            line = 1 + line;
            character = 0;
        } else
        {
            character = 1 + character;
        }
        previous = j;
        return previous;
    }

    public char next(char c)
        throws JSONException
    {
        char c1 = next();
        if(c1 != c)
            throw syntaxError((new StringBuilder("Expected '")).append(c).append("' and instead saw '").append(c1).append("'").toString());
        else
            return c1;
    }

    public String next(int i)
        throws JSONException
    {
        if(i != 0) goto _L2; else goto _L1
_L1:
        String s = "";
_L4:
        return s;
_L2:
        char ac[] = new char[i];
        int j = 0;
        do
        {
label0:
            {
                if(j < i)
                    break label0;
                s = new String(ac);
            }
            if(true)
                continue;
            ac[j] = next();
            if(end())
                throw syntaxError("Substring bounds error");
            j++;
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public char nextClean()
        throws JSONException
    {
        char c;
        do
            c = next();
        while(c != 0 && c <= ' ');
        return c;
    }

    public String nextString(char c)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            char c1 = next();
            switch(c1)
            {
            default:
                if(c1 == c)
                    return stringbuffer.toString();
                break;

            case 0: // '\0'
            case 10: // '\n'
            case 13: // '\r'
                throw syntaxError("Unterminated string");

            case 92: // '\\'
                char c2 = next();
                switch(c2)
                {
                default:
                    throw syntaxError("Illegal escape.");

                case 98: // 'b'
                    stringbuffer.append('\b');
                    break;

                case 116: // 't'
                    stringbuffer.append('\t');
                    break;

                case 110: // 'n'
                    stringbuffer.append('\n');
                    break;

                case 102: // 'f'
                    stringbuffer.append('\f');
                    break;

                case 114: // 'r'
                    stringbuffer.append('\r');
                    break;

                case 117: // 'u'
                    stringbuffer.append((char)Integer.parseInt(next(4), 16));
                    break;

                case 34: // '"'
                case 39: // '\''
                case 47: // '/'
                case 92: // '\\'
                    stringbuffer.append(c2);
                    break;
                }
                continue;
            }
            stringbuffer.append(c1);
        } while(true);
    }

    public String nextTo(char c)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            char c1 = next();
            if(c1 == c || c1 == 0 || c1 == '\n' || c1 == '\r')
            {
                if(c1 != 0)
                    back();
                return stringbuffer.toString().trim();
            }
            stringbuffer.append(c1);
        } while(true);
    }

    public String nextTo(String s)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            char c = next();
            if(s.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r')
            {
                if(c != 0)
                    back();
                return stringbuffer.toString().trim();
            }
            stringbuffer.append(c);
        } while(true);
    }

    public Object nextValue()
        throws JSONException
    {
        char c = nextClean();
        c;
        String s;
        JVM INSTR lookupswitch 5: default 56
    //                   34: 109
    //                   39: 109
    //                   40: 133
    //                   91: 133
    //                   123: 117;
           goto _L1 _L2 _L2 _L3 _L3 _L4
_L2:
        Object obj = nextString(c);
_L6:
        return obj;
_L4:
        back();
        obj = new JSONObject(this);
        continue; /* Loop/switch isn't completed */
_L3:
        back();
        obj = new JSONArray(this);
        continue; /* Loop/switch isn't completed */
_L1:
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            if(c < ' ' || ",:]}/\\\"[{;=#".indexOf(c) >= 0)
            {
                back();
                s = stringbuffer.toString().trim();
                if(s.equals(""))
                    throw syntaxError("Missing value");
                break MISSING_BLOCK_LABEL_163;
            }
            stringbuffer.append(c);
            c = next();
        } while(true);
        obj = JSONObject.stringToValue(s);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public char skipTo(char c)
        throws JSONException
    {
        int i;
        int j;
        int k;
        i = index;
        j = character;
        k = line;
        reader.mark(0x7fffffff);
_L2:
        char c1;
        c1 = next();
        if(c1 != 0)
            continue; /* Loop/switch isn't completed */
        reader.reset();
        index = i;
        character = j;
        line = k;
_L3:
        return c1;
        if(c1 != c) goto _L2; else goto _L1
_L1:
        back();
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
        IOException ioexception;
        ioexception;
        throw new JSONException(ioexception);
    }

    public JSONException syntaxError(String s)
    {
        return new JSONException((new StringBuilder(String.valueOf(s))).append(toString()).toString());
    }

    public String toString()
    {
        return (new StringBuilder(" at ")).append(index).append(" [character ").append(character).append(" line ").append(line).append("]").toString();
    }

    private int character;
    private boolean eof;
    private int index;
    private int line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;
}
