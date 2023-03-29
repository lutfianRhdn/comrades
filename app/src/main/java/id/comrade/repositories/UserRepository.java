package id.comrade.repositories;

import id.comrade.model.RootResponse;
import id.comrade.model.User;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.UserApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserRepository {
    private UserApi userApi = RetrofitService.getInstance().create(UserApi.class);

    private static UserRepository sInstance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UserRepository();
        }

        return sInstance;
    }

    public Single<Response<RootResponse<User>>> login(User user) {
        return userApi.login(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Response<RootResponse<User>>> register(User user) {
        return userApi.register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Single<Response<RootResponse<User>>> editProfile(User user) {
        return userApi.edit(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
