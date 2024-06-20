package com.zktechnology.android.wiegand;

import com.zktechnology.android.utils.LogUtils;
import com.zkteco.android.db.orm.tna.HidFormat;
import com.zkteco.android.zkcore.wiegand.WiegandConfig;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandFormatType;
import com.zkteco.android.zkcore.wiegand.enmutype.WiegandStatus;

public class HidFormatDao {
    private HidFormat hidFormat = new HidFormat();

    public HidFormat getHidFormat4CardBitAndStatusYes(String str, WiegandFormatType wiegandFormatType) {
        try {
            HidFormat hidFormat2 = (HidFormat) new HidFormat().getQueryBuilder().where().eq(WiegandConfig.COLUMN_CARD_BIT, str).and().eq(WiegandConfig.COLUMN_STATUS, Integer.valueOf(WiegandStatus.YES.getValue())).and().eq(WiegandConfig.COLUMN_FORMAT_TYPE, Integer.valueOf(wiegandFormatType.getValue())).queryForFirst();
            if (hidFormat2 != null) {
                return hidFormat2;
            }
            return null;
        } catch (Exception e) {
            LogUtils.e(WiegandUtil.WG_TAG, e.getMessage());
            return null;
        }
    }
}
