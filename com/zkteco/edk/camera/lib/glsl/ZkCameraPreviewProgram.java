package com.zkteco.edk.camera.lib.glsl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.Matrix;
import com.alibaba.fastjson.asm.Opcodes;
import com.zkteco.android.zkcore.view.util.Constant;
import com.zkteco.edk.camera.lib.LogTag;
import com.zkteco.edk.camera.lib.util.AssetsUtils;
import com.zkteco.edk.camera.lib.util.LogUtils;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class ZkCameraPreviewProgram extends ZkBaseProgram {
    public static final int A_POSITION_LOC = 0;
    public static final int A_TEXTURE_COORDINATE_LOC = 1;
    private static final short[] INDEX_DATA = {0, 1, 2, 0, 2, 3};
    public static final String MODEL_UNIFORM = "model";
    public static final String PROJECTION_UNIFORM = "projection";
    public static final String TEXTURE_MATRIX_UNIFORM = "uTextureMatrix";
    public static final String TEXTURE_SAMPLER_UNIFORM = "uTextureSampler";
    private static final float[] VERTEX_DATA = {-1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, -1.0f, -1.0f, 1.0f, 0.0f};
    public static final String VIEW_UNIFORM = "view";
    private final int[] mEBOIds = new int[1];
    private ShortBuffer mIndexDataBuffer;
    private final int[] mOESTextureId = new int[1];
    private int mShaderProgram = -1;
    private int mTextureAngle;
    private int mTextureMirror;
    private final int[] mVAOIds = new int[1];
    private final int[] mVBOIds = new int[1];
    private FloatBuffer mVertexDataBuffer;
    private final float[] modelPositionMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] projectionPositionMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] transformMatrix = new float[16];
    private int uModelLocation = -1;
    private int uProjectionLocation = -1;
    private int uTextureMatrixLocation = -1;
    private int uTextureSamplerLocation = -1;
    private int uViewLocation = -1;
    private final float[] viewPositionMatrix = new float[16];

    public void createProgram(Context context) {
        Context context2 = context;
        float[] fArr = VERTEX_DATA;
        this.mVertexDataBuffer = ZkOpenGLUtils.createBuffer(fArr);
        short[] sArr = INDEX_DATA;
        this.mIndexDataBuffer = ZkOpenGLUtils.createBuffer(sArr);
        int linkProgram = ZkOpenGLUtils.linkProgram(ZkOpenGLUtils.loadShader(35633, AssetsUtils.getFromAssets(context2, "glsl/zk_camera.vs")), ZkOpenGLUtils.loadShader(35632, AssetsUtils.getFromAssets(context2, "glsl/zk_camera.fs")));
        this.mShaderProgram = linkProgram;
        LogUtils.i(String.format("linkProgram %s", new Object[]{Integer.valueOf(linkProgram)}));
        this.uTextureMatrixLocation = GLES30.glGetUniformLocation(this.mShaderProgram, TEXTURE_MATRIX_UNIFORM);
        this.uTextureSamplerLocation = GLES30.glGetUniformLocation(this.mShaderProgram, TEXTURE_SAMPLER_UNIFORM);
        this.uViewLocation = GLES30.glGetUniformLocation(this.mShaderProgram, VIEW_UNIFORM);
        this.uModelLocation = GLES30.glGetUniformLocation(this.mShaderProgram, MODEL_UNIFORM);
        this.uProjectionLocation = GLES30.glGetUniformLocation(this.mShaderProgram, PROJECTION_UNIFORM);
        GLES30.glGenVertexArrays(1, this.mVAOIds, 0);
        GLES30.glGenBuffers(1, this.mVBOIds, 0);
        GLES30.glGenBuffers(1, this.mEBOIds, 0);
        GLES30.glBindVertexArray(this.mVAOIds[0]);
        GLES30.glBindBuffer(34962, this.mVBOIds[0]);
        this.mVertexDataBuffer.position(0);
        GLES30.glBufferData(34962, fArr.length * 4, this.mVertexDataBuffer, 35044);
        GLES30.glBindBuffer(34963, this.mEBOIds[0]);
        this.mIndexDataBuffer.position(0);
        GLES30.glBufferData(34963, sArr.length * 2, this.mIndexDataBuffer, 35044);
        GLES30.glVertexAttribPointer(0, 2, 5126, false, 16, 0);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1, 2, 5126, false, 16, 8);
        GLES30.glEnableVertexAttribArray(1);
        ZkOpenGLUtils.createOESTextureObject(this.mOESTextureId);
        LogUtils.i(LogTag.PREVIEW_PROGRAM, "mOESTextureId:" + this.mOESTextureId[0]);
        GLES30.glBindBuffer(34962, 0);
        GLES30.glBindVertexArray(0);
    }

    public void recycleProgram() {
        GLES30.glDeleteTextures(1, this.mOESTextureId, 0);
        GLES30.glDeleteProgram(this.mShaderProgram);
        this.mVertexDataBuffer = null;
        this.mIndexDataBuffer = null;
        this.mOESTextureId[0] = 0;
    }

    public void draw() {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glUseProgram(this.mShaderProgram);
        GLES30.glBindVertexArray(this.mVAOIds[0]);
        GLES30.glActiveTexture(33984);
        GLES30.glBindTexture(36197, this.mOESTextureId[0]);
        GLES30.glUniform1i(this.uTextureSamplerLocation, 0);
        GLES30.glUniformMatrix4fv(this.uTextureMatrixLocation, 1, false, this.transformMatrix, 0);
        Matrix.setLookAtM(this.viewPositionMatrix, 0, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f);
        Matrix.rotateM(this.viewPositionMatrix, 0, (float) this.mTextureAngle, 0.0f, 0.0f, 1.0f);
        GLES30.glUniformMatrix4fv(this.uViewLocation, 1, false, this.viewPositionMatrix, 0);
        if (this.mTextureMirror == 2) {
            this.modelPositionMatrix[5] = -1.0f;
        } else {
            this.modelPositionMatrix[5] = 1.0f;
        }
        GLES30.glUniformMatrix4fv(this.uModelLocation, 1, false, this.modelPositionMatrix, 0);
        GLES30.glUniformMatrix4fv(this.uProjectionLocation, 1, false, this.projectionPositionMatrix, 0);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glDrawElements(4, INDEX_DATA.length, 5123, 0);
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
        GLES30.glBindVertexArray(0);
    }

    public int getOESTextureId() {
        return this.mOESTextureId[0];
    }

    public void setCameraAngle(int i) {
        if (i == 2) {
            this.mTextureAngle = Constant.DEFAULT_START_ANGLE;
        } else if (i == 3) {
            this.mTextureAngle = Opcodes.GETFIELD;
        } else if (i != 4) {
            this.mTextureAngle = Constant.DEFAULT_SWEEP_ANGLE;
        } else {
            this.mTextureAngle = 90;
        }
    }

    public void setCameraMirror(int i) {
        this.mTextureMirror = i;
    }

    public float[] getTransformMatrix() {
        return this.transformMatrix;
    }
}
