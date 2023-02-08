package mackeabit.shop.vo;

import lombok.Data;

@Data
public class UserVO {

    private String user_idx, email, pwd, sign_date;
    private Integer grade_code, member_point, member_status;

}
