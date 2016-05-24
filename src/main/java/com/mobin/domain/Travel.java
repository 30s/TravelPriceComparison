package com.mobin.domain;

import java.sql.Blob;

import org.apache.hadoop.classification.InterfaceAudience.Private;

public class Travel {

    //URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN

    private String ROWKEY;// 行键
    private String URL;  //
    private String SP;   //出发点
    private String EP;   //目的地

    private String TITLE; // 标题
    private String ET;   //结束时间
    private String TOUATT;  //行程所包含的景点
    private String ST;    // 开始时间
    private String TDATA;    // 出游天数

    private String PRICE; //价格
    private String TRAFFIC;  // 交通
    private String RETURN;   //是否往返
    private String TTYPE;  //旅游类型（跟团游）
    private String IMAGE;   // 图片
    private String PROXY;//旅游代理公司

    private String HOTEL;  //    酒店


    private String ORIGIN;// 数据来源


    public String getROWKEY() {
        return ROWKEY;
    }

    public void setROWKEY(String ROWKEY) {
        this.ROWKEY = ROWKEY;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }

    public String getEP() {
        return EP;
    }

    public void setEP(String EP) {
        this.EP = EP;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getET() {
        return ET;
    }

    public void setET(String ET) {
        this.ET = ET;
    }

    public String getTOUATT() {
        return TOUATT;
    }

    public void setTOUATT(String TOUATT) {
        this.TOUATT = TOUATT;
    }

    public String getST() {
        return ST;
    }

    public void setST(String ST) {
        this.ST = ST;
    }

    public String getTDATA() {
        return TDATA;
    }

    public void setTDATA(String TDATA) {
        this.TDATA = TDATA;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getTRAFFIC() {
        return TRAFFIC;
    }

    public void setTRAFFIC(String TRAFFIC) {
        this.TRAFFIC = TRAFFIC;
    }

    public String getRETURN() {
        return RETURN;
    }

    public void setRETURN(String RETURN) {
        this.RETURN = RETURN;
    }

    public String getTTYPE() {
        return TTYPE;
    }

    public void setTTYPE(String TTYPE) {
        this.TTYPE = TTYPE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getPROXY() {
        return PROXY;
    }

    public void setPROXY(String PROXY) {
        this.PROXY = PROXY;
    }

    public String getHOTEL() {
        return HOTEL;
    }

    public void setHOTEL(String HOTEL) {
        this.HOTEL = HOTEL;
    }

    public String getORIGIN() {
        return ORIGIN;
    }

    public void setORIGIN(String ORIGIN) {
        this.ORIGIN = ORIGIN;
    }
}
