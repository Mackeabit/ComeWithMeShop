package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.mapper.AdminMapper;
import mackeabit.shop.vo.AdminVO;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final AdminMapper adminMapper;

    public Optional<AdminVO> findAdminById(String id) {
        return adminMapper.findAdminById(id);
    }
}
