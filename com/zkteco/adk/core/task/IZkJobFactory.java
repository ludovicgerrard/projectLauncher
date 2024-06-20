package com.zkteco.adk.core.task;

public class IZkJobFactory {
    public IZkJob create(String str) {
        if (str == null) {
            throw new IllegalArgumentException("id is null!");
        } else if (ZkJobType.BASIC.equals(str)) {
            return new ZkBasicJob();
        } else {
            if (ZkJobType.LOOP.equals(str)) {
                return new ZkLoopJob();
            }
            return null;
        }
    }
}
