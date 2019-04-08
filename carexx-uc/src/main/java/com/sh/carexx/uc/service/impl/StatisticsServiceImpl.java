package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.statistics.StatisticsBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.model.uc.Statistics;
import com.sh.carexx.uc.dao.StatisticsMapper;
import com.sh.carexx.uc.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<Statistics> queryStatistics(StatisticsBean statisticsBean) {
        statisticsBean.setSearchBeginDate(
                statisticsBean.getSearchBeginDate() + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX);
        statisticsBean.setSearchEndDate(
                statisticsBean.getSearchEndDate() + CarexxConstant.Datetime.DAY_END_SUFFIX);
        return statisticsMapper.queryStatistics(statisticsBean);
    }
}
