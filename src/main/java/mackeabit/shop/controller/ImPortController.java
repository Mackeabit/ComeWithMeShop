package mackeabit.shop.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//아임포트(IamPort) 사용
@Slf4j
@Controller
public class ImPortController {

    private IamportClient api;

    public ImPortController() {

        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.api = new IamportClient("3253986757543934",
                "183ce619acba8f6e7a7d6d98c1b995e672bdb96ea3e2b5cdcae1503f3c8650a921260e8b4404dbc1");
    }

    @ResponseBody
    @PostMapping(value = "/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(
            Model model
            , Locale locale
            , HttpSession session
            , @PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {

        log.info("IamportResponse");
        log.info("api.paymentByImpUid(imp_uid) = {}", api.paymentByImpUid(imp_uid));

        return api.paymentByImpUid(imp_uid);
    }



}
