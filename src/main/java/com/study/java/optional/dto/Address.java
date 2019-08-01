package com.study.java.optional.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {
	public static final String UNKNOWN_CITY = "UNKNOWN";

	private String street;
	private String city;
	private String zipcode;
}
