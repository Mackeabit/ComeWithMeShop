package mackeabit.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.Repository.AdminRepository;
import mackeabit.shop.dto.*;
import mackeabit.shop.security256.SHA256;
import mackeabit.shop.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository repository;
    private final PaymentService paymentService;

    private final SHA256 sha256;


    public String adminCheck(AdminCheckDTO adminCheckDTO) throws NoSuchAlgorithmException {

        //관리자 비밀번호 암호화 비교
        String collect = sha256.encrypt(adminCheckDTO.getPwd() + sha256.getSALT());

        Optional<AdminVO> findAdmin = repository.findAdminById(adminCheckDTO.getId());

        String messages = "no_id";

        if (!findAdmin.isEmpty()) {
            AdminVO adminVO = findAdmin.get();
            messages = "no_pwd";
            log.info("findAdmin = {}", adminVO);
            if (adminVO.getPwd().equals(collect)) {
                //비밀번호까지 체크 완료
                log.info("success Admin Check");
                messages = "Y";
            }
        }
        return messages;
    }

    public AdminVO findAdminById(String id) {

        Optional<AdminVO> adminById = repository.findAdminById(id);

        if (!adminById.isEmpty()) {
            return adminById.get();
        }

        return null;
    }

    public Annual_SalesVO findAnnualSales(int year, int check) {

        if (check == 0) {
            //올해 매출 구하기
            Annual_SalesVO annual_salesVO = repository.findNowYearSales(year);
            return annual_salesVO;
        }

        // 작년 매출 구하기
        Annual_SalesVO annual_salesVO = repository.findLastYearSales(year);

        return annual_salesVO;
    }

    public Integer findMemberTodaySignUp(String todayStr) {
        return repository.findMemberTodaySignUp(todayStr);
    }

    public Integer findMemberYesterdaySignUp(String yesterdayStr) {
        return repository.findMemberYesterdaySignUp(yesterdayStr);
    }

    public Integer monthPrice(Map<String , Integer> yearMonth) {
        return repository.monthPrice(yearMonth);
    }

    public Integer countVisit(String day) {
        return repository.countVisit(day);
    }

    public List<SignCountWeekDTO> signCountWeek(Map<String , Date> selectDate) {
        return repository.signCountWeek(selectDate);
    }

    public List<SignCountWeekDTO> visitCountWeek(Map<String , Date> selectDate) {
        return repository.visitCountWeek(selectDate);
    }

    public List<SalesVO> weekSales(Map<String , Date> selectDate) {
        return repository.weekSales(selectDate);
    }

    public List<AdminMainPayListDTO> findAdminMainPayList() {
        return repository.findAdminMainPayList();
    }

    public List<AdminMainOrderListDTO> findAdminMainOrderList() {
        return repository.findAdminMainOrderList();
    }

    public Integer lastMonthPrice(Map<String, Integer> yearMonth) {
        return repository.lastMonthPrice(yearMonth);
    }

    public List<AdminVO> findAll() {
        return repository.findAll();
    }

    public List<MembersVO> findAllMembers() {
        return repository.findAllMembers();
    }

    public MembersAllInfoDTO findMemberAllInfo(Long member_idx) {
        return repository.findMemberAllInfo(member_idx);
    }

    public List<Members_logVO> findMemberLog(Long member_idx) {
        return repository.findMemberLog(member_idx);
    }

    @Transactional
    public String updateMembers(MembersAllInfoDTO membersAllInfoDTO) {

        String data = "N";

        int res = repository.updateMembers(membersAllInfoDTO);

        res += repository.updateMemberDetails(membersAllInfoDTO);


        log.info("res = {}", res);
        if (res > 1) {
            data = "Y";
        }

        return data;
    }

    public MembersVO findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String delMemberByMember_idx(Long member_idx) {

        String data = "N";

        int res = repository.delMemberByMember_idx(member_idx);

        if (res > 0) {
            log.info("삭제된 멤버 idx = {}, 삭제 횟수 = {}", member_idx, res);
            data = "Y";
        }

        return data;
    }

    public List<CategorysVO> findCategory() {
        return repository.findCategory();
    }

    //일일 매출액 계산
    public void daySalesUpdate(SalesVO salesVO) {
        repository.daySalesUpdate(salesVO);
    }

    public Long daySaleTotal(Map<String, Object> params) {
        return repository.daySaleTotal(params);
    }

    public Long findAvrPrice(String twoAgo) {
        return repository.findAvrPrice(twoAgo);
    }

    public List<SalesVO> selectByMonth(Map<String, Object> monthlyParams) {
        return repository.selectByMonth(monthlyParams);
    }

    public void monthlySaleInsert(Monthly_SalesVO monthlySales) {
        repository.monthlySaleInsert(monthlySales);
    }

    public List<Monthly_SalesVO> selectMonthlySalesByYear(int year) {
        return repository.selectMonthlySalesByYear(year);
    }

    public void insertAnnualSales(Annual_SalesVO annualSales) {
        repository.insertAnnualSales(annualSales);
    }

    public List<MainProductsDTO> findProductsList() {
        return repository.findProductsList();
    }

    public MainProductsDTO findProductByIdx(Long pd_idx) {
        return repository.findProductByIdx(pd_idx);
    }

    @Transactional
    public String updateProduct(ProductsVO productsVO) {

        String data = "N";

        //상품 업데이트
        int res = repository.updateProduct(productsVO);

        //상품 로그 업데이트
        res += repository.updateProductLog(productsVO.getPd_idx());

        if (res > 1) {
            data = "Y";
        }

        return data;
    }

    public List<AdminAllOrdersDTO> findOrderListAll() {
        return repository.findOrderListAll();
    }

    public List<AdminAllPaymentsDTO> findPaymentsListAll() {
        return repository.findPaymentsListAll();
    }

    @Transactional
    public String paymentsCancel(PaymentsVO paymentsVO) throws IOException {

        String data = "N";

        int res = repository.updatePaymentsStatus(paymentsVO);

        if (res > 0) {
            //결제 DB 취소 처리 이후, 결제내역을 찾아와서 아임포트 취소 요청하기

            PaymentsVO findPayments = repository.findPaymentsByIdx(paymentsVO.getPay_idx());

            String token = paymentService.getToken();
            paymentService.payMentCancle(token, findPayments.getPay_code(), findPayments.getTotal_price(), "결제 취소 요청");

            data = "Y";
        }


        return data;
    }

    public AdminReviewDTO findReviewByOrderIdx(Long order_idx) {
        return repository.findReviewByOrderIdx(order_idx);
    }

    public PhotosVO findReviewPhoto(Long review_idx) {
        return repository.findReviewPhoto(review_idx);
    }

    public String delReview(Long review_idx) {

        String data = "N";

        //리뷰 지우고, orders 리뷰체크 바꾸기
        ReviewsVO reviewsVO = repository.findReviewByIdx(review_idx);

        int res = repository.delReview(review_idx);
        res += repository.changeReviewCheck(reviewsVO.getOrder_idx());

        if (res > 1) {
            data = "Y";
        }

        return data;
    }
}
