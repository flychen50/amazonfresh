// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.json;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;

// Referenced classes of package org.json:
//            JSONArray, XML, JSONObject, JSONML, 
//            JSONStringer, JSONWriter, HTTP, CookieList, 
//            Cookie, JSONTokener, CDL

public class Test
{

    public Test()
    {
    }

    public static void main(String args[])
    {
        _cls1Obj _lcls1obj = new _cls1Obj("A beany object", 42D, true);
        JSONObject jsonobject33;
        Iterator iterator;
        JSONArray jsonarray = new JSONArray("[0.1]");
        System.out.println(jsonarray.toString());
        System.out.println("");
        JSONObject jsonobject = XML.toJSONObject("<![CDATA[This is a collection of test patterns and examples for org.json.]]>  Ignore the stuff past the end.  ");
        System.out.println(jsonobject.toString());
        System.out.println("");
        JSONObject jsonobject1 = new JSONObject();
        jsonobject1.put("booga", null);
        jsonobject1.put("wooga", JSONObject.NULL);
        System.out.println(jsonobject1.toString());
        System.out.println("");
        JSONObject jsonobject2 = new JSONObject();
        jsonobject2.increment("two");
        jsonobject2.increment("two");
        System.out.println(jsonobject2.toString());
        System.out.println("");
        JSONObject jsonobject3 = XML.toJSONObject("<test><blank></blank><empty/></test>");
        System.out.println(jsonobject3.toString(2));
        System.out.println(XML.toString(jsonobject3));
        System.out.println("");
        JSONObject jsonobject4 = new JSONObject("{     \"list of lists\" : [         [1, 2, 3],         [4, 5, 6],     ] }");
        System.out.println(jsonobject4.toString(4));
        System.out.println(XML.toString(jsonobject4));
        JSONObject jsonobject5 = XML.toJSONObject("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ");
        System.out.println(jsonobject5.toString(4));
        System.out.println();
        JSONObject jsonobject6 = JSONML.toJSONObject("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ");
        System.out.println(jsonobject6.toString());
        System.out.println(JSONML.toString(jsonobject6));
        System.out.println();
        JSONArray jsonarray1 = JSONML.toJSONArray("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ");
        System.out.println(jsonarray1.toString(4));
        System.out.println(JSONML.toString(jsonarray1));
        System.out.println();
        JSONObject jsonobject7 = JSONML.toJSONObject("<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>");
        System.out.println(jsonobject7.toString(4));
        System.out.println(JSONML.toString(jsonobject7));
        System.out.println();
        JSONArray jsonarray2 = JSONML.toJSONArray("<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>");
        System.out.println(jsonarray2.toString(4));
        System.out.println(JSONML.toString(jsonarray2));
        System.out.println();
        JSONObject jsonobject8 = XML.toJSONObject("<person created=\"2006-11-11T19:23\" modified=\"2006-12-31T23:59\">\n <firstName>Robert</firstName>\n <lastName>Smith</lastName>\n <address type=\"home\">\n <street>12345 Sixth Ave</street>\n <city>Anytown</city>\n <state>CA</state>\n <postalCode>98765-4321</postalCode>\n </address>\n </person>");
        System.out.println(jsonobject8.toString(4));
        JSONObject jsonobject9 = new JSONObject(_lcls1obj);
        System.out.println(jsonobject9.toString());
        JSONObject jsonobject10 = new JSONObject("{ \"entity\": { \"imageURL\": \"\", \"name\": \"IXXXXXXXXXXXXX\", \"id\": 12336, \"ratingCount\": null, \"averageRating\": null } }");
        System.out.println(jsonobject10.toString(2));
        String s = (new JSONStringer()).object().key("single").value("MARIE HAA'S").key("Johnny").value("MARIE HAA\\'S").key("foo").value("bar").key("baz").array().object().key("quux").value("Thanks, Josh!").endObject().endArray().key("obj keys").value(JSONObject.getNames(_lcls1obj)).endObject().toString();
        System.out.println(s);
        System.out.println((new JSONStringer()).object().key("a").array().array().array().value("b").endArray().endArray().endArray().endObject().toString());
        JSONStringer jsonstringer = new JSONStringer();
        jsonstringer.array();
        jsonstringer.value(1L);
        jsonstringer.array();
        jsonstringer.value(null);
        jsonstringer.array();
        jsonstringer.object();
        jsonstringer.key("empty-array").array().endArray();
        jsonstringer.key("answer").value(42L);
        jsonstringer.key("null").value(null);
        jsonstringer.key("false").value(false);
        jsonstringer.key("true").value(true);
        jsonstringer.key("big").value(1.2345678900000001E+096D);
        jsonstringer.key("small").value(1.2345678899999999E-080D);
        jsonstringer.key("empty-object").object().endObject();
        jsonstringer.key("long");
        jsonstringer.value(0x7fffffffffffffffL);
        jsonstringer.endObject();
        jsonstringer.value("two");
        jsonstringer.endArray();
        jsonstringer.value(true);
        jsonstringer.endArray();
        jsonstringer.value(98.599999999999994D);
        jsonstringer.value(-100D);
        jsonstringer.object();
        jsonstringer.endObject();
        jsonstringer.object();
        jsonstringer.key("one");
        jsonstringer.value(1.0D);
        jsonstringer.endObject();
        jsonstringer.value(_lcls1obj);
        jsonstringer.endArray();
        System.out.println(jsonstringer.toString());
        System.out.println((new JSONArray(jsonstringer.toString())).toString(4));
        int ai[] = new int[3];
        ai[0] = 1;
        ai[1] = 2;
        ai[2] = 3;
        JSONArray jsonarray3 = new JSONArray(ai);
        System.out.println(jsonarray3.toString());
        String args1[] = new String[3];
        args1[0] = "aString";
        args1[1] = "aNumber";
        args1[2] = "aBoolean";
        JSONObject jsonobject11 = new JSONObject(_lcls1obj, args1);
        jsonobject11.put("Testing JSONString interface", _lcls1obj);
        System.out.println(jsonobject11.toString(4));
        JSONObject jsonobject12 = new JSONObject("{slashes: '///', closetag: '</script>', backslash:'\\\\', ei: {quotes: '\"\\''},eo: {a: '\"quoted\"', b:\"don't\"}, quotes: [\"'\", '\"']}");
        System.out.println(jsonobject12.toString(2));
        System.out.println(XML.toString(jsonobject12));
        System.out.println("");
        JSONObject jsonobject13 = new JSONObject("{foo: [true, false,9876543210,    0.0, 1.00000001,  1.000000000001, 1.00000000000000001, .00000000000000001, 2.00, 0.1, 2e100, -32,[],{}, \"string\"],   to   : null, op : 'Good',ten:10} postfix comment");
        jsonobject13.put("String", "98.6");
        jsonobject13.put("JSONObject", new JSONObject());
        jsonobject13.put("JSONArray", new JSONArray());
        jsonobject13.put("int", 57);
        jsonobject13.put("double", 1.2345678901234568E+029D);
        jsonobject13.put("true", true);
        jsonobject13.put("false", false);
        jsonobject13.put("null", JSONObject.NULL);
        jsonobject13.put("bool", "true");
        jsonobject13.put("zero", 0.0D);
        jsonobject13.put("\\u2028", "\u2028");
        jsonobject13.put("\\u2029", "\u2029");
        JSONArray jsonarray4 = jsonobject13.getJSONArray("foo");
        jsonarray4.put(666);
        jsonarray4.put(2001.99D);
        jsonarray4.put("so \"fine\".");
        jsonarray4.put("so <fine>.");
        jsonarray4.put(true);
        jsonarray4.put(false);
        jsonarray4.put(new JSONArray());
        jsonarray4.put(new JSONObject());
        jsonobject13.put("keys", JSONObject.getNames(jsonobject13));
        System.out.println(jsonobject13.toString(4));
        System.out.println(XML.toString(jsonobject13));
        System.out.println((new StringBuilder("String: ")).append(jsonobject13.getDouble("String")).toString());
        System.out.println((new StringBuilder("  bool: ")).append(jsonobject13.getBoolean("bool")).toString());
        System.out.println((new StringBuilder("    to: ")).append(jsonobject13.getString("to")).toString());
        System.out.println((new StringBuilder("  true: ")).append(jsonobject13.getString("true")).toString());
        System.out.println((new StringBuilder("   foo: ")).append(jsonobject13.getJSONArray("foo")).toString());
        System.out.println((new StringBuilder("    op: ")).append(jsonobject13.getString("op")).toString());
        System.out.println((new StringBuilder("   ten: ")).append(jsonobject13.getInt("ten")).toString());
        System.out.println((new StringBuilder("  oops: ")).append(jsonobject13.optBoolean("oops")).toString());
        JSONObject jsonobject14 = XML.toJSONObject("<xml one = 1 two=' \"2\" '><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>");
        System.out.println(jsonobject14.toString(2));
        System.out.println(XML.toString(jsonobject14));
        System.out.println("");
        JSONArray jsonarray5 = JSONML.toJSONArray("<xml one = 1 two=' \"2\" '><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>");
        System.out.println(jsonarray5.toString(4));
        System.out.println(JSONML.toString(jsonarray5));
        System.out.println("");
        JSONArray jsonarray6 = JSONML.toJSONArray("<xml do='0'>uno<a re='1' mi='2'>dos<b fa='3'/>tres<c>true</c>quatro</a>cinqo<d>seis<e/></d></xml>");
        System.out.println(jsonarray6.toString(4));
        System.out.println(JSONML.toString(jsonarray6));
        System.out.println("");
        JSONObject jsonobject15 = XML.toJSONObject("<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>");
        System.out.println(jsonobject15.toString(2));
        System.out.println(XML.toString(jsonobject15));
        System.out.println("");
        JSONArray jsonarray7 = JSONML.toJSONArray("<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>");
        System.out.println(jsonarray7.toString(4));
        System.out.println(JSONML.toString(jsonarray7));
        System.out.println("");
        JSONObject jsonobject16 = XML.toJSONObject("<?xml version=\"1.0\" ?><Book Author=\"Anonymous\"><Title>Sample Book</Title><Chapter id=\"1\">This is chapter 1. It is not very long or interesting.</Chapter><Chapter id=\"2\">This is chapter 2. Although it is longer than chapter 1, it is not any more interesting.</Chapter></Book>");
        System.out.println(jsonobject16.toString(2));
        System.out.println(XML.toString(jsonobject16));
        System.out.println("");
        JSONObject jsonobject17 = XML.toJSONObject("<!DOCTYPE bCard 'http://www.cs.caltech.edu/~adam/schemas/bCard'><bCard><?xml default bCard        firstname = ''        lastname  = '' company   = '' email = '' homepage  = ''?><bCard        firstname = 'Rohit'        lastname  = 'Khare'        company   = 'MCI'        email     = 'khare@mci.net'        homepage  = 'http://pest.w3.org/'/><bCard        firstname = 'Adam'        lastname  = 'Rifkin'        company   = 'Caltech Infospheres Project'        email     = 'adam@cs.caltech.edu'        homepage  = 'http://www.cs.caltech.edu/~adam/'/></bCard>");
        System.out.println(jsonobject17.toString(2));
        System.out.println(XML.toString(jsonobject17));
        System.out.println("");
        JSONObject jsonobject18 = XML.toJSONObject("<?xml version=\"1.0\"?><customer>    <firstName>        <text>Fred</text>    </firstName>    <ID>fbs0001</ID>    <lastName> <text>Scerbo</text>    </lastName>    <MI>        <text>B</text>    </MI></customer>");
        System.out.println(jsonobject18.toString(2));
        System.out.println(XML.toString(jsonobject18));
        System.out.println("");
        JSONObject jsonobject19 = XML.toJSONObject("<!ENTITY tp-address PUBLIC '-//ABC University::Special Collections Library//TEXT (titlepage: name and address)//EN' 'tpspcoll.sgm'><list type='simple'><head>Repository Address </head><item>Special Collections Library</item><item>ABC University</item><item>Main Library, 40 Circle Drive</item><item>Ourtown, Pennsylvania</item><item>17654 USA</item></list>");
        System.out.println(jsonobject19.toString());
        System.out.println(XML.toString(jsonobject19));
        System.out.println("");
        JSONObject jsonobject20 = XML.toJSONObject("<test intertag status=ok><empty/>deluxe<blip sweet=true>&amp;&quot;toot&quot;&toot;&#x41;</blip><x>eks</x><w>bonus</w><w>bonus2</w></test>");
        System.out.println(jsonobject20.toString(2));
        System.out.println(XML.toString(jsonobject20));
        System.out.println("");
        JSONObject jsonobject21 = HTTP.toJSONObject("GET / HTTP/1.0\nAccept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*\nAccept-Language: en-us\nUser-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; Win 9x 4.90; T312461; Q312461)\nHost: www.nokko.com\nConnection: keep-alive\nAccept-encoding: gzip, deflate\n");
        System.out.println(jsonobject21.toString(2));
        System.out.println(HTTP.toString(jsonobject21));
        System.out.println("");
        JSONObject jsonobject22 = HTTP.toJSONObject("HTTP/1.1 200 Oki Doki\nDate: Sun, 26 May 2002 17:38:52 GMT\nServer: Apache/1.3.23 (Unix) mod_perl/1.26\nKeep-Alive: timeout=15, max=100\nConnection: Keep-Alive\nTransfer-Encoding: chunked\nContent-Type: text/html\n");
        System.out.println(jsonobject22.toString(2));
        System.out.println(HTTP.toString(jsonobject22));
        System.out.println("");
        JSONObject jsonobject23 = new JSONObject("{nix: null, nux: false, null: 'null', 'Request-URI': '/', Method: 'GET', 'HTTP-Version': 'HTTP/1.0'}");
        System.out.println(jsonobject23.toString(2));
        System.out.println((new StringBuilder("isNull: ")).append(jsonobject23.isNull("nix")).toString());
        System.out.println((new StringBuilder("   has: ")).append(jsonobject23.has("nix")).toString());
        System.out.println(XML.toString(jsonobject23));
        System.out.println(HTTP.toString(jsonobject23));
        System.out.println("");
        JSONObject jsonobject24 = XML.toJSONObject("<?xml version='1.0' encoding='UTF-8'?>\n\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"><SOAP-ENV:Body><ns1:doGoogleSearch xmlns:ns1=\"urn:GoogleSearch\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><key xsi:type=\"xsd:string\">GOOGLEKEY</key> <q xsi:type=\"xsd:string\">'+search+'</q> <start xsi:type=\"xsd:int\">0</start> <maxResults xsi:type=\"xsd:int\">10</maxResults> <filter xsi:type=\"xsd:boolean\">true</filter> <restrict xsi:type=\"xsd:string\"></restrict> <safeSearch xsi:type=\"xsd:boolean\">false</safeSearch> <lr xsi:type=\"xsd:string\"></lr> <ie xsi:type=\"xsd:string\">latin1</ie> <oe xsi:type=\"xsd:string\">latin1</oe></ns1:doGoogleSearch></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        System.out.println(jsonobject24.toString(2));
        System.out.println(XML.toString(jsonobject24));
        System.out.println("");
        JSONObject jsonobject25 = new JSONObject("{Envelope: {Body: {\"ns1:doGoogleSearch\": {oe: \"latin1\", filter: true, q: \"'+search+'\", key: \"GOOGLEKEY\", maxResults: 10, \"SOAP-ENV:encodingStyle\": \"http://schemas.xmlsoap.org/soap/encoding/\", start: 0, ie: \"latin1\", safeSearch:false, \"xmlns:ns1\": \"urn:GoogleSearch\"}}}}");
        System.out.println(jsonobject25.toString(2));
        System.out.println(XML.toString(jsonobject25));
        System.out.println("");
        JSONObject jsonobject26 = CookieList.toJSONObject("  f%oo = b+l=ah  ; o;n%40e = t.wo ");
        System.out.println(jsonobject26.toString(2));
        System.out.println(CookieList.toString(jsonobject26));
        System.out.println("");
        JSONObject jsonobject27 = Cookie.toJSONObject("f%oo=blah; secure ;expires = April 24, 2002");
        System.out.println(jsonobject27.toString(2));
        System.out.println(Cookie.toString(jsonobject27));
        System.out.println("");
        JSONObject jsonobject28 = new JSONObject("{script: 'It is not allowed in HTML to send a close script tag in a string<script>because it confuses browsers</script>so we insert a backslash before the /'}");
        System.out.println(jsonobject28.toString());
        System.out.println("");
        JSONTokener jsontokener = new JSONTokener("{op:'test', to:'session', pre:1}{op:'test', to:'session', pre:2}");
        JSONObject jsonobject29 = new JSONObject(jsontokener);
        System.out.println(jsonobject29.toString());
        System.out.println((new StringBuilder("pre: ")).append(jsonobject29.optInt("pre")).toString());
        char c = jsontokener.skipTo('{');
        System.out.println(c);
        JSONObject jsonobject30 = new JSONObject(jsontokener);
        System.out.println(jsonobject30.toString());
        System.out.println("");
        JSONArray jsonarray8 = CDL.toJSONArray("Comma delimited list test, '\"Strip\"Quotes', 'quote, comma', No quotes, 'Single Quotes', \"Double Quotes\"\n1,'2',\"3\"\n,'It is \"good,\"', \"It works.\"\n\n");
        String s1 = CDL.toString(jsonarray8);
        System.out.println(s1);
        System.out.println("");
        System.out.println(jsonarray8.toString(4));
        System.out.println("");
        JSONArray jsonarray9 = CDL.toJSONArray(s1);
        System.out.println(jsonarray9.toString(4));
        System.out.println("");
        JSONArray jsonarray10 = new JSONArray(" [\"<escape>\", next is an implied null , , ok,] ");
        System.out.println(jsonarray10.toString());
        System.out.println("");
        System.out.println(XML.toString(jsonarray10));
        System.out.println("");
        JSONObject jsonobject31 = new JSONObject("{ fun => with non-standard forms ; forgiving => This package can be used to parse formats that are similar to but not stricting conforming to JSON; why=To make it easier to migrate existing data to JSON,one = [[1.00]]; uno=[[{1=>1}]];'+':+6e66 ;pluses=+++;empty = '' , 'double':0.666,true: TRUE, false: FALSE, null=NULL;[true] = [[!,@;*]]; string=>  o. k. ; \r oct=0666; hex=0x666; dec=666; o=0999; noh=0x0x}");
        System.out.println(jsonobject31.toString(4));
        System.out.println("");
        if(jsonobject31.getBoolean("true") && !jsonobject31.getBoolean("false"))
            System.out.println("It's all good");
        System.out.println("");
        String args2[] = new String[4];
        args2[0] = "dec";
        args2[1] = "oct";
        args2[2] = "hex";
        args2[3] = "missing";
        JSONObject jsonobject32 = new JSONObject(jsonobject31, args2);
        System.out.println(jsonobject32.toString(4));
        System.out.println("");
        System.out.println((new JSONStringer()).array().value(jsonarray10).value(jsonobject32).endArray());
        jsonobject33 = new JSONObject("{string: \"98.6\", long: 2147483648, int: 2147483647, longer: 9223372036854775807, double: 9223372036854775808}");
        System.out.println(jsonobject33.toString(4));
        System.out.println("\ngetInt");
        System.out.println((new StringBuilder("int    ")).append(jsonobject33.getInt("int")).toString());
        System.out.println((new StringBuilder("long   ")).append(jsonobject33.getInt("long")).toString());
        System.out.println((new StringBuilder("longer ")).append(jsonobject33.getInt("longer")).toString());
        System.out.println("\ngetLong");
        System.out.println((new StringBuilder("int    ")).append(jsonobject33.getLong("int")).toString());
        System.out.println((new StringBuilder("long   ")).append(jsonobject33.getLong("long")).toString());
        System.out.println((new StringBuilder("longer ")).append(jsonobject33.getLong("longer")).toString());
        System.out.println("\ngetDouble");
        System.out.println((new StringBuilder("int    ")).append(jsonobject33.getDouble("int")).toString());
        System.out.println((new StringBuilder("long   ")).append(jsonobject33.getDouble("long")).toString());
        System.out.println((new StringBuilder("longer ")).append(jsonobject33.getDouble("longer")).toString());
        System.out.println((new StringBuilder("double ")).append(jsonobject33.getDouble("double")).toString());
        System.out.println((new StringBuilder("string ")).append(jsonobject33.getDouble("string")).toString());
        jsonobject33.put("good sized", 0x7fffffffffffffffL);
        System.out.println(jsonobject33.toString(4));
        JSONArray jsonarray11 = new JSONArray("[2147483647, 2147483648, 9223372036854775807, 9223372036854775808]");
        System.out.println(jsonarray11.toString(4));
        System.out.println("\nKeys: ");
        iterator = jsonobject33.keys();
_L3:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray14;
        JSONObject jsonobject40;
        JSONArray jsonarray16;
        System.out.println("\naccumulate: ");
        JSONObject jsonobject34 = new JSONObject();
        jsonobject34.accumulate("stooge", "Curly");
        jsonobject34.accumulate("stooge", "Larry");
        jsonobject34.accumulate("stooge", "Moe");
        jsonobject34.getJSONArray("stooge").put(5, "Shemp");
        System.out.println(jsonobject34.toString(4));
        System.out.println("\nwrite:");
        System.out.println(jsonobject34.write(new StringWriter()));
        JSONObject jsonobject35 = XML.toJSONObject("<xml empty><a></a><a>1</a><a>22</a><a>333</a></xml>");
        System.out.println(jsonobject35.toString(4));
        System.out.println(XML.toString(jsonobject35));
        JSONObject jsonobject36 = XML.toJSONObject("<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>");
        System.out.println(jsonobject36.toString(4));
        System.out.println(XML.toString(jsonobject36));
        JSONArray jsonarray12 = JSONML.toJSONArray("<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>");
        System.out.println(jsonarray12.toString(4));
        System.out.println(JSONML.toString(jsonarray12));
        JSONObject jsonobject37 = new JSONObject(null);
        JSONArray jsonarray13 = new JSONArray(null);
        jsonobject37.append("stooge", "Joe DeRita");
        jsonobject37.append("stooge", "Shemp");
        jsonobject37.accumulate("stooges", "Curly");
        jsonobject37.accumulate("stooges", "Larry");
        jsonobject37.accumulate("stooges", "Moe");
        jsonobject37.accumulate("stoogearray", jsonobject37.get("stooges"));
        jsonobject37.put("map", null);
        jsonobject37.put("collection", null);
        jsonobject37.put("array", jsonarray13);
        jsonarray13.put(null);
        jsonarray13.put(null);
        System.out.println(jsonobject37.toString(4));
        JSONObject jsonobject38 = new JSONObject("{plist=Apple; AnimalSmells = { pig = piggish; lamb = lambish; worm = wormy; }; AnimalSounds = { pig = oink; lamb = baa; worm = baa;  Lisa = \"Why is the worm talking like a lamb?\" } ; AnimalColors = { pig = pink; lamb = black; worm = pink; } } ");
        System.out.println(jsonobject38.toString(4));
        jsonarray14 = new JSONArray(" (\"San Francisco\", \"New York\", \"Seoul\", \"London\", \"Seattle\", \"Shanghai\")");
        System.out.println(jsonarray14.toString());
        JSONObject jsonobject39 = XML.toJSONObject("<a ichi='1' ni='2'><b>The content of b</b> and <c san='3'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>");
        System.out.println(jsonobject39.toString(2));
        System.out.println(XML.toString(jsonobject39));
        System.out.println("");
        JSONArray jsonarray15 = JSONML.toJSONArray("<a ichi='1' ni='2'><b>The content of b</b> and <c san='3'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>");
        System.out.println(jsonarray15.toString(4));
        System.out.println(JSONML.toString(jsonarray15));
        System.out.println("");
        jsonobject40 = JSONML.toJSONObject("<Root><MsgType type=\"node\"><BatchType type=\"string\">111111111111111</BatchType></MsgType></Root>");
        System.out.println(jsonobject40);
        jsonarray16 = JSONML.toJSONArray("<Root><MsgType type=\"node\"><BatchType type=\"string\">111111111111111</BatchType></MsgType></Root>");
        System.out.println(jsonarray16);
        System.out.println("\nTesting Exceptions: ");
        System.out.print("Exception: ");
        JSONArray jsonarray17 = new JSONArray("[\n\r\n\r}");
        System.out.println(jsonarray17.toString());
_L5:
        System.out.print("Exception: ");
        JSONArray jsonarray18 = new JSONArray("<\n\r\n\r      ");
        System.out.println(jsonarray18.toString());
        JSONArray jsonarray19 = jsonarray18;
_L6:
        System.out.print("Exception: ");
        JSONArray jsonarray20 = new JSONArray();
        jsonarray20.put((-1.0D / 0.0D));
        jsonarray20.put((0.0D / 0.0D));
        System.out.println(jsonarray20.toString());
        JSONArray jsonarray21 = jsonarray20;
_L7:
        System.out.print("Exception: ");
        Exception exception;
        Exception exception2;
        Exception exception3;
        Exception exception4;
        JSONArray jsonarray22;
        JSONArray jsonarray23;
        JSONArray jsonarray26;
        JSONObject jsonobject41;
        JSONStringer jsonstringer1;
        JSONArray jsonarray27;
        JSONObject jsonobject43;
        JSONObject jsonobject44;
        JSONObject jsonobject45;
        try
        {
            System.out.println(jsonobject40.getDouble("stooge"));
        }
        catch(Exception exception5)
        {
            System.out.println(exception5);
        }
        System.out.print("Exception: ");
        try
        {
            System.out.println(jsonobject40.getDouble("howard"));
        }
        catch(Exception exception6)
        {
            System.out.println(exception6);
        }
        System.out.print("Exception: ");
        try
        {
            System.out.println(jsonobject40.put(null, "howard"));
        }
        catch(Exception exception7)
        {
            System.out.println(exception7);
        }
        System.out.print("Exception: ");
        try
        {
            System.out.println(jsonarray21.getDouble(0));
        }
        catch(Exception exception8)
        {
            System.out.println(exception8);
        }
        System.out.print("Exception: ");
        try
        {
            System.out.println(jsonarray21.get(-1));
        }
        catch(Exception exception9)
        {
            System.out.println(exception9);
        }
        System.out.print("Exception: ");
        try
        {
            System.out.println(jsonarray21.put((0.0D / 0.0D)));
        }
        catch(Exception exception10)
        {
            System.out.println(exception10);
        }
        System.out.print("Exception: ");
        jsonobject45 = XML.toJSONObject("<a><b>    ");
        jsonobject40 = jsonobject45;
_L8:
        System.out.print("Exception: ");
        jsonobject44 = XML.toJSONObject("<a></b>    ");
        jsonobject40 = jsonobject44;
_L9:
        System.out.print("Exception: ");
        jsonobject43 = XML.toJSONObject("<a></a    ");
        jsonobject40 = jsonobject43;
_L10:
        System.out.print("Exception: ");
        jsonarray22 = new JSONArray(new Object());
        System.out.println(jsonarray22.toString());
        jsonarray16 = jsonarray22;
_L11:
        System.out.print("Exception: ");
        jsonarray23 = new JSONArray("[)");
        System.out.println(jsonarray23.toString());
_L12:
        System.out.print("Exception: ");
        jsonarray27 = JSONML.toJSONArray("<xml");
        jsonarray26 = jsonarray27;
        System.out.println(jsonarray26.toString(4));
_L13:
        System.out.print("Exception: ");
        Exception exception11;
        Exception exception12;
        Exception exception13;
        Exception exception15;
        Exception exception16;
        Exception exception17;
        String s3;
        JSONArray jsonarray25;
        try
        {
            jsonarray26 = JSONML.toJSONArray("<right></wrong>");
            System.out.println(jsonarray26.toString(4));
        }
        catch(Exception exception18)
        {
            jsonarray26;
            System.out.println(exception18);
        }
        System.out.print("Exception: ");
        jsonobject41 = new JSONObject("{\"koda\": true, \"koda\": true}");
        System.out.println(jsonobject41.toString(4));
_L14:
        System.out.print("Exception: ");
        jsonstringer1 = new JSONStringer();
        jsonstringer1.object().key("bosanda").value("MARIE HAA'S").key("bosanda").value("MARIE HAA\\'S").endObject().toString();
        System.out.println(jsonobject41.toString(4));
_L4:
        return;
_L2:
        String s2 = (String)iterator.next();
        System.out.println((new StringBuilder(String.valueOf(s2))).append(": ").append(jsonobject33.getString(s2)).toString());
          goto _L3
        exception;
        System.out.println(exception.toString());
          goto _L4
        exception2;
_L22:
        System.out.println(exception2);
        jsonarray17 = jsonarray14;
          goto _L5
_L21:
        System.out.println(exception3);
        jsonarray19 = jsonarray18;
          goto _L6
_L20:
        System.out.println(exception4);
        jsonarray21 = jsonarray20;
          goto _L7
        exception11;
        System.out.println(exception11);
          goto _L8
        exception12;
        System.out.println(exception12);
          goto _L9
        exception13;
        System.out.println(exception13);
          goto _L10
_L19:
        System.out.println(exception15);
          goto _L11
_L18:
        System.out.println(exception16);
          goto _L12
_L17:
        System.out.println(exception17);
        jsonarray26 = jsonarray25;
        s3;
          goto _L13
_L16:
        JSONObject jsonobject42;
        Exception exception20;
        System.out.println(exception20);
        jsonobject41 = jsonobject42;
          goto _L14
_L15:
        Exception exception21;
        System.out.println(exception21);
          goto _L4
        exception21;
          goto _L15
        Exception exception19;
        exception19;
        jsonobject42 = jsonobject41;
        exception20 = exception19;
          goto _L16
        Exception exception22;
        exception22;
        s3 = "<xml";
        jsonarray25 = jsonarray26;
        exception17 = exception22;
          goto _L17
        exception16;
          goto _L18
        Exception exception14;
        exception14;
        jsonarray16 = jsonarray22;
        exception15 = exception14;
          goto _L19
        exception4;
          goto _L20
        exception3;
          goto _L21
        Exception exception1;
        exception1;
        jsonarray14 = jsonarray17;
        exception2 = exception1;
          goto _L22
        Exception exception25;
        exception25;
        jsonarray18 = jsonarray17;
        exception3 = exception25;
          goto _L21
        Exception exception24;
        exception24;
        jsonarray20 = jsonarray19;
        exception4 = exception24;
          goto _L20
        exception15;
          goto _L19
        Exception exception23;
        exception23;
        jsonarray21;
        exception16 = exception23;
          goto _L18
        exception17;
        JSONArray jsonarray24 = jsonarray16;
        s3 = "<xml";
        jsonarray25 = jsonarray24;
          goto _L17
        exception20;
        jsonobject42 = jsonobject40;
          goto _L16
        exception21;
          goto _L15
    }

    private class _cls1Obj
        implements JSONString
    {

        public String getBENT()
        {
            return "All uppercase key";
        }

        public double getNumber()
        {
            return aNumber;
        }

        public String getString()
        {
            return aString;
        }

        public String getX()
        {
            return "x";
        }

        public boolean isBoolean()
        {
            return aBoolean;
        }

        public String toJSONString()
        {
            return (new StringBuilder("{")).append(JSONObject.quote(aString)).append(":").append(JSONObject.doubleToString(aNumber)).append("}").toString();
        }

        public String toString()
        {
            return (new StringBuilder(String.valueOf(getString()))).append(" ").append(getNumber()).append(" ").append(isBoolean()).append(".").append(getBENT()).append(" ").append(getX()).toString();
        }

        public boolean aBoolean;
        public double aNumber;
        public String aString;

        public _cls1Obj(String s, double d, boolean flag)
        {
            aString = s;
            aNumber = d;
            aBoolean = flag;
        }
    }

}
