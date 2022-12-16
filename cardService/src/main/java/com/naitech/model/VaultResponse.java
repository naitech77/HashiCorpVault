package com.naitech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class VaultResponse {

	@Getter
	@Setter
	@JsonIgnore
	private String request_id;
	@Getter
	@Setter
	@JsonIgnore
	private String lease_id;
	@Getter
	@Setter
	@JsonIgnore
	private boolean renewable;
	@Getter
	@Setter
	@JsonIgnore
	private int lease_duration;
	@Getter
	@Setter
	private Data data;
	@Getter
	@Setter
	@JsonIgnore
	private String wrap_info;
	@Getter
	@Setter
	@JsonIgnore
	private String warnings;
	@Getter
	@Setter
	@JsonIgnore
	private String auth;

}
