package com.sh.carexx.uc.service;

import com.sh.carexx.model.uc.PlayCards;

import java.util.List;

/**
 *
 * ClassName: 用户个人账户 <br/>
 *
 * @author hetao
 * @since JDK 1.8
 */
public interface PlayCardsService {

    void addScore(PlayCards playCards);

    void addScoreLog(PlayCards playCards);

    void resetScore();

    List<PlayCards> getAllScore();

    int getMaxJushu();

    String getLastScore();

}
