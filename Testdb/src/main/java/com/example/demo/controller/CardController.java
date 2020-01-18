package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Card;
import com.example.demo.model.CardRequest;
import com.example.demo.model.Deletion;
import com.example.demo.model.StatusChange;
import com.example.demo.model.enums.CardState;
import com.example.demo.model.enums.StatusChangeType;
import com.example.demo.services.CardService;
import com.example.demo.services.DeletionService;
import com.example.demo.services.StatusChangeService;

@Controller
public class CardController {

	@Autowired
	CardService cardservice; 
	StatusChangeService statuschangeservice;
	DeletionService deletionservice;
	
	@GetMapping("/cards")
	public String getAllCards(Model model)
	{
		int id = 1;
		Iterable<Card> cards = cardservice.getAllByOwner_Id(id);
		model.addAttribute("cards", cards);
		return "cards";
	}
	@GetMapping("/card/{id}")
	public String getCard(Model model, @PathVariable int id) {
		Card card = cardservice.getById(id);
		model.addAttribute("card", card);
		return "card";
	}
	@PostMapping("/modify")
	public String modifyCard(@ModelAttribute Card cardupdated ) {
		Card before = cardservice.getById(cardupdated.getId());
		if (cardupdated.getStatus() != before.getStatus()) {
			if (cardupdated.getStatus() == CardState.ALIVE) {
				statuschangeservice.saveOrUpdate(new StatusChange(cardupdated, StatusChangeType.ACTIVATION));
			}
			else 
				statuschangeservice.saveOrUpdate(new StatusChange(cardupdated, StatusChangeType.DEACTIVATION));
		}
		if (cardupdated.isActive() != before.isActive()) {
			if (!cardupdated.isActive()) {
				deletionservice.saveOrUpdate(new Deletion(cardupdated));
			}
		}
			
		
		cardservice.saveOrUpdate(cardupdated);
		return "redirect:/cards";
		
	}
	
}