package in.eapen.apps.tweettweetdroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.TwitterApplication;
import in.eapen.apps.tweettweetdroid.adapters.TweetsArrayAdapter;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.lvHomeTimeline);
        client = TwitterApplication.getRestClient();

        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(this, tweets);

        lvTweets.setAdapter(aTweets);
        populateTimeline();
    }

        private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("XXX", json.toString());
                aTweets.addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("XXX", "Failure");
            }

            @Override
            public void onUserException(Throwable error) {
                Toast.makeText(getApplicationContext(), "Did you hit rate limits?" + error.toString(), Toast.LENGTH_SHORT).show();
                //super.onUserException(error);
            }
        });
    }

}
