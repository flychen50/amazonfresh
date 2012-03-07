// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.os.*;
import android.util.Log;
import com.google.zxing.*;
import com.google.zxing.common.GlobalHistogramBinarizer;
import java.util.Hashtable;
import java.util.Vector;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            CameraManager, CaptureActivity, BaseLuminanceSource

final class DecodeThread extends Thread
{

    DecodeThread(CaptureActivity captureactivity)
    {
        activity = captureactivity;
        setDecodeProductMode();
    }

    private void decode(byte abyte0[], int i, int j)
    {
        long l;
        Result result;
        BaseLuminanceSource baseluminancesource;
        boolean flag;
        l = System.currentTimeMillis();
        result = null;
        baseluminancesource = null;
        flag = false;
        BinaryBitmap binarybitmap;
        baseluminancesource = CameraManager.get().buildLuminanceSource(abyte0, i, j);
        GlobalHistogramBinarizer globalhistogrambinarizer = new GlobalHistogramBinarizer(baseluminancesource);
        binarybitmap = new BinaryBitmap(globalhistogrambinarizer);
        Result result1 = multiFormatReader.decodeWithState(binarybitmap);
        boolean flag1;
        result = result1;
        flag1 = true;
_L1:
        IllegalArgumentException illegalargumentexception;
        ReaderException readerexception;
        if(flag1)
        {
            long l1 = System.currentTimeMillis();
            Log.v("Amazon.DecodeThread", (new StringBuilder("Found barcode (")).append(l1 - l).append(" ms):\n").append(result.toString()).toString());
            Message message = Message.obtain(activity.getHandler(), 0x7f0b0005, result);
            Bundle bundle = new Bundle();
            bundle.putParcelable("barcode_bitmap", baseluminancesource.renderCroppedGreyscaleBitmap());
            message.setData(bundle);
            message.sendToTarget();
        } else
        if(flag)
            Message.obtain(activity.getHandler(), 0x7f0b000a).sendToTarget();
        else
            Message.obtain(activity.getHandler(), 0x7f0b0004).sendToTarget();
        return;
        readerexception;
        flag1 = false;
          goto _L1
        illegalargumentexception;
        flag1 = false;
        flag = true;
          goto _L1
    }

    private void setDecodeProductMode()
    {
        Hashtable hashtable = new Hashtable(3);
        Vector vector = new Vector(4);
        vector.addElement(BarcodeFormat.UPC_A);
        vector.addElement(BarcodeFormat.UPC_E);
        vector.addElement(BarcodeFormat.EAN_13);
        vector.addElement(BarcodeFormat.EAN_8);
        vector.addElement(BarcodeFormat.QR_CODE);
        hashtable.put(DecodeHintType.POSSIBLE_FORMATS, vector);
        hashtable.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        multiFormatReader.setHints(hashtable);
    }

    Handler getHandler()
    {
        return handler;
    }

    public void run()
    {
        Looper.prepare();
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                message.what;
                JVM INSTR tableswitch 2131427331 2131427334: default 36
            //                           2131427331 37
            //                           2131427332 36
            //                           2131427333 36
            //                           2131427334 62;
                   goto _L1 _L2 _L1 _L1 _L3
_L1:
                return;
_L2:
                decode((byte[])message.obj, message.arg1, message.arg2);
                continue; /* Loop/switch isn't completed */
_L3:
                Looper.myLooper().quit();
                if(true) goto _L1; else goto _L4
_L4:
            }

            final DecodeThread this$0;

            
            {
                this$0 = DecodeThread.this;
                super();
            }
        }
;
        Looper.loop();
    }

    public static final String BARCODE_BITMAP = "barcode_bitmap";
    private static final String TAG = "Amazon.DecodeThread";
    private final CaptureActivity activity;
    private Handler handler;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();

}
