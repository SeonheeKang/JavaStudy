package com.study.java.optional;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.junit.BeforeClass;
import org.junit.Test;

import com.study.java.optional.dto.Address;
import com.study.java.optional.dto.Member;
import com.study.java.optional.dto.Order;
import com.study.java.optional.service.OrderService;

/**
 * 자바8 Optional 1부: 빠져나올 수 없는 null 처리의 늪: https://www.daleseo.com/java8-optional-before/
 * 자바8 Optional 2부: null을 대하는 새로운 방법: https://www.daleseo.com/java8-optional-after/
 * 자바8 Optional 3부: Optional을 Optional답게: https://www.daleseo.com/java8-optional-effective/
 */
@Slf4j
public class OderServiceTest {

	private static OrderService orderService;

	@BeforeClass
	public static void before() {
		log.info("before: init orderService");
		orderService = new OrderService();
	}

	@Test
	public void order가_null인_전통적인_NULL체크방식과_Optional방식_비교() {
		Order order = null;
		String city1 = orderService.getCity(order);
		String city2 = orderService.getCityByOptional(order);

		log.info("city1: {}", city1);
		log.info("city2: {}", city2);

		assertEquals(city1, city2);
	}

	@Test
	public void city가_null이_아닌경우_전통적인_NULL체크방식과_Optional방식_비교() {
		Order order = Order.builder()
		                   .id(1L)
		                   .date(new Date())
		                   .member(Member.builder()
		                                 .id(1L)
		                                 .name("김아무개")
		                                 .address(Address.builder().city("경기도").street("판교역로").build())
		                                 .build())
		                   .build();

		String city1 = orderService.getCity(order);
		String city2 = orderService.getCityByOptional(order);
		log.info("city1: {}", city1);
		log.info("city2: {}", city2);

		assertEquals(city1, city2);
	}

	@Test
	public void city글자수비교_optional안티패턴과_Optional잘사용한경우() {
		Order order = Order.builder()
		                   .id(1L)
		                   .date(new Date())
		                   .member(Member.builder()
		                                 .id(1L)
		                                 .name("김아무개")
		                                 .address(Address.builder().city("경기도").street("판교역로").build())
		                                 .build())
		                   .build();

		String city = orderService.getCityByOptional(order);

		int length1 = orderService.lengthOfCityByOptionalAntiPattern(city);
		int length2 = orderService.lengthOfCityByOptional(city);
		log.info("city.length: {}", length1);
		log.info("city.length: {}", length2);

		assertEquals(length1, length2);
	}

	@Test
	public void 특정시간내에_주문한_멤버반환_비교() {
		Order order = Order.builder()
		                   .id(1L)
		                   .date(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).minusMinutes(10).toInstant()))
		                   .member(Member.builder()
		                                 .id(1L)
		                                 .name("김아무개")
		                                 .address(Address.builder().city("경기도").street("판교역로").build())
		                                 .build())
		                   .build();

		Member member1 = orderService.getMemberIfOrderWithin(order, 5);
		Optional<Member> member2 = orderService.getMemberIfOrderWithinByOptional(order, 5);
		log.info("member1: {}", member1);
		log.info("member2: {}", member2);


		assertNull(member1);
		assertEquals(Optional.empty(), member2);
	}

	@Test
	public void null_반환비교() {

	}
}
