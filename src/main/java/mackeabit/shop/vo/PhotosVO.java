package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhotosVO {

    private Long img_idx, member_idx, notice_idx, review_idx;
    private String img_name, file_name;

}
