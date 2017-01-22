package boilermake.swollmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static boilermake.swollmate.MainActivity.ids;

public class DiscoverActivity extends AppCompatActivity {

    int index = 0;
    //DataSnapshot dataSnapshot;
    Button yes, no;
    TextView goals, skillLvl, bio, name, noUsers;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Find workout buddies!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        noUsers = (TextView) findViewById(R.id.noUsers);
        noUsers.setVisibility(View.GONE);


        yes = (Button) findViewById(R.id.yesButton);
        no = (Button) findViewById(R.id.noButton);

        goals = (TextView) findViewById(R.id.goalsText);
        name = (TextView) findViewById(R.id.userName);
        skillLvl = (TextView) findViewById(R.id.skillText);
        bio = (TextView) findViewById(R.id.UserBio);

        if (index < ids.size()) {
            readFromFirebase(ids.get(index));
        } else {
            no_users();
        }


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < ids.size())
                    readFromFirebase(ids.get(index));
                else
                    no_users();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < ids.size())
                    readFromFirebase(ids.get(index));
                else
                    no_users();
            }
        });
    }

    public void no_users() {
        goals.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        skillLvl.setVisibility(View.GONE);
        bio.setVisibility(View.GONE);
        findViewById(R.id.userSkill).setVisibility(View.GONE);
        findViewById(R.id.userPic).setVisibility(View.GONE);
        findViewById(R.id.userGoals).setVisibility(View.GONE);
        findViewById(R.id.noUsers).setVisibility(View.VISIBLE);
    }

    public void readFromFirebase(String uID) {
        index++;
        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uID);

        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue().toString();

                if (key.equals("firstName")) {
                    name.setText(value);
                }
                if (key.equals("lastName")) {
                    name.setText(name.getText() + " " + value);
                }
                if (key.equals("goals")) {
                    goals.setText(value);
                }
                if (key.equals("skill")) {
                    skillLvl.setText(value);
                }
                if (key.equals("bio")) {
                    bio.setText(value);
                }
                if (key.equals("picURL")) {
                    Picasso.with(DiscoverActivity.this).load(value).into((ImageView) findViewById(R.id.userPic));
                }

                Log.d("Key: ", key);
                Log.d("Value: ", value);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersDatabase.addChildEventListener(childListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_discover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
