package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.SubRepositoryImpl;
import mackeabit.shop.vo.Photos_toMainVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubService {

    private final SubRepositoryImpl subRepository;

    public List<Photos_toMainVO> findAll() {

        return subRepository.findAll();
    }

    public List<Photos_toMainVO> findThings(int to_use) {
        return subRepository.findThings(to_use);
    }

}
