package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class VisitsVO {
    private Long visit_idx, total_visits;
    private Date date;
}
