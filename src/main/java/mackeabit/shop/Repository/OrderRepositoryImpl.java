package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.mapper.OrderMapper;
import mackeabit.shop.vo.OrdersVO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
