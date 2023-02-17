package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartInsertDTO {

    private Long pd_idx, member_idx;
    private Integer cart_cnt;
    private String pd_nm, pd_color, pd_size;
}
