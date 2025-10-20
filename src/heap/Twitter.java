package heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// Leetcode problem: 355
public class Twitter {

    Map<Integer, Set<Integer>> followMap;
    Map<Integer, List<int[]>> tweetMap;
    int time;

    public Twitter() {
        followMap = new HashMap<>();
        tweetMap = new HashMap<>();
        time = 1;
    }

    // Add tweet with time posted.
    public void postTweet(int userId, int tweetId) {
        List<int[]> tweets = tweetMap.getOrDefault(userId, new ArrayList<>());
        tweets.add(new int[]{tweetId, time++});
        tweetMap.put(userId, tweets);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        List<NewsFeed> newsFeeds = new ArrayList<>();

        // Get all the followees the user follows.
        Set<Integer> followees = followMap.getOrDefault(userId, new HashSet<>());

        // User can also see self posts, so add user id to the list too.
        followees.add(userId);

        // For the first time add only the last posts of the followees, to reduce queue size.
        for (int followee : followees) {
            if (tweetMap.containsKey(followee)) {
                List<int[]> tweets = tweetMap.get(followee);
                int index = tweets.size() - 1;

                int[] tweet = tweets.get(index);

                // Save the next index from the last to get the previous tweet of that user.
                newsFeeds.add(new NewsFeed(tweet[0], followee, tweet[1], index - 1));
            }
        }

        PriorityQueue<NewsFeed> queue = new PriorityQueue<>(newsFeeds);

        while (!queue.isEmpty() && result.size() < 10) {
            NewsFeed newsFeed = queue.poll();
            result.add(newsFeed.tweetId);

            // If the followee has more post, add it to the queue.
            if (newsFeed.index >= 0) {
                int[] tweet = tweetMap.get(newsFeed.userId).get(newsFeed.index);
                queue.add(new NewsFeed(tweet[0], newsFeed.userId, tweet[1], newsFeed.index - 1));
            }
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        Set<Integer> followSet = followMap.getOrDefault(followerId, new HashSet<>());
        followSet.add(followeeId);
        followMap.put(followerId, followSet);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followSet = followMap.getOrDefault(followerId, new HashSet<>());
        followSet.remove(followeeId);
        followMap.put(followerId, followSet);
    }
}

class NewsFeed implements Comparable<NewsFeed> {
    int tweetId;
    int userId;
    int time;
    int index;

    public NewsFeed(int tweetId, int userId, int time, int index) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.time = time;
        this.index = index;
    }

    @Override
    public int compareTo(NewsFeed other) {
        return other.time - this.time;
    }
}
