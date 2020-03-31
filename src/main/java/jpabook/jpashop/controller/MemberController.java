package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // 유효성 걸린거 다음에 BindingResult가 있으면 여기에 에러가 담기고 정상 실행이됨. (원래는 팅겨져서 에러페이지랑 에러 로그)

        /*
        * member 엔티티를 안쓰고 굳이 Form 클래스를 만들어 사용하는 이유?
        *   - 안그래도 도메인 주도 설계로 복잡한데 더 지져분 해짐.
        *   - DB상에서 원하는 유효성(엔티티기준)과 화면단에서 원하는 유효성이 다를 수 있음(뷰기준) => 따라서 그 화면에 맞는 Form 클래스를 만들어서 이용하는게 좋다.
        **/

        // 유효성에서 걸려서 에러가 담겨져있다면
        if (result.hasErrors()) {
            return "members/createMemberForm"; // MemberForm form을 가져가서 유효성 걸린거 빼고 입력해놨던 값 기입되어있음.
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers(); // 여기도 역시 엔티티 보단 DTO(VO)를 이용하는게 좋음. 지금은 예시용이라..
                                                           // API만들때는 절대 엔티티 반환 금지. => 정보노출, 로직을 추가하면 API 스펙 자체가 바뀌게됨.
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
