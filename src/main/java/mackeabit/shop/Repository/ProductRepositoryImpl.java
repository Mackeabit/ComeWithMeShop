package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.mapper.ProductMapper;
import mackeabit.shop.vo.ProductsVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

//    @RequiredArgsConstructor 으로 autowired 자동 주입
//    public ProductRepositoryImpl(MemberMapper memberMapper) {
//        this.memberMapper = memberMapper;
//    }

    private final ProductMapper productMapper;

    @Override
    public void save(ProductsVO productsVO) {
        productMapper.save(productsVO);
    }

    @Override
    public Optional<ProductsVO> findByName(String pd_nm) {
        return Optional.empty();
    }

    @Override
    public ProductsVO findByIdx(Long pd_idx) {
        return null;
    }

    @Override
    public List<ProductsVO> findAll(Integer pd_idx) {
        return productMapper.findAll(pd_idx);
    }

    @Override
    public List<ColorsDTO> findColors() {
        return productMapper.findColors();
    }

    @Override
    public List<SizesDTO> findSizes() {
        return productMapper.findSizes();
    }
}
