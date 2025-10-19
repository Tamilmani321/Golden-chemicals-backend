package com.gld.entity;

import com.gld.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "party", 
indexes = {
        @Index(name = "idx_name", columnList = "name")
})
@Getter
@Setter
public class Party extends Auditable
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String name;
	
	private String mobileNumber;
	
	private String address;
	
	private Boolean activeFlag = true;

	@Override
	public String toString() {
		return "Party [id=" + id + ", name=" + name + ", mobileNumber=" + mobileNumber + ", address=" + address
				+ ", activeFlag=" + activeFlag + "]";
	}
	
	

}
