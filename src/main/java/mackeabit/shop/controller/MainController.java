package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String basic() {
        return "index";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "sign-up";
    }

    //트랜잭션 적용 전
    @PostMapping("/signup")
    @ResponseBody
    public String signUpCheck(SignUpDTO signUpDTO) {

        int res = memberService.saveMembers(signUpDTO);
        String data = "N";
        if (res > 0) {
            data = "Y";
        }
        return data;
    }

    @RequestMapping("/login")
    public String loginpage() {
        return "login";
    }
}
