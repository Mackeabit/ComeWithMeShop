package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersVO {

    private Integer order_idx, member_idx, pd_idx, order_mi,
            now_price;
    private String address, order_date;

}
