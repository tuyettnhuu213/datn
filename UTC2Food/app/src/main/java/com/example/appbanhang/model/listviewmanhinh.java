package com.example.appbanhang.model;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

public class listviewmanhinh {
    Integer imv;
    String txt;

    public listviewmanhinh(Integer imv, String txt) {
        this.imv = imv;
        this.txt = txt;
    }

    public Integer getImv() {
        return imv;
    }

    public void setImv(Integer imv) {
        this.imv = imv;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
