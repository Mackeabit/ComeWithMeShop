package mackeabit.shop.mapper;

import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.MyPagePayDTO;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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
}
