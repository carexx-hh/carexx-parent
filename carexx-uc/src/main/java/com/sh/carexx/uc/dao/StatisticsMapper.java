package com.sh.carexx.uc.dao;


import com.sh.carexx.bean.statistics.StatisticsBean;
import com.sh.carexx.model.uc.Statistics;

import java.util.List;

public interface StatisticsMapper {

	List<Statistics> queryStatistics(StatisticsBean statisticsBean);

}