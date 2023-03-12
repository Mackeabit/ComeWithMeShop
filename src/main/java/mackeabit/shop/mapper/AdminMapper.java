package mackeabit.shop.mapper;

import mackeabit.shop.dto.AdminMainOrderListDTO;
import mackeabit.shop.dto.AdminMainPayListDTO;
import mackeabit.shop.dto.MembersAllInfoDTO;
import mackeabit.shop.dto.SignCountWeekDTO;
import mackeabit.shop.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Mapper
public interface AdminMapper {

    Optional<AdminVO> findAdminById(String id);

    Annual_SalesVO findNowYearSales(int year);

    Annual_SalesVO findLastYearSales(int year);

    Integer findMemberTodaySignUp(String todayStr);

    Integer findMemberYesterdaySignUp(String yesterdayStr);

    Integer monthPrice(Map<String , Integer> yearMonth);

    Integer countVisit(String day);

    List<SignCountWeekDTO> signCountWeek(Map<String , Date> selectDate);

    List<SignCountWeekDTO> visitCountWeek(Map<String , Date> selectDate);

    List<SalesVO> weekSales(Map<String , Date> selectDate);

    List<AdminMainPayListDTO> findAdminMainPayList();

    List<AdminMainOrderListDTO> findAdminMainOrderList();

    Integer lastMonthPrice(Map<String, Integer> yearMonth);

    List<AdminVO> findAll();

    List<MembersVO> findAllMembers();

    MembersAllInfoDTO findMemberAllInfo(Long member_idx);

    List<Members_logVO> findMemberLog(Long member_idx);

    int updateMembers(MembersAllInfoDTO membersAllInfoDTO);

    int updateMemberDetails(MembersAllInfoDTO membersAllInfoDTO);

    MembersVO findByEmail(String email);

    int delMemberByMember_idx(Long member_idx);
}
