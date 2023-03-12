package mackeabit.shop.mapper;

import mackeabit.shop.dto.AdminMainOrderListDTO;
import mackeabit.shop.dto.AdminMainPayListDTO;
import mackeabit.shop.dto.SignCountWeekDTO;
import mackeabit.shop.vo.AdminVO;
import mackeabit.shop.vo.Annual_SalesVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.SalesVO;
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
}
