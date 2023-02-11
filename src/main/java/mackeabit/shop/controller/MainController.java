package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    @RequestMapping("/")
    public String basic(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MembersVO membersVO,
            Model model) {

        if (membersVO == null) {
            return "index";
        }

        //세션이 있으면 model 에 담아서 홈화면으로 이동
        model.addAttribute("members", membersVO);
        return "index";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "sign-up";
    }

    //트랜잭션 적용 전
    @PostMapping("/signup")
    @ResponseBody
    public String signUpCheck(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {

        int res = memberService.saveMembers(signUpDTO);
        String data = "N";
        if (res > 0) {
            data = "Y";
        }
        return data;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/loginSession")
    public String loginSession(String email) {

        HttpSession session = request.getSession();

        session.setAttribute(
                SessionConst.LOGIN_MEMBER,
                memberService.findByEmail(email)
        );
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logOut() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
