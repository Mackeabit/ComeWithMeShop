package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutDTO {

    private String pd_nm, shipping_price, coupon_code, title;
    private Integer pd_price, coupon_price, total_price, grade_sale;
    private Long member_idx;

}
