package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MainProductsDTO {

    private String pd_nm, pd_color, pd_size, pd_contents, sv_loc, sv_locCt, category_name;
    private Integer pd_status, category_code, pd_cnt, ori_price, sell_price, pd_value, pd_kind, category_ref;
    private Long pd_idx;
    private Date order_date;
}
