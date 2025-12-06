package com.gld.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gld.dto.TransactionDto;
import com.gld.entity.Transaction;
import com.gld.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gmd/transaction")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<TransactionDto> save(@RequestBody TransactionDto transactionDto){
		log.info(" Requested Transaction Data Saving  : "+transactionDto);
		TransactionDto responseDto =  transactionService.save(transactionDto);
		log.info(" Response Transaction Data Saving  : "+responseDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
	
	@GetMapping("/{pId}")
	public Page<Transaction> getTransactionByPid (
			@PathVariable Long pId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size
			){
		log.info(" Requested Transaction Data Fetching  : "+pId);
		return transactionService.getTransactionByPartyIdDesc(pId, page, size);
	}
	
	@GetMapping("/all/{pId}")
	public List<Transaction> getTransactionByPid (@PathVariable Long pId){
		log.info(" Requested All Transaction Data Fetching  : "+pId);
		return transactionService.getTransactionByPartyIdDesc(pId);
	}
	
	@PutMapping 
	public ResponseEntity<TransactionDto> updateTransaction(@RequestBody TransactionDto transactionDto){
		log.info(" Requested Transaction Data Updating  : "+transactionDto);
		TransactionDto responseDto = transactionService.updateTransaction(transactionDto);
		log.info(" Response Transaction Data Updating  : "+responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
		
	}
	
	@DeleteMapping("/{txId}")
	public ResponseEntity<Map<String, String>> deleteTransaction(@PathVariable Long txId){
		log.info(" Requested Transaction Data Deleting  : "+txId);
		transactionService.deleteTransaction(txId);
		return ResponseEntity.ok(Map.of("message", "Deleted"));
	}
	
	@DeleteMapping("/selected/{txIds}")
	public ResponseEntity<String> deleteTransaction(@PathVariable List<Long> txIds){
		log.info(" Requested Transaction Data Deleting  : "+txIds);
		transactionService.deleteSelectedTransactions(txIds);
		return ResponseEntity.status(HttpStatus.OK).body("Delted Selected Records");
	}

}
