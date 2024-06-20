package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;

public interface IZkOpenGLESProgram {
    void createProgram(Context context);

    void draw();

    int programId();

    void recycleProgram();
}
