package com.zkteco.android.core.interfaces;

public interface UsbStorageInterface {
    public static final String CAN_READ = "can-read";
    public static final String CAN_WRITE = "can-write";
    public static final String GET_CAPACITY = "get-capacity";
    public static final String GET_CHUNKSIZE = "get-chunksize";
    public static final String GET_FREESPACE = "get-freespace";
    public static final String GET_OCCUSPACE = "get-occupiedspace";
    public static final String GET_PATH = "get-path";
    public static final String IS_CONNECTED = "is-connected";
    public static final String IS_HASUSB = "is-hasusb";

    Boolean canRead();

    Boolean canWrite();

    String getPath();

    long getUsbCapacity();

    long getUsbChunkSize();

    long getUsbFreeSpace();

    long getUsbOccupiedSpace();

    Boolean isConnected();

    Boolean isHasUDiskDev();
}
