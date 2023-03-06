package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.*;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    @RequestMapping("/emailCheck")
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
    public String couponCK(String coupon_number) {

        String data = "N";

        if (coupon_number.equals("") || coupon_number == null) {
            return data;
        }

        /*
         *  쿠폰 조회 후, 할인 가격 String data 로 받아오는 로직 만들 것
         * */

        data = "2000";

        return data;
    }


    @PostMapping("/addressInsert")
    @ResponseBody
    public String addressInsert(MemberDetailVO memberDetailVO) {
        return memberService.insertDetails(memberDetailVO);
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
            } else if (payList.get(i).getPay_status() == 1){
                cancel_cnt++;
            }

            if (payList.get(i).getPay_status() == -1&&check1) {
                complete_date = payList.get(i).getOrder_date();
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
    public String myPageOrder(Model model) {

        List<MyOrdersDTO> myOrderList = memberService.myOrdersList();

        model.addAttribute("myOrderList", myOrderList);

        return "myOrders";
    }



}
