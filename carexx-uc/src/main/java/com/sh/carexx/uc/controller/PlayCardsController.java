package com.sh.carexx.uc.controller;

import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.web.BasicRetVal;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.model.uc.PlayCards;
import com.sh.carexx.uc.manager.PlayCardsManager;
import com.sh.carexx.uc.service.PlayCardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/play_cards")
public class PlayCardsController {

    @Autowired
    private PlayCardsService playCardsService;
    @Autowired
    private PlayCardsManager playCardsManager;

    @RequestMapping(value = "/addScore/{scores}", method = RequestMethod.GET)
    public BasicRetVal addScore(@PathVariable(value = "scores") String scores) {
        try {
            playCardsManager.addScore(scores);
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
        return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }

    @RequestMapping(value = "/getScore", method = RequestMethod.GET)
    public String getScore() {
        List<PlayCards> resultList = null;
        resultList = this.playCardsService.getAllScore();
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS, resultList).toJSON();
    }

    @RequestMapping(value = "/resetScore", method = RequestMethod.POST)
    public BasicRetVal resetScore() {
        try {
            playCardsManager.resetScore();
        } catch (BizException e) {
            return new BasicRetVal(CarexxConstant.RetCode.SERVER_ERROR, e.getCode(), e.getDesc());
        }
        return new BasicRetVal(CarexxConstant.RetCode.SUCCESS);
    }

}