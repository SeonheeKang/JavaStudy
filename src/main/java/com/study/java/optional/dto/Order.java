package com.study.java.optional.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class Order {
	private Long id;
	private Date date;
	private Member member;
}
