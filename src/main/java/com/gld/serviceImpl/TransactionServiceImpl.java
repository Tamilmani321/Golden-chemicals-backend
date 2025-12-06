package com.gld.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gld.common.TransactionType;
import com.gld.customException.ResourceNotFound;
import com.gld.dao.PartyRepo;
import com.gld.dao.TransactionRepo;
import com.gld.dto.TransactionDto;
import com.gld.entity.Transaction;
import com.gld.mapper.TransactionMapper;
import com.gld.service.TransactionService;
import com.gld.util.GMDUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private PartyRepo partyRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private GMDUtility gmdUtility;


	@Override
	@CacheEvict(value = "transaction", key = "#transactionDto")
	public TransactionDto save(TransactionDto transactionDto) {
		Transaction transaction = gmdUtility.processTransaction(transactionDto);
		transaction = transactionRepo.save(transaction);
		return transactionMapper.entityToDto(transaction);
	}

	@Override
//	@Cacheable(
//		    value = "transactionsByParty", 
//		    key = "#pId + '_' + #pageNo + '_' + #size"
//		)
	public Page<Transaction> getTransactionByPartyIdDesc(Long pId, int pageNo, int size) {
		log.info(" fetching Transaction Data by Id : "+pId);
		PageRequest pageable = PageRequest.of(pageNo, size);
		return transactionRepo.findByPartyIdOrderByCreatedDateDesc(pId, pageable);
	}
	
	public List<Transaction> getTransactionByPartyIdDesc(Long pId) {
		log.info(" fetching Transaction Data by Id : "+pId);
		return transactionRepo.findTransactionsByParty(pId);
	}
	
	
	

	@Override
	@CachePut(
		    value = "modified", 
		    key = "#transactionDto.id")
	public TransactionDto updateTransaction(TransactionDto transactionDto) {
		Long txId = transactionDto.id();
		Transaction existingTxn = transactionRepo.findById(txId)
				.orElseThrow(() -> new ResourceNotFound("Invalid Transaction Id"));
		Long pId = existingTxn.getParty().getId();
		Optional<Transaction> previousTxn = transactionRepo.getPreviousTxn(pId, txId);
//		BigDecimal totalAmt = transactionRepo.getTotalTransactionAmountByPartyId(pId)
//				.orElse(BigDecimal.ZERO);
		
		BigDecimal previousBalance = previousTxn.isPresent() ? previousTxn.get().getBalance() : BigDecimal.ZERO;
		TransactionType type = transactionDto.type();
		
		existingTxn.setTxDate(transactionDto.txDate());
		existingTxn.setProduct(transactionDto.product());
		existingTxn.setRemark(transactionDto.remark());
		existingTxn.setType(type);
		
		BigDecimal transactionAmt;
		BigDecimal currentBalance;
		if (type.equals(TransactionType.DEBIT)) {
			transactionAmt = transactionDto.amount().abs();
			currentBalance = previousBalance.add(transactionAmt);
		} else {
			transactionAmt = transactionDto.amount().abs().negate();
			currentBalance = previousBalance.add(transactionAmt);
		}
		
		existingTxn.setAmount(transactionAmt);
		existingTxn.setBalance(currentBalance);
		transactionRepo.save(existingTxn);
		gmdUtility.recalculateBalances(existingTxn.getParty().getId());
		return transactionMapper.entityToDto(existingTxn);
	}

	@Override
	@CacheEvict(value = "transactions", key = "#txId")
	public void deleteTransaction(Long txId) {
		Transaction txn = transactionRepo.findById(txId)
				.orElseThrow(() -> new ResourceNotFound("Invalid Transaction Id"));
		Long pId = txn.getParty().getId();
		transactionRepo.deleteById(txId);
		gmdUtility.recalculateBalances(pId);
	}

	@Override
	@CacheEvict(value = "transactions", key = "#txIds")
	public void deleteSelectedTransactions(List<Long> txIds) {
		for (Long id : txIds) {
	        transactionRepo.deleteById(id);
	        cacheManager.getCache("transactions").evict(id); // manual eviction
	    }
	}
	
	@Override
	public List<TransactionDto> getTransactionByPartyIdAsc(Long pId) {
		// TODO Auto-generated method stub
		return null;
	}

}
