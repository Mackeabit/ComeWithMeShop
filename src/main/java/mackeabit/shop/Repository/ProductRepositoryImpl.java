package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.*;
import mackeabit.shop.mapper.ProductMapper;
import mackeabit.shop.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

//    @RequiredArgsConstructor 으로 autowired 자동 주입
//    public ProductRepositoryImpl(MemberMapper memberMapper) {
//        this.memberMapper = memberMapper;
//    }

    private final ProductMapper productMapper;

    @Override
    public void save(ProductsVO productsVO) {
        productMapper.save(productsVO);
    }

    @Override
    public Optional<ProductsVO> findByName(String pd_nm) {
        return Optional.empty();
    }

    @Override
    public ProductsVO findByIdx(Long pd_idx) {
        return null;
    }

    @Override
    public List<ProductsVO> findAll(Integer pd_idx) {
        return productMapper.findAll(pd_idx);
    }

    @Override
    public List<ColorsDTO> findColors() {
        return productMapper.findColors();
    }

    @Override
    public List<SizesDTO> findSizes() {
        return productMapper.findSizes();
    }

    @Override
    public Long findPd_idx(CartInsertDTO search) {
        return productMapper.findPd_idx(search);
    }

    @Override
    public List<MainProductsDTO> searchByName(String keyword) {
        return productMapper.searchByName(keyword);
    }

    @Override
    public List<MainProductsDTO> bestProducts(Map<String, Object> params) {
        return productMapper.bestProducts(params);
    }

    @Override
    public int countBestProducts(Map<String, Object> params) {
        return productMapper.countBestProducts(params);
    }

    @Override
    public List<SgPdDTO> findSuggest(Map<String, Object> params) {
        return productMapper.findSuggest(params);
    }

    @Override
    public List<MainProductsDTO> categoryProducts(Map<String, Object> params) {
        return productMapper.categoryProducts(params);
    }

    @Override
    public int countCategoryProducts(Map<String, Object> params) {
        return productMapper.countCategoryProducts(params);
    }

    @Override
    public List<MainProductsDTO> topCategoryProducts(Map<String, Object> params) {
        return productMapper.topCategoryProducts(params);
    }

    @Override
    public int topCountCategoryProducts(Map<String, Object> params) {
        return productMapper.topCountCategoryProducts(params);
    }

    @Override
    public List<MainProductsDTO> findByPd_nm(String pd_nm) {
        return productMapper.findByPd_nm(pd_nm);
    }

    @Override
    public List<MainProductsDTO> findRecommendProducts(Integer category_code) {
        return productMapper.findRecommendProducts(category_code);
    }

    @Override
    public List<ReviewsVO> findReviewsByPd_nm(Map<String, Object> pd_nm) {
        return productMapper.findReviewsByPd_nm(pd_nm);
    }

    @Override
    public void updateProductStars(String pd_nm) {
        productMapper.updateProductStars(pd_nm);
    }

    @Override
    public List<NoticesVO> findQnaByPd_nm(Map<String, Object> pd_nm) {
        return productMapper.findQnaByPd_nm(pd_nm);
    }

    @Override
    public int countReviews(String pd_nm) {
        return productMapper.countReviews(pd_nm);
    }

    @Override
    public int countQnas(String pd_nm) {
        return productMapper.countQnas(pd_nm);
    }

    @Override
    public Products_starsVO findStarsByPd_nm(String pd_nm) {
        return productMapper.findStarsByPd_nm(pd_nm);
    }

    @Override
    public Long insertProduct(ProductsVO productsVO) {
        productMapper.insertProduct(productsVO);
        return productsVO.getPd_idx();
    }

    @Override
    public void insertProductLogByPd_idx(Products_logVO products_logVO) {
        productMapper.insertProductLogByPd_idx(products_logVO);
    }

    @Override
    public void insertProductImg(Product_imgVO product_imgVO) {
        productMapper.insertProductImg(product_imgVO);
    }

    @Override
    public void insertProductStar(Products_starsVO products_starsVO) {
        productMapper.insertProductStar(products_starsVO);
    }


}
