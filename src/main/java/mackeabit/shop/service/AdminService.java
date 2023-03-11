package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.AdminRepository;
import mackeabit.shop.dto.AdminCheckDTO;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.AdminVO;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;

    private final SHA256 sha256;


    public String adminCheck(AdminCheckDTO adminCheckDTO) throws NoSuchAlgorithmException {

        //관리자 비밀번호 암호화 비교
        String collect = sha256.encrypt(adminCheckDTO.getPwd() + sha256.getSALT());

        Optional<AdminVO> findAdmin = repository.findAdminById(adminCheckDTO.getId());

        String messages = "no_id";

        if (!findAdmin.isEmpty()) {
            AdminVO adminVO = findAdmin.get();
            messages = "no_pwd";
            log.info("findAdmin = {}", adminVO);
            if (adminVO.getPwd().equals(collect)) {
                //비밀번호까지 체크 완료
                log.info("success Admin Check");
                messages = "Y";
            }
        }
        return messages;
    }

    public AdminVO findAdminById(String id) {

        Optional<AdminVO> adminById = repository.findAdminById(id);

        if (!adminById.isEmpty()) {
            return adminById.get();
        }

        return null;
    }
}
