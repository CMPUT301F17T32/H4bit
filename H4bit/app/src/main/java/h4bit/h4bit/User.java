package h4bit.h4bit;

import android.media.Image;

/**
 * Created by James on 2017-10-20.
 */

public class User {

    private String username;
    private Image profilePicture;
    //private HabitAdapter habitAdapter;
    //private HabitEventAdapter habitEventAdapter;
    //private FollowAdapter followerAdapter;
    //private FollowAdapter followAdapter;

    public User (String username){
        this.username = username;

    }

    public void sendFollowRequest(String username) {

    }
    public String getUsername(){return username;}

    public String getRequests(){return "fix me";}
}

