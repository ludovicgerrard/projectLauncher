package com.zkteco.android.core.interfaces;

public interface HubProtocolInterface {
    public static final String CONVERT_PUSH_FREE = "convert_push_free";
    public static final String CONVERT_PUSH_INIT = "convert_push_init";
    public static final String CONVERT_PUSH_RESET = "convert_push_reset";
    public static final String CONVERT_PUSH_SET_BYTEFIELD = "convert_push_set_bytefield";
    public static final String CONVERT_PUSH_SET_CON = "convert_push_set_condition";
    public static final String CONVERT_PUSH_SET_INTFIELD = "convert_push_set_intfield";
    public static final String CONVERT_PUSH_SET_STRFIELD = "convert_push_set_strfield";
    public static final String CONVERT_PUSH_SET_TABLENAME = "convert_push_set_tablename";
    public static final String CONVERT_STANDALONE_FREE = "convert_standalone_free";
    public static final String CONVERT_STANDALONE_INIT = "convert_standalone_init";
    public static final String CONVERT_STANDALONE_RESET = "convert_standalone_set_reset";
    public static final String CONVERT_STANDALONE_SET_CARNUMBER = "convert_standalone_set_carnumber";
    public static final String CONVERT_STANDALONE_SET_DOORID = "convert_standalone_set_doorid";
    public static final String CONVERT_STANDALONE_SET_EVENTTYPE = "convert_standalone_set_eventtype";
    public static final String CONVERT_STANDALONE_SET_LENGTH = "convert_standalone_set_length";
    public static final String CONVERT_STANDALONE_SET_RESERVE = "convert_standalone_set_reserve";
    public static final String CONVERT_STANDALONE_SET_RESULT = "convert_standalone_set_result";
    public static final String CONVERT_STANDALONE_SET_STATE = "convert_standalone_set_state";
    public static final String CONVERT_STANDALONE_SET_TIME = "convert_standalone_set_time";
    public static final String CONVERT_STANDALONE_SET_USERPIN = "convert_standalone_set_userpin";
    public static final String CONVERT_STANDALONE_SET_VERIFYTYPE = "convert_standalone_set_verifytype";
    public static final String CONVERT_STANDALONE_SET_WORKCODENUM = "convert_standalone_set_workcodenum";
    public static final String GET_PUSH_VINFO = "get_push_verinfo";
    public static final String GET_SERVER_CONNECT_STATE = "get_server_connect_state";
    public static final String GET_STANDALONE_VINFO = "get_standalone_verinfo";
    public static final String HUBSERVICE_CONNECT = "hubservice_connecthubserver";
    public static final String HUBSERVICE_INIT = "hubservice_init";
    public static final String HUBSERVICE_SEND = "hubservice_sendhub";
    public static final String INFOSCREEN_SEND_BIOPHOTO = "infoscreen_send_biophoto";

    void connectHubServer();

    void convertPushFree(long j);

    long convertPushInit();

    void convertPushReset(long j);

    void convertStandaloneFree(long j);

    long convertStandaloneInit();

    void convertStandaloneReset(long j);

    void convertStandaloneSetCarNumber(long j, String str);

    void convertStandaloneSetDoorID(long j, int i);

    void convertStandaloneSetEventType(long j, int i);

    void convertStandaloneSetLength(long j, int i);

    void convertStandaloneSetReserve(long j, int i);

    void convertStandaloneSetResult(long j, int i);

    void convertStandaloneSetState(long j, int i);

    void convertStandaloneSetTime(long j, String str);

    void convertStandaloneSetUserPIN(long j, String str);

    void convertStandaloneSetVerifyType(long j, int i);

    void convertStandaloneSetWorkCodeNum(long j, String str);

    String getPushVerInfo();

    String getServerConnectState();

    String getStandaloneVerInfo();

    void hubServiceInit();

    void sendBioPhoto(String str, String str2);

    void sendHubAction(int i, long j, String str);

    void setPushByteField(long j, String str, byte[] bArr, int i);

    void setPushCon(long j, String str);

    void setPushIntField(long j, String str, int i);

    void setPushStrField(long j, String str, String str2);

    void setPushTableName(long j, String str);
}
