package boilermake.swollmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static boilermake.swollmate.MainActivity.currentChat;
import static boilermake.swollmate.MainActivity.me;
import static boilermake.swollmate.MainActivity.messages;

public class ChatActivity extends AppCompatActivity {
    DatabaseReference message;
    ChildEventListener listener;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messages = new ArrayList<>();

        LinearLayout ll = (LinearLayout) findViewById(R.id.boardLayout2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        Button send = (Button) findViewById(R.id.sendButton);
        final EditText editText = (EditText) findViewById(R.id.editBoardPost);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = false;
                message.removeEventListener(listener);
                String string = editText.getText().toString();
                addMessage(string);
                writeToFirebase(currentChat, messages);
            }
        });

    }

    public void addMessage(String message) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.boardLayout2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final TextView c1 = new TextView(this);
        messages.add(c1);
        c1.setTextSize(18);
        if (!check) {
            String string = me.firstName + ": " + message;
            c1.setText(string);
        } else {
            c1.setText(message);
        }
        ll.addView(c1);
    }

    @Override
    protected void onStart() {
        super.onStart();

        message = FirebaseDatabase.getInstance().getReference().child("messages").child(currentChat);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue().toString();

                addMessage(value);

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
        message.addChildEventListener(listener);
    }

    public void writeToFirebase(String child, ArrayList<TextView> list) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(list.get(i).getText().toString());
        }

        myRef.child("messages").child(child).setValue(strings);
    }
}
