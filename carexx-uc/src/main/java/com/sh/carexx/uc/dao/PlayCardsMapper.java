package com.sh.carexx.uc.dao;


import com.sh.carexx.model.uc.PlayCards;

import java.util.List;
import java.util.Map;

public interface PlayCardsMapper {

	void addScore(PlayCards playCards);

	void addScoreLog(PlayCards playCards);

	void resetScore();

	List<Map<String, Object>> getAllScore();

	int getMaxJushu();

	String getLastScore();

}