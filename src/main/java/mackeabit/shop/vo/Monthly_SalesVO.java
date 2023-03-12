package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Monthly_SalesVO {
    private Long monthly_sales_idx, total_sales, avr_sales;
    private Integer year, month;
}
