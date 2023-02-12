package mackeabit.shop.Repository;

import mackeabit.shop.vo.CategorysVO;

import java.util.List;

public interface CategoryRepository {

    int save(CategorysVO categorysVO);

    List<CategorysVO> findAll();

    List<CategorysVO> specificCate(Integer category_ref);

    List<CategorysVO> findByName(String category_name);

}
