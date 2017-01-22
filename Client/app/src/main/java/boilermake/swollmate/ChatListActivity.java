package boilermake.swollmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static boilermake.swollmate.MainActivity.buttons;
import static boilermake.swollmate.MainActivity.me;

public class ChatListActivity extends AppCompatActivity {

    private DatabaseReference matchedDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = (String) dataSnapshot.getValue().toString();
               // Log.d("test1: ", me.uID);
                if (value.contains(me.uID)) {
                    value = value.replace(me.uID, "");
                    value = value.trim();
                    addButton(value);
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

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final Button b1 = new Button(this);
        buttons.add(b1);
        b1.setLayoutParams(params);
        b1.setPadding(150, 0, 0, 0);
        b1.setText(name);
        b1.setTextSize(14);
        ll.addView(b1);
    }



}
