package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Card;
import com.example.demo.model.Deletion;
import com.example.demo.model.StatusChange;

public interface StatusChangeRepo extends CrudRepository<StatusChange, Integer>{
	public Iterable<StatusChange> findAllByCard(Card card);
	


}
