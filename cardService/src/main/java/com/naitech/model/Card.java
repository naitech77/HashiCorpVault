package com.naitech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Card {
	@Getter
	@Setter
	@Id 
	private int id;
	
	@Getter 
	@Setter
	private String number;

}
