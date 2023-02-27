package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.MemberRepository;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.MemberDetailVO;
import mackeabit.shop.vo.MembersVO;
import org.springframework.stereotype.Service;

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
}
