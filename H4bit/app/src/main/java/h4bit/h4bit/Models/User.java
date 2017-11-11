package h4bit.h4bit.Models;

import android.media.Image;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by James on 2017-10-20.
 */

public class User {

    private String username;
    private Image profilePicture;
    private HabitList habitList;
    private HabitEventList habitEventList;
    private ArrayList<String> followers;
    private ArrayList<String> following;
    private ArrayList<String> requests;

    //private HabitAdapter habitAdapter;
    //private HabitEventAdapter habitEventAdapter;
    //private FollowAdapter followerAdapter;
    //private FollowAdapter followAdapter;

    public User (){
        this.habitEventList = new HabitEventList();
        this.habitList = new HabitList();
        requests = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();

    }
    public User (String username){
        this.username = username;
        this.habitEventList = new HabitEventList();
        this.habitList = new HabitList();
        requests = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();

    }

    public void sendFollowRequest(String username) {

    }
    public String getUsername(){
        return username;
    }


    public void addHabit(Habit habit){
        this.habitList.addHabit(habit);
    }
    // requests
    public void addRequests(String username) {
        requests.add(username);
    }

    public void removeRequests(String username) {
        requests.remove(username);
    }

    public ArrayList<String> getRequests(){
        return requests;
    }

    // following
    public void addFollowing(String follow){
        following.add(follow);
    }

    public void removeFollowing(String username){
        following.remove(username);
    }

    public ArrayList<String> getFollowing(){
        return following;
    }

    // followers
    public void addFollower(String follow){
        followers.add(follow);
    }

    public void removeFollower(String username){
        followers.remove(username);
    }
    public ArrayList<String> getFollowers() {
        return followers;
    }

    public HabitList getHabitList(){
        return this.habitList;
    }




}

