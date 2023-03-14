package mackeabit.shop.Repository;

import mackeabit.shop.dto.CouponMemberDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.ProductsVO;

import java.util.List;
import java.util.Map;

public interface OrderRepository {

    int saveAll(List<OrdersVO> createOrders);

    List<PopUpWriteReviewDTO> popUpReview(PopUpWriteReviewDTO popUpWriteReviewDTO);

    int reviewCheck(Long order_idx);

    void productCntManageAtOrder(List<ProductsVO> cntManageProducts);

    void usedCoupon(Map<String, Object> params);

    CouponMemberDTO findCoupon(String coupon_code);

    void minusCntCouponByIdx(Long cp_idx);
}
