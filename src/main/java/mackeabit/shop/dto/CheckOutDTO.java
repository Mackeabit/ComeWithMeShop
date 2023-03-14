package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutDTO {

    private String pd_nm, shipping_price, cp_nm, title;
    private Integer pd_price, coupon_price, total_price, grade_sale, shipping_code;
    private Long member_idx;

}
