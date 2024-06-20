package com.zkteco.edk.camera.lib.glsl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;
import com.zkteco.edk.camera.lib.ZkYuvData;
import com.zkteco.edk.camera.lib.util.AssetsUtils;
import com.zkteco.edk.camera.lib.util.MatrixHelper;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ZkNv21Program extends ZkBaseProgram {
    private static final int[] INDEX = {0, 1, 2, 0, 2, 3};
    private static final int POSITION_LOCATION = 0;
    private static final int TEXTURE_LOCATION = 1;
    private static final float[] VERTEX = {-1.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f};
    private IntBuffer drawIndexArray;
    private int[] mEBOId = new int[1];
    private final float[] mMatrix = new float[16];
    private final LinkedBlockingQueue<ZkYuvData> mNv21ImageQueue = new LinkedBlockingQueue<>(1);
    private int mProgramID;
    private final int[] mUVTextureId = {0};
    private int[] mVAOId = new int[1];
    private int[] mVBOId = new int[1];
    private final int[] mYTextureId = {0};
    private int sampleUVTextureUniform = -1;
    private int sampleYTextureUniform = -1;
    private int uMatrixLocation;
    private ByteBuffer uvBuffer;
    private FloatBuffer vertexArray;
    private ByteBuffer yBuffer;

    public void createProgram(Context context) {
        Context context2 = context;
        int linkProgram = ZkOpenGLUtils.linkProgram(ZkOpenGLUtils.loadShader(35633, AssetsUtils.getFromAssets(context2, "glsl/zk_nv21_draw.vs")), ZkOpenGLUtils.loadShader(35632, AssetsUtils.getFromAssets(context2, "glsl/zk_nv21_draw.fs")));
        this.mProgramID = linkProgram;
        GLES30.glUseProgram(linkProgram);
        this.uMatrixLocation = GLES30.glGetUniformLocation(this.mProgramID, "uMatrix");
        this.sampleYTextureUniform = GLES30.glGetUniformLocation(this.mProgramID, "uYTexture");
        this.sampleUVTextureUniform = GLES30.glGetUniformLocation(this.mProgramID, "uUVTexture");
        float[] fArr = VERTEX;
        this.vertexArray = ZkOpenGLUtils.createBuffer(fArr);
        int[] iArr = INDEX;
        this.drawIndexArray = ZkOpenGLUtils.createBuffer(iArr);
        GLES30.glGenVertexArrays(1, this.mVAOId, 0);
        GLES30.glGenBuffers(1, this.mVBOId, 0);
        GLES30.glGenBuffers(1, this.mEBOId, 0);
        GLES30.glBindVertexArray(this.mVAOId[0]);
        ZkOpenGLUtils.bindArrayData(this.mVBOId, fArr, this.vertexArray);
        ZkOpenGLUtils.bindElementArrayBuffer(this.mEBOId, iArr, this.drawIndexArray);
        GLES30.glVertexAttribPointer(0, 3, 5126, false, 20, 0);
        GLES20.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1, 2, 5126, false, 20, 12);
        GLES20.glEnableVertexAttribArray(1);
        ZkOpenGLUtils.createTexture(this.mYTextureId);
        ZkOpenGLUtils.createTexture(this.mUVTextureId);
    }

    public void recycleProgram() {
        GLES30.glDeleteProgram(this.mProgramID);
    }

    public void draw() {
        ZkYuvData zkYuvData;
        try {
            zkYuvData = this.mNv21ImageQueue.poll(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            zkYuvData = null;
        }
        if (zkYuvData != null) {
            int displayOrientation = zkYuvData.getDisplayOrientation();
            boolean isFlipX = zkYuvData.isFlipX();
            boolean isFlipY = zkYuvData.isFlipY();
            byte[] yuv = zkYuvData.getYuv();
            if (this.yBuffer == null) {
                this.yBuffer = ByteBuffer.allocate(zkYuvData.getYLength());
            }
            this.yBuffer.clear();
            this.yBuffer.put(yuv, 0, zkYuvData.getYLength());
            this.yBuffer.position(0);
            if (this.uvBuffer == null) {
                this.uvBuffer = ByteBuffer.allocate(zkYuvData.getUVLength());
            }
            this.uvBuffer.clear();
            this.uvBuffer.put(yuv, zkYuvData.getYLength(), zkYuvData.getUVLength());
            this.uvBuffer.position(0);
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
            GLES30.glUseProgram(this.mProgramID);
            Matrix.setRotateM(this.mMatrix, 0, (float) displayOrientation, 0.0f, 0.0f, 1.0f);
            MatrixHelper.flip(this.mMatrix, isFlipX, isFlipY);
            GLES30.glUniformMatrix4fv(this.uMatrixLocation, 1, false, this.mMatrix, 0);
            GLES30.glBindTexture(3553, this.mYTextureId[0]);
            GLES30.glTexImage2D(3553, 0, 6409, zkYuvData.getWidth(), zkYuvData.getHeight(), 0, 6409, 5121, this.yBuffer);
            GLES30.glBindTexture(3553, this.mUVTextureId[0]);
            GLES30.glTexImage2D(3553, 0, 6410, zkYuvData.getWidth() / 2, zkYuvData.getHeight() / 2, 0, 6410, 5121, this.uvBuffer);
            GLES30.glActiveTexture(33984);
            GLES30.glBindTexture(3553, this.mYTextureId[0]);
            GLES30.glUniform1i(this.sampleYTextureUniform, 0);
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(3553, this.mUVTextureId[0]);
            GLES30.glUniform1i(this.sampleUVTextureUniform, 1);
            GLES30.glBindVertexArray(this.mVAOId[0]);
            GLES30.glDrawElements(4, 6, 5125, 0);
        }
    }

    public void setYuvData(ZkYuvData zkYuvData) {
        this.mNv21ImageQueue.offer(zkYuvData);
    }
}
