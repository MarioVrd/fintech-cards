package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Card;
import com.example.demo.model.Code;
import com.example.demo.model.Deletion;
import com.example.demo.model.StatusChange;
import com.example.demo.model.enums.CardForm;
import com.example.demo.model.enums.CardState;
import com.example.demo.model.enums.StatusChangeType;
import com.example.demo.services.CardService;
import com.example.demo.services.DeletionService;
import com.example.demo.services.JwtService;
import com.example.demo.services.StatusChangeService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CardController {
	

    @Autowired
    CardService cardservice; 

    @Autowired
    StatusChangeService statuschangeservice;

    @Autowired
    DeletionService deletionservice;
    
    @Autowired
    JwtService jwtService;
    
    @GetMapping("/cards")
    public String getAllCards(Model model, HttpServletRequest request)
    {	
        String jwt = jwtService.getJwtFromRequest(request);
        Long id = jwtService.getIdFromJWT(jwt);
        Iterable<Card> cards = cardservice.getActiveByOwner_Id(id);
        model.addAttribute("cards", cards);
        return "cards";
    }
    @PostMapping("/activation/{id}")
    public String activationPhysical(@PathVariable int id, @ModelAttribute Code code) {
        Card card = cardservice.getById(id);
        System.out.println("CODE IS " + card.getCode() + " " + code.getCode());
        if (code.checkCardCode(card)) {
                card.setActive(true);
        cardservice.saveOrUpdate(card);
        statuschangeservice.saveOrUpdate(new StatusChange(card, StatusChangeType.ACTIVATION));
                return "redirect:/cards";
        }
        return "redirect:/activatephysical/" + id;
    }

    @GetMapping("/activatephysical/{id}")
    public String activatePhysical(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("code", new Code());
        return "activate.html";
    }
        
    @GetMapping("/activate/{id}")
    public String activateCard(@PathVariable int id) {
        Card card = cardservice.getById(id);
        int counter = 0;
        Iterable<StatusChange> cards = statuschangeservice.getByCard(card);
        for (StatusChange s : cards)
        	counter++;
        
		if (card.getForm() == CardForm.PHYSICAL && counter == 0) {
        	return "redirect:/activatephysical/" + id;
         }
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
        if (cardupdated.getPinRaw() != null) {
            before.updatePin(cardupdated.getPinRaw());
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