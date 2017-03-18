package com.shou.zhao.suyuansearch.dao;

import java.io.Serializable;

/**
 * Created by haohao on 2017/3/1.
 * 养殖基地养的种类
 */

public class Type implements Serializable {

    private String introduce;
    private String typeid;
    private String typename;
    private String typepicture;
    private Float pricekg;

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    private int is_hot;

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypepicture() {
        return typepicture;
    }

    public void setTypepicture(String typepicture) {
        this.typepicture = typepicture;
    }

    public Float getPricekg() {
        return pricekg;
    }

    public void setPricekg(Float pricekg) {
        this.pricekg = pricekg;
    }
}
