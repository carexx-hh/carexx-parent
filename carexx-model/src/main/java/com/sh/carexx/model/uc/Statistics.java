package com.sh.carexx.model.uc;

import java.io.Serializable;
import java.util.Date;

public class Statistics implements Serializable {
    private String khbh;  //客户编号

    private String khxm;  //客户姓名

    private String lb;  //类别

    private String ssgs;  //所属公司

    private String xh;  //序号

    private String bh;  //编号

    private String fwdh;  //服务单号	

    private String qdr;  //签单人

    private String hly;  //护理员

    private String sjh;  //收据号

    private String fph;  //发票号

    private Date sgrq;  //上岗日期

    private Date jsrq;  //结束日期

    private String zcts;  //正常天数

    private String jjrts;  //节假日天数

    private String dj;  //单价

    private String ysk;  //应收款

    private String zffs;  //支付方式

    private String zkje;  //折扣金额

    private String tzje;  //调整金额

    private String ssk;  //实收款

    private String yyk;  //预收款

    private String jskzc;  //结算款支出

    private String jsktzje;  //结算款调整金额

    private String glf;  //管理费

    private String bz;  //备注

    private Integer instId;  //机构Id

    private static final long serialVersionUID = 1L;

    public String getKhbh() {
        return khbh;
    }

    public void setKhbh(String khbh) {
        this.khbh = khbh;
    }

    public String getKhxm() {
        return khxm;
    }

    public void setKhxm(String khxm) {
        this.khxm = khxm;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getSsgs() {
        return ssgs;
    }

    public void setSsgs(String ssgs) {
        this.ssgs = ssgs;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getFwdh() {
        return fwdh;
    }

    public void setFwdh(String fwdh) {
        this.fwdh = fwdh;
    }

    public String getQdr() {
        return qdr;
    }

    public void setQdr(String qdr) {
        this.qdr = qdr;
    }

    public String getHly() {
        return hly;
    }

    public void setHly(String hly) {
        this.hly = hly;
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public Date getSgrq() {
        return sgrq;
    }

    public void setSgrq(Date sgrq) {
        this.sgrq = sgrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public String getZcts() {
        return zcts;
    }

    public void setZcts(String zcts) {
        this.zcts = zcts;
    }

    public String getJjrts() {
        return jjrts;
    }

    public void setJjrts(String jjrts) {
        this.jjrts = jjrts;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getYsk() {
        return ysk;
    }

    public void setYsk(String ysk) {
        this.ysk = ysk;
    }

    public String getZffs() {
        return zffs;
    }

    public void setZffs(String zffs) {
        this.zffs = zffs;
    }

    public String getZkje() {
        return zkje;
    }

    public void setZkje(String zkje) {
        this.zkje = zkje;
    }

    public String getTzje() {
        return tzje;
    }

    public void setTzje(String tzje) {
        this.tzje = tzje;
    }

    public String getSsk() {
        return ssk;
    }

    public void setSsk(String ssk) {
        this.ssk = ssk;
    }

    public String getYyk() {
        return yyk;
    }

    public void setYyk(String yyk) {
        this.yyk = yyk;
    }

    public String getJskzc() {
        return jskzc;
    }

    public void setJskzc(String jskzc) {
        this.jskzc = jskzc;
    }

    public String getJsktzje() {
        return jsktzje;
    }

    public void setJsktzje(String jsktzje) {
        this.jsktzje = jsktzje;
    }

    public String getGlf() {
        return glf;
    }

    public void setGlf(String glf) {
        this.glf = glf;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }
}