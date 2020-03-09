package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아래 주석된 protected 와 같은 의미
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문당시가격
    private int count; // 주문수량

    // 다른곳에서 생성 메서드 안쓰고 new OrderItem(); 하는 경우
    // 생성 하는 스타일이 다양해져서 유지보수 힘듦. 그래서 이를 방지하는 것
//    protected OrderItem() {
//    }

    // === 생성 메서드 ===
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStack(count); // 주문 시 재고 줄여주는 것

        return orderItem;
    }

    // === 비즈니스 로직 ===
    public void cancel() {
        // 재고를 다시 원상복구 시킴 : 주문수량만큼 재고에 더해줌
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
