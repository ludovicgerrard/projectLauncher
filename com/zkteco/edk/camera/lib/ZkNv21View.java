package com.zkteco.edk.camera.lib;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.zkteco.edk.camera.lib.glsl.ZkNv21Renderer;

public class ZkNv21View extends GLSurfaceView {
    private ZkNv21Renderer mRenderer;

    public ZkNv21View(Context context) {
        super(context);
        initView(context);
    }

    public ZkNv21View(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        getHolder().addCallback(this);
        setEGLContextClientVersion(3);
        this.mRenderer = new ZkNv21Renderer(context);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(-3);
        setZOrderOnTop(false);
        setRenderer(this.mRenderer);
        setRenderMode(0);
    }

    public void draw(byte[] bArr, int i, int i2) {
        ZkNv21Renderer zkNv21Renderer;
        if (bArr != null && (zkNv21Renderer = this.mRenderer) != null && bArr.length == ((i2 * i) * 3) / 2) {
            zkNv21Renderer.addNv21ImageData(bArr, i, i2);
            requestRender();
        }
    }

    public void release() {
        ZkNv21Renderer zkNv21Renderer = this.mRenderer;
        if (zkNv21Renderer != null) {
            zkNv21Renderer.releaseResource();
        }
    }
}
