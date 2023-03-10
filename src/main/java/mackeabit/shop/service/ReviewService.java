package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.OrderRepository;
import mackeabit.shop.Repository.PayRepository;
import mackeabit.shop.Repository.SubRepository;
import mackeabit.shop.dto.MainCartDTO;
import mackeabit.shop.dto.PopUpWriteReviewDTO;
import mackeabit.shop.vo.MembersVO;
import mackeabit.shop.vo.OrdersVO;
import mackeabit.shop.vo.PaymentsVO;
import mackeabit.shop.vo.ReviewsVO;
import mackeabit.shop.web.SessionConst;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final OrderRepository orderRepository;
    private final SubRepository subRepository;


    public List<PopUpWriteReviewDTO> popUpReview(PopUpWriteReviewDTO popUpWriteReviewDTO) {
        return orderRepository.popUpReview(popUpWriteReviewDTO);
    }

    public Long insertReview(ReviewsVO reviewsVO) {
        return subRepository.insertReview(reviewsVO);
    }

    public List<ReviewsVO> findReviewsMain() {
        return subRepository.findReviewsMain();
    }
}
