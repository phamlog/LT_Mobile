package com.example.recyclerview_indicator;

import java.io.Serializable;

public class IconModel implements Serializable {
    private Integer imgId;
    private String desc;
    public IconModel() {}

    public IconModel(Integer imgId, String desc) {
        this.imgId = imgId;
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }
}
