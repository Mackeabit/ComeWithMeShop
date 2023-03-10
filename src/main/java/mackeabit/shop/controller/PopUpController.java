package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.service.ReviewService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PopUpController {

    private final ReviewService reviewService;

    @GetMapping("/WriteReview")
    public String writeReview(Model model, String pd_nm, HttpServletRequest request) {

        log.info("Write Review pd_nm = {}", pd_nm);

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        PopUpWriteReviewDTO popUpWriteReviewDTO = new PopUpWriteReviewDTO();
        popUpWriteReviewDTO.setMember_idx(attribute.getMember_idx());
        popUpWriteReviewDTO.setPd_nm(pd_nm);

        //주문서 중 review 를 쓰지 않은 것을 상품명을 통해 조회해서 받아온다.
        List<PopUpWriteReviewDTO> popUpWriteReviewDTOList = reviewService.popUpReview(popUpWriteReviewDTO);

        model.addAttribute("reviewList", popUpWriteReviewDTOList);

        return "reviewWrite";
    }

}
