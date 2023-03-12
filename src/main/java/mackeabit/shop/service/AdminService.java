package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.AdminRepository;
import mackeabit.shop.dto.*;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.*;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;

    private final SHA256 sha256;


    public String adminCheck(AdminCheckDTO adminCheckDTO) throws NoSuchAlgorithmException {

        //관리자 비밀번호 암호화 비교
        String collect = sha256.encrypt(adminCheckDTO.getPwd() + sha256.getSALT());

        Optional<AdminVO> findAdmin = repository.findAdminById(adminCheckDTO.getId());

        String messages = "no_id";

        if (!findAdmin.isEmpty()) {
            AdminVO adminVO = findAdmin.get();
            messages = "no_pwd";
            log.info("findAdmin = {}", adminVO);
            if (adminVO.getPwd().equals(collect)) {
                //비밀번호까지 체크 완료
                log.info("success Admin Check");
                messages = "Y";
            }
        }
        return messages;
    }

    public AdminVO findAdminById(String id) {

        Optional<AdminVO> adminById = repository.findAdminById(id);

        if (!adminById.isEmpty()) {
            return adminById.get();
        }

        return null;
    }

    public Annual_SalesVO findAnnualSales(int year, int check) {

        if (check == 0) {
            //올해 매출 구하기
            Annual_SalesVO annual_salesVO = repository.findNowYearSales(year);
            return annual_salesVO;
        }

        // 작년 매출 구하기
        Annual_SalesVO annual_salesVO = repository.findLastYearSales(year);

        return annual_salesVO;
    }

    public Integer findMemberTodaySignUp(String todayStr) {
        return repository.findMemberTodaySignUp(todayStr);
    }

    public Integer findMemberYesterdaySignUp(String yesterdayStr) {
        return repository.findMemberYesterdaySignUp(yesterdayStr);
    }

    public Integer monthPrice(Map<String , Integer> yearMonth) {
        return repository.monthPrice(yearMonth);
    }

    public Integer countVisit(String day) {
        return repository.countVisit(day);
    }

    public List<SignCountWeekDTO> signCountWeek(Map<String , Date> selectDate) {
        return repository.signCountWeek(selectDate);
    }

    public List<SignCountWeekDTO> visitCountWeek(Map<String , Date> selectDate) {
        return repository.visitCountWeek(selectDate);
    }

    public List<SalesVO> weekSales(Map<String , Date> selectDate) {
        return repository.weekSales(selectDate);
    }

    public List<AdminMainPayListDTO> findAdminMainPayList() {
        return repository.findAdminMainPayList();
    }

    public List<AdminMainOrderListDTO> findAdminMainOrderList() {
        return repository.findAdminMainOrderList();
    }

    public Integer lastMonthPrice(Map<String, Integer> yearMonth) {
        return repository.lastMonthPrice(yearMonth);
    }

    public List<AdminVO> findAll() {
        return repository.findAll();
    }

    public List<MembersVO> findAllMembers() {
        return repository.findAllMembers();
    }

    public MembersAllInfoDTO findMemberAllInfo(Long member_idx) {
        return repository.findMemberAllInfo(member_idx);
    }

    public List<Members_logVO> findMemberLog(Long member_idx) {
        return repository.findMemberLog(member_idx);
    }
}
