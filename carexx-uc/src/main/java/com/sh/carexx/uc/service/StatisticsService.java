package com.sh.carexx.uc.service;

import com.sh.carexx.bean.statistics.StatisticsBean;
import com.sh.carexx.model.uc.Statistics;

import java.util.List;

public interface StatisticsService {

    List<Statistics> queryStatistics(StatisticsBean statisticsBean);

}
