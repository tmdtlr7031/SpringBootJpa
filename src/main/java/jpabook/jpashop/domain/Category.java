package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", //  중간 테이블
               joinColumns = @JoinColumn(name = "category_id"), // 카테고리쪽 테이블 맵핑
               inverseJoinColumns = @JoinColumn(name = "item_id")) // 아이템쪽 테이블 맵핑
    private List<Item> items = new ArrayList<>();


    // 계층구조?로 나타낼수있다는걸 보여주기위함.
    // 그냥 일반적으로 관계설정해주면 됨.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
