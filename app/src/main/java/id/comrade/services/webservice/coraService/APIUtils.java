package id.comrade.services.webservice.coraService;

public class APIUtils {

    private APIUtils() {}

    public static final String BASE_URL = "https://comrades-api-380815.et.r.appspot.com";

    public static APIService getAPIService() {

        return RetrofitClient.getInstance(BASE_URL).create(APIService.class);
    }
}
