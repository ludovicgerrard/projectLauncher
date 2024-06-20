package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class ZkArgbView extends GLSurfaceView {
    private ZkArgbRenderer mRenderer;

    public ZkArgbView(Context context) {
        super(context);
        initView(context);
    }

    public ZkArgbView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        getHolder().addCallback(this);
        setEGLContextClientVersion(3);
        this.mRenderer = new ZkArgbRenderer(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(-3);
        setZOrderOnTop(false);
        setRenderer(this.mRenderer);
        setRenderMode(0);
    }

    public void draw(byte[] bArr, int i, int i2) {
        ZkArgbRenderer zkArgbRenderer;
        if (bArr != null && (zkArgbRenderer = this.mRenderer) != null && bArr.length == i2 * i * 4) {
            zkArgbRenderer.addImageData(bArr, i, i2);
            requestRender();
        }
    }

    public void release() {
        ZkArgbRenderer zkArgbRenderer = this.mRenderer;
        if (zkArgbRenderer != null) {
            zkArgbRenderer.releaseResource();
        }
    }
}
