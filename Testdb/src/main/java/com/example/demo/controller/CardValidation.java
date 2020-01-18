package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Card;
import com.example.demo.model.CardInfo;
import com.example.demo.services.CardService;

@RestController
public class CardValidation {
	@Autowired
	CardService service;
	@PostMapping("/cards/isinfovalid")
	public boolean cardInfoChecker(@RequestBody CardInfo info) {
		Card card = service.getByCardNumber(info.getCard_number());
		return info.checkCardValidity(card);
		
		
	}

}
