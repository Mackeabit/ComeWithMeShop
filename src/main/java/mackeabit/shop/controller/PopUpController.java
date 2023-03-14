package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.Login;
import mackeabit.shop.dto.PopUpWriteQnaDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.service.QnaService;
import mackeabit.shop.service.ReviewService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.NoticesVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private final QnaService qnaService;

    @GetMapping("/WriteReview")
    public String writeReview(@Login MembersVO membersVO, Model model, String pd_nm, HttpServletRequest request) {

        log.info("Write Review pd_nm = {}", pd_nm);


        PopUpWriteReviewDTO popUpWriteReviewDTO = new PopUpWriteReviewDTO();
        popUpWriteReviewDTO.setMember_idx(membersVO.getMember_idx());
        popUpWriteReviewDTO.setPd_nm(pd_nm);

        //주문서 중 review 를 쓰지 않은 것을 상품명을 통해 조회해서 받아온다.
        List<PopUpWriteReviewDTO> popUpWriteReviewDTOList = reviewService.popUpReview(popUpWriteReviewDTO);

        model.addAttribute("reviewList", popUpWriteReviewDTOList);

        return "reviewWrite";
    }

    @GetMapping("/WriteQna")
    public String writeQna(Model model, String pd_nm) {

        log.info("Write Qna pd_nm = {}", pd_nm);

        //상품명을 받아서 문의글에 필요한 정보들을 갖고 온다.
        //이미지, 사이즈, 색상
        List<PopUpWriteQnaDTO> popUpWriteReviewDTOList = qnaService.popUpQna(pd_nm);

        model.addAttribute("qnaList", popUpWriteReviewDTOList);

        return "qnaWrite";
    }

    @PostMapping("/WriteQna")
    @ResponseBody
    public String writeQnaAfter(HttpServletRequest request, NoticesVO noticesVO) {

        log.info("NoticesVo = {}", noticesVO);

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        noticesVO.setMember_idx(attribute.getMember_idx());

        return qnaService.insertQna(noticesVO);
    }

}
