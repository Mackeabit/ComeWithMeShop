package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.PopUpWriteQnaDTO;
import mackeabit.shop.mapper.SuBMapper;
import mackeabit.shop.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SubRepositoryImpl implements SubRepository{

    private final SuBMapper suBMapper;

    public List<Photos_toMainVO> findAll() {
        List<Photos_toMainVO> findList = suBMapper.findAll();
        return findList;
    }

    public List<Photos_toMainVO> findThings(int to_use) {
        return suBMapper.findThings(to_use);
    }

    @Override
    public List<MainProductsDTO> mainPageProducts(Integer pd_value) {
        return suBMapper.mainPageProducts(pd_value);
    }

    @Override
    public List<MainProductsDTO> sortAllProductsSizes() {
        return suBMapper.sortAllProductsSizes();
    }


    @Override
    public List<MainProductsDTO> mainPageProductsPaged(Map<String, Object> params) {
        return suBMapper.mainPageProductsPaged(params);
    }

    @Override
    public int countMainPageProducts() {
        return suBMapper.countMainPageProducts();
    }

    @Override
    public CategorysVO findCategories(int category_code) {
        return suBMapper.findCategories(category_code);
    }

    @Override
    public int cancelRequestPay(Long pay_idx) {
        return suBMapper.cancelRequestPay(pay_idx);
    }

    @Override
    public Long insertReview(ReviewsVO reviewsVO) {
        suBMapper.insertReview(reviewsVO);
        return reviewsVO.getReview_idx();
    }

    @Override
    public List<PopUpWriteQnaDTO> popUpQna(String pd_nm) {
        return suBMapper.popUpQna(pd_nm);
    }

    @Override
    public int insertQna(NoticesVO noticesVO) {
        return suBMapper.insertQna(noticesVO);
    }

    @Override
    public List<ReviewsVO> findReviewsMain() {
        return suBMapper.findReviewsMain();
    }

    public List<CouponsVO> findMainCoupon() {
        return suBMapper.findMainCoupon();
    }
}
