package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.AdminMainOrderListDTO;
import mackeabit.shop.dto.AdminMainPayListDTO;
import mackeabit.shop.dto.SignCountWeekDTO;
import mackeabit.shop.mapper.AdminMapper;
import mackeabit.shop.vo.AdminVO;
import mackeabit.shop.vo.Annual_SalesVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.SalesVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final AdminMapper adminMapper;

    public Optional<AdminVO> findAdminById(String id) {
        return adminMapper.findAdminById(id);
    }

    public Annual_SalesVO findNowYearSales(int year) {
        return adminMapper.findNowYearSales(year);
    }

    public Annual_SalesVO findLastYearSales(int year) {
        return adminMapper.findLastYearSales(year);
    }

    public Integer findMemberTodaySignUp(String todayStr) {
        return adminMapper.findMemberTodaySignUp(todayStr);
    }

    public Integer findMemberYesterdaySignUp(String yesterdayStr) {
        return adminMapper.findMemberYesterdaySignUp(yesterdayStr);
    }

    public Integer monthPrice(Map<String , Integer> yearMonth) {
        return adminMapper.monthPrice(yearMonth);
    }

    public Integer countVisit(String day) {
        return adminMapper.countVisit(day);
    }

    public List<SignCountWeekDTO> signCountWeek(Map<String , Date> selectDate) {
        return adminMapper.signCountWeek(selectDate);
    }

    public List<SignCountWeekDTO> visitCountWeek(Map<String , Date> selectDate) {
        return adminMapper.visitCountWeek(selectDate);
    }

    public List<SalesVO> weekSales(Map<String , Date> selectDate) {
        return adminMapper.weekSales(selectDate);
    }

    public List<AdminMainPayListDTO> findAdminMainPayList() {
        return adminMapper.findAdminMainPayList();
    }

    public List<AdminMainOrderListDTO> findAdminMainOrderList() {
        return adminMapper.findAdminMainOrderList();
    }

    public Integer lastMonthPrice(Map<String, Integer> yearMonth) {
        return adminMapper.lastMonthPrice(yearMonth);
    }

    public List<AdminVO> findAll() {
        return adminMapper.findAll();
    }

    public List<MembersVO> findAllMembers() {
        return adminMapper.findAllMembers();
    }
}
