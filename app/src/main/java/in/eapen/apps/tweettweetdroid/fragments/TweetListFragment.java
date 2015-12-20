package in.eapen.apps.tweettweetdroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.eapen.apps.tweettweetdroid.R;
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

    private final String TAG = this.getClass().getSimpleName();

    public String timeline;

    static final int COUNT = 25;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private ImageView ivProfile;

    int itemsCount = 0;
    int nextPage = 1;
    boolean loading = false;

    private TwitterClient client;

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
    // inflate view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        ImageView ivProfile = (ImageView) v.findViewById(R.id.ivProfileImage);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                itemsCount = totalItemsCount;
                nextPage = page + 1;
                populateTimeline(timeline);
                Log.d(TAG, "Fetching more " + timeline);
                return loading;
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

    public void populateTimeline(String timeline) {
        loading = true;
        long userId = 0;
        if (getArguments() != null && getArguments().containsKey("userId")) {
            userId = getArguments().getLong("userId", 0);
        }
        client.getTimeline(timeline, userId, COUNT, nextPage, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d(TAG, json.toString());
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
