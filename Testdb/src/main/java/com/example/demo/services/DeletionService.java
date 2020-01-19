package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dao.DeletionRepo;
import com.example.demo.model.Deletion;
import org.springframework.stereotype.Service;

@Service
public class DeletionService {
	@Autowired
	DeletionRepo repo;
	public List<Deletion> getallDeletions() {
		List<Deletion> deletions = new ArrayList<Deletion>();
		repo.findAll().forEach(deletion -> deletions.add(deletion));
		return deletions;
	}



	public void saveOrUpdate(Deletion deletion) {
		repo.save(deletion);
	}

	public void delete(int id) {
		repo.deleteById(id);
	}

	public Optional<Deletion> getCardRequestById(int id) {
		return repo.findById(id);
	}
	public Iterable<Deletion> getByCard_id(int id){
		return repo.findAllByCard(id);
	}


}
