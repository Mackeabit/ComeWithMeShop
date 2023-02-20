package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.SgPdDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.service.ProductService;
import mackeabit.shop.service.SubService;
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


    public static final int PAGE_SIZE = 9;

    @GetMapping
    public String productAll(Model model,@RequestParam(defaultValue = "1") int page) {

        // 페이징 정보
        int startIndex = (page - 1) * PAGE_SIZE;

        // 현재 페이지와 한번에 표시할 제품 수량
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", PAGE_SIZE);
        params.put("startIndex", startIndex);



        //페이징 정보를 통해 상품들 호출
        List<MainProductsDTO> findProducts = subService.mainPageProductsPaged(params);
        int totalCount = subService.countMainPageProducts();

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int currentPage = page;
        log.info("page = {} ", page);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        log.info("pageNumbers = {} ", pageNumbers);
        log.info("totalPages = {} ", totalPages);

        log.info("pr = {}",findProducts);

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("nowURL", "products");

        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        //추천 상품(랜덤)
        List<SgPdDTO> suggestPd = productService.findSuggest(params);
        log.info("suggestPd = {}", suggestPd);
        model.addAttribute("suggestProducts", suggestPd);


        return "shop";
    }



    @GetMapping("/best/{pd_value}/{pd_kind}")
    public String bestProducts(@PathVariable int pd_value, @PathVariable int pd_kind, Model model, @RequestParam(defaultValue = "1") int page) {

        // 페이징 정보
        int startIndex = (page - 1) * PAGE_SIZE;

        log.info("pd_value = {}", pd_value);
        log.info("pd_kind = {}", pd_kind);

        // 현재 페이지와 한번에 표시할 제품 수량
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", PAGE_SIZE);
        params.put("startIndex", startIndex);
        //pd_value -> 0:일반상품, 1:new, 2:best
        params.put("pd_value", pd_value);
        //pd_kind -> 0:남자, 1:여자, 2:남녀
        params.put("pd_kind", pd_kind);

        //페이징 정보를 통해 상품들 호출
        List<MainProductsDTO> findProducts = productService.bestProducts(params);
        int totalCount = productService.countBestProducts(params);

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int currentPage = page;
        log.info("best page = {} ", page);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        log.info("pageNumbers = {} ", pageNumbers);
        log.info("totalPages = {} ", totalPages);

        log.info("pr = {}",findProducts);

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("nowURL", "products/best/"+pd_value+"/"+pd_kind);


        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        //추천 상품(랜덤)
        List<SgPdDTO> suggestPd = productService.findSuggest(params);
        model.addAttribute("suggestPd", suggestPd);

        return "shop";
    }

    //상품별 페이지
    @GetMapping("/{pd_nm}")
    public String product_detail(@PathVariable String  pd_nm, Model model) {
        log.info("pd_nm = {}", pd_nm);



        return "product-details";
    }

    //카테고리별 페이지
    @GetMapping("/category/{category_code}")
    public String category_products(@PathVariable int category_code, Model model, @RequestParam(defaultValue = "1") int page) {
        log.info("category_ref = {}", category_code);

        // 페이징 정보
        int startIndex = (page - 1) * PAGE_SIZE;

        // 현재 페이지와 한번에 표시할 제품 수량
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", PAGE_SIZE);
        params.put("startIndex", startIndex);



        //페이징 정보를 통해 상품들 호출
        List<MainProductsDTO> findProducts = subService.mainPageProductsPaged(params);
        int totalCount = subService.countMainPageProducts();

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int currentPage = page;
        log.info("page = {} ", page);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        log.info("pageNumbers = {} ", pageNumbers);
        log.info("totalPages = {} ", totalPages);

        log.info("pr = {}",findProducts);

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("nowURL", "products");

        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        //추천 상품(랜덤)
        List<SgPdDTO> suggestPd = productService.findSuggest(params);
        log.info("suggestPd = {}", suggestPd);
        model.addAttribute("suggestProducts", suggestPd);


        return "shop";
    }

    //카테고리별 페이지
    @GetMapping("/woman/category/{category_code}")
    public String womanProducts(@PathVariable int category_code, Model model, @RequestParam(defaultValue = "1") int page) {


        // 페이징 정보
        int startIndex = (page - 1) * PAGE_SIZE;

        // 현재 페이지와 한번에 표시할 제품 수량
        //pd_kind -> 0:남자, 1:여자, 2:남녀
        log.info("woman method = {}", category_code);

        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", PAGE_SIZE);
        params.put("startIndex", startIndex);
        params.put("pd_kind", 1);
        params.put("category_code", category_code);

        //쿼리문 대충 만들어놓음, 이용해서 코드 연결하기

        //페이징 정보를 통해 상품들 호출
        List<MainProductsDTO> findProducts = productService.categoryProducts(params);
        int totalCount = productService.countCategoryProducts(params);

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int currentPage = page;
        log.info("page = {} ", page);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        log.info("pageNumbers = {} ", pageNumbers);
        log.info("totalPages = {} ", totalPages);

        log.info("pr = {}",findProducts);

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("nowURL", "products/woman/category/"+ category_code);


        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        //추천 상품(랜덤)
        List<SgPdDTO> suggestPd = productService.findSuggest(params);
        log.info("suggestPd = {}", suggestPd);
        model.addAttribute("suggestProducts", suggestPd);



        return "shop";
    }

    //카테고리별 페이지
    @GetMapping("/man/category/{category_code}")
    public String manProducts(@PathVariable int category_code, Model model, @RequestParam(defaultValue = "1") int page) {
        //pd_kind -> 0:남자, 1:여자, 2:남녀
        log.info("man method = {}", category_code);


        // 페이징 정보
        int startIndex = (page - 1) * PAGE_SIZE;

        // 현재 페이지와 한번에 표시할 제품 수량
        //pd_kind -> 0:남자, 1:여자, 2:남녀
        log.info("woman method = {}", category_code);

        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", PAGE_SIZE);
        params.put("startIndex", startIndex);
        params.put("pd_kind", 0);
        params.put("category_code", category_code);

        //쿼리문 대충 만들어놓음, 이용해서 코드 연결하기

        //페이징 정보를 통해 상품들 호출
        List<MainProductsDTO> findProducts = productService.categoryProducts(params);
        int totalCount = productService.countCategoryProducts(params);

        // 페이지 정보 (총페이지 반올림, 현재 페이지)
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        int currentPage = page;
        log.info("page = {} ", page);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        log.info("pageNumbers = {} ", pageNumbers);
        log.info("totalPages = {} ", totalPages);

        log.info("pr = {}",findProducts);

        // 페이징 정보 model 저장
        model.addAttribute("newProducts", findProducts);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("nowURL", "products/man/category/"+ category_code);


        //상품 색상
        List<ColorsDTO> productColors = productService.findColors();
        model.addAttribute("findColors", productColors);

        //상품 사이즈
        List<SizesDTO> productSizes = productService.findSizes();
        model.addAttribute("findSizes", productSizes);

        //추천 상품(랜덤)
        List<SgPdDTO> suggestPd = productService.findSuggest(params);
        log.info("suggestPd = {}", suggestPd);
        model.addAttribute("suggestProducts", suggestPd);


        return "shop";
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
