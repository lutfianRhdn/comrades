package id.comrade.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import id.comrade.R;
import id.comrade.base.BaseFragment;
import id.comrade.model.User;
import id.comrade.more.MoreActivity;
import id.comrade.services.webservice.RetrofitService;

public class ProfileFragment extends BaseFragment<ProfileViewState, ProfileViewModel> {

    @BindView(R.id.sdv_fragment_profile_photo)
    SimpleDraweeView sdvPhoto;

    @BindView(R.id.tv_fragment_profile_name_photo)
    TextView tvName;
    @BindView(R.id.tv_fragment_profile_email)
    TextView tvEmail;
    @BindView(R.id.tv_fragment_profile_gender)
    TextView tvGender;
    @BindView(R.id.tv_fragment_profile_birth)
    TextView tvBirth;
    @BindView(R.id.tv_fragment_profile_phone)
    TextView tvPhone;
    @BindView(R.id.tv_fragment_profile_change_password)
    TextView tvChangePassword;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(ProfileViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String photoUrl = getSharedPreferences(User.PREF_USER_PHOTO);
        sdvPhoto.setImageURI(photoUrl.contains("https") ? photoUrl : RetrofitService.PIC_USER + photoUrl);
        tvName.setText(getSharedPreferences(User.PREF_USER_NAME));
        tvEmail.setText(getSharedPreferences(User.PREF_USER_EMAIL));
        tvGender.setText(getGender(getSharedPreferences(User.PREF_USER_GENDER)));
        tvBirth.setText(getSharedPreferences(User.PREF_USER_BIRTH));
        tvPhone.setText(getSharedPreferences(User.PREF_USER_PHONE));
        tvChangePassword.setOnClickListener(v -> showToast("Coming soon! Please be patient :)"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fragment_profile_more:
                show(MoreActivity.class);
                return true;
        }

        return false;
    }

    @Override
    protected void handleState(ProfileViewState viewState) {
    }

    private String getGender(String gender) {
        return gender.equals("L") ? getString(R.string.male_label) : getString(R.string.female_label);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }
}
