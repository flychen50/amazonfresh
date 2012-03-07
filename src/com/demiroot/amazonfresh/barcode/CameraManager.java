// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.demiroot.amazonfresh.barcode;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import com.google.zxing.ResultPoint;
import java.io.IOException;
import java.util.regex.Pattern;

// Referenced classes of package com.demiroot.amazonfresh.barcode:
//            InterleavedYUV422LuminanceSource, PlanarYUVLuminanceSource, BaseLuminanceSource

final class CameraManager
{

    private CameraManager(Context context1)
    {
        context = context1;
        camera = null;
        initialized = false;
        previewing = false;
        if(Integer.parseInt(android.os.Build.VERSION.SDK) <= 3)
            useOneShotPreviewCallback = false;
        else
            useOneShotPreviewCallback = true;
    }

    private static Point findBestPreviewSizeValue(CharSequence charsequence, Point point)
    {
        String as[];
        int i;
        int j;
        int k;
        int l;
        int i1;
        as = COMMA_PATTERN.split(charsequence);
        i = as.length;
        j = 0;
        k = 0;
        l = 0;
        i1 = 0x7fffffff;
_L5:
        if(j < i) goto _L2; else goto _L1
_L1:
        int j2 = l;
_L6:
        String s;
        int j1;
        NumberFormatException numberformatexception;
        int k1;
        int l1;
        int i2;
        Point point1;
        if(k > 0 && j2 > 0)
            point1 = new Point(k, j2);
        else
            point1 = null;
        return point1;
_L2:
        s = as[j].trim();
        j1 = s.indexOf('x');
        if(j1 >= 0) goto _L4; else goto _L3
_L3:
        Log.w("CameraManager", (new StringBuilder("Bad preview-size: ")).append(s).toString());
_L7:
        j++;
          goto _L5
_L4:
        k1 = Integer.parseInt(s.substring(0, j1));
        l1 = Integer.parseInt(s.substring(j1 + 1));
        i2 = Math.abs(k1 - point.x) + Math.abs(l1 - point.y);
        if(i2 != 0)
            break MISSING_BLOCK_LABEL_206;
        k = k1;
        j2 = l1;
          goto _L6
        numberformatexception;
        Log.w("CameraManager", (new StringBuilder("Bad preview-size: ")).append(s).toString());
          goto _L7
        if(i2 < i1)
        {
            k = k1;
            l = l1;
            i1 = i2;
        }
          goto _L7
    }

    public static CameraManager get()
    {
        return cameraManager;
    }

    private static Point getCameraResolution(android.hardware.Camera.Parameters parameters, Point point)
    {
        String s = parameters.get("preview-size-values");
        if(s == null)
            s = parameters.get("preview-size-value");
        Point point1 = null;
        if(s != null)
        {
            Log.d("CameraManager", (new StringBuilder("preview-size-values parameter: ")).append(s).toString());
            point1 = findBestPreviewSizeValue(s, point);
        }
        if(point1 == null)
            point1 = new Point((point.x >> 3) << 3, (point.y >> 3) << 3);
        return point1;
    }

    private Point getScreenResolution()
    {
        if(screenResolution == null)
        {
            Display display = ((WindowManager)context.getSystemService("window")).getDefaultDisplay();
            screenResolution = new Point(display.getWidth(), display.getHeight());
        }
        return screenResolution;
    }

    public static void init(Context context1)
    {
        if(cameraManager == null)
            cameraManager = new CameraManager(context1);
    }

    private void setCameraParameters()
    {
        android.hardware.Camera.Parameters parameters = camera.getParameters();
        android.hardware.Camera.Size size = parameters.getPreviewSize();
        Log.v("CameraManager", (new StringBuilder("Default preview size: ")).append(size.width).append(", ").append(size.height).toString());
        previewFormat = parameters.getPreviewFormat();
        previewFormatString = parameters.get("preview-format");
        Log.v("CameraManager", (new StringBuilder("Default preview format: ")).append(previewFormat).toString());
        cameraResolution = getCameraResolution(parameters, screenResolution);
        Log.v("CameraManager", (new StringBuilder("Setting preview size: ")).append(cameraResolution.x).append(", ").append(cameraResolution.y).toString());
        parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
        parameters.set("flash-value", 2);
        parameters.set("flash-mode", "off");
        camera.setParameters(parameters);
    }

