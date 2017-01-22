package boilermake.swollmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static boilermake.swollmate.MainActivity.ids;

public class DiscoverActivity extends AppCompatActivity {

    int index = 0;
    //DataSnapshot dataSnapshot;
    Button yes, no;
    TextView goals, skillLvl, bio, name, noUsers;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Find workoutbuddies!");

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
        findViewById(R.id.imageView).setVisibility(View.GONE);
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
                if (key.equals("goals")) {

                    goals.setText(value);
                }
                if (key.equals("skillLvl")) {
                    skillLvl.setText(value);
                }
                if (key.equals("bio")) {

                    bio.setText(value);
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


}
