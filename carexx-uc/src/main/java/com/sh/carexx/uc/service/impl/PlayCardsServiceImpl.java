package com.sh.carexx.uc.service.impl;

import com.sh.carexx.model.uc.PlayCards;
import com.sh.carexx.uc.dao.PlayCardsMapper;
import com.sh.carexx.uc.service.PlayCardsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayCardsServiceImpl implements PlayCardsService {

    @Autowired
    private PlayCardsMapper playCardsMapper;

    @Override
    public void addScore(PlayCards playCards) {
        this.playCardsMapper.addScore(playCards);
    }

    @Override
    public void addScoreLog(PlayCards playCards) {
        this.playCardsMapper.addScoreLog(playCards);
    }

    @Override
    public void resetScore() {
        this.playCardsMapper.resetScore();
    }

    @Override
    public List<PlayCards> getAllScore() {
        List<PlayCards> result = this.playCardsMapper.getAllScore();
        for (PlayCards playCards : result) {
            if (StringUtils.isNotBlank(playCards.getScore())) {
                playCards.setScores(playCards.getScore().split(","));
            }
            if (StringUtils.isNotBlank(playCards.getOperantScore())) {
                String[] operantScores = playCards.getOperantScore().split(",");
                String[] statuses = new String[operantScores.length];
                playCards.setOperantScores(operantScores);
                for (int i = 0; i < operantScores.length; i++) {
                    if (operantScores[i].indexOf("+") != -1) {
                        statuses[i] = "1";
                    }
                    if (operantScores[i].indexOf("-") != -1) {
                        statuses[i] = "-1";
                    }
                }
                playCards.setStatuses(statuses);
            }
        }
        return result;
    }

    @Override
    public int getMaxJushu() {
        return this.playCardsMapper.getMaxJushu();
    }

    @Override
    public String getLastScore() {
        return this.playCardsMapper.getLastScore();
    }

}
