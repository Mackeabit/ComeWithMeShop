package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.OrderRepository;
import mackeabit.shop.Repository.PayRepository;
import mackeabit.shop.Repository.SubRepositoryImpl;
import mackeabit.shop.dto.CouponMemberDTO;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.vo.*;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PayRepository payRepository;
    private final HttpServletRequest request;
    private final CartService cartService;

    @Transactional
    public String saveAll(String order_mi, String pay_code, String address, String address_detail, Integer total_price, Integer shipping_code, String coupon_code) {

        String data = "RollbackCheck";

        log.info("Enter saveAll");

        //주문서 작성, 결제 작성, 장바구니 비우기, 재고 관리, 회원 쿠폰 처리 로직 메서드
        data = orderAndPayMethod(order_mi, pay_code, address, address_detail, total_price, shipping_code, coupon_code);

        log.info("out saveAll date is = {}", data);

        return data;
    }

    private String orderAndPayMethod(String order_mi, String pay_code, String address, String address_detail, Integer total_price, Integer shipping_code, String coupon_code) {

        log.info("Enter orderAndPayMethod");

        String data = "";

        /* 주문서 작성 + 재고 관리
         *  1. order_idx --> 자동증가
         *  2. order_mi = order_mi
         *  3. member_idx = session memberIdx 값
         *  4. pd_idx = memberCart -> memberCart.pd_idx 로 조회
         *  5. now_price = memberCart -> memberCart.sell_price 로 조회
         *  6. address = address1 + address2
         *  7. order_date = SQL 문에서 now() 로 작성
         *  */

        /* 결제 작성
         *  1. pay_idx --> 자동증가
         *  2. order_mi --> order_mi
         *  3. member_idx --> session.memberIdx 값
         *  4. total_price --> $('#finTotalPrice').val() 로 작성
         *  5. pay_status --> -1 (결제완료)
         *  6. pay_code --> 결제코드 payCode 받아와서 사용
         * */


        /* 주문서 작성 */
        log.info("주문서 작성 시작");
        //1. 현재 유저 정보를 얻어온다.
        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //2. 유저의 장바구니 테이블을 통해 객체를 받아온다.
        Long member_idx = attribute.getMember_idx();
        List<MainCartDTO> memberCart = cartService.findMemberCart(member_idx);

        //3. INSERT 객체 준비(주문서)
        /* order_idx (자동증가), order_mi, member_idx, pd_idx, now_price, address, address_detail, order_date */



        List<OrdersVO> createOrders = new ArrayList<>();
        List<ProductsVO> cntManageProducts = new ArrayList<>();

        for (int i = 0; i < memberCart.size(); i++) {

            OrdersVO ordersVO = new OrdersVO();
            ordersVO.setOrder_mi(order_mi);
            ordersVO.setMember_idx(member_idx);
            ordersVO.setAddress(address);
            ordersVO.setAddress_detail(address_detail);
            ordersVO.setPd_idx(memberCart.get(i).getPd_idx());
            ordersVO.setNow_price(memberCart.get(i).getSell_price());
            ordersVO.setShipping_code(shipping_code);
            ordersVO.setOrder_cart_cnt(memberCart.get(i).getCart_cnt());

            ProductsVO productsVO = new ProductsVO();
            productsVO.setPd_idx(memberCart.get(i).getPd_idx());
            productsVO.setPd_cnt(memberCart.get(i).getCart_cnt()*-1);

            //List 삽입 (주문서)
            createOrders.add(ordersVO);

            //List 삽입 (재고 관리)
            cntManageProducts.add(productsVO);

            log.info("pd_idx = {}",memberCart.get(i).getPd_idx());
            log.info("List = {}", createOrders.get(i));

        }

        //4. 준비된 List 를 통해 INSERT 진행
        int res = orderRepository.saveAll(createOrders);
        log.info("주문서 저장한 res = {}", res);
        log.info("createOrders.size()", createOrders.size());

        //5. Insert 갯수와 List size 를 비교해서 검증, 주문서 작성 완료
        if (res == createOrders.size()) {

            log.error("payAndOrderCart Method Orders Insert Error -> Insert = {}, Success = {}", createOrders.size(), res);

        }

        //상품 재고 관리
        orderRepository.productCntManageAtOrder(cntManageProducts);


        /* 결제 작성 (무조건 한번만 작성, order_mi 를 기준으로 INSERT) */
        /* pay_idx (자동증가), order_mi, member_idx, total_price, pay_status, pay_code */
        log.info("결제 작성 시작");
        PaymentsVO paymentsVO = new PaymentsVO();
        paymentsVO.setOrder_mi(order_mi);
        paymentsVO.setMember_idx(member_idx);
        paymentsVO.setTotal_price(total_price);
        paymentsVO.setPay_code(pay_code);
        paymentsVO.setShipping_code(shipping_code);

        int payRes = payRepository.save(paymentsVO);

        log.info("결제 작성 res = {}", payRes);


        if (payRes > 0) {
            log.info("payRes > 0 = {}", payRes);
            //INSERT 확인
            log.info("payAndOrderCart Method Payments Insert Success -> Insert = {}", payRes);

            //결제 INSERT 정상이면, 장바구니 비우기
            int delCnt = cartService.delAll(member_idx);

            if (delCnt != memberCart.size()) {
                log.info("if delCnt = {}", delCnt);
                log.error("payAndOrderCart Method Cart Del Error -> del = {}, DelSuccess = {}", createOrders.size(), delCnt);
            }

            log.info("delCnt = {}", delCnt);

            data = "Y";

        }

        //회원의 쿠폰 코드 사용으로 바꾸기
        //사용한 쿠폰 찾기
        CouponMemberDTO couponMemberDTO = orderRepository.findCoupon(coupon_code);

        Map<String, Object> params = new HashMap<>();
        params.put("member_idx", member_idx);
        params.put("cp_idx", couponMemberDTO.getCp_idx());

        orderRepository.usedCoupon(params);

        //쿠폰 DB 의 사용횟수 차감
        orderRepository.minusCntCouponByIdx(couponMemberDTO.getCp_idx());


        log.info("Last payRes = {}", payRes);


        return data;
    }

    public CouponMemberDTO findCoupon(String coupon_code) {
        return orderRepository.findCoupon(coupon_code);
    }

    public int reviewCheck(Long order_idx) {
        return orderRepository.reviewCheck(order_idx);
    }
}
