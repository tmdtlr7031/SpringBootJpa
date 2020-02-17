package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // @Embedded 또는 @Embeddable 한 쪽에만 붙여줘도 됨. 여기선 그냥 양쪽 다 붙임.
    private Address address;

    @OneToMany(mappedBy = "member") // 읽기전용, name은 order의 맵핑된 필드값
    private List<Order> orders = new ArrayList<>();
}
