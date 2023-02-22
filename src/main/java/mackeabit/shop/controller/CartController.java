package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @RequestMapping("/cart")
    public String cartPage() {

        return "cart";
    }

    @PostMapping("/cntUpdate")
    @ResponseBody
    public String cntUpdate(Integer pd_idx, Integer cart_cnt, Integer member_idx) {

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
}
