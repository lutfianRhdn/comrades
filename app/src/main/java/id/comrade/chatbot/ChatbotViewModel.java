package id.comrade.chatbot;

import android.app.Application;
import android.util.Log;

import java.util.List;

import id.comrade.R;
import id.comrade.base.BaseViewModel;
import id.comrade.chatbot.response.ResponseFactory;
import id.comrade.model.Chat;
import id.comrade.model.ChatArticle;
import id.comrade.model.ChatBot;
import id.comrade.model.ChatEvent;
import id.comrade.model.ChatNews;
import id.comrade.model.ChatSharingFriend;
import id.comrade.model.ChatTweet;
import id.comrade.model.cora.Post;
import id.comrade.repositories.ArticleCache;
import id.comrade.repositories.EventCache;
import id.comrade.repositories.NewsCache;
import id.comrade.repositories.SahabatOdhaCache;
import id.comrade.repositories.TweetCache;
import id.comrade.services.webservice.ChatBotApi;
import id.comrade.services.webservice.RetrofitService;
import id.comrade.services.webservice.coraService.APIService;
import id.comrade.services.webservice.coraService.APIUtils;
import id.comrade.utils.DateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotViewModel extends BaseViewModel<ChatbotViewState> {

    private NewsCache newsCache = NewsCache.getInstance();
    private SahabatOdhaCache friendToShareCache = SahabatOdhaCache.getInstance();
    private EventCache eventCache = EventCache.getInstance();
    private ArticleCache articleCache = ArticleCache.getInstance();
    private TweetCache tweetCache = TweetCache.getInstance();


    private APIService mAPIService;

    private ResponseFactory responseFactory;

    public ChatbotViewModel(Application app) {
        super(app);
        responseFactory = new ResponseFactory(app);

        ChatbotViewState viewState = new ChatbotViewState();
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void setChats(List<Chat> chats) {
        ChatbotViewState viewState = getViewState().getValue();
        viewState.setChats(chats);
        viewState.setChat(null);
        getViewState().setValue(viewState);
    }

    @SuppressWarnings("ConstantConditions")
    public void retrieveResponse(String message, int userid) {
        String TAG  = "cek";
        Log.d(TAG, message);
        if (message.toLowerCase().indexOf("berita")>0){
            message = "berita";
        } else if (message.toLowerCase().indexOf("konsultasi")>0){
            message = "konsultasi";
        } else if (message.toLowerCase().indexOf("event")>0){
            message = "event";
        }
        else if (message.toLowerCase().indexOf("tweet dukungan")>0){
            message = "tweet dukungan";
        }

        Chat chat = responseFactory.get(message.toLowerCase());
        ChatbotViewState viewState = getViewState().getValue();
        viewState.setChats(null);
        mAPIService = APIUtils.getAPIService();
//        Log.d(TAG, c);

        Log.d(TAG, "retrieveResponse: "+ chat );
        if (chat instanceof ChatNews) {
            Log.d(TAG, "berita ");

            ChatNews chatNews = (ChatNews) chat;
            addDisposable(newsCache.getCache()
                    .subscribe(news -> {
                        chatNews.setNews(news);
                        viewState.setError(null);
                        viewState.setChat(chatNews);
                        getViewState().setValue(viewState);
                    }));
        } else if (chat instanceof ChatSharingFriend) {
            Log.d(TAG, "sharing");
            ChatSharingFriend chatSharingFriend = (ChatSharingFriend) chat;
            addDisposable(friendToShareCache.getCache()
                    .subscribe(friendToShares -> {
                        chatSharingFriend.setFriends(friendToShares);
                        viewState.setError(null);
                        viewState.setChat(chat);
                        getViewState().setValue(viewState);
                    }));
        } else if(chat instanceof ChatEvent){
            Log.d(TAG, "event");

                ChatEvent chatEvent = (ChatEvent) chat;

                addDisposable(eventCache.getCache()
                        .subscribe(event ->{
                            // Inisialisasi Model
                            chatEvent.setEvent(event);

                            viewState.setError(null);
                            viewState.setChat(chatEvent);
                            getViewState().setValue(viewState);
                        }));
        } else if (chat instanceof ChatArticle){
            Log.d(TAG, "Article");
            ChatArticle chatArticle = (ChatArticle) chat;

            addDisposable(articleCache.getCache()
                    .subscribe(article ->{
                        // Inisialisasi Model
                        chatArticle.setArticle(article);
                        viewState.setError(null);
                        viewState.setChat(chatArticle);
                        getViewState().setValue(viewState);
                    }));
        } else if(chat instanceof ChatTweet){
            Log.d(TAG, "Tweet");
            ChatTweet chatTweet = (ChatTweet) chat;

            addDisposable(tweetCache.getCache()
                .subscribe(tweet -> {
                    chatTweet.setTweet(tweet);
                    viewState.setError(null);
                    viewState.setChat(chatTweet);
                    getViewState().setValue(viewState);
                })
            );

        } else if (chat != null &&
                chat.getMessage().equals(getApplication().getString(R.string.response_group_chat))) {
            viewState.setChat(chat);
            getViewState().setValue(viewState);
        } else {
            sendPost(message, userid);
        }
    }

    public void sendPost(String chat, int userid) {
        ChatbotViewState viewState = getViewState().getValue();
        ChatBotApi mAPIService = RetrofitService.getInstance().create(ChatBotApi.class);
        ChatBot chatBot = new ChatBot();
        chatBot.setText(chat);
        Call<ChatBot> call = mAPIService.sendChatMessage(chatBot);
        call.enqueue(new Callback<ChatBot>() {
            @Override
            public void onResponse(Call<ChatBot> call, Response<ChatBot> response) {
                if(response.isSuccessful()){
                    Log.d("cek", "chatbot: response code("+String.valueOf(response.code())+")");
                    Log.d("cek", "chatbot: response code("+String.valueOf(response.body().getResult())+")");
                    Chat res = new Chat(
                            response.body().getResult(),
                            DateUtils.getDate(),
                            DateUtils.getTime(),
                            Chat.READ,
                            ChatbotAdapter.CHATBOT_NAME,
                            ChatbotAdapter.CHATBOT_SEND_TO);
                    viewState.setChat(res);
                    getViewState().setValue(viewState);
                } else {
                    Log.d("cek", "chatbot: response code("+String.valueOf(response.code())+")");
                }
            }

            @Override
            public void onFailure(Call<ChatBot> call, Throwable t) {
                Log.d("cek", "chatbot: response code(" + String.valueOf(t.getMessage()) + ")");
            }
        });
//        Chat res = new Chat(
//                "Cora tidak mengerti",
//                DateUtils.getDate(),
//                DateUtils.getTime(),
//                Chat.READ,
//                ChatbotAdapter.CHATBOT_NAME,
//                ChatbotAdapter.CHATBOT_SEND_TO);
//        viewState.setChat(res);
//        getViewState().setValue(viewState);
//        mAPIService.savePost(chat, userid).enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                Log.d("cek", "chatbot: response code("+String.valueOf(response.code())+")");
//                Log.d("cek", "chatbot: response:"+response.headers().toString());
////                Log.d(TAG, "onResponse");
////                get endpoint url from response
//                Log.d(response.raw().request().url().toString(), "chatbot: url");
////                get request body key from response
//                if(response.isSuccessful()) {
//                    ChatbotViewState viewState = getViewState().getValue();
//                    Log.i("cek", "post submitted to API." + response.body().getResult().get(0).getResponse());
//                    Log.i("cek", "berhasil");
//
//                    viewState.setError(null);
//
//                    Chat res = new Chat(
//                            response.body().getResult().get(0).getResponse(),
//                            DateUtils.getDate(),
//                            DateUtils.getTime(),
//                            Chat.READ,
//                            ChatbotAdapter.CHATBOT_NAME,
//                            ChatbotAdapter.CHATBOT_SEND_TO);
//                    viewState.setChat(res);
//                    getViewState().setValue(viewState);
//                } else {
//                    Log.i("cek", "tidak berhasil ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                Log.e("cek", "Unable to submit post to API.", t);
//            }
//        });

    }


}
