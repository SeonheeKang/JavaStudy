package com.study.java.optional.model;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class Buyer {

	private String name;
	private Address address;

	public Optional<Address> getAddress() {
		return Optional.ofNullable(address);
	}
}
