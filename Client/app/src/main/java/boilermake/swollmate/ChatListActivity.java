package boilermake.swollmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static boilermake.swollmate.MainActivity.chats;
import static boilermake.swollmate.MainActivity.currentChat;
import static boilermake.swollmate.MainActivity.matched;
import static boilermake.swollmate.MainActivity.me;

public class ChatListActivity extends AppCompatActivity {

    private DatabaseReference matchedDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chats = new ArrayList<>();

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final Button b1 = new Button(this);
        b1.setLayoutParams(params);
        b1.setPadding(150, 0, 0, 0);
        b1.setText("Button");
        b1.setTextSize(18);
        ll.addView(b1);

        //  addButton("");

    }


    @Override
    public void onStart() {
        super.onStart();
        matchedDatabase = FirebaseDatabase.getInstance().getReference().child("matched");

        final ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = (String) dataSnapshot.getValue().toString();
                // Log.d("test1: ", me.uID);
                if (value.contains(me.uID)) {
                    value = value.replace(me.uID, "");
                    value = value.trim();

                    final String[] name = new String[1];

                    DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(value);
                    ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String key = dataSnapshot.getKey();
                            String value = dataSnapshot.getValue().toString();

                            if (key.equals("firstName")) {
                                name[0] = value;
                            }

                            if (key.equals("lastName")) {
                                name[0] = name[0] + " " + value;
                                addButton(name[0]);
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
                    usersDatabase.addChildEventListener(childEventListener);

                    //addButton(value);
                    Log.d("test2: ", value);

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
        matchedDatabase.addChildEventListener(childListener);

    }

    public void addButton(String name) {
        for (int i = 0; i < chats.size(); i++){
            if (chats.get(i).getText().equals(name)) {
                return;
            }
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final Button b1 = new Button(this);
        chats.add(b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentChat = matched.get(chats.indexOf(b1));
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<String> s = MainActivity.matched;

        b1.setLayoutParams(params);
        b1.setPadding(150, 0, 0, 0);
        b1.setText(name);
        b1.setTextSize(14);
        ll.addView(b1);
    }


}
