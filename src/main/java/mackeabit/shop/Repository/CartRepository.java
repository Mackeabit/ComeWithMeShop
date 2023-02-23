package mackeabit.shop.Repository;

import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.vo.CartsVO;

import java.util.List;
import java.util.Map;

public interface CartRepository {

    int save(CartsVO cartsVO);

    List<CartsVO> findAll(Long member_idx);

    int delOne(Long cart_idx);

    int delAll(Long member_idx);

    List<MainCartDTO> findMemberCart(Long member_idx);

    Integer selectOne(CartsVO cartsVO);

    int updateCart(Map<String, Object> params);

    int delCart(Map<String, Object> params);
}
