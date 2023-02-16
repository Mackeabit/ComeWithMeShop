package mackeabit.shop.mapper;

import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.vo.Photos_toMainVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuBMapper {

    List<Photos_toMainVO> findAll();

    List<Photos_toMainVO> findThings(int to_use);

    List<MainProductsDTO> mainPageProducts(Integer pd_value);

    List<MainProductsDTO> sortAllProductsSizes();

}
