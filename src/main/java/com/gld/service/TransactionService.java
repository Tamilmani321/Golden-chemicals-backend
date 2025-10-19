package com.gld.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.gld.dto.TransactionDto;
import com.gld.entity.Transaction;

public interface TransactionService {

	TransactionDto save(TransactionDto transactionDto);

	Page<Transaction> getTransactionByPartyIdDesc(Long pId, int page, int size);

	List<TransactionDto> getTransactionByPartyIdAsc(Long pId);

	TransactionDto updateTransaction(TransactionDto transactionDto);

	void deleteTransaction(Long txId);

	void deleteSelectedTransactions(List<Long> txIds);
}
