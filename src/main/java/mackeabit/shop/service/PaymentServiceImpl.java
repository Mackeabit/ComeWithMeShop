package mackeabit.shop.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

    @Value("${imp_key}")
    private String impKey;

    @Value("${imp_secret}")
    private String impSecret;

    @Getter
    @ToString
    private class Response {
        private PaymentInfo response;
    }

    @Getter
    @ToString
    private class PaymentInfo {
        private int amount;
    }


    public String getToken() throws IOException {

        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/users/getToken");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        JsonObject json = new JsonObject();

        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        Gson gson = new Gson();

        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

        log.info("getToken response = {}", response);

        String token = gson.fromJson(response, Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    public int paymentInfo(String imp_uid, String access_token) throws IOException {
        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", access_token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        Gson gson = new Gson();


        Response response = gson.fromJson(br.readLine(), Response.class);

        br.close();
        conn.disconnect();

        return response.getResponse().getAmount();
    }



    public void payMentCancle(String access_token, String imp_uid, int amount, String reason) throws IOException {

        log.info("payMentCancle 결제 취소");
        log.info("access_token = {}", access_token);
        log.info("imp_uid = {}", imp_uid);


        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("reason", reason);
        json.addProperty("imp_uid", imp_uid);
        json.addProperty("amount", amount);
        json.addProperty("checksum", amount);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        br.close();
        conn.disconnect();

    }
}
