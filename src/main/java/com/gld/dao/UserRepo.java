package com.gld.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gld.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByName(String name);

}
