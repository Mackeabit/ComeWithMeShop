package mackeabit.shop.mapper;

import mackeabit.shop.vo.PhotosVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoMapper {
    void saveReviewPhoto(PhotosVO photosVO);
}
