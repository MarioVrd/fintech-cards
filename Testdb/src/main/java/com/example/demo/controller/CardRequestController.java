package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Card;
import com.example.demo.model.CardRequest;
import com.example.demo.services.CardRequestService;
import com.example.demo.services.CardService;
import com.example.demo.services.JwtService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CardRequestController {
	@Autowired
	CardRequestService cardRequestService;
        
	@Autowired 
        CardService cardService;
        
        @Autowired
        JwtService jwtService;
        
	@GetMapping("/newrequest")
	public String newRequest(Model model) {
		model.addAttribute("request", new CardRequest());
		return "newrequest";
		
	}
	@PostMapping("/request")
	public String addRequest(@ModelAttribute CardRequest cardRequest, HttpServletRequest request) {
            String jwt = jwtService.getJwtFromRequest(request);
            Long id = jwtService.getIdFromJWT(jwt);
            String name = jwtService.getNameFromJWT(jwt);
            String surname = jwtService.getSurnameFromJWT(jwt);

            cardRequestService.saveOrUpdate(cardRequest);
            Card newcard = cardRequest.createCard(id, name, surname, cardRequest);
            cardService.saveOrUpdate(newcard);
            return "redirect:/cards";
	}

}
