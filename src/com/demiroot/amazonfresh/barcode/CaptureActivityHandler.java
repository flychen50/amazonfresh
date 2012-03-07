// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.*;
import com.google.zxing.Result;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            DecodeThread, CameraManager, CaptureActivity

public final class CaptureActivityHandler extends Handler
{
    private static final class State extends Enum
    {

        public static State valueOf(String s)
        {
            return (State)Enum.valueOf(com/demiroot/amazonfresh/barcode/CaptureActivityHandler$State, s);
        }

        public static State[] values()
        {
            State astate[] = ENUM$VALUES;
            int i = astate.length;
            State astate1[] = new State[i];
            System.arraycopy(astate, 0, astate1, 0, i);
            return astate1;
        }

        public static final State DONE;
        private static final State ENUM$VALUES[];
        public static final State PREVIEW;
        public static final State SUCCESS;

        static 
        {
            PREVIEW = new State("PREVIEW", 0);
            SUCCESS = new State("SUCCESS", 1);
            DONE = new State("DONE", 2);
            State astate[] = new State[3];
            astate[0] = PREVIEW;
            astate[1] = SUCCESS;
            astate[2] = DONE;
            ENUM$VALUES = astate;
        }

        private State(String s, int i)
        {
            super(s, i);
        }
    }


    CaptureActivityHandler(CaptureActivity captureactivity, boolean flag)
    {
        activity = captureactivity;
        decodeThread = new DecodeThread(captureactivity);
        decodeThread.start();
        state = State.SUCCESS;
        CameraManager.get().startPreview();
        if(flag)
            restartPreviewAndDecode();
    }

    private void restartPreviewAndDecode()
    {
        if(state == State.SUCCESS)
        {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), 0x7f0b0003);
            CameraManager.get().requestAutoFocus(this, 0x7f0b0002);
            activity.resetForScan();
        }
    }

    public void handleMessage(Message message)
    {
        if(state != State.DONE) goto _L2; else goto _L1
_L1:
        return;
_L2:
        switch(message.what)
        {
        case 2131427330: 
            if(state == State.PREVIEW)
                CameraManager.get().requestAutoFocus(this, 0x7f0b0002);
            break;

        case 2131427333: 
            state = State.SUCCESS;
            Bundle bundle1 = message.getData();
            Bitmap bitmap;
            if(bundle1 == null)
                bitmap = null;
            else
                bitmap = (Bitmap)bundle1.getParcelable("barcode_bitmap");
            activity.handleDecode((Result)message.obj, bitmap);
            break;

        case 2131427332: 
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), 0x7f0b0003);
            break;

        case 2131427336: 
            restartPreviewAndDecode();
            break;

        case 2131427335: 
            Intent intent = (Intent)message.obj;
            String s = intent.getStringExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT");
            String s1 = intent.getStringExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT_FORMAT");
            Intent intent1 = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT", s);
            bundle.putString("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT_FORMAT", s1);
            intent1.putExtras(bundle);
            activity.setResult(98, intent1);
            activity.finish();
            break;

        case 2131427338: 
            activity.setResult(97);
            activity.finish();
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void quitSynchronously()
    {
        state = State.DONE;
        CameraManager.get().stopPreview();
        Message.obtain(decodeThread.getHandler(), 0x7f0b0006).sendToTarget();
        try
        {
            decodeThread.join();
        }
        catch(InterruptedException interruptedexception) { }
        removeMessages(0x7f0b0005);
        removeMessages(0x7f0b0004);
        removeMessages(0x7f0b0008);
        removeMessages(0x7f0b0007);
    }

    private final CaptureActivity activity;
    private final DecodeThread decodeThread;
    private State state;
}
