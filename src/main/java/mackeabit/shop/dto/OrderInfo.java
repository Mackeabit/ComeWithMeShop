package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderInfo {

    private String impUid = "";

    private String pd_nm, shipping_price, coupon_code, title, order_mi, pay_code, address, address_detail;
    private Integer pd_price, coupon_price, total_price, grade_sale, now_price, shipping_code;
    private Long member_idx, pd_idx;

}
