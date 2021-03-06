package boilermake.swollmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<String> ids = new ArrayList<>();
    public static User me;
    public static ArrayList<Button> chats = new ArrayList<>();

    public static ArrayList<String> pending = new ArrayList<>();
    public static ArrayList<String> matched = new ArrayList<>();

    public static ArrayList<TextView> messages = new ArrayList<>();

    public static FirebaseAuth mAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;

    public static String currentChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

@SuppressWarnings("WeakerAccess")
class User {
    public String uID;
    public String email;
    public String firstName;
    public String lastName;
    public String picURL;
    public int age;
    public String goals;
    public String gender;
    public String bio;
    public String skill;
    public String like;
    public String dislike;
    public String pending;
    public String matched;

    public User() {
    }
}