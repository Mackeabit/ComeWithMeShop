package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.MemberRepository;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.MyOrdersDTO;
import mackeabit.shop.dto.MyPagePayDTO;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

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
            
            //비밀번호 체크(암호화)
            if (membersVO.getPwd().equals(collect)) {

                messages = "Y";
            }
        }
        
        //결과 리턴
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

    public List<MyOrdersDTO> myOrdersList() {

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        return repository.myOrdersList(attribute.getMember_idx());
    }
}
