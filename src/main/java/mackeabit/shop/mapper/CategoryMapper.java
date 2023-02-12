package mackeabit.shop.mapper;

import mackeabit.shop.vo.CategorysVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    int save(CategorysVO categorysVO);

    List<CategorysVO> findAll();

    List<CategorysVO> specificCate(Integer category_ref);

    List<CategorysVO> findByName(String category_name);

}
