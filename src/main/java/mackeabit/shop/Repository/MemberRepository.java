package mackeabit.shop.Repository;

import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.MyOrdersDTO;
import mackeabit.shop.dto.MyPagePayDTO;
import mackeabit.shop.dto.MyPayAndOrderDTO;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;

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
}
