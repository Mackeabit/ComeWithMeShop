package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAllOrdersDTO {

    private Long order_idx, member_idx, pd_idx;
    private String address, address_detail, order_date, order_mi, email, pd_nm;
    private Integer now_price, shipping_code, order_cart_cnt, review_ck;

}
