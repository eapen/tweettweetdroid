package in.eapen.apps.tweettweetdroid.net;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "";       // Change this
	public static final String REST_CONSUMER_SECRET = ""; // Change this
	public static final String REST_CALLBACK_URL = "oauth://tweettweetdroid"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void postTweet(String status, JsonHttpResponseHandler handler) throws UnsupportedEncodingException {
        String encodedStatus = URLEncoder.encode(status, "UTF-8");
		String apiUrl = getApiUrl("statuses/update.json?status=" + encodedStatus);
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		//params.put("status", encodedStatus);
		client.post(apiUrl, params, handler);
	}

	public void getHomeTimeline(int count, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", count);
		//params.put("since_id", 1);
		client.get(apiUrl, params, handler);
	}

	// TODO: cleanup
	public void getOlderHomeTimeline(int count, int max_id, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", count);
		params.put("max_id", max_id);
		client.get(apiUrl, params, handler);
	}

	// TODO: cleanup
	public void getNewerHomeTimeline(int count, int since_id, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", count);
		params.put("since_id", since_id);
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
}