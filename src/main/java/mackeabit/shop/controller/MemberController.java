package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(String email) {
        return memberService.emailCheck(email);
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public String emailCheck(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {
        return memberService.checkID(signUpDTO);
    }

    @RequestMapping("/coupon")
    @ResponseBody
    public String couponCK(String coupon_number) {

        String data = "N";

        if (coupon_number.equals("") || coupon_number == null) {
            return data;
        }

        /*
        *  쿠폰 조회 후, 할인 가격 String data 로 받아오는 로직 만들 것
        * */

        data = "2000";

        return data;
    }
}
