package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.OrderRepository;
import mackeabit.shop.Repository.SubRepository;
import mackeabit.shop.dto.PopUpWriteQnaDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.vo.NoticesVO;
import mackeabit.shop.vo.ReviewsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {

    private final SubRepository subRepository;

    public List<PopUpWriteQnaDTO> popUpQna(String pd_nm) {
        return subRepository.popUpQna(pd_nm);
    }

    public String insertQna(NoticesVO noticesVO) {

        String data = "N";

        int res = subRepository.insertQna(noticesVO);

        if (res > 0) {
            data = "Y";
        }

        return data;
    }
}
