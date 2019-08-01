package com.study.java.optional.dto.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Address {
	private String address;
	private String postNo;

	public Address(String address, String postNo) {
		this.address = address;
		this.postNo = postNo;
	}
}
