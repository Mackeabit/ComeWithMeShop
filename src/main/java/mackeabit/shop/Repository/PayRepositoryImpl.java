package mackeabit.shop.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.mapper.PaymentMapper;
import mackeabit.shop.vo.PaymentsVO;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PayRepositoryImpl implements PayRepository {

    private final PaymentMapper paymentMapper;

    @Override
    public int save(PaymentsVO paymentsVO) {
        return paymentMapper.save(paymentsVO);
    }
}
