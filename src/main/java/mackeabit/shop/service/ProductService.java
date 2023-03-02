package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.ProductRepository;
import mackeabit.shop.dto.*;
import mackeabit.shop.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductsVO findOne(Long pd_idx) {
        return repository.findByIdx(pd_idx);
    }

    public List<ProductsVO> findAll(Integer pd_value) {
        return repository.findAll(pd_value);
    }

    public List<ColorsDTO> findColors() {
        return repository.findColors();
    }

    public List<SizesDTO> findSizes() {
        return repository.findSizes();
    }

    public Long findPd_idx(CartInsertDTO search) {
        return repository.findPd_idx(search);
    }

    public List<MainProductsDTO> searchByName(String keyword) {
        return repository.searchByName(keyword);
    }

    public List<MainProductsDTO> bestProducts(Map<String, Object> params) {
        return repository.bestProducts(params);
    }

    public int countBestProducts(Map<String, Object> params) {
        return repository.countBestProducts(params);
    }

    public List<SgPdDTO> findSuggest(Map<String, Object> params) {
        return repository.findSuggest(params);
    }

    public List<MainProductsDTO> categoryProducts(Map<String, Object> params) {
        return repository.categoryProducts(params);
    }

    public int countCategoryProducts(Map<String, Object> params) {
        return repository.countCategoryProducts(params);
    }

    public List<MainProductsDTO> topCategoryProducts(Map<String, Object> params) {
        return repository.topCategoryProducts(params);
    }

    public int topCountCategoryProducts(Map<String, Object> params) {
        return repository.topCountCategoryProducts(params);
    }

    public List<MainProductsDTO> findByPd_nm(String pd_nm) {
        return repository.findByPd_nm(pd_nm);
    }
}
