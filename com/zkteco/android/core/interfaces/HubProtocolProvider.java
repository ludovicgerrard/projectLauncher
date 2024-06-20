package com.zkteco.android.core.interfaces;

import com.zkteco.android.core.library.Provider;

public class HubProtocolProvider extends AbstractProvider implements HubProtocolInterface {
    private HubProtocolProvider(Provider provider) {
        super(provider);
    }

    public static HubProtocolProvider getInstance(Provider provider) {
        return new HubProtocolProvider(provider);
    }

    public void hubServiceInit() {
        getProvider().invoke(HubProtocolInterface.HUBSERVICE_INIT, new Object[0]);
    }

    public void connectHubServer() {
        getProvider().invoke(HubProtocolInterface.HUBSERVICE_CONNECT, new Object[0]);
    }

    public void sendHubAction(int i, long j, String str) {
        getProvider().invoke(HubProtocolInterface.HUBSERVICE_SEND, Integer.valueOf(i), Long.valueOf(j), str);
    }

    public long convertPushInit() {
        return ((Long) getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_INIT, new Object[0])).longValue();
    }

    public void convertPushFree(long j) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_FREE, Long.valueOf(j));
    }

    public void setPushTableName(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_SET_TABLENAME, Long.valueOf(j), str);
    }

    public void setPushCon(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_SET_CON, Long.valueOf(j), str);
    }

    public void setPushIntField(long j, String str, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_SET_INTFIELD, Long.valueOf(j), str, Integer.valueOf(i));
    }

    public void setPushStrField(long j, String str, String str2) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_SET_STRFIELD, Long.valueOf(j), str, str2);
    }

    public void setPushByteField(long j, String str, byte[] bArr, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_SET_BYTEFIELD, Long.valueOf(j), str, bArr, Integer.valueOf(i));
    }

    public void convertPushReset(long j) {
        getProvider().invoke(HubProtocolInterface.CONVERT_PUSH_RESET, Long.valueOf(j));
    }

    public long convertStandaloneInit() {
        return ((Long) getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_INIT, new Object[0])).longValue();
    }

    public void convertStandaloneFree(long j) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_FREE, Long.valueOf(j));
    }

    public void convertStandaloneSetCarNumber(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_CARNUMBER, Long.valueOf(j), str);
    }

    public void convertStandaloneSetUserPIN(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_USERPIN, Long.valueOf(j), str);
    }

    public void convertStandaloneSetVerifyType(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_VERIFYTYPE, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetDoorID(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_DOORID, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetEventType(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_EVENTTYPE, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetState(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_STATE, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetTime(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_TIME, Long.valueOf(j), str);
    }

    public void convertStandaloneSetResult(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_RESULT, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetLength(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_LENGTH, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneSetWorkCodeNum(long j, String str) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_WORKCODENUM, Long.valueOf(j), str);
    }

    public void convertStandaloneSetReserve(long j, int i) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_SET_RESERVE, Long.valueOf(j), Integer.valueOf(i));
    }

    public void convertStandaloneReset(long j) {
        getProvider().invoke(HubProtocolInterface.CONVERT_STANDALONE_RESET, Long.valueOf(j));
    }

    public void sendBioPhoto(String str, String str2) {
        getProvider().invoke(HubProtocolInterface.INFOSCREEN_SEND_BIOPHOTO, str, str2);
    }

    public String getPushVerInfo() {
        return (String) getProvider().invoke(HubProtocolInterface.GET_PUSH_VINFO, new Object[0]);
    }

    public String getStandaloneVerInfo() {
        return (String) getProvider().invoke(HubProtocolInterface.GET_STANDALONE_VINFO, new Object[0]);
    }

    public String getServerConnectState() {
        return (String) getProvider().invoke(HubProtocolInterface.GET_SERVER_CONNECT_STATE, new Object[0]);
    }
}
