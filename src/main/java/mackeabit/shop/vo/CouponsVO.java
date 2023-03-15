package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CouponsVO {

    private Long cp_idx;
    private Integer cp_price, cp_value, min_price, cp_cnt;
    private String cp_nm, cp_text;

}
