package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.statistics.StatisticsBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.uc.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/queryStatistics", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String queryRepository(@RequestBody StatisticsBean statisticsBean) {
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS, this.statisticsService.queryStatistics(statisticsBean)).toJSON();
    }

}