package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignCountWeekDTO {
    private Date date;
    private Integer count;
}
