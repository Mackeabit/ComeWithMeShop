package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReviewsVO {

    private Long review_idx, member_idx, img_idx, pd_idx;
    private Integer cnt;
    private Float stars;
    private String title, contents, pd_nm;
    private Date date;

}
