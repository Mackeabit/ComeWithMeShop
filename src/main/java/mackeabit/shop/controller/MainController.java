package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.argument.Login;
import mackeabit.shop.dto.*;
import mackeabit.shop.service.CartService;
import mackeabit.shop.service.MemberService;
import mackeabit.shop.service.ProductService;
import mackeabit.shop.service.SubService;
import mackeabit.shop.vo.*;
import mackeabit.shop.web.DiscountPercent;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    private final ProductService productService;
    private final SubService subService;
    private final CartService cartService;
    private final HttpServletRequest request;

    @RequestMapping("/")
    public String basic(@Login MembersVO membersVO, Model model) {

        //메인 사진 받아서 model
        List<Photos_toMainVO> photoMain = subService.findThings(0);
        model.addAttribute("photoMain", photoMain);

        //서브 사진 받아서 model(중복값 제거)
        List<Photos_toMainVO> photoSub = subService.findThings(1);
        model.addAttribute("photoSub", photoSub);

        //중복값 포함 사진+모든 상품 조회
        List<MainProductsDTO> allProducts = subService.sortAllProductsSizes();
        model.addAttribute("allProducts", allProducts);

        log.info("all Products = {}", allProducts);

        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        /**
         * pd_value
         * 0 : 보통
         * 1 : 신상품
         * 2 : 베스트 상품
         */

        //신상품 받아서 model
        List<MainProductsDTO> findProducts = subService.mainPageProducts(1);
        model.addAttribute("newProducts", findProducts);

        log.info("new Products = {}", findProducts);


        if (membersVO == null) {
            return "index";
        }

        /** 회원은 session, model 에 정보 담아서 홈화면으로 이동
         * 회원정보 - members
         * 장바구니 - cartsList
         */
        model.addAttribute("members", membersVO);

        log.info("MembersVO = {}", membersVO);

/*        List<CartsVO> cartList = cartService.findAll(membersVO.getMember_idx());
        model.addAttribute("carts", cartList);*/

        HttpSession session = request.getSession();
        List<MainCartDTO> memberCart = cartService.findMemberCart(membersVO.getMember_idx());

        log.info("memberCart = {}", memberCart);

        session.setAttribute(
                SessionConst.MEMBER_CART,
                memberCart
        );

        model.addAttribute("cartsList", cartService.findMemberCart(membersVO.getMember_idx()));

        return "index";
    }


    /* 404 테스트용 */
    @RequestMapping("/404page")
    public void errorPageTest(HttpServletResponse response) throws IOException {
        response.sendError(404, "404오류");
    }

    @GetMapping("/signup")
    public String signUpPage() {

        return "sign-up";
    }

    //트랜잭션 적용 전
    @PostMapping("/signup")
    @ResponseBody
    public String signUpCheck(SignUpDTO signUpDTO) throws NoSuchAlgorithmException {

        int res = memberService.saveMembers(signUpDTO);
        String data = "N";
        if (res > 0) {
            data = "Y";
        }
        return data;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/loginSession")
    public String loginSession(String email) {

        HttpSession session = request.getSession();
        MembersVO findMember = memberService.findByEmail(email);

        log.info("findMember = {}", findMember);

        session.setAttribute(
                SessionConst.LOGIN_MEMBER,
                findMember
        );
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }


    //메인화면 장바구니 추가
    @RequestMapping("/cartInsert")
    @ResponseBody
    public String cartInsert(CartInsertDTO search) {

        //데이터 정상 판별
        String data = "Y";

        //0. 회원이 아니면 이용 불가능
        if (search.getMember_idx() == null) {
            data = "NN";
            return data;
        }

        log.info("first search = {}", search);

        /* 넘어오는 데이터 예시
         *  1. pd_idx=6&pd_nm=BLACK+T-SHIRT&member_idx=1&cart_cnt=2&pd_color=BLACK&pd_size=M
         *  2. pd_idx=9&pd_nm=MCK+T-SHIRT&member_idx=1&cart_cnt=2&pd_color=N&pd_size=N
         *
         * idx가 아닌 이름, 옵션들(색상, 사이즈)로 찾아야함.
         *
         * */

        //1. 넘어온 데이터들에게(상품 옵션) N 값 부여되어 있으면 null 처리
        if (search.getPd_size().equals("N")) {
            search.setPd_size(null);
        }

        if (search.getPd_color().equals("N")) {
            search.setPd_color(null);
        }

        log.info("second search = {}", search);

        //2. 데이터를 통해 pd_idx 찾기(색상, 사이즈, 이름에 맞는)
        Long pd_idx = productService.findPd_idx(search);


        if (pd_idx == null) {
            data = "noIdx";
            log.error("상품 인덱스 찾기 오류 발생");
            return data;
        }

        //3. CartsVO 셋팅
        CartsVO cartsVO = new CartsVO();
        cartsVO.setMember_idx(search.getMember_idx());
        cartsVO.setPd_idx(pd_idx);
        cartsVO.setCart_cnt(search.getCart_cnt());

        //4. 셋팅한 VO로 중복 조회(중복이라면 저장X)
        Integer res = cartService.selectOne(cartsVO);
        log.info("res = {} ", res);

        if (res != null) {
            data = "exist";
            return data;
        }

        data = cartService.insertCart(cartsVO);

        return data;
    }

    @RequestMapping("/checkout")
    public String checkOut(Model model, Integer pd_idx, CheckOutDTO checkOutDTO) {

        String title = "";

        //할인, 쿠폰 등 적용 전 금액
        int beforePrice = 0;

        //할인, 쿠폰 등 적용 후 금액
        int totalPrice = 0;

        if (pd_idx != null) {
            //상품페이지에서 결제를 눌렀을 때(단일 상품 즉시 결제),
            //productService.findByPd_idx(pd_idx);

        }

        //장바구니에서 결제를 눌렀을 때,
        HttpSession session = request.getSession();
        MembersVO attribute = (MembersVO) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<MainCartDTO> memberCart = cartService.findMemberCart(attribute.getMember_idx());



        for (int i = 0; i < memberCart.size(); i++) {

            beforePrice += memberCart.get(i).getSell_price() * memberCart.get(i).getCart_cnt();

        }


        if (memberCart.size() > 1) {
            title = memberCart.get(0).getPd_nm() + " 등 " + memberCart.size() + "개";
        } else {
            title = memberCart.get(0).getPd_nm();
        }

        //쿠폰 코드로 해당 쿠폰의 가격 뽑아오는 코드 추가할 것.

        //회원등급에 따른 할인
        int sales = calculateGrade(attribute.getGrade_code(), beforePrice);

        //모든 할인 적용한 결재 금액
        totalPrice = beforePrice - Integer.parseInt(checkOutDTO.getShipping_price()) - 2000 - sales;

        /**
         *  차례대로
         *  - 상품명
         *  - 전체 가격
         *  - 쿠폰 할인
         *  - 배송료 (이미 받아와서 들어가 있음)
         *  - 회원 등급 할인
         *  - 총 결제 금액
         */
        checkOutDTO.setTitle(title);
        checkOutDTO.setPd_price(beforePrice);
        checkOutDTO.setCoupon_price(-2000);
        checkOutDTO.setGrade_sale(-sales);
        checkOutDTO.setTotal_price(totalPrice);

        model.addAttribute("checkOutInfo", checkOutDTO);

        return "checkout";
    }

    //등급별 할인 메서드
    private int calculateGrade(Integer grade_code, int beforePrice) {

        int discountPercent = 0;

        switch (grade_code) {

            case 0:
                discountPercent = DiscountPercent.NORMAL;
                break;
            case 1:
                discountPercent = DiscountPercent.SILVER;
                break;
            case 2:
                discountPercent = DiscountPercent.GOLD;
                break;
            case 3:
                discountPercent = DiscountPercent.VIP;
                break;

        }

        int sales = beforePrice * discountPercent / 100;

        return sales;
    }
}
