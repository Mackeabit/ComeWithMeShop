package mackeabit.shop.Repository;

import mackeabit.shop.vo.ProductsVO;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(ProductsVO productsVO);

    Optional<ProductsVO> findByName(String pd_nm);

    ProductsVO findByIdx(Long pd_idx);

    List<ProductsVO> findAll();

}