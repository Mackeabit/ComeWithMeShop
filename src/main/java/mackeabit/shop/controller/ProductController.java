package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String productAll() {
        return "shop";
    }

    //상품별 페이지
    @GetMapping("/{pd_idx}")
    public String product_detail(@PathVariable Long pd_idx, Model model) {
        log.info("pd_idx = {}", pd_idx);
        return "product-details";
    }

    //카테고리별 페이지
    @GetMapping("/category/{category_ref}")
    public String product_detail(@PathVariable Integer category_ref, Model model) {
        log.info("category_ref = {}", category_ref);
        return "product-details";
    }


}
