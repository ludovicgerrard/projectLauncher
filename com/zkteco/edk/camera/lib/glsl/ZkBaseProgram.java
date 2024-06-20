package com.zkteco.edk.camera.lib.glsl;

import android.content.Context;
import com.zkteco.edk.camera.lib.IZkOpenGLESProgram;

public abstract class ZkBaseProgram implements IZkOpenGLESProgram {
    private Context mContext;
    private int mProgramId = -1;

    public int programId() {
        return this.mProgramId;
    }
}
