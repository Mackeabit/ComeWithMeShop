package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.SubRepositoryImpl;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.vo.CategorysVO;
import mackeabit.shop.vo.Photos_toMainVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubService {

    private final SubRepositoryImpl subRepository;

    public List<Photos_toMainVO> findAll() {

        return subRepository.findAll();
    }

    public List<Photos_toMainVO> findThings(int to_use) {
        return subRepository.findThings(to_use);
    }

    public List<MainProductsDTO> mainPageProducts(Integer pd_value) {
        return subRepository.mainPageProducts(pd_value);
    }

    /**
     *  모든 상품 + 사진 엮어서 size 정렬 시킨 DB값
     */
    public List<MainProductsDTO> sortAllProductsSizes() {
        return subRepository.sortAllProductsSizes();
    }


    public List<MainProductsDTO> mainPageProductsPaged(Map<String, Object> params) {
        return subRepository.mainPageProductsPaged(params);
    }

    public int countMainPageProducts() {
        return subRepository.countMainPageProducts();
    }

    public CategorysVO findCategories(int category_code) {
        CategorysVO categorysVO = subRepository.findCategories(category_code);
        return categorysVO;
    }
}
