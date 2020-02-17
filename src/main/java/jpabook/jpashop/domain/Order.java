package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /*
    *   order기준 N:1 member기준 1:N이라 양쪽다 어노테이션 걸면
    *   jpa는 어떤 엔티티를 기준으로 잡을지 판단 X
    *   이때 member를 기준으로 잡으면 유지보수 힘듦 + 성능 문제
    *   (해당 회원의 주문이 10개면 회원 정보가 바뀌는 경우 주문테이블이 업데이트 되게 되고 이는 코드상에서 확인하기 힘들뿐만 아니라 update 쿼리가 하나 더 나가는 것이 되기 때문)
    *
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order를 persist하고자 할 때,
                                                             // orderitems가 여러개인 경우 각각 persist 후 order를 persist해야하는데
                                                            // cascade를 쓰면 order만 persist해도 알아서 됨. 삭제도 동일하게 특정 order만 지우면 헤당하는 orderitems도 지워짐
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 1:1 관계인 경우 조회를 많이 하는 쪽에 기준점을 두면됨. 여기서는 주문에 따른 배송정보를 본다는 가정.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    /*
    *   연관관계 편의 메서드
    *   : 양방향 맵핑이 되어있는 경우, 양쪽에서 접근이 가능해야하고 그때그때 넣어주면 빼먹을 수 있기때문에 편의상 양쪽다 넣어주는 메서드를 만듦
    *   : 나중에 더 나아가서는 편의 메서드 내에서 이미 등록이 되어있는지 검증도 해야함..!
    *   : 연관관계 주인쪽에 만들어주는걸 추천. 찾기도 편하고..
    **/
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
