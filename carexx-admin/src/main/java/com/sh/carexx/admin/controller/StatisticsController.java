package com.sh.carexx.admin.controller;

import com.sh.carexx.bean.statistics.StatisticsBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController extends BaseController {

    @RequestMapping(value = "/queryStatistics")
    public String queryStatistics(StatisticsBean statisticsBean) {
        return this.ucServiceClient.queryStatistics(statisticsBean);
    }

}