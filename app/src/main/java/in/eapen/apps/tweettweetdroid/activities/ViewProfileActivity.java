package in.eapen.apps.tweettweetdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import in.eapen.apps.tweettweetdroid.R;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_twitter_circle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
