package com.sh.carexx.uc.service.impl;

import com.sh.carexx.model.uc.PlayCards;
import com.sh.carexx.uc.dao.PlayCardsMapper;
import com.sh.carexx.uc.service.PlayCardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
	public List<Map<String, Object>> getAllScore() {
		return this.playCardsMapper.getAllScore();
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
