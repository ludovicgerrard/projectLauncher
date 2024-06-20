package com.zkteco.android.db.orm.manager.template;

import com.zkteco.android.db.orm.tna.PersBiotemplate;
import com.zkteco.android.db.orm.tna.PersBiotemplatedata;

public class TemplateModule {
    private PersBiotemplate persBiotemplate;
    private PersBiotemplatedata persBiotemplatedata;

    public TemplateModule(PersBiotemplate persBiotemplate2, PersBiotemplatedata persBiotemplatedata2) {
        this.persBiotemplate = persBiotemplate2;
        this.persBiotemplatedata = persBiotemplatedata2;
    }

    public PersBiotemplate getPersBiotemplate() {
        return this.persBiotemplate;
    }

    public PersBiotemplatedata getPersBiotemplatedata() {
        return this.persBiotemplatedata;
    }
}
