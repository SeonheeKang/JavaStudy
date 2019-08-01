package com.study.java.optional.dto.test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.study.java.optional.dto.test.Address;
import com.study.java.optional.dto.test.Buyer;

/**
 * https://dzone.com/articles/optional-anti-patterns
 */
@Slf4j
public class OptionalTest {
	private static final Address DEFAULT_ADDRESS = new Address("디폴트주소", "0000");

	/**
	 * Optional.of()
	 *      null이 아닌 객체를 담고 있는 Optional 객체를 생성
	 *      null이 넘어올 경우, NPE를 던지기 때문에 주의해서 사용해야 함
	 */
	@Test(expected = NullPointerException.class)
	public void optional_of() {
		Optional<Object> o = Optional.of(null);
	}

	/**
	 * Optional.ofNullable()
	 * null인지 아닌지 확신할 수 없는 객체를 담고 있는 Optional 객체를 생성
	 * null이 넘어올 경우, NPE를 던지지 않고 Optional.empty() 리턴
	 */
	@Test
	public void optional_ofNullable() {
		Optional<Object> o = Optional.ofNullable(null);
		assertFalse(o.isPresent());
	}

	/**
	 * Optional.emtpy()
	 *      비어있는 Optional 객체 리턴
	 *      Optional 내부적으로 미리 생성해놓은 싱글턴 인스턴스
	 */
	@Test
	public void optional_empty() {
		Optional<Object> empty = Optional.empty();
		Optional<Object> o = Optional.ofNullable(null);
		assertEquals(empty, o);
	}

	@Test
	public void optional_get_값이_존재하는_경우() {
		Buyer buyer = Buyer.builder()
		                    .name("김아무개")
		                    .address(new Address("서울", "12345"))
		                    .build();

		log.info("buyer: {}", buyer.toString());
		log.info("address: {}", buyer.getAddress().get());
	}

	/**
	 * Optional.get()
	 *      값이 있는 경우, 리턴
	 *      값이 없는데 get()하는 경우, 에러 발생함
	 */
	@Test(expected = NoSuchElementException.class)
	public void optional_get_값이_없는_경우() {
		Buyer buyer = buyerWithNoAddressFixture();
		log.info("Optional.get(): {}", buyer.getAddress().get());
	}

	/**
	 * Optional.orElse()
	 *      값이 있는 경우, 리턴
	 *      값이 없는 경우, 다른 값 리턴
	 */
	@Test
	public void optional_orElse_값이없는경우() {
		Buyer buyer = buyerWithNoAddressFixture();
		Address address = buyer.getAddress().orElse(DEFAULT_ADDRESS);

		log.info("Optional.orElse(): {}", address);
		assertEquals(DEFAULT_ADDRESS.getAddress(), address.getAddress());
		assertEquals(DEFAULT_ADDRESS.getPostNo(), address.getPostNo());
	}

	/**
	 * Optional.orElseGet()
	 *      값이 있는 경우, 리턴
	 *      값이 없는 경우, 다른 Optional.get()으로 대체
	 *
	 * e.g) JPAEntity.findById(id).orElseGet(Entity.new) // 없는 경우, 새로운 객체 생성해서 리턴
	 * e.g) Optional.ofNulllable(map.get(id)).orElseGet(() -> {
	 *     return map.first
	 * })
	 */
	@Test
	public void optional_orElseGet() {
		Buyer buyer = buyerWithNoAddressFixture();
		Address address = buyer.getAddress().orElseGet(() -> new Address("어떤주소", "0"));
		assertNotNull(address);
		log.info("address: {}", address);
	}

	/**
	 * Optional.orElseThrow
	 *      값이 있는 경우, 리턴
	 *      값이 없는 경우, 예외 던짐
	 */
	@Test(expected = RuntimeException.class)
	public void optional_orElseThrow() {
		Buyer buyer = buyerWithNoAddressFixture();
		buyer.getAddress().orElseThrow(() -> new RuntimeException("invalid address"));
	}

	private Buyer buyerWithNoAddressFixture() {
		Buyer buyer = Buyer.builder()
		                   .name("강아무개")
		                   .build();

		log.info("buyer: {}", buyer.toString());
		return buyer;
	}
}
