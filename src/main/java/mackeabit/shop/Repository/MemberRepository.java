package mackeabit.shop.Repository;

import mackeabit.shop.dto.*;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.Members_logVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository {

    int save(MembersVO membersVO);

    Optional<MembersVO> findByEmail(String email);

    MembersVO findByIdx(Long member_idx);

    List<MembersVO> findAll();

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

    int saveMember_log(Members_logVO members_logVO);

    int realDeleteInfo(MembersVO member);


    int updateMemberLogin_date(MembersVO membersVO);
}
