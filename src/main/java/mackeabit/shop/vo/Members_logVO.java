package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Members_logVO {

    private Long ml_idx, member_idx;
    private String login_ip;
    private Date login_date;

}
