package com.study.java.optional.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class Member {

	private Long id;
	private String name;
	private Address address;

}
