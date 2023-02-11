package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.ProductRepository;
import mackeabit.shop.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductsVO> findAll() {
        return repository.findAll();
    }

    public ProductsVO findOne(Long pd_idx) {
        return repository.findByIdx(pd_idx);
    }



}
