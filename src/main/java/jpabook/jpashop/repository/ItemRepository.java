package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId() == null) {
            // 신규등록인 경우
            em.persist(item);
        } else {
            // db에 조회결과가 있는 경우
            // merge는 파라미터로 업데이트 됨. 원하는 컬럼만이 아니라 넘어온 파라미터 전부다..
            // 따라서 merge는 쓰지않는다를 깔고 가자. 여기선 예제니깐..
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                 .getResultList();
    }
}
