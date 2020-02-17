package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true) // '조회' 할때만 사용, 조회 시 약간의 성능 최적화.
@RequiredArgsConstructor // final이 붙은 필드값만 생성자 주입을 해주는 역할.
public class MemberService {

    /*
     * 일반적으로 쓰는 @autowired는 테스트 시 mock개체를 주기 힘들기 때문에 불편, setter injection은 어플리케이션 실행 중에 바뀔 수 있는 가능성 존재.
     * 따라서 생성자 주입을 권장

        private final MemberRepository memberRepository; // final 중요

        @Autowired // 생성자가 하나만 있는 경우 @autowired 생략가능
        public MemberService(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }
      *
      *
      * 하지만 롬복을 이용하면 어노테이션으로 가능.
      */

    private final MemberRepository memberRepository;

    /*
     * 회원 가입
     */

    @Transactional // 기본적으로 트렌젝션 안에서 처리해야함. spring꺼를 써야 쓸 수 있는게 더 많기 때문에 추천
    public Long join(Member member){
        // 중복회원 확인
        validationDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(Member member) {
        // exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다."); // 이렇게 방어해도 멀티스레드 환경을 (동시에 같이 insert) 고려해서 DB에도 unique 제약조건을 걸어주는 것이 안전
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
