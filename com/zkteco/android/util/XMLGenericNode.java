package com.zkteco.android.util;

import android.sax.Element;
import android.sax.ElementListener;
import android.sax.EndTextElementListener;
import org.xml.sax.Attributes;

public abstract class XMLGenericNode implements ElementListener, EndTextElementListener {
    public Element node;

    public void end() {
    }

    public void end(String str) {
    }

    public void start(Attributes attributes) {
    }

    protected XMLGenericNode(Element element, String str, boolean z) {
        Element child = element.getChild(str);
        this.node = child;
        child.setStartElementListener(this);
        this.node.setEndElementListener(this);
        if (z) {
            this.node.setEndTextElementListener(this);
        }
    }
}
