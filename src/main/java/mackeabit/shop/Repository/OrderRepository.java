package mackeabit.shop.Repository;

import mackeabit.shop.vo.OrdersVO;

import java.util.List;

public interface OrderRepository {

    int saveAll(List<OrdersVO> createOrders);
}
