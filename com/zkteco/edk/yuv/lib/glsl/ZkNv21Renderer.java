package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ZkNv21Renderer implements GLSurfaceView.Renderer {
    private final Context mContext;
    private ZkNv21Program mNv21Program = new ZkNv21Program();

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    public ZkNv21Renderer(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        ZkNv21Program zkNv21Program = this.mNv21Program;
        if (zkNv21Program != null) {
            zkNv21Program.createProgram(this.mContext);
        }
    }

    public void onDrawFrame(GL10 gl10) {
        ZkNv21Program zkNv21Program = this.mNv21Program;
        if (zkNv21Program != null) {
            zkNv21Program.draw();
        }
    }

    public void releaseResource() {
        ZkNv21Program zkNv21Program = this.mNv21Program;
        if (zkNv21Program != null) {
            zkNv21Program.recycleProgram();
        }
        this.mNv21Program = null;
    }

    public void addNv21ImageData(ZkYuvData zkYuvData) {
        ZkNv21Program zkNv21Program = this.mNv21Program;
        if (zkNv21Program != null) {
            zkNv21Program.setYuvData(zkYuvData);
        }
    }

    public void addNv21ImageData(byte[] bArr, int i, int i2) {
        ZkYuvData zkYuvData = new ZkYuvData(bArr, i, i2);
        ZkNv21Program zkNv21Program = this.mNv21Program;
        if (zkNv21Program != null) {
            zkNv21Program.setYuvData(zkYuvData);
        }
    }
}
