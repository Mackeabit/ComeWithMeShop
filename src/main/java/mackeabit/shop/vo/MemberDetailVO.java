package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailVO {

    private Integer post_code;
    private String address, address_detail;
    private Long member_idx;

}
