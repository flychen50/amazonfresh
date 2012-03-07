// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.zxing.*;
import java.io.IOException;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            CameraManager, CaptureActivityHandler, ViewfinderView

public final class CaptureActivity extends Activity
    implements android.content.DialogInterface.OnDismissListener, android.content.DialogInterface.OnClickListener, android.view.SurfaceHolder.Callback
{
    private static class BeepListener
        implements android.media.MediaPlayer.OnCompletionListener
    {

        public void onCompletion(MediaPlayer mediaplayer)
        {
            mediaplayer.seekTo(0);
        }

        private BeepListener()
        {
        }

        BeepListener(BeepListener beeplistener)
        {
            this();
        }
    }


    public CaptureActivity()
    {
    }

    private void dismissSearchingDialogIfNotCurrentlySearching()
    {
        if(areCurrentlySearching || !searchingDialog.isShowing())
            break MISSING_BLOCK_LABEL_24;
        searchingDialog.dismiss();
_L1:
        return;
        Exception exception;
        exception;
        Log.e("ProgressDialog", exception.getMessage());
          goto _L1
    }

    private void drawResultPoints(Bitmap bitmap, Result result)
    {
        ResultPoint aresultpoint[] = result.getResultPoints();
        if(aresultpoint != null && aresultpoint.length > 0)
        {
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(0x7f090021));
            paint.setStrokeWidth(3F);
            paint.setStyle(android.graphics.Paint.Style.STROKE);
            canvas.drawRect(new Rect(2, 2, bitmap.getWidth() - 2, bitmap.getHeight() - 2), paint);
            paint.setColor(getResources().getColor(0x7f090022));
            if(aresultpoint.length == 2)
            {
                paint.setStrokeWidth(4F);
                canvas.drawLine(aresultpoint[0].getX(), aresultpoint[0].getY(), aresultpoint[1].getX(), aresultpoint[1].getY(), paint);
            } else
            {
                paint.setStrokeWidth(10F);
                int i = aresultpoint.length;
                int j = 0;
                while(j < i) 
                {
                    ResultPoint resultpoint = aresultpoint[j];
                    canvas.drawPoint(resultpoint.getX(), resultpoint.getY(), paint);
                    j++;
                }
            }
        }
    }

    private void initBeepSound()
    {
        AssetFileDescriptor assetfiledescriptor;
        if(mediaPlayer != null)
            break MISSING_BLOCK_LABEL_93;
        setVolumeControlStream(3);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.setOnCompletionListener(beepListener);
        assetfiledescriptor = getResources().openRawResourceFd(0x7f060000);
        mediaPlayer.setDataSource(assetfiledescriptor.getFileDescriptor(), assetfiledescriptor.getStartOffset(), assetfiledescriptor.getLength());
        assetfiledescriptor.close();
        mediaPlayer.setVolume(0.1F, 0.1F);
        mediaPlayer.prepare();
_L1:
        return;
        IOException ioexception;
        ioexception;
        mediaPlayer = null;
          goto _L1
    }

    private void initCamera(SurfaceHolder surfaceholder)
    {
label0:
        {
            try
            {
                CameraManager.get().openDriver(surfaceholder);
            }
            catch(IOException ioexception)
            {
                Log.w("Amazon.CaptureActivity", ioexception);
                if(false)
                    ;
                else
                    break label0;
            }
            if(handler == null)
            {
                boolean flag;
                if(lastResult == null)
                    flag = true;
                else
                    flag = false;
                handler = new CaptureActivityHandler(this, flag);
            }
        }
    }

    private void playBeepSoundAndVibrate()
    {
        if(mediaPlayer != null)
            mediaPlayer.start();
        ((Vibrator)getSystemService("vibrator")).vibrate(200L);
    }

    private void resetStatusView()
    {
        statusView.setBackgroundColor(getResources().getColor(0x7f09001f));
        statusTextView.setGravity(19);
        statusTextView.setTextSize(14F);
        statusTextView.setText(0x7f070005);
        lastResult = null;
    }

    private void showSearchingDialogIfCurrentlySearching()
    {
        if(!areCurrentlySearching || searchingDialog.isShowing())
            break MISSING_BLOCK_LABEL_24;
        searchingDialog.show();
_L1:
        return;
        Exception exception;
        exception;
        Log.e("ProgressDialog", exception.getMessage());
          goto _L1
    }

    public void drawViewfinder()
    {
        viewfinderView.drawViewfinder();
    }

    public Handler getHandler()
    {
        return handler;
    }

    public void handleDecode(Result result, Bitmap bitmap)
    {
        lastResult = result;
        drawResultPoints(bitmap, result);
        viewfinderView.drawResultBitmap(bitmap);
        statusView.setBackgroundColor(getResources().getColor(0x7f09001e));
        statusTextView.setGravity(17);
        statusTextView.setTextSize(18F);
        statusTextView.setText(0x7f070006);
        Intent intent = new Intent(getIntent().getAction());
        intent.putExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT", result.toString());
        intent.putExtra("com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT_FORMAT", result.getBarcodeFormat().toString());
        Message message = Message.obtain(handler, 0x7f0b0007);
        message.obj = intent;
        handler.sendMessageDelayed(message, 1500L);
    }

    void indicateCurrentlySearching(boolean flag)
    {
        if(flag)
        {
            areCurrentlySearching = true;
            showSearchingDialogIfCurrentlySearching();
        } else
        {
            areCurrentlySearching = false;
            dismissSearchingDialogIfNotCurrentlySearching();
        }
    }

    public void onClick(DialogInterface dialoginterface, int i)
    {
        if(i == -1)
        {
            setResult(99);
            finish();
        } else
        if(i == -3)
            Message.obtain(handler, 0x7f0b0008).sendToTarget();
        else
            Log.e("Amazon.CaptureActivity", (new StringBuilder("Unexpected button click with which=")).append(i).toString());
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addFlags(128);
        setContentView(0x7f030007);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView)findViewById(0x7f0b0020);
        statusView = findViewById(0x7f0b0021);
        statusTextView = (TextView)findViewById(0x7f0b0022);
        handler = null;
        lastResult = null;
        hasSurface = false;
        areCurrentlySearching = false;
        searchingDialog = new ProgressDialog(this);
        searchingDialog.setProgressStyle(0);
        searchingDialog.setTitle(0x7f070007);
        window.setSoftInputMode(3);
    }

    public void onDismiss(DialogInterface dialoginterface)
    {
        setResult(0);
        finish();
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(i == 4)
        {
            if(handler != null)
            {
                handler.quitSynchronously();
                handler = null;
            }
            CameraManager.get().closeDriver();
            indicateCurrentlySearching(false);
            setResult(0);
            finish();
            flag = true;
        } else
        if(i == 80 || i == 27)
            flag = true;
        else
        if(i == 84)
            flag = true;
        else
            flag = super.onKeyDown(i, keyevent);
        return flag;
    }

    protected void onPause()
    {
        super.onPause();
        dismissSearchingDialogIfNotCurrentlySearching();
        if(handler != null)
        {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    protected void onResume()
    {
        super.onResume();
        SurfaceHolder surfaceholder = ((SurfaceView)findViewById(0x7f0b001f)).getHolder();
        if(hasSurface)
        {
            initCamera(surfaceholder);
        } else
        {
            surfaceholder.addCallback(this);
            surfaceholder.setType(3);
        }
        resetStatusView();
        showSearchingDialogIfCurrentlySearching();
    }

    void resetForScan()
    {
        resetStatusView();
        drawViewfinder();
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
    {
    }

    public void surfaceCreated(SurfaceHolder surfaceholder)
    {
        if(!hasSurface)
        {
            hasSurface = true;
            initCamera(surfaceholder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder)
    {
        hasSurface = false;
    }

    public static final String BARCODE_SCAN_INTENT_EXTRA_OUT_RESULT_FORMAT_KEY = "com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT_FORMAT";
    public static final String BARCODE_SCAN_INTENT_EXTRA_OUT_RESULT_KEY = "com.amazon.mShop.android.barcode.CaptureActivity.OUT_RESULT";
    private static final float BEEP_VOLUME = 0.1F;
    private static final long INTENT_RESULT_DURATION = 1500L;
    public static final int RESULT_BARCODE_SCAN_SUCCESS = 98;
    public static final int RESULT_CAMERA_PICTURE_FORMAT_UNSUPPORTED = 97;
    public static final int RESULT_TAKE_PHOTO = 99;
    private static final String TAG = "Amazon.CaptureActivity";
    private static final long VIBRATE_DURATION = 200L;
    private boolean areCurrentlySearching;
    private ImageButton barcodeScanHelpIconButton;
    private final android.media.MediaPlayer.OnCompletionListener beepListener = new BeepListener(null);
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Result lastResult;
    private MediaPlayer mediaPlayer;
    private ProgressDialog searchingDialog;
    private TextView statusTextView;
    private View statusView;
    private ViewfinderView viewfinderView;
}
