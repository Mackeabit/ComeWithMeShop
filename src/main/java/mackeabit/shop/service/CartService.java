package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.CartRepository;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.vo.CartsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository repository;


    public List<CartsVO> findAll(Long member_idx) {

        return repository.findAll(member_idx);
    }

    public List<MainCartDTO> findMemberCart(Long member_idx) {
        return repository.findMemberCart(member_idx);
    }

    //장바구니 추가
    public String insertCart(CartsVO cartsVO) {
        String data = "N";

        if (cartsVO.getMember_idx() == null) {
            data = "NN";
            return data;
        }

        int save = repository.save(cartsVO);
        if (save > 0) {
            data = "Y";
        }
        return data;
    }

    public Integer selectOne(CartsVO cartsVO) {
        return repository.selectOne(cartsVO);
    }

    public String updateCart(Map<String, Object> params) {

        String data = "N";

        int res = repository.updateCart(params);

        if (res > 0) {
            data = "Y";
        }
        return data;
    }

    public String delCart(Map<String, Object> params) {

        String data = "N";

        int res = repository.delCart(params);

        if (res > 0) {
            data = "Y";
        }

        return data;
    }

    public int delAll(Long member_idx) {
        return repository.delAll(member_idx);
    }
}

