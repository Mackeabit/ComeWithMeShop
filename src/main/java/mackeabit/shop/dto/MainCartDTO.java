package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCartDTO {

    private String pd_nm, sv_locCt, pd_size, pd_color;
    private Integer cart_cnt, sell_price;
    private Long pd_idx, cart_idx;

}
