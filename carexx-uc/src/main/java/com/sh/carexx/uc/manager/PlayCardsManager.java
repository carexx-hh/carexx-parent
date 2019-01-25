package com.sh.carexx.uc.manager;

import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.model.uc.PlayCards;
import com.sh.carexx.uc.service.PlayCardsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayCardsManager {

    @Autowired
    private PlayCardsService playCardsService;

    public void addScore(String scores) throws BizException {
        PlayCards playCards = new PlayCards();
        int jushu = playCardsService.getMaxJushu();
        String oldScore = playCardsService.getLastScore();
        playCards.setJushu((long) (jushu + 1));
        playCards.setStatus((byte) 1);
        playCards.setScore(scores);
        //每局记录
        this.playCardsService.addScoreLog(playCards);
        if (StringUtils.isNotBlank(oldScore)) {
            //之前最后一条数据 lastScore+scores
            String[] newScores = scores.split(",");
            String[] lastScores = oldScore.split(",");
            Integer[] finalScores = new Integer[newScores.length];
            for (int i = 0; i < newScores.length; i++) {
                finalScores[i] = Integer.parseInt(newScores[i]) + Integer.parseInt(lastScores[i]);
            }
            playCards.setOperantScore(this.operantScore(finalScores));
            playCards.setScore(StringUtils.join(finalScores, ","));
        }
        this.playCardsService.addScore(playCards);
    }

    public void resetScore() throws BizException {
        this.playCardsService.resetScore();
    }

    private String operantScore(Integer[] scores) {
        int[] newScores = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            while (scores[i] >= 100) {
                for (int j = 0; j < scores.length; j++) {
                    if (scores[j] <= -100) {
                        scores[i] = scores[i] - 100;
                        scores[j] = scores[j] + 100;
                        newScores[i] = newScores[i] + 100;
                        newScores[j] = newScores[j] + -100;
                        if (scores[i] < 100) {
                            break;
                        }
                    }
                }
            }
        }
        String operantScore = "";
        for (int i : newScores) {
            operantScore = operantScore + "," + i;
        }
        if(StringUtils.isBlank(operantScore.substring(1))){
            return "0,0,0,0";
        }
        return operantScore.substring(1);
    }

}
