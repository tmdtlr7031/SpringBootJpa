//package jpabook.jpashop;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//
//    // 테스트에 적용된 경우, test끝내고 데이터를 롤백해버림. 따라서 DB에 테스트 데이터가 없음.
//    // 만약 테스트 데이터 롤백을 안하려면 @RollBack(false) 어노테이션 추가하면 됨.
//    @Transactional
//    @Rollback(false)
//    public void testMember() throws Exception{
//        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member); // 결과 : true
//                                                            // 왜? 같은 트렉젝션 안에서 저장하고 조회하면 영속성 컨텍스트가 같고 그럼 id값이 같기 때문.
//                                                           // 즉, 1차 캐쉬에서 가져오기 때문에 같음
//    }
//}