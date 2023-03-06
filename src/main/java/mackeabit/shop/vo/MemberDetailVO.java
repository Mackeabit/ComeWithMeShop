package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDetailVO {

    private Integer post_code;
    private String address, address_detail;
    private Long member_idx;

}
