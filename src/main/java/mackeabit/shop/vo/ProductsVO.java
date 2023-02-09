package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsVO {

    private String pd_nm;
    private Integer pd_idx, pd_status, category_code, option_code, pd_cnt, ori_price, sell_price;

}
