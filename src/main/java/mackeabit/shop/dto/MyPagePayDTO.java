package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyPagePayDTO {

    private Long pay_idx, order_idx, pd_idx, member_idx;
    private String order_mi, pay_code, address, address_detail;
    private Integer now_price, total_price, pay_status;
    private Date order_date, pay_date;

}
