package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.*;
import mackeabit.shop.mapper.AdminMapper;
import mackeabit.shop.vo.*;
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

    public MembersAllInfoDTO findMemberAllInfo(Long member_idx) {
        return adminMapper.findMemberAllInfo(member_idx);
    }

    public List<Members_logVO> findMemberLog(Long member_idx) {
        return adminMapper.findMemberLog(member_idx);
    }

    public int updateMembers(MembersAllInfoDTO membersAllInfoDTO) {
        return adminMapper.updateMembers(membersAllInfoDTO);
    }

    public int updateMemberDetails(MembersAllInfoDTO membersAllInfoDTO) {
        return adminMapper.updateMemberDetails(membersAllInfoDTO);
    }

    public MembersVO findByEmail(String email) {
        return adminMapper.findByEmail(email);
    }

    public int delMemberByMember_idx(Long member_idx) {
        return adminMapper.delMemberByMember_idx(member_idx);
    }

    public List<CategorysVO> findCategory() {
        return adminMapper.findCategory();
    }

    public void daySalesUpdate(SalesVO salesVO) {
        adminMapper.daySalesUpdate(salesVO);
    }

    public Long daySaleTotal(Map<String, Object> params) {
        return adminMapper.daySaleTotal(params);
    }

    public Long findAvrPrice(String twoAgo) {
        return adminMapper.findAvrPrice(twoAgo);
    }

    public List<SalesVO> selectByMonth(Map<String, Object> monthlyParams) {
        return adminMapper.selectByMonth(monthlyParams);
    }

    public void monthlySaleInsert(Monthly_SalesVO monthlySales) {
        adminMapper.monthlySaleInsert(monthlySales);
    }

    public List<Monthly_SalesVO> selectMonthlySalesByYear(int year) {
        return adminMapper.selectMonthlySalesByYear(year);
    }

    public void insertAnnualSales(Annual_SalesVO annualSales) {
        adminMapper.insertAnnualSales(annualSales);
    }

    public List<MainProductsDTO> findProductsList() {
        return adminMapper.findProductsList();
    }

}
