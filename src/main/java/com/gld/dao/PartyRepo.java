package com.gld.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gld.entity.Party;

public interface PartyRepo extends JpaRepository<Party, Long> 
{

}
	