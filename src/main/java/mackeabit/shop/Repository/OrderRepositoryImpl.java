package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.CouponMemberDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.mapper.OrderMapper;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.ProductsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper orderMapper;

    @Override
    public int saveAll(List<OrdersVO> createOrders) {
        return orderMapper.saveAll(createOrders);
    }

    @Override
    public List<PopUpWriteReviewDTO> popUpReview(PopUpWriteReviewDTO popUpWriteReviewDTO) {
        return orderMapper.popUpReview(popUpWriteReviewDTO);
    }

    @Override
    public int reviewCheck(Long order_idx) {
        return orderMapper.reviewCheck(order_idx);
    }

    @Override
    public void productCntManageAtOrder(List<ProductsVO> cntManageProducts) {
        orderMapper.productCntManageAtOrder(cntManageProducts);
    }

    @Override
    public void usedCoupon(Map<String, Object> params) {
        orderMapper.usedCoupon(params);
    }

    @Override
    public CouponMemberDTO findCoupon(String coupon_code) {
        return orderMapper.findCoupon(coupon_code);
    }

    @Override
    public void minusCntCouponByIdx(Long cp_idx) {
        orderMapper.minusCntCouponByIdx(cp_idx);
    }
}
