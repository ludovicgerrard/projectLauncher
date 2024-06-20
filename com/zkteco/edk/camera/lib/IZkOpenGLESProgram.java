package com.zkteco.edk.camera.lib;

import android.content.Context;

public interface IZkOpenGLESProgram {
    void createProgram(Context context);

    void draw();

    int programId();

    void recycleProgram();
}
