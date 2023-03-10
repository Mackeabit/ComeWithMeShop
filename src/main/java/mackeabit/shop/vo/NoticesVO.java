package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NoticesVO {

    private Long notice_idx, member_idx;
    private String title, contents, pd_nm;
    private Integer secret_ck;
    private Date date;

}
