package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.model.CardRequest;

import antlr.collections.List;

@RepositoryRestResource(collectionResourceRel = "CardRequest", path = "CardRequest")
public interface CardRequestRepo extends CrudRepository<CardRequest, Integer> {
	


	
}
