package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class MembersAllInfoDTO {

    private String email, pwd, address, address_detail;
    private Integer grade_code, member_point, member_status, email_ck, post_code;
    private Long member_idx;
    private Date sign_date, delete_date, login_date;

}
