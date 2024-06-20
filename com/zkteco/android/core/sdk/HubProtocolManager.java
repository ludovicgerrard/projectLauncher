package com.zkteco.android.core.sdk;

import android.content.Context;
import com.zkteco.android.core.interfaces.HubProtocolInterface;
import com.zkteco.android.core.interfaces.HubProtocolProvider;
import com.zkteco.android.core.library.CoreProvider;

public class HubProtocolManager implements HubProtocolInterface {
    private HubProtocolProvider provider;

    public HubProtocolManager(Context context) {
        this.provider = HubProtocolProvider.getInstance(new CoreProvider(context));
    }

    public void hubServiceInit() {
        this.provider.hubServiceInit();
    }

    public void connectHubServer() {
        this.provider.connectHubServer();
    }

    public void sendHubAction(int i, long j, String str) {
        this.provider.sendHubAction(i, j, str);
    }

    public long convertPushInit() {
        return this.provider.convertPushInit();
    }

    public void convertPushFree(long j) {
        this.provider.convertPushFree(j);
    }

    public void setPushTableName(long j, String str) {
        this.provider.setPushTableName(j, str);
    }

    public void setPushCon(long j, String str) {
        this.provider.setPushCon(j, str);
    }

    public void setPushIntField(long j, String str, int i) {
        this.provider.setPushIntField(j, str, i);
    }

    public void setPushStrField(long j, String str, String str2) {
        this.provider.setPushStrField(j, str, str2);
    }

    public void setPushByteField(long j, String str, byte[] bArr, int i) {
        this.provider.setPushByteField(j, str, bArr, i);
    }

    public void convertPushReset(long j) {
        this.provider.convertPushReset(j);
    }

    public long convertStandaloneInit() {
        return this.provider.convertStandaloneInit();
    }

    public void convertStandaloneFree(long j) {
        this.provider.convertStandaloneFree(j);
    }

    public void convertStandaloneSetCarNumber(long j, String str) {
        this.provider.convertStandaloneSetCarNumber(j, str);
    }

    public void convertStandaloneSetUserPIN(long j, String str) {
        this.provider.convertStandaloneSetUserPIN(j, str);
    }

    public void convertStandaloneSetVerifyType(long j, int i) {
        this.provider.convertStandaloneSetVerifyType(j, i);
    }

    public void convertStandaloneSetDoorID(long j, int i) {
        this.provider.convertStandaloneSetDoorID(j, i);
    }

    public void convertStandaloneSetEventType(long j, int i) {
        this.provider.convertStandaloneSetEventType(j, i);
    }

    public void convertStandaloneSetState(long j, int i) {
        this.provider.convertStandaloneSetState(j, i);
    }

    public void convertStandaloneSetTime(long j, String str) {
        this.provider.convertStandaloneSetTime(j, str);
    }

    public void convertStandaloneSetResult(long j, int i) {
        this.provider.convertStandaloneSetResult(j, i);
    }

    public void convertStandaloneSetLength(long j, int i) {
        this.provider.convertStandaloneSetLength(j, i);
    }

    public void convertStandaloneSetWorkCodeNum(long j, String str) {
        this.provider.convertStandaloneSetWorkCodeNum(j, str);
    }

    public void convertStandaloneSetReserve(long j, int i) {
        this.provider.convertStandaloneSetReserve(j, i);
    }

    public void convertStandaloneReset(long j) {
        this.provider.convertStandaloneReset(j);
    }

    public void sendBioPhoto(String str, String str2) {
        this.provider.sendBioPhoto(str, str2);
    }

    public String getPushVerInfo() {
        return this.provider.getPushVerInfo();
    }

    public String getStandaloneVerInfo() {
        return this.provider.getStandaloneVerInfo();
    }

    public String getServerConnectState() {
        return this.provider.getServerConnectState();
    }
}
