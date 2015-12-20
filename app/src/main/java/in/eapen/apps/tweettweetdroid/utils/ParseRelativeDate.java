package in.eapen.apps.tweettweetdroid.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by geapen on 12/10/15.
 */
public final class ParseRelativeDate {
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            relativeDate = relativeDate.replace(" mins", "m").replace(" min", "m")
                    .replace(" hours", "h").replace(" hour", "h")
                    .replace(" secs", "s").replace(" sec", "s")
                    .replace(" days", "d").replace(" day", "d")
                    .replace(" ago", "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
