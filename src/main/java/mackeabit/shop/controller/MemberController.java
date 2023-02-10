package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(String email) {
        return memberService.emailCheck(email);
    }
}
