package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@ToString
public class MembersVO {

    private String email, pwd;
    private Integer grade_code, member_point, member_status, email_ck;
    private Long member_idx;
    private Date sign_date, delete_date;

}
