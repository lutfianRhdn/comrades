package id.comrade.chatbot.viewholders.headings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.comrade.R;
import id.comrade.base.BaseActivity;
import id.comrade.chatbot.ChatbotAdapter;
import id.comrade.model.Chat;
import id.comrade.model.User;
import id.comrade.repositories.TopikCache;
import id.comrade.utils.DateUtils;

public class HeadingViewHolder extends ChatbotAdapter.ViewHolder {
    @BindView(R.id.tv_item_chatbot_heading_welcome_heading)
    TextView tvWelcome;

    @BindView(R.id.rv_item_chatbot_heading)
    RecyclerView rvSuggestion;

    private HeadingTopicAdapter adapter;

    private TopikCache topicCache;

    public HeadingViewHolder(View itemView, Activity activity) {
        super(itemView, activity);

        ButterKnife.bind(this, itemView);

        adapter = new HeadingTopicAdapter();
        topicCache = TopikCache.getInstance();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity(),
                FlexDirection.ROW, FlexWrap.WRAP);
        rvSuggestion.setLayoutManager(layoutManager);
        rvSuggestion.setHasFixedSize(true);
        rvSuggestion.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    @Override
    public void init(Chat chat) {
        topicCache.getCache()
                .subscribe(topics -> adapter.setTopics(topics));

        BaseActivity baseActivity = (BaseActivity) getActivity();
        tvWelcome.setText(getActivity().getString(R.string.fragment_home_greeting,
                DateUtils.getTimeDescription(getActivity()),
                baseActivity.getSharedPreferences(User.PREF_USER_NAME)));
    }
}
