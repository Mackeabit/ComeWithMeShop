package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyOrdersDTO {

    private String pd_nm, pd_color, pd_size, pd_contents, sv_loc, sv_locCt, category_name, order_mi;
    private Integer pd_status, category_code, pd_cnt, ori_price, sell_price, pd_value, pd_kind, category_ref, order_cart_cnt, review_ck;
    private Long pd_idx, order_idx, member_idx;
    private Date order_date;

}
