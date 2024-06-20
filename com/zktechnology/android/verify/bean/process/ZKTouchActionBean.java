package com.zktechnology.android.verify.bean.process;

public class ZKTouchActionBean {
    private boolean bolActionOpen = true;
    private boolean bolFace = true;
    private boolean bolFingerprint = true;
    private boolean bolKeyboard = true;
    private boolean bolMenu = true;
    private boolean bolPalm = true;
    private boolean bolRFidRead = true;
    private boolean bolWiegand = true;

    public boolean isBolWiegand() {
        return this.bolWiegand;
    }

    public void setBolWiegand(boolean z) {
        this.bolWiegand = z;
    }

    public boolean isBolMenu() {
        return this.bolMenu;
    }

    public void setBolMenu(boolean z) {
        this.bolMenu = z;
    }

    public boolean isBolActionOpen() {
        return this.bolActionOpen;
    }

    public void setBolActionOpen(boolean z) {
        this.bolActionOpen = z;
    }

    public boolean isBolFingerprint() {
        return this.bolFingerprint;
    }

    public void setBolFingerprint(boolean z) {
        this.bolFingerprint = z;
    }

    public boolean isBolRFidRead() {
        return this.bolRFidRead;
    }

    public void setBolRFidRead(boolean z) {
        this.bolRFidRead = z;
    }

    public boolean isBolKeyboard() {
        return this.bolKeyboard;
    }

    public void setBolKeyboard(boolean z) {
        this.bolKeyboard = z;
    }

    public boolean isBolFace() {
        return this.bolFace;
    }

    public void setBolFace(boolean z) {
        this.bolFace = z;
    }

    public boolean isBolPalm() {
        return this.bolPalm;
    }

    public void setBolPalm(boolean z) {
        this.bolPalm = z;
    }

    public void setFTouchAction() {
        this.bolMenu = false;
        this.bolActionOpen = false;
        this.bolFingerprint = false;
        this.bolRFidRead = false;
        this.bolKeyboard = false;
        this.bolFace = false;
        this.bolWiegand = false;
        this.bolPalm = false;
    }

    public void setTTouchAction() {
        this.bolMenu = true;
        this.bolActionOpen = true;
        this.bolFingerprint = true;
        this.bolRFidRead = true;
        this.bolKeyboard = true;
        this.bolFace = true;
        this.bolWiegand = true;
        this.bolPalm = true;
    }

    public boolean isVerifying() {
        return !this.bolMenu || !this.bolActionOpen || !this.bolFingerprint || !this.bolRFidRead || !this.bolKeyboard || !this.bolFace || !this.bolPalm;
    }
}
