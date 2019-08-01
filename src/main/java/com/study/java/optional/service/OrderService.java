package com.study.java.optional.service;

import java.util.Optional;

import com.study.java.optional.dto.Address;
import com.study.java.optional.dto.Member;
import com.study.java.optional.dto.Order;

public class OrderService {

	/**
	 * getCity Version1.
	 *
	 * 전통적인(?) NPE 방어 패턴: 사방에서 return 하기
	 * @param order
	 * @return
	 */
	public String getCity(Order order) {
		if (order == null) {
			return Address.UNKNOWN_CITY;
		}

		Member member = order.getMember();
		if (member == null) {
			return Address.UNKNOWN_CITY;
		}

		Address address = member.getAddress();
		if (address == null) {
			return Address.UNKNOWN_CITY;
		}

		String city = address.getCity();
		if (city == null) {
			return Address.UNKNOWN_CITY;
		}

		return city;
	}

	/**
	 * getCity Version2.
	 *
	 * Stream처럼 사용하기
	 * - Optional을 최대 1개의 원소를 가지고 있는 특별한 Stream이라고 생각
	 * - Stream 클래스가 가지고 있는 map()이나 flatMap(), filter()와 같은 메소드를 Optional도 가지고 있음
	 *
	 * -> 단순한 메소드 체이닝으로 모두 대체함
	 * -> 고통스러운 null 처리를 직접하지 않고 Optional 클래스에 위임함
	 *
	 * @param order
	 * @return
	 */
	public String getCityByOptional(Order order) {
		return Optional.ofNullable(order)
		               .map(Order::getMember)
		               .map(Member::getAddress)
		               .map(Address::getCity)
		               .orElse(Address.UNKNOWN_CITY);
	}

	// ------------------------------------------------------------------

	/**
	 * lengthOfCity Version1.
	 *
	 * Optional 적용 후 null 체크를 하는 방식
	 *      아래와 같은 방식으로 Optional을 사용하게 되면 Java8 이전에 직접 null 체크를 하던 코딩 수준에서 크게 벗어나지 못하게 됩니다.
	 *
	 * @param city
	 * @return
	 */
	public int lengthOfCityByOptionalAntiPattern(String city) {
		Optional<String> cityOpt = Optional.ofNullable(city);
		if (cityOpt.isPresent()) {
			return cityOpt.get().length();
		} else {
			return 0;
		}
	}

	/**
	 * lengthOfCity Version2.
	 *
	 * Optional 적용후에는 null 체크를 할 필요가 없으니 안하면됨
	 *
	 * @param city
	 * @return
	 */
	public int lengthOfCityByOptional(String city) {
		return Optional.ofNullable(city)
		               .map(String::length)
		               .orElse(0);
	}

	// ------------------------------------------------------------------

	/**
	 * getMemberIfOrderWithin Version1.
	 *
	 * 주어진 시간(분) 내에 생성된 주문을 한 경우에만 해당 회원 정보를 구하는 메소드
	 *
	 * @param order
	 * @param min
	 * @return
	 */
	public Member getMemberIfOrderWithin(Order order, int min) {
		if (order.getDate() != null && isWithinMinutesOrder(order, min)) {
			return order.getMember();
		}

		return null;
	}

	/**
	 * getMemberIfOrderWithin Version2.
	 *
	 * 주어진 시간(분) 내에 생성된 주문을 한 경우에만 해당 회원 정보를 구하는 메소드
	 * 메소드의 리턴 타입을 Optional로 사용함으로써 호출자에게 해당 메소드가 null을 담고 있는 Optional을 반환할 수도 있다는 것을 명시적으로 알려준다.
	 *
	 * @param order
	 * @param min
	 * @return
	 */
	public Optional<Member> getMemberIfOrderWithinByOptional(Order order, int min) {
		return Optional.ofNullable(order)
		        .filter(o -> isWithinMinutesOrder(o, min))
		        .map(Order::getMember);
	}

	private boolean isWithinMinutesOrder(Order order, int min) {
		return beforeMinutes(min) < order.getDate().getTime();
	}

	private long beforeMinutes(int min) {
		return System.currentTimeMillis() - (min * 1000);
	}
}
