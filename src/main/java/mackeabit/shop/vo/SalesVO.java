package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SalesVO {
    private Long sales_idx, total_sales, avr_sales;
    private Date date;
}
