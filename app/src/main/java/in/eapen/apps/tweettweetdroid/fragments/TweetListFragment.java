package in.eapen.apps.tweettweetdroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.activities.ComposeTweetActivity;
import in.eapen.apps.tweettweetdroid.activities.TweetDetailActivity;
import in.eapen.apps.tweettweetdroid.adapters.TweetsArrayAdapter;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;
import in.eapen.apps.tweettweetdroid.utils.EndlessScrollListener;
import in.eapen.apps.tweettweetdroid.utils.NetworkCheck;
import in.eapen.apps.tweettweetdroid.utils.TwitterApplication;

/**
 * Created by geapen on 12/17/15.
 */
public class TweetListFragment extends Fragment {

    static final int COUNT = 25;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    int itemsCount = 0;
    int nextPage = 1;
    boolean loading = false;

    private TwitterClient client;

    // inflate view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                itemsCount = totalItemsCount;
                nextPage = page + 1;
                //populateTimeline("Home");
                return loading;
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet t = aTweets.getItem(position);
                Intent i = new Intent(getActivity(), TweetDetailActivity.class);
                i.putExtra("tweet", t);
                startActivityForResult(i, TweetDetailActivity.TWEET_DETAIL_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);

        client = TwitterApplication.getRestClient();
        NetworkCheck nc = new NetworkCheck(getActivity());
        if (!nc.isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Network unavailable", Toast.LENGTH_LONG).show();
            // TODO: abort network calls
            getActivity().finish();
        }
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }


    // TODO: this should move to the Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ComposeTweetActivity.COMPOSE_TWEET_REQUEST) {
            // Make sure the request was successful
            if (resultCode == getActivity().RESULT_OK) {
                Tweet tweet = data.getParcelableExtra("tweet");
                aTweets.insert(tweet, 0);
                aTweets.notifyDataSetChanged();
            }
        }
    }


    public void populateTimeline(String timeline) {
        loading = true;
        long userId = 0;
        try {
            userId = getArguments().getLong("userId", 0);
        } catch (Exception e) {
            Log.e("XXX", e.toString());
        }
        client.getTimeline(timeline, userId, COUNT, nextPage, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("XXX", json.toString());
                addAll(Tweet.fromJSONArray(json));
                loading = false;
            }

            // handle JSON failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 429) {
                    Toast.makeText(getActivity(), "Exceeded limit. Please wait 15 minutes.", Toast.LENGTH_SHORT).show();
                }
                Log.e("ERROR", errorResponse.toString());
                loading = false;
            }

            // handle non-JSON error responses
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                Log.e("ERROR", errorResponse.toString());
                loading = false;
            }

            @Override
            public void onUserException(Throwable error) {
                Toast.makeText(getActivity(), "User exception: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", error.toString());
                loading = false;
            }
        });
    }
}