    public BaseLuminanceSource buildLuminanceSource(byte abyte0[], int i, int j)
    {
        Rect rect = getFramingRectInPreview();
        previewFormat;
        JVM INSTR tableswitch 16 17: default 32
    //                   16 80
    //                   17 80;
           goto _L1 _L2 _L2
_L1:
        Object obj;
        if(previewFormatString.equals("yuv422i-yuyv"))
            obj = new InterleavedYUV422LuminanceSource(abyte0, i, j, rect.left, rect.top, rect.width(), rect.height());
        else
        if(previewFormatString.equals("yuv420p"))
            obj = new PlanarYUVLuminanceSource(abyte0, i, j, rect.left, rect.top, rect.width(), rect.height());
        else
            throw new IllegalArgumentException((new StringBuilder("Unsupported picture format: ")).append(previewFormat).append('/').append(previewFormatString).toString());
_L4:
        return ((BaseLuminanceSource) (obj));
_L2:
        obj = new PlanarYUVLuminanceSource(abyte0, i, j, rect.left, rect.top, rect.width(), rect.height());
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void closeDriver()
    {
        if(camera != null)
        {
            camera.release();
            camera = null;
        }
    }

    public Point[] convertResultPoints(ResultPoint aresultpoint[])
    {
        Rect rect = getFramingRect();
        int i = aresultpoint.length;
        Point apoint[] = new Point[i];
        int j = 0;
        do
        {
            if(j >= i)
                return apoint;
            apoint[j] = new Point();
            apoint[j].x = rect.left + (int)(0.5F + aresultpoint[j].getX());
            apoint[j].y = rect.top + (int)(0.5F + aresultpoint[j].getY());
            j++;
        } while(true);
    }

    public Rect getFramingRect()
    {
        if(framingRect != null) goto _L2; else goto _L1
_L1:
        if(camera != null) goto _L4; else goto _L3
_L3:
        Rect rect = null;
_L6:
        return rect;
_L4:
        int i = (3 * screenResolution.x) / 4;
        int j;
        int k;
        int l;
        if(i < 240)
            i = 240;
        else
        if(i > 480)
            i = 480;
        j = (3 * screenResolution.y) / 4;
        if(j < 240)
            j = 240;
        else
        if(j > 360)
            j = 360;
        k = (screenResolution.x - i) / 2;
        l = (screenResolution.y - j) / 2;
        framingRect = new Rect(k, l, k + i, l + j);
        Log.v("CameraManager", (new StringBuilder("Calculated framing rect: ")).append(framingRect).toString());
_L2:
        rect = framingRect;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Rect getFramingRectInPreview()
    {
        if(framingRectInPreview == null)
        {
            Rect rect = new Rect(getFramingRect());
            rect.left = (rect.left * cameraResolution.x) / screenResolution.x;
            rect.right = (rect.right * cameraResolution.x) / screenResolution.x;
            rect.top = (rect.top * cameraResolution.y) / screenResolution.y;
            rect.bottom = (rect.bottom * cameraResolution.y) / screenResolution.y;
            framingRectInPreview = rect;
        }
        return framingRectInPreview;
    }

    public void openDriver(SurfaceHolder surfaceholder)
        throws IOException
    {
        if(camera == null)
        {
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceholder);
            if(!initialized)
            {
                initialized = true;
                getScreenResolution();
            }
            setCameraParameters();
        }
    }

    public void requestAutoFocus(Handler handler, int i)
    {
        if(camera != null && previewing)
        {
            autoFocusHandler = handler;
            autoFocusMessage = i;
            camera.autoFocus(autoFocusCallback);
        }
    }

    public void requestPreviewFrame(Handler handler, int i)
    {
        if(camera != null && previewing)
        {
            previewHandler = handler;
            previewMessage = i;
            if(useOneShotPreviewCallback)
                camera.setOneShotPreviewCallback(previewCallback);
            else
                camera.setPreviewCallback(previewCallback);
        }
    }

    public void startPreview()
    {
        if(camera != null && !previewing)
        {
            camera.startPreview();
            previewing = true;
        }
    }

    public void stopPreview()
    {
        if(camera != null && previewing)
        {
            if(!useOneShotPreviewCallback)
                camera.setPreviewCallback(null);
            camera.stopPreview();
            previewHandler = null;
            autoFocusHandler = null;
            previewing = false;
        }
    }

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final int MAX_FRAME_HEIGHT = 360;
    private static final int MAX_FRAME_WIDTH = 480;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MIN_FRAME_WIDTH = 240;
    private static final String TAG = "CameraManager";
    private static CameraManager cameraManager;
    private final android.hardware.Camera.AutoFocusCallback autoFocusCallback = new android.hardware.Camera.AutoFocusCallback() {

        public void onAutoFocus(boolean flag, Camera camera1)
        {
            if(autoFocusHandler != null)
            {
                Message message = autoFocusHandler.obtainMessage(autoFocusMessage, Boolean.valueOf(flag));
                autoFocusHandler.sendMessageDelayed(message, 1500L);
                autoFocusHandler = null;
            }
        }

        final CameraManager this$0;

            
            {
                this$0 = CameraManager.this;
                super();
            }
    }
;
    private Handler autoFocusHandler;
    private int autoFocusMessage;
    private Camera camera;
    private Point cameraResolution;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private final android.hardware.Camera.PreviewCallback previewCallback = new android.hardware.Camera.PreviewCallback() {

        public void onPreviewFrame(byte abyte0[], Camera camera1)
        {
            if(!useOneShotPreviewCallback)
                camera1.setPreviewCallback(null);
            if(previewHandler != null)
            {
                previewHandler.obtainMessage(previewMessage, cameraResolution.x, cameraResolution.y, abyte0).sendToTarget();
                previewHandler = null;
            }
        }

        final CameraManager this$0;

            
            {
                this$0 = CameraManager.this;
                super();
            }
    }
;
    private int previewFormat;
    private String previewFormatString;
    private Handler previewHandler;
    private int previewMessage;
    private boolean previewing;
    private Point screenResolution;
    private boolean useOneShotPreviewCallback;









}
