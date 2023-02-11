package mackeabit.shop.Repository;

import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.vo.CartsVO;
import mackeabit.shop.vo.ProductsVO;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    int save(CartsVO cartsVO);

    List<CartsVO> findAll(Long member_idx);

    int delOne(Long cart_idx);

    int delAll(Long member_idx);

    List<MainCartDTO> findMemberCart(Long member_idx);

}
