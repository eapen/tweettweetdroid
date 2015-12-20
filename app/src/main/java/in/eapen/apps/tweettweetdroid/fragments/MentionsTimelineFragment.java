package in.eapen.apps.tweettweetdroid.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by geapen on 12/17/15.
 */
public class MentionsTimelineFragment extends TweetListFragment {

    public String timeline = "Mentions";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline(timeline);
        setTimeline(timeline);
    }

}
