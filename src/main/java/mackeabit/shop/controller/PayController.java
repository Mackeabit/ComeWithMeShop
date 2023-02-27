package mackeabit.shop.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.OrderService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//아임포트(IamPort) 사용
@Slf4j
@Controller
@RequiredArgsConstructor
public class PayController {

    private final CartService cartService;
    private final HttpServletRequest request;

    private final OrderService orderService;

    private IamportClient api;

    public void IamPortController() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.api = new IamportClient("3253986757543934",
                "183ce619acba8f6e7a7d6d98c1b995e672bdb96ea3e2b5cdcae1503f3c8650a921260e8b4404dbc1");
    }

    @ResponseBody
    @RequestMapping(value = "/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(
            Model model
            , Locale locale
            , HttpSession session
            , @PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {

        return api.paymentByImpUid(imp_uid);
    }

    @RequestMapping("/payAndOrderCart")
    @ResponseBody
    public String payAndOrderCart(String order_mi, String pay_code, String address, Long total_price) {

        //장바구니에서 결제로 넘어온 경우 사용되는 메서드

        String data = "Y";

        data = orderService.saveAll(order_mi, pay_code, address, total_price);


        return data;
    }

}
