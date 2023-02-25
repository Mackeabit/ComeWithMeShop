package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import mackeabit.shop.dto.EmailResponseDto;
import mackeabit.shop.service.EmailService;
import mackeabit.shop.util.EmailMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

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
}