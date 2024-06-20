package com.zktechnology.android.wiegand.bean;

public class WiegandOutData extends WiegandBaseData {
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x01e6: MOVE  (r15v4 int) = (r20v1 int)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:122)
        	at jadx.core.dex.visitors.regions.TernaryMod.visitRegion(TernaryMod.java:34)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:73)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:78)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:31)
        */
    public byte[] getWiegandBinaryData() {
        /*
            r28 = this;
            r0 = r28
            com.zktechnology.android.wiegand.HidFormatDao r1 = r0.hidFormatDao
            java.lang.String r2 = r0.cardBit
            com.zkteco.android.zkcore.wiegand.enmutype.WiegandFormatType r3 = com.zkteco.android.zkcore.wiegand.enmutype.WiegandFormatType.FORMAT_TYPE_OUT
            com.zkteco.android.db.orm.tna.HidFormat r1 = r1.getHidFormat4CardBitAndStatusYes(r2, r3)
            java.lang.String r2 = "TAG_WIEGAND_"
            r3 = 0
            if (r1 != 0) goto L_0x001b
            com.zkteco.android.zkcore.wiegand.WiegandLogUtils r1 = com.zkteco.android.zkcore.wiegand.WiegandLogUtils.getInstance()
            java.lang.String r4 = "wiegandOut###hidFormat is null or wiegandData is null"
            r1.e(r2, r4)
            return r3
        L_0x001b:
            long r4 = r28.getNumCode()
            java.lang.String r6 = r1.getFormat_Name()
            java.lang.String r7 = "Wiegand26"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x0033
            java.lang.String r7 = "Wiegand34a"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x003b
        L_0x0033:
            r7 = 16777215(0xffffff, double:8.2890456E-317)
            int r7 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x003b
            return r3
        L_0x003b:
            java.lang.String r7 = "Wiegand26a"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x005b
            java.lang.String r7 = "Wiegand36a"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x005b
            java.lang.String r7 = "Wiegand36"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x005b
            java.lang.String r7 = "Wiegand37a"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x0063
        L_0x005b:
            r7 = 65535(0xffff, double:3.23786E-319)
            int r7 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x0063
            return r3
        L_0x0063:
            java.lang.String r7 = "Wiegand34"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x0073
            java.lang.String r7 = "Wiegand50"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x007d
        L_0x0073:
            r7 = 4294967295(0xffffffff, double:2.1219957905E-314)
            int r7 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x007d
            return r3
        L_0x007d:
            java.lang.String r7 = "Wiegand37"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x008d
            r6 = 524287(0x7ffff, double:2.59032E-318)
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x008d
            return r3
        L_0x008d:
            int r3 = r28.getSiteCode()
            int r6 = r28.getFacilityCode()
            int r7 = r28.getManufactureCode()
            int r8 = r1.getCard_Bit()
            char[] r8 = new char[r8]
            java.lang.String r9 = r1.getCard_Format()
            java.lang.String r10 = r1.getFirst_Even()
            java.lang.String r11 = r1.getFirst_Odd()
            java.lang.String r12 = r1.getSecond_Even()
            java.lang.String r13 = r1.getSecond_Odd()
            boolean r14 = android.text.TextUtils.isEmpty(r9)
            if (r14 == 0) goto L_0x00c0
            int r9 = r1.getCard_Bit()
            char[] r9 = new char[r9]
            goto L_0x00c4
        L_0x00c0:
            char[] r9 = r9.toCharArray()
        L_0x00c4:
            boolean r14 = android.text.TextUtils.isEmpty(r10)
            if (r14 == 0) goto L_0x00d1
            int r10 = r1.getCard_Bit()
            char[] r10 = new char[r10]
            goto L_0x00d5
        L_0x00d1:
            char[] r10 = r10.toCharArray()
        L_0x00d5:
            boolean r14 = android.text.TextUtils.isEmpty(r11)
            if (r14 == 0) goto L_0x00e2
            int r11 = r1.getCard_Bit()
            char[] r11 = new char[r11]
            goto L_0x00e6
        L_0x00e2:
            char[] r11 = r11.toCharArray()
        L_0x00e6:
            boolean r14 = android.text.TextUtils.isEmpty(r12)
            if (r14 == 0) goto L_0x00f3
            int r12 = r1.getCard_Bit()
            char[] r12 = new char[r12]
            goto L_0x00f7
        L_0x00f3:
            char[] r12 = r12.toCharArray()
        L_0x00f7:
            boolean r14 = android.text.TextUtils.isEmpty(r13)
            if (r14 == 0) goto L_0x0104
            int r13 = r1.getCard_Bit()
            char[] r13 = new char[r13]
            goto L_0x0108
        L_0x0104:
            char[] r13 = r13.toCharArray()
        L_0x0108:
            int r1 = r1.getCard_Bit()
            r14 = 1
            int r1 = r1 - r14
            r15 = -1
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = -1
            r21 = -1
            r22 = -1
        L_0x011d:
            if (r1 < 0) goto L_0x01d2
            r23 = 48
            r8[r1] = r23
            char r14 = r9[r1]
            r0 = 67
            if (r14 == r0) goto L_0x0171
            r0 = 77
            if (r14 == r0) goto L_0x016d
            r0 = 79
            if (r14 == r0) goto L_0x0185
            r0 = 83
            if (r14 == r0) goto L_0x0176
            r0 = 99
            if (r14 == r0) goto L_0x0171
            r0 = 109(0x6d, float:1.53E-43)
            if (r14 == r0) goto L_0x016d
            r0 = 111(0x6f, float:1.56E-43)
            if (r14 == r0) goto L_0x016a
            r0 = 115(0x73, float:1.61E-43)
            if (r14 == r0) goto L_0x0176
            r0 = 69
            if (r14 == r0) goto L_0x0168
            r0 = 70
            if (r14 == r0) goto L_0x015b
            r0 = 101(0x65, float:1.42E-43)
            if (r14 == r0) goto L_0x0158
            r0 = 102(0x66, float:1.43E-43)
            if (r14 == r0) goto L_0x015b
        L_0x0155:
            r0 = 49
            goto L_0x01a4
        L_0x0158:
            r20 = r1
            goto L_0x0155
        L_0x015b:
            r0 = r6 & 1
            r14 = 1
            if (r0 != r14) goto L_0x0164
            r0 = 49
            r8[r1] = r0
        L_0x0164:
            int r0 = r6 >> 1
            r6 = r0
            goto L_0x0155
        L_0x0168:
            r15 = r1
            goto L_0x0155
        L_0x016a:
            r22 = r1
            goto L_0x0155
        L_0x016d:
            r0 = 49
            r14 = 1
            goto L_0x018a
        L_0x0171:
            r23 = r3
            r0 = 49
            goto L_0x0196
        L_0x0176:
            r0 = r3 & 1
            r14 = 1
            if (r0 != r14) goto L_0x0180
            r0 = 49
            r8[r1] = r0
            goto L_0x0182
        L_0x0180:
            r0 = 49
        L_0x0182:
            int r3 = r3 >> 1
            goto L_0x01a4
        L_0x0185:
            r0 = 49
            r21 = r1
            goto L_0x01a4
        L_0x018a:
            r23 = r3
            r3 = r7 & 1
            if (r3 != r14) goto L_0x0192
            r8[r1] = r0
        L_0x0192:
            int r3 = r7 >> 1
            r7 = r3
            goto L_0x01a2
        L_0x0196:
            r24 = 1
            long r26 = r4 & r24
            int r3 = (r26 > r24 ? 1 : (r26 == r24 ? 0 : -1))
            if (r3 != 0) goto L_0x01a0
            r8[r1] = r0
        L_0x01a0:
            r3 = 1
            long r4 = r4 >> r3
        L_0x01a2:
            r3 = r23
        L_0x01a4:
            char r14 = r10[r1]
            if (r14 != r0) goto L_0x01ae
            char r14 = r8[r1]
            if (r14 != r0) goto L_0x01ae
            int r16 = r16 + 1
        L_0x01ae:
            char r14 = r11[r1]
            if (r14 != r0) goto L_0x01b8
            char r14 = r8[r1]
            if (r14 != r0) goto L_0x01b8
            int r18 = r18 + 1
        L_0x01b8:
            char r14 = r12[r1]
            if (r14 != r0) goto L_0x01c2
            char r14 = r8[r1]
            if (r14 != r0) goto L_0x01c2
            int r17 = r17 + 1
        L_0x01c2:
            char r14 = r13[r1]
            if (r14 != r0) goto L_0x01cc
            char r14 = r8[r1]
            if (r14 != r0) goto L_0x01cc
            int r19 = r19 + 1
        L_0x01cc:
            int r1 = r1 + -1
            r0 = r28
            goto L_0x011d
        L_0x01d2:
            int r0 = r16 % 2
            r1 = 1
            if (r0 != r1) goto L_0x01df
            r0 = -1
            r3 = 49
            if (r15 == r0) goto L_0x01e2
            r8[r15] = r3
            goto L_0x01e2
        L_0x01df:
            r0 = -1
            r3 = 49
        L_0x01e2:
            int r4 = r17 % 2
            if (r4 != r1) goto L_0x01ec
            r15 = r20
            if (r15 == r0) goto L_0x01ec
            r8[r15] = r3
        L_0x01ec:
            int r18 = r18 % 2
            if (r18 != 0) goto L_0x01f6
            r15 = r21
            if (r15 == r0) goto L_0x01f6
            r8[r15] = r3
        L_0x01f6:
            int r19 = r19 % 2
            if (r19 != 0) goto L_0x0200
            r15 = r22
            if (r15 == r0) goto L_0x0200
            r8[r15] = r3
        L_0x0200:
            java.lang.String r0 = java.lang.String.valueOf(r8)
            com.zkteco.android.zkcore.wiegand.WiegandLogUtils r1 = com.zkteco.android.zkcore.wiegand.WiegandLogUtils.getInstance()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "wiegandOut###"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            r1.i(r2, r3)
            byte[] r0 = com.zktechnology.android.wiegand.WiegandUtil.binStrToByteArr4Long(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.wiegand.bean.WiegandOutData.getWiegandBinaryData():byte[]");
    }
}
