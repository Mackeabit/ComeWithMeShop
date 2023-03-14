package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponMemberDTO {

    private Long cp_idx, member_idx;
    private Integer cp_price, cp_value, min_price, cp_cnt, cp_ck;
    private String cp_nm, cp_text;

}
