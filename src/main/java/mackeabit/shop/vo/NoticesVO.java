package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticesVO {

    private Integer notice_idx, member_idx, img_idx, cnt;
    private String title, contents, date;

}
