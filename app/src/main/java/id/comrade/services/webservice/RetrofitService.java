package id.comrade.services.webservice;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class    RetrofitService {
    private static Retrofit sInstance;

    private static final String BASE_URL = "https://comrades-api-380815.et.r.appspot.com";
    public static final String PIC_POSTING = BASE_URL + "/pic_posting/";
    public static final String PIC_EVENT = BASE_URL + "/pic_event/";
    public static final String PIC_USER = BASE_URL + "/pic_user/";

    private RetrofitService() {
    }

    public static Retrofit getInstance() {
        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return sInstance;
    }
}
