package mackeabit.shop.mapper;

import mackeabit.shop.dto.*;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    int save(MembersVO membersVO);

    List<MembersVO> findAll();

    Optional<MembersVO> findByEmail(String email);

    MembersVO findByIdx(Long member_idx);

    int emailCheck(String email);

    int changeEmailCheck(Long member_idx);

    MemberDetailVO memberDetailOne(Long member_idx);

    int insertDetails(MemberDetailVO memberDetailVO);

    List<MainProductsDTO> findOrders(Long member_idx);

    List<MyPagePayDTO> findPayments(Long member_idx);

    MemberDetailVO findMemberDetailByMemberIdx(Long member_idx);

    int updateMembers(MembersVO membersVO);

    int updateDetails(MemberDetailVO memberDetailVO);

    List<MyOrdersDTO> myOrdersList(Map<String, Object> params);

    List<String> findOrder_mi(Long member_idx);

    List<MyPayAndOrderDTO> findPayAndOrder(Map<String, Object> params);

    int updateMemberStatus(SignUpDTO signUpDTO);

    List<MembersVO> findByDelete_dateBefore(LocalDateTime minusDays);

    int delete(MembersVO membersVO);

    List<MembersVO> findDeleteCandidates(LocalDateTime deleteCutoffTime);

    int realDelete(MembersVO member);
}
