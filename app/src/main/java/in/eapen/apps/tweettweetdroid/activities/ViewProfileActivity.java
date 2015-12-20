package in.eapen.apps.tweettweetdroid.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.fragments.UserTimelineFragment;
import in.eapen.apps.tweettweetdroid.models.User;

public class ViewProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvTagline;
    private TextView tvFollowersCount;
    private TextView tvFriendsCount;
    private TextView tvTweetsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        User user = getIntent().getParcelableExtra("user");


        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(user.getUid());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUTTweets, userTimelineFragment);
            ft.commit();
        }

        final RelativeLayout rlHeader = (RelativeLayout) findViewById(R.id.rlUTHeader);
        actionBar.setTitle(user.getScreenName());
        ivProfilePicture = (ImageView) findViewById(R.id.ivUTProfilePicture);
        tvName = (TextView) findViewById(R.id.tvUTName);
        tvScreenName = (TextView) findViewById(R.id.tvUTScreenName);
        tvTagline = (TextView) findViewById(R.id.tvUTTagline);
        tvFollowersCount = (TextView) findViewById(R.id.tvUTFollowersCount);
        tvFriendsCount = (TextView) findViewById(R.id.tvUTFriendsCount);
        tvTweetsCount = (TextView) findViewById(R.id.tvUTTweetsCount);

        tvName.setText(user.getName());
        tvScreenName.setText("@" + user.getScreenName());
        tvTagline.setText(user.getTagline());
        tvFollowersCount.setText(Integer.toString(user.getFollowingCount()) + " Following");
        tvFriendsCount.setText(Integer.toString(user.getFollowersCount()) + " Followers");
        tvTweetsCount.setText(Long.toString(user.getTweetsCount()) + " Tweets");
        /*
        if (user.getBackgroundImageUrl() != null && user.getBackgroundImageUrl() != "") {
            Picasso.with(getApplicationContext()).load(Uri.parse(user.getBackgroundImageUrl())).into(new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    rlHeader.setBackground(new BitmapDrawable(getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(final Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });
        }
        */
        Picasso.with(getApplicationContext()).load(Uri.parse(user.getProfileImageUrl())).placeholder(R.drawable.ic_profile_placeholder).into(ivProfilePicture);


    }
}
