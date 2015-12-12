package in.eapen.apps.tweettweetdroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.models.User;
import in.eapen.apps.tweettweetdroid.net.TwitterClient;
import in.eapen.apps.tweettweetdroid.utils.TwitterApplication;

public class ComposeTweetActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvName;
    private TextView tvScreenName;
    private TextView tvText;

    private TwitterClient client;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Log.d("XXX", e.toString());
                    e.printStackTrace();
                }
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
                Log.d("XXX", "an error occurred");
                Toast.makeText(getApplicationContext(), "error retrieving user", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compose_tweet, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_text_compose);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
