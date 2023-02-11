package mackeabit.shop.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDetailVO {

    private Integer post_code, age;
    private String address, address_detail;
    private Long member_idx;

}
