package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CardRequestRepo;
import com.example.demo.model.Card;
import com.example.demo.model.CardRequest;

@Service
public class CardRequestService {
	@Autowired
	CardRequestRepo repo;
	public List<CardRequest> getAllCardRequests() {
		List<CardRequest> requests = new ArrayList<CardRequest>();
		repo.findAll().forEach(request -> requests.add(request));
		return requests;
	}



	public void saveOrUpdate(CardRequest req) {
		repo.save(req);
	}

	public void delete(int id) {
		repo.deleteById(id);
	}

	public Optional<CardRequest> getCardRequestById(int id) {
		return repo.findById(id);
	}

}



