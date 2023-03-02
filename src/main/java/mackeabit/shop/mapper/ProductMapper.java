package mackeabit.shop.mapper;

import mackeabit.shop.dto.*;
import mackeabit.shop.vo.ProductsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    void save(ProductsVO productsVO);

    Optional<ProductsVO> findByName(String pd_nm);

    ProductsVO findByIdx(Long pd_idx);

    List<ProductsVO> findAll(Integer pd_value);

    List<SizesDTO> findSizes();

    List<ColorsDTO> findColors();

    Long findPd_idx(CartInsertDTO search);

    List<MainProductsDTO> searchByName(String keyword);

    List<MainProductsDTO> bestProducts(Map<String, Object> params);

    int countBestProducts(Map<String, Object> params);

    List<SgPdDTO> findSuggest(Map<String, Object> params);

    List<MainProductsDTO> categoryProducts(Map<String, Object> params);

    int countCategoryProducts(Map<String, Object> params);

    List<MainProductsDTO> topCategoryProducts(Map<String, Object> params);

    int topCountCategoryProducts(Map<String, Object> params);

    List<MainProductsDTO> findByPd_nm(String pd_nm);

    List<MainProductsDTO> findRecommendProducts(Integer category_code);
}
