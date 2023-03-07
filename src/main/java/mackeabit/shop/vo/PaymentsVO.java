package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentsVO {

    private Long pay_idx, member_idx;
    private Integer total_price, pay_status, shipping_code;
    private String order_mi, pay_code;
    private Date pay_date;
}
