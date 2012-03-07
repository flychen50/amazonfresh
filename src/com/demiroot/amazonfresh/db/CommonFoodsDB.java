// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.db;

import android.content.*;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import java.util.List;
import java.util.Scanner;

public class CommonFoodsDB extends ContentProvider
{
    static class OpenerHelper extends SQLiteOpenHelper
    {

        private void loadSearchTerms(SQLiteDatabase sqlitedatabase)
        {
            Scanner scanner = new Scanner(context.getResources().openRawResource(0x7f060001));
            int i = 0;
            do
            {
                if(!scanner.hasNextLine())
                    return;
                String s = scanner.nextLine();
                Object aobj[] = new Object[2];
                aobj[0] = s;
                int j = i + 1;
                aobj[1] = Integer.valueOf(i);
                sqlitedatabase.execSQL("INSERT INTO common_search_terms (suggest_text_1, rank) VALUES (?,?)", aobj);
                i = j;
            } while(true);
        }

        public void onCreate(SQLiteDatabase sqlitedatabase)
        {
            sqlitedatabase.execSQL("CREATE TABLE common_search_terms (_id INTEGER PRIMARY KEY AUTOINCREMENT, suggest_text_1 varchar(100), rank int)");
            loadSearchTerms(sqlitedatabase);
            sqlitedatabase.execSQL("CREATE TABLE recent_search_terms (_id INTEGER PRIMARY KEY AUTOINCREMENT, suggest_text_1 varchar(100), rank int)");
        }

        public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
        {
            sqlitedatabase.execSQL("DROP TABLE IF EXISTS common_search_terms");
            sqlitedatabase.execSQL("DROP TABLE IF EXISTS recent_search_terms");
            onCreate(sqlitedatabase);
        }

        private Context context;

        public OpenerHelper(Context context1)
        {
            super(context1, "common_search_terms", null, 5);
            context = context1;
        }
    }


    public CommonFoodsDB()
    {
    }

    public CommonFoodsDB(Context context)
    {
        db = (new OpenerHelper(context)).getWritableDatabase();
    }

