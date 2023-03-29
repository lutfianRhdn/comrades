package id.comrade.friendtoshare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import id.comrade.R;
import id.comrade.base.BaseFragment;
import id.comrade.model.User;

public class FriendToShareFragment extends BaseFragment<FriendToShareViewState,
        FriendToShareViewModel> {
    private FriendToShareAdapter adapter;

    @BindView(R.id.et_fragment_friend_to_share_search_box)
    EditText etSearchBox;

    @BindView(R.id.rv_fragment_friend_to_share)
    RecyclerView rvFriends;

    @BindView(R.id.rl_fragment_friend_to_share_main_view)
    RelativeLayout rlMainView;

    @BindView(R.id.pb_fragment_friend_to_share_progress)
    ProgressBar pbProgress;

    public static FriendToShareFragment newInstance() {
        return new FriendToShareFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(FriendToShareViewModel.class);
        getViewModel().findSahabat(getSharedPreferences(User.PREF_USER_TYPE));

        adapter = new FriendToShareAdapter(getContext());
        rvFriends.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Override
    protected void handleState(FriendToShareViewState viewState) {
        handleLoading(viewState.isLoading());
        handleUsers(viewState.getUsers());
        handleError(viewState.getError());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friend_to_share;
    }

    @OnTextChanged(R.id.et_fragment_friend_to_share_search_box)
    public void onSearchChanged() {
        getViewModel().filter(etSearchBox.getText().toString());
    }

    private void handleLoading(boolean isLoading) {
        if (isLoading) {
            pbProgress.setVisibility(View.VISIBLE);
            rlMainView.setVisibility(View.GONE);
        } else {
            pbProgress.setVisibility(View.GONE);
            rlMainView.setVisibility(View.VISIBLE);
        }
    }

    private void handleUsers(List<User> users) {
        if (users != null) {
            adapter.setFriends(users);
        }
    }

    private void handleError(String msg) {
        if (msg != null) {
            showToast(msg);
        }
    }
}
