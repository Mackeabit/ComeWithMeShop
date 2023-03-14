package mackeabit.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdminNoticeDTO {

    private Long notice_idx, member_idx;
    private String title, contents, pd_nm, qna_reply, sv_loc, sv_locCt, email, pd_contents;
    private Integer secret_ck, stars, sell_price, pd_cnt;
    private Date date;

}
