package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsVO {

    private Integer Review_idx, member_idx, img_idx, pd_idx, cnt;
    private Float stars;
    private String title, contents, date;

}
