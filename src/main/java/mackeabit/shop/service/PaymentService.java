package mackeabit.shop.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface PaymentService {
    String getToken() throws IOException;

    int paymentInfo(String imp_uid, String access_token) throws MalformedURLException, IOException;

    public void payMentCancle(String access_token, String imp_uid, int amount, String reason) throws IOException;
}
