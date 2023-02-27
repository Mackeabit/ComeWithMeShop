package mackeabit.shop.mapper;

import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.PaymentsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    int save(PaymentsVO paymentsVO);
}
