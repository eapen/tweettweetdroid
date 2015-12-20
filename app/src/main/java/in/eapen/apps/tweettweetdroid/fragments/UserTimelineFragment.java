package in.eapen.apps.tweettweetdroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by geapen on 12/18/15.
 */
public class UserTimelineFragment extends TweetListFragment {

    public String timeline = "User";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.populateTimeline(timeline);
    }

    public static UserTimelineFragment newInstance(long userId) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putLong("userId", userId);
        userFragment.setArguments(args);
        return userFragment;
    }

}