package mackeabit.shop.mapper;

import mackeabit.shop.vo.CartsVO;
import mackeabit.shop.vo.MembersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {

    //장바구니에 추가하기 클릭시
    int save(CartsVO cartsVO);

    //장바구니 화면, 메인페이지(장바구니 미니)에 사용
    List<CartsVO> findAll(Long member_idx);

    //장바구니 상품 하나 삭제
    int delOne(Long cart_idx);

    //장바구니 상품 전체삭제
    int delAll(Long member_idx);
}
