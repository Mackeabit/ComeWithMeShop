package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.CartRepository;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.vo.CartsVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

}

