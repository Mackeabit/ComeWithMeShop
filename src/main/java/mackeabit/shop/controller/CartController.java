package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final HttpServletRequest request;


    @RequestMapping("/cart")
    public String cartPage() {

        return "cart";
    }

    @PostMapping("/cntUpdate")
    @ResponseBody
    public String cntUpdate(Long pd_idx, Integer cart_cnt, Long member_idx) {

        log.info("pd_idx ={}, cart_cnt = {}, member_idx = {}", pd_idx, cart_cnt, member_idx);

        String data = "no_id";

        if (member_idx == null) {
            return data;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pd_idx", pd_idx);
        params.put("cart_cnt", cart_cnt);
        params.put("member_idx", member_idx);

        data = cartService.updateCart(params);

        return data;
    }

    @PostMapping("/delCart")
    @ResponseBody
    public String delCart(Long pd_idx, Long member_idx) {

        log.info("pd_idx ={}", pd_idx);

        String data = "no_id";

        if (member_idx == null) {
            return data;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pd_idx", pd_idx);
        params.put("member_idx", member_idx);


        data = cartService.delCart(params);

        if (data == "Y") {

            HttpSession session = request.getSession();

            //기존 장바구니 세션 제거
            session.removeAttribute(SessionConst.MEMBER_CART);

            //장바구니 내용 갱신후 세션 생성
            List<MainCartDTO> memberCart = cartService.findMemberCart(member_idx);

            log.info("memberCart = {}", memberCart);

            session.setAttribute(
                    SessionConst.MEMBER_CART,
                    memberCart
            );
        }


        return data;

    }



}
