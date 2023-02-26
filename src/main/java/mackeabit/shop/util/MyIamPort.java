package mackeabit.shop.util;

import com.siot.IamportRestClient.IamportClient;

public class MyIamPort {

    IamportClient client;

    private IamportClient getClient() {
        String test_api_key = "5978210787555892";
        String test_api_secret = "9e75ulp4f9Wwj0i8MSHlKFA9PCTcuMYE15Kvr9AHixeCxwKkpsFa7fkWSd9m0711dLxEV7leEAQc6Bxv";

        return new IamportClient(test_api_key, test_api_secret);
    }

}
