package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.mapper.CategoryMapper;
import mackeabit.shop.vo.CategorysVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    /**
     *  카테고리 추가
     */
    @Override
    public int save(CategorysVO categorysVO) {
        int res = categoryMapper.save(categorysVO);
        return res;
    }

    /**
     *  전체 카테고리 찾기
     */
    @Override
    public List<CategorysVO> findAll() {
        List<CategorysVO> findCate = categoryMapper.findAll();
        return findCate;
    }

    /**
     *  특정 카테고리 찾기(분류별)
     * [n = 인덱스값]
     * [1. 의류] - 대분류
     *      [2. 상의] - 중분류
     *          [6. 여성 상의] - 소분류
     *  		[7. 남성 상의]
     *      [3. 하의]
     *  		[8. 여성 하의]
     * 	    	[9. 남성 하의]
     *      [4. 신발]
     *      [10. 원피스]
     *      [11. 드레스세트]
     * [5. 악세사리]
     *   	[12. 모자]
     *  	[13. 양말]
     *      [14. 주얼리]
     * [16. etc]
     *      [15. 가방]
     *
     *      ex. category_ref = 2 로 검색시(상의)
     *      여성 상의, 남성 상의 카테고리가 나옴
     *      
     *      select * from categorys where category_ref is null;
     *      null 값 조회시 최상위 카테고리만 조회
     *      
     */
    @Override
    public List<CategorysVO> specificCate(Integer category_ref) {
        List<CategorysVO> findCate = categoryMapper.specificCate(category_ref);
        return findCate;
    }


    @Override
    public List<CategorysVO> findByName(String category_name) {
        List<CategorysVO> findCate = categoryMapper.findByName(category_name);
        return findCate;
    }


}
