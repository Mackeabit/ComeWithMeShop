package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.mapper.MemberMapper;
import mackeabit.shop.vo.MembersVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

//    @RequiredArgsConstructor 으로 autowired 자동 주입
//    public MemberRepositoryImpl(MemberMapper memberMapper) {
//        this.memberMapper = memberMapper;
//    }

    private final MemberMapper memberMapper;

    @Override
    public void save(MembersVO membersVO) {
        log.info("Member save ==> MembersVO SAVE");
        memberMapper.save(membersVO);
    }

    //비밀번호 수정에 사용
    @Override
    public Optional<MembersVO> findByEmail(String email) {
        log.info("Member findByEmail ==> MembersVO By Email (Optional)");
        Optional<MembersVO> find = memberMapper.findByEmail(email);
        return find;
    }

    //회원 찾을 때 사용
    @Override
    public MembersVO findByIdx(Long member_idx) {
        log.info("Member findByIdx ==> MembersVO By Idx");
        return memberMapper.findByIdx(member_idx);
    }

    @Override
    public List<MembersVO> findAll() {
        log.info("Member findAll ==> List<MembersVO>");
        return memberMapper.findAll();
    }

    @Override
    public int emailCheck(String email) {
        int cnt = memberMapper.emailCheck(email);
        log.info("cnt = {}", cnt);
        return cnt;
    }
}
