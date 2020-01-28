/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dao;

import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mario
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByUsername(String username);
}
