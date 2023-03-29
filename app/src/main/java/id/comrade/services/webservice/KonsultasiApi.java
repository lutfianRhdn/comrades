package id.comrade.services.webservice;

import java.util.List;
import java.util.Map;

import id.comrade.model.Konsultasi;
import id.comrade.model.ListKonsultasiResponse;
import id.comrade.model.RootResponse;
import id.comrade.model.User;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KonsultasiApi {
    @POST("/v2/konsultasi/sendMessage")
    Single<Response<RootResponse<Konsultasi>>> sendChat(@Body Map<String, String> body);

    @GET("/v2/konsultasi/sahabatodha/{id}")
    Single<Response<RootResponse<ListKonsultasiResponse>>> getListChatSahabat(@Path("id") int id);

    @GET("/v2/konsultasi/sahabatodha/{id}")
    Single<Response<RootResponse<ListKonsultasiResponse>>> getListChatUser(@Path("id") int id);

    @POST("/v2/konsultasi/user")
    Single<Response<RootResponse<KonsultasiResponse>>> getChatKonsultasi(@Body Map<String, String> body);

    class KonsultasiResponse {
        private User user1;
        private User user2;
        private List<Konsultasi> listChat;

        public User getUser1() {
            return user1;
        }

        public void setUser1(User user1) {
            this.user1 = user1;
        }

        public User getUser2() {
            return user2;
        }

        public void setUser2(User user2) {
            this.user2 = user2;
        }

        public List<Konsultasi> getListChat() {
            return listChat;
        }

        public void setListChat(List<Konsultasi> listChat) {
            this.listChat = listChat;
        }
    }
}
