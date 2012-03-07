// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.google.zxing.client.result:
//            BookmarkDoCoMoResultParser, AddressBookDoCoMoResultParser, EmailDoCoMoResultParser, AddressBookAUResultParser, 
//            VCardResultParser, BizcardResultParser, VEventResultParser, EmailAddressResultParser, 
//            TelResultParser, SMSMMSResultParser, GeoResultParser, URLTOResultParser, 
//            URIResultParser, ISBNResultParser, ProductResultParser, TextParsedResult, 
//            ParsedResult

public abstract class ResultParser
{

    public ResultParser()
    {
    }

    private static void appendKeyValue(String s, int i, int j, Hashtable hashtable)
    {
        int k = s.indexOf('=', i);
        if(k >= 0)
            hashtable.put(s.substring(i, k), urlDecode(s.substring(k + 1, j)));
    }

    private static int findFirstEscape(char ac[])
    {
        int i;
        int j;
        i = ac.length;
        j = 0;
_L3:
        char c;
        if(j >= i)
            break MISSING_BLOCK_LABEL_39;
        c = ac[j];
        if(c != '+' && c != '%') goto _L2; else goto _L1
_L1:
        int k = j;
_L4:
        return k;
_L2:
        j++;
          goto _L3
        k = -1;
          goto _L4
    }

