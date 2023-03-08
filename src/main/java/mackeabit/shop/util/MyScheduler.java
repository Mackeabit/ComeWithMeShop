package mackeabit.shop.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.vo.MembersVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyScheduler {

    private final MemberService memberService;

    // * 을 입력할경우 모두(항상)으로 설정함.
    //                 초  분  시  일  월  요일
    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * ?")
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


        // 3개월이 지난 탈퇴 회원 정보는 삭제
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

    }

}
