package com.zkteco.edk.camera.lib;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import com.alibaba.fastjson.asm.Opcodes;
import com.zkteco.android.zkcore.view.util.Constant;
import com.zkteco.edk.camera.lib.glsl.ZkNv21Renderer;
import com.zkteco.edk.camera.lib.util.LogUtils;
import java.util.List;

public class ZkCameraView extends GLSurfaceView implements Camera.PreviewCallback {
    private Camera mCamera;
    private int mCameraAngle = 1;
    private byte[] mCameraBuffer;
    private int mCameraIndex = 0;
    private final Object mCameraLock = new Object();
    private int mCameraMirror = 1;
    private ZkCameraPreviewCallback mCameraPreviewCallback;
    private ZkCameraStateCallback mCameraStateCallback;
    private boolean mIsEnableDrawPreview = true;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private ZkNv21Renderer mRenderer;
    private int mResolution = 3;
    private byte[] mRotateImageCache;
    private SurfaceTexture mSurfaceTexture = new SurfaceTexture(36197);

    private int changeAngle(int i) {
        if (i == 2) {
            return 90;
        }
        if (i == 3) {
            return Opcodes.GETFIELD;
        }
        if (i != 4) {
            return 0;
        }
        return Constant.DEFAULT_START_ANGLE;
    }

    private boolean checkCameraResolution(int i, int i2, int i3) {
        if (i3 == 1) {
            return (i == 240 && i2 == 320) || (i == 320 && i2 == 240);
        }
        if (i3 == 2) {
            return (i == 480 && i2 == 640) || (i == 640 && i2 == 480);
        }
        if (i3 == 3) {
            return (i == 720 && i2 == 1280) || (i == 1280 && i2 == 720);
        }
        if (i3 == 4) {
            return (i == 1080 && i2 == 1920) || (i == 1920 && i2 == 1080);
        }
        if (i3 != 5) {
            return false;
        }
        return (i == 960 && i2 == 1280) || (i == 1280 && i2 == 960);
    }

    public ZkCameraView(Context context) {
        super(context);
        initCameraView(context, (AttributeSet) null);
    }

