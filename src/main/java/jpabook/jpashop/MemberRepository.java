package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    // 스프링컨테이너에서 해당 어노테이션이 붙어있으면 entitymanager를 주입해줌
    // 엔티티 매니저 생성 전에 엔티티 팩토리를 생성해야하는데 이건 다 springboot에서 알아서 (spring-data-jpa가!)
    @PersistenceContext
    private EntityManager em;

    // 저장하는 코드
    public  Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
