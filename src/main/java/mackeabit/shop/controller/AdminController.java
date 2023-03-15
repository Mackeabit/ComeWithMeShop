package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.AdminLogin;
import mackeabit.shop.dto.*;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.service.AdminService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.service.PaymentService;
import mackeabit.shop.vo.*;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final PaymentService paymentService;

    @GetMapping()
    public String adminMain(@AdminLogin AdminVO adminVO, Model model) {

        log.info("admin = {}", adminVO);
//테스트를 위해 잠시 주석 처리
        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }

        /* 관리자 페이지 메인에 필요한 정보
         *
         *  연간 매출 현황 (작년 매출) v
         *  일간 가입자 수 (전일 대비) v
         *  오늘 방문자 수 (전일 대비) v
         *  월간 매출 현황 (전월 대비) v
         *  일주일 가입자 수 리스트 (일일) v
         *  일주일 방문자 수 리스트 (일일) v
         *  일주일 매출 그래프 (일일) v
         *  최근 결제 내역 (7건, sv_locCt, pd_nm, email, total_price, pay_status) v
         *  최근 주문 내역 (6건, order_mi, date) v
         *
         *  */

        // 작년 매출 현황 (인자 -1 : 작년)
        LocalDate now = LocalDate.now();
        int lastYear = now.getYear() - 1; //작년 (ex. 2022)
        Annual_SalesVO annual_salesVO = adminService.findAnnualSales(lastYear, -1);

        log.info("year = {}", now.getYear());
        log.info("lastYear = {}", lastYear);

        // 올해 매출 현황 (인자 0 : 올해)
        Annual_SalesVO annualSales = adminService.findAnnualSales(now.getYear(), 0);

        // 오늘 가입자 수
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayStr = now.format(formatter);
        Integer signUpToday = adminService.findMemberTodaySignUp(todayStr);

        // 전날 가입자 수
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String yesterdayStr = yesterday.format(formatter);
        Integer signUpYesterday = adminService.findMemberYesterdaySignUp(yesterdayStr);

        // 오늘 방문자 수
        Integer todayVisit = adminService.countVisit(todayStr);

        // 어제 방문자 수
        Integer yesterdayVisit = adminService.countVisit(yesterdayStr);

        //월별 매출 구하기
        int year = now.getYear(); // 올해
        int month = now.getMonthValue(); // 이번달
        int lastMonth = now.minusMonths(1).getMonthValue(); //저번달

        // 이번달 매출 현황
        Map<String, Integer> yearMonth = new ConcurrentHashMap<>();
        yearMonth.put("year", year);
        yearMonth.put("month", month);
        Integer nowMonthPrice = adminService.monthPrice(yearMonth);

        // 저번달 매출 현황
        LocalDate localDate = now.minusMonths(1);
        if (localDate.getYear() != year) { // 년도가 다르면
            year = localDate.getYear(); // 년도를 1년 빼줌
        }

        Map<String, Integer> lastYearMonth = new ConcurrentHashMap<>();
        lastYearMonth.put("year", year);
        lastYearMonth.put("month", lastMonth);
        Integer lastMonthPrice = adminService.lastMonthPrice(lastYearMonth);

        // 일주일 일별로 가입자 수
        LocalDate today = LocalDate.now();
        LocalDate aWeekAgo = today.minusWeeks(1);
        Date startDate = Date.from(aWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Map<String, Date> selectDate = new ConcurrentHashMap<>();
        selectDate.put("startDate", startDate);
        selectDate.put("endDate", endDate);

        List<SignCountWeekDTO> signCountWeekDTOList = adminService.signCountWeek(selectDate);

        // 일주일 일별로 방문자 수
        List<SignCountWeekDTO> visitCountWeekDTOList = adminService.visitCountWeek(selectDate);

        // 일주일 일일 매출
        List<SalesVO> salesVOList = adminService.weekSales(selectDate);

        // 최근 결제 내역 (7건, sv_locCt, pd_nm, email, total_price, pay_status)
        List<AdminMainPayListDTO> adminMainPayListDTOList = adminService.findAdminMainPayList();

        // 최근 주문 내역 (6건, order_mi, order_date)
        List<AdminMainOrderListDTO> AdminMainOrderListDTOList = adminService.findAdminMainOrderList();

        log.info("annualSales.getTotal_sales() = {}", annualSales);

        // Model 에 담아서 전송
        model.addAttribute("lastYearPrice", annual_salesVO.getTotal_sales())
                .addAttribute("nowYearPrice", annualSales.getTotal_sales())
                .addAttribute("signUpToday", signUpToday)
                .addAttribute("signUpYesterday", signUpYesterday)
                .addAttribute("todayVisit", todayVisit)
                .addAttribute("yesterdayVisit", yesterdayVisit)
                .addAttribute("nowMonthPrice", nowMonthPrice)
                .addAttribute("lastMonthPrice", lastMonthPrice)
                .addAttribute("weekSignCountList", signCountWeekDTOList)
                .addAttribute("weekVisitCountList", visitCountWeekDTOList)
                .addAttribute("weekSalesList", salesVOList)
                .addAttribute("adminPayList", adminMainPayListDTOList)
                .addAttribute("adminOrderList", AdminMainOrderListDTOList);

        //AdminVO 값이 있다면 해당 세션과 함께 관리자 페이지 진입
        return "adminDashboard";
    }

    @PostMapping("/adminCheck")
    @ResponseBody
    public String adminCheck(AdminCheckDTO adminCheckDTO) throws NoSuchAlgorithmException {

        return adminService.adminCheck(adminCheckDTO);
    }

    @GetMapping("/adminSession")
    public String adminSession(HttpServletRequest request, @RequestParam String id) {

        HttpSession session = request.getSession();
        AdminVO findAdmin = adminService.findAdminById(id);

        log.info("adminSession findAdmin = {}", findAdmin);

        if (findAdmin.getAdmin_level() == 0) {
            //최고 관리자
            session.setAttribute(SessionConst.SUPER_ADMIN, findAdmin);
        } else if (findAdmin.getAdmin_level() == -1) {
            //일반 관리자
            session.setAttribute(
                    SessionConst.LOGIN_ADMIN,
                    findAdmin
            );
        }

        return "redirect:/admin";
    }


    @GetMapping("/members")
    public String adminMembers(@AdminLogin AdminVO adminVO, Model model) {

/*
        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }
*/

        /* 관리자 계정
         *  일반 유저 계정 담기 (member_idx, email, grade_code, member_status, sign_date)
         *   */

        List<AdminVO> adminVOList = adminService.findAll();
        List<MembersVO> membersVOList = adminService.findAllMembers();

        model.addAttribute("adminList", adminVOList)
                .addAttribute("memberList", membersVOList);

        return "adminPageAllMembers";
    }


    @GetMapping("/memberInfo")
    public String adminMemberInfo(@AdminLogin AdminVO adminVO, Model model, @RequestParam Long member_idx) {

/*
        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }
*/

        log.info("member_idx = {}", member_idx);

        // 회원 정보
        MembersAllInfoDTO findMember = adminService.findMemberAllInfo(member_idx);

        // 회원 로그
        List<Members_logVO> membersLogList = adminService.findMemberLog(member_idx);

        if (findMember == null) {
            //members_detail 이 없는 회원(주소 작성X)

            MembersVO membersVO = memberService.findByIdx(member_idx);

            findMember = new MembersAllInfoDTO();
            findMember.setMember_idx(member_idx);
            findMember.setEmail(membersVO.getEmail());
            findMember.setPwd(membersVO.getPwd());
            findMember.setAddress("");
            findMember.setAddress_detail("");
            findMember.setLogin_date(membersVO.getLogin_date());
            findMember.setGrade_code(membersVO.getGrade_code());
            findMember.setEmail_ck(membersVO.getEmail_ck());
            findMember.setSign_date(membersVO.getSign_date());

        }


        log.info("membersLogList = {}", membersLogList);
        log.info("membersLogList.size() = {}", membersLogList.size());

        if (membersLogList.size() == 0 || membersLogList == null) {

            Members_logVO members_logVO = new Members_logVO();

            members_logVO.setLogin_ip("접속 기록 없음");
            members_logVO.setMember_idx(member_idx);

            membersLogList.add(members_logVO);
        }

        log.info("memberInfo.sign_date = {}", findMember.getSign_date());
        log.info("memberInfo.getMember_idx() = {}", findMember.getMember_idx());

        model.addAttribute("memberInfo", findMember)
                .addAttribute("memberLogList", membersLogList);

        return "adminDetailPage";
    }


    @PostMapping("/updateMembers")
    @ResponseBody
    public String updateMembers(MembersAllInfoDTO membersAllInfoDTO) throws NoSuchAlgorithmException {

        /* 넘어오는 정보
         *  member_idx
         *  email
         *  address
         *  address_detail
         *  post_code
         *  pwd
         *  */

        log.info("memberVO = {}", membersAllInfoDTO);

        String data = "no_email";

        MembersVO findMember = memberService.findByIdx(membersAllInfoDTO.getMember_idx());


        if (membersAllInfoDTO.getEmail() != "") {
            //이메일을 변경하기 원했을 때, 중복체크
            MembersVO emailCheck = adminService.findByEmail(membersAllInfoDTO.getEmail());
            if (emailCheck != null) {
                return data;
            }
        }

        if (membersAllInfoDTO.getEmail() == "") {
            //이메일 변경을 원치 않을때 기존 이메일을 세팅
            membersAllInfoDTO.setEmail(findMember.getEmail());
        }

        if (membersAllInfoDTO.getPwd() == "") {
            //비밀번호 변경을 원치 않을때 기존 비밀번호 세팅
            membersAllInfoDTO.setPwd(findMember.getPwd());
        } else {
            String pwd = membersAllInfoDTO.getPwd();
            SHA256 sha256 = new SHA256();
            //비밀번호 암호화 저장
            membersAllInfoDTO.setPwd(sha256.encrypt(pwd + sha256.getSALT()));
        }

        return adminService.updateMembers(membersAllInfoDTO);
    }

    @PostMapping("/deleteMembers")
    @ResponseBody
    public String deleteMembers(Long member_idx) {
        /* 계정상태를 -1로 바꿈 */
        return adminService.delMemberByMember_idx(member_idx);
    }

    @GetMapping("/productReg")
    public String productReg(@AdminLogin AdminVO adminVO, Model model) {

        //카테고리 넘겨주기(하위 목록)
        List<CategorysVO> categorysVOList = adminService.findCategory();

        model.addAttribute("categoriesList", categorysVOList);

        return "adminProductsReg";
    }

    @GetMapping("/productsList")
    public String productsList(@AdminLogin AdminVO adminVO, Model model) {

        //상품명 중복 제거를 하지 않고 리스트 갖고 오기
        List<MainProductsDTO> allProductsList = adminService.findProductsList();


        model.addAttribute("allProductsList", allProductsList);

        return "adminProductsList";
    }

    @GetMapping("/editProduct")
    public String editProduct(Long pd_idx, Model model) {

        log.info("editProduct By pd_idx = {}", pd_idx);

        //넘겨받은 pd_idx 를 통해 상품 찾기 (+ 대표 이미지)
        MainProductsDTO mainProductsDTO = adminService.findProductByIdx(pd_idx);
        model.addAttribute("pd", mainProductsDTO);

        //카테고리 넘겨주기(하위 목록)
        List<CategorysVO> categorysVOList = adminService.findCategory();

        model.addAttribute("categoriesList", categorysVOList);

        return "adminEditProduct";
    }

    @PostMapping("/editProduct")
    @ResponseBody
    public String editProductPost(ProductsVO productsVO) {
        return adminService.updateProduct(productsVO);
    }

    @GetMapping("/ordersList")
    public String ordersList(Model model) {

        //주문 목록 (+ members, products JOIN)
        List<AdminAllOrdersDTO> adminAllOrdersDTOList = adminService.findOrderListAll();


        model.addAttribute("ordersList", adminAllOrdersDTOList);


        return "adminOrdersList";
    }

    @GetMapping("/paymentsList")
    public String paymentsList(Model model) {

        //결제 목록 (+members JOIN)
        List<AdminAllPaymentsDTO> adminAllPaymentsDTOList = adminService.findPaymentsListAll();

        model.addAttribute("paymentList", adminAllPaymentsDTOList);

        return "adminPaymentsList";
    }

    @PostMapping("/adminPaymentsCancel")
    @ResponseBody
    public String paymentsCancelByAdmin(PaymentsVO paymentsVO) throws IOException {

        log.info("paymentsCancelByAdmin = {}", paymentsVO);

        return adminService.paymentsCancel(paymentsVO);
    }

    @GetMapping("/adminReviewManage")
    public String adminReviewManage(Long order_idx, Model model) {

        //order_idx 로 리뷰 갖고 오기
        AdminReviewDTO adminReviewDTO = adminService.findReviewByOrderIdx(order_idx);

        //구해온 리뷰를 통해 등록한 사진이 갖고 오기
        PhotosVO photosVO = adminService.findReviewPhoto(adminReviewDTO.getReview_idx());

        model.addAttribute("rv", adminReviewDTO)
                .addAttribute("photos", photosVO);

        return "adminReviewManagePage";
    }

    @PostMapping("/delReview")
    @ResponseBody
    public String delReview(Long review_idx) {
        return adminService.delReview(review_idx);
    }


    @GetMapping("/qnaList")
    public String adminQna(Model model) {

        //모든 문의글 조회
        List<AdminNoticeDTO> qnaList = adminService.findAllQna();

        model.addAttribute("qnaList", qnaList);

        return "adminQnaList";
    }

    @GetMapping("/qnaWriteAdmin")
    public String qnaWriteAdmin(Model model, Long notice_idx) {

        //해당 문의글 조회
        AdminNoticeDTO adminNoticeDTO = adminService.findNoticeOneByIdx(notice_idx);

        model.addAttribute("qna", adminNoticeDTO);

        return "adminQnaAnswerPage";
    }

    @PostMapping("/answerQna")
    @ResponseBody
    public String answerQna(NoticesVO noticesVO) {
        //update 코드 (qna_reply, notice_idx)
        return adminService.qnaReplyUpdate(noticesVO);
    }

    @PostMapping("/delQna")
    @ResponseBody
    public String delQna(Long notice_idx) {
        //삭제 코드
        return adminService.qnaDelByIdx(notice_idx);
    }

    @PostMapping("/adminAdd")
    @ResponseBody
    public String adminAdd(AdminVO adminVO) throws NoSuchAlgorithmException {

        return adminService.adminAdd(adminVO);
    }

    @GetMapping("/adminSign")
    public String adminSign(@AdminLogin AdminVO adminVO) {

        if (adminVO == null) {
            return "adminLogin";
        }

        if (adminVO.getAdmin_level() != 0) {
            return "adminLogin";
        }

        return "adminSignUp";
    }

    @GetMapping("/adminLogOut")
    public String adminLogOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "adminLogin";
    }

    @GetMapping("/couponList")
    public String couponList(Model model) {

        List<CouponsVO> couponsVOList = adminService.findAllCoupon();
        model.addAttribute("couponList", couponsVOList);

        return "adminCouponList";
    }

    @GetMapping("/couponReg")
    public String couponRegPage() {
        return "adminCouponReg";
    }

    @PostMapping("/couponReg")
    @ResponseBody
    public String couponReg(CouponsVO couponsVO) {

        log.info("coupon = {}", couponsVO);

        return adminService.couponReg(couponsVO);
    }

    @GetMapping("/couponEdit")
    public String couponEdit(Model model, Long cp_idx) {

        CouponsVO couponsVO = adminService.findCouponOneByIdx(cp_idx);
        model.addAttribute("cp", couponsVO);

        return "adminCouponEdit";
    }

    @PostMapping("/updateCoupon")
    @ResponseBody
    public String updateCoupon(CouponsVO couponsVO) {
        return adminService.updateCoupon(couponsVO);
    }

    @PostMapping("/delCoupon")
    @ResponseBody
    public String delCoupon(CouponsVO couponsVO) {
        return adminService.delCoupon(couponsVO.getCp_idx());
    }


    @GetMapping("/test")
    public String test() {
        return "adminCouponList";
    }

    @PostMapping("/delAdmin")
    @ResponseBody
    public String delAdmin(Long admin_idx) {

        return adminService.delAdmin(admin_idx);
    }

}
