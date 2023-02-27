package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.OrderRepository;
import mackeabit.shop.Repository.SubRepositoryImpl;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.Photos_toMainVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final HttpServletRequest request;
    private final CartService cartService;

    @Transactional
    public String saveAll(String order_mi, String pay_code, String address, Long total_price) {

        String data = "Y";

        //주문서 작성과 결제 작성 해주는 로직 메서드
        orderAndPayMethod(order_mi, pay_code, address, total_price);

        data = rollbackCatch();

        return data;
    }

    private String rollbackCatch() {
        String data = "";

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCompletion(int status) {
                        if (status == STATUS_ROLLED_BACK) {

                        }
                    }
                });

        return data;
    }

    private void orderAndPayMethod(String order_mi, String pay_code, String address, Long total_price) {


        /* 주문서 작성
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
        //1. 현재 유저 정보를 얻어온다.
        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //2. 유저의 장바구니 테이블을 통해 객체를 받아온다.
        Long member_idx = attribute.getMember_idx();
        List<MainCartDTO> memberCart = cartService.findMemberCart(member_idx);

        //3. INSERT 객체 준비(주문서)

        List<OrdersVO> createOrders = new ArrayList<>();

        OrdersVO ordersVO = new OrdersVO();
        ordersVO.setOrder_mi(order_mi);
        ordersVO.setMember_idx(member_idx);
        ordersVO.setAddress(address);
        ordersVO.setNow_price(total_price);

        for (int i = 0; i < memberCart.size(); i++) {

            //OrdersVO 에 값 셋팅하기(재사용)
            ordersVO.setPd_idx(memberCart.get(i).getPd_idx());

            //List 삽입
            createOrders.add(ordersVO);

        }

        //4. 준비된 List 를 통해 INSERT 진행
        int res = orderRepository.saveAll(createOrders);

        //5. Insert 갯수와 List size 를 비교해서 검증, 주문서 작성 완료
        if (res == createOrders.size()) {

            log.error("payAndOrderCart Method Insert Error -> Insert = {}, Success = {}", res, createOrders.size());

        }

        /* 결제 작성 */



    }
}