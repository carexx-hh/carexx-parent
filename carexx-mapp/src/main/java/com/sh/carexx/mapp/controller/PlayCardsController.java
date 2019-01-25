package com.sh.carexx.mapp.controller;

import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by scchen
 * Created time on 2019/1/24
 * Description 描述
 */
@RestController
@RequestMapping(value = "/play_cards")
public class PlayCardsController extends BaseController   {

    @RequestMapping(value = "/addScore/{scores}")
    public BasicRetVal addScore(@PathVariable(value = "scores") String scores) {
        return this.ucServiceClient.addScore(scores);
    }

    @RequestMapping(value = "/getScore")
    public String getScore() {
        return this.ucServiceClient.getScore();
    }

    @RequestMapping(value = "/resetScore")
    public BasicRetVal resetScore() {
        return this.ucServiceClient.resetScore();
    }
}