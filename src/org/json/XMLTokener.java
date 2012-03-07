// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.util.HashMap;

// Referenced classes of package org.json:
//            JSONTokener, XML, JSONException

public class XMLTokener extends JSONTokener
{

    public XMLTokener(String s)
    {
        super(s);
    }

    public String nextCDATA()
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i;
        do
        {
            char c = next();
            if(end())
                throw syntaxError("Unclosed CDATA");
            stringbuffer.append(c);
            i = stringbuffer.length() - 3;
        } while(i < 0 || stringbuffer.charAt(i) != ']' || stringbuffer.charAt(i + 1) != ']' || stringbuffer.charAt(i + 2) != '>');
        stringbuffer.setLength(i);
        return stringbuffer.toString();
    }

    public Object nextContent()
        throws JSONException
    {
        char c;
        do
            c = next();
        while(Character.isWhitespace(c));
        if(c != 0) goto _L2; else goto _L1
_L1:
        Object obj = null;
_L4:
        return obj;
_L2:
        StringBuffer stringbuffer;
        if(c == '<')
        {
            obj = XML.LT;
            continue; /* Loop/switch isn't completed */
        }
        stringbuffer = new StringBuffer();
_L5:
label0:
        {
            if(c != '<' && c != 0)
                break label0;
            back();
            obj = stringbuffer.toString().trim();
        }
        if(true) goto _L4; else goto _L3
_L3:
        if(c == '&')
            stringbuffer.append(nextEntity(c));
        else
            stringbuffer.append(c);
        c = next();
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
    }

    public Object nextEntity(char c)
        throws JSONException
    {
        StringBuffer stringbuffer = new StringBuffer();
        char c1;
        do
        {
            c1 = next();
            if(!Character.isLetterOrDigit(c1) && c1 != '#')
                break;
            stringbuffer.append(Character.toLowerCase(c1));
        } while(true);
        if(c1 == ';')
        {
            String s = stringbuffer.toString();
            Object obj = entity.get(s);
            Object obj1;
            if(obj != null)
                obj1 = obj;
            else
                obj1 = (new StringBuilder(String.valueOf(c))).append(s).append(";").toString();
            return obj1;
        } else
        {
            throw syntaxError((new StringBuilder("Missing ';' in XML entity: &")).append(stringbuffer).toString());
        }
    }

    public Object nextMeta()
        throws JSONException
    {
        char c;
        do
            c = next();
        while(Character.isWhitespace(c));
        c;
        JVM INSTR lookupswitch 9: default 96
    //                   0: 116
    //                   33: 151
    //                   34: 165
    //                   39: 165
    //                   47: 137
    //                   60: 123
    //                   61: 144
    //                   62: 130
    //                   63: 158;
           goto _L1 _L2 _L3 _L4 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
        char c2 = next();
        if(!Character.isWhitespace(c2)) goto _L11; else goto _L10
_L10:
        Object obj = Boolean.TRUE;
_L12:
        return obj;
_L2:
        throw syntaxError("Misshaped meta tag");
_L6:
        obj = XML.LT;
        continue; /* Loop/switch isn't completed */
_L8:
        obj = XML.GT;
        continue; /* Loop/switch isn't completed */
_L5:
        obj = XML.SLASH;
        continue; /* Loop/switch isn't completed */
_L7:
        obj = XML.EQ;
        continue; /* Loop/switch isn't completed */
_L3:
        obj = XML.BANG;
        continue; /* Loop/switch isn't completed */
_L9:
        obj = XML.QUEST;
        continue; /* Loop/switch isn't completed */
_L4:
        char c1;
        do
        {
            c1 = next();
            if(c1 == 0)
                throw syntaxError("Unterminated string");
        } while(c1 != c);
        obj = Boolean.TRUE;
        if(true) goto _L12; else goto _L11
_L11:
        switch(c2)
        {
        default:
            break;

        case 0: // '\0'
        case 33: // '!'
        case 34: // '"'
        case 39: // '\''
        case 47: // '/'
        case 60: // '<'
        case 61: // '='
        case 62: // '>'
        case 63: // '?'
            back();
            obj = Boolean.TRUE;
            break;
        }
        if(true) goto _L12; else goto _L13
_L13:
        if(true) goto _L1; else goto _L14
_L14:
    }

    public Object nextToken()
        throws JSONException
    {
        char c;
        do
            c = next();
        while(Character.isWhitespace(c));
        c;
        JVM INSTR lookupswitch 9: default 96
    //                   0: 134
    //                   33: 172
    //                   34: 188
    //                   39: 188
    //                   47: 156
    //                   60: 141
    //                   61: 164
    //                   62: 148
    //                   63: 180;
           goto _L1 _L2 _L3 _L4 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
        StringBuffer stringbuffer1 = new StringBuffer();
_L14:
        stringbuffer1.append(c);
        c = next();
        if(!Character.isWhitespace(c)) goto _L11; else goto _L10
_L10:
        Object obj = stringbuffer1.toString();
_L12:
        return obj;
_L2:
        throw syntaxError("Misshaped element");
_L6:
        throw syntaxError("Misplaced '<'");
_L8:
        obj = XML.GT;
          goto _L12
_L5:
        obj = XML.SLASH;
          goto _L12
_L7:
        obj = XML.EQ;
          goto _L12
_L3:
        obj = XML.BANG;
          goto _L12
_L9:
        obj = XML.QUEST;
          goto _L12
_L4:
        char c1;
        StringBuffer stringbuffer;
        c1 = c;
        stringbuffer = new StringBuffer();
_L13:
        char c2;
label0:
        {
            c2 = next();
            if(c2 == 0)
                throw syntaxError("Unterminated string");
            if(c2 != c1)
                break label0;
            obj = stringbuffer.toString();
        }
          goto _L12
        if(c2 == '&')
            stringbuffer.append(nextEntity(c2));
        else
            stringbuffer.append(c2);
          goto _L13
_L11:
        c;
        JVM INSTR lookupswitch 11: default 360
    //                   0: 363
    //                   33: 373
    //                   34: 387
    //                   39: 387
    //                   47: 373
    //                   60: 387
    //                   61: 373
    //                   62: 373
    //                   63: 373
    //                   91: 373
    //                   93: 373;
           goto _L14 _L15 _L16 _L17 _L17 _L16 _L17 _L16 _L16 _L16 _L16 _L16
_L15:
        obj = stringbuffer1.toString();
          goto _L12
_L16:
        back();
        obj = stringbuffer1.toString();
          goto _L12
_L17:
        throw syntaxError("Bad character in a name");
    }

    public boolean skipPast(String s)
        throws JSONException
    {
        int i;
        int j;
        char ac[];
        int k;
        i = 0;
        j = s.length();
        ac = new char[j];
        k = 0;
_L5:
        if(k < j) goto _L2; else goto _L1
_L1:
        int l;
        boolean flag1;
        int i1;
        l = i;
        flag1 = true;
        i1 = 0;
_L6:
        char c1;
        char c;
        boolean flag;
        if(i1 < j)
        {
label0:
            {
                if(ac[l] == s.charAt(i1))
                    break label0;
                flag1 = false;
            }
        }
        if(flag1)
        {
            flag = true;
        } else
        {
label1:
            {
                c1 = next();
                if(c1 != 0)
                    break label1;
                flag = false;
            }
        }
_L4:
        return flag;
_L2:
        c = next();
        if(c != 0)
            break; /* Loop/switch isn't completed */
        flag = false;
        if(true) goto _L4; else goto _L3
_L3:
        ac[k] = c;
        k++;
          goto _L5
        if(++l >= j)
            l -= j;
        i1++;
          goto _L6
        ac[i] = c1;
        if(++i >= j)
            i -= j;
          goto _L1
    }

    public static final HashMap entity;

    static 
    {
        entity = new HashMap(8);
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }
}
