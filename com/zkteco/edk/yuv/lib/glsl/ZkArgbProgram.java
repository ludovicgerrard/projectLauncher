package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import com.zkteco.edk.yuv.lib.utils.AssetsUtils;
import com.zkteco.edk.yuv.lib.utils.ZkOpenGLUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ZkArgbProgram extends ZkBaseProgram {
    private static final int[] INDEX = {0, 1, 2, 0, 2, 3};
    private static final int POSITION_LOCATION = 0;
    private static final int TEXTURE_LOCATION = 1;
    private static final float[] VERTEX = {-1.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f};
    private final int[] mEBOId = new int[1];
    private ByteBuffer mImageBuffer;
    private final LinkedBlockingQueue<ZkArgbData> mImageQueue = new LinkedBlockingQueue<>(1);
    private final float[] mMatrix = new float[16];
    private int mMatrixLocation;
    private final int[] mTextureId = {0};
    private int mTextureUniform = -1;
    private final int[] mVAOId = new int[1];
    private final int[] mVBOId = new int[1];

    public void createProgram(Context context) {
        Context context2 = context;
        int linkProgram = ZkOpenGLUtils.linkProgram(ZkOpenGLUtils.loadShader(35633, AssetsUtils.getFromAssets(context2, "glsl/zk_argb_draw.vs")), ZkOpenGLUtils.loadShader(35632, AssetsUtils.getFromAssets(context2, "glsl/zk_argb_draw.fs")));
        setProgramId(linkProgram);
        GLES30.glUseProgram(linkProgram);
        this.mMatrixLocation = GLES30.glGetUniformLocation(linkProgram, "uMatrix");
        this.mTextureUniform = GLES30.glGetUniformLocation(linkProgram, "uTexture");
        float[] fArr = VERTEX;
        FloatBuffer createBuffer = ZkOpenGLUtils.createBuffer(fArr);
        int[] iArr = INDEX;
        IntBuffer createBuffer2 = ZkOpenGLUtils.createBuffer(iArr);
        GLES30.glGenVertexArrays(1, this.mVAOId, 0);
        GLES30.glGenBuffers(1, this.mVBOId, 0);
        GLES30.glGenBuffers(1, this.mEBOId, 0);
        GLES30.glBindVertexArray(this.mVAOId[0]);
        ZkOpenGLUtils.bindArrayData(this.mVBOId, fArr, createBuffer);
        ZkOpenGLUtils.bindElementArrayBuffer(this.mEBOId, iArr, createBuffer2);
        GLES30.glVertexAttribPointer(0, 3, 5126, false, 20, 0);
        GLES20.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1, 2, 5126, false, 20, 12);
        GLES20.glEnableVertexAttribArray(1);
        ZkOpenGLUtils.createTexture(this.mTextureId);
    }

    public void recycleProgram() {
        GLES30.glDeleteProgram(programId());
    }

    public void draw() {
        ZkArgbData zkArgbData;
        try {
            zkArgbData = this.mImageQueue.poll(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            zkArgbData = null;
        }
        if (zkArgbData != null) {
            int displayOrientation = zkArgbData.getDisplayOrientation();
            boolean isFlipX = zkArgbData.isFlipX();
            boolean isFlipY = zkArgbData.isFlipY();
            byte[] imageData = zkArgbData.getImageData();
            if (this.mImageBuffer == null) {
                this.mImageBuffer = ByteBuffer.allocate(zkArgbData.getImageLength());
            }
            this.mImageBuffer.clear();
            this.mImageBuffer.put(imageData, 0, zkArgbData.getImageLength());
            this.mImageBuffer.position(0);
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
            GLES30.glUseProgram(programId());
            Matrix.setRotateM(this.mMatrix, 0, (float) displayOrientation, 0.0f, 0.0f, 1.0f);
            if (isFlipX || isFlipY) {
                float[] fArr = this.mMatrix;
                float f = -1.0f;
                float f2 = isFlipX ? -1.0f : 1.0f;
                if (!isFlipY) {
                    f = 1.0f;
                }
                Matrix.scaleM(fArr, 0, f2, f, 1.0f);
            }
            GLES30.glUniformMatrix4fv(this.mMatrixLocation, 1, false, this.mMatrix, 0);
            GLES30.glBindTexture(3553, this.mTextureId[0]);
            GLES30.glTexImage2D(3553, 0, 6408, zkArgbData.getWidth(), zkArgbData.getHeight(), 0, 6408, 5121, this.mImageBuffer);
            GLES30.glActiveTexture(33984);
            GLES30.glBindTexture(3553, this.mTextureId[0]);
            GLES30.glUniform1i(this.mTextureUniform, 0);
            GLES30.glBindVertexArray(this.mVAOId[0]);
            GLES30.glDrawElements(4, 6, 5125, 0);
        }
    }

    public void setImageData(ZkArgbData zkArgbData) {
        this.mImageQueue.offer(zkArgbData);
    }
}
