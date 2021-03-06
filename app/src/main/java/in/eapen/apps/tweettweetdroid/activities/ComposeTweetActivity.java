package in.eapen.apps.tweettweetdroid.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.models.User;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;
import in.eapen.apps.tweettweetdroid.utils.TwitterApplication;

public class ComposeTweetActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static final int COMPOSE_TWEET_REQUEST = 100;
    private int MAX_CHARACTERS;
    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvScreenName;
    private EditText etText;

    private MenuItem counterItem;
    private MenuItem tweetBtn;

    private TwitterClient client;
    private User user;

    private int characterCounter;
    private int currentLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Resources res = getResources();
        MAX_CHARACTERS = res.getInteger(R.integer.char_limit);
        setContentView(R.layout.activity_compose_tweet);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Compose");

        // Fetch views
        ivProfilePicture = (ImageView) findViewById(R.id.ivCTProfilePicture);
        tvName = (TextView) findViewById(R.id.tvCTName);
        tvScreenName = (TextView) findViewById(R.id.tvCTScreenName);
        etText = (EditText) findViewById(R.id.etCTTweetContent);

        // etText.setOnKeyListener(updateCharCounter());
        user = new User();

        client = TwitterApplication.getRestClient();

        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    user = User.fromJson(json);
                    tvName.setText(user.getName());
                    tvScreenName.setText("@" + user.getScreenName());
                    Picasso.with(getApplicationContext()).load(Uri.parse(user.getProfileImageUrl())).placeholder(R.drawable.ic_profile_placeholder).into(ivProfilePicture);
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

        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    characterCounter = Integer.parseInt(counterItem.getTitle().toString());
                } catch (NumberFormatException e) {
                    characterCounter = 0;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentLength = etText.getText().toString().length();
                counterItem.setTitle(String.valueOf(MAX_CHARACTERS - currentLength));
                if (currentLength > MAX_CHARACTERS) {
                    etText.setText(etText.getText().toString().subSequence(0, MAX_CHARACTERS));
                    etText.setSelection(MAX_CHARACTERS);
                }
            }
        });
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compose_tweet, menu);
        counterItem = menu.findItem(R.id.character_count);
        counterItem.setTitle(Integer.toString(MAX_CHARACTERS));
        tweetBtn = menu.findItem(R.id.menu_text_compose);
        tweetBtn.setTitle(R.string.compose_tweet_button);
        tweetBtn.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), etText.getText().toString(), Toast.LENGTH_SHORT).show();
                try {
                    client.postTweet(etText.getText().toString(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                            Log.d(TAG, "Created tweet");
                            Intent data = new Intent();
                            Tweet tweet = new Tweet();
                            try {
                                tweet = Tweet.fromJSON(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            data.putExtra("tweet", tweet);
                            setResult(RESULT_OK, data);
                            finish();
                        }

                        // handle JSON error responses
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d(TAG, "Error: " + errorResponse.toString());
                            Toast.makeText(getBaseContext(), errorResponse.toString(), Toast.LENGTH_SHORT).show();
                        }

                        // handle non-JSON error responses
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                            Log.e("ERROR", errorResponse.toString());
                        }

                    });
                    return true;
                } catch (UnsupportedEncodingException e) {
                    Log.e("ERROR", e.toString());
                    e.printStackTrace();
                    return false;
                }
            }
        });

        return true;
    }
}
