package com.gld.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable {

	@CreatedBy
	@Column(nullable = false)
	private String createdBy;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedBy
	@Column(nullable = false)
	private String updatedBy;
	
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedDate;

}
