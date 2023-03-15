package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.EmailResponseDto;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.service.EmailService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.util.EmailMessage;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final MemberService memberService;

    // 이메일 인증 - 요청 시 body 로 인증번호 반환하도록 작성하였음
    @PostMapping("/emailCheck")
    @ResponseBody
    public ResponseEntity sendJoinMail(String email) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[Come With Me] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }

    @PostMapping("/emailCode")
    @ResponseBody
    public String changeCode(HttpServletRequest request, Long member_idx) {

        String data = "Y";

        log.info("changeCode Method -> member_idx = {}", member_idx);

        HttpSession session = request.getSession(false);
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);


        if (member_idx != attribute.getMember_idx()) {
            data = "diff id";
            return data;
        }

        data = memberService.changeEmailCheck(member_idx);

        if (data.equals("N")) {
            log.error("changeCode Method is not Perfect finished -> Update members email_ck error");
        }

        return data;
    }

    //비밀 번호 찾기 요청 시
    @PostMapping("/changePwd")
    @ResponseBody
    public ResponseEntity changePwd(String email) throws NoSuchAlgorithmException {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[Come With Me] 임시 비밀번호 발급")
                .build();

        String code = emailService.sendMail(emailMessage, "change");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        SHA256 sha256 = new SHA256();
        String pwd = sha256.encrypt(code + sha256.getSALT());

        params.put("pwd", pwd);

        memberService.changePwd(params);

        return ResponseEntity.ok(emailResponseDto);
    }






}