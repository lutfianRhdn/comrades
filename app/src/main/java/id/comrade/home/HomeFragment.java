package id.comrade.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseFragment;
import id.comrade.chatbot.ChatbotActivity;
import id.comrade.main.MainActivity;
import id.comrade.main.MainPagerAdapter;
import id.comrade.model.Posting;
import id.comrade.model.Reminder;
import id.comrade.model.Topic;
import id.comrade.model.User;
import id.comrade.news.latest.LatestNewsActivity;
import id.comrade.reminder.add.AddReminderActivity;
import id.comrade.utils.DateUtils;

public class HomeFragment extends BaseFragment<HomeViewState, HomeViewModel> {

    @BindView(R.id.pb_fragment_home_progress)
    ProgressBar pbProgress;

    @BindView(R.id.nsv_fragment_home_main_view)
    NestedScrollView nsvMainView;

    @BindView(R.id.iv_fragment_home_cora_navigation)
    ImageView ivCoraNavigation;
    @BindView(R.id.iv_fragment_home_add_reminder)
    ImageView ivAddReminder;
    @BindView(R.id.iv_fragment_home_active_indicator)
    ImageView ivActiveIndicator;

    @BindView(R.id.rv_fragment_home_topic_suggestion)
    RecyclerView rvTopics;
    @BindView(R.id.rv_fragment_home_news)
    RecyclerView rvNews;

    @BindView(R.id.tv_fragment_home_greeting)
    TextView tvGreetings;
    @BindView(R.id.tv_fragment_home_last_chat_time)
    TextView tvLastChatTime;
    @BindView(R.id.tv_fragment_home_last_chat_content)
    TextView tvLastChat;
    @BindView(R.id.tv_fragment_home_cora_label)
    TextView tvCoraNavigation;
    @BindView(R.id.tv_fragment_home_selengkapnya)
    TextView tvMore;
    @BindView(R.id.tv_fragment_home_next_reminder_title)
    TextView tvReminderTitle;
    @BindView(R.id.tv_fragment_home_next_reminder_time)
    TextView tvReminderTime;
    @BindView(R.id.tv_fragment_home_all_news)
    TextView tvAllNews;

    private HomeTopicAdapter topicAdapter;
    private HomeNewsAdapter newsAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(HomeViewModel.class);

        topicAdapter = new HomeTopicAdapter(getContext());
        rvTopics.setAdapter(topicAdapter);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity(),
                FlexDirection.ROW, FlexWrap.WRAP);
        rvTopics.setLayoutManager(layoutManager);
        rvTopics.setHasFixedSize(true);
        rvTopics.setNestedScrollingEnabled(false);

        newsAdapter = new HomeNewsAdapter(getContext());
        rvNews.setAdapter(newsAdapter);

        tvGreetings.setText(getString(
                R.string.fragment_home_greeting,
                DateUtils.getTimeDescription(getContext()),
                getSharedPreferences(User.PREF_USER_NAME)
        ));
        tvLastChat.setText("Apakah HIV dapat menular melalui sentuhan dengan yang lainnya?");
        tvLastChatTime.setText("20 menit yang lalu");

        tvMore.setOnClickListener(v -> showReminder());

        ivAddReminder.setOnClickListener(v -> show(AddReminderActivity.class));
        tvCoraNavigation.setOnClickListener(v -> show(ChatbotActivity.class));
        ivCoraNavigation.setOnClickListener(v -> show(ChatbotActivity.class));
        tvAllNews.setOnClickListener(v -> show(LatestNewsActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        topicAdapter = null;
    }

    @SuppressWarnings("ConstantConditions")
    private void showReminder() {
        MainActivity activity = (MainActivity) getActivity();
        activity.show(MainPagerAdapter.REMINDER_TAB);
    }

    @Override
    protected void handleState(HomeViewState viewState) {
        handleLoading(viewState.isLoading());
        handleTopics(viewState.getTopics());
        handleNews(viewState.getNews());
        handleError(viewState.getError());
        handleReminder(viewState.getReminder());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    private void handleReminder(Reminder reminder) {
        if (reminder == null) {
            tvReminderTitle.setText(getString(R.string.reminder_not_found));
            tvReminderTime.setText("");
            tvReminderTime.setVisibility(View.GONE);
        } else {
            tvReminderTitle.setText(reminder.getNamaObat());
            tvReminderTime.setVisibility(View.VISIBLE);
            tvReminderTime.setText(DateUtils.getReminderRemainingTime(getContext(),
                    reminder.getStartingTime(), Integer.valueOf(reminder.getReminder())));
        }
    }

    private void handleError(String error) {
        if (error != null) {
//            showToast(error);

        }
    }

    private void handleNews(List<Posting> news) {
        if (news != null) {
            newsAdapter.setNews(news);
        }
    }

    private void handleTopics(List<Topic> topics) {
        if (topics != null) {
            topicAdapter.setTopics(topics);
        }
    }

    private void handleLoading(boolean loading) {
        if (loading) {
            pbProgress.setVisibility(View.VISIBLE);
            nsvMainView.setVisibility(View.GONE);
        } else {
            pbProgress.setVisibility(View.GONE);
            nsvMainView.setVisibility(View.VISIBLE);
        }
    }
}
