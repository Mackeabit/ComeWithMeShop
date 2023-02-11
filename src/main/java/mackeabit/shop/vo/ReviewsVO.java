package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsVO {

    private Long Review_idx, member_idx, img_idx, pd_idx;
    private Integer cnt;
    private Float stars;
    private String title, contents, date;

}
