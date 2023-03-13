package mackeabit.shop.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.service.AdminService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.Monthly_SalesVO;
import mackeabit.shop.vo.SalesVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyScheduler {

    private final MemberService memberService;
    private final AdminService adminService;

    // * 을 입력할경우 모두(항상)으로 설정함.
    //                 초  분  시  일  월  요일
    // 매일 자정에 실행
    @Scheduled(cron = "0 30 12 * * ?")
    public void deleteInactiveMembers() {

        log.info("MyScheduler 실행");

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // delete_date 가 24시간 이상인 회원 찾기
        List<MembersVO> members = memberService.findByDelete_dateBefore(now.minusDays(1));

        int res = 0;

        for (MembersVO member : members) {

            // 회원 정보 삭제 (업데이트로 member_status 만 변경)
            // 3개월 이후 정보 모두 삭제
            res = memberService.delete(member);

        }

        if (res > 0) {
            log.info("회원 탈퇴 적용 계정 수 = {}", res);
        } else {
            log.info("회원 탈퇴 적용 계정이 없습니다.");
        }


        // 탈퇴시간 3개월이 지난 탈퇴 회원 정보는 삭제
        LocalDateTime deleteCutoffTime = now.minusMonths(3);
        List<MembersVO> deleteCandidates = memberService.findDeleteCandidates(deleteCutoffTime);

        int resReal = 0;

        for (MembersVO member : deleteCandidates) {
            /* 구매, 주문 등은 삭제하지 않고 개인정보만 삭제 */
            resReal = memberService.realDelete(member);
        }

        if (resReal > 0) {
            log.info("개인정보 삭제 계정 수 = {}", resReal);
        } else {
            log.info("개인정보 삭제 계정이 없습니다.");
        }


        // 휴면 계정 전환 (현재로 부터 1개월 전)
        LocalDateTime restTime = now.minusMonths(1);
        List<MembersVO> restCandidates = memberService.findRestCandidates(restTime);

        int changeCnt = 0;

        for (MembersVO member : restCandidates) {
            /* 검색된 계정들을 휴면 계정으로 상태 변경 */
            changeCnt = memberService.changeStatusAtRest(member);
        }

        if (changeCnt > 0) {
            log.info("휴면 계정 전환한 계정 수 = {}", changeCnt);
        } else {
            log.info("휴면 계정으로 바뀐 계정이 없습니다.");
        }

        //일일 매출액 계산
        //0시 기준에 실행이 되므로 하루 전날짜의 총 일일매출 구하기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(formatter);
        String startDate = LocalDate.now().minusDays(1).format(formatter);
        String endDate = today;

        String twoAgo = LocalDate.now().minusDays(2).format(formatter);


        Map<String, Object> params = new ConcurrentHashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        //하루 동안의 총 매출액
        Long total_sales = adminService.daySaleTotal(params);
        if (total_sales == null) {
            total_sales = Long.valueOf(0);
        }

        //그 전날의 평균 매출액
        Long avr_sales = adminService.findAvrPrice(twoAgo);
        if (avr_sales == null) {
            avr_sales = Long.valueOf(0);
        }

        //입력해야 할 평균 매출액
        Long insertAvrSales = (total_sales + avr_sales) / 2;

        SalesVO salesVO = new SalesVO();
        salesVO.setTotal_sales(total_sales);
        salesVO.setAvr_sales(insertAvrSales);

        adminService.daySalesUpdate(salesVO);

    }// 매일 자정에 실행되는 스케쥴러


    // 매월 1일 0시에 실행
    @Scheduled(cron = "0 0 0 1 * ?")
    public void monthlySalesScheduler() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        Map<String, Object> monthlyParams = new ConcurrentHashMap<>();
        monthlyParams.put("year", year);
        monthlyParams.put("month", month);


        // 해당월의 매출 데이터 조회
        List<SalesVO> salesList = adminService.selectByMonth(monthlyParams);

        // 해당월의 총 매출 및 평균 매출 계산
        Long totalSales = Long.valueOf(0);
        for (SalesVO sales : salesList) {
            totalSales += sales.getTotal_sales();
        }
        Long avrSales = totalSales / salesList.size();

        // MonthlySales 객체 생성
        Monthly_SalesVO monthlySales = new Monthly_SalesVO();
        monthlySales.setYear(year);
        monthlySales.setMonth(month);
        monthlySales.setTotal_sales(totalSales);
        monthlySales.setAvr_sales(avrSales);

        // 매월 DB 저장
        adminService.monthlySaleInsert(monthlySales);

    }


}
