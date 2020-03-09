package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // === 비즈니스 로직 ===
    // 왜? 재고 관련 데이터를 가지고있는 곳(stockQuantity 가 있기 때문)에 해당 메서드가 있어야 응집력도 좋아짐. 여기저기 set하는건 소스추적도 힘듦
    // 따라서 재고 관련된 로직은 여기 작성된 메서드를 통해서 변경이 되야하면 관련 방어코드는 메서드에 작성하면 됨.

    // stock 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // stock 감소
    public void removeStack(int quantity) {
        int restStock = this.stockQuantity - quantity;
        // 남은 재고가 0보다 작은 경우 예외
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
