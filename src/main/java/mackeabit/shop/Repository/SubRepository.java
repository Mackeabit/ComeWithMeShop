package mackeabit.shop.Repository;

import mackeabit.shop.vo.Photos_toMainVO;

import java.util.List;

public interface SubRepository {

    public List<Photos_toMainVO> findAll();

    public List<Photos_toMainVO> findThings(int to_use);

}
