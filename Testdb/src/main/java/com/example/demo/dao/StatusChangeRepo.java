package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Deletion;
import com.example.demo.model.StatusChange;

public interface StatusChangeRepo extends CrudRepository<StatusChange, Integer>{
	public Iterable<StatusChange> findAllByCard(int id);

}
