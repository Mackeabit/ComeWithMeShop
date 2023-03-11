package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.AdminLogin;
import mackeabit.shop.dto.AdminCheckDTO;
import mackeabit.shop.dto.SignUpDTO;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.service.AdminService;
import mackeabit.shop.vo.AdminVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping()
    public String adminMain(@AdminLogin AdminVO adminVO) {

        log.info("admin = {}", adminVO);

        //AdminVO 값이 없으면 로그인 화면
        if (adminVO == null) {
            return "adminLogin";
        }

        //AdminVO 값이 있다면 해당하는 세션과 함께 관리자 페이지 진입
        return "adminDashboard";
    }

    @PostMapping("/adminCheck")
    @ResponseBody
    public String adminCheck(AdminCheckDTO adminCheckDTO) throws NoSuchAlgorithmException {

        return adminService.adminCheck(adminCheckDTO);
    }

    @GetMapping("/adminSession")
    public String adminSession(HttpServletRequest request, @RequestParam String id) {

        HttpSession session = request.getSession();
        AdminVO findAdmin = adminService.findAdminById(id);

        log.info("adminSession findAdmin = {}", findAdmin);

        if (findAdmin.getAdmin_level() == 0) {
            //최고 관리자
            session.setAttribute(SessionConst.SUPER_ADMIN, findAdmin);
        } else if (findAdmin.getAdmin_level() == -1){
            //일반 관리자
            session.setAttribute(
                    SessionConst.LOGIN_ADMIN,
                    findAdmin
            );
        }

        return "redirect:/admin";
    }

    @GetMapping("/test")
    public String test() {
        return "tables";
    }

}