    public static boolean isIntentAvailable(Context context, String s)
    {
        boolean flag;
        if(context.getPackageManager().queryIntentActivities(new Intent(s), 0x10000).size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void loadDB()
    {
        if(db == null)
            db = (new OpenerHelper(getContext())).getWritableDatabase();
    }

    public static void updateNewSuggestions(List list, Context context)
    {
        SQLiteDatabase sqlitedatabase;
        int i;
        sqlitedatabase = (new OpenerHelper(context)).getWritableDatabase();
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS common_search_terms_temp");
        sqlitedatabase.execSQL("CREATE TABLE common_search_terms_temp (_id INTEGER PRIMARY KEY AUTOINCREMENT, suggest_text_1 varchar(100), rank int)");
        i = 0;
_L2:
        if(i < list.size())
            break MISSING_BLOCK_LABEL_65;
        "common_search_terms";
        JVM INSTR monitorenter ;
        sqlitedatabase.execSQL("ALTER TABLE common_search_terms RENAME TO temp_for_switch");
        sqlitedatabase.execSQL("ALTER TABLE common_search_terms_temp RENAME TO common_search_terms");
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS temp_for_switch");
        "common_search_terms";
        JVM INSTR monitorexit ;
        sqlitedatabase.close();
        return;
        String s = (String)list.get(i);
        Object aobj[] = new Object[2];
        aobj[0] = s;
        aobj[1] = Integer.valueOf(i);
        sqlitedatabase.execSQL("INSERT INTO common_search_terms_temp (suggest_text_1, rank) VALUES (?,?)", aobj);
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        "common_search_terms";
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int delete(Uri uri, String s, String as[])
    {
        throw new UnsupportedOperationException("Delete is not supported");
    }

    public Cursor getFilterCursor(CharSequence charsequence)
    {
        Cursor cursor;
        if(charsequence == null || charsequence.length() < 2)
        {
            cursor = null;
        } else
        {
            SQLiteDatabase sqlitedatabase = db;
            String as[] = new String[2];
            as[0] = "_id";
            as[1] = "suggest_text_1";
            cursor = sqlitedatabase.query("common_search_terms", as, (new StringBuilder("suggest_text_1 like '")).append(charsequence).append("%'").toString(), null, null, null, "rank ASC");
        }
        return cursor;
    }

    public String getType(Uri uri)
    {
        String s;
        if("".equals(uri.getPath()) || "/".equals(uri.getPath()))
            s = "vnd.android.cursor.dir/com.demiroot.amazonfresh.commonfoods";
        else
            s = "vnd.android.cursor.item/com.demiroot.amazonfresh.commonfoods";
        return s;
    }

    public Uri insert(Uri uri, ContentValues contentvalues)
    {
        throw new UnsupportedOperationException("Insert is not supported");
    }

    public boolean onCreate()
    {
        return true;
    }

    public Cursor query(Uri uri, String as[], String s, String as1[], String s1)
    {
        String s2;
        loadDB();
        s2 = TextUtils.join(" ", as1);
        if(s2.length() != 0) goto _L2; else goto _L1
_L1:
        Object obj;
        if(!isIntentAvailable(getContext(), "android.media.action.IMAGE_CAPTURE"))
        {
            obj = null;
        } else
        {
            String as4[] = new String[4];
            as4[0] = "_id";
            as4[1] = "suggest_icon_1";
            as4[2] = "suggest_text_1";
            as4[3] = "suggest_intent_data";
            MatrixCursor matrixcursor = new MatrixCursor(as4);
            String as5[] = new String[4];
            as5[0] = "0";
            as5[1] = "2130837519";
            as5[2] = getContext().getString(0x7f070075);
            as5[3] = "BARCODE_SEARCH_KEYWORD";
            matrixcursor.addRow(as5);
            obj = matrixcursor;
        }
_L3:
        return ((Cursor) (obj));
_L2:
        "common_search_terms";
        JVM INSTR monitorenter ;
        int i = s2.length();
        if(i >= 2)
            break MISSING_BLOCK_LABEL_217;
        Cursor cursor;
        SQLiteDatabase sqlitedatabase1 = db;
        String as3[] = new String[2];
        as3[0] = "_id";
        as3[1] = "suggest_text_1 as suggest_intent_data";
        cursor = sqlitedatabase1.query("recent_search_terms", as3, null, null, null, null, "rank ASC");
        obj = cursor;
        "common_search_terms";
        JVM INSTR monitorexit ;
          goto _L3
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        "common_search_terms";
        JVM INSTR monitorexit ;
        obj = null;
          goto _L3
        SQLiteDatabase sqlitedatabase = db;
        String as2[] = new String[3];
        as2[0] = "_id";
        as2[1] = "suggest_text_1";
        as2[2] = "suggest_text_1 as suggest_intent_data";
        obj = sqlitedatabase.query("common_search_terms", as2, (new StringBuilder("suggest_text_1 LIKE '")).append(s2).append("%' OR ").append("suggest_text_1").append(" like '% ").append(s2).append("%'").toString(), null, null, null, "rank ASC");
        "common_search_terms";
        JVM INSTR monitorexit ;
          goto _L3
    }

    public int update(Uri uri, ContentValues contentvalues, String s, String as[])
    {
        throw new UnsupportedOperationException("Insert is not supported");
    }

    public static final String AUTHORITY = "com.demiroot.amazonfresh.db.CommonFoodsDB";
    public static final String TABLE_NAME = "common_search_terms";
    public static final String TABLE_NAME_RECENT = "recent_search_terms";
    public static final String TABLE_TEMP = "common_search_terms_temp";
    public static final int VERSION = 5;
    private volatile SQLiteDatabase db;
}
