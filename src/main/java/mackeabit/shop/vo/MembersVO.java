package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MembersVO {

    private String email, pwd, sign_date;
    private Integer grade_code, member_point, member_status;
    private Long member_idx;



}
