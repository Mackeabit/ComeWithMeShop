package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyPageDTO {

    private Integer complete_cnt, cancel_cnt;
    private Date cancel_date, complete_date;

}
