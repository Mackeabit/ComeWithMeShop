package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentsVO {

    private Long pay_idx, order_idx, member_idx;
    private Integer total_price, pay_status, pay_code;
}
