package mackeabit.shop.Repository;

import mackeabit.shop.vo.MembersVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(MembersVO membersVO);

    Optional<MembersVO> findByEmail(String email);

    MembersVO findByIdx(Long member_idx);

    List<MembersVO> findAll();

}
