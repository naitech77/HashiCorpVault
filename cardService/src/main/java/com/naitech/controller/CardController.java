package com.naitech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naitech.model.Card;
import com.naitech.service.CardService;

@RestController
public class CardController {
	
	@Autowired
	private CardService cardService;
		
	@PostMapping("/savecard")
	public void saveCard(@RequestBody Card card) {
		cardService.saveCard(card);
	}
	
	@GetMapping("/getcard/{id}")
	public Card saveCard(@PathVariable Integer id) {
		Card card = cardService.getCard(id);
		return card;
	}

}
