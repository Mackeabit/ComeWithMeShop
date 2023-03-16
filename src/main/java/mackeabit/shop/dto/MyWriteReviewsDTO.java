package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyWriteReviewsDTO {

    private Long order_idx, member_idx, pd_idx, review_idx;
    private String title, contents, order_mi, pd_nm, sv_loc, sv_locCt, file_name;
    private Integer stars;
    private Date date;

}
