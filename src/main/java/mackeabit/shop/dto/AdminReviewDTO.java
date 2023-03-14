package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AdminReviewDTO {

    private Long review_idx, member_idx, img_idx, pd_idx, order_idx;
    private Integer order_cart_cnt, sell_price;
    private Float stars;
    private String title, contents, pd_nm, pd_size, pd_color, sv_loc, sv_locCt;
    private Date date;

}
