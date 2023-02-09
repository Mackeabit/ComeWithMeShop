package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelVO {

    private Integer cl_idx, pay_idx, member_idx;
    private String cancel_date;

}
