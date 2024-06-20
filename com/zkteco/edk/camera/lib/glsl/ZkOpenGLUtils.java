package com.zkteco.edk.camera.lib.glsl;

import android.opengl.GLES20;
import android.opengl.GLES30;
import com.zkteco.edk.camera.lib.util.LogUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class ZkOpenGLUtils {
    public static FloatBuffer createBuffer(float[] fArr) {
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        asFloatBuffer.put(fArr).position(0);
        return asFloatBuffer;
    }

    public static IntBuffer createBuffer(int[] iArr) {
        IntBuffer asIntBuffer = ByteBuffer.allocateDirect(iArr.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        asIntBuffer.put(iArr).position(0);
        return asIntBuffer;
    }

    public static ShortBuffer createBuffer(short[] sArr) {
        ShortBuffer asShortBuffer = ByteBuffer.allocateDirect(sArr.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        asShortBuffer.put(sArr).position(0);
        return asShortBuffer;
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader != 0) {
            GLES20.glShaderSource(glCreateShader, str);
            GLES20.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES30.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] != 0) {
                return glCreateShader;
            }
            LogUtils.e(GLES30.glGetShaderInfoLog(glCreateShader));
            GLES30.glDeleteShader(glCreateShader);
            throw new RuntimeException("Compile Shader Failed!" + GLES20.glGetError());
        }
        throw new RuntimeException("Create Shader Failed!" + GLES20.glGetError());
    }

    public static int linkProgram(int i, int i2) {
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, i);
            GLES20.glAttachShader(glCreateProgram, i2);
            GLES20.glLinkProgram(glCreateProgram);
            int[] iArr = new int[1];
            GLES30.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            LogUtils.v("Results of linking program:\n" + GLES20.glGetProgramInfoLog(glCreateProgram));
            if (iArr[0] != 0) {
                GLES20.glDeleteShader(i);
                GLES20.glDeleteShader(i2);
                return glCreateProgram;
            }
            GLES20.glDeleteProgram(glCreateProgram);
            LogUtils.e("Linking of program failed.");
            throw new RuntimeException("Linked Program Failed!" + GLES20.glGetError());
        }
        throw new RuntimeException("Create Program Failed!" + GLES20.glGetError());
    }

    public static void createOESTextureObject(int[] iArr) {
        if (iArr == null || iArr.length < 1) {
            throw new RuntimeException("create OES Texture Id length not be null");
        }
        GLES30.glGenTextures(1, iArr, 0);
        LogUtils.i(String.format("glGenTextures %s", new Object[]{Integer.valueOf(iArr[0])}));
        GLES30.glBindTexture(36197, iArr[0]);
        GLES30.glTexParameterf(36197, 10241, 9728.0f);
        GLES30.glTexParameterf(36197, 10240, 9729.0f);
        GLES30.glTexParameterf(36197, 10242, 33071.0f);
        GLES30.glTexParameterf(36197, 10243, 33071.0f);
    }

    public static void createTexture(int[] iArr) {
        GLES30.glGenTextures(1, iArr, 0);
        GLES30.glBindTexture(3553, iArr[0]);
        GLES30.glTexParameterf(3553, 10241, 9729.0f);
        GLES30.glTexParameterf(3553, 10240, 9729.0f);
        GLES30.glTexParameterf(3553, 10242, 33071.0f);
        GLES30.glTexParameterf(3553, 10243, 33071.0f);
        GLES30.glBindTexture(3553, 0);
    }

    public static void enableVertexAttributePointer(int i, int i2, int i3, int i4, FloatBuffer floatBuffer) {
        floatBuffer.position(i);
        GLES30.glVertexAttribPointer(i2, i3, 5126, false, i4, floatBuffer);
        GLES30.glEnableVertexAttribArray(i2);
        floatBuffer.position(0);
    }

    public static void enableAttributePointerVBO(int i, int i2, int i3, int i4) {
        GLES30.glVertexAttribPointer(i, i2, 5126, false, i3, i4);
        GLES30.glEnableVertexAttribArray(i);
    }

    public static void disableAttributePointer(int i) {
        GLES30.glDisableVertexAttribArray(i);
    }

    public static void bindArrayData(int[] iArr, float[] fArr, FloatBuffer floatBuffer) {
        floatBuffer.position(0);
        GLES20.glBindBuffer(34962, iArr[0]);
        GLES20.glBufferData(34962, fArr.length * 4, floatBuffer, 35044);
    }

    public static void bindElementArrayBuffer(int[] iArr, int[] iArr2, IntBuffer intBuffer) {
        intBuffer.position(0);
        GLES20.glBindBuffer(34963, iArr[0]);
        GLES20.glBufferData(34963, iArr2.length * 4, intBuffer, 35044);
    }
}
