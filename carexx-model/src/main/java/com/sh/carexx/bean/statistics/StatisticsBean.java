package com.sh.carexx.bean.statistics;

import com.sh.carexx.bean.BasicFormBean;
import com.sh.carexx.common.CarexxConstant;
import org.apache.commons.lang.StringUtils;

public class StatisticsBean extends BasicFormBean {

    private Integer instId;  //机构Id

    private String searchBeginDate; //搜索时间

    private String searchEndDate; //搜索时间

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

    public String getSearchBeginDate() {
        return searchBeginDate;
    }

    public void setSearchBeginDate(String searchBeginDate) {
        if (StringUtils.isBlank(searchBeginDate)) {
            this.searchBeginDate = null;
        }
        this.searchBeginDate = searchBeginDate + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX;
    }

    public String getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(String searchEndDate) {
        if (StringUtils.isBlank(searchBeginDate)) {
            this.searchEndDate = null;
        }
        this.searchEndDate = searchEndDate + CarexxConstant.Datetime.DAY_END_SUFFIX;
    }
}