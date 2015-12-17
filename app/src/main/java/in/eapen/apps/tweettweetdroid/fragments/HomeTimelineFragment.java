package in.eapen.apps.tweettweetdroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;
import in.eapen.apps.tweettweetdroid.utils.TwitterApplication;

/**
 * Created by geapen on 12/17/15.
 */
public class HomeTimelineFragment extends TweetListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        if (isNetworkAvailable()) {
            populateTimeline(client);
        } else {
            Toast.makeText(getActivity(), "Network unavailable", Toast.LENGTH_LONG).show();
        }

    }

    public void populateTimeline(TwitterClient client) {
        loading = true;
        client.getHomeTimeline(COUNT, nextPage, new JsonHttpResponseHandler() {
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


    public Boolean isNetworkAvailable() {
        /*ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();*/
        return true;
    }

}
