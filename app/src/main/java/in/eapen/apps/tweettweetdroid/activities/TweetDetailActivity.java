package in.eapen.apps.tweettweetdroid.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.models.User;

public class TweetDetailActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvText;

    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Tweet Deets");

        // Fetch views
        ivProfilePicture = (ImageView) findViewById(R.id.ivTDProfilePicture);
        tvName = (TextView) findViewById(R.id.tvTDName);
        tvScreenName = (TextView) findViewById(R.id.tvTDScreenName);
        tvText = (TextView) findViewById(R.id.tvTDTweetContent);

        // Extract book object from intent extras
        tweet = (Tweet) getIntent().getParcelableExtra("tweet");
        // Use book object to populate data into views
        tvName.setText(tweet.getUser().getName());
        tvScreenName.setText("@" + tweet.getUser().getScreenName());
        tvText.setText(tweet.getText());
        Picasso.with(getApplicationContext()).load(Uri.parse(tweet.getUser().getProfileImageUrl())).placeholder(R.drawable.ic_profile_placeholder).into(ivProfilePicture);

    }
}
