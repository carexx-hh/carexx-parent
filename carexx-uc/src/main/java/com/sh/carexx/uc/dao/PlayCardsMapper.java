package com.sh.carexx.uc.dao;


import com.sh.carexx.model.uc.PlayCards;

import java.util.List;

public interface PlayCardsMapper {

	void addScore(PlayCards playCards);

	void addScoreLog(PlayCards playCards);

	void resetScore();

	List<PlayCards> getAllScore();

	int getMaxJushu();

	String getLastScore();

}