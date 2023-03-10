package mackeabit.shop.Repository;

import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.PopUpWriteQnaDTO;
import mackeabit.shop.vo.CategorysVO;
import mackeabit.shop.vo.NoticesVO;
import mackeabit.shop.vo.Photos_toMainVO;
import mackeabit.shop.vo.ReviewsVO;

import java.util.List;
import java.util.Map;

public interface SubRepository {

    public List<Photos_toMainVO> findAll();

    public List<Photos_toMainVO> findThings(int to_use);

    public List<MainProductsDTO> mainPageProducts(Integer pd_value);

    List<MainProductsDTO> sortAllProductsSizes();


    List<MainProductsDTO> mainPageProductsPaged(Map<String, Object> params);

    int countMainPageProducts();

    CategorysVO findCategories(int category_code);

    int cancelRequestPay(Long pay_idx);

    Long insertReview(ReviewsVO reviewsVO);

    List<PopUpWriteQnaDTO> popUpQna(String pd_nm);

    int insertQna(NoticesVO noticesVO);
}
