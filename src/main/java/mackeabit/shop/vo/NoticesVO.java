package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticesVO {

    private Long notice_idx, member_idx, img_idx;
    private Integer cnt;
    private String title, contents, date;

}
