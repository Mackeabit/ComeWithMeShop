package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.OrderInfo;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.OrderService;
import mackeabit.shop.service.PaymentService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.DiscountPercent;
import mackeabit.shop.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PayController {

    private final CartService cartService;
    private final PaymentService paymentService;
    private final HttpServletRequest request;
    private final OrderService orderService;

/*    @RequestMapping("/payAndOrderCart")
    @ResponseBody
    public String payAndOrderCart(String order_mi, String pay_code, String address, Integer total_price) {

        String data = "N";

        data = orderService.saveAll(order_mi, pay_code, address, total_price);

        return data;
    }*/



    //결제 검증 성공 후
    @PostMapping("/completeOrders")
    public ResponseEntity<String> paymentComplete(HttpSession session, OrderInfo orderInfo) throws IOException {

        log.info("completeOrders");

        String token = paymentService.getToken();

        log.info("토큰 = {}", token);
        log.info("OrderInfo = {}", orderInfo);


        // 결제 완료된 금액
        int amount = paymentService.paymentInfo(orderInfo.getImpUid(), token);

        try {

            // 실제 계산 금액 가져오기
            // 장바구니에서 결제를 눌렀을 때,
            MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

            List<MainCartDTO> memberCart = cartService.findMemberCart(attribute.getMember_idx());

            int beforePrice = 0;

            for (int i = 0; i < memberCart.size(); i++) {

                beforePrice += memberCart.get(i).getSell_price() * memberCart.get(i).getCart_cnt();

            }

            //쿠폰 코드로 해당 쿠폰의 가격 뽑아오는 코드 추가할 것.

            //회원등급에 따른 할인
            int sales = calculateGrade(attribute.getGrade_code(), beforePrice);

            log.info("Shipping_price = {}", orderInfo.getShipping_price());

            //모든 할인 적용한 결제 금액 (DB)
            int totalDBPrice = beforePrice - Integer.parseInt(orderInfo.getShipping_price()) - sales;

            // 스크립트 계산 금액과 DB 실제 금액이 다를 때
            if (totalDBPrice != amount) {
                log.info("totalDBPrice != amount --> {}, {}",totalDBPrice, amount);
                paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "결제 금액 오류");
                return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
            }

            // 정상 결제일 경우 트랜잭션 로직 실행
            String data = "N";

            if (orderInfo.getPd_idx() == null) {
                log.info("pd_idx = {}", orderInfo.getPd_idx());
                data = orderService.saveAll(orderInfo.getOrder_mi(), orderInfo.getPay_code(), orderInfo.getAddress(), orderInfo.getAddress_detail(),orderInfo.getTotal_price(), orderInfo.getShipping_code());
            } else if (orderInfo.getPd_idx() != null) {
                data = "Not Yet";
            }

            if (data.equals("Y")) {
                log.info("data = {}", data);
                session.removeAttribute(SessionConst.MEMBER_CART);
                return new ResponseEntity<>("주문이 완료되었습니다", HttpStatus.OK);
            }

        } catch (Exception e) {
            paymentService.payMentCancle(token, orderInfo.getImpUid(), amount, "결제 에러");
            return new ResponseEntity<String>("결제 에러", HttpStatus.BAD_REQUEST);
        }


        log.info("The End of completeOrders Method");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //등급별 할인 메서드
    private int calculateGrade(Integer grade_code, int beforePrice) {

        int discountPercent = 0;

        switch (grade_code) {

            case 0:
                discountPercent = DiscountPercent.NORMAL;
                break;
            case 1:
                discountPercent = DiscountPercent.SILVER;
                break;
            case 2:
                discountPercent = DiscountPercent.GOLD;
                break;
            case 3:
                discountPercent = DiscountPercent.VIP;
                break;

        }

        int sales = beforePrice * discountPercent / 100;

        return sales;
    }


}
