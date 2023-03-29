package id.comrade.model;

import java.util.List;

public class ChatTweet extends Chat {
    private List<Tweet> mTweet;
    public ChatTweet(String message, String date, String time, int status,
                     String uidSender, String uidRecevier, List<Tweet> mTweet) {
        super(message, date, time, status, uidSender, uidRecevier);
        this.mTweet = mTweet;
    }

    private Integer totalPage;


    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Tweet> getTweet() {
        return mTweet;
    }

    public void setTweet(List<Tweet> mTweet) {
        this.mTweet = mTweet;
    }
}
