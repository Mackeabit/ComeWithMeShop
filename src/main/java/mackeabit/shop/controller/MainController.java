package mackeabit.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String basic() {
        return "index";
    }

    @RequestMapping("/signup")
    public String signUpPage() {
        return "sign-up";
    }

    @RequestMapping("/login")
    public String loginpage() {
        return "login";
    }
}
