package mackeabit.shop.mapper;

import mackeabit.shop.dto.CouponMemberDTO;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.vo.CartsVO;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.ProductsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    int saveAll(List<OrdersVO> createOrders);

    List<PopUpWriteReviewDTO> popUpReview(PopUpWriteReviewDTO popUpWriteReviewDTO);

    int reviewCheck(Long order_idx);

    void productCntManageAtOrder(List<ProductsVO> cntManageProducts);

    void usedCoupon(Map<String, Object> params);

    CouponMemberDTO findCoupon(String coupon_code);

    void minusCntCouponByIdx(Long cp_idx);

}
