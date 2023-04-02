package id.comrade.services.webservice;


import java.util.List;

import id.comrade.model.ChatBot;
import id.comrade.model.Event;
import id.comrade.model.Obat;
import id.comrade.model.RootResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatBotApi {

    @POST("/v2/chatbot")
    Call<ChatBot> sendChatMessage( @Body ChatBot text);
}
