package in.eapen.apps.tweettweetdroid.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.fragments.HomeTimelineFragment;
import in.eapen.apps.tweettweetdroid.fragments.MentionsTimelineFragment;
import in.eapen.apps.tweettweetdroid.models.User;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;


public class TimelineActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private static final int COUNT = 25;
    private boolean loading;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        final TweetsPagerAdapter tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(tweetsPagerAdapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);
        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                actionBar.setTitle(tweetsPagerAdapter.getPageTitle(position));

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_compose);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
                startActivityForResult(i, ComposeTweetActivity.COMPOSE_TWEET_REQUEST);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void onLogout(MenuItem mi) {
        TwitterClient client = new TwitterClient(getApplicationContext());
        client.clearAccessToken();
        Toast.makeText(TimelineActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onProfileView(MenuItem mi) {
        TwitterClient client = new TwitterClient(getApplicationContext());
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    User user = User.fromJson(json);
                    user.getUid();
                    Intent i = new Intent(TimelineActivity.this, ViewProfileActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 429) {
                    Toast.makeText(getApplicationContext(), "Exceeded limit. Please wait 15 minutes.", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "an error occurred");
                Toast.makeText(getApplicationContext(), "error retrieving user", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = { "Home", "Mentions" };

        // Adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        public String getPageTitle(int position) {
            return tabTitles[position];
        }
    }


}
