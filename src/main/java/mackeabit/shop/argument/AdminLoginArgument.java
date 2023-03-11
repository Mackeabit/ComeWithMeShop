package mackeabit.shop.argument;

import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.vo.AdminVO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminLoginArgument implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("Admin supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(AdminLogin.class);

        boolean hasMemberType = AdminVO.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("Admin resolveArgument 실행");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }


        Object admin = session.getAttribute(SessionConst.LOGIN_ADMIN);
        log.info("admin resolve = {}",admin);
        if (admin == null) {
            admin = session.getAttribute(SessionConst.SUPER_ADMIN);
        }

        return admin;
    }
}
