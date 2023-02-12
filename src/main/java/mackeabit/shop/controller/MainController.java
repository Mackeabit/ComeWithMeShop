package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.Login;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.CartsVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final CartService cartService;
    private final HttpServletRequest request;

    @RequestMapping("/")
    public String basic(@Login MembersVO membersVO, Model model) {

        if (membersVO == null) {
            return "index";
        }

        /** 세션이 있으면 model 에 정보 담아서 홈화면으로 이동
         * 회원정보 - members
         * 장바구니 - carts
         */
        model.addAttribute("members", membersVO);

        log.info("MembersVO = {}", membersVO);

/*        List<CartsVO> cartList = cartService.findAll(membersVO.getMember_idx());
        model.addAttribute("carts", cartList);*/

        model.addAttribute("cartsList", cartService.findMemberCart(membersVO.getMember_idx()));

        return "index";
    }


    /* 404 테스트용 */
    @RequestMapping("/404page")
    public void errorPageTest(HttpServletResponse response) throws IOException {
        response.sendError(404,"404오류");
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
        MembersVO findMember = memberService.findByEmail(email);

        log.info("findMember = {}", findMember);

        session.setAttribute(
                SessionConst.LOGIN_MEMBER,
                findMember
        );
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }




}
