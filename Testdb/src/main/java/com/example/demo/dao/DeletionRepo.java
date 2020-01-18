package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Deletion;

public interface DeletionRepo extends CrudRepository<Deletion, Integer> {
	public Iterable<Deletion> findAllByCard(int id);

}
