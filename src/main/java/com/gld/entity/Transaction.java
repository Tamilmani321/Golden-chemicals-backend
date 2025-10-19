package com.gld.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gld.audit.Auditable;
import com.gld.common.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction",
		indexes = {
			@Index(name = "idx_tx_party_date", columnList = "party_id, tx_date"),
		    @Index(name = "idx_tx_type", columnList = "type")
		})

@Setter
@Getter
public class Transaction extends Auditable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime txDate;
	
	private String product;
	
	private String remark;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType type;
	
	@Column(nullable = false, precision = 20, scale = 2)
	private BigDecimal amount;
	
	@Column(nullable = false, precision = 20, scale = 2)
	private BigDecimal balance;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Party party;

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", txDate=" + txDate + ", product=" + product + ", remark=" + remark
				+ ", type=" + type + ", amount=" + amount + ", balance=" + balance + ", party=" + party + "]";
	}

}
