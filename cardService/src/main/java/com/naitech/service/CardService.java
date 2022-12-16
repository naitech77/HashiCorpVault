package com.naitech.service;

import com.naitech.model.Card;

public interface CardService {
	
	void saveCard(Card card);
	
	Card getCard(int id);

}
