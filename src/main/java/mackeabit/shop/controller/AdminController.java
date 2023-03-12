package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.AdminLogin;
import mackeabit.shop.dto.*;
import mackeabit.shop.service.AdminService;
import mackeabit.shop.vo.AdminVO;
import mackeabit.shop.vo.Annual_SalesVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.SalesVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping()
    public String adminMain(@AdminLogin AdminVO adminVO, Model model) {

        log.info("admin = {}", adminVO);
/*
테스트를 위해 잠시 주석 처리
        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }
*/

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
        Map<String , Integer> yearMonth = new ConcurrentHashMap<>();
        yearMonth.put("year", year);
        yearMonth.put("month", month);
        Integer nowMonthPrice = adminService.monthPrice(yearMonth);

        // 저번달 매출 현황
        LocalDate localDate = now.minusMonths(1);
        if (localDate.getYear() != year) { // 년도가 다르면
            year = localDate.getYear() ; // 년도를 1년 빼줌
        }

        Map<String , Integer> lastYearMonth = new ConcurrentHashMap<>();
        lastYearMonth.put("year", year);
        lastYearMonth.put("month", lastMonth);
        Integer lastMonthPrice = adminService.lastMonthPrice(lastYearMonth);

        // 일주일 일별로 가입자 수
        LocalDate today = LocalDate.now();
        LocalDate aWeekAgo = today.minusWeeks(1);
        Date startDate = Date.from(aWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Map<String , Date> selectDate = new ConcurrentHashMap<>();
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
        } else if (findAdmin.getAdmin_level() == -1){
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


    @GetMapping("/member")
    public String adminMemberInfo(@AdminLogin AdminVO adminVO, Model model, @RequestParam Long member_idx) {

/*
        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }
*/



        return "";
    }


    @GetMapping("/test")
    public String test() {
        return "adminDetailPage";
    }

}
