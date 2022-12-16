package com.naitech.model;

import lombok.Getter;
import lombok.Setter;

public class Data {

	@Getter
	@Setter
	private String ciphertext;
	@Getter
	@Setter
	private int key_version;

}
