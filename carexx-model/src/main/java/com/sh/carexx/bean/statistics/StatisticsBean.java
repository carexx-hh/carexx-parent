package com.sh.carexx.bean.statistics;

import com.sh.carexx.bean.BasicFormBean;

import java.util.Date;

public class StatisticsBean extends BasicFormBean {

    private Integer instId;  //机构Id

    private Date searchBeginDate; //搜索时间

    private Date searchEndDate; //搜索时间

    private String hly;  //护理员

    private String qdr;  //签单人

    public String getHly() {
        return hly;
    }

    public void setHly(String hly) {
        this.hly = hly;
    }

    public String getQdr() {
        return qdr;
    }

    public void setQdr(String qdr) {
        this.qdr = qdr;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public Date getSearchBeginDate() {
        return searchBeginDate;
    }

    public void setSearchBeginDate(Date searchBeginDate) {
        this.searchBeginDate = searchBeginDate;
    }

    public Date getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(Date searchEndDate) {
        this.searchEndDate = searchEndDate;
    }
}