package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderInfo {

    private String impUid = "";

    private String pd_nm, shipping_price, coupon_code, title;
    private Integer pd_price, coupon_price, total_price, grade_sale;
    private Long member_idx;

}
