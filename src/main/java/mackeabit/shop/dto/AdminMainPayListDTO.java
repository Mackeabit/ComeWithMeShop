package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMainPayListDTO {

    private String pd_nm, sv_loc, sv_locCt, email;
    private Integer total_price, pay_status;

}
