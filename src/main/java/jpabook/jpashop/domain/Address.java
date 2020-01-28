package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/* 값타입은 여기저기서 변경이 되어선 안된다.
 * 따라서 setter를 제공하지 않는게 좋다. 수정이 필요하면 생성자를 통해서 값을 넣거나 값을 복사해서 사용
 * 주의할 점은 기본생성자가 반드시 필요하며, 이때 접근 범위로 protected도 허용된다. public보다 훨씬 안전할 것이다.
*/
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
