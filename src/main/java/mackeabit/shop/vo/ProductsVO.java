package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsVO {

    private String pd_nm, pd_color, pd_size, pd_contents;
    private Integer pd_status, category_code, pd_cnt, ori_price, sell_price, pd_value, pd_kind;
    private Long pd_idx;

}
