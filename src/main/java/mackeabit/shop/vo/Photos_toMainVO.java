package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photos_toMainVO {

    private Long img_idx;
    private Integer category_code, to_use;
    private String title, bene, img_name, file_loc;

}
