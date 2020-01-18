package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Card;
import com.example.demo.model.CardRequest;
import com.example.demo.services.CardService;

@Controller
public class CardController {

	@Autowired
	CardService cardservice; 
	
	@GetMapping("/cards")
	public String getAllCards(Model model)
	{
		int id = 1;
		Iterable<Card> cards = cardservice.getAllByOwner_Id(id);
		model.addAttribute("cards", cards);
		return "cards";
	}
	
}