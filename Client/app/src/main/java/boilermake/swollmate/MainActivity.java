package boilermake.swollmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList <User> users = new ArrayList<>();
    public static ArrayList <String> ids = new ArrayList<>();
    public static User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

class User {
    String uID;
    String email;
    String firstName;
    String lastName;
    int age;
    String goal[];
    String gender;
    String bio;

    public User() {
    }

    public User(String uID, String email, String firstName, String lastName, int age, String[] goal, String gender, String bio) {
        this.uID = uID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.goal = goal;
        this.gender = gender;
        this.bio = bio;
    }
}