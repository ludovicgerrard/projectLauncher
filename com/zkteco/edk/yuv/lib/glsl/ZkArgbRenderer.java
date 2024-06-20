package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ZkArgbRenderer implements GLSurfaceView.Renderer {
    private ZkArgbProgram mArgbProgram = new ZkArgbProgram();
    private final Context mContext;

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    public ZkArgbRenderer(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        ZkArgbProgram zkArgbProgram = this.mArgbProgram;
        if (zkArgbProgram != null) {
            zkArgbProgram.createProgram(this.mContext);
        }
    }

    public void onDrawFrame(GL10 gl10) {
        ZkArgbProgram zkArgbProgram = this.mArgbProgram;
        if (zkArgbProgram != null) {
            zkArgbProgram.draw();
        }
    }

    public void releaseResource() {
        ZkArgbProgram zkArgbProgram = this.mArgbProgram;
        if (zkArgbProgram != null) {
            zkArgbProgram.recycleProgram();
        }
        this.mArgbProgram = null;
    }

    public void addImageData(ZkArgbData zkArgbData) {
        ZkArgbProgram zkArgbProgram = this.mArgbProgram;
        if (zkArgbProgram != null) {
            zkArgbProgram.setImageData(zkArgbData);
        }
    }

    public void addImageData(byte[] bArr, int i, int i2) {
        ZkArgbData zkArgbData = new ZkArgbData();
        zkArgbData.setImageData(bArr);
        zkArgbData.setWidth(i);
        zkArgbData.setHeight(i2);
        ZkArgbProgram zkArgbProgram = this.mArgbProgram;
        if (zkArgbProgram != null) {
            zkArgbProgram.setImageData(zkArgbData);
        }
    }
}
