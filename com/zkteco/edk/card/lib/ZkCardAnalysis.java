package com.zkteco.edk.card.lib;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZkCardAnalysis {
    private static final byte ZERO_BYTE = 0;
    private static ZkCardAnalysis instance;
    private final Map<String, String> mRuleParams;

    private ZkCardAnalysis() {
        HashMap hashMap = new HashMap();
        this.mRuleParams = hashMap;
        hashMap.put(ZkCardRuleName.CARD_RULE_MODE, ZkCardRuleMode.DEFAULT);
        hashMap.put(ZkCardRuleName.CARD_REVERT, "0");
        hashMap.put(ZkCardRuleName.CARD_LENGTH, "4");
        hashMap.put(ZkCardRuleName.START_POSITION, "0");
        hashMap.put(ZkCardRuleName.HEX_CARD, "0");
    }

    public static ZkCardAnalysis getInstance() {
        if (instance == null) {
            synchronized (ZkCardAnalysis.class) {
                if (instance == null) {
                    instance = new ZkCardAnalysis();
                }
            }
        }
        return instance;
    }

    public boolean isCustomMode() {
        return "custom".equals(getCardRuleMode());
    }

    public String getCardRuleMode() {
        return this.mRuleParams.get(ZkCardRuleName.CARD_RULE_MODE);
    }

    public void setCardRuleMode(String str) {
        this.mRuleParams.put(ZkCardRuleName.CARD_RULE_MODE, str);
    }

    public int getStartPosition() {
        try {
            String str = this.mRuleParams.get(ZkCardRuleName.START_POSITION);
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int setStartPosition(int i) {
        if (i > 8 || i < 0) {
            return -1004;
        }
        this.mRuleParams.put(ZkCardRuleName.START_POSITION, String.valueOf(i));
        return 0;
    }

    public int getCardLength() {
        try {
            String str = this.mRuleParams.get(ZkCardRuleName.CARD_LENGTH);
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int setCardLength(int i) {
        if (i > 8 || i < 0) {
            return -1004;
        }
        this.mRuleParams.put(ZkCardRuleName.CARD_LENGTH, String.valueOf(i));
        return 0;
    }

    public boolean isCardRevert() {
        try {
            String str = this.mRuleParams.get(ZkCardRuleName.CARD_REVERT);
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            return "1".equals(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setCardRevert(boolean z) {
        this.mRuleParams.put(ZkCardRuleName.CARD_REVERT, z ? "1" : "0");
    }

    public String analysisCard(byte[] bArr) {
        int length;
        if (!"custom".equals(getCardRuleMode()) || bArr == null || bArr.length == 0 || getCardLength() <= 0 || getStartPosition() < 0 || getStartPosition() > (length = bArr.length)) {
            return "";
        }
        byte[] bArr2 = new byte[getCardLength()];
        Arrays.fill(bArr2, (byte) 0);
        System.arraycopy(bArr, getStartPosition(), bArr2, 0, (getStartPosition() + getCardLength()) - length < 0 ? getCardLength() : length - getStartPosition());
        if (isCardRevert()) {
            HexUtils.reserveByte(bArr2);
        }
        String bytes2HexString = HexUtils.bytes2HexString(bArr2);
        if ("1".equals(getInstance().getCardRule(ZkCardRuleName.HEX_CARD))) {
            return bytes2HexString;
        }
        return String.valueOf(Long.parseLong(bytes2HexString, 16));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c1, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCardRule(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r1 = -1004(0xfffffffffffffc14, float:NaN)
            if (r0 != 0) goto L_0x00c2
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0010
            goto L_0x00c2
        L_0x0010:
            r7.hashCode()
            r0 = -1
            int r2 = r7.hashCode()
            java.lang.String r3 = "HexCard"
            r4 = 0
            r5 = 1
            switch(r2) {
                case -1865485770: goto L_0x004a;
                case -1814752085: goto L_0x0041;
                case -1693474508: goto L_0x0036;
                case 1391364623: goto L_0x002b;
                case 1673883531: goto L_0x0020;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0054
        L_0x0020:
            java.lang.String r2 = "StartPosition"
            boolean r7 = r7.equals(r2)
            if (r7 != 0) goto L_0x0029
            goto L_0x0054
        L_0x0029:
            r0 = 4
            goto L_0x0054
        L_0x002b:
            java.lang.String r2 = "CardRuleMode"
            boolean r7 = r7.equals(r2)
            if (r7 != 0) goto L_0x0034
            goto L_0x0054
        L_0x0034:
            r0 = 3
            goto L_0x0054
        L_0x0036:
            java.lang.String r2 = "CardRevert"
            boolean r7 = r7.equals(r2)
            if (r7 != 0) goto L_0x003f
            goto L_0x0054
        L_0x003f:
            r0 = 2
            goto L_0x0054
        L_0x0041:
            boolean r7 = r7.equals(r3)
            if (r7 != 0) goto L_0x0048
            goto L_0x0054
        L_0x0048:
            r0 = r5
            goto L_0x0054
        L_0x004a:
            java.lang.String r2 = "CardLength"
            boolean r7 = r7.equals(r2)
            if (r7 != 0) goto L_0x0053
            goto L_0x0054
        L_0x0053:
            r0 = r4
        L_0x0054:
            r7 = 8
            java.lang.String r2 = "1"
            switch(r0) {
                case 0: goto L_0x00a8;
                case 1: goto L_0x0099;
                case 2: goto L_0x0091;
                case 3: goto L_0x0078;
                case 4: goto L_0x005d;
                default: goto L_0x005b;
            }
        L_0x005b:
            goto L_0x00c1
        L_0x005d:
            int r0 = r8.length()
            if (r0 > r5) goto L_0x0077
            boolean r0 = android.text.TextUtils.isDigitsOnly(r8)
            if (r0 != 0) goto L_0x006a
            goto L_0x0077
        L_0x006a:
            int r8 = java.lang.Integer.parseInt(r8)
            if (r8 < 0) goto L_0x0077
            if (r8 <= r7) goto L_0x0073
            goto L_0x0077
        L_0x0073:
            r6.setStartPosition(r8)
            goto L_0x00c1
        L_0x0077:
            return r1
        L_0x0078:
            java.lang.String r7 = "custom"
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x008d
            java.lang.String r7 = "default"
            boolean r0 = r7.equals(r8)
            if (r0 == 0) goto L_0x0089
            goto L_0x008d
        L_0x0089:
            r6.setCardRuleMode(r7)
            return r1
        L_0x008d:
            r6.setCardRuleMode(r8)
            goto L_0x00c1
        L_0x0091:
            boolean r7 = r2.equals(r8)
            r6.setCardRevert(r7)
            goto L_0x00c1
        L_0x0099:
            java.util.Map<java.lang.String, java.lang.String> r7 = r6.mRuleParams
            boolean r8 = r2.equals(r8)
            if (r8 == 0) goto L_0x00a2
            goto L_0x00a4
        L_0x00a2:
            java.lang.String r2 = "0"
        L_0x00a4:
            r7.put(r3, r2)
            goto L_0x00c1
        L_0x00a8:
            int r0 = r8.length()
            if (r0 > r5) goto L_0x00c2
            boolean r0 = android.text.TextUtils.isDigitsOnly(r8)
            if (r0 != 0) goto L_0x00b5
            goto L_0x00c2
        L_0x00b5:
            int r8 = java.lang.Integer.parseInt(r8)
            if (r8 < 0) goto L_0x00c2
            if (r8 <= r7) goto L_0x00be
            goto L_0x00c2
        L_0x00be:
            r6.setCardLength(r8)
        L_0x00c1:
            return r4
        L_0x00c2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zkteco.edk.card.lib.ZkCardAnalysis.setCardRule(java.lang.String, java.lang.String):int");
    }

    public String getCardRule(String str) {
        return this.mRuleParams.get(str);
    }
}
