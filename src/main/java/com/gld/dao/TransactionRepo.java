package com.gld.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gld.common.CustomQueries;
import com.gld.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long>
{
	
	 List<Transaction> findByPartyIdOrderByCreatedDateAsc(Long partyId);
	 
	 Page<Transaction> findByPartyIdOrderByCreatedDateDesc(Long partyId, Pageable page);
	 
	 @Query(CustomQueries.GET_TRANSACTIONS)
	    List<Transaction> findTransactionsByParty(@Param("partyId") Long partyId);
	 
	 @Query(CustomQueries.GET_TOTAL_AMOUNT)
	 	Optional<BigDecimal> getTotalTransactionAmountByPartyId (Long partyId);
	 
	 
	 @Query(CustomQueries.GET_PREVIOUS_TXN)
	 	Optional<Transaction> getPreviousTxn (@Param("partyId") Long partyId,
                @Param("currentId") Long currentId);
}
