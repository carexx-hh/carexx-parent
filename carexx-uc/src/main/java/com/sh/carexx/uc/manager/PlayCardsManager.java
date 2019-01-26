package com.sh.carexx.uc.manager;

import com.sh.carexx.common.ErrorCode;
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
        try {
            this.playCardsService.addScoreLog(playCards);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
        if (StringUtils.isNotBlank(oldScore)) {
            //之前最后一条数据 lastScore+scores
            String[] newScores = scores.split(",");
            String[] lastScores = oldScore.split(",");
            if (newScores.length != lastScores.length) {
                throw new BizException(ErrorCode.DB_ERROR);
            } else {
                Integer[] finalScores = new Integer[newScores.length];
                for (int i = 0; i < newScores.length; i++) {
                    finalScores[i] = Integer.parseInt(newScores[i]) + Integer.parseInt(lastScores[i]);
                }
                playCards.setOperantScore(this.operantScore(finalScores));
                playCards.setScore(StringUtils.join(finalScores, ","));
            }
        }
        try {
            this.playCardsService.addScore(playCards);
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
    }

    public void resetScore() throws BizException {
        try {
            this.playCardsService.resetScore();
        } catch (Exception e) {
            throw new BizException(ErrorCode.DB_ERROR, e);
        }
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
            if (i > 0) {
                operantScore = operantScore + ",+" + i;
            } else if (i == 0) {
                operantScore = operantScore + ",";
            } else {
                operantScore = operantScore + "," + i;
            }
        }
//        if (StringUtils.isBlank(operantScore.substring(1))) {
//            return "0,0,0,0";
//        }
        return operantScore.substring(1);
    }

}
