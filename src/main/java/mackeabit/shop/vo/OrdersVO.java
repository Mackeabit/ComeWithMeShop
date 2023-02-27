package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersVO {

    private Long order_idx, member_idx, pd_idx, now_price;
    private String address, order_date, order_mi;

}
