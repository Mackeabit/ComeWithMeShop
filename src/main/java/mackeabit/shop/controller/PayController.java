package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PayController {
    private final CartService cartService;
    private final HttpServletRequest request;
    private final OrderService orderService;

    @RequestMapping("/payAndOrderCart")
    @ResponseBody
    public String payAndOrderCart(String order_mi, String pay_code, String address, Integer total_price) {

        String data = "N";

        data = orderService.saveAll(order_mi, pay_code, address, total_price);

        return data;
    }

}
