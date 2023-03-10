package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PopUpWriteReviewDTO {

    private Long member_idx, order_idx, pd_idx;
    private String pd_nm, pd_color, pd_size, sv_locCt, order_mi;
    private Date order_date;

}
