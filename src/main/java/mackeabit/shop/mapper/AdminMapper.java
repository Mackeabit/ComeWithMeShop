package mackeabit.shop.mapper;

import mackeabit.shop.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;


@Mapper
public interface AdminMapper {

    Optional<AdminVO> findAdminById(String id);
}
