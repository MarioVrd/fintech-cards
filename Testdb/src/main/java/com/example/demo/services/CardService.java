package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CardRepo;
import com.example.demo.model.Card;
import java.util.List;

@Service
public class CardService {

	@Autowired
	CardRepo cardrepo;

	public List<Card> getAllCards() {
		List<Card> cards = new ArrayList<Card>();
		cardrepo.findAll().forEach(card -> cards.add(card));
		return cards;
	}

	public Iterable<Card> getAllByOwner_Id(int id) {
		return cardrepo.findAllByOwner(id);
	}
	public Iterable<Card> getActiveByOwner_Id(Long id) {
		return cardrepo.findActiveByOwner(id);
	}

	public void saveOrUpdate(Card card) {
		cardrepo.save(card);
	}

	public void delete(int id) {
		cardrepo.deleteById(id);
	}

	public Card getById(int id) {
		return cardrepo.findById(id);
	}
	public Card getByCardNumber(String cardnum) {
		return cardrepo.findByCardNumber(cardnum);
	}

}
