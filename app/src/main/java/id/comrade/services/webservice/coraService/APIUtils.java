package id.comrade.services.webservice.coraService;

public class APIUtils {

    private APIUtils() {}

    public static final String BASE_URL = "https://corachatbot.herokuapp.com";

    public static APIService getAPIService() {

        return RetrofitClient.getInstance(BASE_URL).create(APIService.class);
    }
}
