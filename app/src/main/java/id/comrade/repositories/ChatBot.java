package id.comrade.repositories;

import java.util.List;

import io.reactivex.Single;

public class ChatBot extends  Cache<ChatBot>{

    @Override
    protected Single<List<ChatBot>> retrieve() {
        return null;
    }
}
