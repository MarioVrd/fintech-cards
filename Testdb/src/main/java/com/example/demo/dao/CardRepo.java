package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Card;

import antlr.collections.List;

public interface CardRepo extends CrudRepository<Card, Integer> {
	public Iterable<Card> findAllByOwner(int id);
	public Card findById(int id);
	public Card findByCardNumber(String card_number);
	@Query(value="SELECT * FROM card where status=1", nativeQuery=true)
	public Iterable<Card>findActiveByOwner(int id);
			

}
