package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.MemberRepository;
import mackeabit.shop.dto.*;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.Members_logVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

//    @RequiredArgsConstructor 으로 autowired 자동 주입
//    public MemberService(MemberRepository repository) {
//        this.repository = repository;
//    }

    private final MemberRepository repository;
    private final HttpServletRequest request;

    private final SHA256 sha256;

    public List<MembersVO> findAll() {

        return repository.findAll();

    }

    public MembersVO findByEmail(String email) {
        return repository.findByEmail(email).get();
    }

    public int emailCheck(String email) {
        return repository.emailCheck(email);
    }

    public int saveMembers(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {

        MembersVO membersVO = new MembersVO();
        membersVO.setEmail(signUpDTO.getEmail());

        //비밀번호 암호화
        membersVO.setPwd(
                sha256.encrypt(
                        signUpDTO.getPwd()+sha256.getSALT()
                ));

        return repository.save(membersVO);
    }


    public String checkID(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {

        String collect = sha256.encrypt(signUpDTO.getPwd() + sha256.getSALT());

        Optional<MembersVO> findMember = repository.findByEmail(signUpDTO.getEmail());


        /*repository.findByEmail(signUpDTO.getEmail())
                .filter(m -> m.getPwd().equals(collect))
                .orElse(null);*/

        //암호화 정보 비교(로그인)

        String messages = "no_email";
        
        //이메일 체크
        if (!findMember.isEmpty()) {
            MembersVO membersVO = findMember.get();
            log.info("member = {}", membersVO.getPwd());
            log.info("collect = {}", collect);
            messages = "no_pwd";
            
            //비밀번호 체크(암호화 비교)
            if (membersVO.getPwd().equals(collect)) {

                /* 아이디(이메일), 비밀번호 검사 모두 끝내고 실행 */

                // 계정의 status 에 따른 코드 실행
                int status = membersVO.getMember_status();

                if(status == -1){
                    //탈퇴 계정 : -1
                    messages = "del_account";
                } else if (status == 0) {
                    //휴면 계정 : 0
                    messages = "rest_account";
                } else {
                    //정상 계정 : 1 , 탈퇴 대기 : 2

                    //IP
                    String ipAddress = request.getRemoteAddr();

                    //Data Setting
                    Members_logVO members_logVO = new Members_logVO();
                    members_logVO.setMember_idx(membersVO.getMember_idx());
                    members_logVO.setLogin_ip(ipAddress);

                    //member_log 작성(로그인 기록, IP)
                    int result = repository.saveMember_log(members_logVO);

                    //members.login_date 작성 (최근 로그인)
                    result += repository.updateMemberLogin_date(membersVO);

                    log.info("login_date cnt = {}", result);

                    // result 가 정상 실행시 무조건 2++ 를 처리
                    if (result > 1) {
                        messages = "Y";
                    } else {
                        messages = "no_logSave";
                        log.error("no_logSave METHOD IS checkID");
                    }

                }//계정 status. else


            }//비밀번호 체크 if
        }//이메일 체크 if
        
        //결과값 리턴
        return messages;
    }

    public String  changeEmailCheck(Long member_idx) {

        String data = "N";

        int res = repository.changeEmailCheck(member_idx);

        if (res > 0) {
            data = "Y";
        }

        return data;
    }

    public MemberDetailVO memberDetailOne(Long member_idx) {
        return repository.memberDetailOne(member_idx);
    }


    public String insertDetails(MemberDetailVO memberDetailVO) {
        String data = "N";

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        memberDetailVO.setMember_idx(attribute.getMember_idx());

        int res = repository.insertDetails(memberDetailVO);

        if (res > 0) {
            data = "Y";
        }

        return data;
    }

    public List<MainProductsDTO> findOrders(Long member_idx) {
        return repository.findOrders(member_idx);
    }

    public List<MyPagePayDTO> findPayments(Long member_idx) {
        return repository.findPayments(member_idx);
    }

    public MemberDetailVO findMemberDetailByMemberIdx(Long member_idx) {
        return repository.findMemberDetailByMemberIdx(member_idx);
    }

    @Transactional
    public String updateMyPageDetails(MemberDetailVO memberDetailVO, String pwd) throws NoSuchAlgorithmException {
        String data = "N";

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        memberDetailVO.setMember_idx(attribute.getMember_idx());

        log.info("memberDetail = {}", memberDetailVO);


        int res = 0;

        if (pwd != null) {

            log.info("pwd change");

            MembersVO membersVO = new MembersVO();
            membersVO.setMember_idx(attribute.getMember_idx());
            membersVO.setPwd(sha256.encrypt(
                    pwd + sha256.getSALT()
            ));

            res += repository.updateMembers(membersVO);
            res += repository.updateDetails(memberDetailVO);
        } else if (pwd == null){
            log.info("member_detail change");
            res = repository.updateDetails(memberDetailVO);
        }


        if (res > 0) {
            data = "Y";
        }

        return data;
    }

/*    public List<MyOrdersDTO> myOrdersList() {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return repository.myOrdersList(attribute.getMember_idx());
    }*/

    public List<MyOrdersDTO> myOrdersList(int limit, int offset) {
        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Map<String, Object> params = new HashMap<>();
        params.put("member_idx", attribute.getMember_idx());
        params.put("limit", limit);
        params.put("offset", offset);

        return repository.myOrdersList(params);
    }

    public List<MyPayAndOrderDTO> myPayAndOrderList(int level) {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //주문 번호 가져오기(중복 제거)
        List<String> order_mi = repository.findOrder_mi(attribute.getMember_idx());

        log.info("level = {}", level);
        log.info("order_mi.size = {}", order_mi.size());

        if (level + 1 > order_mi.size()) {
            return null;
        }

        /* ex List<String> order_mi
        * merchant_1678114143117, merchant_1678108223709, merchant_1678108168180
        * */

        //받아온 주문 번호 중 원하는 level (인덱스)에 해당하는 결제+주문 내역 갖고 오기
        //인덱스 0 -> 가장 최근 주문번호, 1 -> 최근에서 두번째 주문번호
        Map<String, Object> params = new HashMap<>();
        params.put("member_idx", attribute.getMember_idx());
        params.put("order_mi", order_mi.get(level));

        log.info("현재 조회 중인 주문번호 = {}", order_mi.get(level));

        List<MyPayAndOrderDTO> findList = repository.findPayAndOrder(params);

        if (findList == null) {

            //null 이면
            MyPayAndOrderDTO myPayAndOrderDTO = new MyPayAndOrderDTO();
            myPayAndOrderDTO.setOrder_mi("결제 내역이 없습니다.");
            findList = new ArrayList<>();
            findList.add(myPayAndOrderDTO);

        }

        return findList;
    }

    public String updateMemberStatus(SignUpDTO signUpDTO) {

        /* 1. 회원 계정 상태 변환
        *  2. 회원 탈퇴 날짜 작성 */

        String data = "N";

        int res = repository.updateMemberStatus(signUpDTO);

        if (res > 0) {
            data = "Y";
        }

        return data;
    }

    public List<MembersVO> findByDelete_dateBefore(LocalDateTime minusDays) {
        return repository.findByDelete_dateBefore(minusDays);
    }

    public int delete(MembersVO membersVO) {
        return repository.delete(membersVO);
    }

    public List<MembersVO> findDeleteCandidates(LocalDateTime deleteCutoffTime) {
        return repository.findDeleteCandidates(deleteCutoffTime);
    }

    public int realDelete(MembersVO member) {

        //Members_detail Table Delete

        return repository.realDeleteInfo(member);
    }

    public List<MembersVO> findRestCandidates(LocalDateTime restTime) {
        return repository.findRestCandidates(restTime);
    }

    public int changeStatusAtRest(MembersVO member) {
        return repository.changeStatusAtRest(member);
    }

    public String restoreMemberStatus(SignUpDTO signUpDTO) {

        String data = "N";

        int res = repository.restoreMemberStatus(signUpDTO);

        //UPDATE 쿼리가 정상 실행 되었는지 체크
        if (res > 0) {
            data = "Y";
        }

        return data;
    }

    public List<MyReviewsDTO> enableMyReviews(Long member_idx) {
        return repository.enableMyReviews(member_idx);
    }

    public List<MyReviewsDTO> disableMyReviews(Long member_idx) {
        return repository.disableMyReviews(member_idx);
    }
}
