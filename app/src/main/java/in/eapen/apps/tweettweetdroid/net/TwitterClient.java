package in.eapen.apps.tweettweetdroid.net;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "";       // Change this
	public static final String REST_CONSUMER_SECRET = ""; // Change this
	public static final String REST_CALLBACK_URL = "oauth://tweettweetdroid";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void postTweet(String status, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        String encodedStatus = URLEncoder.encode(status, "UTF-8");
		String apiUrl = getApiUrl("statuses/update.json?status=" + encodedStatus);
		RequestParams params = new RequestParams();
		client.post(apiUrl, params, handler);
	}

	public void getHomeTimeline(int count, int page, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", count);
		params.put("page", page);
		Log.d("XXX", apiUrl + " " + params.toString());
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
}