package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.*;
import mackeabit.shop.mapper.MemberMapper;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    public int save(MembersVO membersVO) {
        log.info("Member save ==> MembersVO SAVE");
        int res = memberMapper.save(membersVO);
        return res;
    }

    //비밀번호 수정에 사용
    @Override
    public Optional<MembersVO> findByEmail(String email) {
        Optional<MembersVO> find = memberMapper.findByEmail(email);
        log.info("find = {}", find);
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

    @Override
    public int changeEmailCheck(Long member_idx) {
        return memberMapper.changeEmailCheck(member_idx);
    }

    @Override
    public MemberDetailVO memberDetailOne(Long member_idx) {
        return memberMapper.memberDetailOne(member_idx);
    }

    @Override
    public int insertDetails(MemberDetailVO memberDetailVO) {
        return memberMapper.insertDetails(memberDetailVO);
    }

    @Override
    public List<MainProductsDTO> findOrders(Long member_idx) {
        return memberMapper.findOrders(member_idx);
    }

    @Override
    public List<MyPagePayDTO> findPayments(Long member_idx) {
        return memberMapper.findPayments(member_idx);
    }

    @Override
    public MemberDetailVO findMemberDetailByMemberIdx(Long member_idx) {
        return memberMapper.findMemberDetailByMemberIdx(member_idx);
    }

    @Override
    public int updateMembers(MembersVO membersVO) {
        return memberMapper.updateMembers(membersVO);
    }

    @Override
    public int updateDetails(MemberDetailVO memberDetailVO) {
        return memberMapper.updateDetails(memberDetailVO);
    }

    @Override
    public List<MyOrdersDTO> myOrdersList(Map<String, Object> params) {
        return memberMapper.myOrdersList(params);
    }

    @Override
    public List<String> findOrder_mi(Long member_idx) {
        return memberMapper.findOrder_mi(member_idx);
    }

    @Override
    public List<MyPayAndOrderDTO> findPayAndOrder(Map<String, Object> params) {
        return memberMapper.findPayAndOrder(params);
    }

    @Override
    public int updateMemberStatus(SignUpDTO signUpDTO) {
        return memberMapper.updateMemberStatus(signUpDTO);
    }

    @Override
    public List<MembersVO> findByDelete_dateBefore(LocalDateTime minusDays) {
        return memberMapper.findByDelete_dateBefore(minusDays);
    }

    @Override
    public int delete(MembersVO membersVO) {
        return memberMapper.delete(membersVO);
    }

    @Override
    public List<MembersVO> findDeleteCandidates(LocalDateTime deleteCutoffTime) {
        return memberMapper.findDeleteCandidates(deleteCutoffTime);
    }

    @Override
    public int realDelete(MembersVO member) {
        return memberMapper.realDelete(member);
    }


}
