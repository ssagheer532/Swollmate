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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static boilermake.swollmate.MainActivity.ids;
import static boilermake.swollmate.MainActivity.me;

public class DiscoverActivity extends AppCompatActivity {

    int index = 0;
    //DataSnapshot dataSnapshot;
    Button yes, no;
    TextView goals, skillLvl, bio, name, noUsers;
    private DatabaseReference usersDatabase;
    private DatabaseReference allUsers;

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
            if (ids.get(index).equals(me.uID)) {
                index++;
            }
            if (index < ids.size()) {
                readFromFirebase(ids.get(index));
            } else {
                no_users();
            }
        } else {
            no_users();
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < ids.size()) {
                    String string = me.uID + " " + ids.get(index);
                    String opp = ids.get(index) + " " + me.uID;

                    if (MainActivity.pending.contains(opp) || MainActivity.pending.contains(string)) {
                        MainActivity.matched.add(string);
                        Set<String> mtemp = new HashSet<String>();
                        mtemp.addAll(MainActivity.matched);
                        MainActivity.matched.clear();
                        MainActivity.matched.addAll(mtemp);
                        writeToFirebase("matched", MainActivity.matched);

                        MainActivity.pending.remove(opp);
                        Set<String> ptemp = new HashSet<String>();
                        ptemp.addAll(MainActivity.pending);
                        MainActivity.pending.clear();
                        MainActivity.pending.addAll(ptemp);
                        writeToFirebase("pending", MainActivity.pending);
                    } else {
                        MainActivity.pending.add(string);
                        Set<String> ptemp = new HashSet<String>();
                        ptemp.addAll(MainActivity.pending);
                        MainActivity.pending.clear();
                        MainActivity.pending.addAll(ptemp);
                        writeToFirebase("pending", MainActivity.pending);
                    }
                    index++;
                    if (index < ids.size()) {
                        readFromFirebase(ids.get(index));
                    } else {
                        no_users();
                    }
                } else
                    no_users();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < ids.size()) {
                    String string = me.uID + " " + ids.get(index);
                    String opp = ids.get(index) + " " + me.uID;

                    if (MainActivity.pending.contains(opp) || MainActivity.pending.contains(string)) {
                        MainActivity.pending.remove(string);
                        Set<String> ptemp = new HashSet<String>();
                        ptemp.addAll(MainActivity.pending);
                        MainActivity.pending.clear();
                        MainActivity.pending.addAll(ptemp);
                        writeToFirebase("pending", MainActivity.pending);
                    }

                    index++;
                    if (index < ids.size()) {
                        readFromFirebase(ids.get(index));
                    } else {
                        no_users();
                    }
                } else
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
        if (uID.equals(me.uID)) {
            index++;
            return;
        }
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

    public void writeToFirebase(String child, ArrayList<String> list) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child(child).setValue(list);
    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference pending = FirebaseDatabase.getInstance().getReference().child("pending");
        ChildEventListener pListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue().toString();

                MainActivity.pending.add(value);

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
        pending.addChildEventListener(pListener);

        DatabaseReference matched = FirebaseDatabase.getInstance().getReference().child("matched");
        ChildEventListener mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue().toString();

                MainActivity.matched.add(value);

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
        matched.addChildEventListener(mListener);


        //TODO For chat
        /*
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.w(TAG, "Successful Read" + dataSnapshot.getValue());
                mStatusTextView.setText((String) dataSnapshot.getValue());
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        test.addValueEventListener(postListener);
        */

        /*
        final int[] i = new int[1];
        i[0] = 0;
        for ( ; i[0] < MainActivity.ids.size(); i[0]++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    allUsers = FirebaseDatabase.getInstance().getReference().child("users").child(MainActivity.ids.get(i[0]));
                    final User user = new User();
                    user.like = new ArrayList<>();
                    user.dislike = new ArrayList<>();
                    user.pending = new ArrayList<>();
                    user.matched = new ArrayList<>();
                    ChildEventListener childListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.d("DiscoverActivity", "onChildAdded: " + dataSnapshot);
                            String key = dataSnapshot.getKey();
                            String value = String.valueOf(dataSnapshot.getValue());

                            if (key.equals("firstName")) {
                                user.firstName = value;
                            }

                            else if (key.equals("lastName")) {
                                user.lastName = value;
                            }

                            else if (key.equals("picURL")) {
                                user.picURL = value;
                            }

                            else if (key.equals("age")) {
                                user.age = Integer.parseInt(value);
                            }

                            else if (key.equals("bio")) {
                                user.bio = value;
                            }

                            else if (key.equals("gender")) {
                                user.gender = value;
                            }

                            else if (key.equals("skill")) {
                                user.skill = value;
                            }

                            else if (key.equals("goals")) {
                                user.goals = value;
                            }

                            else if (key.equals("like")) {
                                user.like.add(value);
                            }

                            else if (key.equals("dislike")) {
                                user.dislike.add(value);
                            }

                            else if (key.equals("pending")) {
                                user.pending.add(value);
                            }

                            else if (key.equals("matched")) {
                                user.matched.add(value);
                            }

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
                    allUsers.addChildEventListener(childListener);
                    MainActivity.users.add(user);
                }
            });

            android.os.SystemClock.sleep(1000);
        }
        ArrayList <User> temp = MainActivity.users;
        int a = 0;
        a++;
        */
    }
}
