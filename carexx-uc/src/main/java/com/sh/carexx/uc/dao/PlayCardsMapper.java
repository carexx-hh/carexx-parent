package com.sh.carexx.uc.dao;


import com.sh.carexx.model.uc.PlayCards;
import com.sh.carexx.model.uc.User;

import java.util.List;

public interface PlayCardsMapper {

	void addScore(PlayCards playCards);

	void addScoreLog(PlayCards playCards);

	void resetScore();

	List<PlayCards> getAllScore();

	int getMaxJushu();

	String getLastScore();

	void addUserName(User user);

	void updateUserName(User user);

	int getUserById(int id);

	List<User> queryUserName();

}