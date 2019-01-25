package com.sh.carexx.uc.service;

import com.sh.carexx.model.uc.PlayCards;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> getAllScore();

    int getMaxJushu();

    String getLastScore();

}
