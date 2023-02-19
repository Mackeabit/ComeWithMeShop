package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.service.ProductService;
import mackeabit.shop.service.SubService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SubService subService;

    @GetMapping
    public String productAll(Model model,@RequestParam(defaultValue = "1") int page) {

        // 페이징 정보
        int pageSize = 9;
        int startIndex = (page - 1) * pageSize;

        // 현재 페이지와 한번에 표시할 제품 수량
        Map<String, Object> params = new HashMap<>();
//        params.put("pd_value", 4);
        params.put("pageSize", pageSize);
        params.put("startIndex", startIndex);

        //신상품 받아서 model(pd_value = 4 -> 전체 조회)
        //현재 페이지에 맞는 상품들 호출
        List<MainProductsDTO> findProducts = subService.mainPageProductsPaged(params);
        int totalCount = subService.countMainPageProducts();

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int currentPage = page;
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);



        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        return "shop";
    }

    @GetMapping("/best/{pd_kind}")
    public String bestProducts(@PathVariable Integer pd_kind, Model model) {
        if (pd_kind == 2) {
            //베스트 상품 전체일 경우
        }



        return "shop";
    }

    //상품별 페이지
    @GetMapping("/{pd_nm}")
    public String product_detail(@PathVariable String  pd_nm, Model model) {
        log.info("pd_nm = {}", pd_nm);



        return "product-details";
    }

    //카테고리별 페이지
    @GetMapping("/category/{category_ref}")
    public String product_detail(@PathVariable Integer category_ref, Model model) {
        log.info("category_ref = {}", category_ref);



        return "product-details";
    }

    //상품 이름 검색
    @GetMapping("/search")
    public String products_search(Model model, String keyword) {

        //검색한 상품 받아서 model
        List<MainProductsDTO> findProducts = productService.searchByName(keyword);

        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

//        List<MainProductsDTO> findProducts = subService.mainPageProducts(1);
//        model.addAttribute("newProducts", findProducts);

        log.info("search count = {}", findProducts.size());
        log.info("findProducts = {}", findProducts);

        model.addAttribute("searchResults", findProducts);

        return "product_lists";
    }
}
