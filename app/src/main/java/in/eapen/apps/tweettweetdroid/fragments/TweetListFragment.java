package in.eapen.apps.tweettweetdroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.activities.ComposeTweetActivity;
import in.eapen.apps.tweettweetdroid.activities.TweetDetailActivity;
import in.eapen.apps.tweettweetdroid.adapters.TweetsArrayAdapter;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.utils.EndlessScrollListener;

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
                nextPage = page+1;
                //populateTimeline();
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
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

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

}
