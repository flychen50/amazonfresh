// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.github.droidfu.support;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class IntentSupport
{

    public IntentSupport()
    {
    }

    public static boolean isIntentAvailable(Context context, Intent intent)
    {
        boolean flag;
        if(context.getPackageManager().queryIntentActivities(intent, 0x10000).isEmpty())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static boolean isIntentAvailable(Context context, String s, Uri uri, String s1)
    {
        Intent intent;
        boolean flag;
        if(uri != null)
            intent = new Intent(s, uri);
        else
            intent = new Intent(s);
        if(s1 != null)
            intent.setType(s1);
        if(context.getPackageManager().queryIntentActivities(intent, 0x10000).isEmpty())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static boolean isIntentAvailable(Context context, String s, String s1)
    {
        Intent intent = new Intent(s);
        if(s1 != null)
            intent.setType(s1);
        boolean flag;
        if(context.getPackageManager().queryIntentActivities(intent, 0x10000).isEmpty())
            flag = false;
        else
            flag = true;
        return flag;
    }

    public static Intent newCallNumberIntent(String s)
    {
        return new Intent("android.intent.action.CALL", Uri.parse((new StringBuilder("tel:")).append(s.replace(" ", "")).toString()));
    }

    public static Intent newDialNumberIntent(String s)
    {
        return new Intent("android.intent.action.DIAL", Uri.parse((new StringBuilder("tel:")).append(s.replace(" ", "")).toString()));
    }

    public static Intent newEmailIntent(Context context, String s, String s1, String s2)
    {
        Intent intent = new Intent("android.intent.action.SEND");
        String as[] = new String[1];
        as[0] = s;
        intent.putExtra("android.intent.extra.EMAIL", as);
        intent.putExtra("android.intent.extra.TEXT", s2);
        intent.putExtra("android.intent.extra.SUBJECT", s1);
        intent.setType("message/rfc822");
        return intent;
    }

    public static Intent newMapsIntent(String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("geo:0,0?q=");
        stringbuilder.append(Uri.encode(s));
        stringbuilder.append(Uri.encode((new StringBuilder("(")).append(s1).append(")").toString()));
        stringbuilder.append((new StringBuilder("&hl=")).append(Locale.getDefault().getLanguage()).toString());
        return new Intent("android.intent.action.VIEW", Uri.parse(stringbuilder.toString()));
    }

    public static Intent newSelectPictureIntent()
    {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        return intent;
    }

    public static Intent newShareIntent(Context context, String s, String s1, String s2)
    {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", s1);
        intent.putExtra("android.intent.extra.SUBJECT", s);
        intent.setType("text/*");
        return Intent.createChooser(intent, s2);
    }

    public static Intent newTakePictureIntent(File file)
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(file));
        return intent;
    }

    public static final String MIME_TYPE_EMAIL = "message/rfc822";
    public static final String MIME_TYPE_TEXT = "text/*";
}
