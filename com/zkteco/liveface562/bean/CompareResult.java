package com.zkteco.liveface562.bean;

public class CompareResult {
    public float compareThreshold;
    public float livenessScore1;
    public float livenessScore2;
    public float livenessThreshold;
    public int result;
    public float score;

    public CompareResult(int i, float f, float f2, float f3, float f4, float f5) {
        this.result = i;
        this.score = f;
        this.compareThreshold = f2;
        this.livenessThreshold = f3;
        this.livenessScore1 = f4;
        this.livenessScore2 = f5;
    }

    public String toString() {
        return "ZkCompareResult{result=" + this.result + ", score=" + this.score + ", compareThreshold=" + this.compareThreshold + '}';
    }
}
