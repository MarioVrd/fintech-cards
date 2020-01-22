package com.example.demo.controller;
import com.example.demo.dao.CardRepo;
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
        
        @Autowired
	StatusChangeService statuschangeservice;
        
        @Autowired
	DeletionService deletionservice;
        	
	@GetMapping("/cards")
	public String getAllCards(Model model)
	{
		int id = 1;
		Iterable<Card> cards = cardservice.getActiveByOwner_Id(id);


		model.addAttribute("cards", cards);
		return "cards";
	}
        
        @GetMapping("/activate/{id}")
        public String activateCard(@PathVariable int id) {
            Card card = cardservice.getById(id);
            card.setActive(true);
            cardservice.saveOrUpdate(card);
            statuschangeservice.saveOrUpdate(new StatusChange(card, StatusChangeType.ACTIVATION));
            return "redirect:/cards";
        }
        
	@GetMapping("/card/{id}")
	public String getCard(Model model, @PathVariable int id) {
		Card card = cardservice.getById(id);
		model.addAttribute("card", card);
		return "card";
	}
        
        @GetMapping("/delete/{id}")
        public String deleteCard(@PathVariable int id) {
            Card card = cardservice.getById(id);
            card.setActive(false);
            card.setStatus(CardState.DELETED);
            cardservice.saveOrUpdate(card);
            deletionservice.saveOrUpdate(new Deletion(card));
            return "redirect:/cards";
        }

        @GetMapping("/freeze/{id}")
        public String freezeCard(@PathVariable int id) {
            Card card = cardservice.getById(id);
            card.setActive(false);
            cardservice.saveOrUpdate(card);
            statuschangeservice.saveOrUpdate(new StatusChange(card, StatusChangeType.DEACTIVATION));
            return "redirect:/cards";
        }
        
	@PostMapping("/modify/{id}")
	public String modifyCard(@ModelAttribute Card cardupdated, @PathVariable int id) {
            Card before = cardservice.getById(id);
            
            if (cardupdated.getPin() != null) {
                before.setPin(cardupdated.getPin());
                before.setPin_changes(before.getPin_changes() + 1);                
            }
            
            if (cardupdated.getPayment_limit() != 0) {
                before.setPayment_limit(cardupdated.getPayment_limit());
            }
            
            if (cardupdated.isContactless_payment()) {
                before.setContactless_payment(true);
            }
            
            if (cardupdated.isOnline_payment()) {
                before.setOnline_payment(true);
            }
            
            cardservice.saveOrUpdate(before);
            return "redirect:/card/" + id;		
	}
	
}