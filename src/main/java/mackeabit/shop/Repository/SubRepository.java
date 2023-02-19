package mackeabit.shop.Repository;

import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.vo.Photos_toMainVO;

import java.util.List;
import java.util.Map;

public interface SubRepository {

    public List<Photos_toMainVO> findAll();

    public List<Photos_toMainVO> findThings(int to_use);

    public List<MainProductsDTO> mainPageProducts(Integer pd_value);

    List<MainProductsDTO> sortAllProductsSizes();


    List<MainProductsDTO> mainPageProductsPaged(Map<String, Object> params);

    int countMainPageProducts();
}
