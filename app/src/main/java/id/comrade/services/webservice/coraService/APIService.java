package id.comrade.services.webservice.coraService;

import id.comrade.model.cora.Post;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    //    @POST("/posts")
//    @FormUrlEncoded
//    Call<Post> savePost(@Body Post post);
    @POST("/chatbot")
    @FormUrlEncoded
    Call<Post> savePost(@Field("text") String text,
                        @Field("userid") long userId);
}