    protected static boolean isStringOfDigits(String s, int i)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        return flag;
_L2:
        if(i != s.length())
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        int j = 0;
        do
        {
            if(j >= i)
                break;
            char c = s.charAt(j);
            if(c < '0' || c > '9')
            {
                flag = false;
                continue; /* Loop/switch isn't completed */
            }
            j++;
        } while(true);
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    static String[] matchPrefixedField(String s, String s1, char c, boolean flag)
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
                boolean flag1;
                int i1;
                String s2;
                if(vector == null || vector.isEmpty())
                    as = null;
                else
                    as = toStringArray(vector);
                return as;
            }
            i = k + s.length();
            l = i;
            flag1 = false;
            while(!flag1) 
            {
                i1 = s1.indexOf(c, i);
                if(i1 < 0)
                {
                    i = s1.length();
                    flag1 = true;
                } else
                if(s1.charAt(i1 - 1) == '\\')
                {
                    i = i1 + 1;
                } else
                {
                    if(vector == null)
                        vector = new Vector(3);
                    s2 = unescapeBackslash(s1.substring(l, i1));
                    if(flag)
                        s2 = s2.trim();
                    vector.addElement(s2);
                    i = i1 + 1;
                    flag1 = true;
                }
            }
        } while(true);
    }

    static String matchSinglePrefixedField(String s, String s1, char c, boolean flag)
    {
        String as[] = matchPrefixedField(s, s1, c, flag);
        String s2;
        if(as == null)
            s2 = null;
        else
            s2 = as[0];
        return s2;
    }

    protected static void maybeAppend(String s, StringBuffer stringbuffer)
    {
        if(s != null)
        {
            stringbuffer.append('\n');
            stringbuffer.append(s);
        }
    }

    protected static void maybeAppend(String as[], StringBuffer stringbuffer)
    {
        if(as != null)
        {
            for(int i = 0; i < as.length; i++)
            {
                stringbuffer.append('\n');
                stringbuffer.append(as[i]);
            }

        }
    }

    protected static String[] maybeWrap(String s)
    {
        String as[];
        if(s == null)
        {
            as = null;
        } else
        {
            as = new String[1];
            as[0] = s;
        }
        return as;
    }

    private static int parseHexDigit(char c)
    {
        if(c < 'a') goto _L2; else goto _L1
_L1:
        if(c > 'f') goto _L4; else goto _L3
_L3:
        int i = 10 + (c - 97);
_L6:
        return i;
_L2:
        if(c >= 'A')
        {
            if(c <= 'F')
            {
                i = 10 + (c - 65);
                continue; /* Loop/switch isn't completed */
            }
        } else
        if(c >= '0' && c <= '9')
        {
            i = c - 48;
            continue; /* Loop/switch isn't completed */
        }
_L4:
        i = -1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    static Hashtable parseNameValuePairs(String s)
    {
        int i = s.indexOf('?');
        Hashtable hashtable1;
        if(i < 0)
        {
            hashtable1 = null;
        } else
        {
            Hashtable hashtable = new Hashtable(3);
            int j = i + 1;
            do
            {
                int k = s.indexOf('&', j);
                if(k < 0)
                    break;
                appendKeyValue(s, j, k, hashtable);
                j = k + 1;
            } while(true);
            appendKeyValue(s, j, s.length(), hashtable);
            hashtable1 = hashtable;
        }
        return hashtable1;
    }

    public static ParsedResult parseResult(Result result)
    {
        URIParsedResult uriparsedresult = BookmarkDoCoMoResultParser.parse(result);
        Object obj;
        if(uriparsedresult != null)
        {
            obj = uriparsedresult;
        } else
        {
            AddressBookParsedResult addressbookparsedresult = AddressBookDoCoMoResultParser.parse(result);
            if(addressbookparsedresult != null)
            {
                obj = addressbookparsedresult;
            } else
            {
                EmailAddressParsedResult emailaddressparsedresult = EmailDoCoMoResultParser.parse(result);
                if(emailaddressparsedresult != null)
                {
                    obj = emailaddressparsedresult;
                } else
                {
                    AddressBookParsedResult addressbookparsedresult1 = AddressBookAUResultParser.parse(result);
                    if(addressbookparsedresult1 != null)
                    {
                        obj = addressbookparsedresult1;
                    } else
                    {
                        AddressBookParsedResult addressbookparsedresult2 = VCardResultParser.parse(result);
                        if(addressbookparsedresult2 != null)
                        {
                            obj = addressbookparsedresult2;
                        } else
                        {
                            AddressBookParsedResult addressbookparsedresult3 = BizcardResultParser.parse(result);
                            if(addressbookparsedresult3 != null)
                            {
                                obj = addressbookparsedresult3;
                            } else
                            {
                                CalendarParsedResult calendarparsedresult = VEventResultParser.parse(result);
                                if(calendarparsedresult != null)
                                {
                                    obj = calendarparsedresult;
                                } else
                                {
                                    EmailAddressParsedResult emailaddressparsedresult1 = EmailAddressResultParser.parse(result);
                                    if(emailaddressparsedresult1 != null)
                                    {
                                        obj = emailaddressparsedresult1;
                                    } else
                                    {
                                        TelParsedResult telparsedresult = TelResultParser.parse(result);
                                        if(telparsedresult != null)
                                        {
                                            obj = telparsedresult;
                                        } else
                                        {
                                            SMSParsedResult smsparsedresult = SMSMMSResultParser.parse(result);
                                            if(smsparsedresult != null)
                                            {
                                                obj = smsparsedresult;
                                            } else
                                            {
                                                GeoParsedResult geoparsedresult = GeoResultParser.parse(result);
                                                if(geoparsedresult != null)
                                                {
                                                    obj = geoparsedresult;
                                                } else
                                                {
                                                    URIParsedResult uriparsedresult1 = URLTOResultParser.parse(result);
                                                    if(uriparsedresult1 != null)
                                                    {
                                                        obj = uriparsedresult1;
                                                    } else
                                                    {
                                                        URIParsedResult uriparsedresult2 = URIResultParser.parse(result);
                                                        if(uriparsedresult2 != null)
                                                        {
                                                            obj = uriparsedresult2;
                                                        } else
                                                        {
                                                            ISBNParsedResult isbnparsedresult = ISBNResultParser.parse(result);
                                                            if(isbnparsedresult != null)
                                                            {
                                                                obj = isbnparsedresult;
                                                            } else
                                                            {
                                                                ProductParsedResult productparsedresult = ProductResultParser.parse(result);
                                                                if(productparsedresult != null)
                                                                    obj = productparsedresult;
                                                                else
                                                                    obj = new TextParsedResult(result.getText(), null);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ((ParsedResult) (obj));
    }

    static String[] toStringArray(Vector vector)
    {
        int i = vector.size();
        String as[] = new String[i];
        for(int j = 0; j < i; j++)
            as[j] = (String)vector.elementAt(j);

        return as;
    }

    protected static String unescapeBackslash(String s)
    {
        if(s == null) goto _L2; else goto _L1
_L1:
        int i = s.indexOf('\\');
        if(i < 0) goto _L2; else goto _L3
_L3:
        String s1;
        int j = s.length();
        StringBuffer stringbuffer = new StringBuffer(j - 1);
        stringbuffer.append(s.toCharArray(), 0, i);
        boolean flag = false;
        int k = i;
        while(k < j) 
        {
            char c = s.charAt(k);
            if(flag || c != '\\')
            {
                stringbuffer.append(c);
                flag = false;
            } else
            {
                flag = true;
            }
            k++;
        }
        s1 = stringbuffer.toString();
_L5:
        return s1;
_L2:
        s1 = s;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static String urlDecode(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            char ac[] = s.toCharArray();
            int i = findFirstEscape(ac);
            if(i < 0)
            {
                s1 = s;
            } else
            {
                int j = ac.length;
                StringBuffer stringbuffer = new StringBuffer(j - 2);
                stringbuffer.append(ac, 0, i);
                int k = i;
                while(k < j) 
                {
                    char c = ac[k];
                    if(c == '+')
                        stringbuffer.append(' ');
                    else
                    if(c == '%')
                    {
                        if(k >= j - 2)
                        {
                            stringbuffer.append('%');
                        } else
                        {
                            int l = k + 1;
                            int i1 = parseHexDigit(ac[l]);
                            k = l + 1;
                            int j1 = parseHexDigit(ac[k]);
                            if(i1 < 0 || j1 < 0)
                            {
                                stringbuffer.append('%');
                                stringbuffer.append(ac[k - 1]);
                                stringbuffer.append(ac[k]);
                            }
                            stringbuffer.append((char)(j1 + (i1 << 4)));
                        }
                    } else
                    {
                        stringbuffer.append(c);
                    }
                    k++;
                }
                s1 = stringbuffer.toString();
            }
        }
        return s1;
    }
}
