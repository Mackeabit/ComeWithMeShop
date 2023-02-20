package mackeabit.shop.Repository;

import mackeabit.shop.dto.CartInsertDTO;
import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.vo.ProductsVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {

    void save(ProductsVO productsVO);

    Optional<ProductsVO> findByName(String pd_nm);

    ProductsVO findByIdx(Long pd_idx);

//    List<ProductsVO> findAll();

    List<ProductsVO> findAll(Integer pd_value);

    List<ColorsDTO> findColors();

    List<SizesDTO> findSizes();

    Long findPd_idx(CartInsertDTO search);

    List<MainProductsDTO> searchByName(String keyword);

    List<MainProductsDTO> bestProducts(Map<String, Object> params);

    int countBestProducts();
}
