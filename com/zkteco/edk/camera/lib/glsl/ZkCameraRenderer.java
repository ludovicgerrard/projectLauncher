package com.zkteco.edk.camera.lib.glsl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import com.zkteco.edk.camera.lib.util.LogUtils;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ZkCameraRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "ZkCameraRenderer";
    private final Context mContext;
    private WeakReference<GLSurfaceView> mGLSurfaceView;
    private OnInitListener mOnInitListener;
    private ZkCameraPreviewProgram mPreviewProgram = new ZkCameraPreviewProgram();
    private SurfaceTexture mSurfaceTexture;

    public interface OnInitListener {
        void onRendererInitFinish();
    }

    public ZkCameraRenderer(Context context, GLSurfaceView gLSurfaceView, OnInitListener onInitListener) {
        this.mGLSurfaceView = new WeakReference<>(gLSurfaceView);
        this.mContext = context.getApplicationContext();
        this.mOnInitListener = onInitListener;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        ZkCameraPreviewProgram zkCameraPreviewProgram = this.mPreviewProgram;
        if (zkCameraPreviewProgram != null) {
            zkCameraPreviewProgram.createProgram(this.mContext);
            SurfaceTexture surfaceTexture = new SurfaceTexture(this.mPreviewProgram.getOESTextureId());
            this.mSurfaceTexture = surfaceTexture;
            surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    ZkCameraRenderer.this.lambda$onSurfaceCreated$0$ZkCameraRenderer(surfaceTexture);
                }
            });
        }
        LogUtils.d(TAG, "ZkCameraRenderer onSurfaceCreated");
        OnInitListener onInitListener = this.mOnInitListener;
        if (onInitListener != null) {
            onInitListener.onRendererInitFinish();
        }
    }

    public /* synthetic */ void lambda$onSurfaceCreated$0$ZkCameraRenderer(SurfaceTexture surfaceTexture) {
        WeakReference<GLSurfaceView> weakReference = this.mGLSurfaceView;
        if (weakReference != null && weakReference.get() != null) {
            ((GLSurfaceView) this.mGLSurfaceView.get()).requestRender();
        }
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        LogUtils.d(TAG, "screen size width: " + i + " ; height: " + i2);
    }

    public void onDrawFrame(GL10 gl10) {
        if (this.mPreviewProgram != null) {
            SurfaceTexture surfaceTexture = this.mSurfaceTexture;
            if (surfaceTexture != null) {
                surfaceTexture.updateTexImage();
                this.mSurfaceTexture.getTransformMatrix(this.mPreviewProgram.getTransformMatrix());
            }
            this.mPreviewProgram.draw();
        }
    }

    public void releaseResource() {
        ZkCameraPreviewProgram zkCameraPreviewProgram = this.mPreviewProgram;
        if (zkCameraPreviewProgram != null) {
            zkCameraPreviewProgram.recycleProgram();
        }
        this.mPreviewProgram = null;
        SurfaceTexture surfaceTexture = this.mSurfaceTexture;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.mSurfaceTexture = null;
        }
        this.mOnInitListener = null;
    }

    public void setCameraAngle(int i) {
        ZkCameraPreviewProgram zkCameraPreviewProgram = this.mPreviewProgram;
        if (zkCameraPreviewProgram != null) {
            zkCameraPreviewProgram.setCameraAngle(i);
        }
    }

    public void setCameraMirror(int i) {
        ZkCameraPreviewProgram zkCameraPreviewProgram = this.mPreviewProgram;
        if (zkCameraPreviewProgram != null) {
            zkCameraPreviewProgram.setCameraMirror(i);
        }
    }
}
