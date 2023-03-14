package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponsVO {

    private Long cp_idx;
    private Integer cp_price, cp_value, min_price, cp_cnt;
    private String cp_nm, cp_text;

}
