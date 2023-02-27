package mackeabit.shop.Repository;

import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.PaymentsVO;

import java.util.List;

public interface PayRepository {

    int save(PaymentsVO paymentsVO);
}
