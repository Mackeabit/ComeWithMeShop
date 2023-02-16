package mackeabit.shop.mapper;

import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.vo.ProductsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    void save(ProductsVO productsVO);

    Optional<ProductsVO> findByName(String pd_nm);

    ProductsVO findByIdx(Long pd_idx);

    List<ProductsVO> findAll(Integer pd_value);

    List<SizesDTO> findSizes();

    List<ColorsDTO> findColors();
}
