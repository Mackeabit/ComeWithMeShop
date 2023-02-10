package mackeabit.shop.mapper;

import mackeabit.shop.vo.MembersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MembersVO membersVO);

    List<MembersVO> findAll();

    Optional<MembersVO> findByEmail(String email);

    MembersVO findByIdx(Long member_idx);

    int emailCheck(String email);

}
