# *TweetTweet Droid*

**TweetTweet Droid** is an android Twitter client clone. Yes, yet another one.

Submitted by: **Eapen**

Time spent in Week 2: **15** hours

Time spent in Week 1: **16** hours spent in total

## Video Walkthrough 

Here's a walkthrough of implemented user stories:


Week 2:

<img src='https://github.com/eapen/tweettweetdroid/blob/master/tweettweetdroid-screencap-week-2.gif' title='Video Walkthrough Week 2' width='' alt='Video Walkthrough Week 2' />

Week 1:

<img src='https://github.com/eapen/tweettweetdroid/blob/master/tweettweetdroid-screencap.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).



## Week 2 User Stories

The following **required** functionality is completed:

* [x] User can switch between Timeline and Mention views using tabs.
* [x] User can view their home timeline tweets.
* [x] User can view the recent mentions of their username.
* [x] User can navigate to view their own profile
* [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can click on the profile image in any tweet to see another user's profile.
* [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [x] Profile view should include that user's timeline
* [ ] Optional: User can view following / followers list through the profile
* [x] User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom


The following **optional** features are implemented:

* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* [ ] Advanced: When a network request is sent, user sees an indeterminate progress indicator
* [ ] Advanced: User can "reply" to any tweet on their home timeline
* [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [-] Advanced: User can click on a tweet to be taken to a "detail view" of that tweet
* [ ] Advanced: User can take favorite (and unfavorite) or reweet actions on a tweet
* [ ] Advanced: Improve the user interface and theme the app to feel "twitter branded"
* [ ] Advanced: User can search for tweets matching a particular query and see results
* [ ] Bonus: Replace the existing ActionBar with the newer support App ToolBar instead.
* [ ] Bonus: Apply the popular Butterknife annotation library to reduce view boilerplate.
* [ ] Bonus: Leverage the popular GSON library to streamline the parsing of JSON data.
* [ ] Bonus: User can view their Twitter direct messages (and/or send new ones)

The following **additional** features are implemented:

* [x] Autolinking content in Tweet's but lost ability to go to detailed tweet view
* [x] Added "Logout" menu option


## Week 1 User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login
* [x] User can view the tweets from their home timeline
* [x] Should display the username, name, and body for each tweet
* [x] Should be displayed the relative timestamp for each tweet "8m", "7h"
* [x] User can view more tweets as they scroll with infinite pagination
* [x] User can compose a new tweet
* [x] User can click a “Compose” icon in the Action Bar on the top right
* [x] User can then enter a new tweet and post this to twitter
* [x] User is taken back to home timeline with new tweet visible in timeline


The following **optional** features are implemented:

* [x] Advanced: While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [ ] Advanced: Links in tweets are clickable and will launch the web browser (see autolink)
* [ ] Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
* [ ] Advanced: User can open the twitter app offline and see last loaded tweets (using sqlite)
* [ ] Advanced: User can tap a tweet to display a "detailed" view of that tweet
* [ ] Advanced: User can select "reply" from detail view to respond to a tweet
* [ ] Advanced: Improve the user interface and theme the app to feel "twitter branded"
* [ ] Bonus: User can see embedded image media within the tweet detail view
* [ ] Bonus: Compose activity is replaced with a modal overlay

The following **additional** features are implemented:

* [x] Utilizing ViewHolder pattern for performance optimization
* [x] Displaying HTML characters properly in the TextView
* [x] Added Tweet Detail view

## Notes

I am using Twitter's deprecated "page" parameter for paging to get the minimum requirements and need to implement the paging using the since_id.


## License

    Copyright [2015] [George Eapen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
