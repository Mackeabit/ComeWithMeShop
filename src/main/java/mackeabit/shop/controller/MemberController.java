package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.Login;
import mackeabit.shop.dto.*;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.*;
import mackeabit.shop.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    @RequestMapping("/emailCheckServer")
    @ResponseBody
    public int emailCheck(String email) {
        return memberService.emailCheck(email);
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public String emailCheck(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {
        return memberService.checkID(signUpDTO);
    }

    @RequestMapping("/coupon")
    @ResponseBody
    public String couponCK(@Login MembersVO membersVO, String cp_nm, Integer min_price) {

        String data = "N";

        if (cp_nm.equals("") || cp_nm == null) {
            return data;
        }

        /*
         *  쿠폰 조회 후, 할인 가격 String data 로 받아오는 로직 만들 것
         * */

        //1. 회원에게 해당 쿠폰 사용 이력이 있는지 확인
        Members_couponVO members_couponVO = memberService.findCouponByCp_nm(cp_nm);

        // 조회 결과가 있다면 사용한 쿠폰이므로 return
        if (members_couponVO != null) {
            data = "exist";
            return data;
        }

        CouponsVO coupon = memberService.findCouponByNm(cp_nm);

        if (coupon == null) {
            return data;
        }

        if (coupon.getMin_price() > min_price) {
            data = "no_min";
            return data;
        }

        if (coupon.getCp_cnt() <= 0) {
            data = "no_more";
            return data;
        }

        data = coupon.getCp_price().toString();

        return data;
    }

    @PostMapping("/findMemberEmail")
    @ResponseBody
    public String findMemberEmail(String email) {
        log.info("email = {}", email);

        String data = "no_email";

        MembersVO byEmail = memberService.findEmailAtForgot(email);

        if (byEmail == null) {
            return data;
        }

        data = "Y";

        return data;
    }


    @PostMapping("/addressInsert")
    @ResponseBody
    public String addressInsert(MemberDetailVO memberDetailVO) {
        return memberService.insertDetails(memberDetailVO);
    }

    @GetMapping("/delMember")
    public String delPage() {

        return "delAccountPage";
    }

    @PostMapping("/delMember")
    @ResponseBody
    public String delMembers(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {

        String data = memberService.checkID(signUpDTO);


        if (data.equals("Y")) {
            //이메일, 패스워드 체크를 통과했다면 회원 탈퇴(update)
            data = memberService.updateMemberStatus(signUpDTO);

        } else {
            return data;
        }


        return data;
    }


    @GetMapping("/myPage")
    public String myPage(Model model) {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<MainProductsDTO> myPageList = memberService.findOrders(attribute.getMember_idx());

        List<MyPagePayDTO> payList = memberService.findPayments(attribute.getMember_idx());

        int complete_cnt = 0;
        int cancel_cnt = 0;

        boolean check1 = true;
        boolean check2 = true;

        Date complete_date = new Date();
        Date cancel_date = new Date();

        for (int i = 0; i < payList.size(); i++) {

            if (payList.get(i).getPay_status() == -1) {
                complete_cnt++;
            } else if (payList.get(i).getPay_status() == 1) {
                cancel_cnt++;
            }

            if (payList.get(i).getPay_status() == -1 && check1) {
                complete_date = payList.get(i).getPay_date();
            } else if (payList.get(i).getPay_status() == 1 & check2) {
                cancel_date = payList.get(i).getOrder_date();
            }

        }

        MyPageDTO myPageDTO = new MyPageDTO();
        myPageDTO.setComplete_cnt(complete_cnt);
        myPageDTO.setCancel_cnt(cancel_cnt);
        myPageDTO.setComplete_date(complete_date);
        myPageDTO.setCancel_date(cancel_date);

        model.addAttribute("orderList", myPageList);
        model.addAttribute("payList", myPageDTO);

        return "myPage";
    }

    @GetMapping("/myPage/memberInfo")
    public String memberInfo(Model model) {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        MemberDetailVO memberDetailVO = memberService.findMemberDetailByMemberIdx(attribute.getMember_idx());

        log.info("memberDetailVO = {}", memberDetailVO);

        if (memberDetailVO == null) {

            //타임리프 변수 처리를 위해 null 값 셋팅

            memberDetailVO = new MemberDetailVO();
            memberDetailVO.setAddress("");
            memberDetailVO.setAddress_detail("");
            memberDetailVO.setPost_code(null);
        }

        model.addAttribute("members_detail", memberDetailVO);

        return "memberInfo";
    }

    @PostMapping("/myPage/memberInfo")
    public ResponseEntity<String> insertMemberInfo(MemberDetailVO memberDetailVO, String pwd) {

        log.info("pwd = {}", pwd);

        try {
            String data = memberService.updateMyPageDetails(memberDetailVO, pwd);

            if (data.equals("Y")) {
                return new ResponseEntity<>("정보 수정이 완료되었습니다.", HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>("정보 수정 중 오류가 발생하였습니다. 잠시 후 재시도 해주세요.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/myPage/orders")
    public String myPageOrder(Model model, @RequestParam(defaultValue = "4") int limit, @RequestParam(defaultValue = "1") int offset) {


        List<MyOrdersDTO> myOrderList = memberService.myOrdersList(limit, (offset - 1) * limit);
        model.addAttribute("myOrderList", myOrderList);
        model.addAttribute("offset", offset);

        log.info("myOrderList = {}", myOrderList);

        if (myOrderList == null || myOrderList.isEmpty()) {
            return "myNoOrderList";
        }

        return "myOrders";
    }

    @GetMapping("/myPage/orders/data")
    @ResponseBody
    public List<MyOrdersDTO> myPageOrderData(@RequestParam(defaultValue = "4") int limit, @RequestParam(defaultValue = "1") int offset) {
        log.info("myOrderPage Paging Infinity");
        List<MyOrdersDTO> myOrderList = memberService.myOrdersList(limit, (offset - 1) * limit);
        return myOrderList;
    }

    @GetMapping("myPage/pays")
    public String myPagePayList(Model model, @RequestParam(defaultValue = "0") int level) {

        List<MyPayAndOrderDTO> myPayAndOrderList = memberService.myPayAndOrderList(level);

        if (myPayAndOrderList == null || myPayAndOrderList.isEmpty()) {
            return "myNoPayList";
        }

        model.addAttribute("myPayList", myPayAndOrderList);

        return "myPayList";
    }

    @GetMapping("myPage/pays/data")
    @ResponseBody
    public List<MyPayAndOrderDTO> myPagePayData(Model model, @RequestParam(defaultValue = "0") int level) {

        log.info("myPayPage Paging Infinity, level = {}", level);

        List<MyPayAndOrderDTO> myPayAndOrderList = memberService.myPayAndOrderList(level);

        log.info("myPayPage Paging Infinity, List = {}", myPayAndOrderList);


        return myPayAndOrderList;
    }

    @GetMapping("/myPage/myReviews")
    public String myReviews(Model model) {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //작성 가능한 리뷰 가져오기 (주문 목록)
        List<MyReviewsDTO> myReviewsList = memberService.enableMyReviews(attribute.getMember_idx());
        model.addAttribute("enableReviews", myReviewsList);

        //작성한 리뷰 가져오기
        List<MyWriteReviewsDTO> myReviewsListNo = memberService.disableMyReviews(attribute.getMember_idx());
        model.addAttribute("disableReviews", myReviewsListNo);


        return "myReviews";
    }

    @GetMapping("/myPage/myQna")
    public String myQna(Model model) {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //작성한 QnA 가져오기
        List<NoticesVO> myNoticesList = memberService.findMyNoticesByMember_idx(attribute.getMember_idx());
        model.addAttribute("myNoticesList", myNoticesList);


        return "myQna";
    }

    @GetMapping("test")
    public String test() {

        return "userPasswordFind";
    }


}