    public ZkCameraView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initCameraView(context, attributeSet);
    }

    public void setPreviewCallback(ZkCameraPreviewCallback zkCameraPreviewCallback) {
        this.mCameraPreviewCallback = zkCameraPreviewCallback;
    }

    public void setCameraStateCallback(ZkCameraStateCallback zkCameraStateCallback) {
        this.mCameraStateCallback = zkCameraStateCallback;
    }

    public void setErrorCallback(Camera.ErrorCallback errorCallback) {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.setErrorCallback(errorCallback);
        }
    }

    private void callbackCameraState(int i) {
        ZkCameraStateCallback zkCameraStateCallback = this.mCameraStateCallback;
        if (zkCameraStateCallback != null) {
            zkCameraStateCallback.onCameraStateChange(i);
        }
    }

    private void initCameraView(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ZkCameraView);
            this.mResolution = obtainStyledAttributes.getInteger(R.styleable.ZkCameraView_zk_camera_view_resolution, 4);
            this.mCameraIndex = obtainStyledAttributes.getInteger(R.styleable.ZkCameraView_zk_camera_index, 0);
            this.mCameraAngle = obtainStyledAttributes.getInteger(R.styleable.ZkCameraView_zk_camera_view_angle, 1);
            this.mCameraMirror = obtainStyledAttributes.getInteger(R.styleable.ZkCameraView_zk_camera_view_mirror, 1);
            obtainStyledAttributes.recycle();
        }
        getHolder().addCallback(this);
        setEGLContextClientVersion(3);
        this.mRenderer = new ZkNv21Renderer(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(-3);
        setZOrderOnTop(false);
        setKeepScreenOn(true);
        setRenderer(this.mRenderer);
        setRenderMode(0);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        LogUtils.i("surfaceCreated");
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        super.surfaceChanged(surfaceHolder, i, i2, i3);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        super.surfaceDestroyed(surfaceHolder);
        LogUtils.i("surfaceDestroyed");
    }

    public void openCamera() {
        try {
            synchronized (this.mCameraLock) {
                findCamera(this.mCameraIndex);
                if (this.mCamera != null) {
                    setCameraDisplayOrientation((Activity) getContext(), this.mCamera);
                    this.mCamera.setPreviewTexture(this.mSurfaceTexture);
                    updatePreview();
                } else {
                    LogUtils.e("openCamera error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeCamera() {
        try {
            if (this.mCamera != null) {
                synchronized (this.mCameraLock) {
                    if (this.mCamera != null) {
                        LogUtils.i("start close camera");
                        this.mCamera.stopPreview();
                        this.mCamera.setPreviewCallbackWithBuffer((Camera.PreviewCallback) null);
                        this.mCamera.setErrorCallback((Camera.ErrorCallback) null);
                        this.mCamera.setPreviewTexture((SurfaceTexture) null);
                        this.mCamera.addCallbackBuffer((byte[]) null);
                        this.mCamera.release();
                        this.mCamera = null;
                        callbackCameraState(-1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void findCamera(int r7) throws java.lang.RuntimeException {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mCameraLock     // Catch:{ Exception -> 0x003a }
            monitor-enter(r0)     // Catch:{ Exception -> 0x003a }
            int r1 = android.hardware.Camera.getNumberOfCameras()     // Catch:{ all -> 0x0037 }
            java.lang.String r2 = "devices camera size:%d"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0037 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0037 }
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x0037 }
            java.lang.String r2 = java.lang.String.format(r2, r3)     // Catch:{ all -> 0x0037 }
            com.zkteco.edk.camera.lib.util.LogUtils.i(r2)     // Catch:{ all -> 0x0037 }
            int r2 = r7 + 1
            if (r2 > r1) goto L_0x0026
            android.hardware.Camera r7 = android.hardware.Camera.open(r7)     // Catch:{ all -> 0x0037 }
            r6.mCamera = r7     // Catch:{ all -> 0x0037 }
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            return
        L_0x0026:
            android.hardware.Camera r7 = r6.mCamera     // Catch:{ all -> 0x0037 }
            if (r7 != 0) goto L_0x0035
            java.lang.String r7 = "Camera force open!"
            com.zkteco.edk.camera.lib.util.LogUtils.i(r7)     // Catch:{ all -> 0x0037 }
            android.hardware.Camera r7 = android.hardware.Camera.open(r5)     // Catch:{ all -> 0x0037 }
            r6.mCamera = r7     // Catch:{ all -> 0x0037 }
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            goto L_0x003e
        L_0x0037:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0037 }
            throw r7     // Catch:{ Exception -> 0x003a }
        L_0x003a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x003e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.camera.lib.ZkCameraView.findCamera(int):void");
    }

    private void setCameraDisplayOrientation(Activity activity, Camera camera) {
        LogUtils.i("setCameraDisplayOrientation() ");
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int i = 0;
        if (rotation != 0) {
            if (rotation == 1) {
                i = 90;
            } else if (rotation == 2) {
                i = Constant.DEFAULT_START_ANGLE;
            } else if (rotation == 3) {
                i = Opcodes.GETFIELD;
            }
        }
        LogUtils.w("setDisplayOrientation mDegrees:" + i);
        camera.setDisplayOrientation(i);
    }

    private void updatePreview() {
        if (this.mCamera != null) {
            synchronized (this.mCameraLock) {
                this.mCamera.stopPreview();
                initPreviewSize();
                initPreviewBuffer();
                this.mCamera.startPreview();
                callbackCameraState(0);
            }
        }
    }

    public void startPreview() {
        synchronized (this.mCameraLock) {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.startPreview();
            }
        }
    }

    public void stopPreview() {
        synchronized (this.mCameraLock) {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.stopPreview();
            }
        }
    }

    private void initPreviewSize() {
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size fitPreviewSize = getFitPreviewSize(parameters);
            this.mPreviewWidth = fitPreviewSize.width;
            this.mPreviewHeight = fitPreviewSize.height;
            parameters.setPreviewFormat(17);
            parameters.setPictureFormat(256);
            parameters.setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
            parameters.setPictureSize(this.mPreviewWidth, this.mPreviewHeight);
            LogUtils.i("initPreviewSize  mPreviewWidth: " + this.mPreviewWidth + ", mPreviewHeight: " + this.mPreviewHeight);
            this.mCamera.setParameters(parameters);
        }
    }

    private Camera.Size getFitPreviewSize(Camera.Parameters parameters) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        for (int i = 0; i < supportedPreviewSizes.size(); i++) {
            Camera.Size size = supportedPreviewSizes.get(i);
            LogUtils.i("SupportedPreviewSize, width: " + size.width + ", height: " + size.height);
            if (checkCameraResolution(size.width, size.height, this.mResolution)) {
                return size;
            }
        }
        return supportedPreviewSizes.get(supportedPreviewSizes.size() - 1);
    }

    private void initPreviewBuffer() {
        if (this.mCamera != null) {
            LogUtils.i("initPreviewBuffer() ");
            byte[] bArr = new byte[(((ImageFormat.getBitsPerPixel(17) * this.mPreviewWidth) * this.mPreviewHeight) / 8)];
            this.mCameraBuffer = bArr;
            this.mCamera.addCallbackBuffer(bArr);
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.setPreviewCallbackWithBuffer(this);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0065 A[Catch:{ Exception -> 0x006b }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPreviewFrame(byte[] r10, android.hardware.Camera r11) {
        /*
            r9 = this;
            android.hardware.Camera r0 = r9.mCamera
            if (r0 == 0) goto L_0x006f
            byte[] r1 = r9.mCameraBuffer
            r0.addCallbackBuffer(r1)
            int r0 = r9.mPreviewWidth
            if (r0 <= 0) goto L_0x006f
            int r1 = r9.mPreviewHeight
            if (r1 > 0) goto L_0x0012
            goto L_0x006f
        L_0x0012:
            int r1 = r1 * r0
            int r1 = r1 * 3
            int r1 = r1 / 2
            int r0 = r10.length
            if (r0 == r1) goto L_0x001b
            return
        L_0x001b:
            int r0 = r9.mCameraMirror     // Catch:{ Exception -> 0x006b }
            r2 = 1
            if (r0 == r2) goto L_0x0021
            goto L_0x0022
        L_0x0021:
            r2 = 0
        L_0x0022:
            r8 = r2
            byte[] r0 = r9.mRotateImageCache     // Catch:{ Exception -> 0x006b }
            if (r0 == 0) goto L_0x002a
            int r0 = r0.length     // Catch:{ Exception -> 0x006b }
            if (r0 == r1) goto L_0x002e
        L_0x002a:
            byte[] r0 = new byte[r1]     // Catch:{ Exception -> 0x006b }
            r9.mRotateImageCache = r0     // Catch:{ Exception -> 0x006b }
        L_0x002e:
            int r0 = r9.mCameraAngle     // Catch:{ Exception -> 0x006b }
            int r0 = r9.changeAngle(r0)     // Catch:{ Exception -> 0x006b }
            byte[] r4 = r9.mRotateImageCache     // Catch:{ Exception -> 0x006b }
            int r5 = r9.mPreviewWidth     // Catch:{ Exception -> 0x006b }
            int r6 = r9.mPreviewHeight     // Catch:{ Exception -> 0x006b }
            r3 = r10
            r7 = r0
            com.zkteco.edk.yuv.lib.ZkYuvUtils.nativeNV21Transform(r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x006b }
            r10 = 90
            if (r0 == r10) goto L_0x004d
            r10 = 270(0x10e, float:3.78E-43)
            if (r0 != r10) goto L_0x0048
            goto L_0x004d
        L_0x0048:
            int r10 = r9.mPreviewWidth     // Catch:{ Exception -> 0x006b }
            int r0 = r9.mPreviewHeight     // Catch:{ Exception -> 0x006b }
            goto L_0x0051
        L_0x004d:
            int r10 = r9.mPreviewHeight     // Catch:{ Exception -> 0x006b }
            int r0 = r9.mPreviewWidth     // Catch:{ Exception -> 0x006b }
        L_0x0051:
            com.zkteco.edk.camera.lib.glsl.ZkNv21Renderer r1 = r9.mRenderer     // Catch:{ Exception -> 0x006b }
            if (r1 == 0) goto L_0x0061
            boolean r2 = r9.mIsEnableDrawPreview     // Catch:{ Exception -> 0x006b }
            if (r2 == 0) goto L_0x0061
            byte[] r2 = r9.mRotateImageCache     // Catch:{ Exception -> 0x006b }
            r1.addNv21ImageData(r2, r10, r0)     // Catch:{ Exception -> 0x006b }
            r9.requestRender()     // Catch:{ Exception -> 0x006b }
        L_0x0061:
            com.zkteco.edk.camera.lib.ZkCameraPreviewCallback r1 = r9.mCameraPreviewCallback     // Catch:{ Exception -> 0x006b }
            if (r1 == 0) goto L_0x006f
            byte[] r2 = r9.mRotateImageCache     // Catch:{ Exception -> 0x006b }
            r1.onPreviewFrame(r2, r11, r10, r0)     // Catch:{ Exception -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r10 = move-exception
            r10.printStackTrace()
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.camera.lib.ZkCameraView.onPreviewFrame(byte[], android.hardware.Camera):void");
    }

    public synchronized void releaseResource() {
        if (this.mRenderer != null) {
            LogUtils.i("releaseResource() ");
            this.mRenderer.releaseResource();
            this.mRenderer = null;
        }
        this.mSurfaceTexture = null;
    }

    public void setCameraResolution(int i) {
        this.mResolution = i;
    }

    public void setCameraAngle(int i) {
        this.mCameraAngle = i;
    }

    public void setCameraMirror(int i) {
        this.mCameraMirror = i;
    }

    public void setCameraIndex(int i) {
        if (i >= 0) {
            this.mCameraIndex = i;
            return;
        }
        throw new RuntimeException("please set correct camera index!");
    }

    public void enableDrawPreview(boolean z) {
        this.mIsEnableDrawPreview = z;
    }
}
