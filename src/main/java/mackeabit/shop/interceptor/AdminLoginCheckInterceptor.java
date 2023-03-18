package mackeabit.shop.interceptor;

import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.web.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminLoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("관리자 인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession();

        if (session.getAttribute(SessionConst.SUPER_ADMIN) == null) {
            if (session.getAttribute(SessionConst.LOGIN_ADMIN) == null) {
                log.info("미인증 관리자 요청");
                session.invalidate();
                response.sendRedirect("/admin/login?redirectURL=" + requestURI);
                return false;
            }
        }

        return true;
    }
}
