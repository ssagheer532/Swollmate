package boilermake.swollmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static boilermake.swollmate.MainActivity.ids;
import static boilermake.swollmate.MainActivity.me;

public class DiscoverActivity extends AppCompatActivity {
    int index = 1;
    DataSnapshot dataSnapshot;
    Button yes, no;
    TextView goals, skillLvl, bio, name;
    private DatabaseReference idsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        yes = (Button) findViewById(R.id.yesButton);
        no = (Button) findViewById(R.id.noButton);

        goals = (TextView) findViewById(R.id.goalsText);
        name = (TextView) findViewById(R.id.userName);
        skillLvl = (TextView) findViewById(R.id.skillText);
        bio = (TextView) findViewById(R.id.UserBio);

        if (ids.get(index).equals(me.uID))
            index++;

        readFromFirebase(0);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromFirebase(index++);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromFirebase(index++);
            }
        });
    }

    public void readFromFirebase(int index) {
        idsDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        String example = (String) dataSnapshot.getValue();
        Log.d("test", example);

        //for (int i = 0; i < idsDatabase)
        //idsDatabase.

    }

}
