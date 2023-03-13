package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdminAllPaymentsDTO {

    private Long pay_idx, member_idx;
    private Integer total_price, pay_status, shipping_code;
    private String order_mi, pay_code, email;
    private Date pay_date;
}
