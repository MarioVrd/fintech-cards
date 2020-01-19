package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.StatusChangeRepo;
import com.example.demo.model.Deletion;
import com.example.demo.model.StatusChange;
import org.springframework.stereotype.Service;



@Service
public class StatusChangeService {
	@Autowired
	StatusChangeRepo repo;
	public List<StatusChange> getAllChanges() {
		List<StatusChange> changes = new ArrayList<StatusChange>();
		repo.findAll().forEach(change -> changes.add(change));
		return changes;
	}



	public void saveOrUpdate(StatusChange change) {
		repo.save(change);
	}

	public void delete(int id) {
		repo.deleteById(id);
	}

	public Optional<StatusChange> getStatusChangeById(int id) {
		return repo.findById(id);
	}
	public Iterable<StatusChange> getByCard_id(int id){
		return repo.findAllByCard(id);
	}
	

}



