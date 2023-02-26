package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class MembersVO {

    private String email, pwd, sign_date;
    private Integer grade_code, member_point, member_status, email_ck;
    private Long member_idx;

}
