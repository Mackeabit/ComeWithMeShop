package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCartDTO {

    private String pd_nm, sv_locCt;
    private Integer cart_cnt;
    private Long pd_idx, cart_idx;

}
