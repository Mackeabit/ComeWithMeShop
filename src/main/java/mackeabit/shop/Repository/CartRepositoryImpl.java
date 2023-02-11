package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.mapper.CartMapper;
import mackeabit.shop.vo.CartsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartMapper cartMapper;

    @Override
    public int save(CartsVO cartsVO) {
        int res = cartMapper.save(cartsVO);
        return res;
    }

    @Override
    public List<CartsVO> findAll(Long member_idx) {
        List<CartsVO> cartsList = cartMapper.findAll(member_idx);
        return cartsList;
    }

    @Override
    public int delOne(Long cart_idx) {

        int res = cartMapper.delOne(cart_idx);

        return res;
    }

    @Override
    public int delAll(Long member_idx) {
        int res = cartMapper.delAll(member_idx);
        return res;
    }

    @Override
    public List<MainCartDTO> findMemberCart(Long member_idx) {
        return cartMapper.findMemberCart(member_idx);
    }
}
