package mackeabit.shop.Repository;

import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.vo.OrdersVO;

import java.util.List;

public interface OrderRepository {

    int saveAll(List<OrdersVO> createOrders);

    List<PopUpWriteReviewDTO> popUpReview(PopUpWriteReviewDTO popUpWriteReviewDTO);

    int reviewCheck(Long order_idx);
}
