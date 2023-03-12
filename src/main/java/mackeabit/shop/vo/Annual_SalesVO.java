package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Annual_SalesVO {
    private Long annual_sales_idx, total_sales, avr_sales;
    private Integer year;
}
