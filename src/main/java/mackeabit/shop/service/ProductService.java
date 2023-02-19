package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.ProductRepository;
import mackeabit.shop.dto.CartInsertDTO;
import mackeabit.shop.dto.ColorsDTO;
import mackeabit.shop.dto.MainProductsDTO;
import mackeabit.shop.dto.SizesDTO;
import mackeabit.shop.vo.ProductsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public ProductsVO findOne(Long pd_idx) {
        return repository.findByIdx(pd_idx);
    }

    public List<ProductsVO> findAll(Integer pd_value) {
        return repository.findAll(pd_value);
    }

    public List<ColorsDTO> findColors() {
        return repository.findColors();
    }

    public List<SizesDTO> findSizes() {
        return repository.findSizes();
    }

    public Long findPd_idx(CartInsertDTO search) {
        return repository.findPd_idx(search);
    }

    public List<MainProductsDTO> searchByName(String keyword) {
        return repository.searchByName(keyword);
    }
}
