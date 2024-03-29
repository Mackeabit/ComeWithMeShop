package mackeabit.shop;

import mackeabit.shop.argument.AdminLoginArgument;
import mackeabit.shop.argument.LoginMemberArgument;
import mackeabit.shop.interceptor.AdminLoginCheckInterceptor;
import mackeabit.shop.interceptor.LogInterceptor;
import mackeabit.shop.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new AdminLoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin","/admin/login", "/admin/adminCheck", "/admin/adminSession");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(3)
                .addPathPatterns("/myPage/**");


    }

    //@Login 어노테이션 활용
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgument());
        resolvers.add(new AdminLoginArgument());
    }

}
