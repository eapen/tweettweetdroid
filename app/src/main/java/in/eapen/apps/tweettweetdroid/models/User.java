package in.eapen.apps.tweettweetdroid.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/*
"user": {
      "name": "OAuth Dancer",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "created_at": "Wed Mar 03 19:37:35 +0000 2010",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "119476949",
      "is_translator": false,
      "profile_link_color": "0084B4",
      "entities": {
        "url": {
          "urls": [
            {
              "expanded_url": null,
              "url": "http://bit.ly/oauth-dancer",
              "indices": [
                0,
                26
              ],
              "display_url": null
            }
          ]
        },
        "description": null
      },
      "default_profile": false,
      "url": "http://bit.ly/oauth-dancer",
      "contributors_enabled": false,
      "favourites_count": 7,
      "utc_offset": null,
      "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "id": 119476949,
      "listed_count": 1,
      "profile_use_background_image": true,
      "profile_text_color": "333333",
      "followers_count": 28,
      "lang": "en",
      "protected": false,
      "geo_enabled": true,
      "notifications": false,
      "description": "",
      "profile_background_color": "C0DEED",
      "verified": false,
      "time_zone": null,
      "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
      "statuses_count": 166,
      "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
      "default_profile_image": false,
      "friends_count": 14,
      "following": false,
      "show_all_inline_media": false,
      "screen_name": "oauth_dancer"
    }
 */

public class User implements Parcelable {
    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowingCount() { return followingCount; }

    public int getFollowersCount() { return followersCount; }

    public long getTweetsCount() { return tweetsCount; }

    private String name;
    private String screenName;
    private long uid;
    private String profileImageUrl;
    private String backgroundImageUrl;
    private String tagline;
    private int followingCount;
    private int followersCount;
    private long tweetsCount;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.uid = jsonObject.getLong("id");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https").replace("_normal", "_bigger");
        user.backgroundImageUrl= jsonObject.getString("profile_background_image_url_https");
        user.tagline = jsonObject.getString("description");
        user.followingCount = jsonObject.getInt("followers_count");
        user.followersCount = jsonObject.getInt("friends_count");
        user.tweetsCount = jsonObject.getLong("statuses_count");
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.screenName);
        dest.writeLong(this.uid);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.backgroundImageUrl);
        dest.writeString(this.tagline);
        dest.writeInt(this.followingCount);
        dest.writeInt(this.followersCount);
        dest.writeLong(this.tweetsCount);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.screenName = in.readString();
        this.uid = in.readLong();
        this.profileImageUrl = in.readString();
        this.backgroundImageUrl = in.readString();
        this.tagline = in.readString();
        this.followingCount = in.readInt();
        this.followersCount = in.readInt();
        this.tweetsCount = in.readLong();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
