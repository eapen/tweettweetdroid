package in.eapen.apps.tweettweetdroid.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import in.eapen.apps.tweettweetdroid.R;


public class TimelineActivity extends AppCompatActivity {

    private static final int COUNT = 25;
    private boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
                startActivityForResult(i, ComposeTweetActivity.COMPOSE_TWEET_REQUEST);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
