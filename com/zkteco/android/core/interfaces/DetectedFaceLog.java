package com.zkteco.android.core.interfaces;

public class DetectedFaceLog {
    private static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    public static final String TABLE_NAME = "detected_face_log";
    private String algorithmId;
    private int id;
    private String image;
    private long timestamp;

    public String getAlgorithmId() {
        return this.algorithmId;
    }

    public void setAlgorithmId(String str) {
        this.algorithmId = str;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getImage() {
        return this.image;
    }

    public String toString() {
        return "DetectedFaceLog{id=" + this.id + ", algorithmId=" + this.algorithmId + ", timestamp='" + this.timestamp + '\'' + ", image='" + this.image + '\'' + '}';
    }
}
