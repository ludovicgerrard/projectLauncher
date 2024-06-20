package com.zkteco.edk.yuv.lib.glsl;

import android.content.Context;

public abstract class ZkBaseProgram implements IZkOpenGLESProgram {
    private Context mContext;
    private int mProgramId = -1;

    public int programId() {
        return this.mProgramId;
    }

    /* access modifiers changed from: protected */
    public void setProgramId(int i) {
        this.mProgramId = i;
    }
}
