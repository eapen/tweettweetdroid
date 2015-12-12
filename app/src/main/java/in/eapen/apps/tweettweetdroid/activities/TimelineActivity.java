package in.eapen.apps.tweettweetdroid.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.utils.TwitterApplication;
import in.eapen.apps.tweettweetdroid.adapters.TweetsArrayAdapter;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.models.User;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;


public class TimelineActivity extends AppCompatActivity {

    private static final int COUNT = 25;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        lvTweets = (ListView) findViewById(R.id.lvHomeTimeline);
        client = TwitterApplication.getRestClient();

        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(this, tweets);

        lvTweets.setAdapter(aTweets);
        populateTimeline();


        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet t = aTweets.getItem(position);
                Intent i = new Intent(TimelineActivity.this, TweetDetailActivity.class);
                i.putExtra("tweet", t);
                startActivityForResult(i, 100);
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
                startActivityForResult(i, 100);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void populateTimeline() {
        client.getHomeTimeline(COUNT, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("XXX", json.toString());
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    if (errorResponse.getJSONArray("errors").getJSONObject(0).getInt("code") == 88) {
                        Toast.makeText(getApplicationContext(), "Exceeded limit. Please wait 15 minutes.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("XXX", errorResponse.toString());
            }

            @Override
            public void onUserException(Throwable error) {
                Toast.makeText(getApplicationContext(), "User exception" + error.toString(), Toast.LENGTH_SHORT).show();
                //super.onUserException(error);
            }
        });
    }

}
