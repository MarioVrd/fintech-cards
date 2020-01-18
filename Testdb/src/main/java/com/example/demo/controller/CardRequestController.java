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

@Controller
public class CardRequestController {
	@Autowired
	CardRequestService service;
	@Autowired CardService cardservice;
	@GetMapping("/newrequest")
	public String newRequest(Model model) {
		model.addAttribute("request", new CardRequest());
		return "newrequest";
		
	}
	@PostMapping("/request")
	public String addRequest(@ModelAttribute CardRequest request ) {
		int ownerid = 1;
		service.saveOrUpdate(request);
		Card newcard = request.createCard(ownerid, request);
		cardservice.saveOrUpdate(newcard);
		return "redirect:/cards";

		
	}

}
