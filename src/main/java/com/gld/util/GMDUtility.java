package com.gld.util;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gld.common.TransactionType;
import com.gld.customException.ResourceNotFound;
import com.gld.dao.PartyRepo;
import com.gld.dao.TransactionRepo;
import com.gld.dto.TransactionDto;
import com.gld.entity.Party;
import com.gld.entity.Transaction;
import com.gld.mapper.TransactionMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class GMDUtility {
	
	@Autowired
	private PartyRepo partyRepo;
	
	@Autowired
	private  TransactionRepo transactionRepo;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	public Transaction processTransaction(TransactionDto transactionDto) {
		log.info(" processing transation ");
		Long pId = transactionDto.partyDto().id();
		TransactionType type = transactionDto.type();
		
		Party party = partyRepo.findById(pId)
				.orElseThrow(() -> new ResourceNotFound("Invalid User"));

		// current total amount 
		BigDecimal totalAmt = transactionRepo.getTotalTransactionAmountByPartyId(pId)
				.orElse(BigDecimal.ZERO);
		
		log.info(" totalAmt : "+totalAmt);
		log.info(" type : "+type);
		BigDecimal transactionAmt;
		BigDecimal currentBalance;
		
		// calculate transaction amount
		if (type.equals(TransactionType.DEBIT)) {
			transactionAmt = transactionDto.amount().abs();
			currentBalance = totalAmt.add(transactionAmt);
		} else {
			transactionAmt = transactionDto.amount().abs().negate();
			currentBalance = totalAmt.add(transactionAmt);
		}
		
		log.info(" currentBalance : "+currentBalance);
		Transaction transaction = new Transaction();
		transaction.setParty(party);
		transaction.setProduct(transactionDto.product());
		transaction.setRemark(transactionDto.remark());
		transaction.setAmount(transactionAmt);
		transaction.setBalance(currentBalance);
		transaction.setType(type);
		transaction.setTxDate(transactionDto.txDate());
		log.info(" transaction : "+transaction);
		return transaction;
	}
	
	public void recalculateBalances (Long partyId) {
		 List<Transaction> transactions = transactionRepo
			        .findByPartyIdOrderByTxDateAsc(partyId);
		 log.info("Transactions : "+transactions);
		 BigDecimal balance = BigDecimal.ZERO;
		 for (Transaction txn : transactions) {
			 log.info(" transaction : "+txn);
		        BigDecimal amount = txn.getAmount();
		        log.info("amount : "+amount);
		        if (txn.getType() == TransactionType.DEBIT) {
		            balance = balance.add(amount.abs());
		        } else if (txn.getType() == TransactionType.CREDIT) {
//		            balance = balance.subtract(amount.abs());
//		            transactionAmt = amount.abs().negate();
		            balance = balance.add(amount.abs().negate());
		        }
		        log.info("balance : "+balance);
		        txn.setBalance(balance);
		    }
		    transactionRepo.saveAll(transactions);
	}
	
//	@Transactional
//	public void recalculateBalances(Long partyId) {
//	    List<Transaction> transactions =
//	        transactionRepo.findByPartyIdOrderByTxDateAsc(partyId);
//
//	    BigDecimal balance = BigDecimal.ZERO;
//	    for (Transaction txn : transactions) {
//	        BigDecimal amount = txn.getAmount();
//
//	        if (txn.getType() == TransactionType.DEBIT) {
//	            balance = balance.add(amount.abs());
//	        } else {
//	            balance = balance.subtract(amount.abs());
//	        }
//
//	        txn.setBalance(balance);
//	    }
//
//	    transactionRepo.saveAll(transactions);
//	}
	
//	public Transaction processTransaction(TransactionDto transactionDto) {
//	    log.info("Processing transaction...");
//	    Long pId = transactionDto.partyDto().id();
//	    TransactionType type = transactionDto.type();
//
//	    Party party = partyRepo.findById(pId)
//	        .orElseThrow(() -> new ResourceNotFound("Invalid User"));
//
//	    Transaction transaction = new Transaction();
//	    transaction.setParty(party);
//	    transaction.setProduct(transactionDto.product());
//	    transaction.setRemark(transactionDto.remark());
//	    transaction.setAmount(transactionDto.amount().abs());
//	    transaction.setType(type);
//	    transaction.setTxDate(transactionDto.txDate());
//
//	    // Save it temporarily so it’s included in recalculation order
//	    Transaction saved = transactionRepo.save(transaction);
//
//	    // ✅ Recalculate all balances in one place
//	    recalculateBalances(pId);
//
//	    // Return the saved transaction (now with updated balance)
//	    return saved;
//	}



}
