package in.eapen.apps.tweettweetdroid.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.eapen.apps.tweettweetdroid.R;
import in.eapen.apps.tweettweetdroid.models.Tweet;
import in.eapen.apps.tweettweetdroid.utils.ParseRelativeDate;

/**
 * Created by geapen on 12/10/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    // View lookup cache
    private static class ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvName;
        public TextView tvScreenName;
        public TextView tvRelativeDate;
        public TextView tvTweet;
    }

    public TweetsArrayAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Tweet tweet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
            viewHolder.tvRelativeDate = (TextView) convertView.findViewById(R.id.tvRelativeTime);
            viewHolder.tvTweet = (TextView) convertView.findViewById(R.id.tvTweet);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        viewHolder.tvName.setText(tweet.getUser().getName());
        viewHolder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
        String relativeTime = ParseRelativeDate.getRelativeTimeAgo(tweet.getTimestampString());
        viewHolder.tvRelativeDate.setText(relativeTime);
        viewHolder.tvTweet.setText(tweet.getText());
        Picasso.with(getContext()).load(Uri.parse(tweet.getUser().getProfileImageUrl())).placeholder(R.drawable.ic_profile_placeholder).into(viewHolder.ivProfileImage);
        // Return the completed view to render on screen
        return convertView;
    }

}
