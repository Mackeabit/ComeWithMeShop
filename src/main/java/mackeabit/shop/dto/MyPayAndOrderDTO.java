package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyPayAndOrderDTO {

    private String pd_nm, pd_color, pd_size, pd_contents, sv_loc, sv_locCt, category_name, order_mi;
    private Integer pd_status, category_code, pd_cnt, ori_price, sell_price, pd_value, pd_kind, category_ref, order_cart_cnt, shipping_code, total_price, pay_status;
    private Long pd_idx, order_idx, member_idx, pay_idx;
    private Date order_date, pay_date;

}
