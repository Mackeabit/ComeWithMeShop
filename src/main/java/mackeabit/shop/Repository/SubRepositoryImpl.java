package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.mapper.SuBMapper;
import mackeabit.shop.vo.Photos_toMainVO;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
