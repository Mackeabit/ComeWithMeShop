package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyReviewsDTO {

    private Long order_idx, member_idx, pd_idx;
    private String address, address_detail, order_date, order_mi, pd_nm, sv_loc, sv_locCt;
    private Integer now_price, shipping_code, order_cart_cnt, review_ck;

}
