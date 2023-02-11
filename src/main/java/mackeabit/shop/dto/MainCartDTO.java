package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainCartDTO {

    private String pd_nm, pd_imgCt, sv_locCt;
    private Long pd_idx, cart_idx, cart_cnt;

}
