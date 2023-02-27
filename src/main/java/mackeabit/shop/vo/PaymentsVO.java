package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentsVO {

    private Long pay_idx, member_idx;
    private Integer total_price, pay_status;
    private String order_mi, pay_code;
}
